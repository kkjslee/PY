var Template_VolumeLogList = '\
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
			<td id="filterHead0"><span>' + Locale["volumelogmgr.label.header.login"] + ' : </span><span><input zmlm:item="userlogin" type="text" style="width:100px;" placeholder="'+Locale["volumelogmgr.label.search.enter"] +'"></span>\
			<td id="filterHead4"><span>' + Locale["volumelogmgr.label.header.adminlogin"] + ' : </span><span><input zmlm:item="adminlogin" type="text" style="width:100px;" placeholder="'+Locale["volumelogmgr.label.search.enter"] +'"></span>\
			<td id="filterHead1"><span>' + Locale["volumelogmgr.label.header.status"] + ' : </span><select style="width:80px;height:20px;" zmlm:item="action">\
			<option value="">' + Locale["volumelogmgr.label.header.all"] + '</option>\
			<option value="approve" selected>' + Locale["volumelogmgr.label.operation.approve"] + '</option>\
			<option value="reject">' + Locale["volumelogmgr.label.operation.refuse"] + '</option>\
			</select></td>\
			<td id="filterHead2"><span>' +  Locale["volumelogmgr.label.search.starttime"] + ' : </span><span><input id="starttime" readonly zmlm:item="starttime" type="text" style="width:80px;padding-left:5px;"></span>\
			<td id="filterHead3"><span>' +  Locale["volumelogmgr.label.search.endtime"] + ' : </span><span><input id="endtime" readonly zmlm:item="endtime" type="text" style="width:80px;padding-left:5px;"></span>\
		</tr>\
		</tbody>\
	</table>\
</div>\
<div id="${id}" >\
	<table class="dataTableFile" >\
	<thead >\
		<tr class="headerRow">\
			<th class="volumeLogMgrName">' + Locale["volumelogmgr.label.header.name"] + '</th>\
			<th class="volumeLogMgrName">' + Locale["volumelogmgr.label.header.size"] + '</th>\
			<th class="volumeLogMgrZoneDisplay">' + Locale["volumelogmgr.label.header.zone"] + '</th>\
			<th class="volumeLogMgrLogin">' + Locale["volumelogmgr.label.header.login"] + '</th>\
			<th class="volumeLogMgrQuantity">' + Locale["volumelogmgr.label.header.quantity"] + '</th>\
			<th class="volumeLogMgrNotes">' + Locale["volumelogmgr.label.header.notes"] + '</th>\
			<th class="volumeLogMgrLogin">' + Locale["volumelogmgr.label.header.adminlogin"] + '</th>\
			<th class="volumeLogClientsubmissiontime">' + Locale["volumelogmgr.label.header.submissiontime"] + '</th>\
			<th class="volumeLogMgrStatus">' + Locale["volumelogmgr.label.header.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
		<tr class="footerRow">\
		<td colspan="8" style="text-align:center;">\
		</td>\
		<td colspan="1" style="text-align:right;">\
			<button onclick="loadVolumeLogList()">' + Locale["volumelogmgr.label.table.refresh"] + '</button>\
		</td>\
		</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_VolumeLogRow = '\
<tr class="volumeLogRow">\
	<input zmlm:item="logid" type="hidden" value="${logid}" />\
	<input zmlm:item="volumename" type="hidden" value="${volumename}" />\
	<input zmlm:item="adminactiontime" type="hidden" value="${adminactiontime}" />\
	<input zmlm:item="action" type="hidden" value="${action}" />\
	<input zmlm:item="loginuser" type="hidden" value="${loginuser}" />\
	<input zmlm:item="zone" type="hidden" value="${zone}" />\
	<input zmlm:item="zonedisplay" type="hidden" value="${zonedisplay}" />\
	<input zmlm:item="quantity" type="hidden" value="${quantity}" />\
	<input zmlm:item="notes" type="hidden" value="${notes}" />\
	<input zmlm:item="adminlogin" type="hidden" value="${adminlogin}" />\
	<td class="volumeLogMgrName" title="${volumename}">${volumename}</td>\
	<td class="volumeLogMgrSize" title="${volumesize}">${volumesize}</td>\
	<td class="volumeLogMgrZoneDisplay" title="${zonedisplay}">${zonedisplay}</td>\
	<td class="volumeLogMgrLogin" title="${login}">${login}</tdh>\
	<td class="volumeLogMgrQuantity" title="${quantity}">${quantity}</td>\
	<td class="volumeLogMgrNotes" title="${notes}">${notes}</td>\
	<td class="volumeLogMgrLogin" title="${adminlogin}">${adminlogin}</td>\
	<td class="volumeLogMgrsubmissiontime" title="${formatDate(adminactiontime.time)}">${formatDate(adminactiontime.time)}</td>\
	<td class="volumeLogMgrStatus {{if action}} {{if action=="approve"}}green{{/if}}  {{if action=="reject"}}orange{{/if}} {{/if}}" title="{{if action}} {{if action=="approve"}}'+Locale["volumelogmgr.label.operation.approve"]+'{{/if}}{{if action=="reject"}}'+Locale["volumelogmgr.label.operation.refuse"]+'{{/if}}{{else}}${action}{{/if}}">\
	{{if action}} {{if action=="approve"}}'+Locale["volumelogmgr.label.operation.approve"]+'{{/if}}{{if action=="reject"}}'+Locale["volumelogmgr.label.operation.refuse"]+'{{/if}}{{else}}${action}{{/if}}</td>\
</tr>\
';


var Template_MessageBox = '\
<div title="' + Locale["volumelogmgr.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';