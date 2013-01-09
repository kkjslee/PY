//need to change type model after i18n
var Template_VMChargeList = '\
<div id="${id}" >\
	<table class="dataTableFile">\
	<thead>\
		<tr>\
			<th colspan="7" align="left" style="text-align:left;font-weight:normal;padding-bottom:4px;" >' + Locale["vmcharge.label.header.selectzone"] + ': \
				<select id="filtermodel" style="width:120px;" name="filter_zone">\
					<option value="">' + Locale["vmcharge.message.choose.type.filter"] + '</option>\
				</select>\
			</th>\
		</tr>\
		<tr>\
			<th class="vmChargeName">' + Locale["vmcharge.label.header.name"] + ' <input type="text" style="width:50px;height:18px" name="filter_planname"></th>\
			<th class="vmChargePrice">' + Locale["vmcharge.label.header.price"] + ' (' + Locale["vmcharge.label.unit.currency"] + ')</th>\
			<th class="vmChargeTime">' + Locale["vmcharge.label.header.time"] + ' (' + Locale["vmcharge.label.unit.time"] + ')</th>\
			<th class="vmChargeBefore">' + Locale["vmcharge.label.header.before"] + ' (' + Locale["vmcharge.label.unit.firstnotice"] + ')</th>\
			<th class="vmChargeDesc">' + Locale["vmcharge.label.header.description"] + '</th>\
			<th class="vmChargeType">' + Locale["vmcharge.label.header.type"] + '\</th>\
			<th class="vmChargeOperation">' + Locale["vmcharge.label.header.operation"] + '</th>\
		</tr>\
		<tr >\
			<th colspan="7" class="splitter"></th>\
		</tr>\
	</thead>\
	<colgroup>\
		<col class="vmChargeName">\
		<col class="vmChargePrice">\
		<col class="vmChargeTime">\
		<col class="vmChargeBefore">\
		<col class="vmChargeDesc">\
		<col class="vmChargeType">\
		<col class="vmChargeOperation">\
	</colgroup>\
	<tbody>\
	</tbody>\
	<tfoot>\
		<tr>\
			<td colspan="7" class="splitter" >\
			</td>\
		</tr>\
		<tr>\
			<td colspan="7">\
				<button onclick="loadVMChargeList()">' + Locale["vmcharge.label.table.refresh"] + '</button>\
				<button onclick="showCreateVMChargeItem()">' + Locale["vmcharge.lael.operation.create"] + '</button>\
			</td>\
		</tr>\
	</tfoot>\
	</table>\
</div>\
';

