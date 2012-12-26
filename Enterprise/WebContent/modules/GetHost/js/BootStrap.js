// JavaScript Document
// Author: Bill, 2012
 /*<!--#include virtual="../../../config.shtml" -->*/

var _DEBUG_=false;

var Order=function() {
	
	this.enterprise=isEnterpriseVersion();
	
	this.enableSaveOrder=true;
	
	this.softwareid=undefined;
	this.datacenter=undefined;
	this.provider=undefined;
	this.hardwareid=undefined;
	this.networkType=undefined;
	this.volumeSize=undefined;
	this.volumeName=undefined;
	
	/*
	// debug
	this.enterprise=false;
	this.hasNetwork="true";
	this.hasVolume="true";
	this.volumeprice=0.5;
	this.volumeSize=1024;
	this.ipprice=50;
	this.volumeName="abc";
	this.orderAmount=1;
	this.networkType="public";
	*/
	
};

var scriptToLoad=[
	"../../locale.jsp?_module=GetHost",
	"js/template.js"
];

$(function(){
	BootLoader.init();
	
	BootLoader.prepareScriptSerial(scriptToLoad, function(){
		
		BootLoader.cache.order=new Order();
		
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
	$.template("mainPanelTemplate", Template_MainPanel);
	$.template("softwareRowTemplate", Template_SoftwareRow);
	$.template("hardwareRowTemplate", Template_HardwareRow);
	$.template("planRowTemplate", Template_PlanRow);
	$.template("confirmDialogTemplate", Template_ConfirmDialog);
	$.template("saveOrderDialogTemplate", Template_SaveOrderDialog);
	$.template("messageBoxTemplate", Template_MessageBox);
}

function setup() {
	$("#mainBody").empty();
	
	var panel=$.tmpl("mainPanelTemplate", [{id:"mainPanel"}]).appendTo("#mainBody");
	
	$(panel).find("#checkvmname").bind("click", function(e){
		hideAdditionalPanel(); // hide additional panel when button click to avoid invalid VM name using
		checkVmName();
	});
	
	$(panel).find("#vmname").bind("keydown", function(e){
		hideAdditionalPanel(); // hide additional panel when input is changing to avoid invalid VM name using
	});
	
	$(panel).find("#vmname").bind("paste", function(e){
		hideAdditionalPanel(); // hide additional panel when input is changing to avoid invalid VM name using
	});
	
	loadDataCenter();
}

function initUi() {
	// jquery ui init
	$("button").button();
	
	$("#banner").html(Locale["gethost.banner"]);
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
				text: Locale["gethost.dialog.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function showProcessingDialog() {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+Locale["gethost.dialog.processing"]+"</div>").dialog({
		autoOpen: true,
		width: 240,
		height: 100,
		resizable: false,
		modal: true,
		closeOnEscape: false,
		open: function(event, ui) {   $(this).parents(".ui-dialog").first().find(".ui-dialog-titlebar-close").hide();  },
		buttons: {
		}
	});
	return view;
}


/* Module Specified */
function loadSoftwares(callback) {
	var list=$("#mainPanel").find("[zmlm\\\:item='softwareList']").empty();
	$("<em class='tipsLabel'>"+Locale["gethost.message.loading"]+"</em>").appendTo(list);
	
	$.ajax({
		url: Server+"/RedDragonEnterprise/ApplicationPublishManager",
		cache: false,
		type: "get",
		data: {
			methodtype: "fetchsoftware",
			zone: BootLoader.cache.order.datacenter
		}, 
		success: function(data) {
			// clear table before inserting new data
			var dataTable=$(list).parents("table").first();
			$(dataTable).dataTable().fnDestroy();
			$(list).empty(); // clear
			
			try{
				data=$.parseJSON(data);
				
				// let's make a trick here, and foo it for json usage.
				var softwares=[];
				if(data instanceof Array) {
					$(data).each(function(index, element){
						if(this.softwarestatus=="show") {
							softwares.push({
								id: this.softwareid,
								SoftwareName: this.softwarename,
								Description: this.softwareintroduction,
								softwareimage: this.softwareimage,
								visible: this.softwarestatus
							});
						}
					});
				}
				
				if(softwares.length>0){
					
					$.tmpl("softwareRowTemplate", softwares).appendTo($(list));
			
					// (re)initialize the table
					$(dataTable).dataTable({
						"bAutoWidth": false,
						"sPaginationType": "full_numbers",
						"oLanguage": (Locale && Locale.dataTable) || null,
						"sDom": "<'#dataTable't<'#pager'>>"
					});
					
					// listen to the click event
					$(list).find(".Row").bind("click", function(){
						if(!$(this).is(".selected")) {
							$(this).addClass("selected");
							$(this).siblings(".Row").removeClass("selected");
							
							var softwareid=$(this).find("input[name='softwareId']").val();
							var softwarename=$(this).find("input[name='softwareName']").val();
				
							// stores info into order instance
							BootLoader.cache.order.softwareid=softwareid;
							BootLoader.cache.order.softwarename=softwarename;
							
							BootLoader.log("choosing software "+softwareid);
							
							// goto next step
							goNextStepFrom("software");
						}
					});
					
				}else{
					$("<em>"+Locale["gethost.message.no_data"]+"</em>").appendTo($(list));
				}
				
			}catch(e){
				printMessage(e);
			}
			
			callback && callback();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			
		}
	});	
}

function loadDataCenter() {
	var pd=showProcessingDialog();
	$.ajax({
		url: Server+"/RedDragonEnterprise/InformationRetriverServlet",
		cache: false,
		type: "get",
		data: {
			methodtype: 'getzonelist'
		}, 
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				// let's make a trick here, and foo it for json usage.
				var datacenters=[];
				if(data instanceof Array) {
					$(data).each(function(index, element){
						datacenters.push({
							label:this.zonename,
							data: this.zone,
							provider: this.zoneprovider
						});
					});
				}
				
				BootLoader.cache.datacenter=datacenters;
				
				// TODO: Maybe i should specify a default data center and jump out this step when one and only one default specifeid data center is configured .shtml. 
				showDataCenterPanel(function(){ // show it coz i've cached it while this page up
					// scroll to that
					window.location.hash="";
					window.location.hash="datacenterpanel";
				});
				
			}catch(e){
				printMessage(e);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");
		}
	});
}

function loadHardware(callback) {
	var list=$("#mainPanel").find("[zmlm\\\:item='hardwareList']").empty();
	$("<em class='tipsLabel'>"+Locale["gethost.message.loading"]+"</em>").appendTo(list);
	
	$.ajax({
		url: Server+"/RedDragonEnterprise/ApplicationPublishManager",
		cache: false,
		type: "get",
		data: {
			methodtype: 'fetchsolesftresources',
			softwareid: BootLoader.cache.order.softwareid,
			zone: BootLoader.cache.order.datacenter
		}, 
		success: function(data) {
			// clear table before inserting new data
			var dataTable=$(list).parents("table").first();
			$(dataTable).dataTable().fnDestroy();
			$(list).empty(); // clear
			
			try{
				data=$.parseJSON(data);
					
				// let's make a trick here, and foo it for json usage.
				var hardwares=[];
				if(data instanceof Array) {
					$(data).each(function(index, element){
						hardwares.push({
							resourceid: this.sftresid,
							cpu: this.sftresdefcpu,
							maxcpu: this.sftresmaxcpu,
							memory: this.sftresdefmem,
							maxmemory: this.sftresmaxmem,
							description: this.sftresdes
						});
					});
				}
				
				if(hardwares.length>0){
					
					$.tmpl("hardwareRowTemplate", hardwares).appendTo($(list));
			
					// (re)initialize the table
					$(dataTable).dataTable({
						"bAutoWidth": false,
						"sPaginationType": "full_numbers",
						"oLanguage": (Locale && Locale.dataTable) || null,
						"sDom": "<'#dataTableHardware't<'#pager'>>"
					});
					
					// listen to the click event
					$(list).find(".Row").bind("click", function(){
						if(!$(this).is(".selected")) {
							$(this).addClass("selected");
							$(this).siblings(".Row").removeClass("selected");
							
							var hardwareid=$(this).find("input[name='hardwareId']").val();
							var hardwaredcpu=$(this).find("input[name='hardwareCpu']").val();
							var hardwaremcpu=$(this).find("input[name='hardwareMaxCpu']").val();
							var hardwaredmem=$(this).find("input[name='hardwareMem']").val();
							var hardwaremmem=$(this).find("input[name='hardwareMaxMem']").val();
				
							// stores info into order instance
							BootLoader.cache.order.hardwareid=hardwareid;
							
							BootLoader.cache.order.hardwareDesc=$(this).find(".hardwareDesc").html();
							BootLoader.cache.order.hardwareCpu=$(this).find(".hardwareCpu").html();
							BootLoader.cache.order.hardwareMaxCpu=$(this).find(".hardwareMaxCpu").html();
							BootLoader.cache.order.hardwareMem=$(this).find(".hardwareMem").html();
							BootLoader.cache.order.hardwareMaxMem=$(this).find(".hardwareMaxMem").html();
							
							BootLoader.cache.order.dcpu=hardwaredcpu;
							BootLoader.cache.order.mcpu=hardwaremcpu;
							BootLoader.cache.order.dmem=hardwaredmem;
							BootLoader.cache.order.mmem=hardwaremmem;
							
							BootLoader.log("choosing hardware "+hardwareid);
							
							// goto next step
							goNextStepFrom("hardware");
						}
					});
				}else{
					$("<em>"+Locale["gethost.message.no_data"]+"</em>").appendTo($(list));
				}
				
			}catch(e) {
				printMessage(e);
			}
				
			callback && callback();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {}
	});
}

function loadPlan(callback) {
	var list=$("#mainPanel").find("[zmlm\\\:item='planList']").empty();
	$("<em class='tipsLabel'>"+Locale["gethost.message.loading"]+"</em>").appendTo(list);
	
	$.ajax({
		url: Server+"/RedDragonEnterprise/ApplicationPublishManager",
		cache: false,
		type: "get",
		data: {
			methodtype: 'fetchvmplansbysftresid',
			softwareid: BootLoader.cache.order.softwareid,
			sftresourceid: BootLoader.cache.order.hardwareid,
			zone: BootLoader.cache.order.datacenter
		}, 
		success: function(data) {
			// clear table before inserting new data
			var dataTable=$(list).parents("table").first();
			$(dataTable).dataTable().fnDestroy();
			$(list).empty(); // clear
			
			try{
				data=$.parseJSON(data);
					
				// let's make a trick here, and foo it for json usage.
				var plans=[];
				if(data instanceof Array) {
					$(data).each(function(index, element){
						plans.push({
							resourceid: this.sftresid,
							planname: this.sftresdes,
							vmplanid: this.vmplanid,
							price: this.vmplanprice,
							cpufee: this.cpuprice,
							memfee: this.memprice,
							netfee: this.networkprice,
							plandesc: this.vmplandes
						});
					});
				}
				
				if(plans.length>0){
					
					$.tmpl("planRowTemplate", plans).appendTo($(list));
			
					// (re)initialize the table
					$(dataTable).dataTable({
						"bAutoWidth": false,
						"sPaginationType": "full_numbers",
						"oLanguage": (Locale && Locale.dataTable) || null,
						"sDom": "<'#dataTablePlan't<'#pager'>>"
					});
					
					// listen to the click event
					$(list).find(".Row").bind("click", function(){
						if(!$(this).is(".selected")) {
							$(this).siblings(".Row").removeClass("selected");
							$(this).addClass("selected");
							
							var softwareresourceid=$(this).find("input[name='softwareresourceid']").val();
							var planid=$(this).find("input[name='planid']").val();
				
							// stores info into order instance
							BootLoader.cache.order.planid=planid;
							BootLoader.cache.order.softwareresourceid=softwareresourceid;
							
							BootLoader.cache.order.planName=$(this).find(".planName").html();
							BootLoader.cache.order.planDesc=$(this).find(".planDesc").html();
							BootLoader.cache.order.planPrice=$(this).find("[name='onceprice']").val();
							BootLoader.cache.order.planCpu=$(this).find("[name='plancpu']").val();
							BootLoader.cache.order.planMem=$(this).find("[name='planmem']").val();
							BootLoader.cache.order.planNet=$(this).find("[name='plannet']").val();
							
							BootLoader.log("choosing plan "+planid);
							
							// goto next step
							setTimeout('goNextStepFrom("plan")', 100); // set a delay here coz there is an unknown delay problem without it
						}
					});
				}else{
					$("<em>"+Locale["gethost.message.no_data"]+"</em>").appendTo($(list));
				}
				
			}catch(e) {
				printMessage(e);
			}
				
			callback && callback();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {}
	});
}

function loadIpPrice() {	
	var pd=showProcessingDialog();
	$.ajax({
		url: Server+"/billingCN/BillingServlet", 
		cache: false,
		data: {
			RequestType: 'getResourcePricesByZone',
			resourceType: 'IP',
			zone: BootLoader.cache.order.datacenter
		}, 
		success: function(data) {
			try{
				data=$.parseXML("<data>"+data+"</data>");
					
				// TODO: ----Foo for JSON----
				// let's make a trick here, and foo it for json usage.
				var price=$(data).find("resource").attr("price");
				// ----Foo for JSON----
				
				BootLoader.cache.order.ipprice=price;
				
				$("#ippricespan").html(Locale["gethost.label.fee.network"].sprintf(price));
				
			}catch(e){
				printMessage(e);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");
		}
	});
}

function loadVolumePrice() {	
	var pd=showProcessingDialog();
	$.ajax({
		url: Server+"/billingCN/BillingServlet", 
		cache: false,
		data: {
			RequestType: 'getResourcePricesByZone',
			resourceType: 'volume',
			zone: BootLoader.cache.order.datacenter
		}, 
		success: function(data) {
			try{
				data=$.parseXML("<data>"+data+"</data>");
					
				// TODO: ----Foo for JSON----
				// let's make a trick here, and foo it for json usage.
				var price=$(data).find("resource").attr("price");
				// ----Foo for JSON----
				
				BootLoader.cache.order.volumeprice=price;
				
				$("#volumepricespan").html(Locale["gethost.label.fee.volume"].sprintf(price));
				
			}catch(e){
				printMessage(e);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");
		}
	});
}

function checkVmName() {
	var vmname=$("#vmname").val();
	
	if(!/<!--#echo var='RegExp_Instance'-->/i.test(vmname)) {printMessage(Locale["gethost.label.tips.vmname.invalid"]);return;}
	
	var pd=showProcessingDialog();
	$.ajax({
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data:{
			methodType: 'CheckInstanceName',
			loginUser: getUsername(),
			instanceName: vmname
		},
		success: function(data) {
			
			try{
				data=$.parseXML(data);
				
				// TODO: ----Foo for JSON----
				// let's make a trick here, and foo it for json usage.
				var result={
					status: $(data).find("status").text().toLowerCase()
				};
				// ----Foo for JSON----
				
				if(result.status=="available") {
					BootLoader.cache.order.vmname=vmname;
					
					BootLoader.log("set vm name "+vmname);
					
					goNextStepFrom("vmname");
				}else{
					printMessage(Locale["gethost.label.tips.vmname.unavailable"]);
				}
				
			}catch(e){
				printMessage(e);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");			
		}
	});
}

function validateOrder() {
	var networkType=$("[name='additionalpanel'] input[type='radio'][name='networktype']:checked").val();
	var volumeSize=$("#volumesize").val();
	var volumeName=$("#volumename").val();
	
	var hasNetwork=$("[name='additionalpanel']").find("input[type='radio'][name='network']:checked").val();
	var hasVolume=$("[name='additionalpanel']").find("input[type='radio'][name='volume']:checked").val();
	
	BootLoader.cache.order.hasNetwork=hasNetwork;
	BootLoader.cache.order.hasVolume=hasVolume;
	
	BootLoader.cache.order.networkType=networkType;
	BootLoader.cache.order.volumeSize=volumeSize;
	BootLoader.cache.order.volumeName=volumeName;
	
	if(!BootLoader.cache.order.hasNetwork || (BootLoader.cache.order.hasNetwork=="true" && (!BootLoader.cache.order.networkType))) {
		printMessage(Locale["gethost.label.tips.network.vacancy"]);
		return false;
	}else if(!BootLoader.cache.order.hasVolume || (BootLoader.cache.order.hasVolume=="true" && (!BootLoader.cache.order.volumeName || !BootLoader.cache.order.volumeSize))) {
		printMessage(Locale["gethost.label.tips.volume.vacancy"]);
		return false;
	}else if(BootLoader.cache.order.hasVolume=="true" && !/<!--#echo var='RegExp_Instance'-->/i.test(volumeName)) {
		printMessage(Locale["gethost.label.tips.volume.name.invalid"]);
		return false;
	}else {
		showConfirmDialog();
		return true;
	}
	
}

function goNextStepFrom(whichStep) {
	if(whichStep=="datacenter"){
		showSoftwarePanel(function(){
			// scroll to that
			window.location.hash="";
			window.location.hash="softwarepanel";
		});
		
		hideHardwarePanel();
		hidePlanPanel();
		hideVmNamePanel();
		hideAdditionalPanel();
		
	}else if(whichStep=="software") {
		
		showHardwarePanel(function(){
			// scroll to that
			window.location.hash="";
			window.location.hash="hardwarepanel";
		});
		
		// Hide the following panel due to it's not their turn
		hidePlanPanel();
		hideVmNamePanel();
		hideAdditionalPanel();
		
	}else if(whichStep=="hardware") {
		showPlanPanel(function(){
			// scroll to that
			window.location.hash="";
			window.location.hash="planpanel";
		});
		
		hideVmNamePanel();
		hideAdditionalPanel();
		
	}else if(whichStep=="plan") {
		showVmNamePanel(function(){
			// scroll to that
			window.location.hash="";
			window.location.hash="vmnamepanel";
		});
		
		hideAdditionalPanel();
		
	}else if(whichStep=="vmname") {
		if(BootLoader.cache.order.enterprise) {
			validateOrder();
		}else{
			// go next step of network
			showAdditionalPanel(function(){
				window.location.hash="";
				window.location.hash="networkpanel";
			});
		}
	}
	
}

function showNetworkDetailPanel(callback) {
	// show this panel, when it's turn
	$("[name='networkdetailpanel']").slideDown("fast", function(){
		callback && callback();
	});
}

function hideNetworkDetailPanel() {
	$("[name='networkdetailpanel']").slideUp("fast");
}

function showVolumeDetailPanel(callback) {
	// dump volume size
	var sizelist=$("[name='volumedetailpanel']").find("[zmlm\\\:item='volumesizeList'] select").empty();

	var options="<!--#echo var='volume.size.options' -->";
	options=options.split(",");
	for(var i=0; i<options.length; i++) {
		var key=options[i].substring(0, options[i].indexOf(":"));
		var value=options[i].substring(options[i].indexOf(":")+1);
		$(sizelist).append("<option value='"+key+"'>"+value+"</option>");
	}
	
	// show this panel, when it's turn
	$("[name='volumedetailpanel']").slideDown("fast", function(){
		callback && callback();
	});
}

function hideVolumeDetailPanel() {
	$("[name='volumedetailpanel']").slideUp("fast");
}

function showAdditionalPanel(callback) {
	// show this panel, when it's turn
	$("[name='additionalpanel']").slideDown("fast", function(){
		
		$(this).find("input[type='radio'][name='network']").unbind("change").bind("change", function(){
			if($(this).val()=="true") {
				showNetworkDetailPanel(function(){
					window.location.hash="";
					window.location.hash="networkdetailpanel";
				});
			}else{
				hideNetworkDetailPanel();
			}
			
			BootLoader.log("set network "+$(this).val());
		});
		
		$(this).find("input[type='radio'][name='volume']").unbind("change").bind("change", function(){
			if($(this).val()=="true") {
				showVolumeDetailPanel(function(){
					window.location.hash="";
					window.location.hash="volumedetailpanel";
				});
			}else{
				hideVolumeDetailPanel();
			}
			
			BootLoader.log("set volume "+$(this).val());
		});
		
		// get ip price
		loadIpPrice();
		loadVolumePrice();
		
		callback && callback();
	});
}

function hideAdditionalPanel() {
	$("[name='additionalpanel']").slideUp("fast");
}

function showVmNamePanel(callback) {
	// show this panel, when it's turn
	$("[name='vmnamepanel']").slideDown("fast", function(){
		callback && callback();
	});
}

function hideVmNamePanel() {
	$("[name='vmnamepanel']").slideUp("fast");
}

function showPlanPanel(callback) {
	var pd=showProcessingDialog();
	loadPlan(function() {
		$(pd).dialog("destroy");
		
		// show this panel, when it's turn
		$("[name='planpanel']").slideDown("fast", function(){
			callback && callback();
		});
	});
}

function hidePlanPanel() {
	$("[name='planpanel']").slideUp("fast");
}

function showHardwarePanel(callback) {
	var pd=showProcessingDialog();
	loadHardware(function() {
		$(pd).dialog("destroy");
		
		// show this panel, when it's turn
		$("[name='hardwarepanel']").slideDown("fast", function(){
			callback && callback();
		});
	});
}

function hideHardwarePanel() {
	$("[name='hardwarepanel']").slideUp("fast");
}

function hideSoftwarePanel() {
	$("[name='softwarepanel']").slideUp("fast");
}

function showSoftwarePanel(callback) {
	var pd=showProcessingDialog();
	loadSoftwares(function() {
		$(pd).dialog("destroy");
		
		// show this panel, when it's turn
		$("[name='softwarepanel']").slideDown("fast", function(){
			callback && callback();
		});
	});
}

function showDataCenterPanel(callback) {
	var list=$("#mainPanel").find("[zmlm\\\:item='datacenterList']").empty();
	
	var datacenters=BootLoader.cache.datacenter;
	if(datacenters.length>0){
		$(datacenters).each(function(index, element){
			var itemid=randomid();
			var item=$(["<span style='margin:5px 10px;display:inline-block;' class='hidable'>",
				"<input name='datacenter' id='", itemid, "' type='radio' />", 
				"<label for='", itemid, "'>", element.label, "</label>", 
				"</span>"].join("")).appendTo(list);
				
			$(item).find("input").data("data", element); // bind data to this option
		});
		
		$(list).find("input[name='datacenter']").bind("change", function(){
			var itemdata=$(this).data("data");
			
			BootLoader.log("choosing data center "+itemdata.data);
			
			// stores info into order instance
			BootLoader.cache.order.datacenter=itemdata.data;
			BootLoader.cache.order.provider=itemdata.provider;
			BootLoader.cache.order.datacenterlabel=itemdata.label;
							
			// goto next step
			goNextStepFrom("datacenter");
		});
		
		// show this panel, when it's turn
		$("[name='datacenterpanel']").slideDown("fast", function(){
			callback && callback();
		});
		
	}else{
		printMessage(Locale["gethost.label.tips.datacenter.unavailable"]);
	}
}

function showSaveOrderDialog() {
	var view=$.tmpl("saveOrderDialogTemplate", [{id: "saveOrderDialog"}]).dialog({
		autoOpen: true,
		resizable: false,
		width: 480,
		modal: true,
		closeOnEscape: false,
		open: function() {
			var ui=this;
			$(ui).find("[name='orderAmount']").unbind("change").bind("change", function(){
				var orderAmount=$(this).val();
				var total=orderAmount*((data.hasNetwork=="true"?parseFloat(data.ipprice):0)
					+(data.hasVolume=="true"?parseFloat(data.volumeprice*data.volumeSize):0)
					+(parseFloat(data.planPrice)));
				
				$(ui).find("[name='feeTotal']").html(total);
				
				BootLoader.cache.order.pricetotal=total;
				
				BootLoader.cache.order.orderamount=orderAmount;
				
				BootLoader.log("Order Amount set to "+orderAmount);
				
			}).triggerHandler("change");
		},
		close: function(){BootLoader.log("Closing save as template dialog."); $(this).remove();},
		buttons: [
			{
				text: Locale["gethost.button.order.save"],
				click: function() {
					var ui=this;
					var tname=$(ui).find("[name='templatename']").val()
					var tdesc=$(ui).find("[name='templatedesc']").val();
					
					if(""==$.trim(tname)) {
						printMessage(Locale["gethost.message.type.in.saveorder.template.name"]);
					}else if(confirm(Locale["gethost.confirm.order.save"])) {
						saveOrder(tname, tdesc);
					}
				}
			},
			{
				text: Locale["gethost.dialog.close"],
				click: function() {
					$(this).dialog("close");
				}
			}
		]
	});
	
}

function showConfirmDialog() {
	data=BootLoader.cache.order;
	
	var buttons=[
		{
			text: Locale["gethost.button.order.save"],
			click: function() {
				showSaveOrderDialog();
			}
		},
		{
			text: Locale["gethost.button.order.submit"],
			click: function() {
				if(confirm(Locale["gethost.confirm.order.submit"])) {
					submitOrder();
					$(this).dialog("close");
				}
			}
		},
		{
			text: Locale["gethost.dialog.close"],
			click: function() {
				$(this).dialog("close");
			}
		}
	];
		
	if(!BootLoader.cache.order.enableSaveOrder) {
		buttons.shift()
	}
	
	var view=$.tmpl("confirmDialogTemplate", [data]).dialog({
		autoOpen: true,
		resizable: false,
		width: 600,
		modal: true,
		closeOnEscape: false,
		open: function() {
			var ui=this;
			$(ui).find("[name='orderAmount']").unbind("change").bind("change", function(){
				var orderAmount=$(this).val();
				var addprice=(data.hasNetwork=="true"?calIpPrice(data.ipprice, data.provider):0)
					+(data.hasVolume=="true"?parseFloat(data.volumeprice*data.volumeSize):0);
				var total=orderAmount*((data.enterprise?0:parseFloat(addprice))
					+(parseFloat(data.planPrice)));
				
				$(ui).find("[name='feeTotal']").html(total);
				
				BootLoader.cache.order.pricetotal=total;
				
				BootLoader.cache.order.orderamount=orderAmount;
				
				BootLoader.log("Order Amount set to "+orderAmount);
				
			}).triggerHandler("change");
		},
		close: function(){BootLoader.log("Closing dialog."); $(this).remove();},
		buttons: buttons
	});
}

function submitOrder() {
	var pd=showProcessingDialog();
	var reqArgs, reqUrl, reqCallback;
					
	// dispatch request
	if(BootLoader.cache.order.enterprise){ // private cloud
		reqUrl=Server+"/RedDragonEnterprise/EnterpriseManager";
		reqArgs={
			operation: "requestvm",
			
			vmname: BootLoader.cache.order.vmname,
			softwareid: BootLoader.cache.order.softwareid,
			login: getUsername(),
			sftresourceid: BootLoader.cache.order.softwareresourceid,//planid
			zone: BootLoader.cache.order.datacenter,
			quantity: BootLoader.cache.order.orderamount,
			submissiontime: $.now(),
		};
		
		reqCallback=function(data) {
			try{
				data=$.parseJSON(data);
				
				var infostr="";
				switch(data.status) {
					case "done": {
						infostr=Locale["gethost.page.message.buy.inst.ORDERED"];
						break;
					}
					case "error":
					case "exception": {
						infostr=Locale["gethost.page.message.buy.ERROR"];
						break;
					}
					default: {
						infostr=Locale["gethost.message.undefined"].sprintf(data.status);
					}
				}
				
				printMessage(infostr);
				
			}catch(e){
				printMessage(e);
			}
		};
		
	}else{ // public cloud
		reqUrl=Server+"/RedDragonEnterprise/InstanceCtrlServlet";
		reqArgs={			
			methodType: 'GreenPath',
			loginUser: getUsername(),
			Password: getPassword(),
			zone: BootLoader.cache.order.datacenter,
			SoftwareId: BootLoader.cache.order.softwareid,
			SoftwareResourceID: BootLoader.cache.order.softwareresourceid,
			PaymentAmount: BootLoader.cache.order.pricetotal,
			
			maxCPU: BootLoader.cache.order.mcpu,
			cpu: BootLoader.cache.order.dcpu,
			maxMemo: BootLoader.cache.order.mmem,
			memo: BootLoader.cache.order.dmem,
			instanceName: BootLoader.cache.order.vmname,
			
			doIP: BootLoader.cache.order.hasNetwork,
			userType: BootLoader.cache.order.networkType || "",
			provider: BootLoader.cache.order.provider,
			
			doVolume: BootLoader.cache.order.hasVolume,
			hdSize: BootLoader.cache.order.volumeSize || 0,
			volumeName: BootLoader.cache.order.volumeName,
			
			orderamount: BootLoader.cache.order.orderamount
		};
		
		reqCallback=function(data) {
			try{
	      		var result=$.parseXML(data);
				
				var isok=true;
				
				var orderId=$(result).find('getOrderID').text();
				var instanceResult=$(result).find('instanceResult').text();
				var ipAddressResult=$(result).find('ipAddressResult').text();
				var volumeResult=$(result).find('volumeResult').text();
	  
				var infostr=Locale["gethost.page.message.buy.prefix"];
				switch(orderId.toUpperCase()) {
	                case 'VM_EXCEEDED':
	                  isok=false;
	                  infostr+=Locale["gethost.page.message.buy.VM_EXCEEDED"]+'<br/>';
	                  break;
	                case 'NO_ENOUGH_STOCK':
	                  isok=false;
	                  infostr+=Locale["gethost.page.message.buy.NO_ENOUGH_STOCK"]+'<br/>';
	                  break;
					case 'NOIDORDER':
						isok=false;
						infostr+=Locale["gethost.page.message.buy.NOIDORDER"]+'<br/>';
					case 'ERROR':
						isok=false;
						infostr+=Locale["gethost.page.message.buy.ERROR"]+'<br/>';
					case 'EXCEPTION':
						isok=false;
						infostr+=Locale["gethost.page.message.buy.EXCEPTION"]+'<br/>';
						break;
					case 'LOWBALANCE':
						isok=false;
						infostr+=Locale["gethost.page.message.buy.LOWBALANCE"]+'<br/>';
						break;
					default : {
					}
				}
				
				if(isok){
					infostr=Locale["gethost.page.message.buy.prefix.inst"];
					switch(instanceResult.toUpperCase()) {
	                  	case 'NO_ENOUGH_STOCK':
		                    isok=false;
		                    infostr+=Locale["gethost.page.message.buy.inst.NO_ENOUGH_STOCK"]+'<br/>';
		                    break;
						case 'FAILED':
						case 'ERROR':
						case 'EXCEPTION':
							isok=false;
							infostr+=Locale["gethost.page.message.buy.inst.EXCEPTION"]+'<br/>';
							break;
						case 'NAMENOTAVAILABLE':
							isok=false;
							infostr+=Locale["gethost.page.message.buy.inst.NAMENOTAVAILABLE"]+'<br/>';
							break;
						case 'OSTYPEERROR':
							isok=false;
							infostr+=Locale["gethost.page.message.buy.inst.OSTYPEERROR"]+'<br/>';
							break;
						case 'CREATED':
							infostr+=Locale["gethost.page.message.buy.inst.CREATED"].sprintf(
								["<font style='color:red;'>", Locale["gethost.page.message.buy.inst.CREATED.keyword"], "</font>"].join("")
							)+'<br/>';
							break;
						case 'LOWBALANCE':
							isok=false;
							infostr+=Locale["gethost.page.message.buy.inst.LOWBALANCE"]+'<br/>';
							break;
						case 'ONEVMONLY':
							isok=false;
							infostr+=Locale["gethost.page.message.buy.inst.ONEVMONLY"]+'<br/>';
							break;
						default:{
		                    if(instanceResult.indexOf("{")==0) {
		                      var rets=$.parseJSON(instanceResult);
		                      infostr+=Locale["gethost.page.message.buy.inst.DONE"].sprintf(rets["success"], rets["failure"]);
		                    }else{									
		                      isok=false;
		                      infostr+=Locale["gethost.page.message.buy.inst.UNKNOWN_ERROR"]+'<br/>';
		                    }
						}
					}
				
	                switch(ipAddressResult.toUpperCase()) {
	                  case 'NO_ENOUGH_STOCK':
	                    isok=false;
	                    infostr+=Locale["gethost.page.message.buy.ip.NO_ENOUGH_STOCK"]+'<br/>';
	                    break;
	                  case 'FAILED':
	                  case 'ERROR':
	                  case 'EXCEPTION':
	                    isok=false;
	                    infostr+=Locale["gethost.page.message.buy.ip.EXCEPTION"]+'<br/>';
	                    break;
	                  case 'NOIP':
	                    isok=false;
	                    infostr+=Locale["gethost.page.message.buy.ip.NOIP"]+'<br/>';
	                    break;
	                  case 'WEB':
	                    infostr+=Locale["gethost.page.message.buy.ip.WEB"]+'<br/>';
	                    break;
	                  case 'LOWBALANCE':
	                    isok=false;
	                    infostr+=Locale["gethost.page.message.buy.ip.LOWBALANCE"]+'<br/>';
	                    break;
	                  case 'SUCCESSFUL':
	                  case 'PERSONAL':
	                    infostr+=Locale["gethost.page.message.buy.ip.PERSONAL"].sprintf(
	                    	[
	                    		"<span style='color:red;vertical-align:vertical-align:bottom;font-family:Microsoft Yahei;'>", 
	                    		Locale["gethost.page.message.buy.ip.PERSONAL.keyword"], 
	                    		"</span>"
	                    	].join("")
	                    )+'<br/>';
	                    break;
	                  case 'NO_IP':
	                  case 'NOTBUY':
	                    break;
	                  default:{
	                    if(ipAddressResult.indexOf("{")==0) {
	                      var rets=$.parseJSON(ipAddressResult);
	                      infostr+=Locale["gethost.page.message.buy.ip.BATCH"].sprintf(rets["success"], rets["failure"])+'<br/>';
	                    }else{									
	                      isok=false;
	                      infostr+=Locale["gethost.page.message.buy.ip.UNKNOWN_ERROR"]+'<br/>';
	                    }
	                  }			
	                }
	                
	                switch(volumeResult.toUpperCase()) {
	                  case 'NO_ENOUGH_STOCK':
	                    isok=false;
	                    infostr+=Locale["gethost.page.message.buy.volume.NO_ENOUGH_STOCK"]+'<br/>';
	                    break;
	                  case 'FAILED':
	                  case 'ERROR':
	                  case 'EXCEPTION':
	                    isok=false;
	                    infostr+=Locale["gethost.page.message.buy.volume.EXCEPTION"]+'<br/>';
	                    break;
	                  case 'LOWBALANCE':
	                    isok=false;
	                    infostr+=Locale["gethost.page.message.buy.volume.LOWBALANCE"]+'<br/>';
	                    break;
	                  case 'DONE':
	                    infostr+=Locale["gethost.page.message.buy.volume.DONE"].sprintf(
	                    	["<span style='color:red;font-family:Microsoft Yahei;'>", Locale["gethost.page.message.buy.volume.DONE.keyword"], "</span>"].join("")
	                    )+'<br/>';
	                    break;
	                  case 'NO_VOLUME':
	                  case 'NOTBUY':
	                    break;
	                  default:{
	                    if(volumeResult.indexOf("{")==0) {
	                      var rets=$.parseJSON(volumeResult);
	                      infostr+=Locale["gethost.page.message.buy.volume.BATCH"].sprintf(rets["success"], rets["failure"])+'<br/>';
	                    }else{									
	                      isok=false;
	                      infostr+=Locale["gethost.page.message.buy.volume.UNKNOWN_ERROR"]+'<br/>';
	                    }
	                  }
	                }
				}
				
				if(isok) {
					printMessage(infostr);
				}else{
					printMessage(infostr);
				}
			}catch(e) {
				printMessage(e);
			}
		};
	}
	
	// send request
	$.ajax({
		url: reqUrl,
		cache: false,
		type: "get",
		data: reqArgs,
		success: reqCallback,
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");
		}
	});
	
}

function saveOrder(tname, tdesc) {
	var pd=showProcessingDialog();
	$.ajax({
		url: Server+"/RedDragonEnterprise/orderManagementJson",
		cache: false,
		type: "get",
		data: {
			methodtype: 'savetemplate',
			templatename: tname,
			templatedes: tdesc,
			loginuser: getUsername(),
			needVolume: BootLoader.cache.order.hasVolume,
			needip: BootLoader.cache.order.hasNetwork,
			softwareresourceid: BootLoader.cache.order.softwareresourceid,
			zone: BootLoader.cache.order.datacenter,
			volumesize: BootLoader.cache.order.hasVolume=="true"?BootLoader.cache.order.volumeSize:0,
			iptype: BootLoader.cache.order.networkType || "",
			ipprovider: BootLoader.cache.order.provider
		}, 
		success: function(data) {
			
			try{
				data=$.parseJSON(data);
				
				if(data.status=="done") {
					printMessage(Locale["gethost.page.message.save.success"]);
				}else{
					printMessage(Locale["gethost.page.message.save.failure"]);
				}
			}catch(e){
				printMessage(e);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");			
		}
	});
}

function isPagOrder(planid) {
	if(/^PAG/.test(planid)) {
		return true;
	}else{
		return false;
	}
}

function formatSize(bytes, digit) {
	if(null==bytes || 0==bytes) return "--";
	
	var i=0;
	while(1023 < bytes){
		bytes /= 1024;
		++i;
	};
	return i?bytes.toFixed(digit || 0) + ["", " KB", " MB", " GB", " TB"][i] : bytes + " bytes";
}

function formatPrice(price) {
	if(!!parseFloat(price)) return price;
	else return "-";
}

function randomid() {
	BootLoader.cache._randomid_counter || (BootLoader.cache._randomid_counter=0);
	return (new Date().getTime())+"-"+(BootLoader.cache._randomid_counter++);
}

function dumpOrderAmountList() {
	var tmp=[];
	for(var i=1; i<=parseInt("<!--#echo var='MaxCount_InstancePerOrder'-->"); i++) {
		tmp.push(["<option value='", i, "'>", i, "</option>"].join(""));
	}
	return tmp.join("");
}

function calIpPrice(ipprice, provider) {
	return ipprice*(provider=="BOTH"?2:1);
}

function formatBoolean(b) {
	if(b=="true" || b==true) {
		return Locale["gethost.label.boolean.true"];
	}else if(b=="false" || b==false){
		return Locale["gethost.label.boolean.false"];
	}else {
		return b;
	}
}

function formatNetworkType(type) {
	if(type=="personal") {
		return Locale["gethost.label.network.private"];
	}else if(type=="web") {
		return Locale["gethost.label.network.public"];
	}else{
		return type;
	}
}

function formatVolumeSize(size) {
	return size+" GB";
}

function formatOSIcon(softwarename) {
	if(/win/i.test(softwarename)){
		return '<img style="width:16px;" src="css/image/windows.png"/>';
	}else{
		return '<img style="width:16px;" src="css/image/linux.png"/>';
	}
}
