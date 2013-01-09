// JavaScript Document
// Author: Bill, 2011

var _DEBUG_=false;

$(function(){
	registerTemplate();
	
	setup();
	
	initAjax();
	
	initUi();
});

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function registerTemplate() {
	$.template("logsPanelTemplate", Template_LogsPanel);
	$.template("logsRowTemplate", Template_LogsRow);
	$.template("messageBoxTemplate", Template_MessageBox);
}

function setup() {
	$("#mainBody").empty();
	
	var panel=$.tmpl("logsPanelTemplate", [{id:"logsPanel"}]).appendTo("#mainBody");
	
	// set up highlight & selection effect for [task]
	$(panel).delegate(".logsRow", "mouseover", function() {
		$(this).addClass("hover");
	}).delegate(".logsRow", "mouseout", function() {
		$(this).removeClass("hover");
	});
	
	$(panel).delegate("[name='filter_networkip']", "keyup", function() {
		if(event.keyCode==13) {filter(panel);}
	});
	
	loadLogs();
}

function filter(panel) {
	var rows=$(panel).find(".logsRow");
	var ipprefix=$.trim($(panel).find("[name='filter_networkip']").val().toLowerCase());
	
	var count=0;
	for(var i=0; i<rows.length; i++) {
		var networkip=$(rows[i]).find(".logsIp").html().toLowerCase();
		
		if(0!=networkip.indexOf(ipprefix)){
			$(rows[i]).hide();
		}else{
			$(rows[i]).show();
			
			interpolated(count++, rows[i]);
		}
	}
}

function initUi() {
	// jquery ui init
	$("button").button();
	
	$("#banner").html(Locale["network.banner.log.approve"]);
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
				text: Locale["network.dialog.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function showProcessingDialog() {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+Locale["network.dialog.processing"]+"</div>").dialog({
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
function loadLogs() {
	var list=$("#logsPanel").find("span[zmlm\\\:item='logsList']").empty();
	$("<em>"+Locale["network.message.loading"]+"</em>").appendTo(list);
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/IPAdmin",
		cache: false,
		data: {
			methodtype: "fetchipappprovaltrans",
			actiontype: "all"
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				if(data.length>0){
					var datalist=$.tmpl("logsRowTemplate", data).appendTo($(list).empty());
					
					for(var i=0; i<datalist.length; i++) {
						interpolated(i, datalist[i]);
					}
				}else{
					$("<em>"+Locale["network.message.no_data"]+"</em>").appendTo($(list).empty());
				}
				
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function interpolated(i, row) {
	$(row).removeClass("rowEven").removeClass("rowOdds");
	i%2==0?$(row).addClass("rowEven"):$(row).addClass("rowOdds");
}

function formatIpStatus(status) {
	switch(status) {
		case "approved":
		case "approve": return "<span style='color:#65c300;'>"+Locale["logs.network.action.approve"]+"</span>";//ui-icon ui-icon-check smallIcon
		case "unapproved": 
		case "unapprove": return "<span style='color:#c34444;'>"+Locale["logs.network.action.unapprove"]+"</span>";
		default: return status;
	}
}

function formatDate(dateobj) {
	if(dateobj) {
		var date=new Date(dateobj.time);
		var str="%s-%s-%s %s".sprintf(date.getFullYear(), date.getMonth()+1, date.getDate(), date.toLocaleTimeString());
		return str;
	}
	return "";
}



