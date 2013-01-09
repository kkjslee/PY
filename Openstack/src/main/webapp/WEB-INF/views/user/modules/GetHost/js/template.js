// JavaScript Document
// Author: Bill, 2012
 /*<!--#include virtual="../../../config.shtml" -->*/

var Template_MainPanel=[
'<div id="${id}">',
	'<div style="margin-bottom:5px;font-size:12px;padding:5px;" class="ui-widget-content ui-corner-all">',
		Locale["gethost.template.tips"],
	'</div>',
	
	// Data Center
	'<div id="datacenterpanel" name="datacenterpanel" class="ui-widget-content ui-corner-all step-panel">',
		'<div class="orderTitle">', Locale["gethost.panel.title.datacenter"], '</div>',
		'<div zmlm:item="datacenterList" class="List"></div>',
	'</div>',
	
	// Software
	'<div id="softwarepanel" name="softwarepanel" class="ui-widget-content ui-corner-all step-panel step-panel-hidden">',
		'<div class="orderTitle">', Locale["gethost.panel.title.software"], '</div>',
		
		'<table class="List" style="margin-bottom:0;">',
			'<thead>',
				'<tr>',
					'<th><span class="softwareName RowHeader">', Locale["gethost.template.software.name"], '</span></th>',
					'<th><span class="softwareDesc RowHeader">', Locale["gethost.template.software.desc"], '</span></th>',
				'</tr>',
			'</thead>',
			'<tbody zmlm:item="softwareList"></tbody>',
		'</table>',
	'</div>',
	
	// Hardware
	'<div id="hardwarepanel" name="hardwarepanel" class="ui-widget-content ui-corner-all step-panel step-panel-hidden">',
		'<div class="orderTitle">', Locale["gethost.panel.title.hardware"], '</div>',
		
		'<table class="List" style="margin-bottom:0;">',
			'<thead>',
				'<tr>',
					'<th><span class="hardwareDesc RowHeader">', Locale["gethost.template.hardware.type"], '</span></th>',
					'<th><span class="hardwareCpu RowHeader">', Locale["gethost.template.hardware.dcpu"], '</span></th>',
					'<th><span class="hardwareMaxCpu RowHeader">', Locale["gethost.template.hardware.mcpu"], '</span></th>',
					'<th><span class="hardwareMem RowHeader">', Locale["gethost.template.hardware.dmem"], '</span></th>',
					'<th><span class="hardwareMaxMem RowHeader">', Locale["gethost.template.hardware.mmem"], '</span></th>',
				'</tr>',
			'</thead>',
			'<tbody zmlm:item="hardwareList"></tbody>',
		'</table>',
	'</div>',
	
	// Plan
	'<div id="planpanel" name="planpanel" class="ui-widget-content ui-corner-all step-panel step-panel-hidden">',
		'<div class="orderTitle">', Locale["gethost.panel.title.plan"], '</div>',
		
		'<table class="List" style="margin-bottom:0;">',
			'<thead>',
				'<tr>',
					'<th><span class="planName RowHeader">', Locale["gethost.template.plan.name"], '</span></th>',
					'<th><span class="planDesc RowHeader">', Locale["gethost.template.plan.desc"], '</span></th>',
					'<th><span class="planPrice RowHeader">', Locale["gethost.template.plan.price"], '</span></th>',
					'<th><span class="planCpu RowHeader">', Locale["gethost.template.plan.cpuprice"], '</span></th>',
					'<th><span class="planMem RowHeader">', Locale["gethost.template.plan.memprice"], '</span></th>',
					'<th><span class="planNet RowHeader">', Locale["gethost.template.plan.netprice"], '</span></th>',
				'</tr>',
			'</thead>',
			'<tbody zmlm:item="planList"></tbody>',
		'</table>',
	'</div>',
	
	// VM Name
	'<div id="vmnamepanel" name="vmnamepanel" class="ui-widget-content ui-corner-all step-panel step-panel-hidden">',
		'<div class="orderTitle">', Locale["gethost.panel.title.naming"], '</div>',
		
		'<div zmlm:item="vmname" class="List" style="padding:5px;">',
			'<span>', Locale["gethost.template.naming.name"], '</span>',
			'<input id="vmname" style="font-size:16px;text-align:center;" maxlength="<!--#echo var="MaxLength_Instance"-->" class="ui-widget-content ui-corner-all bgyellow" />',
			'<button id="checkvmname">', Locale["gethost.template.naming.btncheck"], '</button>',
			'<label style="color:#aaa;">', Locale["gethost.template.naming.tips"], '</label>',
		'</div>',
	'</div>',
	
	// Additional Settings
	'<div id="additionalpanel" name="additionalpanel" class="ui-widget-content ui-corner-all step-panel step-panel-hidden">',
		'<div class="orderTitle">', Locale["gethost.panel.title.additional"], '</div>',
		
		// Public Network IP
		'<div id="networkpanel" name="networkpanel" style="margin:10px;background:#fff;" class="ui-widget-content ui-corner-all">',
			'<div class="orderTitle">', Locale["gethost.template.additional.network.subtitle"], '</div>',
		
			'<div zmlm:item="network" class="List" style="padding:5px;">',
				'<span>', Locale["gethost.template.additional.network.needed"], '</span>',
				'<span style="display:inline-block; margin:0 10px;">',
					'<input id="network_no" value="false" name="network" type="radio" checked/>',
					'<label for="network_no">', Locale["gethost.template.additional.network.needed.no"], '</label>',
				'</span>',
				'<span style="display:inline-block; margin:0 10px;">',
					'<input id="network_yes" value="true" name="network" type="radio" />',
					'<label for="network_yes">', Locale["gethost.template.additional.network.needed.yes"], '</label>',
				'</span>',
				'<span style="display:inline-block; margin:0 10px;">',
					'<div style="color:red;" id="ippricespan"></div>',
				'</span>',
			'</div>',
		
			// Public Network Type
			'<div id="networkdetailpanel" name="networkdetailpanel" class="step-panel-hidden">',
				'<div zmlm:item="networktype" class="List" style="padding:5px;">',
					'<span>', Locale["gethost.template.additional.network.type"], '</span>',
					'<span style="display:inline-block; margin:0 10px;">',
						'<input id="network_public" value="web" name="networktype" type="radio" />',
						'<label for="network_public">', Locale["gethost.template.additional.network.type.public"], '</label>',
					'</span>',
					'<span style="display:inline-block; margin:0 10px;">',
						'<input id="network_private" value="personal" name="networktype" type="radio" />',
						'<label for="network_private">', Locale["gethost.template.additional.network.type.private"], '</label>',
					'</span>',
				'</div>',
			'</div>',
		'</div>',
	
		// Volume
		'<div id="volumepanel" name="volumepanel" style="margin:10px;background:#fff;" class="ui-widget-content ui-corner-all step-panel">',
			'<div class="orderTitle">', Locale["gethost.template.additional.volume.subtitle"], '</div>',
			
			'<div zmlm:item="volume" class="List" style="padding:5px;">',
				'<span>', Locale["gethost.template.additional.volume.needed"], '</span>',
				'<span style="display:inline-block; margin:0 10px;">',
					'<input id="volume_no" value="false" name="volume" type="radio" checked/>',
					'<label for="volume_no">', Locale["gethost.template.additional.volume.needed.no"], '</label>',
				'</span>',
				'<span style="display:inline-block; margin:0 10px;">',
					'<input id="volume_yes" value="true" name="volume" type="radio" />',
					'<label for="volume_yes">', Locale["gethost.template.additional.volume.needed.yes"], '</label>',
				'</span>',
				'<span style="display:inline-block; margin:0 10px;">',
					'<div style="color:red;" id="volumepricespan"></div>',
				'</span>',
			'</div>',
	
			// Volume Size & Name
			'<div id="volumedetailpanel" name="volumedetailpanel" class="step-panel-hidden">',
				'<div zmlm:item="volumename" class="List" style="padding:5px;">',
					'<span>', Locale["gethost.template.additional.volume.name"], '</span>',
					'<input id="volumename" style="font-size:16px;text-align:center;" maxlength="<!--#echo var="MaxLength_Volume"-->" class="ui-widget-content ui-corner-all bgyellow" />',
					'<label style="color:#aaa;">', Locale["gethost.template.additional.volume.name.tips"], '</label>',
				'</div>',
				
				'<div zmlm:item="volumesize" class="List" style="padding:5px;">',
					'<span>', Locale["gethost.template.additional.volume.size"], '</span>',
					'<span zmlm:item="volumesizeList" class="List"><select id="volumesize"></select></span>',
				'</div>',
			'</div>',
		'</div>',
		
		'<div style="margin:10px;text-align:center;">',
			'<button onclick="validateOrder()">', Locale["gethost.template.button.order.submit"], '</button>',
		'</div>',
		
	'</div>',
'</div>'
].join("");

