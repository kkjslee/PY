// JavaScript Document
// Author: Bill, 2011

var _DEBUG_=false;


$(function(){
	registerTemplate();
	
	setup();
	
	initAjax();
	
	getZoneInfo();
	
	initUi();
});

function registerTemplate() {
	$.template("ctrlBarTemplate", Template_CtrlBar);
	$.template("boxTemplate", Template_Box);
	$.template("theAppTemplate", Template_TheAppTempPanel);
	$.template("softwareItemTemplate", Template_SoftwareItem);
	$.template("messageBoxTemplate", Template_MessageBox);
	$.template("examineTabsTemplate", Template_ExamineTabs);
	$.template("examineRowTemplate", Template_ExamineRow);
	$.template("zoneGroupTemplate", Template_ZoneGroup);
	
}

function setTitle(title) {
	//$(".banner").html(title);
}

function setup() {
	$("body").empty();
	
	$("<div class=\"banner\"></div>").appendTo("body");
	$.tmpl("ctrlBarTemplate").appendTo("body");
	$("<div id=\"mainFrame\"></div>").appendTo("body");
	//$("<div class=\"footer\">© 2011~2012, Zhumulangma Inc. or its affiliates</div>").appendTo("body");
	
	if(window.location.hash=="#exam") {
		loadExamPage();
	}else{
		loadManPage();
	}
	
	// new app template panel
	var newAppTempPanel=$.tmpl("theAppTemplate", [{id:"newAppTempDialog"}]).appendTo("body");
	
	$(newAppTempPanel).delegate(".listItem", "click", function(){
		$(this).toggleClass("select");
	}).delegate(".listItem", "hover", function(){
		$(this).toggleClass("hover");
	});
	
	$(newAppTempPanel).dialog({
		title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>"+Locale["template.dialog.title.new"],
		modal:true,
		autoOpen:false,
		resizable: false,
		show: "slide",
		hide: "slide",
		width: "640px",
		buttons: [
			{
				text:Locale["template.dialog.button.ok"],
				click: function() {
					var appTempZone=$("#zone").val();	
					var appTempName=$.trim($(this).find("[zmlm\\\:item='appTempName']").val());
					var appTempDesc=$(this).find("[zmlm\\\:item='appTempDesc']").val();
					var appTempNotes=$(this).find("[zmlm\\\:item='appTempNotes']").val();
					
					if(""==appTempName){
						printMessage(Locale["template.message.illegal.name.empty"]);
					}else {				
						var softwares=new Array();
						$(this).find(".listPanel").children(".select").each(function(){
							var id=$($(this).data("data")).children("id").text();
							softwares.push(id);
						});
						
						var appTempSoft=softwares.join(",");
										
						commitAppTemp(appTempName, appTempDesc, appTempNotes, appTempZone, appTempSoft);
						
						$(this).dialog("close");
					}				
				}
			},
			{
				text:Locale["template.dialog.button.cancel"],
				click: function() {
					$(this).dialog("close");
				}
			}
		]
	});
	
	// update app template panel
	var updateAppTempPanel=$.tmpl("theAppTemplate", [{id:"updateAppTempDialog"}]).appendTo("body");
	
	$(updateAppTempPanel).delegate(".listItem", "click", function(){
		$(this).toggleClass("select");
	}).delegate(".listItem", "hover", function(){
		$(this).toggleClass("hover");
	});
	
	$(updateAppTempPanel).dialog({
		title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>"+Locale["template.dialog.title.update"],
		modal:true,
		autoOpen:false,
		resizable: false,
		show: "slide",
		hide: "slide",
		width: "640px",
		buttons: [
			{
				text:Locale["template.dialog.button.ok"],
				click: function() {
					var appTempId=$.trim($(this).find("[zmlm\\\:item='appTempId']").val());
					var appTempName=$.trim($(this).find("[zmlm\\\:item='appTempName']").val());
					var appTempDesc=$(this).find("[zmlm\\\:item='appTempDesc']").val();
					var appTempNotes=$(this).find("[zmlm\\\:item='appTempNotes']").val();
					var appTempZone=$(this).find("[zmlm\\\:item='appTempZone']").val();
					
					if(""==appTempName){
						printMessage(Locale["template.message.illegal.name.empty"]);
					}else {				
						var softwares=new Array();
						$(this).find(".listPanel").children(".select").each(function(){
							var id=$($(this).data("data")).children("id").text();
							softwares.push(id);
						});
						
						var appTempSoft=softwares.join(",");
										
						updateAppTemp(appTempId, appTempName, appTempDesc, appTempNotes, appTempZone, appTempSoft);
						
						$(this).dialog("close");
					}				
				}
			},
			{
				text:Locale["template.dialog.button.cancel"],
				click: function() {
					$(this).dialog("close");
				}
			}
		]
	});
}

