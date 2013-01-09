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
	"monitor.banner" : "Monitor Management ",
	"monitor.dialog.close" : "Close",
	"monitor.dialog.create" : "Create",
	"monitor.dialog.modify" : "Modify",
	"monitor.dialog.processing" : "In progress, Please wait...",
	"monitor.dialog.title.tips" : "Tips",
	"monitor.dialog.title.create" : "Create Monitor",
	"monitor.dialog.title.update" : "Update Monitor",
	"monitor.confirm.create" : "Do you want to create monitor?",
	"monitor.confirm.remove" : "Do you want to remove monitor [%s] ?",
	"monitor.confirm.update" : "Do you want to %s monitor [%s] ?",
	"monitor.confirm.modify" : "Do you want to modify the settings of monitor ?",
	"monitor.message.type.in.monitor.name" : "Please fill in monitor name. It should construct with letters, numbers or underline. Numbers cannot be initial. Monitor name must not longer than 20 characters.",
	"monitor.message.type.in.monitor.ip" : "Please choose an available IP.",
	"monitor.message.loading" : "Loading...",
	"monitor.message.create.done" : "Monitor Created",
	"monitor.message.remove.done" : "Monitor Removed",
	"monitor.message.update.done" : "Monitor Updated",
	"monitor.message.modify.done" : "Monitor Settings Updated",
	"monitor.message.error" : "Exception found, Please contact your administrator",
	"monitor.message.undefined" : "Undefined return message [%s]",
	"monitor.message.no_data": "No Data",
	"monitor.template.tips" : "1. When creating monitor, please wait for a moment for monitor's collecting data.<br/>2. Monitor data will be removed while detaching IP from VM.",
	"monitor.template.monitor.name": "Monitor",
	"monitor.template.monitor.ip": "IP",
	"monitor.template.monitor.frequency": "Frequency",
	"monitor.template.monitor.status": "Status",
	"monitor.template.monitor.operation": "Operation",
	"monitor.template.monitor.refresh": "Refresh",
	"monitor.template.monitor.create": "Create",
	"monitor.template.monitor.remove": "Remove",
	"monitor.template.monitor.modify": "Modify",
	"monitor.template.monitor.pause": "Pause",
	"monitor.template.monitor.resume": "Resume",
	"monitor.template.tabs.history": "Availability History",
	"monitor.template.tabs.summary": "Availability Summary",
	"monitor.template.tabs.logging": "Availability Logging",
	"monitor.template.tabs.empty": "Choose item to view",
	"monitor.template.label.summary.avail": "Avail",
	"monitor.template.label.summary.down": "Down",
	"monitor.template.label.down": "Down Time",
	"monitor.template.label.back": "Back Time",
	"monitor.template.label.interval": "Interval",
	"monitor.template.label.interval.second": "Seconds",
	"monitor.template.label.interval.minute": "Minutes",
	"monitor.template.label.interval.hour": "Hours",
	"monitor.template.label.availability": "Availability",
	"monitor.template.label.status.monitoring": "Monitoring",
	"monitor.template.label.status.pause": "Pause",
	"monitor.template.label.noavailableip": "No Available IP",
	"monitor.template.label.foo": "**"
	
	// please customize your locale string in file "extension/en_GB.ex"
	//<!--#include virtual="extension/en_GB.ex"-->
}

var Resource={
}