var typepag = "PAG";
var typepfm = "PFM";
var typepfmyear = "PFM-year"
var Template_VMChargeRow = '\
<tr class="vmChargeRow">\
	<input zmlm:item="vmplanid" type="hidden" value="${vmplanid}" />\
	<input zmlm:item="planname" type="hidden" value="${planname}" />\
	<input zmlm:item="subscriptionfee" type="hidden" value="${subscriptionfee}" />\
	<input zmlm:item="description" type="hidden" value="${description}" />\
	<input zmlm:item="cpuallow" type="hidden" value="${cpuallow}" />\
	<input zmlm:item="cpulimit" type="hidden" value="${cpulimit}" />\
	<input zmlm:item="cpuprice" type="hidden" value="${cpuprice}" />\
	<input zmlm:item="memallow" type="hidden" value="${memallow}" />\
	<input zmlm:item="memlimit" type="hidden" value="${memlimit}" />\
	<input zmlm:item="memprice" type="hidden" value="${memprice}" />\
	<input zmlm:item="downloadallow" type="hidden" value="${downloadallow}" />\
	<input zmlm:item="downloadlimit" type="hidden" value="${downloadlimit}" />\
	<input zmlm:item="downloadprice" type="hidden" value="${downloadprice}" />\
	<input zmlm:item="uploadallow" type="hidden" value="${uploadallow}" />\
	<input zmlm:item="uploadlimit" type="hidden" value="${uploadlimit}" />\
	<input zmlm:item="uploadprice" type="hidden" value="${uploadprice}" />\
	<input zmlm:item="ioreadallow" type="hidden" value="${ioreadallow}" />\
	<input zmlm:item="ioreadlimit" type="hidden" value="${ioreadlimit}" />\
	<input zmlm:item="ioreadprice" type="hidden" value="${ioreadprice}" />\
	<input zmlm:item="iowriteallow" type="hidden" value="${iowriteallow}" />\
	<input zmlm:item="iowritelimit" type="hidden" value="${iowritelimit}" />\
	<input zmlm:item="ioreadallow" type="hidden" value="${ioreadallow}" />\
	<input zmlm:item="iowriteprice" type="hidden" value="${iowriteprice}" />\
	<input zmlm:item="invoiceinterval" type="hidden" value="${invoiceinterval}" />\
	<input zmlm:item="invoiceduedays" type="hidden" value="${invoiceduedays}" />\
	<input zmlm:item="firstnotice" type="hidden" value="${firstnotice}" />\
	<input zmlm:item="deductfrombalance" type="hidden" value="${deductfrombalance}" />\
	<input zmlm:item="suspendfrombalance" type="hidden" value="${suspendfrombalance}" />\
	<input zmlm:item="suspendremoveinterval" type="hidden" value="${suspendremoveinterval}" />\
	<input zmlm:item="zone" type="hidden" value="${zone}" />\
	<input zmlm:item="zonedisplay" type="hidden" value="${zonedisplay}" />\
	<td class="vmChargeName" title="${planname}">${planname}</td>\
	<td class="vmChargePrice" title="${subscriptionfee}">${subscriptionfee.toFixed(2)}</td>\
	<td class="vmChargeTime" title="${invoiceinterval}">${invoiceinterval}</td>\
	<td class="vmChargeBefore" title="${firstnotice}">${firstnotice}</td>\
	<td class="vmChargeDesc" title="${description}">${description}</td>\
	<td class="vmChargeType {{if vmplanid}}{{if startWith(vmplanid,typepag)}}green {{else startWith(vmplanid,typepfmyear)}} red {{else startWith(vmplanid,typepfm)}} orange{{/if}}{{/if}}">{{if vmplanid}}${formatModelType(vmplanid)}{{/if}}</td>\
	<td class="vmChargeOperation">\
		<a href="#" onclick="showUpdateVMChargeItem(this,false);return false;">' + Locale["vmcharge.label.operation.details"] + '</a>\
		<a href="#" onclick="showUpdateVMChargeItem(this,true);return false;">' + Locale["vmcharge.label.operation.update"] + '</a>\
		<a href="#" onclick="remove(this);return false;">' + Locale["vmcharge.label.operation.remove"] + '</a>\
	</td>\
</tr>\
';

