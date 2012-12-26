// JavaScript Document
// Author: Bill, 2012

var Template_MyReqPanel=[
'<div id="${id}">',
	'<div style="margin-bottom:5px;font-size:12px;padding:5px;" class="ui-widget-content ui-corner-all">',
		Locale["myreq.template.tips"],
	'</div>',
	
	'<table class="List" style="margin-bottom:250px;">',
		'<thead>',
			'<tr>',
				'<th><span class="myreqName RowHeader">'+Locale["myreq.template.myreq.name"]+'</span></th>',
				'<th><span class="myreqIp RowHeader">'+Locale["myreq.template.myreq.ip"]+'</span></th>',
				'<th><span class="myreqStatus RowHeader">'+Locale["myreq.template.myreq.status"]+'</span></th>',
				//'<th><span class="myreqOperation RowHeader">'+Locale["myreq.template.myreq.operation"]+'</span></th>',
			'</tr>',
		'</thead>',
		'<tbody zmlm:item="myreqList"></tbody>',
		'<tfoot style="text-align:right;">',
			'<tr>',
				'<td colspan="2" style="text-align:center;"></td>',
				'<td>',
					'<button onclick="loadRequests()">'+Locale["myreq.template.myreq.refresh"]+'</button>',
				'</td>',
			'</tr>',
		'</tfoot>',
	'</table>',
	
'</div>'
].join("");

var Template_MyReqRow=[
'<tr class="Row">',
	'<input name="myreqIp" type="hidden" value="${ip}" />',
	'<input name="myreqName" type="hidden" value="${name}" />',
	'<input name="myreqFreq" type="hidden" value="${frequency}" />',
	'<input name="myreqStatus" type="hidden" value="${status}" />',
	'<td><span class="RowCell myreqName">${zonename}</span></td>',
	'<td><span class="RowCell myreqIp">${ip}</span></td>',
	'<td><span class="RowCell myreqStatus">{{html formatStatus(ipstatus)}}</span></td>',
	//'<td><span class="RowCell myreqOperation">',
		//'<a href="#" onclick="return false;">'+Locale["myreq.template.myreq.remove"]+'</a>',
	//'</span></td>',
'</tr>'
].join("");

var Template_MessageBox=[
'<div title="'+Locale["myreq.dialog.title.tips"]+'">',
	'<p class="message">{{html message}}</p>',
'</div>'
].join("");







