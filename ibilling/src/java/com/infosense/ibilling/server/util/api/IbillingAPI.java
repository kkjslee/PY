package com.infosense.ibilling.server.util.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infosense.ibilling.server.entity.AchDTO;
import com.infosense.ibilling.server.entity.CreditCardDTO;
import com.infosense.ibilling.server.item.ItemDTOEx;
import com.infosense.ibilling.server.item.PricingField;
import com.infosense.ibilling.server.payment.PaymentAuthorizationDTOEx;
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

public interface IbillingAPI {
    public InvoiceWS getInvoiceWS(Integer invoiceId)
            throws IbillingAPIException;

    public InvoiceWS getLatestInvoice(Integer userId)
            throws IbillingAPIException;

    public Integer[] getLastInvoices(Integer userId, Integer number)
            throws IbillingAPIException;

    public Integer[] getInvoicesByDate(String since, String until)
            throws IbillingAPIException;
    
    public byte[] getPaperInvoicePDF(Integer invoiceId) throws IbillingAPIException;

    public Integer getAutoPaymentType(Integer userId) throws IbillingAPIException;
    
    public void setAutoPaymentType(Integer userId, Integer autoPaymentType, boolean use)
            throws IbillingAPIException;

    public Integer[] getUserInvoicesByDate(Integer userId, String since, 
            String until) throws IbillingAPIException;

    public Integer[] createInvoice(Integer userId, boolean onlyRecurring)
            throws IbillingAPIException;

    public Integer createInvoiceFromOrder(Integer orderId, Integer invoiceId) throws IbillingAPIException;

    public Integer createUser(UserWS newUser) throws IbillingAPIException;

    public void deleteUser(Integer userId) throws IbillingAPIException;

    public void deleteInvoice(Integer invoiceId) throws IbillingAPIException;

    public void updateUserContact(Integer userId, Integer typeId,
            ContactWS contact) throws IbillingAPIException;

    public void updateUser(UserWS user) throws IbillingAPIException;

    public UserWS getUserWS(Integer userId) throws IbillingAPIException;

    public ContactWS[] getUserContactsWS(Integer userId)
            throws IbillingAPIException;

    public Integer getUserId(String username) throws IbillingAPIException;

    public Integer[] getUsersInStatus(Integer statusId)
            throws IbillingAPIException;

    public Integer[] getUsersByCreditCard(String number)
        throws IbillingAPIException;

    public Integer[] getUsersNotInStatus(Integer statusId)
            throws IbillingAPIException;

    public Integer[] getUsersByCustomField(Integer typeId, String value)
            throws IbillingAPIException;

    public CreateResponseWS create(UserWS user, OrderWS order)
            throws IbillingAPIException;

    public PaymentAuthorizationDTOEx payInvoice(Integer invoiceId)
            throws IbillingAPIException;

    public void updateCreditCard(Integer userId, CreditCardDTO creditCard)
            throws IbillingAPIException;
    
    public void updateAch(Integer userId, AchDTO ach)
            throws IbillingAPIException;

    public PaymentAuthorizationDTOEx createOrderPreAuthorize(OrderWS order)
            throws IbillingAPIException;

    public Integer createOrder(OrderWS order) throws IbillingAPIException;

    public Integer createOrderAndInvoice(OrderWS order) throws IbillingAPIException;

    public void updateOrder(OrderWS order) throws IbillingAPIException;

    public OrderWS getOrder(Integer orderId) throws IbillingAPIException;

    public Integer[] getOrderByPeriod(Integer userId, Integer periodId)
            throws IbillingAPIException;

    public OrderLineWS getOrderLine(Integer orderLineId)
            throws IbillingAPIException;

    public void updateOrderLine(OrderLineWS line) throws IbillingAPIException;

    public OrderWS getLatestOrder(Integer userId) throws IbillingAPIException;

    public Integer[] getLastOrders(Integer userId, Integer number)
            throws IbillingAPIException;

    public void deleteOrder(Integer id) throws IbillingAPIException;

    public OrderWS getCurrentOrder(Integer userId, Date date) 
            throws IbillingAPIException;

