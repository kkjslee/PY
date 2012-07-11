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

package com.infosense.ibilling.server.user.balance;

import com.infosense.ibilling.common.Constants;
import com.infosense.ibilling.server.item.CurrencyBL;
import com.infosense.ibilling.server.order.db.OrderDAS;
import com.infosense.ibilling.server.order.event.NewOrderEvent;
import com.infosense.ibilling.server.order.event.NewQuantityEvent;
import com.infosense.ibilling.server.order.event.OrderAddedOnInvoiceEvent;
import com.infosense.ibilling.server.order.event.OrderDeletedEvent;
import com.infosense.ibilling.server.payment.event.PaymentDeletedEvent;
import com.infosense.ibilling.server.payment.event.PaymentSuccessfulEvent;
import com.infosense.ibilling.server.pluggableTask.PluggableTask;
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskException;
import com.infosense.ibilling.server.system.event.Event;
import com.infosense.ibilling.server.system.event.EventManager;
import com.infosense.ibilling.server.system.event.task.IInternalEventsTask;
import com.infosense.ibilling.server.user.db.CustomerDTO;
import com.infosense.ibilling.server.user.db.UserDAS;
import com.infosense.ibilling.server.user.db.UserDTO;
import com.infosense.ibilling.server.user.event.DynamicBalanceChangeEvent;
import com.infosense.ibilling.server.util.audit.EventLogger;

import java.math.BigDecimal;
import org.apache.log4j.Logger;

/**
 *
 * @author emilc
 */
public class DynamicBalanceManagerTask extends PluggableTask implements IInternalEventsTask {
    private static final Logger LOG = Logger.getLogger(DynamicBalanceManagerTask.class);

    @SuppressWarnings("unchecked")
    private static final Class<Event> events[] = new Class[] { 
        PaymentSuccessfulEvent.class,
        OrderDeletedEvent.class,
        NewOrderEvent.class,
        PaymentDeletedEvent.class,
        OrderAddedOnInvoiceEvent.class,
        NewQuantityEvent.class
    };

    public Class<Event>[] getSubscribedEvents() {
        return events;
    }

    public void process(Event event) throws PluggableTaskException {
        updateDynamicBalance(event.getEntityId(), determineUserId(event), determineAmount(event));
    }

    private BigDecimal determineAmount(Event event) {
        if (event instanceof PaymentSuccessfulEvent) {
            PaymentSuccessfulEvent payment = (PaymentSuccessfulEvent) event;
            BigDecimal retVal= payment.getPayment().getAmount();
            return convertAmountToUsersCurrency(retVal, payment.getPayment().getCurrency().getId(),
                    payment.getPayment().getUserId(), payment.getEntityId());

        } else if (event instanceof OrderDeletedEvent) {
            OrderDeletedEvent order = (OrderDeletedEvent) event;
            if (order.getOrder().getOrderPeriod().getId() ==  com.infosense.ibilling.server.util.Constants.ORDER_PERIOD_ONCE) {
                return order.getOrder().getTotal();
            } else {
                return BigDecimal.ZERO;
            }

        } else if (event instanceof NewOrderEvent) {
            NewOrderEvent order = (NewOrderEvent) event;
            if (order.getOrder().getOrderPeriod().getId() ==  com.infosense.ibilling.server.util.Constants.ORDER_PERIOD_ONCE) {
                return order.getOrder().getTotal().multiply(new BigDecimal(-1));
            } else {
                return BigDecimal.ZERO;
            }

        } else if (event instanceof PaymentDeletedEvent) {
            PaymentDeletedEvent payment = (PaymentDeletedEvent) event;
            BigDecimal retVal= payment.getPayment().getAmount().negate();
            return convertAmountToUsersCurrency(retVal, payment.getPayment().getCurrency().getId(),
                    payment.getPayment().getBaseUser().getId(), payment.getEntityId());


        } else if (event instanceof OrderAddedOnInvoiceEvent) {

            OrderAddedOnInvoiceEvent orderOnInvoiceEvent = (OrderAddedOnInvoiceEvent) event;
            OrderAddedOnInvoiceEvent order = (OrderAddedOnInvoiceEvent) event;
            if (order.getOrder().getOrderPeriod().getId() !=  com.infosense.ibilling.server.util.Constants.ORDER_PERIOD_ONCE) {
                return orderOnInvoiceEvent.getTotalInvoiced().multiply(new BigDecimal(-1));
            } else {
                return BigDecimal.ZERO;
            }

        } else if (event instanceof NewQuantityEvent) {
            NewQuantityEvent nq = (NewQuantityEvent) event;

            if (new OrderDAS().find(nq.getOrderId()).getOrderPeriod().getId() ==
                    com.infosense.ibilling.server.util.Constants.ORDER_PERIOD_ONCE) {
                BigDecimal newTotal, oldTotal;
                // new order line, or old one updated?
                if (nq.getNewOrderLine() == null) {
                    // new
                    oldTotal = BigDecimal.ZERO;
                    newTotal = nq.getOrderLine().getAmount();
                    if (nq.getNewQuantity().compareTo(BigDecimal.ZERO) == 0) {
                        // it is a delete
                        newTotal = newTotal.multiply(new BigDecimal(-1));
                    }
                } else {
                    // old
                    oldTotal = nq.getOrderLine().getAmount();
                    newTotal = nq.getNewOrderLine().getAmount();
                }
                return newTotal.subtract(oldTotal).multiply(new BigDecimal(-1));
                
            } else {
                return BigDecimal.ZERO;
            }
        }  else {
            LOG.error("Can not determine amount for event " + event);
            return null;
        }
    }

