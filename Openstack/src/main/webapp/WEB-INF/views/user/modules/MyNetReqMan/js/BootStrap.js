// JavaScript Document
// Author: Bill, 2011
 /*<!--#include virtual="../../../config.shtml" -->*/

var _DEBUG_=false;

var scriptToLoad=[
	"../../locale.jsp?_module=MyNetReqMan",
	"js/template.js"
];

$(function(){
	BootLoader.init();
	
	BootLoader.prepareScriptSerial(scriptToLoad, function(){
		registerTemplate();
		
		setup();
		
		initAjax();
		
		initUi();
	});
});

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function registerTemplate() {
	$.template("myreqPanelTemplate", Template_MyReqPanel);
	$.template("myreqRowTemplate", Template_MyReqRow);
	$.template("messageBoxTemplate", Template_MessageBox);
}

function setup() {
	$("#mainBody").empty();
	
	var panel=$.tmpl("myreqPanelTemplate", [{id:"myreqPanel"}]).appendTo("#mainBody");
	
	
	loadRequests();
}

function initUi() {
	// jquery ui init
	$("button").button();
	
	$("#banner").html(Locale["myreq.banner"]);
}

function initAjax() {
	jQuery.support.cors = true;
	
	$(document).ajaxStart(function(){
		$("#loadingIcon").show();
	}).ajaxStop(function(){
		$("#loadingIcon").hide();
	});
}

function printError(jqXHR, textStatus, errorThrown) {
	printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}

function printMessage(msg) {
	return $.tmpl("messageBoxTemplate", [{message: msg}]).appendTo("#mainBody").dialog({
		resizable: false,
		modal: true,
		buttons: [
			{
				text: Locale["myreq.dialog.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function showProcessingDialog(msg) {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+(msg || Locale["myreq.dialog.processing"])+"</div>").dialog({
		autoOpen: true,
		width: 240,
		height: 100,
		resizable: false,
		modal: true,
		closeOnEscape: false,
		open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
		buttons: {
		}
	});
	return view;
}


/* Module Specified */
function loadRequests() {
	var list=$("#myreqPanel").find("[zmlm\\\:item='myreqList']").empty();
	$("<em class='tipsLabel'>"+Locale["myreq.message.loading"]+"</em>").appendTo(list);
	
	$.ajax({
		type: "get",
		url: Server+"/RedDragonEnterprise/NetWorkServlet",
		cache: false,
		data: {
			methodtype: "fetchips",
			loginuser: getUsername()
		},
		success: function(data) {
			// clear table before inserting new data
			var dataTable=$(list).parents("table").first();
			$(dataTable).dataTable().fnDestroy();
			
			try{
				data=$.parseJSON(data);
				
				if(data.length>0){
					var datalist=$.tmpl("myreqRowTemplate", data).appendTo($(list).empty());
					
				}else{
					$("<em>"+Locale["myreq.message.no_data"]+"</em>").appendTo($(list).empty());
				}
				
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
			}
			
			// (re)initialize the table
			$(dataTable).dataTable(BootLoader.conf.dataTable(dataTable));
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function showLoadingIcon() {
	$("[name='loadingIcon']").show();
}
function hideLoadingIcon() {
	$("[name='loadingIcon']").hide();
}

function formatStatus(status) {
	if(status) {
		switch(status) {
			case "approved": return "<green>"+Locale["myreq.template.label.status.approved"]+"</green>";
			case "unapproved": return "<orange>"+Locale["myreq.template.label.status.pending"]+"</orange>";
			case "rejected": return Locale["myreq.template.label.status.rejected"];
		}
	}
	return status;
}

function formatInterval(interval) {
	var units=[
		Locale["myreq.template.label.interval.second"], 
		Locale["myreq.template.label.interval.minute"], 
		Locale["myreq.template.label.interval.hour"]
	];
	
	if(interval) {
		interval=parseInt(interval);
		
		var u=0;
		while(59<interval && u<(units.length-1)){
			interval/=60;
			u++;
		}
		
		return (u?interval.toFixed(0):interval)+" "+units[u];
	}
	
	return null;
}



