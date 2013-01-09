// JavaScript Document
// Author: Bill, 2011

var _DEBUG_=false;


$(function(){
	registerTemplate();
	
	setup();
	
	initAjax();
	
	initUi();
});

function registerTemplate() {
	$.template("softwareItemTemplate", Template_SoftwareItem);
	$.template("messageBoxTemplate", Template_MessageBox);
	$.template("examineTabsTemplate", Template_ExamineTabs);
	$.template("examineRowTemplate", Template_ExamineRow);
	
}

function setTitle(title) {
	//$(".banner").html(title);
}

function setup() {
	$("body").empty();
	
	$("<div class=\"banner\"></div>").appendTo("body");
	$("<div id=\"mainFrame\"></div>").appendTo("body");
	//$("<div class=\"footer\">© 2011~2012, Zhumulangma Inc. or its affiliates</div>").appendTo("body");
	
	//loadManPage();
	loadExamPage();
	
}

function loadExamPage() {
	$("#mainFrame").empty();
	
	setTitle(Locale["project.title.management"]);
	
	var tabs=$.tmpl("examineTabsTemplate", [{id:"examineTabs"}]).appendTo("#mainFrame");
	tabs.tabs().css("border", "0");
	
	
	$(tabs).delegate(".appReqListRow", "mouseover", function() {
		$(this).addClass("hover");
		$(this).children("span").addClass("textExpanded");
		
		$(this).children(".examTempRes").removeClass("appReqResCollapse");
		
	}).delegate(".appReqListRow", "mouseout", function() {
		$(this).removeClass("hover");
		
	}).delegate(".appReqListRow", "click", function() {
		$(tabs).find(".appReqListRow").removeClass("select");
		
		$(this).toggleClass("select");
	});
	
	$(tabs).bind("tabsshow", function(event, ui){
		
		if(null==ui || ui.panel.id=="tab-unapproved") {		
			getAppTempReq(tabs.children("#tab-unapproved").find("[zmlm\\\:item='appReqList']"), "unapproved");			
		}else if(ui.panel.id=="tab-approved"){
			getAppTempReq(tabs.children("#tab-approved").find("[zmlm\\\:item='appReqList']"), "approved");	
		}else if(ui.panel.id=="tab-rejected") {
			getAppTempReq(tabs.children("#tab-rejected").find("[zmlm\\\:item='appReqList']"), "rejected");
		}
		
	}).triggerHandler("tabsshow");
	
	$(tabs).find("[zmlm\\\:item='buttonApproved']").button({
		icons: {
			primary: "ui-icon-circle-check"
		}
	}).bind("click", function(){
		var requestArray=new Array();
		$("#tab-unapproved").find("[zmlm\\\:item='appReqList']").find(".select").each(function(index, element){
			var reqId=$(this).find("input[zmlm\\\:item='examReqId']").val();
			
			requestArray.push(reqId);
		});
		
		if(requestArray.length>0 && confirm(Locale["project.confirm.accept"])) {
			approveAppRequest(requestArray);
		}
	});
		
	$(tabs).find("[zmlm\\\:item='buttonRejected']").button({
		icons: {
			primary: "ui-icon-circle-close"
		}
	}).bind("click", function(){
		var requestArray=new Array();
		$("#tab-unapproved").find("[zmlm\\\:item='appReqList']").find(".select").each(function(index, element){
			var reqId=$(this).find("[zmlm\\\:item='examReqId']").val();
			
			requestArray.push(reqId);
		});
		
		if(requestArray.length>0 && confirm(Locale["project.confirm.reject"])) {
			rejectAppRequest(requestArray);
		}
	});
	
}

function initAjax() {
	jQuery.support.cors = true;
	
	$(document).ajaxStart(function(){
		$("#loadingIcon").show();
	}).ajaxStop(function(){
		$("#loadingIcon").hide();
	});
}

function initUi() {
	$("#newTemplateBtn").button({
		icons: {
			primary: "ui-icon-circle-plus"
		}
	});
	
	$("#refreshBtn").button({
		icons: {
			primary: "ui-icon-arrowrefresh-1-n"
		}
	});
	
	$("#manageBtn").button({
		icons: {
			primary: "ui-icon-calculator"
		}
	});
	
	$("#examineBtn").button({
		icons: {
			primary: "ui-icon-clipboard"
		}
	});
	
	$("button[name='deleteTemplate']").button({
		icons: {
			primary: "ui-icon-minus"
		}
	});
	
	$("button[name='updateTemplate']").button({
		icons: {
			primary: "ui-icon-document"
		}
	});
}

function approveAppRequest(reqArr) {
	var rid=reqArr.join(",");
	
	$.ajax({
		type: "GET",
		url: Server+"/RedDragonEnterprise/ProjectManager",
		cache: false,
		data: {
			operation: "updateprojectrequest",
			projectid: rid,
			action: "approve"
		},
		success: function(data) {
			if(data.match("true")) {
				printMessage(Locale["project.message.verify.success"]);
				
				$("#examineTabs").trigger("tabsshow");
				
			}else if(data.match("false")){
				printMessage(Locale["project.message.verify.failure.stock"].sprintf(data));
			}else{
				printMessage(Locale["project.message.verify.failure.exception"].sprintf(data));
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function rejectAppRequest(reqArr) {
	var rid=reqArr.join(",");
	
	$.ajax({
		type: "GET",
		url: Server+"/RedDragonEnterprise/ProjectManager",
		cache: false,
		data: {
			operation: "updateprojectrequest",
			projectid: rid,
			action: "reject"
		},
		success: function(data) {
			if(data.match("true")) {
				printMessage(Locale["project.message.refuse.success"]);
				
				$("#examineTabs").trigger("tabsshow");
			}else{
				printMessage(Locale["project.message.refuse.failure"].sprintf(data));
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});	
}

function formatSize(bytes) {
	if(null==bytes || 0==bytes) return "--";
	
	var i=0;
	while(1023 < bytes){
		bytes /= 1024;
		++i;
	};
	return i?bytes.toFixed(2) + ["", " KB", " MB", " GB", " TB"][i] : bytes + " bytes";
}

function formatDate(longtime) {
	var date=new Date(longtime);
	var ret=date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
	return ret;
	//return new Date(longtime).toUTCString();
}

function formatExamStatus(status) {
	switch(status) {
		case "unapproved":return Locale["project.apply.status.unapproved"];
		case "approved":return Locale["project.apply.status.proved"];
		case "rejected":return Locale["project.apply.status.rejected"];
		default : return "[N/A]";
	}
}

function getAppTempReq(container, type) {
	$("<em>"+Locale["project.message.loading"]+"</em>").appendTo($(container).empty());
	
	$.ajax({
		type:"GET",
		url: Server+"/RedDragonEnterprise/ProjectManager",
		cache: false,
		data: {
			operation: "fetchprojectrequests",
			status: type
		},
		success: function(data) {
			$(container).empty();
			
			try{
				data=$.parseJSON(data);
				
				$(container).data("data", data);
				
				if(data.length>0)
					$.tmpl("examineRowTemplate", data).appendTo($(container));
				else
					$("<span class=\"empty\">"+Locale["project.message.empty"]+"</span>").appendTo($(container));
				
			}catch(e){
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function printError(jqXHR, textStatus, errorThrown) {
	printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}

function printMessage(msg) {
	$.tmpl("messageBoxTemplate", [{message: msg}]).appendTo("body").dialog({
		resizable: false,
		modal: true,
		buttons: [
			{
				text: Locale["project.dialog.button.close"],
				click: function() {
					$(this).dialog("close");
					$(this).remove();
				}
			}
		]
	});
}


