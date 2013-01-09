// JavaScript Document
// Author: Bill, 2012
 /*<!--#include virtual="../../../config.shtml" -->*/

var Template_MainPanel=[
'<div id="${id}">',
	'<div style="margin-bottom:5px;font-size:12px;padding:5px;" class="ui-widget-content ui-corner-all">',
		Locale["ordertemplate.template.tips"],
	'</div>',
	
	'<div name="customGridContainer"></div>',
	
	'<div name="defaultTemplateContainer"></div>',
	
	'<div name="myselfTemplateContainer"></div>',
	
'</div>'
].join("");

var Template_OrderGrid=[
'<div class="ui-widget-content ui-corner-all order-grid">',
	'<div class="title">',
		'<span>',
			'{{if templatename==null || templatename==""}}',
				Locale["ordertemplate.template.grid.tips.name.empty"],
			'{{else}}',
				'${templatename}',
			'{{/if}}',
		'</span>',
		
		'{{if removable==true}}',
		'<a href="#" onclick="removeOrderTemplate(this);return false;"><img class="remove-icon" src="css/image/delete.gif"></a>',
		'{{/if}}',
	'</div>',
	
	'<table>',
		'<tr>',
			'<td class="grid-icon">',
				'<span>{{html formatGridIcon(softwarename)}}</span>',
				
				'{{if prompt==true}}',
				'<span class="grid-medal"><img src="css/image/medal.png"></span>',
				'{{/if}}',
			'</td>',
			'<td class="col1">',
				'<table>',
					'<tr>',
						'<td>'+Locale["ordertemplate.template.grid.software.image"]+'</td>','<td><span>${softwarename}</span></td>',
					'</tr>',
					'<tr>',
						'<td>'+Locale["ordertemplate.template.grid.software.zone"]+'</td>','<td>${zonedisplay}</td>',
					'</tr>',
					'<tr>',
						'<td>'+Locale["ordertemplate.template.grid.plan.name"]+'</td>','<td>${vmplanname}</td>',
					'</tr>',
					'{{if enterprise==false}}',
						'<tr>',
							'<td>', Locale["ordertemplate.template.grid.network"], '</td>',
							'{{if needip==true}}',
								'<td>{{html formatNetworkType(iptype)}}</td>',
							'{{else}}',
								'<td>', Locale["ordertemplate.template.grid.network.none"], '</td>',
							'{{/if}}',
						'</tr>',
					'{{/if}}',
				'</table>',
			'</td>',
			'<td class="col2">',
				'<table>',
					'<tr>',
						'<td>', Locale["ordertemplate.template.grid.hardware.dcpu"], ' / ', Locale["ordertemplate.template.grid.hardware.mcpu"], '</td>','<td>${cpu} Core / ${maxcpu} Core</td>',
					'</tr>',
					'<tr>',
						'<td>', Locale["ordertemplate.template.grid.hardware.dmem"], ' / ', Locale["ordertemplate.template.grid.hardware.mmem"], '</td>','<td>{{html formatSize(memory*1024,2)}} / {{html formatSize(maxmemory*1024,2)}}</td>',
					'</tr>',
					'<tr>',
						'<td>', Locale["ordertemplate.template.grid.plan.fee"]+'</td>',
						'<td>', 
							'<span style="font-weight:bold;color:#777;margin-right:4px;font-size:14px;">'+Locale["ordertemplate.template.grid.fee.unit"]+'</span>',
							'<span>${vmplanprice}</span>',
						'</td>',
					'</tr>',
					'{{if enterprise==false}}',
						'<tr>',
							'<td>', Locale["ordertemplate.template.grid.volume"], '</td>',
							'{{if needvolume==true}}',
								'<td>${volumesize}</td>',
							'{{else}}',
								'<td>', Locale["ordertemplate.template.grid.volume.none"], '</td>',
							'{{/if}}',
						'</tr>',
					'{{/if}}',
				'</table>',
			'</td>',
		'</tr>',
		'<tr>',
			'<td class="button-field">',
				'<div class="ui-widget-content ui-corner-all ui-state-highlight" onclick="showConfirmDialog(this);return false;">',
					'<span class="midspan" >',
						'<span class="ui-icon ui-icon-cart" style="float:left;margin-right:.3em;"></span>',
						'<span>'+Locale["ordertemplate.template.grid.button.buy"]+'</span>',
					'</span>',
				'</div>',
			'</td>',
			'<td colspan="2">',
				'<div class="desc-field">',
					'{{if templatedes==null || templatedes==""}}',
						Locale["ordertemplate.template.grid.tips.notes.empty"],
					'{{else}}',
						'${templatedes}',
					'{{/if}}',
				'</div>',
			'</td>',
		'</tr>',
	'</table>',
'</div>'
].join("");

var Template_CustomGrid=[
'<div class="ui-widget-content ui-corner-all custom-grid" onclick="gotoCustomPage();">',
	'<table>',
		'<tr>',
			'<td class="custom-icon"><img src="css/image/custom.png" /></td>',
			'<td class="custom-text">'+Locale["ordertemplate.template.grid.button.custom"]+'</td>',
		'</tr>',
	'</table>',	
'</div>'
].join("");

