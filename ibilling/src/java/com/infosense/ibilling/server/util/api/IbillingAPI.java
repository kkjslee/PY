package com.infosense.ibilling.server.util.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infosense.ibilling.common.SessionInternalError;
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

/**
 * 
 * The API interface.
 *
 */
public interface IbillingAPI {
	
	/**
	 * Get the invoice entity by invoice id.
	 */
    public InvoiceWS getInvoiceWS(Integer invoiceId) throws IbillingAPIException;

    /**
     * Get the latest invoice by user id.
     */
    public InvoiceWS getLatestInvoice(Integer userId) throws IbillingAPIException;

    /**
     * Get the latest 'n' invoices by user id.
     * 
     * @param userId the identify of user
     * @param number the maximum number of result size
     * @throws IbillingAPIException
     */
    public Integer[] getLastInvoices(Integer userId, Integer number) throws IbillingAPIException;

    /**
     * Get the invoices within the given date range.
     * @param since The start date for query
     * @param until The end date for query
     * @throws IbillingAPIException
     */
    public Integer[] getInvoicesByDate(String since, String until) throws IbillingAPIException;
    
    /**
     * Generate printable invoice by invoice id.
     * @return the byte stream in PDF format
     */
    public byte[] getPaperInvoicePDF(Integer invoiceId) throws IbillingAPIException;

    /**
     * Get the automatic payment type of user.<br>
     * 1 - Credit card<br>
     * 2 - Ach<br>
     * 3 - Cheque<br>
     * 0 - Unknown<br>
     * @see com.infosense.ibilling.server.ws.UserWS#automaticPaymentType
     * @throws IbillingAPIException
     */
    public Integer getAutoPaymentType(Integer userId) throws IbillingAPIException;
    
    /**
     * Set the automatic payment type of user.<br>
     * 1 - Credit card<br>
     * 2 - Ach<br>
     * 3 - Cheque<br>
     * 0 - Unknown<br>
     * @see com.infosense.ibilling.server.ws.UserWS#automaticPaymentType
     * @throws IbillingAPIException
     */
    public void setAutoPaymentType(Integer userId, Integer autoPaymentType, boolean use) throws IbillingAPIException;

    /**
     * Returns the invoices for the user within the given date range.
     * @param userId
     * @param since the begin of date range
     * @param until the end of date range 
     * @return the array of invoice id.
     * @throws IbillingAPIException
     */
    public Integer[] getUserInvoicesByDate(Integer userId, String since, String until) throws IbillingAPIException;

    /**
     * Generates invoices for orders not yet invoiced for this user.<br>
     * @param userId the user id
     * @param onlyRecurring only allow recurring orders to generate invoices
     * @return the ids of the invoices generated
     * @throws IbillingAPIException
     */
    public Integer[] createInvoice(Integer userId, boolean onlyRecurring) throws IbillingAPIException;

    /**
     * Generates a new invoice for an order, or adds the order to an existing invoice.
     * @param orderId order id to generate an invoice for
     * @param invoiceId optional invoice id to add the order to. If null, a new invoice will be created.
     * @return id of generated invoice, null if no invoice generated.
     * @throws SessionInternalError if user id or order id is null.
     */
    public Integer createInvoiceFromOrder(Integer orderId, Integer invoiceId) throws IbillingAPIException;

    /**
     * Creates a new user. The user to be created has to be of the roles customer
     * or partner.
     * The user name has to be unique, otherwise the creating won't go through. If
     * that is the case, the return value will be null.
     * @param newUser
     * The user object with all the information of the new user. If contact or
     * credit card information are present, they will be included in the creation
     * although they are not mandatory.
     * @return The id of the new user, or null if non was created
     */
    public Integer createUser(UserWS newUser) throws IbillingAPIException;

    /**
     * Delete an user
     * @param userId
     * The id of the user to delete 
     * @throws IbillingAPIException
     */
    public void deleteUser(Integer userId) throws IbillingAPIException;

    /**
     * Deletes an invoice
     * @param invoiceId
     * The id of the invoice to delete
     */
    public void deleteInvoice(Integer invoiceId) throws IbillingAPIException;

    /**
     * Update the contact information of an user
     * @param userId
     * The id of the user to update
     * @param typeId
     * The id of contact type
     * @param contact
     * The contact object with all information of the contact.
     * @throws IbillingAPIException
     */
    public void updateUserContact(Integer userId, Integer typeId, ContactWS contact) throws IbillingAPIException;

