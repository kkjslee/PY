package com.infosense.ibilling.server.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.infosense.ibilling.common.SessionInternalError;
import com.infosense.ibilling.server.entity.AchDTO;
import com.infosense.ibilling.server.item.ItemDTOEx;
import com.infosense.ibilling.server.notification.MessageDTO;
import com.infosense.ibilling.server.payment.PaymentAuthorizationDTOEx;
import com.infosense.ibilling.server.ws.AgeingWS;
import com.infosense.ibilling.server.ws.BillingProcessConfigurationWS;
import com.infosense.ibilling.server.ws.BillingProcessWS;
import com.infosense.ibilling.server.ws.CompanyWS;
import com.infosense.ibilling.server.ws.ContactFieldTypeWS;
import com.infosense.ibilling.server.ws.ContactTypeWS;
import com.infosense.ibilling.server.ws.ContactWS;
import com.infosense.ibilling.server.ws.CreateResponseWS;
import com.infosense.ibilling.server.ws.CurrencyWS;
import com.infosense.ibilling.server.ws.InvoiceWS;
import com.infosense.ibilling.server.ws.ItemTypeWS;
import com.infosense.ibilling.server.ws.MediationConfigurationWS;
import com.infosense.ibilling.server.ws.MediationProcessWS;
import com.infosense.ibilling.server.ws.MediationRecordLineWS;
import com.infosense.ibilling.server.ws.MediationRecordWS;
import com.infosense.ibilling.server.ws.OrderLineWS;
import com.infosense.ibilling.server.ws.OrderPeriodWS;
import com.infosense.ibilling.server.ws.OrderProcessWS;
import com.infosense.ibilling.server.ws.OrderWS;
import com.infosense.ibilling.server.ws.PartnerWS;
import com.infosense.ibilling.server.ws.PaymentWS;
import com.infosense.ibilling.server.ws.PluggableTaskWS;
import com.infosense.ibilling.server.ws.PreferenceWS;
import com.infosense.ibilling.server.ws.RecordCountWS;
import com.infosense.ibilling.server.ws.UserTransitionResponseWS;
import com.infosense.ibilling.server.ws.UserWS;
import com.infosense.ibilling.server.ws.ValidatePurchaseWS;

/**
 * Web service bean interface. 
 * {@see com.infosense.ibilling.server.util.WebServicesSessionSpringBean} for documentation.
 */
@WebService
public interface IWebServicesSessionBean {

    public Integer getCallerId();
    public Integer getCallerCompanyId();
    public Integer getCallerLanguageId();
    public Integer getCallerCurrencyId();

    /*
        Users
     */

    public UserWS getUserWS(Integer userId) throws SessionInternalError;
    public Integer createUser(UserWS newUser) throws SessionInternalError;
    public void updateUser(UserWS user) throws SessionInternalError;
    public void deleteUser(Integer userId) throws SessionInternalError;

    public ContactWS[] getUserContactsWS(Integer userId) throws SessionInternalError;
    public void updateUserContact(Integer userId, Integer typeId, ContactWS contact) throws SessionInternalError;

    public ContactTypeWS getContactTypeWS(Integer contactTypeId) throws SessionInternalError;
    public Integer createContactTypeWS(ContactTypeWS contactType) throws SessionInternalError;

    public void updateCreditCard(Integer userId, com.infosense.ibilling.server.entity.CreditCardDTO creditCard) throws SessionInternalError;
    public void deleteCreditCard(Integer userId);
    public void updateAch(Integer userId, AchDTO ach) throws SessionInternalError;
    public void deleteAch(Integer userId);

    public void setAuthPaymentType(Integer userId, Integer autoPaymentType, boolean use) throws SessionInternalError;
    public Integer getAuthPaymentType(Integer userId) throws SessionInternalError;

    public Integer[] getUsersByStatus(Integer statusId, boolean in) throws SessionInternalError;
    public Integer[] getUsersInStatus(Integer statusId) throws SessionInternalError;
    public Integer[] getUsersNotInStatus(Integer statusId) throws SessionInternalError;
    public Integer[] getUsersByCustomField(Integer typeId, String value) throws SessionInternalError;
    public Integer[] getUsersByCreditCard(String number) throws SessionInternalError;

    public Integer getUserId(String username) throws SessionInternalError;

    public void saveCustomContactFields(ContactFieldTypeWS[] fields) throws SessionInternalError;

    public void processPartnerPayouts(Date runDate);
    public PartnerWS getPartner(Integer partnerId) throws SessionInternalError;

    public UserTransitionResponseWS[] getUserTransitions(Date from, Date to) throws SessionInternalError;
    public UserTransitionResponseWS[] getUserTransitionsAfterId(Integer id) throws SessionInternalError;

    public CreateResponseWS create(UserWS user, OrderWS order) throws SessionInternalError;


