var Template_VolumeReqClientList = '\
<div class="volumenotes ui-widget-content ui-corner-all">'+Locale["volumeReqClient.message.tips"]+'</div>\
<div id="${id}" >\
	<table class="dataTableFile" width="100%">\
	<thead>\
		<tr class="headerRow">\
			<th class="volumeReqClientName">' + Locale["volumeReqClient.label.header.name"] + '</th>\
			<th class="volumeReqClientZoneDisplay">' + Locale["volumeReqClient.label.header.zone"] + '</th>\
			<th class="volumeReqClientStatus">' + Locale["volumeReqClient.label.header.status"] + '</th>\
			<th class="volumeReqClientQuantity">' + Locale["volumeReqClient.label.header.quantity"] + '</th>\
			<th class="volumeReqClientNotes">' + Locale["volumeReqClient.label.header.notes"] + '</th>\
			<th class="volumeReqClientsubmissiontime">' + Locale["volumeReqClient.label.header.submissiontime"] + '</th>\
			<th class="volumeReqClientOpe">' + Locale["volumeReqClient.label.header.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	<tr  class="footerRow">\
	<td colspan="6" style="text-align:center;">\
	</td>\
	<td colspan="1"  style="text-align:right;">\
		<button onclick="loadVolumeReqClientList()">' + Locale["volumeReqClient.label.table.refresh"] + '</button>\
	</td>\
	</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_VolumeReqClientRow = '\
<tr class="volumeReqClientRow">\
	<input zmlm:item="volumerequestid" type="hidden" value="${volumerequestid}" />\
	<input zmlm:item="volumename" type="hidden" value="${volumename}" />\
	<input zmlm:item="submissiontime" type="hidden" value="${submissiontime}" />\
	<input zmlm:item="status" type="hidden" value="${status}" />\
	<input zmlm:item="login" type="hidden" value="${login}" />\
	<input zmlm:item="zone" type="hidden" value="${zone}" />\
	<input zmlm:item="zonedisplay" type="hidden" value="${zonedisplay}" />\
	<input zmlm:item="quantity" type="hidden" value="${quantity}" />\
	<input zmlm:item="notes" type="hidden" value="${notes}" />\
	<td class="volumeReqClientName" title="${volumename}">${volumename}</td>\
	<td class="volumeReqClientZoneDisplay" title="${zonedisplay}">${zonedisplay}</td>\
	<td class="volumeReqClientStatus {{if status}} {{if status=="approved"}}green{{/if}}  {{if status=="rejected"}}orange{{/if}} {{/if}}" title="{{if status}} {{if status=="approved"}}'+Locale["volumeReqClient.label.status.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["volumeReqClient.label.status.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["volumeReqClient.label.status.unapproved"]+'{{/if}}{{/if}}">\
	{{if status}} {{if status=="approved"}}'+Locale["volumeReqClient.label.status.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["volumeReqClient.label.status.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["volumeReqClient.label.status.unapproved"]+'{{/if}}{{/if}}</td>\
	<td class="volumeReqClientQuantity" title="${quantity}">${quantity}</td>\
	<td class="volumeReqClientNotes" title="${notes}">${notes}</td>\
	<td class="volumeReqClientsubmissiontime" title="${formatDate(submissiontime.time)}">${formatDate(submissiontime.time)}</td>\
	<td class="volumeReqClientOpe">\
	{{if status}}{{if status=="unapproved"}}<a href="#" onclick="remove(this);return false;">' + Locale["volumeReqClient.label.operation.remove"] + '</a>{{/if}}{{/if}}\
	</td>\
</tr>\
';


var Template_MessageBox = '\
<div title="' + Locale["volumeReqClient.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';