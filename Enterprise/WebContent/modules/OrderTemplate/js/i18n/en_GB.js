/* JavaScript Document
Author: Bill, 2011~2012
<!--#config timefmt="%A %B %d, %Y %H:%M:%S" -->
This file last modified <!--#echo var="LAST_MODIFIED" -->

HTTP_REFERER: <!--#echo var="HTTP_REFERER" -->
REQUEST_METHOD: <!--#echo var="REQUEST_METHOD" -->
CONTENT_TYPE: <!--#echo var="CONTENT_TYPE" -->
UNIQUE_ID: <!--#echo var="UNIQUE_ID" -->
*/

/*
 * <!--#include virtual="../../../../config.shtml" -->
 */

var Locale={
	/*-- Global --*/
	"global.processing": "Processing...",
	
	dataTable: {
	    "sEmptyTable":     "No data available in table",
	    "sInfo":           "Showing _START_ to _END_ of _TOTAL_ entries",
	    "sInfoEmpty":      "Showing 0 to 0 of 0 entries",
	    "sInfoFiltered":   "(filtered from _MAX_ total entries)",
	    "sInfoPostFix":    "",
	    "sInfoThousands":  ",",
	    "sLengthMenu":     "Show _MENU_ entries",
	    "sLoadingRecords": "Loading...",
	    "sProcessing":     "Processing...",
	    "sSearch":         "Search:",
	    "sZeroRecords":    "No matching records found",
	    "oPaginate": {
	        "sFirst":    "First",
	        "sLast":     "Last",
	        "sNext":     "Next",
	        "sPrevious": "Previous"
	    },
	    "oAria": {
	        "sSortAscending":  ": activate to sort column ascending",
	        "sSortDescending": ": activate to sort column descending"
	    }
	},
	
	/* ----Module Monitor Management---- */
	"ordertemplate.banner" : "Get VM",
	
	"ordertemplate.dialog.close" : "Close",
	
	"ordertemplate.dialog.processing" : "Processing...",
	"ordertemplate.dialog.title.tips" : "Note",
	
	"ordertemplate.confirm.order.remove" : "Are you sure you want to delete the template？",
	"ordertemplate.confirm.order.submit" : "Are you sure you want to submit？",
	
	"ordertemplate.message.type.in.saveorder.template.name" : "Please fill in template name。",
	"ordertemplate.message.loading" : "Data Loading...",
	"ordertemplate.message.error" : "Please contact admin for this problem",
	"ordertemplate.message.undefined" : "Unknown Return [%s]",
	"ordertemplate.message.no_data": "No data for this module",
	"ordertemplate.message.remove.done" : "Deletion Confirmed",
	"ordertemplate.message.remove.failed" : "Please contact admin for this problem",
	
	"ordertemplate.template.tips" : "Please choose your VM from recommended VM templates below or click the \"Customised VM\" button for your own configurtion",
	"ordertemplate.template.tips.confirm" : "Please double check your order. All the prices in the order are in GBP",
	
	"ordertemplate.template.grid.software.image": "Images",
	"ordertemplate.template.grid.software.zone": "Data Centre",
	"ordertemplate.template.grid.plan.name": "Pricing Models",
	"ordertemplate.template.grid.plan.fee": "Prices",
	"ordertemplate.template.grid.network": "Network Settings",
	"ordertemplate.template.grid.network.none": "None",
	"ordertemplate.template.grid.volume": "Volume",
	"ordertemplate.template.grid.volume.none": "None",
	"ordertemplate.template.grid.hardware.type": "Hardware Configuire",
	"ordertemplate.template.grid.hardware.dcpu": "Default CPU",
	"ordertemplate.template.grid.hardware.mcpu": "Max CPU",
	"ordertemplate.template.grid.hardware.dmem": "Default Memory",
	"ordertemplate.template.grid.hardware.mmem": "Max Memory",
	"ordertemplate.template.grid.fee.unit": "£",
	"ordertemplate.template.grid.tips.notes.empty": "There is no notes for this template。",
	"ordertemplate.template.grid.tips.name.empty": "Unnamed Template",
	"ordertemplate.template.grid.button.buy": "Purchase",
	"ordertemplate.template.grid.button.custom": "Customised VM",
	
	"ordertemplate.template.naming.vm.tips": "(Only chars, numbers or _ are allowed.）",
	
	"ordertemplate.template.naming.volume.tips": "(Only chars, numbers or _ are allowed.）",
	
	"ordertemplate.template.dialog.confirm.title": "Order",
	"ordertemplate.template.dialog.confirm.software": "Images",
	"ordertemplate.template.dialog.confirm.vmname": "VM Name",
	"ordertemplate.template.dialog.confirm.datacenter": "Data Centre",
	"ordertemplate.template.dialog.confirm.dcpu": "Default CPU",
	"ordertemplate.template.dialog.confirm.mcpu": "Max CPU",
	"ordertemplate.template.dialog.confirm.dmem": "Default Memory",
	"ordertemplate.template.dialog.confirm.mmem": "Max Memory",
	"ordertemplate.template.dialog.confirm.plan": "Pricing Models",
	"ordertemplate.template.dialog.confirm.price": "Prices",
	"ordertemplate.template.dialog.confirm.cpuprice": "CPU Price",
	"ordertemplate.template.dialog.confirm.memprice": "Memory Price",
	"ordertemplate.template.dialog.confirm.netprice": "Network Price",
	"ordertemplate.template.dialog.confirm.hasnetwork": "Do you need a public IP",
	"ordertemplate.template.dialog.confirm.networktype": "Network Type",
	"ordertemplate.template.dialog.confirm.hasvolume": "Do you need a volume",
	"ordertemplate.template.dialog.confirm.volumename": "Volume Name",
	"ordertemplate.template.dialog.confirm.volumesize": "Volume Size",
	"ordertemplate.template.dialog.confirm.fee.vm": "VM Price",
	"ordertemplate.template.dialog.confirm.fee.network": "IP Price",
	"ordertemplate.template.dialog.confirm.fee.volume": "Volume Price",
	"ordertemplate.template.dialog.confirm.fee.ordercount": "Order Quantity",
	"ordertemplate.template.dialog.confirm.fee.total": "Total",
	"ordertemplate.template.dialog.confirm.subtitle.software": "Basic Configure",
	"ordertemplate.template.dialog.confirm.subtitle.hardware": "Hardware Configure",
	"ordertemplate.template.dialog.confirm.subtitle.plan": "Pricing Model",
	"ordertemplate.template.dialog.confirm.subtitle.network": "Network Configure",
	"ordertemplate.template.dialog.confirm.subtitle.volume": "Volume Configure",
	"ordertemplate.template.dialog.confirm.subtitle.total": "Order Sum",
	
	"ordertemplate.label.fee.network": "（IP Price： %s £1/30 days）",
	"ordertemplate.label.fee.volume": "（Volume Price： %s £1/GB/day）",

	"ordertemplate.label.tips.vmname.invalid": "Illegal VM name. Please use chars, numbers or _ , total length should be less than 8.",
	"ordertemplate.label.tips.vmname.unavailable": "The VM name has been taken.",
	"ordertemplate.label.tips.network.vacancy": "If you need to use network config, please fill in network information.",
	"ordertemplate.label.tips.volume.vacancy": "If you need to use volume configu, please fill in volume information.",
	"ordertemplate.label.tips.volume.name.invalid": "Illegal volume name. Please use chars, numbers or _ , total length should be less than 10.",
	"ordertemplate.label.tips.datacenter.unavailable": "Please contact your admin for this problem (Invalid Data Centre)",
	
	"ordertemplate.label.boolean.true": "Yes",
	"ordertemplate.label.boolean.false": "No",
	"ordertemplate.label.network.public": "Web Hosting",
	"ordertemplate.label.network.private": "Personal",
	
	"ordertemplate.button.order.submit": "Submit Order",
	
	"ordertemplate.page.message.buy.prefix": "Your Order:",
	"ordertemplate.page.message.buy.prefix.inst": "VM",
	"ordertemplate.page.message.buy.NO_ENOUGH_STOCK": "Sorry, system inventory is low, please try later",
	"ordertemplate.page.message.buy.VM_EXCEEDED": "Sorry, you have reached the limit of your system license, please contact admin",
	"ordertemplate.page.message.buy.NOIDORDER": "Sorry, we cannot deal with your order for now, please try later",
	"ordertemplate.page.message.buy.ERROR": "Sorry, we cannot deal with your order for now, please try later",
	"ordertemplate.page.message.buy.EXCEPTION": "Sorry, cannot deal with your request. Please contact your admin for this problem",
	"ordertemplate.page.message.buy.LOWBALANCE": "Sorry, your account balance is low, pleas top up first",
	"ordertemplate.page.message.buy.inst.NO_ENOUGH_STOCK": "Sorry，system inventory is low, pleas try later",
	"ordertemplate.page.message.buy.inst.EXCEPTION": "Sorry, cannot deal with your request. Please contact your admin for this problem",
	"ordertemplate.page.message.buy.inst.NAMENOTAVAILABLE": "Sorry, your vm name has been taken. Your order has not been made",
	"ordertemplate.page.message.buy.inst.OSTYPEERROR": "Sorry, cannot deal with your request. Please contact your admin for this problem",
	"ordertemplate.page.message.buy.inst.ORDERED": "VM request has been made sucessfully, please wait for admin's approval",
	"ordertemplate.page.message.buy.inst.CREATED": "Order submitted sucessfully, please wait for Email confirmation，After you received your email, please go to “%s” to operate your VM",
	"ordertemplate.page.message.buy.inst.CREATED.keyword": "My Cloud",
	"ordertemplate.page.message.buy.inst.LOWBALANCE": "Sorry, your account balance is low, pleas top up first",
	"ordertemplate.page.message.buy.inst.ONEVMONLY": "Sorry, you cannot apply for more VMs as you have reached your limit",
	"ordertemplate.page.message.buy.inst.DONE": "VM Order has been made. Succeed %s , Failed %s",
	"ordertemplate.page.message.buy.inst.UNKNOWN_ERROR": "Sorry, cannot deal with your request. Please contact your admin for this problem",
	"ordertemplate.page.message.buy.ip.NO_ENOUGH_STOCK": "Sorry, system inventory is low, please try later",
	"ordertemplate.page.message.buy.ip.EXCEPTION": "Sorry, system inventory is low, please try later",
	"ordertemplate.page.message.buy.ip.NOIP": "IP Purchase: Sorry, we cannot deal with your request. Please contact admin for this problem.",
	"ordertemplate.page.message.buy.ip.WEB": "IP Purchase: Order made. If you need to use this IP to host your web, please provide your information",
	"ordertemplate.page.message.buy.ip.LOWBALANCE": "IP Purchase: Sorry, your account balance is low. Your order has not been made",
	"ordertemplate.page.message.buy.ip.PERSONAL": "IP Purchase: Purchase Succeed. You can go to “%s” to operate your IP",
	"ordertemplate.page.message.buy.ip.PERSONAL.keyword": "My Cloud",
	"ordertemplate.page.message.buy.ip.BATCH": "IP Purchase：Succeed %s, Failed %s",
	"ordertemplate.page.message.buy.ip.UNKNOWN_ERROR": "IP Purchase：we cannot deal with your request, please contact admin",
	"ordertemplate.page.message.buy.volume.NO_ENOUGH_STOCK": "Sorry, system inventory is low, please try later",
	"ordertemplate.page.message.buy.volume.EXCEPTION": "Volume Purchase：Sorry, service is under maintanence, please try later",
	"ordertemplate.page.message.buy.volume.LOWBALANCE": "Volume Purchase: Sorry, your account balance is low. Your order has not been made",
	"ordertemplate.page.message.buy.volume.DONE": "Volume Purchase: Purchase Succeed. You can go to “%s” to use your volume",
	"ordertemplate.page.message.buy.volume.DONE.keyword": "My Cloud",
	"ordertemplate.page.message.buy.volume.BATCH": "Volume Purchase: Succeed %s, Failed %s",
	"ordertemplate.page.message.buy.volume.UNKNOWN_ERROR": "Volume Purchase: we cannot deal with your request, please contact admin",
	
	"ordertemplate.page.message.save.success": "Successfully Saved",
	"ordertemplate.page.message.save.failure": "Failed to Save. Please Try Again."
	
	// please customize your locale string in file "extension/en_GB.ex"
	//<!--#include virtual="extension/en_GB.ex"-->
}

var Resource={
}
