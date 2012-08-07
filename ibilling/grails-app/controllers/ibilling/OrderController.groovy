/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */

package ibilling

import java.util.Iterator;

import ibilling.Filter;
import ibilling.FilterConstraint;
import ibilling.FilterType;
import ibilling.RecentItemType;

import com.infosense.ibilling.client.util.Constants;
import com.infosense.ibilling.client.util.DownloadHelper
import com.infosense.ibilling.common.SessionInternalError
import com.infosense.ibilling.server.customer.CustomerBL
import com.infosense.ibilling.server.invoice.InvoiceBL
import com.infosense.ibilling.server.invoice.db.InvoiceDAS
import com.infosense.ibilling.server.item.CurrencyBL
import com.infosense.ibilling.server.order.OrderBL
import com.infosense.ibilling.server.order.db.OrderDAS
import com.infosense.ibilling.server.order.db.OrderDTO
import com.infosense.ibilling.server.order.db.OrderLineDTO;
import com.infosense.ibilling.server.order.db.OrderPeriodDAS
import com.infosense.ibilling.server.order.db.OrderStatusDAS
import com.infosense.ibilling.server.user.db.CustomerDTO
import com.infosense.ibilling.server.user.db.UserDAS
import com.infosense.ibilling.server.user.db.UserDTO
import com.infosense.ibilling.server.util.csv.CsvExporter
import com.infosense.ibilling.server.util.csv.Exporter
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import com.infosense.ibilling.server.user.db.CompanyDTO
import com.infosense.ibilling.server.ws.OrderLineWS
import com.infosense.ibilling.server.ws.OrderWS;
import com.infosense.ibilling.server.ws.UserWS;

import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.infosense.ibilling.client.util.SortableCriteria
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

/**
 *
 * @author vikas bodani
 * @since  20-Jan-2011
 *
 */

