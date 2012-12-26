var Template_AppMgrTabs = '\
<div id="${id}">\
	<ul>\
		<li><a href="#tab-publishapp">' + Locale["appmgr.template.tabs.publish"] + '</a></li>\
		<li><a href="#tab-listapp">' + Locale["appmgr.template.tabs.list"] + '</a></li>\
	</ul>\
	<div id="tab-publishapp">\
	</div>\
	<div id="tab-listapp">\
	</div>\
</div>\
';
var Template_PublishApp = '\
	<div>\
		<h3>Step.1: ' + Locale["appmgr.label.image.choose"] + '</h3>\
		<div id="vmImageListDiv" class="line-10">\
		</div>\
		<h3>Step.2: ' + Locale["appmgr.label.app.info"] + '</h3>\
		<div id="createAppItemDiv" class="line-10">\
		</div>\
		<h3>Step.3: ' + Locale["appmgr.label.resource.config"] + '</h3>\
		<div id="resourceConfigDiv" class="line-10">\
		</div>\
		<h3>Step.4: ' + Locale["appmgr.label.modeltype.choose"] + '</h3>\
		<div id="vmChargeListDiv" class="line-10">\
		</div>\
	</div>\
';
var Template_AppList = '\
	<div id="publishedAppListDiv">\
	</div>\
	<div id="resourceConfigListDiv" >\
	</div>\
';

