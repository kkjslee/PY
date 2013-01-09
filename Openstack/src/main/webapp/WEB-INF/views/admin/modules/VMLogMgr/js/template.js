var Template_VMLogList = '\
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
				<td id="filterHead4"><span>' +  Locale["vmlogmgr.label.header.login"] + ' : </span><span><input zmlm:item="userlogin" type="text" style="width:100px;" placeholder="'+Locale["vmlogmgr.label.search.enter"] +'"></span>\
				<td id="filterHead0"><span>' +  Locale["vmlogmgr.label.header.adminlogin"] + ' : </span><span><input zmlm:item="adminlogin" type="text" style="width:100px;" placeholder="'+Locale["vmlogmgr.label.search.enter"] +'"></span>\
				<td id="filterHead1"><span>' + Locale["vmlogmgr.label.header.action"] + ' : </span><select style="width:80px;height:20px;" zmlm:item="action">\
				<option value="">' + Locale["vmlogmgr.label.header.all"] + '</option>\
				<option value="approve" selected>' + Locale["vmlogmgr.label.operation.approve"] + '</option>\
				<option value="reject" >' + Locale["vmlogmgr.label.operation.refuse"] + '</option>\
				</select></td>\
				<td id="filterHead2"><span>' +  Locale["vmlogmgr.label.search.starttime"] + ' : </span><span><input id="starttime" readonly zmlm:item="starttime" type="text" style="width:80px;padding-left:5px;"></span>\
				<td id="filterHead3"><span>' +  Locale["vmlogmgr.label.search.endtime"] + ' : </span><span><input id="endtime" readonly zmlm:item="endtime" type="text" style="width:80px;padding-left:5px;"></span>\
				</tr>\
		</tbody>\
	</table>\
</div>\
<div id="${id}" >\
	<table class="dataTableFile" >\
	<colgroup>\
	<col class="vmLogMgrZoneDisplay"/>\
	<col class="vmLogMgrSoftName"/>\
	<col class="vmLogMgrVMplanname"/>\
	<col class="vmLogMgrLogin"/>\
	<col class="vmLogMgrQuantity"/>\
	<col class="vmLogMgrNotes"/>\
	<col class="vmLogMgrAdminLogin"/>\
	<col class="vmLogMgrActionTime"/>\
	<col class="vmLogMgrAction"/>\
	</colgropup>\
	<thead >\
		<tr class="headerRow">\
			<th class="vmLogMgrZoneDisplay">' +  Locale["vmlogmgr.label.header.zone"] + '</th>\
			<th class="vmLogMgrSoftName">' +  Locale["vmlogmgr.label.header.softwarename"] + '</th>\
			<th class="vmLogMgrVMplanname">' +  Locale["vmlogmgr.label.header.vmplanname"] + '</th>\
			<th class="vmLogMgrLogin">' +  Locale["vmlogmgr.label.header.login"] + '</th>\
			<th class="vmLogMgrQuantity">' +  Locale["vmlogmgr.label.header.quantity"] + '</th>\
			<th class="vmLogMgrNotes">' +  Locale["vmlogmgr.label.header.notes"] + '</th>\
			<th class="vmLogMgrAdminLogin">' +  Locale["vmlogmgr.label.header.adminlogin"] + '</th>\
			<th class="vmLogMgrActionTime">' +  Locale["vmlogmgr.label.header.actiontime"] + '</th>\
			<th class="vmLogMgrAction">' +  Locale["vmlogmgr.label.header.action"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	<tr class="footerRow">\
	<td colspan="8" style="text-align:center;">\
	</td>\
	<td colspan="1" style="text-align:right;">\
		<button onclick="loadVMLogList()">' + Locale["vmlogmgr.label.table.refresh"] + '</button>\
	</td>\
	</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_VMLogRow = '\
<tr class="vmLogRow">\
	<input zmlm:item="logid" type="hidden" value="${logid}" />\
	<input zmlm:item="adminactiontime" type="hidden" value="${adminactiontime}" />\
	<input zmlm:item="adminlogin" type="hidden" value="${adminlogin}" />\
	<input zmlm:item="zone" type="hidden" value="${zone}" />\
	<input zmlm:item="zonedisplay" type="hidden" value="${zonedisplay}" />\
	<input zmlm:item="quantity" type="hidden" value="${quantity}" />\
	<input zmlm:item="notes" type="hidden" value="${notes}" />\
	<input zmlm:item="action" type="hidden" value="${action}" />\
	<input zmlm:item="loginuser" type="hidden" value="${loginuser}" />\
	<input zmlm:item="softwarename" type="hidden" value="${softwarename}" />\
	<input zmlm:item="vmplanname" type="hidden" value="${vmplanname}" />\
	<td class="vmLogMgrZoneDisplay" title="${zonedisplay}">${zonedisplay}</td>\
	<td class="vmLogMgrSoftName" title="${softwarename}">${softwarename}</td>\
	<td class="vmLogMgrVMplanname" title="${vmplanname}">${vmplanname}</td>\
	<td class="vmLogMgrLogin" title="${loginuser}">${loginuser}</td>\
	<td class="vmLogMgrQuantity" title="${quantity}">${quantity}</td>\
	<td class="vmLogMgrNotes" title="${notes}">${notes}</td>\
	<td class="vmLogMgrAdminLogin" title="${adminlogin}">${adminlogin}</td>\
	<td class="vmLogMgrActionTime" title="${formatDate(adminactiontime.time)}">${formatDate(adminactiontime.time)}</td>\
	<td class="vmLogMgrAction {{if action}} {{if action=="approve"}}green{{/if}}  {{if action=="reject"}}orange{{/if}} {{/if}}" title="{{if action}} {{if action=="approve"}}'+Locale["vmlogmgr.label.operation.approve"]+'{{/if}}{{if action=="reject"}}'+Locale["vmlogmgr.label.operation.refuse"]+'{{/if}}{{else}}${action}{{/if}}">\
	{{if action}} {{if action=="approve"}}'+Locale["vmlogmgr.label.operation.approve"]+'{{/if}}{{if action=="reject"}}'+Locale["vmlogmgr.label.operation.refuse"]+'{{/if}}{{else}}${action}{{/if}}</td>\
</tr>\
';

var Template_MessageBox = '\
<div title="' +  Locale["vmlogmgr.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';