@Secured(["MENU_92"])
class OrderController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    def webServicesSession
    def viewUtils
    def filterService
    def recentItemService
    def breadcrumbService

    def index = {
        redirect action: list, params: params
    }

    def getFilteredOrders(filters, GrailsParameterMap params) {
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return OrderDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {
            createAlias('baseUserByUserId', 'u', Criteria.LEFT_JOIN)
            and {
                filters.each { filter ->
                    if (filter.value) {
                        //handle orderStatus & orderPeriod separately
                        if (filter.constraintType == FilterConstraint.STATUS) {
                            if (filter.field == 'orderStatus') {
                                def statuses = new OrderStatusDAS().findAll()
                                eq("orderStatus", statuses.find{ it.id == filter.integerValue })
                            } else if (filter.field == 'orderPeriod') {
                                def periods = new OrderPeriodDAS().findAll()
                                eq("orderPeriod", periods.find{ it.id == filter.integerValue })
                            }
                        } else {
                            addToCriteria(filter.getRestrictions());
                        }
                    }
                }
                eq('u.company', new CompanyDTO(session['company_id']))
                eq('deleted', 0)

                // limit list to only this customer's orders
                if (SpringSecurityUtils.ifNotGranted("ORDER_28")) {
                    eq('u.id', session['user_id'])
                }
            }

            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }

    def list = {
        def filters = filterService.getFilters(FilterType.ORDER, params)
        def orders = getFilteredOrders(filters, params)

        def selected = params.id ? webServicesSession.getOrder(params.int("id")) : null
        def user = selected ? webServicesSession.getUserWS(selected.userId) : null
		
		Map<String , String> groupMap = new HashMap<String,String>()
		if(selected!=null){
			groupMap = formatOrderLinesWithGroupId(selected)
		}
		
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'orders', model: [ orders: orders, order: selected, user: user, currencies: currencies, filters: filters ]
        } else {
            [ orders: orders, order: selected, user: user, currencies: currencies, filters: filters, groupMap: groupMap ]
        }
    }

    @Secured(["ORDER_24"])
    def show = {
        OrderWS order = webServicesSession.getOrder(params.int('id'))
        UserWS user = webServicesSession.getUserWS(order.getUserId())
		Map<String , String> groupMap = formatOrderLinesWithGroupId(order)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, order.id)
        recentItemService.addRecentItem(order.id, RecentItemType.ORDER)
		
        render template:'show', model: [order: order, user: user, currencies: currencies, groupMap:groupMap]
    }
	
	def formatOrderLinesWithGroupId(OrderWS order){
		Map<String , String> groupMap = new HashMap<String, String>()
		String classType = "odd"
		String groupId = null
		for (Iterator it = order.getOrderLines().iterator(); it.hasNext();) {
			OrderLineWS line = (OrderLineWS) it.next()
			groupId = line.getGroupId()
			if(groupId == null || groupId == ""){
					continue
			}
			if(!groupMap.containsKey(groupId)){
				groupMap.put(groupId,classType)
				classType = classType == "odd"	? "even" : "odd"
			}
		}
		return groupMap
	}
    /**
     * Applies the set filters to the order list, and exports it as a CSV for download.
     */
    @Secured(["ORDER_25"])
    def csv = {
        def filters = filterService.getFilters(FilterType.ORDER, params)

        params.max = CsvExporter.MAX_RESULTS
        def orders = getFilteredOrders(filters, params)

        if (orders.totalCount > CsvExporter.MAX_RESULTS) {
            flash.error = message(code: 'error.export.exceeds.maximum')
            flash.args = [ CsvExporter.MAX_RESULTS ]
            redirect action: 'list', id: params.id

        } else {
            DownloadHelper.setResponseHeader(response, "orders.csv")
            Exporter<OrderDTO> exporter = CsvExporter.createExporter(OrderDTO.class);
            render text: exporter.export(orders), contentType: "text/csv"
        }
    }

    /**
     * Convenience shortcut, this action shows all invoices for the given user id.
     */
    def user = {
        def filter = new Filter(type: FilterType.ORDER, constraintType: FilterConstraint.EQ, field: 'baseUserByUserId.id', template: 'id', visible: true, integerValue: params.int('id'))
        filterService.setFilter(FilterType.ORDER, filter)
        redirect action: 'list'
    }

    @Secured(["ORDER_23"])
    def generateInvoice = {
        log.debug "generateInvoice for order ${params.id}"

        def orderId = params.id?.toInteger()

        Integer invoiceID= null;
        try {
            invoiceID = webServicesSession.createInvoiceFromOrder(orderId, null)

        } catch (SessionInternalError e) {
            flash.error= 'order.error.generating.invoice'
            redirect action: 'list', params: [ id: params.id ]
            return
        }

        if ( null != invoiceID) {
            flash.message ='order.geninvoice.success'
            flash.args = [orderId]
            redirect controller: 'invoice', action: 'list', params: [id: invoiceID]

        } else {
            flash.error ='order.error.geninvoice.inactive'
            redirect action: 'list', params: [ id: params.id ]
        }
    }

    @Secured(["ORDER_23"])
    def applyToInvoice = {
        def invoices = getApplicableInvoices(params.int('userId'))

        if (!invoices || invoices.size() == 0) {
            flash.error = 'order.error.invoices.not.found'
            flash.args = [params.userId]
            redirect (action: 'list', params: [ id: params.id ])
        }

        session.applyToInvoiceOrderId = params.int('id')
        [ invoices:invoices, currencies: currencies, orderId: params.id ]
    }

    @Secured(["ORDER_23"])
    def apply = {
        log.debug "apply: for order ${params.id}"
        Integer invoiceID= params.int('invoiceId')

        try {
            OrderDTO order= new OrderDAS().find(params.int('id'))
            if (!order.getStatusId().equals(
                    Constants.ORDER_STATUS_ACTIVE)) {
                throw new Exception('order.error.status.not.active')
            } else if ( !invoiceID ) {
                throw new Exception('order.error.invoice.is.null')
            }

            def invoice= webServicesSession.createInvoiceFromOrder(order.getId(), invoiceID)
            if ( !invoice ) {
                throw new Exception('order.error.apply.invoice')
            }
            flash.message = 'order.succcessfully.applied.to.invoice'
            flash.args = [params.id, invoice]
        } catch (SessionInternalError e){
            flash.error ='order.error.apply.invoice'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list', params: [ id: params.id ]
    }

    def getApplicableInvoices(Integer userId) {

        CustomerDTO payingUser
        Integer _userId
        UserDTO user= new UserDAS().find(userId)
        if (user.getCustomer()?.getParent()) {
            payingUser= new CustomerBL(user.getCustomer().getId()).getInvoicableParent()
            _userId=payingUser.getBaseUser().getId()
        } else {
            _userId= user.getId()
        }
        InvoiceDAS das= new InvoiceDAS()
        List invoices =  new ArrayList()
        for (Iterator it= das.findAllApplicableInvoicesByUser(_userId ).iterator(); it.hasNext();) {
            invoices.add InvoiceBL.getWS(das.find (it.next()))
        }

        log.debug "Found ${invoices.size()} for user ${_userId}"

        invoices as List
    }

    def getCurrencies() {
        def currencies = new CurrencyBL().getCurrencies(session['language_id'].toInteger(), session['company_id'].toInteger())
        return currencies.findAll{ it.inUse }
    }

    def byProcess = {
        OrderBL bl= new OrderBL();
        List<Integer> orderIds= bl.getOrdersByProcess(params.id.toInteger())

        log.debug "Expecting ${orderIds.size()} orders."

        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        def filters=filterService.getFilters(FilterType.ORDER, params)

        def orders = OrderDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {
            and {
                'in'('id', orderIds.toArray(new Integer[orderIds.size()]))
                //eq('deleted', 0)
            }
            order("id", "desc")
        }
        log.debug("Found ${orders.size()} orders.")
        render view: 'list', model: [orders:orders, filters:filters]
    }

    @Secured(["ORDER_22"])
    def deleteOrder = {
        try {
            webServicesSession.deleteOrder(params.int('id'))
            flash.message = 'order.delete.success'
            flash.args = [params.id, params.id]
        } catch (SessionInternalError e){
            flash.error ='order.error.delete'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list'
    }

}
