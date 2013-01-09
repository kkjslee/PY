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
	
	$(panel).find("[name='datepicker']").datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		maxDate: "+0"
	});
	
	// set up highlight & selection effect for [task]
	$(panel).delegate(".logsRow", "mouseover", function() {
		$(this).addClass("hover");
	}).delegate(".logsRow", "mouseout", function() {
		$(this).removeClass("hover");
	});
	
	$(panel).find("[name='serchuser']").bind("keydown", function(){
		if(event.keyCode==13) {$(this).siblings("[name='search']").trigger("click");}
	});
	
	loadLogs();
}

function initUi() {
	// jquery ui init
	$("button").button();
	
	$(".navibar [name='prevbtn']").button({icons: {primary: "ui-icon-triangle-1-w"}});
	$(".navibar [name='nextbtn']").button({icons: {secondary: "ui-icon-triangle-1-e"}});
	
	$("#banner").html(Locale["logs.banner.email"]);
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
				text: Locale["logs.dialog.button.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function showProcessingDialog() {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+Locale["logs.dialog.processing"]+"</div>").dialog({
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

	
var past=0; //0-today, 1-yesterday

/* Module Specified */
function loadLogs(param, advance) {
	var list=$("#logsPanel").find("[zmlm\\\:item='logsList']>tbody").empty();
	$("<span class='logsListTipsLabel'><em>"+Locale["logs.message.loading"]+"</em></span>").appendTo(list);
	
	if(param===1) {
		past=past>0?past-1:past;
		
		$("#logsPanel").find("[name='datepicker']").datepicker("setDate", new Date($.now()-past*86400000));
	}else if(param===-1) {
		past+=1;
		
		$("#logsPanel").find("[name='datepicker']").datepicker("setDate", new Date($.now()-past*86400000));
	}else if(param) {
		var date=$(param).siblings("[name='datepicker']").datepicker("getDate");
		
		if(date) {
			past=Math.floor(($.now()-date.getTime())/86400000-1);
			past=past<0?0:past;
		}else{
			past=0;
		}
	}
	
	var start=(Math.floor(($.now()/86400000)-past))*86400000;
	var end=start+86400000;
	var targetlogin="all";
	
	if(advance) {
		$("#logsPanel .footerRow").find("td").children().css("visibility", "hidden");
		targetlogin=advance.user;
		start=advance.start;
		end=advance.end;
	}else{
		$("#logsPanel .footerRow").find("td").children().css("visibility", "inherit");
	}
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/LogManager",
		cache: false,
		data: {
			methodtype: "getemaillogs",
			logtype: "emaillog",
			loginuser: getUsername(),
			requestedlogin: targetlogin,
			starttime: start,
			endtime: end
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				if(data.length>0){
					
					data.sort(function(a, b){
						return (a.senttime && a.senttime.time && b.senttime && b.senttime.time)?b.senttime.time-a.senttime.time:0;
					});
					
					var datalist=$.tmpl("logsRowTemplate", data).appendTo($(list).empty());
					
					for(var i=0; i<datalist.length; i++) {
						interpolated(i, datalist[i]);
					}
				}else{
					$("<span class='logsListTipsLabel'><em>"+Locale["logs.message.empty"]+"</em></span>").appendTo($(list).empty());
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

function search() {
	var user=$("[name='serchuser']").val();
	
	if($.trim(user)=="") {
		loadLogs();
	}else{
		loadLogs(null, {
			start: 0,
			end: $.now(),
			user: user
		});
	}
}

function interpolated(i, row) {
	$(row).removeClass("rowEven").removeClass("rowOdds");
	i%2==0?$(row).addClass("rowEven"):$(row).addClass("rowOdds");
}

function formatDate(dateobj) {
	if(dateobj) {
		var date=new Date(dateobj.time);
		var str="%s-%s-%s %s".sprintf(date.getFullYear(), date.getMonth()+1, date.getDate(), date.toLocaleTimeString());
		return str;
	}
	return "";
}



