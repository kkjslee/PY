package ibilling

/**
 * RecentItemType
 */
enum RecentItemType {

    INVOICE             ("invoice", "list", null, "icon09.gif", "recent.item.invoice.title"),
    ORDER               ("order", "showListAndOrder", null, "icon10.gif", "recent.item.order.title"),
    PRODUCT             ("product", "show", null, "icon13.gif", "recent.item.product.title"),
    CUSTOMER            ("customer", "list", null, "icon12.gif", "recent.item.customer.title"),
    PAYMENT             ("payment", "list", null, "icon11.gif", "recent.item.payment.title"),
    PLUGIN              ("plugin", "list", null, "icon13.gif", "recent.item.plugin.title"),
    BILLINGPROCESS      ("billing", "show", null, "icon13.gif", "recent.item.billing.process.title"),
	MEDIATIONPROCESS    ("mediation", "show", null, "icon13.gif", "recent.item.mediation.process.title");

    String controller
    String action
    Map params
    String icon
    String messageCode

    def RecentItemType(controller, action, params, icon, messageCode) {
        this.controller = controller
        this.action = action
        this.params = params
        this.icon = icon
        this.messageCode = messageCode
    }
}