    public OrderWS updateCurrentOrder(Integer userId, OrderLineWS[] lines, 
            PricingField[] fields, Date date, String eventDescription) 
            throws IbillingAPIException;

    public Integer applyPayment(PaymentWS payment, Integer invoiceId)
            throws IbillingAPIException;

    public PaymentWS getPayment(Integer paymentId) throws IbillingAPIException;

    public PaymentWS getLatestPayment(Integer userId)
            throws IbillingAPIException;

    public Integer[] getLastPayments(Integer userId, Integer number)
            throws IbillingAPIException;

    public Integer createItem(ItemDTOEx dto) throws IbillingAPIException;
    
    public ItemDTOEx[] getAllItems() throws IbillingAPIException;

    public UserTransitionResponseWS[] getUserTransitions(Date from, Date to)
            throws IbillingAPIException;

    public UserTransitionResponseWS[] getUserTransitionsAfterId(Integer id)
            throws IbillingAPIException;
  
    public ItemDTOEx getItem(Integer itemId, Integer userId, PricingField[] fields)
            throws IbillingAPIException;

    public OrderWS rateOrder(OrderWS order) throws IbillingAPIException;

    public OrderWS[] rateOrders(OrderWS orders[]) throws IbillingAPIException;
    
    public void updateItem(ItemDTOEx item) throws IbillingAPIException;
    
    // "byItemType" routines:
    
    public BillingProcessConfigurationWS getBillingProcessConfiguration() throws IbillingAPIException;
    public Integer createUpdateBillingProcessConfiguration(BillingProcessConfigurationWS ws) throws IbillingAPIException;
    public Integer createUpdateOrder(OrderWS order) throws IbillingAPIException;
    public boolean triggerBilling(Date runDate);
    public void triggerAgeing(Date runDate);
    public BillingProcessWS getBillingProcess(Integer processId);
    
    public Integer getLastBillingProcess() throws IbillingAPIException;
    
    public List<OrderProcessWS> getOrderProcesses(Integer orderId);
    public List<OrderProcessWS> getOrderProcessesByInvoice(Integer invoiceId);

    public BillingProcessWS getReviewBillingProcess();
    
    public BillingProcessConfigurationWS setReviewApproval(Boolean flag) throws IbillingAPIException;
    
    public OrderWS getLatestOrderByItemType(Integer userId, Integer itemTypeId) throws IbillingAPIException;
    
    public Integer[] getAllInvoices(Integer userId);
    
    public List<Integer> getBillingProcessGeneratedInvoices(Integer processId);
    
    public InvoiceWS getLatestInvoiceByItemType(Integer userId, Integer itemTypeId) throws IbillingAPIException;

    public Integer[] getLastInvoicesByItemType(Integer userId, Integer itemTypeId, Integer number) throws IbillingAPIException;

    public Integer[] getLastOrdersByItemType(Integer userId, Integer itemTypeId, Integer number) throws IbillingAPIException;

    public String isUserSubscribedTo(Integer userId, Integer itemId) throws IbillingAPIException;
    
    public Integer[] getUserItemsByCategory(Integer userId, Integer categoryId) throws IbillingAPIException;

    public ItemDTOEx[] getItemByCategory(Integer itemTypeId) throws IbillingAPIException;

    public ItemTypeWS[] getAllItemCategories() throws IbillingAPIException;

    public PaymentAuthorizationDTOEx processPayment(PaymentWS payment,Integer invoiceId) throws IbillingAPIException;

    public ValidatePurchaseWS validatePurchase(Integer userId, Integer itemId, PricingField[] fields) throws IbillingAPIException;

    public ValidatePurchaseWS validateMultiPurchase(Integer userId, Integer[] itemIds, PricingField[][] fields) throws IbillingAPIException;

    public Integer createItemCategory(ItemTypeWS itemType) throws IbillingAPIException;

    void updateItemCategory(ItemTypeWS itemType) throws IbillingAPIException;

    public void generateRules(String rulesData) throws IbillingAPIException;
    
    public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory);
	
	public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory, Map<String, Integer> properties);
}
