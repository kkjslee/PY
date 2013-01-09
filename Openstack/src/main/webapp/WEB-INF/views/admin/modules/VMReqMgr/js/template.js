var Template_VMReqList = '\
<div>\
	<table class="finder" zmlm:item="finder">\
		<thead class="finder-indicator">\
			<tr>\
				<th><span class="ui-icon ui-icon-search"></span></th>\
				<th>Finder</th>\
			</tr>\
		</thead>\
		<tbody>\
			<tr>\
				<td id="filterHead0"><span>' + Locale["vmreqmgr.label.header.name"] + ' : </span><span><input zmlm:item="vmname" type="text" placeholder="'+Locale["vmreqmgr.label.search.enter"] +'" style="width:100px;"></span>\
				<td id="filterHead1"><span>' + Locale["vmreqmgr.label.header.status"] + ' : </span><select style="width:80px;height:20px;" zmlm:item="status">\
				<option value="">' + Locale["vmreqmgr.label.header.all"] + '</option>\
				<option value="unapproved" selected>' + Locale["vmreqmgr.label.status.unapproved"] + '</option>\
				<option value="approved" >' + Locale["vmreqmgr.label.status.approved"] + '</option>\
				<option value="rejected">' + Locale["vmreqmgr.label.status.rejected"] + '</option>\
			</select></td></tr>\
		</tbody>\
	</table>\
</div>\
<div id="${id}" >\
	<table class="dataTableFile" >\
	<thead >\
		<tr class="headerRow">\
			<th class="vmReqMgrName">' + Locale["vmreqmgr.label.header.name"] + '</th>\
			<th class="vmReqMgrZoneDisplay">' + Locale["vmreqmgr.label.header.zone"] + '</th>\
			<th class="vmReqMgrStatus">' + Locale["vmreqmgr.label.header.status"] + '</th>\
			<th class="vmReqMgrLogin">' + Locale["vmreqmgr.label.header.login"] + '</th>\
			<th class="vmReqMgrQuantity">' + Locale["vmreqmgr.label.header.quantity"] + '</th>\
			<th class="vmReqMgrNotes">' + Locale["vmreqmgr.label.header.notes"] + '</th>\
			<th class="vmReqMgrDefaultCPU">' + Locale["vmreqmgr.label.header.defaultcpu"] + '</th>\
			<th class="vmReqMgrMaxCPU">' + Locale["vmreqmgr.label.header.maxcpu"] + '</th>\
			<th class="vmReqMgrDefaultmem">' + Locale["vmreqmgr.label.header.defaultmem"] + '</th>\
			<th class="vmReqMgrMaxmem">' + Locale["vmreqmgr.label.header.maxmem"] + '</th>\
			<th class="vmReqMgrVMplanname">' + Locale["vmreqmgr.label.header.vmplanname"] + '</th>\
			<th class="vmReqMgrSoftName">' + Locale["vmreqmgr.label.header.softwarename"] + '</th>\
			<th class="vmReqMgrsubmissiontime">' + Locale["vmreqmgr.label.header.submissiontime"] + '</th>\
			<th class="vmReqMgrOpe">' + Locale["vmreqmgr.label.header.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	<tr class="footerRow">\
	<td colspan="13" style="text-align:center;">\
	</td>\
	<td colspan="1" style="text-align:right;">\
		<button onclick="loadVMReqList()">' + Locale["vmreqmgr.label.table.refresh"] + '</button>\
	</td>\
	</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_VMReqRow = '\
<tr class="vmReqRow">\
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
	<td class="vmReqMgrName" title="${vmname}">${vmname}</td>\
	<td class="vmReqMgrZoneDisplay" title="${zonedisplay}">${zonedisplay}</td>\
	<td class="vmReqMgrStatus {{if status}} {{if status=="approved"}}green{{/if}}  {{if status=="rejected"}}orange{{/if}} {{/if}}" title="{{if status}} {{if status=="approved"}}'+Locale["vmreqmgr.label.status.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["vmreqmgr.label.status.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["vmreqmgr.label.status.unapproved"]+'{{/if}}{{/if}}">\
	{{if status}} {{if status=="approved"}}'+Locale["vmreqmgr.label.status.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["vmreqmgr.label.status.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["vmreqmgr.label.status.unapproved"]+'{{/if}}{{/if}}</td>\
	<td class="vmReqMgrLogin" title="${login}">${login}</tdh>\
	<td class="vmReqMgrQuantity" title="${quantity}">${quantity}</td>\
	<td class="vmReqMgrNotes" title="${notes}">${notes}</td>\
	<td class="vmReqMgrDefaultCPU" title="${defaultcpu}">${defaultcpu}</td>\
	<td class="vmReqMgrMaxCPU" title="${maxcpu}">${maxcpu}</td>\
	<td class="vmReqMgrDefaultmem" title="${formatSize(defaultmem)}">${formatSize(defaultmem)}</td>\
	<td class="vmReqMgrMaxmem" title="${formatSize(maxmem)}">${formatSize(maxmem)}</td>\
	<td class="vmReqMgrVMplanname" title="${vmplanname}">${vmplanname}</td>\
	<td class="vmReqMgrSoftName" title="${softwarename}">${softwarename}</td>\
	<td class="vmReqMgrsubmissiontime" title="${formatDate(submissiontime.time)}">${formatDate(submissiontime.time)}</td>\
	<td class="vmReqMgrOpe">\
	{{if status}} {{if status=="unapproved"}}<a href="#" onclick="action(this,0);return false;">' + Locale["vmreqmgr.label.operation.approve"] + '</a>\
		<a href="#" onclick="action(this,1);return false;">' + Locale["vmreqmgr.label.operation.refuse"] + '{{/if}}{{/if}}</a>\
	</td>\
</tr>\
';

var Template_ReFuseVMReq = '\
	<div id="${id}" style="font-size:12px;">\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["vmreqmgr.label.action.notes"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="notes" type="input" value=""/>\
			</span>\
		</span>\
	</div>';

var Template_MessageBox = '\
<div title="' + Locale["vmreqmgr.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';