var Template_VMReqClientList = '\
<div class="vmnotes ui-widget-content ui-corner-all">'+Locale["vmReqClient.message.notes"]+'</div>\
<div id="${id}" >\
	<table class="dataTableFile" width="100%">\
	<thead>\
		<tr class="headerRow">\
			<th class="vmReqClientName">' + Locale["vmReqClient.label.header.name"] + '</th>\
			<th class="vmReqClientZoneDisplay">' + Locale["vmReqClient.label.header.zone"] + '</th>\
			<th class="vmReqClientStatus">' + Locale["vmReqClient.label.header.status"] + '</th>\
			<th class="vmReqClientQuantity">' + Locale["vmReqClient.label.header.quantity"] + '</th>\
			<th class="vmReqClientNotes">' + Locale["vmReqClient.label.header.notes"] + '</th>\
			<th class="vmReqClientDefaultCPU">' + Locale["vmReqClient.label.header.defaultcpu"] + '</th>\
			<th class="vmReqClientMaxCPU">' + Locale["vmReqClient.label.header.maxcpu"] + '</th>\
			<th class="vmReqClientDefaultmem">' + Locale["vmReqClient.label.header.defaultmem"] + '</th>\
			<th class="vmReqClientMaxmem">' + Locale["vmReqClient.label.header.maxmem"] + '</th>\
			<th class="vmReqClientVMplanname">' + Locale["vmReqClient.label.header.vmplanname"] + '</th>\
			<th class="vmReqClientSoftName">' + Locale["vmReqClient.label.header.softwarename"] + '</th>\
			<th class="vmReqClientsubmissiontime">' + Locale["vmReqClient.label.header.submissiontime"] + '</th>\
			<th class="vmReqClientOpe">' + Locale["vmReqClient.label.header.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	<tr class="footerRow">\
	<td colspan="11" style="text-align:center;">\
	</td>\
	<td colspan="2" style="text-align:right;">\
		<button onclick="loadvmReqClientList()">' + Locale["vmReqClient.label.table.refresh"] + '</button>\
	</td>\
	</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_VMReqClientRow = '\
<tr class="vmReqClientRow">\
	<input zmlm:item="vmrequestid" type="hidden" value="${vmrequestid}" />\
	<input zmlm:item="vmname" type="hidden" value="${vmname}" />\
	<input zmlm:item="softwareid" type="hidden" value="${softwareid}" />\
	<input zmlm:item="sftresourceid" type="hidden" value="${sftresourceid}" />\
	<input zmlm:item="submissiontime" type="hidden" value="${submissiontime}" />\
	<input zmlm:item="status" type="hidden" value="${status}" />\
	<input zmlm:item="login" type="hidden" value="${login}" />\
	<input zmlm:item="zone" type="hidden" value="${zone}" />\
	<input zmlm:item="zonedisplay" type="hidden" value="${zonedisplay}" />\
	<input zmlm:item="quantity" type="hidden" value="${quantity}" />\
	<input zmlm:item="notes" type="hidden" value="${notes}" />\
	<input zmlm:item="defaultcpu" type="hidden" value="${defaultcpu}" />\
	<input zmlm:item="maxcpu" type="hidden" value="${maxcpu}" />\
	<input zmlm:item="defaultmem" type="hidden" value="${defaultmem}" />\
	<input zmlm:item="maxmem" type="hidden" value="${maxmem}" />\
	<input zmlm:item="softwarename" type="hidden" value="${softwarename}" />\
	<input zmlm:item="vmplanname" type="hidden" value="${vmplanname}" />\
	<td class="vmReqClientName" title="${vmname}">${vmname}</td>\
	<td class="vmReqClientZoneDisplay" title="${zonedisplay}">${zonedisplay}</td>\
	<td class="vmReqClientStatus  {{if status}} {{if status=="approved"}}green{{/if}}  {{if status=="rejected"}}orange{{/if}} {{/if}}" title="{{if status}} {{if status=="approved"}}'+Locale["vmReqClient.label.label.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["vmReqClient.label.label.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["vmReqClient.label.label.unapproved"]+'{{/if}}{{/if}}" >\
	{{if status}} {{if status=="approved"}}'+Locale["vmReqClient.label.label.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["vmReqClient.label.label.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["vmReqClient.label.label.unapproved"]+'{{/if}}{{/if}}</td>\
	<td class="vmReqClientQuantity" title="${quantity}">${quantity}</td>\
	<td class="vmReqClientNotes" title="${notes}">${notes}</td>\
	<td class="vmReqClientDefaultCPU" title="${defaultcpu}">${defaultcpu}</td>\
	<td class="vmReqClientMaxCPU" title="${maxcpu}">${maxcpu}</td>\
	<td class="vmReqClientDefaultmem" title="${formatSize(defaultmem)}">${formatSize(defaultmem)}</td>\
	<td class="vmReqClientMaxmem" title="${formatSize(defaultmem)}">${formatSize(maxmem)}</td>\
	<td class="vmReqClientVMplanname" title="${vmplanname}">${vmplanname}</td>\
	<td class="vmReqClientSoftName" title="${softwarename}">${softwarename}</td>\
	<td class="vmReqClientsubmissiontime" title="${formatDate(submissiontime.time)}">${formatDate(submissiontime.time)}</td>\
	<td class="vmReqClientOpe">\
	{{if status}}{{if status=="unapproved"}}<a href="#" onclick="remove(this);return false;">' + Locale["vmReqClient.label.operation.remove"] + '</a>{{/if}}{{/if}}\
	</td>\
</tr>\
';

var Template_MessageBox = '\
<div title="' + Locale["vmReqClient.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';