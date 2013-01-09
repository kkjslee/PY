// JavaScript Document
// Author: Bill, 2011

var Template_LogsPanel='\
<div id="${id}">\
	<div style="margin:10px 5px;color:#777;font-size:12px;">\
		<span>Email sent time is between 00:00 ~ 23:59 in GMT+0</span>\
		<span style="border-left:1px dashed silver;padding:0 10px;margin:0 10px;">\
			<span>'+Locale["logs.label.search"]+'</span>\
			<input name="serchuser" />\
			<button name="search" onclick="search()">Go</button>\
		</span>\
	</div>\
	<table zmlm:item="logsList" class="logsList">\
		<thead>\
			<tr class="headerRow">\
				<th class="logsEmailTime logsRowHeader">'+Locale["logs.template.email.time"]+'</th>\
				<th class="logsEmailSender logsRowHeader">'+Locale["logs.template.email.sender"]+'</th>\
				<th class="logsEmailLogin logsRowHeader">'+Locale["logs.template.email.login"]+'</th>\
				<th class="logsEmailReceiver logsRowHeader">'+Locale["logs.template.email.receiver"]+'</th>\
				<th class="logsEmailSubject logsRowHeader">'+Locale["logs.template.email.subject"]+'</th>\
				<th class="logsEmailNotes logsRowHeader">'+Locale["logs.template.email.notes"]+'</th>\
				<th class="logsEmailBody logsRowHeader">'+Locale["logs.template.email.body"]+'</th>\
			</tr>\
		</thead>\
		<tbody></tbody>\
		<tfoot>\
			<tr class="footerRow">\
				<td colspan="5">\
					<span class="navibar">\
						<button name="prevbtn" onclick="loadLogs(-1)">'+Locale["logs.button.prev"]+'</button>\
						<input name="datepicker" readonly/>\
						<a href="javascript:void" onclick="loadLogs(this);return false;">'+Locale["logs.button.goto"]+'</a>\
						<button name="nextbtn" onclick="loadLogs(1)">'+Locale["logs.button.next"]+'</button>\
					</span>\
				</td>\
				<td>\
					<button onclick="loadLogs()">'+Locale["network.template.network.refresh"]+'</button></td>\
				</td>\
			</tr>\
		</tfoot>\
	</table>\
</div>\
';

var Template_LogsRow='\
<tr class="logsRow">\
	<td class="logsEmailTime logsRowCell"><span>{{html formatDate(senttime)}}</span></td>\
	<td class="logsEmailSender logsRowCell"><span>${emailsender}</span></td>\
	<td class="logsEmailLogin logsRowCell"><span>${loginuesr}</span></td>\
	<td class="logsEmailReceiver logsRowCell"><span>${emailreceiver}</span></td>\
	<td class="logsEmailSubject logsRowCell"><span>${emailsubject}</span></td>\
	<td class="logsEmailNotes logsRowCell"><span>${notes}</span></td>\
	<td class="logsEmailBody logsRowCell"><span>${emailbody}</span></td>\
</tr>\
';

var Template_MessageBox='\
<div title="'+Locale["logs.dialog.title.tips"]+'">\
	<p class="message">{{html message}}</p>\
</div>';