    private BigDecimal convertAmountToUsersCurrency(BigDecimal amount, Integer amountCurrencyId, Integer userId, Integer entityId) {
        //no need to convert zeros
        if ( null != amount && ! (amount.compareTo(BigDecimal.ZERO) == 0) ) {
            //non-zero return value, must be converted if
            Integer userCurrencyId= new UserDAS().find(userId).getCurrencyId();
            if ( amountCurrencyId != userCurrencyId ) {
                //convert to user's currency
                LOG.debug("Converting amount to User's specific currency");
                amount= new CurrencyBL().convert(amountCurrencyId, userCurrencyId, amount, entityId);
            }
        }
        return amount;
    }
    
    private int determineUserId(Event event) {
        if (event instanceof PaymentSuccessfulEvent) {
            PaymentSuccessfulEvent payment = (PaymentSuccessfulEvent) event;
            return payment.getPayment().getUserId();
        } else if (event instanceof OrderDeletedEvent) {
            OrderDeletedEvent order = (OrderDeletedEvent) event;
            return order.getOrder().getBaseUserByUserId().getId();
        } else if (event instanceof NewOrderEvent) {
            NewOrderEvent order = (NewOrderEvent) event;
            return order.getOrder().getBaseUserByUserId().getId();
        } else if (event instanceof PaymentDeletedEvent) {
            PaymentDeletedEvent payment = (PaymentDeletedEvent) event;
            return payment.getPayment().getBaseUser().getId();
        } else if (event instanceof OrderAddedOnInvoiceEvent) {
            OrderAddedOnInvoiceEvent order = (OrderAddedOnInvoiceEvent) event;
            return order.getOrder().getBaseUserByUserId().getId();
        } else if (event instanceof NewQuantityEvent) {
            NewQuantityEvent nq = (NewQuantityEvent) event;
            return new OrderDAS().find(nq.getOrderId()).getBaseUserByUserId().getId();
        }  else {
            LOG.error("Can not determine user for event " + event);
            return 0;
        }
    }

    private void updateDynamicBalance(Integer entityId, Integer userId, BigDecimal amount) {
        UserDTO user = new UserDAS().find(userId);
        CustomerDTO customer = user.getCustomer();

        // get the parent customer that pays, if it exists
        if (customer != null) {
            while (customer.getParent() != null
                    && (customer.getInvoiceChild() == null || customer.getInvoiceChild() == 0)) {                
                customer =  customer.getParent(); // go up one level
            }
        }

        // fail fast condition, no dynamic balance or ammount is zero
        if (customer == null
                || customer.getBalanceType() == Constants.BALANCE_NO_DYNAMIC
                || amount.compareTo(BigDecimal.ZERO) == 0) {
            LOG.debug("Nothing to update");
            return;
        }

        LOG.debug("Updating dynamic balance for " + amount);

        BigDecimal balance = (customer.getDynamicBalance() == null ? BigDecimal.ZERO : customer.getDynamicBalance());

        // register the event, before the balance is changed
        new EventLogger().auditBySystem(entityId,
                                        customer.getBaseUser().getId(),
                                        com.infosense.ibilling.server.util.Constants.TABLE_CUSTOMER,
                                        user.getCustomer().getId(),
                                        EventLogger.MODULE_USER_MAINTENANCE,
                                        EventLogger.DYNAMIC_BALANCE_CHANGE,
                                        null,
                                        balance.toString(),
                                        null);

        if (customer.getBalanceType() == Constants.BALANCE_CREDIT_LIMIT) {
            customer.setDynamicBalance(balance.subtract(amount));

        } else if (customer.getBalanceType() == Constants.BALANCE_PRE_PAID) {
            customer.setDynamicBalance(balance.add(amount));

        } else {
            customer.setDynamicBalance(balance);
        }

        if (!balance.equals(customer.getDynamicBalance())) {
            DynamicBalanceChangeEvent event = new DynamicBalanceChangeEvent(user.getEntity().getId(),
                                                                            user.getUserId(),
                                                                            customer.getDynamicBalance(), // new
                                                                            balance);                     // old
            EventManager.process(event);
        }
    }
}