function loadManPage(message) {
	$("#mainFrame").empty();
	
	setTitle(Locale["template.title.manage"]);
	
	if(null==message)
		$("<span class=\"unloaded\">"+Locale["template.message.no_data"]+"</span>").appendTo("#mainFrame");
	else
		$("<span class=\"unloaded\">"+message+"</span>").appendTo("#mainFrame");
	
}

function loadExamPage() {
	$("#mainFrame").empty();
	
	setTitle(Locale["template.title.verify"]);
	
	var tabs=$.tmpl("examineTabsTemplate", [{id:"examineTabs"}]).appendTo("#mainFrame");
	tabs.tabs().css("border", "0");
	
	
	$(tabs).delegate(".appReqListRow", "mouseover", function() {
		$(this).addClass("hover");
		$(this).children("span").addClass("textExpanded");
		
		$(this).children(".examTempRes").removeClass("appReqResCollapse");
		
	}).delegate(".appReqListRow", "mouseout", function() {
		$(this).removeClass("hover");
		$(this).children("span").removeClass("textExpanded");
		
		if(!$(this).hasClass("select"))
			$(this).children(".examTempRes").addClass("appReqResCollapse");
		
	}).delegate(".appReqListRow", "click", function() {
		$(tabs).find(".appReqListRow").removeClass("select");
		
		$(this).toggleClass("select");
	});
	
	$(tabs).bind("tabsshow", function(event, ui){
		
		if(null==ui || ui.panel.id=="tab-unapproved") {		
			getAppTempReq(tabs.children("#tab-unapproved").find("[zmlm\\\:item='appReqList']"), "unapproved");			
		}else if(ui.panel.id=="tab-approved"){
			getAppTempReq(tabs.children("#tab-approved").find("[zmlm\\\:item='appReqList']"), "proved");	
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
		
		if(requestArray.length>0 && confirm(Locale["template.confirm.verify"])) {
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
		
		if(requestArray.length>0 && confirm(Locale["template.confirm.refuse"])) {
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
		url: Server+"/RedDragonEnterprise/AppTemplateManager",
		cache: false,
		data: {
			operation: "updatetemplateinstance",
			requestid: rid,
			action: "prove",
			iprequired: false,
			volumerequired: false
		},
		success: function(data) {
			if(data.match("true")) {
				printMessage(Locale["template.message.verify.success"]);
				
				$("#examineTabs").trigger("tabsshow");
			}else if(data.match("false")){
				printMessage(Locale["template.message.verify.failure.stock"].sprintf(data));
			}else{
				printMessage(Locale["template.message.verify.failure.exception"].sprintf(data));
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
		url: Server+"/RedDragonEnterprise/AppTemplateManager",
		cache: false,
		data: {
			operation: "updatetemplateinstance",
			requestid: rid,
			action: "reject",
			iprequired: false,
			volumerequired: false
		},
		success: function(data) {
			if(data.match("true")) {
				printMessage(Locale["template.message.refuse.success"]);
				
				$("#examineTabs").trigger("tabsshow");
			}else{
				printMessage(Locale["template.message.refuse.failure"].sprintf(data));
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});	
}

function commitAppTemp(appTempName, appTempDesc, appTempNotes, appTempZone, appTempSoft) {	
	$.ajax({
		type:"GET",
		url: Server+"/RedDragonEnterprise/AppTemplateManager",
		cache: false,
		data: {
			operation: "createapptemplate",
			name: appTempName,
			description: appTempDesc,
			notes: appTempNotes,
			softwares: appTempSoft,
			zone: appTempZone
		},
		success: function(data) {
			if(data.match("succeed")) {
				printMessage(Locale["template.message.add.success"]);
				
				reload();
			}else{
				printMessage(Locale["template.message.add.failure"].sprintf(data));
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function updateAppTemp(appTempId, appTempName, appTempDesc, appTempNotes, appTempZone, appTempSoft) {	
	$.ajax({
		type:"GET",
		url: Server+"/RedDragonEnterprise/AppTemplateManager",
		cache: false,
		data: {
			operation: "updateapptemplate",
			apptemplateid: appTempId,
			name: appTempName,
			description: appTempDesc,
			notes: appTempNotes,
			softwares: appTempSoft,
			zone: appTempZone
		},
		success: function(data) {
			if(data.match("succeed")) {
				printMessage(Locale["template.message.update.success"]);
				
				reload();
			}else{
				printMessage(Locale["template.message.update.failure"].sprintf(data));
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function deleteAppTemp(appTempId) {
	$.ajax({
		type:"GET",
		url: Server+"/RedDragonEnterprise/AppTemplateManager",
		cache: false,
		data: {
			operation: "deleteapptemplate",
			apptemplateid: appTempId
		},
		success: function(data) {
			if(data.match("succeed")) {
				printMessage(Locale["template.message.remove.success"]);
				
				reload();
			}else{
				printMessage(Locale["template.message.remove.failure"].sprintf(data));
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function reload() {
	loadManPage(Locale["template.message.loading"]);
	
	var zone=$("#zone").val();
	
	$.ajax({
		type:"GET",
		url: Server+"/RedDragonEnterprise/AppTemplateManager",
		cache: false,
		data: {
			operation: "getapptemplates",
			zone: zone
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				var view=$.tmpl("boxTemplate", data);
				
				if(view.length>0) {
					$(view).appendTo($("#mainFrame").empty());
					
					view.each(function(index, element){  // .boxInfo
						$(this).data("data", data[index]);
						
						$(this).bind("delete", function(){
							var id=$(this).data("data").id;
							var name=$(this).data("data").name;
							
							if(confirm(Locale["template.confirm.remove"].sprintf(name))) {								
								deleteAppTemp(id);
							}
							
						}).bind("update", function(){
							var entity=$(this).data("data");
							
							var id=entity.id;
							var name=entity.name;
							var notes=entity.notes;
							var desc=entity.description;
							var zone=$(this).find("[zmlm\\\:item='zone']").val();
							
							var softwares=new Array();
							for(var i=0; i<entity.softwares.length; i++) {
								softwares.push(entity.softwares[i].softwareid);
							};
							softwares=softwares.join(",");
							
							showUpdateAppTempDialog(id, name, desc, notes, zone, softwares);							
						});
					});
					
				}else{
					$("#mainFrame").empty().append("<span class=\"empty\">No Templates</span>");
				}
	
				$("[zmlm\\\:item='resources']").hide();
				
				initUi();
			}catch(e){
				printMessage("Data Broken: ["+e+"]");
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
	return new Date(longtime).toUTCString();
}

function formatExamStatus(status) {
	switch(status) {
		case "unapproved":return Locale["template.apply.status.unapproved"];
		case "proved":return Locale["template.apply.status.proved"];
		case "rejected":return Locale["template.apply.status.rejected"];
		default : return "[Undefined Status]";
	}
}

function getZoneInfo() {
	$.ajax({
		type: "GET",
		url: Server+"/RedDragonEnterprise/InformationRetriverServlet",
		cache: false,
		data: {
			methodType: 'GETZONEINFOR'
		}, 
		dataType: "xml",
		success: function(data) {
			try{
				var dataCenters=$(data).find('mydatacentre');
				
				var optionArray=new Array();
						
				dataCenters.each(function(index, element) {
					optionArray.push({lable:$(this).attr("lable"), value:$(this).attr("data")});
				});
				
				var view=$.tmpl("zoneGroupTemplate", [{
					id:"zone", 
					options:optionArray
				}]);
				view.data("data", data);
				
				$("#zone").replaceWith(view);
				
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function getAppTempReq(container, type) {
	$("<em>"+Locale["template.message.loading"]+"</em>").appendTo($(container).empty());
	
	$.ajax({
		type:"GET",
		url: Server+"/RedDragonEnterprise/AppTemplateManager",
		cache: false,
		data: {
			operation: "fetchapptemplaterequests",
			status: type
		},
		success: function(data) {/*
			data='[{"requestid":"89071a55-ee10-431b-a6df-a51981076510","templatename":"test","requestedname":"req name","submissiontime":{"date":18,"day":2,"hours":11,"minutes":54,"month":9,"nanos":0,"seconds":28,"time":1318910068000,"timezoneOffset":-480,"year":111},"status":"unapproved"}, {"requestid":"c69018d3-a25b-4738-88a7-2e7710324ee5","sftresources":[{"softwareid":"1023-ab1112222-123123123123xxxxx","softwarename":"Win 2003","sftresourceid":"3","cpu":"1","maxcpu":"2","mem":"1024","maxmem":"2048"}],"templatename":"test","requestedname":"req name","submissiontime":{"date":18,"day":2,"hours":11,"minutes":51,"month":9,"nanos":0,"seconds":45,"time":1318909905000,"timezoneOffset":-480,"year":111},"status":"unapproved"}, {"requestid":"ce38e3d9-743d-4d5c-838b-a2a3362eb9c7","sftresources":[{"softwareid":"1","softwarename":"2","sftresourceid":"3","cpu":"2","maxcpu":"8","mem":"2048","maxmem":"4096"},{"softwareid":"1","softwarename":"2","sftresourceid":"2","cpu":"4","maxcpu":"4","mem":"1024","maxmem":"8192"}],"templatename":"test","requestedname":"reqname","submissiontime":{"date":18,"day":2,"hours":11,"minutes":44,"month":9,"nanos":0,"seconds":24,"time":1318909464000,"timezoneOffset":-480,"year":111},"status":"unapproved"}]';*/
			
			$(container).empty();
			
			try{			
				data=$.parseJSON(data);
				
				$(container).data("data", data);
				
				if(data.length>0)
					$.tmpl("examineRowTemplate", data).appendTo($(container));
				else
					$("<span class=\"empty\">"+Locale["template.message.empty"]+"</span>").appendTo($(container));
				
			}catch(e){
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
	
}

// load softwares
function getSoftware(parent, defaultSoftwares) {			
	var container=$(parent).find(".listPanel").empty();
	
	$.ajax({
		type: "GET", 
		url: Server+"/billingCN/BillingServlet",
		cache: false,
		data: {
			RequestType: 'getSoftware',
			type: 'All',
			SearchName: ''
		},
		success: function(data) {
			try{
				data="<data>"+data+"</data>";
				data=$.parseXML(data);
				
				if($(data).find("Software").length>0) {
					$(data).find("Software").each(function(index, element) {
						var id=$(this).children("id").text();
						var softwareName=$(this).children("SoftwareName").text();
						var modelType=$(this).children("ModelType").text();
						var description=$(this).children("Description").text();
						var introduction=$(this).children("Introduction").text();
						
						var view=$.tmpl("softwareItemTemplate", 
							[{
								id: id,
								name: softwareName,
								type: modelType,
								desc: description,
								intro: introduction
							}]
						);
						
						if(null!=defaultSoftwares && ""!=defaultSoftwares) {
							softwares=defaultSoftwares.split(",");
							
							for(var i=0; i<softwares.length; i++) {
								if(id==softwares[i]) {
									view.addClass("select");
									break;
								}
							}							
						}
						
						// binding data
						view.data("data", this);
						
						// display it
						view.appendTo(container);
					});
				}else{
					container.append("<span class=\"empty\"><em>"+Locale["template.message.empty"]+"</em></span>");
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

function printError(jqXHR, textStatus, errorThrown) {
	printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}

function printMessage(msg) {
	$.tmpl("messageBoxTemplate", [{message: msg}]).appendTo("body").dialog({
		resizable: false,
		modal: true,
		buttons: [{
			text:Locale["template.dialog.button.close"],
			click: function() {
				$(this).dialog("close");
				$(this).remove();
			}
		}]
	});
}

function showNewTemplateDialog() {
	$("#newAppTempDialog").dialog("open");
	
	getSoftware($("#newAppTempDialog"));
}

function showUpdateAppTempDialog(id, name, desc, notes, zone, softwares) {
	$("#updateAppTempDialog").dialog("open");
	
	$("#updateAppTempDialog").find("[zmlm\\\:item='appTempId']").val(id);
	$("#updateAppTempDialog").find("[zmlm\\\:item='appTempName']").val(name);
	$("#updateAppTempDialog").find("[zmlm\\\:item='appTempDesc']").val(desc);
	$("#updateAppTempDialog").find("[zmlm\\\:item='appTempNotes']").val(notes);
	$("#updateAppTempDialog").find("[zmlm\\\:item='appTempZone']").val(zone);
	
	getSoftware($("#updateAppTempDialog"), softwares);		
	
}


