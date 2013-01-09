var Template_VolumeReqList = '\
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
			<td id="filterHead0"><span>' + Locale["volumereqmgr.label.header.name"] + ' : </span><span><input zmlm:item="volumename" type="text" style="width:100px;" placeholder="'+Locale["volumereqmgr.label.search.enter"] +'"></span>\
			<td id="filterHead1"><span>' + Locale["volumereqmgr.label.header.status"] + ' : </span><select style="width:80px;height:20px;" zmlm:item="status">\
			<option value="">' + Locale["volumereqmgr.label.header.all"] + '</option>\
			<option value="unapproved" selected>' + Locale["volumereqmgr.label.status.unapproved"] + '</option>\
			<option value="approved" >' + Locale["volumereqmgr.label.status.approved"] + '</option>\
			<option value="rejected">' + Locale["volumereqmgr.label.status.rejected"] + '</option>\
		</select></td></tr>\
	</tbody>\
	</table>\
</div>\
<div id="${id}" >\
	<table class="dataTableFile" >\
	<thead >\
		<tr class="headerRow">\
			<th class="volumeReqMgrName">' + Locale["volumereqmgr.label.header.name"] + '</th>\
			<th class="volumeReqMgrZoneDisplay">' + Locale["volumereqmgr.label.header.zone"] + '</th>\
			<th class="volumeReqMgrStatus">' + Locale["volumereqmgr.label.header.status"] + '</th>\
			<th class="volumeReqMgrLogin">' + Locale["volumereqmgr.label.header.login"] + '</th>\
			<th class="volumeReqMgrQuantity">' + Locale["volumereqmgr.label.header.quantity"] + '</th>\
			<th class="volumeReqMgrNotes">' + Locale["volumereqmgr.label.header.notes"] + '</th>\
			<th class="volumeReqClientsubmissiontime">' + Locale["volumereqmgr.label.header.submissiontime"] + '</th>\
			<th class="volumeReqMgrOpe">' + Locale["volumereqmgr.label.header.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
		<tr  class="footerRow">\
		<td colspan="7" style="text-align:center;">\
		</td>\
		<td colspan="1" style="text-align:right;">\
			<button onclick="loadVolumeReqList()">' + Locale["volumereqmgr.label.table.refresh"] + '</button>\
		</td>\
		</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_VolumeReqRow = '\
<tr class="volumeReqRow">\
	<input zmlm:item="volumerequestid" type="hidden" value="${volumerequestid}" />\
	<input zmlm:item="volumename" type="hidden" value="${volumename}" />\
	<input zmlm:item="submissiontime" type="hidden" value="${submissiontime}" />\
	<input zmlm:item="status" type="hidden" value="${status}" />\
	<input zmlm:item="login" type="hidden" value="${login}" />\
	<input zmlm:item="zone" type="hidden" value="${zone}" />\
	<input zmlm:item="zonedisplay" type="hidden" value="${zonedisplay}" />\
	<input zmlm:item="quantity" type="hidden" value="${quantity}" />\
	<input zmlm:item="notes" type="hidden" value="${notes}" />\
	<td class="mReqMgrName" title="${volumename}">${volumename}</td>\
	<td class="volumeReqMgrZoneDisplay" title="${zonedisplay}">${zonedisplay}</td>\
	<td class="volumeReqMgrStatus {{if status}} {{if status=="approved"}}green{{/if}}  {{if status=="rejected"}}orange{{/if}} {{/if}}" title="{{if status}} {{if status=="approved"}}'+Locale["volumereqmgr.label.status.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["volumereqmgr.label.status.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["volumereqmgr.label.status.unapproved"]+'{{/if}}{{/if}}">\
	{{if status}} {{if status=="approved"}}'+Locale["volumereqmgr.label.status.approved"]+'{{/if}}{{if status=="rejected"}}'+Locale["volumereqmgr.label.status.rejected"]+'{{/if}}{{if status=="unapproved"}}'+Locale["volumereqmgr.label.status.unapproved"]+'{{/if}}{{/if}}</td>\
	<td class="volumeReqMgrLogin" title="${login}">${login}</tdh>\
	<td class="volumeReqMgrQuantity" title="${quantity}">${quantity}</td>\
	<td class="volumeReqMgrNotes" title="${notes}">${notes}</td>\
	<td class="volumeReqMgrsubmissiontime" title="${formatDate(submissiontime.time)}">${formatDate(submissiontime.time)}</td>\
	<td class="volumeReqMgrOpe">\
	{{if status}} {{if status=="unapproved"}}<a href="#" onclick="action(this,0);return false;">' + Locale["volumereqmgr.label.operation.approve"] + '</a>\
		<a href="#" onclick="action(this,1);return false;">' + Locale["volumereqmgr.label.operation.refuse"] + '{{/if}}{{/if}}</a>\
	</td>\
</tr>\
';

var Template_ReFuseVolumeReq = '\
	<div id="${id}" style="font-size:12px;">\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["volumereqmgr.label.action.notes"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="notes" type="input" value=""/>\
			</span>\
		</span>\
	</div>';

var Template_MessageBox = '\
<div title="' + Locale["volumereqmgr.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';