var Template_SoftwareRow=[
'<tr class="Row hidable">',
	'<input name="softwareId" type="hidden" value="${id}" />',
	'<input name="softwareName" type="hidden" value="${SoftwareName}" />',
	'<td><span class="RowCell softwareName">{{html formatOSIcon(SoftwareName)}} ${SoftwareName}</span></td>',
	'<td><span class="RowCell softwareDesc">${Description}</span></td>',
'</tr>'
].join("");

var Template_HardwareRow=[
'<tr class="Row hidable">',
	'<input name="hardwareId" type="hidden" value="${resourceid}" />',
	'<input name="hardwareMaxCpu" type="hidden" value="${maxcpu}" />',
	'<input name="hardwareCpu" type="hidden" value="${cpu}" />',
	'<input name="hardwareMaxMem" type="hidden" value="${maxmemory}" />',
	'<input name="hardwareMem" type="hidden" value="${memory}" />',
	'<td><span class="RowCell hardwareDesc">${description}</span></td>',
	'<td><span class="RowCell hardwareCpu">${cpu}</span></td>',
	'<td><span class="RowCell hardwareMaxCpu">${maxcpu}</span></td>',
	'<td><span class="RowCell hardwareMem">{{html formatSize(memory*1024*1024, 2)}}</span></td>',
	'<td><span class="RowCell hardwareMaxMem">{{html formatSize(maxmemory*1024*1024, 2)}}</span></td>',
'</tr>'
].join("");

