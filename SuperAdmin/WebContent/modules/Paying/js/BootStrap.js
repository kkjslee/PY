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
	$.template("payingPanelTemplate", Template_PayingPanel);
	$.template("payingRowTemplate", Template_PayingRow);
	$.template("messageBoxTemplate", Template_MessageBox);
}

function setup() {
	$("#mainBody").empty();
	
	var panel=$.tmpl("payingPanelTemplate", [{id:"payingPanel"}]).appendTo("#mainBody");
	
	// set up highlight & selection effect for [task]
	$(panel).delegate(".payingRow", "mouseover", function() {
		$(this).addClass("hover");
	}).delegate(".payingRow", "mouseout", function() {
		$(this).removeClass("hover");
	});
	
	$(panel).delegate("[name='filter_user'],[name='filter_vm']", "keyup", function() {
		if(event.keyCode==13) {filter(panel);}
	});
	
	$(panel).delegate("[name='filter_status']", "change", function() {
		filter(panel);
	});
	
	loadPaying();
}

function filter(panel) {
	var rows=$(panel).find(".payingRow");
	var vmprefix=($.trim($(panel).find("[name='filter_vm']").val() || "").toLowerCase());
	var userprefix=$.trim($(panel).find("[name='filter_user']").val().toLowerCase());
	var statusprefix=$.trim($(panel).find("[name='filter_status']").val().toLowerCase());
	
	var count=0;
	for(var i=0; i<rows.length; i++) {
		var vm=$(rows[i]).find(".payingVm").html().toLowerCase();
		var user=$(rows[i]).find(".payingUser").html().toLowerCase();
		var status=$(rows[i]).find("input[zmlm\\\:item='requeststatus']").val().toLowerCase();
		
		if(0!=vm.indexOf(vmprefix) || 0!=user.indexOf(userprefix) || 0!=status.indexOf(statusprefix)){
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
	
	$("#banner").html(Locale["paying.banner"]);
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
				text: Locale["paying.dialog.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function showProcessingDialog() {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+Locale["paying.dialog.processing"]+"</div>").dialog({
		autoOpen: true,
		width: 240,
		height: 100,
		resizable: false,
		modal: true,
		closeOnEscape: false,
		open: function(event, ui) {  $(this).parents(".ui-dialog").first().find(".ui-dialog-titlebar-close").hide(); },
		buttons: {
		}
	});
	return view;
}

/* Module Specified */
function loadPaying() {
	var list=$("#payingPanel").find("span[zmlm\\\:item='payingList']").empty();
	$("<em>"+Locale["paying.message.loading"]+"</em>").appendTo(list);
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ApplicationPackageManagement",
		cache: false,
		data: {
			operation: "fetchallchangerequest",
			status: "all"
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				var map={};
				if(data.length>0){
					var datalist=$.tmpl("payingRowTemplate", data).appendTo($(list).empty());
					
					for(var i=0; i<datalist.length; i++) {
						interpolated(i, datalist[i]);
						
						if(data[i].zonedisplay) {
							map[data[i].zonedisplay]=data[i].zone;
						}
					}
					
					filter($("#payingPanel"));
					
				}else{
					$("<em>"+Locale["paying.message.no_data"]+"</em>").appendTo($(list).empty());
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

function formatDate(dateobj) {
	if(dateobj) {
		var date=new Date(dateobj.time);
		var str="%s-%s-%s %s".sprintf(date.getFullYear(), date.getMonth()+1, date.getDate(), date.toLocaleTimeString());
		return str;
	}
	return "";
}

function formatStatus(status) {
	switch(status) {
		case "approved": return "<span style='color:#65c300;'>"+Locale["paying.status.approved"]+"</span>";//ui-icon ui-icon-check smallIcon
		case "unapproved": return "<span style='color:#0c3065;'>"+Locale["paying.status.unapproved"]+"</span>";
		case "waiting": return "<span style='color:#C44;'>"+Locale["paying.status.waiting"]+"</span>";
		default: return status;
	}
}

function formatPrice(price) {
	try {
		return parseFloat(price).toFixed(2);
	}catch(e){return "N/A"};
}

function exam(which, status) {
	if(!confirm(status==="approved"?Locale["paying.confirm.approve"]:Locale["paying.confirm.reject"])) return;
	
	var requestid=$(which).parents(".payingRow").first().find("[zmlm\\\:item='requestid']").val();
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ApplicationPackageManagement",
		cache: false,
		data: {
			operation: "updaterequest",
			status: status,
			requestid: requestid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["paying.message.done"];break;
					case "failed": {
						switch(data.reason) {
							case "systemerror": 
							case "exception": msg=Locale["paying.message.error"]; break;
							case "lowbalance": msg=Locale["paying.message.lowbalance"]; break;
							default: msg=Locale["paying.message.undefined"].sprintf(data.reason);
						}
						break;
					}
					default: msg=Locale["paying.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadPaying();
				
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			pd.dialog("destroy");
			printError(jqXHR, textStatus, errorThrown);
		}
	});
	
}



