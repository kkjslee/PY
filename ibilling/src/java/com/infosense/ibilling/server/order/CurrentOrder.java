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

package com.infosense.ibilling.server.order;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


import com.infosense.ibilling.common.CommonConstants;
import com.infosense.ibilling.common.SessionInternalError;
import com.infosense.ibilling.common.Util;
import com.infosense.ibilling.server.order.db.OrderDAS;
import com.infosense.ibilling.server.order.db.OrderDTO;
import com.infosense.ibilling.server.user.EntityBL;
import com.infosense.ibilling.server.user.UserBL;
import com.infosense.ibilling.server.util.Constants;
import com.infosense.ibilling.server.util.Context;
import com.infosense.ibilling.server.util.MapPeriodToCalendar;
import com.infosense.ibilling.server.util.audit.EventLogger;
import com.infosense.ibilling.server.util.db.CurrencyDTO;

import java.util.List;
import org.springmodules.cache.CachingModel;
import org.springmodules.cache.FlushingModel;
import org.springmodules.cache.provider.CacheProviderFacade;

public class CurrentOrder {
    private static final Logger LOG = Logger.getLogger(CurrentOrder.class);

    private final EventLogger eLogger = EventLogger.getInstance();

    private final Date eventDate;
    private final Integer userId;
    private final UserBL user;

    // current order
    private OrderBL order = null;

    // cache management
    private CacheProviderFacade cache;
    private CachingModel cacheModel;
    private FlushingModel flushModel;

    public CurrentOrder(Integer userId, Date eventDate) {
        if (userId == null) throw new IllegalArgumentException("Parameter userId cannot be null!");
        if (eventDate == null) throw new IllegalArgumentException("Parameter eventDate cannot be null!");

        this.userId = userId;
        this.eventDate = eventDate;
        this.user = new UserBL(userId);

        cache = (CacheProviderFacade) Context.getBean(Context.Name.CACHE);
        cacheModel = (CachingModel) Context.getBean(Context.Name.CACHE_MODEL_RW);
        flushModel = (FlushingModel) Context.getBean(Context.Name.CACHE_FLUSH_MODEL_RW);

        LOG.debug("Current order constructed with user " + userId + " event date " + eventDate);
    }
    
    /**
     * Returns the ID of a one-time order, where to add an event.
     * Returns null if no applicable order
     *
     * @return order ID of the current order
     */
    public Integer getCurrent() {
    	return getCurrent(false);
    }
    
    public Integer getCurrent(boolean isMediation) {

        // find in the cache
        String cacheKey = userId.toString() + Util.truncateDate(eventDate) + (isMediation?1:0);
        Integer retValue = (Integer) cache.getFromCache(cacheKey, cacheModel);
        LOG.debug("Retrieved from cache '" + cacheKey + "', order id: " + retValue);

        // a hit is only a hit if the order is still active
        if (retValue != null && Constants.ORDER_STATUS_ACTIVE.equals(new OrderDAS().find(retValue).getStatusId())) {
            LOG.debug("Cache hit for " + retValue);
            return retValue;
        }

        Integer subscriptionId = user.getEntity().getCustomer().getCurrentOrderId();
        Integer entityId = null;
        Integer currencyId = null;
        if (subscriptionId == null) {
            return null;
        }

        // find main subscription order for user
        Integer mainOrder;
        try {
            order = new OrderBL(subscriptionId);
            entityId = order.getEntity().getBaseUserByUserId().getCompany().getId();
            currencyId = order.getEntity().getCurrencyId();
            mainOrder = order.getEntity().getId();
        } catch (Exception e) {
            throw new SessionInternalError("Error looking for main subscription order",
                    CurrentOrder.class, e);
        }

        // loop through future periods until we find a usable current order
        boolean orderFound = false;
        mainOrder = order.getEntity().getId();
            order.set(mainOrder);
            final Date newOrderDate = calculateDate(0);
            LOG.debug("Calculated one timer date: " + newOrderDate );

            if (newOrderDate == null) {
                // this is an error, there isn't a good date give the event date and
                // the main subscription order
                LOG.error("Could not calculate order date for event. Event date is before the order active since date.");
                return null;
            }

            try {
                List<OrderDTO> rows = new OrderDAS().findOneTimersByDate(userId, newOrderDate);
                LOG.debug("Found " + rows.size() + " one-time orders for new order date: " + newOrderDate);
                for (OrderDTO oneTime : rows) {
                    order.set(oneTime.getId());
                    if (order.getEntity().getStatusId().equals(Constants.ORDER_STATUS_FINISHED)) {
                        LOG.debug("Found one timer " + oneTime.getId() + " but status is finished");
                    } else {
                    	if(isMediation && oneTime.getOrderType()==1){
                    		orderFound = true;
                    	}
                    	
                    	if(!isMediation && oneTime.getOrderType()!=1){
                    		 orderFound = true;
                    	}
                    	
                    	if(orderFound){
                            LOG.debug("Found existing one-time order");
                            break;
                    	}
                    }
                }
            } catch (Exception e) {
                throw new SessionInternalError("Error looking for one time orders", CurrentOrder.class, e);
            }

            if (!orderFound) {
                // there aren't any one-time mediation orders for this date at all, create one
                create(newOrderDate, currencyId, entityId, isMediation?1:0);
                LOG.debug("Created new one-time order");
            }

        // the result is in 'order'
        retValue = order.getEntity().getId();

        LOG.debug("Caching order " + retValue + " with key '" + cacheKey + "'");
        cache.putInCache(cacheKey, cacheModel, retValue);

        LOG.debug("Returning " + retValue);
        return retValue;
    }
    