    /*
        Items
     */

    public ItemDTOEx getItem(Integer itemId, Integer userId, String pricing);
    public ItemDTOEx[] getAllItems() throws SessionInternalError;
    public Integer createItem(ItemDTOEx item) throws SessionInternalError;
    public void updateItem(ItemDTOEx item);
    public void deleteItem(Integer itemId);

    public ItemDTOEx[] getItemByCategory(Integer itemTypeId);
    public Integer[] getUserItemsByCategory(Integer userId, Integer categoryId);

    public ItemTypeWS[] getAllItemCategories();
    public Integer createItemCategory(ItemTypeWS itemType) throws SessionInternalError;
    public void updateItemCategory(ItemTypeWS itemType) throws SessionInternalError;
    public void deleteItemCategory(Integer itemCategoryId);
    
    public String isUserSubscribedTo(Integer userId, Integer itemId);

    public InvoiceWS getLatestInvoiceByItemType(Integer userId, Integer itemTypeId) throws SessionInternalError;
    public Integer[] getLastInvoicesByItemType(Integer userId, Integer itemTypeId, Integer number) throws SessionInternalError;

    public OrderWS getLatestOrderByItemType(Integer userId, Integer itemTypeId) throws SessionInternalError;
    public Integer[] getLastOrdersByItemType(Integer userId, Integer itemTypeId, Integer number) throws SessionInternalError;

    public ValidatePurchaseWS validatePurchase(Integer userId, Integer itemId, String fields);
    public ValidatePurchaseWS validateMultiPurchase(Integer userId, Integer[] itemId, String[] fields);


    /*
        Orders
     */

    public OrderWS getOrder(Integer orderId) throws SessionInternalError;
    public Integer createOrder(OrderWS order) throws SessionInternalError;
    public void updateOrder(OrderWS order) throws SessionInternalError;
    public Integer createUpdateOrder(OrderWS order) throws SessionInternalError;
    public void deleteOrder(Integer id) throws SessionInternalError;

    public Integer createOrderAndInvoice(OrderWS order) throws SessionInternalError;

    public OrderWS getCurrentOrder(Integer userId, Date date) throws SessionInternalError;
    public OrderWS updateCurrentOrder(Integer userId, OrderLineWS[] lines, String pricing, Date date, String eventDescription) throws SessionInternalError;

    public OrderWS[] getUserSubscriptions(Integer userId) throws SessionInternalError;
    
    public OrderLineWS getOrderLine(Integer orderLineId) throws SessionInternalError;
    public void updateOrderLine(OrderLineWS line) throws SessionInternalError;

    public Integer[] getOrderByPeriod(Integer userId, Integer periodId) throws SessionInternalError;
    public OrderWS getLatestOrder(Integer userId) throws SessionInternalError;
    public Integer[] getLastOrders(Integer userId, Integer number) throws SessionInternalError;

    public OrderWS rateOrder(OrderWS order) throws SessionInternalError;
    public OrderWS[] rateOrders(OrderWS orders[]) throws SessionInternalError;

    public boolean updateOrderPeriods(OrderPeriodWS[] orderPeriods) throws SessionInternalError;
    public boolean updateOrCreateOrderPeriod(OrderPeriodWS orderPeriod) throws SessionInternalError;
    public boolean deleteOrderPeriod(Integer periodId) throws SessionInternalError;
    
    public PaymentAuthorizationDTOEx createOrderPreAuthorize(OrderWS order) throws SessionInternalError;


    /*
        Invoices
     */

    public InvoiceWS getInvoiceWS(Integer invoiceId) throws SessionInternalError;
    public Integer[] createInvoice(Integer userId, boolean onlyRecurring) throws SessionInternalError;
    public Integer createInvoiceFromOrder(Integer orderId, Integer invoiceId) throws SessionInternalError;
    public void deleteInvoice(Integer invoiceId);

    public InvoiceWS[] getAllInvoicesForUser(Integer userId);
    public Integer[] getAllInvoices(Integer userId);
    public InvoiceWS getLatestInvoice(Integer userId) throws SessionInternalError;
    public Integer[] getLastInvoices(Integer userId, Integer number) throws SessionInternalError;

    public Integer[] getInvoicesByDate(String since, String until) throws SessionInternalError;
    public Integer[] getUserInvoicesByDate(Integer userId, String since, String until) throws SessionInternalError;
    public Integer[] getUnpaidInvoices(Integer userId) throws SessionInternalError;

    public byte[] getPaperInvoicePDF(Integer invoiceId) throws SessionInternalError;
    public boolean notifyInvoiceByEmail(Integer invoiceId);
    public boolean notifyPaymentByEmail(Integer paymentId);

    /*
        Payments
     */