var Template_CreateVMChargeItem = '\
	<div id="${id}" style="font-size:12px;" class="chargePanel"><table>\
			<tr>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.header.name"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="planname" type="text" style="width:120px;" value=""/>\
				</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.header.description"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="description" type="text" style="width:170px; " value=""/>\
				</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.header.price"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="subscriptionfee" type="text" value=""/> (' + Locale["vmcharge.label.unit.currency"] + ')\
				</td>\
			</tr>\
			<tr>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.header.time"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="invoiceinterval" type="text" value=""/> (' + Locale["vmcharge.label.unit.time"] + ')\
				</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.suspendremoveinterval"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="suspendremoveinterval" type="text" value=""/> (' + Locale["vmcharge.label.unit.suspendremoveinterval"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.invoiceduedays"] + ':</td>\
				<td class="dialogLineRight">\
				<input zmlm:item="invoiceduedays" type="text" value=""/> (' + Locale["vmcharge.label.unit.invoiceduedays"] + ')</td>\
			</tr>\
			<tr>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.cpuprice"] + ':</td>\
				<td class="dialogLineRight">\
						<input zmlm:item="cpuprice" type="text" value=""/> (' + Locale["vmcharge.label.unit.cpuprice"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.cpuallow"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="cpuallow" type="text" value=""/> (' + Locale["vmcharge.label.unit.cpuallow"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.cpulimit"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="cpulimit" type="text" value=""/> (' + Locale["vmcharge.label.unit.cpulimit"] + ')</td>\
			</tr>\
			<tr>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.memprice"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="memprice" type="text" value=""/> (' + Locale["vmcharge.label.unit.memprice"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.memallow"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="memallow" type="text" value=""/> (' + Locale["vmcharge.label.unit.memallow"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.memlimit"] + ':</td>\
				<td class="dialogLineRight">\
				<input zmlm:item="memlimit" type="text" value=""/> (' + Locale["vmcharge.label.unit.memlimit"] + ')</td>\
			</tr>\
			<tr>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.downloadprice"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="downloadprice" type="text" value=""/> (' + Locale["vmcharge.label.unit.downloadprice"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.downloadallow"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="downloadallow" type="text" value=""/> (' + Locale["vmcharge.label.unit.downloadallow"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.downloadlimit"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="downloadlimit" type="text" value=""/> (' + Locale["vmcharge.label.unit.downloadlimit"] + ')</td>\
			</tr>\
			<tr>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.uploadprice"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="uploadprice" type="text" value=""/> (' + Locale["vmcharge.label.unit.uploadprice"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.uploadallow"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="uploadallow" type="text" value=""/> (' + Locale["vmcharge.label.unit.uploadallow"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.uploadlimit"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="uploadlimit" type="text" value=""/> (' + Locale["vmcharge.label.unit.uploadlimit"] + ')</td>\
			</tr>\
			<tr>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.ioreadprice"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="ioreadprice" type="text" value=""/> (' + Locale["vmcharge.label.unit.ioprice"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.ioreadallow"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="ioreadallow" type="text" value=""/> (' + Locale["vmcharge.label.unit.ioallow"] + ')</td>\
				<td class="dialogLineLeft">' + Locale["vmcharge.label.field.ioreadlimit"] + ':</td>\
				<td class="dialogLineRight">\
					<input zmlm:item="ioreadlimit" type="text" value=""/> (' + Locale["vmcharge.label.unit.iolimit"] + ')</td>\
			</tr>\
		<tr>\
			<td class="dialogLineLeft">' + Locale["vmcharge.label.field.iowriteprice"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="iowriteprice" type="text" value=""/> (' + Locale["vmcharge.label.unit.ioprice"] + ')</td>\
			<td class="dialogLineLeft">' + Locale["vmcharge.label.field.iowriteallow"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="iowriteallow" type="text" value=""/> (' + Locale["vmcharge.label.unit.ioallow"] + ')</td>\
			<td class="dialogLineLeft">' + Locale["vmcharge.label.field.iowritelimit"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="iowritelimit" type="text" value=""/> (' + Locale["vmcharge.label.unit.iolimit"] + ')</td>\
		</tr>\
		<tr>\
			<td class="dialogLineLeft">' + Locale["vmcharge.label.header.before"] + ':</td>\
			<td class="dialogLineRight">\
			<input zmlm:item="firstnotice" type="text" value=""/> (' + Locale["vmcharge.label.unit.firstnotice"] + ')\
			</td>\
			<td class="dialogLineLeft typelabel">' + Locale["vmcharge.label.header.type"] + ':</td>\
			<td class="dialogLineRight typeselect">\
			<select id="selplantype" zmlm:item="softwaremodel" style="width:150px;">\
			</select>\
			</td>\
			<td class="dialogLineLeft zonelabel">' + Locale["vmcharge.label.header.zone"] + ':</td>\
			<td class="dialogLineRight zoneselect">\
				<select id="selzone" name="zones"  multiple="multiple" style="width:115px;">\
				</select>\
			</td>\
		</tr></table>\
	</div>';

var Template_modelOption = '<option value="${sftmodelname}">${sftmodeldes}</option>';

var Template_MessageBox = '\
<div title="' + Locale["vmcharge.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';