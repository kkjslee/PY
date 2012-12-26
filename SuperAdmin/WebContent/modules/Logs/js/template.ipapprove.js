// JavaScript Document
// Author: Bill, 2011

var Template_LogsPanel='\
<div id="${id}">\
	<span class="headerRow">\
		<span class="logsIp logsRowHeader">'+Locale["logs.template.network.ip"]+':<input name="filter_networkip" style="width:60px;" placeholder="'+Locale["global.placeholder.enter"]+'" /></span>\
		<span class="logsRequestor logsRowHeader">'+Locale["logs.template.network.iprequestor"]+'</span>\
		<span class="logsActionTime logsRowHeader">'+Locale["logs.template.network.actiontime"]+'</span>\
		<span class="logsAction logsRowHeader">'+Locale["logs.template.network.action"]+'</span>\
	</span>														\
	<span zmlm:item="logsList" class="logsList"></span>		\
	<span class="footerRow">\
		<button onclick="loadLogs()">'+Locale["network.template.network.refresh"]+'</button>\
	</span>\
</div>\
';

var Template_LogsRow='\
<span class="logsRow">\
	<span class="logsRowCell logsIp textCollapse">${ip}</span>	\
	<span class="logsRowCell logsRequestor textCollapse">${iprequestor}</span>	\
	<span class="logsRowCell logsActionTime textCollapse">{{html formatDate(actiontime)}}</span>\
	<span class="logsRowCell logsAction textCollapse">{{html formatIpStatus(action)}}</span>\
</span>\
';

var Template_MessageBox='\
<div title="'+Locale["network.dialog.title.tips"]+'">\
	<p class="message">{{html message}}</p>\
</div>';







