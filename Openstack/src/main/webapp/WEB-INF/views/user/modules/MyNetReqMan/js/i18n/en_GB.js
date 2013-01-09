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
	
	/* ----Module My IP Request Management---- */
	"myreq.banner" : "My IP Request",
	"myreq.dialog.close" : "Close",
	"myreq.dialog.processing" : "Processing...",
	"myreq.dialog.title.tips" : "Tips",
	"myreq.message.loading" : "Loading...",
	"myreq.message.error" : "Exception occurs.",
	"myreq.message.undefined" : "Unhandled error [%s]",
	"myreq.message.no_data": "No data",
	"myreq.template.tips" : "1. *<br/>2. *",
	"myreq.template.myreq.name": "Monitor",
	"myreq.template.myreq.ip": "IP",
	"myreq.template.myreq.status": "Status",
	"myreq.template.myreq.operation": "Operation",
	"myreq.template.myreq.refresh": "Refresh",
	"myreq.template.myreq.remove": "Remove",
	
	"myreq.template.label.status.approved": "Approved",
	"myreq.template.label.status.pending": "Pending",
	"myreq.template.label.status.rejected": "Rejected",
	
	"myreq.template.label.foo": "**"
	
	// please customize your locale string in file "extension/en_GB.ex"
	//<!--#include virtual="extension/en_GB.ex"-->
}

var Resource={
}