    /**
     * Update the information of an user
     */
    public void updateUser(UserWS user) throws IbillingAPIException;

    /**
     * Retrieves a user with its contact and credit card information.
     * @param userId
     * The id of the user to be returned
     */
    public UserWS getUserWS(Integer userId) throws IbillingAPIException;

    /**
     * Retrieves all the contacts of a user
     * @param userId
     * The id of the user to be returned
     */
    public ContactWS[] getUserContactsWS(Integer userId)
            throws IbillingAPIException;

    /**
     * Retrieves the user id for the given username
     */
    public Integer getUserId(String username) throws IbillingAPIException;

    /**
     * Retrieves an array of users in the required status
     */
    public Integer[] getUsersInStatus(Integer statusId) throws IbillingAPIException;

    /**
     * Retrieves an array of users not in the required status
     */
    public Integer[] getUsersNotInStatus(Integer statusId) throws IbillingAPIException;
    
    /**
     * Retrieves an array of users with the credit card number
     */
    public Integer[] getUsersByCreditCard(String number)
        throws IbillingAPIException;

    /**
     * Retrieves an array of users by custom field
     */
    public Integer[] getUsersByCustomField(Integer typeId, String value)
            throws IbillingAPIException;

    /**
     * Creates a user, then an order for it, an invoice out the order
     * and tries the invoice to be paid by an online payment
     */
    public CreateResponseWS create(UserWS user, OrderWS order)
            throws IbillingAPIException;

    /**
     * Pays given invoice, using the first credit card available for invoice'd
     * user.
     *
     * @return <code>null</code> if invoice has not positive balance, or if
     *         user does not have credit card
     * @return resulting authorization record. The payment itself can be found by
     * calling getLatestPayment
     */
    public PaymentAuthorizationDTOEx payInvoice(Integer invoiceId)
            throws IbillingAPIException;

    /**
     * Updates a users stored credit card to the given details. If the given credit card is
     * null then the user's existing credit card will be deleted.
     *
     * @param userId user to update
     * @param creditCard credit card details
     * @throws SessionInternalError
     */
    public void updateCreditCard(Integer userId, CreditCardDTO creditCard)
            throws IbillingAPIException;
    
    /**
     * Updates a users stored ACH details.
     *
     * @param userId user to update
     * @param ach ACH details
     * @throws SessionInternalError
     */
    public void updateAch(Integer userId, AchDTO ach) throws IbillingAPIException;

    /**
     * Retrieves the payment authorization information.
     * @return the information of the payment authorization, or NULL if the
     * user does not have a credit card
     */
    public PaymentAuthorizationDTOEx createOrderPreAuthorize(OrderWS order)
            throws IbillingAPIException;

    /**
     * Create the given order in iBilling.
     * @param order
     * The pojo object includes all information of order 
     * @return the id of generated order
     * @throws IbillingAPIException
     */
    public Integer createOrder(OrderWS order) throws IbillingAPIException;

    /**
     * Creates the given Order in iBilling, generates an Invoice for the same.
     * Returns the generated Invoice ID
     */
    public Integer createOrderAndInvoice(OrderWS order) throws IbillingAPIException;

    /**
     * Update order in iBilling
     * @param order
     * The pojo object includes all information of order 
     * @throws IbillingAPIException
     */
    public void updateOrder(OrderWS order) throws IbillingAPIException;

    /**
     * Retrieves order by given id
     * @param orderId
     * @return the information of the order, or NULL if the id does not exist
     * @throws IbillingAPIException
     */
    public OrderWS getOrder(Integer orderId) throws IbillingAPIException;

    /**
     * Retrieves the orders of user and given period
     * @param userId
     * @param periodId
     * @return the ids of orders, or NULL if the user/period are null.
     * @throws IbillingAPIException
     */
    public Integer[] getOrderByPeriod(Integer userId, Integer periodId)
            throws IbillingAPIException;

    /**
     * Retrieves order by given id
     * @param orderLineId
     * @return the information of the order line, or NULL if the id does not exist
     * @throws IbillingAPIException
     */
    public OrderLineWS getOrderLine(Integer orderLineId)
            throws IbillingAPIException;