    public PaymentWS getPayment(Integer paymentId) throws SessionInternalError;
    public PaymentWS getLatestPayment(Integer userId) throws SessionInternalError;
    public Integer[] getLastPayments(Integer userId, Integer number) throws SessionInternalError;
    public BigDecimal getTotalRevenueByUser (Integer userId) throws SessionInternalError;

    public PaymentWS getUserPaymentInstrument(Integer userId) throws SessionInternalError;

    public Integer createPayment(PaymentWS payment);
    public void updatePayment(PaymentWS payment);
    public void deletePayment(Integer paymentId);

    public void removePaymentLink(Integer invoiceId, Integer paymentId) throws SessionInternalError;
    public void createPaymentLink(Integer invoiceId, Integer paymentId);

    public PaymentAuthorizationDTOEx payInvoice(Integer invoiceId) throws SessionInternalError;
    public Integer applyPayment(PaymentWS payment, Integer invoiceId) throws SessionInternalError;
    public PaymentAuthorizationDTOEx processPayment(PaymentWS payment, Integer invoiceId);

    
    /*
        Billing process
     */

    public boolean isBillingRunning();
    public void triggerBillingAsync(final Date runDate);
    public boolean triggerBilling(Date runDate);
    public void triggerAgeing(Date runDate);

    public BillingProcessConfigurationWS getBillingProcessConfiguration() throws SessionInternalError;
    public Integer createUpdateBillingProcessConfiguration(BillingProcessConfigurationWS ws) throws SessionInternalError;

    public BillingProcessWS getBillingProcess(Integer processId);
    public Integer getLastBillingProcess() throws SessionInternalError;
    
    public List<OrderProcessWS> getOrderProcesses(Integer orderId);
    public List<OrderProcessWS> getOrderProcessesByInvoice(Integer invoiceId);

    public BillingProcessWS getReviewBillingProcess();
    public BillingProcessConfigurationWS setReviewApproval(Boolean flag) throws SessionInternalError;

    public List<Integer> getBillingProcessGeneratedInvoices(Integer processId);

    public AgeingWS[] getAgeingConfiguration(Integer languageId) throws SessionInternalError ;
    public void saveAgeingConfiguration(AgeingWS[] steps, Integer gracePeriod, Integer languageId) throws SessionInternalError;


    /*
        Mediation process
     */

    public void triggerMediation();
    public boolean isMediationProcessing();

    public List<MediationProcessWS> getAllMediationProcesses();
    public List<MediationRecordLineWS> getMediationEventsForOrder(Integer orderId);
    public List<MediationRecordLineWS> getMediationEventsForInvoice(Integer invoiceId);
    public List<MediationRecordWS> getMediationRecordsByMediationProcess(Integer mediationProcessId);
    public List<RecordCountWS> getNumberOfMediationRecordsByStatuses();

    public List<MediationConfigurationWS> getAllMediationConfigurations();
    public void createMediationConfiguration(MediationConfigurationWS cfg);
    public List<Integer> updateAllMediationConfigurations(List<MediationConfigurationWS> configurations) throws SessionInternalError;
    public void deleteMediationConfiguration(Integer cfgId);


    /*
        Provisioning process
     */

    public void triggerProvisioning();

    public void updateOrderAndLineProvisioningStatus(Integer inOrderId, Integer inLineId, String result);
    public void updateLineProvisioningStatus(Integer orderLineId, Integer provisioningStatus);


    /*
        Utilities
     */

    public void generateRules(String rulesData) throws SessionInternalError;


    /*
        Preferences
     */

    public void updatePreferences(PreferenceWS[] prefList);
    public void updatePreference(PreferenceWS preference);
    public PreferenceWS getPreference(Integer preferenceTypeId);


    /*
        Currencies
     */

    public CurrencyWS[] getCurrencies();
    public void updateCurrencies(CurrencyWS[] currencies);
    public void updateCurrency(CurrencyWS currency);
    public Integer createCurrency(CurrencyWS currency);

    public CompanyWS getCompany();
    public void updateCompany(CompanyWS companyWS);
    
    /*
        Notifications
    */

    public void createUpdateNofications(Integer messageId, MessageDTO dto);
    public void saveCustomerNotes(Integer userId, String notes);


    /*
        Plug-ins
     */

    public PluggableTaskWS getPluginWS(Integer pluginId);
    public Integer createPlugin(PluggableTaskWS plugin);
    public void updatePlugin(PluggableTaskWS plugin);
    public void deletePlugin(Integer plugin);


    public boolean validateCreditCard(com.infosense.ibilling.server.entity.CreditCardDTO creditCard, ContactWS contact, int level) 
    throws SessionInternalError;
    
    /*
     * Plans
     */
    
    public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory);
	
	public Integer createPlan(ItemDTOEx newPlan, Integer cpu, Integer memory, Map<String, Integer> properties);
	
	public InvoiceWS getLatestInvoiceByOrder(Integer orderId);
}