var Template_PlanRow=[
'<tr class="Row hidable">',
	'<input name="softwareresourceid" type="hidden" value="${resourceid}" />',
	'<input name="planid" type="hidden" value="${vmplanid}" />',
	'<input name="onceprice" type="hidden" value="${price}" />',
	'<input name="plancpu" type="hidden" value="${cpufee}" />',
	'<input name="planmem" type="hidden" value="${memfee}" />',
	'<input name="plannet" type="hidden" value="${netfee}" />',
	'<td><span class="RowCell planName">${planname}</span></td>',
	'<td><span class="RowCell planDesc">${plandesc}</span></td>',
	'<td><span class="RowCell planPrice">{{html formatPrice(price)}}</span></td>',
	'<td><span class="RowCell planCpu">{{html formatPrice(cpufee)}}</span></td>',
	'<td><span class="RowCell planMem">{{html formatPrice(memfee)}}</span></td>',
	'<td><span class="RowCell planNet">{{html formatPrice(netfee)}}</span></td>',
'</tr>'
].join("");

var Template_ConfirmDialog=[
'<div title="', Locale["gethost.template.dialog.confirm.title"], '" class="confirmDialog">',
	'<div style="margin-bottom:2px;font-size:12px;padding:5px;display:inline-block;width:90%;text-align:left;" class="ui-widget-content ui-corner-all ui-state-highlight">',
		'<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>',
		Locale["gethost.template.tips.confirm"],
	'</div>',
	
	'<table class="ui-widget-content ui-corner-all">',
		'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["gethost.template.dialog.confirm.subtitle.software"],'</td></tr>',
		
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.software"], '</td><td class="value">${softwarename}</td></tr>',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.vmname"], '</td><td class="value">${vmname}</td></tr>',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.datacenter"], '</td><td class="value">${datacenterlabel}</td></tr>',
		
		'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["gethost.template.dialog.confirm.subtitle.hardware"],'</td></tr>',
		
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.dcpu"], '</td><td class="value">${hardwareCpu}</td></tr>',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.mcpu"], '</td><td class="value">${hardwareMaxCpu}</td></tr>',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.dmem"], '</td><td class="value">${hardwareMem}</td></tr>',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.mmem"], '</td><td class="value">${hardwareMaxMem}</td></tr>',
		
		'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["gethost.template.dialog.confirm.subtitle.plan"],'</td></tr>',
		
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.plan"], '</td><td class="value">${planName}</td></tr>',
		'{{if isPagOrder(planid)==false}}',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.price"], '</td><td class="value">${planPrice}</td></tr>',
		'{{else}}',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.cpuprice"], '</td><td class="value">${planCpu}</td></tr>',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.memprice"], '</td><td class="value">${planMem}</td></tr>',
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.netprice"], '</td><td class="value">${planNet}</td></tr>',
		'{{/if}}',
		
		
		'{{if enterprise==false}}',
			'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["gethost.template.dialog.confirm.subtitle.network"],'</td></tr>',
			
			'<tr><td class="label">', Locale["gethost.template.dialog.confirm.hasnetwork"], '</td><td class="value">{{html formatBoolean(hasNetwork)}}</td></tr>',
			'{{if hasNetwork=="true"}}',
				'<tr><td class="label">', Locale["gethost.template.dialog.confirm.networktype"], '</td><td class="value">{{html formatNetworkType(networkType)}}</td></tr>',
			'{{/if}}',
		
			'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["gethost.template.dialog.confirm.subtitle.volume"],'</td></tr>',
			
			'<tr><td class="label">', Locale["gethost.template.dialog.confirm.hasvolume"], '</td><td class="value">{{html formatBoolean(hasVolume)}}</td></tr>',
			'{{if hasVolume=="true"}}',
				'<tr><td class="label">', Locale["gethost.template.dialog.confirm.volumename"], '</td><td class="value">${volumeName}</td></tr>',
				'<tr><td class="label">', Locale["gethost.template.dialog.confirm.volumesize"], '</td><td class="value">{{html formatVolumeSize(volumeSize)}}</td></tr>',
			'{{/if}}',
		'{{/if}}',
		
		'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["gethost.template.dialog.confirm.subtitle.total"],'</td></tr>',
		
		
		'<tr><td class="label">', Locale["gethost.template.dialog.confirm.fee.vm"], '</td><td class="value">{{html planPrice}}</td></tr>',
		'{{if enterprise==false}}',
			'{{if hasNetwork=="true"}}',
			'<tr><td class="label">', Locale["gethost.template.dialog.confirm.fee.network"], '</td><td class="value">{{html calIpPrice(ipprice, provider)}}</td></tr>',
			'{{/if}}',
			'{{if hasVolume=="true"}}',
			'<tr><td class="label">', Locale["gethost.template.dialog.confirm.fee.volume"], '</td><td class="value">{{html (volumeprice*volumeSize)}}</td></tr>',
			'{{/if}}',
		'{{/if}}',
		'<tr>',
			'<td class="label">', Locale["gethost.template.dialog.confirm.fee.ordercount"], '</td><td class="value">',
				'<select name="orderAmount">', dumpOrderAmountList(), '</select>',
			'</td>',
		'</tr>',
		'<tr style="height:36px;">',
			'<td class="label emphasis">', Locale["gethost.template.dialog.confirm.fee.total"], '</td>',
			'<td class="value emphasis bgyellow" name="feeTotal" style="color:red;"></td>',
		'</tr>',
		
	'</table>',
	
'</div>'
].join("");

var Template_SaveOrderDialog=[
'<div id="${id}" title="', Locale["gethost.template.dialog.saveorder.title"], '" class="saveOrderDialog">',
	'<table class="ui-widget-content ui-corner-all">',
		'<tr>',
			'<td class="label">', Locale["gethost.template.dialog.saveorder.template.name"], '</td>',
			'<td class="value"><input name="templatename" class="ui-widget-content ui-corner-all bgyellow" style="width:95%;" maxlength="<!--#echo var="MaxLength_OrderTemplateName"-->"></td>',
		'</tr>',
		
		'<tr>',
			'<td class="label">', Locale["gethost.template.dialog.saveorder.template.desc"], '</td>',
			'<td class="value"><input name="templatedesc" class="ui-widget-content ui-corner-all bgyellow" style="width:95%;" maxlength="<!--#echo var="MaxLength_OrderTemplateDesc"-->"></td>',
		'</tr>',
	'</table>',
'</div>'
].join("");

var Template_MessageBox=[
'<div title="'+Locale["gethost.dialog.title.tips"]+'">',
	'<p class="message">{{html message}}</p>',
'</div>'
].join("");







