// JavaScript Document
// Author: Bill, 2012

var Template_MonitorPanel=[
'<div id="${id}">',
	'<div style="margin-bottom:5px;font-size:12px;padding:5px;" class="ui-widget-content ui-corner-all">',
		Locale["monitor.template.tips"],
	'</div>',
	
	'<table class="List" style="margin-bottom:250px;">',
		'<thead>',
			'<tr>',
				'<th><span class="monitorName RowHeader">'+Locale["monitor.template.monitor.name"]+'</span></th>',
				'<th><span class="monitorIp RowHeader">'+Locale["monitor.template.monitor.ip"]+'</span></th>',
				'<th><span class="monitorFreq RowHeader">'+Locale["monitor.template.monitor.frequency"]+'</span></th>',
				'<th><span class="monitorStatus RowHeader">'+Locale["monitor.template.monitor.status"]+'</span></th>',
				'<th><span class="monitorOperation RowHeader">'+Locale["monitor.template.monitor.operation"]+'</span></th>',
			'</tr>',
		'</thead>',
		'<tbody zmlm:item="monitorList"></tbody>',
		'<tfoot style="text-align:right;">',
			'<tr>',
				'<td colspan="4" style="text-align:center;"></td>',
				'<td>',
					'<button onclick="showCreateMonitorDialog();">'+Locale["monitor.template.monitor.create"]+'</button>',
					'<button onclick="loadMonitors()">'+Locale["monitor.template.monitor.refresh"]+'</button>',
				'</td>',
			'</tr>',
		'</tfoot>',
	'</table>',
	
	/* Up-time Panel */
	'<div name="uptimePanel" class="uptimePanel">',
		'<div style="position:relative;margin:0px 2px;display:block;">',
		'<table>',
			'<tr>',
				'<td>',
					'<div name="uptimeTabs">',
						'<ul>',
							'<li><a href="#_history">'+Locale["monitor.template.tabs.history"]+'</a></li>',
							'<li><a href="#_summary">'+Locale["monitor.template.tabs.summary"]+'</a></li>',
							'<li><a href="#_logging">'+Locale["monitor.template.tabs.logging"]+'</a></li>',
						'</ul>',
						'<div id="_history" class="_history">',
							'<div class="ctrlBar"><img name="loadingIcon" src="css/image/progress.gif" /></div>',
							'<div id="_historyChart"><span class="tipsCenter">'+Locale["monitor.template.tabs.empty"]+'</span></div>',
						'</div>',
						'<div id="_summary" class="_summary">',
							'<div class="ctrlBar"><img name="loadingIcon" src="css/image/progress.gif" /></div>',
							'<div id="_summaryChart"><span class="tipsCenter">'+Locale["monitor.template.tabs.empty"]+'</span></div>',
						'</div>',
						'<div id="_logging" class="_logging">',
							'<div class="ctrlBar"><img name="loadingIcon" src="css/image/progress.gif" /></div>',
							'<div class="loggingContent">',
								'<table>',
									'<thead>',
										'<tr>',
											'<th>'+Locale["monitor.template.label.down"]+'</th>',
											'<th>'+Locale["monitor.template.label.back"]+'</th>',
											'<th>'+Locale["monitor.template.label.interval"]+'</th>',
										'</tr>',
									'</thead>',
									'<tbody>',
										'<tr>',
											'<td>'+Locale["monitor.template.label.foo"]+'</td>',
											'<td>'+Locale["monitor.template.label.foo"]+'</td>',
											'<td>'+Locale["monitor.template.label.foo"]+'</td>',
										'</tr>',
									'</tbody>',
								'</table>',
							'</div>',
						'</div>',
					'</div>',
				'</td>',
			'</tr>',
		'</table>',
		'</div>',
	'</div>',
'</div>'
].join("");

var Template_MonitorRow=[
'<tr class="Row">',
	'<input name="monitorIp" type="hidden" value="${ip}" />',
	'<input name="monitorName" type="hidden" value="${name}" />',
	'<input name="monitorFreq" type="hidden" value="${frequency}" />',
	'<input name="monitorStatus" type="hidden" value="${status}" />',
	'<td><span class="RowCell monitorName">${name}</span></td>',
	'<td><span class="RowCell monitorIp">${ip}</span></td>',
	'<td><span class="RowCell monitorFreq">${frequency} '+Locale["monitor.template.label.interval.minute"]+'</span></td>',
	'<td><span class="RowCell monitorStatus">{{html formatStatus(status)}}</span></td>',
	'<td><span class="RowCell monitorOperation">',
		'{{if status=="monitoring"}}',
			'<a href="#" onclick="updateMonitor(this, \'pause\');return false;">'+Locale["monitor.template.monitor.pause"]+'</a>',
		'{{else status=="pause"}}',
			'<a href="#" onclick="updateMonitor(this, \'monitoring\');return false;">'+Locale["monitor.template.monitor.resume"]+'</a>',
		'{{/if}}',
		'<a href="#" onclick="showUpdateMonitorDialog(this);return false;">'+Locale["monitor.template.monitor.modify"]+'</a>',
		'<a href="#" onclick="removeMonitor(this);return false;">'+Locale["monitor.template.monitor.remove"]+'</a>',
	'</span></td>',
'</tr>'
].join("");

var Template_MonitorDialog=[
'<div id="${id}" class="monitorDialog">',
	'<table>',
		'<tr><td>'+Locale["monitor.template.monitor.name"]+'</td></tr>',
		'<tr><td><input name="monitorName" type="text" class="ui-widget-content ui-corner-all" /></td></tr>',
		'<tr class="splitter"></tr>',
		'<tr><td>'+Locale["monitor.template.monitor.ip"]+'</td></tr>',
		'<tr>',
			'<td>',
				'<select name="monitorIp"></select>', // we insert options dynamically
			'</td>',
		'</tr>',
		'<tr class="splitter"></tr>',
		'<tr><td>'+Locale["monitor.template.monitor.frequency"]+'</td></tr>',
		'<tr>',
			'<td>',
				'<div name="monitorFreq"></div>', // we insert options according shtml
			'</td>',
		'</tr>',
		'<tr class="splitter"></tr>',
	'</table>',
'</div>'
].join("");

var Template_MessageBox=[
'<div title="'+Locale["monitor.dialog.title.tips"]+'">',
	'<p class="message">{{html message}}</p>',
'</div>'
].join("");







