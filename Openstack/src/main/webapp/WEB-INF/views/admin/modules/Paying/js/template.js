// JavaScript Document
// Author: Bill, 2012

var Template_PayingPanel='\
<div id="${id}">\
	<span class="headerRow">\
		<span class="payingUser payingRowHeader">'+Locale["paying.template.paying.login"]+':<input name="filter_user" style="width:60px;" placeholder="'+Locale["global.placeholder.enter"]+'" /></span>\
		<span class="payingVm payingRowHeader">'+Locale["paying.template.paying.vmname"]+':<input name="filter_vm" style="width:60px;" placeholder="'+Locale["global.placeholder.enter"]+'" /></span>\
		<span class="payingPlan payingRowHeader">'+Locale["paying.template.paying.vmplan"]+':</span>\
		<span class="payingPrice payingRowHeader">'+Locale["paying.template.paying.vmplanprice"]+'</span>\
		<span class="payingStatus payingRowHeader">'+Locale["paying.template.paying.status"]+':\
			<select name="filter_status" style="width:80px;">\
				<option value="">'+Locale["paying.template.option.all"]+'</option>\
				<option value="approved">'+Locale["paying.template.option.approved"]+'</option>\
				<option value="unapproved">'+Locale["paying.template.option.unapproved"]+'</option>\
				<option value="waiting">'+Locale["paying.template.option.waiting"]+'</option>\
			</select>\
		</span>\
		<span class="payingSubmitted payingRowHeader">'+Locale["paying.template.paying.submissiontime"]+'</span>\
		<span class="payingOperation payingRowHeader">'+Locale["paying.template.paying.operation"]+'</span>\
	</span>														\
	<span zmlm:item="payingList" class="payingList"></span>		\
	<span class="footerRow">\
		<button onclick="loadPaying()">'+Locale["paying.template.paying.refresh"]+'</button>\
	</span>\
</div>\
';

var Template_PayingRow='\
<span class="payingRow">\
	<input zmlm:item="requestid" type="hidden" value="${requestid}" />\
	<input zmlm:item="requeststatus" type="hidden" value="${status}" />\
	<span class="payingRowCell payingUser textCollapse">${login}</span>\
	<span class="payingRowCell payingVm textCollapse">${vmname}</span>\
	<span class="payingRowCell payingPlan textCollapse">${vmplan}</span>\
	<span class="payingRowCell payingPrice textCollapse">{{html formatPrice(vmplanprice)}}</span>\
	<span class="payingRowCell payingStatus textCollapse">{{html formatStatus(status)}}</span>\
	<span class="payingRowCell payingSubmitted textCollapse">{{html formatDate(submissiontime)}}</span>\
	<span class="payingRowCell payingOperation textCollapse">\
		{{if status==="waiting"}}\
			<a href="#" onclick="exam(this, \'approved\');return false;">'+Locale["paying.template.paying.approve"]+'</a>\
			<a href="#" onclick="exam(this, \'unapproved\');return false;">'+Locale["paying.template.paying.reject"]+'</a>\
		{{/if}}\
	</span>\
</span>\
';

var Template_MessageBox='\
<div title="'+Locale["paying.dialog.title.tips"]+'">\
	<p class="message">{{html message}}</p>\
</div>';







