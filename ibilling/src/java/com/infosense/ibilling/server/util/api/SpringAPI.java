package com.infosense.ibilling.server.util.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infosense.ibilling.server.entity.AchDTO;
import com.infosense.ibilling.server.entity.CreditCardDTO;
import com.infosense.ibilling.server.item.ItemDTOEx;
import com.infosense.ibilling.server.item.PricingField;
import com.infosense.ibilling.server.payment.PaymentAuthorizationDTOEx;
import com.infosense.ibilling.server.util.IWebServicesSessionBean;
import com.infosense.ibilling.server.util.RemoteContext;
import com.infosense.ibilling.server.ws.BillingProcessConfigurationWS;
import com.infosense.ibilling.server.ws.BillingProcessWS;
import com.infosense.ibilling.server.ws.ContactWS;
import com.infosense.ibilling.server.ws.CreateResponseWS;
import com.infosense.ibilling.server.ws.InvoiceWS;
import com.infosense.ibilling.server.ws.ItemTypeWS;
import com.infosense.ibilling.server.ws.OrderLineWS;
import com.infosense.ibilling.server.ws.OrderProcessWS;
import com.infosense.ibilling.server.ws.OrderWS;
import com.infosense.ibilling.server.ws.PaymentWS;
import com.infosense.ibilling.server.ws.UserTransitionResponseWS;
import com.infosense.ibilling.server.ws.UserWS;
import com.infosense.ibilling.server.ws.ValidatePurchaseWS;

/**
 * The implementation of IbillingAPI
 * @see com.infosense.ibilling.server.util.api.IbillingAPI 
 *
 */
public class SpringAPI implements IbillingAPI {

    private IWebServicesSessionBean session = null;

    public SpringAPI() throws IbillingAPIException {
        this(RemoteContext.Name.API_CLIENT);
    }

    public SpringAPI(RemoteContext.Name bean) {
        session = (IWebServicesSessionBean) RemoteContext.getBean(bean);
    }