var Template_ConfirmDialog=[
'<div title="', Locale["ordertemplate.template.dialog.confirm.title"], '" class="confirmDialog">',
	'<div style="margin-bottom:2px;font-size:12px;padding:5px;display:inline-block;width:90%;text-align:left;" class="ui-widget-content ui-corner-all ui-state-highlight">',
		'<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>',
		Locale["ordertemplate.template.tips.confirm"],
	'</div>',
	
	'<table class="ui-widget-content ui-corner-all">',
		'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["ordertemplate.template.dialog.confirm.subtitle.software"],'</td></tr>',
		
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.software"], '</td><td class="value">${softwarename}</td></tr>',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.vmname"], '</td><td class="value"><input class="ui-widget-content ui-corner-all bgyellow" name="vmname" /></td></tr>',
		'<tr><td colspan="2" style="text-align:center;color:#aaa;">'+Locale["ordertemplate.template.naming.vm.tips"]+'</td></tr>',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.datacenter"], '</td><td class="value">${zonedisplay}</td></tr>',
		
		'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["ordertemplate.template.dialog.confirm.subtitle.hardware"],'</td></tr>',
		
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.dcpu"], '</td><td class="value">${cpu}</td></tr>',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.mcpu"], '</td><td class="value">${maxcpu}</td></tr>',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.dmem"], '</td><td class="value">{{html formatSize(memory*1024, 2)}}</td></tr>',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.mmem"], '</td><td class="value">{{html formatSize(maxmemory*1024, 2)}}</td></tr>',
		
		'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["ordertemplate.template.dialog.confirm.subtitle.plan"],'</td></tr>',
		
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.plan"], '</td><td class="value">${vmplanname}</td></tr>',
		'{{if isPagOrder(vmplanid)==false}}',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.price"], '</td><td class="value">${vmplanprice}</td></tr>',
		'{{else}}',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.cpuprice"], '</td><td class="value">${cpuprice}</td></tr>',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.memprice"], '</td><td class="value">${memprice}</td></tr>',
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.netprice"], '</td><td class="value">${networkprice}</td></tr>',
		'{{/if}}',
		
		
		'{{if enterprise==false}}',
			'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["ordertemplate.template.dialog.confirm.subtitle.network"],'</td></tr>',
			
			'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.hasnetwork"], '</td><td class="value">{{html formatBoolean(needip)}}</td></tr>',
			'{{if needip==true}}',
				'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.networktype"], '</td><td class="value">{{html formatNetworkType(iptype)}}</td></tr>',
			'{{/if}}',
		
			'<tr><td colspan="2" class="subtitle ui-corner-all ui-widget-content ">', Locale["ordertemplate.template.dialog.confirm.subtitle.volume"],'</td></tr>',
			
			'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.hasvolume"], '</td><td class="value">{{html formatBoolean(needvolume)}}</td></tr>',
			'{{if needvolume==true}}',
				'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.volumename"], '</td><td class="value"><input class="ui-widget-content ui-corner-all bgyellow" name="volumename" /></td></tr>',
				'<tr><td colspan="2" style="text-align:center;color:#aaa;">'+Locale["ordertemplate.template.naming.volume.tips"]+'</td></tr>',
				'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.volumesize"], '</td><td class="value">{{html formatVolumeSize(volumesize)}}</td></tr>',
			'{{/if}}',
		'{{/if}}',
		
		'<tr>',
			'<td colspan="2" class="subtitle ui-corner-all ui-widget-content ">',
				Locale["ordertemplate.template.dialog.confirm.subtitle.total"],
			'</td>',
		'</tr>',
		
		
		'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.fee.vm"], '</td><td class="value">${vmplanprice}</td></tr>',
		'{{if enterprise==false}}',
			'{{if needip==true}}',
			'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.fee.network"], '</td><td class="value">{{html calIpPrice(ipprice, provider)}}</td></tr>',
			'{{/if}}',
			'{{if needvolume==true}}',
			'<tr><td class="label">', Locale["ordertemplate.template.dialog.confirm.fee.volume"], '</td><td class="value">{{html (volumeprice*volumesize)}}</td></tr>',
			'{{/if}}',
		'{{/if}}',
		'<tr>',
			'<td class="label">', Locale["ordertemplate.template.dialog.confirm.fee.ordercount"], '</td><td class="value">',
				'<select name="orderAmount">', dumpOrderAmountList(), '</select>',
			'</td>',
		'</tr>',
		'<tr style="height:36px;">',
			'<td class="label emphasis">', Locale["ordertemplate.template.dialog.confirm.fee.total"], '</td>',
			'<td class="value emphasis bgyellow" name="feeTotal" style="color:red;"></td>',
		'</tr>',
		
	'</table>',
	
'</div>'
].join("");

var Template_MessageBox=[
'<div title="'+Locale["ordertemplate.dialog.title.tips"]+'">',
	'<p class="message">{{html message}}</p>',
'</div>'
].join("");