    /**
     * Update order line
     * @param line
     * The pojo object includes all information of order line
     * @throws IbillingAPIException
     */
    public void updateOrderLine(OrderLineWS line) throws IbillingAPIException;

    /**
     * Get the latest invoice by user id.
     * @param userId
     * @return
     * The pojo object includes all information of order
     * @throws IbillingAPIException
     */
    public OrderWS getLatestOrder(Integer userId) throws IbillingAPIException;

    /**
     * Get the latest 'n' orders by user id.
     * 
     * @param userId the identify of user
     * @param number the maximum number of result size
     * @return the ids of orders
     * @throws IbillingAPIException
     */
    public Integer[] getLastOrders(Integer userId, Integer number)
            throws IbillingAPIException;

    /**
     * Delete the special order
     * @param id
     * @throws IbillingAPIException
     */
    public void deleteOrder(Integer id) throws IbillingAPIException;

    /**
     * Retrieves the current order (order collecting current one-time charges) for the 
     * period of the given date and the given user. 
     * Return null for users with no main subscription order.
     */
    public OrderWS getCurrentOrder(Integer userId, Date date) 
            throws IbillingAPIException;

    /**
     * Updates the uesr's current one-time order for the given date.
     * Returns the updated current order. Throws an exception for
     * users with no main subscription order.
     */
    public OrderWS updateCurrentOrder(Integer userId, OrderLineWS[] lines, 
            PricingField[] fields, Date date, String eventDescription) 
            throws IbillingAPIException;

    /**
     * Enters a payment and applies it to the given invoice. This method DOES NOT process
     * the payment but only creates it as 'Entered'. The entered payment will later be
     * processed by the billing process.
     *
     * Invoice ID is optional. If no invoice ID is given the payment will be applied to
     * the payment user's account according to the configured entity preferences.
     *
     * @param payment payment to apply
     * @param invoiceId invoice id
     * @return created payment id
     * @throws SessionInternalError
     */
    public Integer applyPayment(PaymentWS payment, Integer invoiceId)
            throws IbillingAPIException;

    /**
     * Retrieves payment by given id
     * @return the information of the payment, or NULL if the id does not exist
     * @throws IbillingAPIException
     */
    public PaymentWS getPayment(Integer paymentId) throws IbillingAPIException;

    /**
     * Retrieves latest payment of the given user
     * @return the information of the payment, or NULL if the id does not exist
     * @throws IbillingAPIException
     */
    public PaymentWS getLatestPayment(Integer userId)
            throws IbillingAPIException;

    public Integer[] getLastPayments(Integer userId, Integer number)
            throws IbillingAPIException;

    /**
     * Generate item in the iBilling.
     * @param dto
     * @return the id of generated item
     * @throws IbillingAPIException
     */
    public Integer createItem(ItemDTOEx dto) throws IbillingAPIException;
    
    /**
     * Retrieves an array of items for the caller's entity.
     * @return an array of items from the caller's entity
     */
    public ItemDTOEx[] getAllItems() throws IbillingAPIException;

    /**
     * Implementation of the User Transitions List webservice. This accepts a
     * start and end date as arguments, and produces an array of data containing
     * the user transitions logged in the requested time range.
     * @param from Date indicating the lower limit for the extraction of transition
     * logs. It can be <code>null</code>, in such a case, the extraction will start
     * where the last extraction left off. If no extractions have been done so far and
     * this parameter is null, the function will extract from the oldest transition
     * logged.
     * @param to Date indicatin the upper limit for the extraction of transition logs.
     * It can be <code>null</code>, in which case the extraction will have no upper
     * limit.
     * @return UserTransitionResponseWS[] an array of objects containing the result
     * of the extraction, or <code>null</code> if there is no data thas satisfies
     * the extraction parameters.
     */
    public UserTransitionResponseWS[] getUserTransitions(Date from, Date to)
            throws IbillingAPIException;

    /**
     * @return UserTransitionResponseWS[] an array of objects containing the result
     * of the extraction, or <code>null</code> if there is no data thas satisfies
     * the extraction parameters.
     */
    public UserTransitionResponseWS[] getUserTransitionsAfterId(Integer id)
            throws IbillingAPIException;
  
    /**
     * Retrieves an item object with the given id and price of user's default currency
     * @param itemId
     * @param userId
     * @param fields
     * @return
     * @throws IbillingAPIException
     */
    public ItemDTOEx getItem(Integer itemId, Integer userId, PricingField[] fields)
            throws IbillingAPIException;