    /**
     * Assumes that the order has been set with the main subscription order
     * @param futurePeriods date for N periods into the future
     * @return calculated period date for N future periods
     */
    private Date calculateDate(int futurePeriods) {
        GregorianCalendar cal = new GregorianCalendar();

        // start from the active since if it is there, otherwise the create time
        final Date startingTime = order.getEntity().getActiveSince() == null
                                  ? order.getEntity().getCreateDate()
                                  : order.getEntity().getActiveSince();

        // calculate the event date with the added future periods
        Date actualEventDate = eventDate;
        cal.setTime(actualEventDate);
        for (int f = 0; f < futurePeriods; f++) {
            cal.add(MapPeriodToCalendar.map(order.getEntity().getOrderPeriod().getPeriodUnit().getId()), 
                                            order.getEntity().getOrderPeriod().getValue());
        }
        actualEventDate = cal.getTime();

        // is the starting date beyond the time frame of the main order?
        if (order.getEntity().getActiveSince() != null && actualEventDate.before(order.getEntity().getActiveSince())) {
            LOG.error("The event for date " + actualEventDate
                    + " can not be assigned for order " + order.getEntity().getId()
                    + " active since " + order.getEntity().getActiveSince());
            return null;
        }
        
        Date newOrderDate = startingTime;
        cal.setTime(startingTime);
        while (cal.getTime().before(actualEventDate)) {
            newOrderDate = cal.getTime();
            cal.add(MapPeriodToCalendar.map(order.getEntity().getOrderPeriod().getPeriodUnit().getId()), 
                                            order.getEntity().getOrderPeriod().getValue());
        }

        // is the found date beyond the time frame of the main order?
        if (order.getEntity().getActiveUntil() != null && newOrderDate.after(order.getEntity().getActiveUntil())) {
            LOG.error("The event for date " + actualEventDate
                    + " can not be assigned for order " + order.getEntity().getId()
                    + " active until " + order.getEntity().getActiveUntil());
            return null;
        }

        return newOrderDate;
    }

    /**
     * Creates a new one-time order for the given active since date.
     * @param activeSince active since date
     * @param currencyId currency of order
     * @param entityId company id of order
     * @return new order
     */
    public Integer create(Date activeSince, Integer currencyId, Integer entityId) {
    	return create(activeSince, currencyId, entityId, CommonConstants.ORDER_TYPE_DEFAULT);
    }

    public Integer create(Date activeSince, Integer currencyId, Integer entityId, Integer orderType) {
        OrderDTO currentOrder = new OrderDTO();
        currentOrder.setCurrency(new CurrencyDTO(currencyId));

        // notes
        try {
            EntityBL entity = new EntityBL(entityId);
            ResourceBundle bundle = ResourceBundle.getBundle("entityNotifications", entity.getLocale());
            currentOrder.setNotes(bundle.getString("order.current.notes"));
        } catch (Exception e) {
            throw new SessionInternalError("Error setting the new order notes", CurrentOrder.class, e);
        } 

        currentOrder.setActiveSince(activeSince);
        
        // create the order
        if (order == null) {
            order = new OrderBL();
        }

        order.set(currentOrder);
        order.addRelationships(userId, Constants.ORDER_PERIOD_ONCE, currencyId);
        if(orderType != null){
        	currentOrder.setOrderType(orderType);
        }

        return order.create(entityId, null, currentOrder);
    }
}