    public Integer applyPayment(PaymentWS payment, Integer invoiceId)
            throws IbillingAPIException {
        try {
            return session.applyPayment(payment, invoiceId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }
    

    public CreateResponseWS create(UserWS user, OrderWS order)
            throws IbillingAPIException {
        try {
            return session.create(user, order);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer createItem(ItemDTOEx dto) throws IbillingAPIException {
        try {
            return session.createItem(dto);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }    

    public Integer createOrder(OrderWS order) throws IbillingAPIException {
        try {
            return session.createOrder(order);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer createOrderAndInvoice(OrderWS order) throws IbillingAPIException {
        try {
            return session.createOrderAndInvoice(order);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public PaymentAuthorizationDTOEx createOrderPreAuthorize(OrderWS order)
            throws IbillingAPIException {
        try {
            return session.createOrderPreAuthorize(order);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer createUser(UserWS newUser) throws IbillingAPIException {
        try {
            return session.createUser(newUser);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void deleteOrder(Integer id) throws IbillingAPIException {
        try {
            session.deleteOrder(id);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void deleteUser(Integer userId) throws IbillingAPIException {
        try {
            session.deleteUser(userId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void deleteInvoice(Integer invoiceId) throws IbillingAPIException {
        try {
            session.deleteInvoice(invoiceId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public ItemDTOEx[] getAllItems() throws IbillingAPIException {
        try {
            return session.getAllItems();
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public InvoiceWS getInvoiceWS(Integer invoiceId)
            throws IbillingAPIException {
        try {
            return session.getInvoiceWS(invoiceId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getInvoicesByDate(String since, String until)
            throws IbillingAPIException {
        try {
            return session.getInvoicesByDate(since, until);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public byte[] getPaperInvoicePDF(Integer invoiceId) throws IbillingAPIException {
        try {
            return session.getPaperInvoicePDF(invoiceId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }    

    public Integer[] getLastInvoices(Integer userId, Integer number)
            throws IbillingAPIException {
        try {
            return session.getLastInvoices(userId, number);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getUserInvoicesByDate(Integer userId, String since,
            String until) throws IbillingAPIException {
        try {
            return session.getUserInvoicesByDate(userId, since, until);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getLastInvoicesByItemType(Integer userId, Integer itemTypeId, Integer number)
            throws IbillingAPIException {
        try {
            return session.getLastInvoicesByItemType(userId, itemTypeId, number);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getLastOrders(Integer userId, Integer number)
            throws IbillingAPIException {
        try {
            return session.getLastOrders(userId, number);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }


    public Integer[] getLastOrdersByItemType(Integer userId, Integer itemTypeId, Integer number)
            throws IbillingAPIException {
        try {
            return session.getLastOrdersByItemType(userId, itemTypeId, number);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public OrderWS getCurrentOrder(Integer userId, Date date) 
            throws IbillingAPIException {
        try {
            return session.getCurrentOrder(userId, date);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public OrderWS updateCurrentOrder(Integer userId, OrderLineWS[] lines, 
            PricingField[] fields, Date date, String eventDescription) 
            throws IbillingAPIException {
        try {
            return session.updateCurrentOrder(userId, lines, 
                    PricingField.setPricingFieldsValue(fields), date, 
                    eventDescription);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getLastPayments(Integer userId, Integer number)
            throws IbillingAPIException {
        try {
            return session.getLastPayments(userId, number);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public InvoiceWS getLatestInvoice(Integer userId)
            throws IbillingAPIException {
        try {
            return session.getLatestInvoice(userId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public InvoiceWS getLatestInvoiceByItemType(Integer userId, Integer itemTypeId)
            throws IbillingAPIException {
        try {
            return session.getLatestInvoiceByItemType(userId, itemTypeId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public OrderWS getLatestOrder(Integer userId) throws IbillingAPIException {
        try {
            return session.getLatestOrder(userId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public OrderWS getLatestOrderByItemType(Integer userId, Integer itemTypeId) throws IbillingAPIException {
        try {
            return session.getLatestOrderByItemType(userId, itemTypeId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public PaymentWS getLatestPayment(Integer userId)
            throws IbillingAPIException {
        try {
            return session.getLatestPayment(userId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public OrderWS getOrder(Integer orderId) throws IbillingAPIException {
        try {
            return session.getOrder(orderId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getOrderByPeriod(Integer userId, Integer periodId)
            throws IbillingAPIException {
        try {
            return session.getOrderByPeriod(userId, periodId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public OrderLineWS getOrderLine(Integer orderLineId)
            throws IbillingAPIException {
        try {
            return session.getOrderLine(orderLineId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public PaymentWS getPayment(Integer paymentId) throws IbillingAPIException {
        try {
            return session.getPayment(paymentId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public ContactWS[] getUserContactsWS(Integer userId)
            throws IbillingAPIException {
        try {
            return session.getUserContactsWS(userId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer getUserId(String username) throws IbillingAPIException {
        try {
            return session.getUserId(username);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public UserTransitionResponseWS[] getUserTransitions(Date from, Date to)
            throws IbillingAPIException {
        try {
            return session.getUserTransitions(from, to);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public UserTransitionResponseWS[] getUserTransitionsAfterId(Integer id)
            throws IbillingAPIException {
        try {
            return session.getUserTransitionsAfterId(id);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public UserWS getUserWS(Integer userId) throws IbillingAPIException {
        try {
            return session.getUserWS(userId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getUsersByCustomField(Integer typeId, String value)
            throws IbillingAPIException {
        try {
            return session.getUsersByCustomField(typeId, value);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getUsersInStatus(Integer statusId)
            throws IbillingAPIException {
        try {
            return session.getUsersInStatus(statusId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] getUsersNotInStatus(Integer statusId)
            throws IbillingAPIException {
        try {
            return session.getUsersNotInStatus(statusId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }
    
    public PaymentAuthorizationDTOEx payInvoice(Integer invoiceId)
            throws IbillingAPIException {
        try {
            return session.payInvoice(invoiceId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void updateCreditCard(Integer userId, CreditCardDTO creditCard)
            throws IbillingAPIException {
        try {
            session.updateCreditCard(userId, creditCard);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }
    
    public void updateAch(Integer userId, AchDTO ach) throws IbillingAPIException {
        try {
            session.updateAch(userId, ach);
                    //new com.infosense.ibilling.server.user.db.AchDTO(ach));
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void updateOrder(OrderWS order) throws IbillingAPIException {
        try {
            session.updateOrder(order);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void updateOrderLine(OrderLineWS line) throws IbillingAPIException {
        try {
            session.updateOrderLine(line);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void updateUser(UserWS user) throws IbillingAPIException {
        try {
            session.updateUser(user);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void updateUserContact(Integer userId, Integer typeId,
            ContactWS contact) throws IbillingAPIException {
        try {
            session.updateUserContact(userId, typeId, contact);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }
    
    public Integer[] getUsersByCreditCard(String number)
        throws IbillingAPIException {
        try {
            return session.getUsersByCreditCard(number);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }
    
    public ItemDTOEx getItem(Integer itemId, Integer userId, PricingField[] fields) 
            throws IbillingAPIException {
        try {
            return session.getItem(itemId, userId, PricingField.setPricingFieldsValue(fields));
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }
    
    public OrderWS rateOrder(OrderWS order) throws IbillingAPIException {
        try {
            return session.rateOrder(order);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public OrderWS[] rateOrders(OrderWS orders[]) throws IbillingAPIException {
        try {
            return session.rateOrders(orders);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }
    
    public void updateItem(ItemDTOEx item) throws IbillingAPIException {
        try {
            session.updateItem(item);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer[] createInvoice(Integer userId, boolean onlyRecurring)
        throws IbillingAPIException {
        try {
            return session.createInvoice(userId, onlyRecurring);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer createInvoiceFromOrder(Integer orderId, Integer invoiceId) throws IbillingAPIException {
        try {
            return session.createInvoiceFromOrder(orderId, invoiceId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public String isUserSubscribedTo(Integer userId, Integer itemId)
            throws IbillingAPIException {
        try {
            return session.isUserSubscribedTo(userId, itemId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    @Override
    public Integer[] getUserItemsByCategory(Integer userId, Integer categoryId)
            throws IbillingAPIException {
        try {
            return session.getUserItemsByCategory(userId, categoryId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public ItemDTOEx[] getItemByCategory(Integer itemTypeId) throws IbillingAPIException {
        try {
            return session.getItemByCategory(itemTypeId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public ItemTypeWS[] getAllItemCategories() throws IbillingAPIException {
        try {
            return session.getAllItemCategories();
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    /*
     * @see com.infosense.ibilling.server.util.api.JbillingAPI#processPayment(com.infosense.ibilling.server.payment.PaymentWS)
     */
    @Override
    public PaymentAuthorizationDTOEx processPayment(PaymentWS payment,Integer invoiceId)
            throws IbillingAPIException {
        try {
            return session.processPayment(payment,invoiceId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public ValidatePurchaseWS validatePurchase(Integer userId, Integer itemId,
            PricingField[] fields) throws IbillingAPIException {
        try {
            return session.validatePurchase(userId, itemId, 
                    PricingField.setPricingFieldsValue(fields));
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public ValidatePurchaseWS validateMultiPurchase(Integer userId, 
            Integer[] itemIds, PricingField[][] fields) 
            throws IbillingAPIException {
        try {
            String[] pricingFields = null;
            if (fields != null) {
                pricingFields = new String[fields.length];
                for (int i = 0; i < pricingFields.length; i++) {
                    pricingFields[i] = PricingField.setPricingFieldsValue(
                            fields[i]);
                }
            }
            return session.validateMultiPurchase(userId, itemIds, 
                    pricingFields);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public Integer createItemCategory(ItemTypeWS itemType) throws IbillingAPIException {
        try {
            return session.createItemCategory(itemType);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void updateItemCategory(ItemTypeWS itemType) throws IbillingAPIException {
        try {
            session.updateItemCategory(itemType);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    @Override
    public Integer getAutoPaymentType(Integer userId)
            throws IbillingAPIException {
        try {
            return session.getAuthPaymentType(userId);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    @Override
    public void setAutoPaymentType(Integer userId, Integer autoPaymentType, boolean use)
            throws IbillingAPIException {
        try {
            session.setAuthPaymentType(userId, autoPaymentType, use);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

    public void generateRules(String rulesData) throws IbillingAPIException {
        try {
            session.generateRules(rulesData);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
    }

	@Override
	public BillingProcessConfigurationWS getBillingProcessConfiguration()
			throws IbillingAPIException {
		try {
            return session.getBillingProcessConfiguration();
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
	}

	@Override
	public Integer createUpdateBillingProcessConfiguration(
			BillingProcessConfigurationWS ws) throws IbillingAPIException {
		try {
            return session.createUpdateBillingProcessConfiguration(ws);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
	}

	@Override
	public Integer createUpdateOrder(OrderWS order) throws IbillingAPIException {
		try {
            return session.createUpdateOrder(order);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
	}

	@Override
	public boolean triggerBilling(Date runDate) {
		return session.triggerBilling(runDate);
	}

	@Override
	public void triggerAgeing(Date runDate) {
		// TODO Auto-generated method stub
		session.triggerAgeing(runDate);
	}

	@Override
	public BillingProcessWS getBillingProcess(Integer processId) {
		// TODO Auto-generated method stub
		return session.getBillingProcess(processId);
	}

	@Override
	public Integer getLastBillingProcess() throws IbillingAPIException {
		try {
            return session.getLastBillingProcess();
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
	}

	@Override
	public List<OrderProcessWS> getOrderProcesses(Integer orderId) {
		// TODO Auto-generated method stub
		return session.getOrderProcesses(orderId);
	}

	@Override
	public List<OrderProcessWS> getOrderProcessesByInvoice(Integer invoiceId) {
		// TODO Auto-generated method stub
		return session.getOrderProcessesByInvoice(invoiceId);
	}

	@Override
	public BillingProcessWS getReviewBillingProcess() {
		// TODO Auto-generated method stub
		return session.getReviewBillingProcess();
	}

	@Override
	public BillingProcessConfigurationWS setReviewApproval(Boolean flag)
			throws IbillingAPIException {
		try {
            return session.setReviewApproval(flag);
        } catch (Exception e) {
            throw new IbillingAPIException(e);
        }
	}

	@Override
	public Integer[] getAllInvoices(Integer userId) {
		return session.getAllInvoices(userId);
	}

	@Override
	public List<Integer> getBillingProcessGeneratedInvoices(Integer processId) {
		return session.getBillingProcessGeneratedInvoices(processId);
	}

	@Override
	public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory) {
		return session.createPlan(newPlan, cpu, memory);
	}

	@Override
	public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory, Map<String, Integer> properties) {
		return session.createPlan(newPlan, cpu, memory, properties);
	}

	@Override
	public InvoiceWS getLatestInvoiceByOrder(Integer orderId) {
		return session.getLatestInvoiceByOrder(orderId);
	}

	@Override
	public List<OrderLineWS> getOrderLineByUUID(String uuid) {
		return session.getOrderLineByUUID(uuid);
	}
	
}