var Template_PublishedAppList = '\
	<table id="${id}">\
	<colgroup>\
	<col class="appCheck"/>\
	<col class="appName"/>\
	<col class="appIntro"/>\
	<col class="appImage"/>\
	<col class="appStatus"/>\
	<col class="appOperation"/>\
	</colgropup>\
	<thead>\
		<tr>\
			<th colspan="6" align="left" style="text-align:left;font-weight:normal;padding-bottom:4px;" >' + Locale["appmgr.label.header.selectzone"] + ': \
				<select id="filtermodel_app" style="width:120px;" name="filter_zone_app">\
				</select>\
			</th>\
		</tr>\
		</tr>\
		<tr  class="rowConfig">\
			<th class="appCheck"></th>\
			<th class="appName">' + Locale["appmgr.label.app.name"] + '</th>\
			<th class="appIntro">' + Locale["appmgr.label.app.introduction"] + '</th>\
			<th class="appImage">' + Locale["appmgr.label.image.name"] + '</th>\
			<th class="appStatus">' + Locale["appmgr.label.app.status"] + '</th>\
			<th class="appOperation">' + Locale["appmgr.label.app.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	</tfoot>\
	</table>\
	';

var Template_PublishedAppRow = '\
<tr class="appRow">\
	<input type="hidden" zmlm:item="zone" value="${zone}"/>\
	<input type="hidden" zmlm:item="softwareid" value="${softwareid}"/>\
	<input type="hidden" zmlm:item="softwarename" value="${softwarename}"/>\
	<input type="hidden" zmlm:item="softwarestatus" value="${softwarestatus}"/>\
	<td class="appCheck" ><input type="radio" name="softwareid" value="${softwareid}"/></td>\
	<td class="appName" title="${softwarename}">${softwarename}</td>\
	<td class="appIntro" title="${softwareintroduction}">${softwareintroduction}</td>\
	<td class="appImage" title="${softwareimage}">${softwareimage}</td>\
	<td class="appStatus {{if statusStyle(softwarestatus)}} green {{/if}}" >${localOperation(softwarestatus)}</td>	\
	<td class="appOperation">\
		<a href="#" onclick="removeApp(this);return false;">' + Locale["appmgr.label.operation.remove"] + '</a><span style="cursor:default">&nbsp;&nbsp;&nbsp;&nbsp;</span>\
		<a href="#" onclick="changeAppStatus(this,0);return false;">' + Locale["appmgr.label.operation.hide"] + '</a><span style="cursor:default">&nbsp;&nbsp;/&nbsp;&nbsp;</span>\
		<a href="#" onclick="changeAppStatus(this,1);return false;">' + Locale["appmgr.label.operation.show"] + '</a>\
	</td>\
</tr>\
';

var Template_ResourceConfigList = '\
	<h3>' + Locale["appmgr.label.resource.config"] + ':</h3>\
	<div class="line-7">\
	<table id="${id}" >\
	<colgroup>\
	<col class="rsdesc"/>\
	<col class="rsmaxcpu"/>\
	<col class="rsstartcpu"/>\
	<col class="rsmaxmem"/>\
	<col class="rsstartmem"/>\
	<col class="rsname"/>\
	<col class="rsprice"/>\
	<col class="rsoperation"/>\
	</colgroup>\
	<thead>\
		<tr  class="rowConfig">\
			<th class="rsdesc">' + Locale["appmgr.label.resource.desc"] + '</th>\
			<th class="rsmaxcpu">' + Locale["appmgr.label.resource.maxcpu"] + '</th>\
			<th class="rsstartcpu">' + Locale["appmgr.label.resource.startcpu"] + '</th>\
			<th class="rsmaxmem">' + Locale["appmgr.label.resource.maxmem"] + '</th>\
			<th class="rsstartmem">' + Locale["appmgr.label.resource.startmem"] + '</th>\
			<th class="rsname">' + Locale["appmgr.label.charge.desc"] + '</th>\
			<th class="rsprice">' + Locale["appmgr.label.charge.price"] + '</th>\
			<th class="rsoperation">' + Locale["appmgr.label.app.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	</tfoot>\
	</table>\
	</div>\
	';

var Template_ResourceConfigRow = '\
<tr class="resourceConfigRow">\
	<input zmlm:item="sftresid" type="hidden" value="${sftresid}" />\
	<input zmlm:item="sftresdes" type="hidden" value="${sftresdes}" />\
	<td class="rsdesc" title="${sftresdes}">${sftresdes}</td>\
	<td class="rsmaxcpu" title="${sftresmaxcpu}">${sftresmaxcpu}</td>\
	<td class="rsstartcpu" title="${sftresdefcpu}">${sftresdefcpu}</td>\
	<td class="rsmaxmem" title="${sftresmaxmem}">${sftresmaxmem}</td>	\
	<td class="rsstartmem" title="${sftresdefmem}">${sftresdefmem}</td>	\
	<td class="rsname" title="${vmplandes}">${vmplandes}</td>	\
	<td class="rsprice" title="${vmplanprice}">${vmplanprice.toFixed(2)}</td>	\
	<td class="rsoperation">\
		<a href="#" onclick="removeAppResource(this);return false;">' + Locale["appmgr.label.operation.remove"] + '</a>\
	</td>\
</tr>\
';

var Template_VMChargeTypeList = '\
	<table id="${id}">\
	<colgroup>\
	<col class="appCheck"/>\
	<col class="vmChargeName"/>\
	<col class="vmChargeDesc"/>\
	<col class="vmChargePrice"/>\
	<col class="vmChargeZone"/>\
	</colgroup>\
	<thead>\
		<tr class="rowConfig">\
			<th class="appCheck"></th>\
			<th class="vmChargeName">' + Locale["appmgr.label.charge.name"] + '</th>\
			<th class="vmChargeDesc">' + Locale["appmgr.label.charge.desc"] + '</th>\
			<th class="vmChargePrice">' + Locale["appmgr.label.charge.price"] + '</th>\
			<th class="vmChargeZone">' + Locale["appmgr.label.header.selectzone"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	<tr>\
	<td colspan="5">\
	<button onclick="publishApp()">' + Locale["appmgr.label.table.publish"] + '</button>\
	<button onclick="clearAppForm()">' + Locale["appmgr.label.operation.clear"] + '</button>\
	</td>\
	</tr>\
	</tfoot>\
	</table>\
';

var Template_VMChargeTypeRow = '\
	<tr class="vmChargeRow">\
		<td class="appCheck"><input type="checkbox" zmlm:item="vmplanid" value="${vmplanid}"/></td>\
		<td class="vmChargeName" title="${planname}">${planname}</td>\
		<td class="vmChargeDesc" title="${description}">${description}</td>\
		<td class="vmChargePrice" title="${subscriptionfee.toFixed(2)}">${subscriptionfee.toFixed(2)}</td>\
		<td class="vmChargeZone" >${zonedisplay}</td>	\
	</tr>\
';
var Template_ResourceConfig = '\
<table id="${id}">\
	<tbody>\
	<tr class="rowConfig">\
		<td class="dialogLineLeft">' + Locale["appmgr.label.resource.desc"] + ':</td>\
		<td class="dialogLineRight">\
			<input zmlm:item="resdescription" type="text" style="width:120px;" value=""/>\
		</td>\
		<td class="dialogLineLeft">' + Locale["appmgr.label.resource.startcpu"] + ':</td>\
		<td class="dialogLineRight">\
			<input zmlm:item="defaultcpu" type="text" style="width:80px; " value=""/>\
		</td>\
		<td class="dialogLineLeft">' + Locale["appmgr.label.resource.maxcpu"] + ':</td>\
		<td class="dialogLineRight">\
			<input zmlm:item="maxcpu" type="text" style="width:80px; " value=""/>\
		</td>\
		<td class="dialogLineLeft">' + Locale["appmgr.label.resource.startmem"] + ':</td>\
		<td class="dialogLineRight">\
			<input zmlm:item="defaultmem" type="text" style="width:80px; " value=""/>\
		</td>\
		<td class="dialogLineLeft">' + Locale["appmgr.label.resource.maxmem"] + ':</td>\
		<td class="dialogLineRight">\
			<input zmlm:item="maxmem" type="text" style="width:80px; " value=""/>\
		</td>\
	</tr>\
	</tbody>\
</table>';
var Template_CreateAppItem = '\
	<table id="${id}">\
		<tbody>\
		<tr class="rowConfig">\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.name"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="sftname" type="text" style="width:120px;" value=""/>\
			</td>\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.desc"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="sftdescription" type="text" style="width:170px;" value=""/>\
			</td>\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.introduction"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="sftintroduction" type="text" style="width:170px;" value=""/>\
			</td>\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.price"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="sftprice" type="text" value=""/></td>\
		</tr>\
		<tr><td colspan="8" class="trSplitter"></td></tr>\
		<tr class="rowConfig line-5">\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.modeltype"] + ':</td>\
			<td class="dialogLineRight">\
				<select id="sftmodeltype" zmlm:item="sftmodeltype" style="width:188px;">\
				</select>\
			</td>\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.modeltypeintro"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="sftpricingdes" type="text" style="width:170px;" value=""/>\
			</td>\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.total"] + ':</td>\
			<td class="dialogLineRight">\
				<input zmlm:item="sftlicenseamount" type="text" value=""/> \
			</td>\
			<td class="dialogLineLeft">' + Locale["appmgr.label.app.length"] + ':</td>\
			<td class="dialogLineRight">\
			<input zmlm:item="sftlicenselength" type="text" value=""/></td>\
		</tr>\
		</tbody>\
	</table>';

var Template_VMImageList = '\
	<table id="${id}">\
	<colgroup>\
	<col class="appCheck"/>\
	<col class="vmListPath"/>\
	<col class="vmListFolder"/>\
	<col class="vmListName"/>\
	<col class="vmListOS"/>\
	<col class="vmListSize"/>\
	<col class="vmListType"/>\
	</colgroup>\
	<thead>\
		<tr class="rowConfig">\
			<th class="appCheck"></th>\
			<th class="vmListPath">' + Locale["appmgr.vmimage.label.header.path"] + '</th>\
			<th class="vmListFolder">' + Locale["appmgr.vmimage.label.header.folder"] + '</th>\
			<th class="vmListName">' + Locale["appmgr.vmimage.label.header.name"] + '</th>\
			<th class="vmListOS">' + Locale["appmgr.vmimage.label.header.os"] + '</th>\
			<th class="vmListSize">' + Locale["appmgr.vmimage.label.header.size"] + '</th>\
			<th class="vmListType">' + Locale["appmgr.vmimage.label.header.type"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	</table>\
';

var Template_VMImagekRow = '\
<tr class="vmImagekRow">\
	<td class="appCheck"><input type="radio" zmlm:item="imageid" value="${imageid}"/></td>\
	<td class="vmListPath" title="${imagepath}">${imagepath}</td>\
	<td class="vmListFolder" title="${imagefolder}">${imagefolder}</td>\
	<td class="vmListName" title="${imagename}">${imagename}</td>\
	<td class="vmListOS" title="${imageos}">{{html formatOstype(imageos)}}</td>	\
	<td class="vmListSize">${formatSize(imagesize)}</td>\
	<td class="vmListType" title="${imagetype}">${imagetype}</td>\
</tr>\
';

var Template_modelOption = '<option value="${sftmodelname}">${sftmodeldes}</option>';

var Template_MessageBox = '\
<div title="' + Locale["appmgr.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';