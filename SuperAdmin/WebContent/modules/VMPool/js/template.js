// JavaScript Document
// Author: Bill, 2011

/**
 * Template - Zone Option Group
 */

var Template_VMPoolTabs=[
'<div page id="${id}">',
	'<ul>',
	'</ul>',
'</div>'
].join("");

var Template_TabContent=[
'<div>',
	'<span class="headerRow">',
		'<span class="hostName hostListHeader">'+Locale["vmpool.template.column.host.name"]+'</span>',
		'<span class="hostZone hostListHeader">'+Locale["vmpool.template.column.host.zone"]+'</span>',
		'<span class="hostStatus hostListHeader">'+Locale["vmpool.template.column.host.status"]+'</span>',
		'<span class="hostCpuModel hostListHeader">'+Locale["vmpool.template.column.host.cpumodel"]+'</span>',
		'<span class="hostIp hostListHeader">'+Locale["vmpool.template.column.host.ip"]+'</span>',
		'<span class="hostCpu hostListHeader">'+Locale["vmpool.template.column.host.cpu"]+'</span>',
		'<span class="hostMem hostListHeader">'+Locale["vmpool.template.column.host.mem"]+'</span>',
		'<span class="hostSpace hostListHeader">'+Locale["vmpool.template.column.host.space"]+'</span>',
	'</span>',
	'<span zmlm:item="hostList" class="hostList"></span>',
	'<span class="footerRow">',
		'<button onclick="window.location.reload()">'+Locale["vmpool.template.button.refresh"]+'</button>',
	'</span>',
'</div>'
].join("");


var Template_HostRow=[
'<h3 style="margin-top:0;">',
	'<a href="#">',
		'<span class="hostRow">',
			'<input type="hidden" name="hostZone" value="${zone}" />',
			'<input type="hidden" name="hostEntry" value="${ip}" />',
			'<span class="hostCell hostName textCollapse" title="${name}">${name}</span>',
			'<span class="hostCell hostZone textCollapse" title="${zone}">${zone}</span>',
			'<span class="hostCell hostStatus textCollapse">',
				'<select defVal="{{if status=="offline"}}offline{{else}}online{{/if}}" style="margin:0;padding:0;" onchange="hostStatus(this)" onclick="javascript:function(event){event.preventDefault();event.stopPropagation();}">',
					'<option value="offline" {{if status=="offline"}}selected{{/if}}>Offline</option>',
					'<option value="online" {{if status=="up and running"}}selected{{/if}}>Up & Running</option>',
				'</select>',
			'</span>',
			'<span class="hostCell hostCpuModel textCollapse">${cpumodel}</span>',
			'<span class="hostCell hostIp textCollapse">${ip}</span>',
			'<span class="hostCell hostCpu textCollapse" title="'+Locale["vmpool.template.label.cpu"].sprintf("${usedcpu}", "${maxcpu}")+'">',
				'<span name="pbcpu" class="pbrate"></span>${usedcpu} / ${maxcpu}',
			'</span>',
			'<span class="hostCell hostMem textCollapse" title="'+Locale["vmpool.template.label.mem"].sprintf("${usedmem}", "${maxmem}")+'">',
				'<span name="pbmem" class="pbrate"></span>${usedmem} / ${maxmem}',
			'</span>',
			'<span class="hostCell hostSpace textCollapse" title="'+Locale["vmpool.template.label.vol"].sprintf("${usedspace}", "${totalspace}")+'">',
				'<span name="pbspace" class="pbrate"></span>${usedspace} / ${totalspace}',
			'</span>',
			'<span class="hostCell hostOperation textCollapse">',
				'<select style="margin:0;padding:0;" onchange="hostOper(this)" onclick="javascript:function(event){event.preventDefault();event.stopPropagation();}">',
					'<option value="">'+Locale["vmpool.template.option.recover"]+'</option>',
					'<option value="reconstruct">'+Locale["vmpool.template.option.reconstruct"]+'</option>',
					'<option value="recoverstate">'+Locale["vmpool.template.option.recoverstate"]+'</option>',
				'</select>',
			'</span>',
		'</span>',
	'</a>',
'</h3>',
'<div style="margin:0px;margin-bottom:0;">',
	'<div style="margin-bottom:16px;text-align:center;white-space:nowrap;background:none;">',
		'<table style="width:100%;"><tr>',
			'<td style="width:33%;text-align:center;"><span name="cpuchart" style="display:inline-block;border: 1px solid silver;"></span></td>',
			'<td style="width:33%;text-align:center;"><span name="memchart" style="display:inline-block;border: 1px solid silver;"></span></td>',
			'<td><span name="volchart" style="display:inline-block;border: 1px solid silver;"></span></td>',
		'</tr></table>',
	'</div>',
	'<span class="headerRow">',
		'<span class="vmName hostListHeader">'+Locale["vmpool.template.column.vm.name"]+'</span>',
		'<span class="vmOwner hostListHeader">'+Locale["vmpool.template.column.vm.owner"]+'</span>',
		'<span class="vmOwner hostListHeader">'+Locale["vmpool.template.column.vm.status"]+'</span>',
	'</span>',
	'<span class="vmList">',
		'{{each vms}}',
		'<span class="vmRow">',
			'<span class="vmCell vmName textCollapse">${vmname}</span>',
			'<span class="vmCell vmOwner textCollapse">${vmowner}</span>',
			'<span class="vmCell vmStatus textCollapse">{{html formatStatus(vmstatus)}}</span>',
		'</span>',
		'{{/each}}',
	'</span>',
'</div>'
].join("");

var Template_MessageBox=[
'<div title="'+Locale["vmpool.dialog.title.tips"]+'">',
	'<p class="message">{{html message}}</p>',
'</div>'
].join("");