    /**
     * Rate order.<br>
     * Process the lines and let the items provide the order line details<br>
     * Set a default cycle starts if needed (obtained from the main subscription order, if it exists)
     * @param order
     * @return
     * @throws IbillingAPIException
     */
    public OrderWS rateOrder(OrderWS order) throws IbillingAPIException;

    /**
     * Rate orders
     * @see com.infosense.ibilling.server.util.api.IbillingAPI#rateOrder(OrderWS)
     * @param orders
     * @return
     * @throws IbillingAPIException
     */
    public OrderWS[] rateOrders(OrderWS orders[]) throws IbillingAPIException;
    
    public void updateItem(ItemDTOEx item) throws IbillingAPIException;
    
    /**
     * Retrieves billing process configuration
     * @see com.infosense.ibilling.server.ws.BillingProcessConfigurationWS
     * @return
     * @throws IbillingAPIException
     */
    public BillingProcessConfigurationWS getBillingProcessConfiguration() throws IbillingAPIException;
    
    /**
     * Generates a billing process configuration
     * @see com.infosense.ibilling.server.ws.BillingProcessConfigurationWS
     * @param ws
     * @return the id of generated configuration
     * @throws IbillingAPIException
     */
    public Integer createUpdateBillingProcessConfiguration(BillingProcessConfigurationWS ws) throws IbillingAPIException;
    
    /**
     * Update the given order, or create it if it doesn't already exist.
     *
     * @param order order to update or create
     * @return order id
     * @throws SessionInternalError
     */
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

    /**
     * Processes a payment and applies it to the given invoice. This method will actively
     * processes the payment using the configured payment plug-in.
     *
     * Payment is optional when an invoice ID is provided. If no payment is given, the payment
     * will be processed using the invoiced user's configured "automatic payment" instrument.
     *
     * Invoice ID is optional. If no invoice ID is given the payment will be applied to the
     * payment user's account according to the configured entity preferences.
     *
     * @param payment payment to process
     * @param invoiceId invoice id
     * @return payment authorization from the payment processor
     */
    public PaymentAuthorizationDTOEx processPayment(PaymentWS payment,Integer invoiceId) throws IbillingAPIException;

    /**
     * Validate purchase
     * @param userId
     * @param itemId
     * @param fields
     * @return
     * @throws IbillingAPIException
     */
    public ValidatePurchaseWS validatePurchase(Integer userId, Integer itemId, PricingField[] fields) throws IbillingAPIException;

    /**
     * Validate purchases
     * @see com.infosense.ibilling.server.util.api.IbillingAPI#validatePurchase(Integer, Integer, PricingField[])
     * @param userId
     * @param itemIds
     * @param fields
     * @return
     * @throws IbillingAPIException
     */
    public ValidatePurchaseWS validateMultiPurchase(Integer userId, Integer[] itemIds, PricingField[][] fields) throws IbillingAPIException;

    /**
     * Create item type.
     * The item category names are unique, or an exception will be thrown.
     * @param itemType
     * @throws IbillingAPIException
     * @return
     * The id of generated category
     */
    public Integer createItemCategory(ItemTypeWS itemType) throws IbillingAPIException;

    /**
     * Update item type.
     * The item category names are unique, or an exception will be thrown.
     * @param itemType
     * @throws IbillingAPIException
     */
    void updateItemCategory(ItemTypeWS itemType) throws IbillingAPIException;

    /**
     * Generates, compiles and saves the rules
     * @param rulesData
     * The input string to parse and validate.
     * @throws IbillingAPIException
     */
    public void generateRules(String rulesData) throws IbillingAPIException;
    
    /**
     * Generates a host item with given cpu and memory
     * @param newPlan
     * The item object with all information
     * @param cpu
     * The number of cpu
     * @param memory
     * The number of memory
     * @return the id of generated item 
     */
    public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory);
	
    /**
     * Generates a host item with given cpu, memory and other additional components
     * @param newPlan
     * The item object with all information
     * @param cpu
     * The number of cpu
     * @param memory
     * The number of memory
     * @param properties
     * A map of other additional components, the key is component name and value is quality of component
     * @return the id of generated item 
     */
	public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory, Map<String, Integer> properties);
	
	public InvoiceWS getLatestInvoiceByOrder(Integer orderId);
}
