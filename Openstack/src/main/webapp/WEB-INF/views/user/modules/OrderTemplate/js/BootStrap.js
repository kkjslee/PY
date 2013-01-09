// JavaScript Document
// Author: Bill, 2012
 /*<!--#include virtual="../../../config.shtml" -->*/

var _DEBUG_=false;

var Order=function() {
	
	this.enterprise=isEnterpriseVersion();
	
	/*
	this.softwareid=undefined;
	this.datacenter=undefined;
	this.provider=undefined;
	this.hardwareid=undefined;
	this.networkType=undefined;
	this.volumeSize=undefined;
	this.volumeName=undefined;
	*/
	
	
	// debug
	/*
	this.planid="planid-123";
	this.enterprise=false;
	this.hasNetwork="true";
	this.hasVolume="true";
	this.volumeprice=0.5;
	this.volumeSize=1024;
	this.ipprice=50;
	this.volumeName="abc";
	this.orderAmount=1;
	this.networkType="web";
	
	
	// setup debug fields
	this.paymentamount=100;
	this.provider="provider";
	this.softwarename="softwarename";
	this.vmplanid="vmplanid";
	this.vmplanname="vmplanname";
	this.cpu=1;
	this.maxcpu=2;
	this.memory=1024;
	this.maxmemory=2048;
	this.needip=true;
	this.iptype="web";
	this.needvolume=true;
	this.volumesize=1024;
	this.zone="caohejing1";
	this.zonedisplay="chj1";
	*/
	
};

var scriptToLoad=[
	"../../locale.jsp?_module=OrderTemplate",
	"js/template.js"
];

$(function(){
	BootLoader.init();
	
	BootLoader.prepareScriptSerial(scriptToLoad, function(){
		
		registerTemplate();
		
		setup();
		
		initAjax();
		
		initUi();
		
		showCustomGrid();
		
		loadDefaultTemplates(showDefaultTemplates);
		
		loadMyselfTemplates(showMyselfTemplates);
		
	});
});

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function registerTemplate() {
	$.template("mainPanelTemplate", Template_MainPanel);
	$.template("orderGridTemplate", Template_OrderGrid);
	$.template("customGridTemplate", Template_CustomGrid);
	$.template("confirmDialogTemplate", Template_ConfirmDialog);
	$.template("messageBoxTemplate", Template_MessageBox);
}

function setup() {
	$("#mainBody").empty();
	
	var panel=$.tmpl("mainPanelTemplate", [{id:"mainPanel"}]).appendTo("#mainBody");
	
}

function initUi() {
	// jquery ui init
	$("button").button();
	
	$("#banner").html(Locale["ordertemplate.banner"]);
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
				text: Locale["ordertemplate.dialog.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function showProcessingDialog(msg) {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+(msg || Locale["ordertemplate.dialog.processing"])+"</div>").dialog({
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
function loadDefaultTemplates(callback) {
	var pd=showProcessingDialog();	
	return $.ajax({
		url: Server+"/RedDragonEnterprise/orderManagementJson",
		cache: false,
		type: "get",
		data: {
			methodtype: "fetchtemplates",
			templatetype: "real",
			loginuser: "default"
		}, 
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				BootLoader.cache.templates || (BootLoader.cache.templates={});
				BootLoader.cache.templates["default"]=convertRemoteToLocal(data, {removable: false, prompt: true});
				
			}catch(e){
				printMessage(e);
			}
			
			callback && callback();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");
		}
	});	
}

function loadMyselfTemplates(callback) {
	var pd=showProcessingDialog();	
	return $.ajax({
		url: Server+"/RedDragonEnterprise/orderManagementJson",
		cache: false,
		type: "get",
		data: {
			methodtype: "fetchtemplates",
			templatetype: "real",
			loginuser: getUsername()
		}, 
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				BootLoader.cache.templates || (BootLoader.cache.templates={});
				BootLoader.cache.templates["myself"]=convertRemoteToLocal(data, {removable: true, prompt: false});
				
			}catch(e){
				printMessage(e);
			}
			
			callback && callback();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");
		}
	});	
}

function convertRemoteToLocal(data, extra) {
	var retlist=[];
	
	if(data instanceof Array) {
		for(var i=0; i<data.length; i++) {
			var order=new Order();
			
			for(var key in data[i]) {
				order[key]=data[i][key];
			}
			
			for(var key in extra) {
				order[key]=extra[key];
			}
			
			retlist.push(order);
		}
	}else{
		BootLoader.log("cannot convert remote data coz it's not an array object");
	}
	
	return retlist;
}

function showDefaultTemplates() {
	var orderlist=BootLoader.cache.templates["default"];
	
	for(var i=0; orderlist && i<orderlist.length; i++) {
		var item=$.tmpl("orderGridTemplate", orderlist[i]).appendTo($("#mainPanel").find("[name='defaultTemplateContainer']"));
		$(item).data("data", orderlist[i]); // bind up data to ui
	}
}

function showMyselfTemplates() {
	var orderlist=BootLoader.cache.templates["myself"];
	
	for(var i=0; orderlist && i<orderlist.length; i++) {
		var item=$.tmpl("orderGridTemplate", orderlist[i]).appendTo($("#mainPanel").find("[name='myselfTemplateContainer']"));
		$(item).data("data", orderlist[i]); // bind up data to ui
	}
}

function showCustomGrid() {
	var item=$.tmpl("customGridTemplate", [{}]).appendTo($("#mainPanel").find("[name='customGridContainer']"));
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

function removeOrderTemplate(which) {
	var uigrid=$(which).parents(".order-grid").first();
	var order=$(uigrid).data("data");
	
	BootLoader.log("Going to remove template "+order.templateid);
	
	if(confirm(Locale["ordertemplate.confirm.order.remove"])) {
		var pd=showProcessingDialog();
		$.ajax({
			url: Server+"/RedDragonEnterprise/orderManagementJson",
			cache: false,
			data: {
				methodtype: "deletetemplate",
				templateid: order.templateid
			},
			success: function(data) {
				try{
					data=$.parseJSON(data);
					
					var msg="";
					switch(data.status) {
						case "done": {
							$(uigrid).fadeOut(600, function(){$(this).remove();});
							msg=Locale["ordertemplate.message.remove.done"];
							break;
						}
						case "failed": msg=Locale["ordertemplate.message.remove.failed"]; break;
						case "exception": msg=Locale["ordertemplate.message.error"]; break;
						default: msg=Locale["ordertemplate.message.undefined"].sprintf(data.status);
					}
					
					printMessage(msg);
					
				}catch(e) {
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
}

function checkVmName(callback) {
	var vmname=BootLoader.cache.order.vmname;
	
	if(!/<!--#echo var='RegExp_Instance'-->/i.test(vmname)) {printMessage(Locale["ordertemplate.label.tips.vmname.invalid"]);return;}
	
	var pd=showProcessingDialog("正在检索VM名，请稍候...");
	return $.ajax({
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
					// i destroy dialog here coz it should open confirm dialog after processing dialog closed
					$(pd).dialog("destroy").promise().done(function(){
						callback && callback();
					});
				}else{
					printMessage(Locale["ordertemplate.label.tips.vmname.unavailable"]);
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

function validateOrder(container, callback) {
	var volumeName=$(container).find("input[name='volumename']").val();
	var vmName=$(container).find("input[name='vmname']").val();
	
	BootLoader.cache.order.volumeName=volumeName;
	BootLoader.cache.order.vmname=vmName; // store VM name to cache, but check it according ajax later
	
	checkVmName(function(){
		if(BootLoader.cache.order.hasVolume=="true" && (!BootLoader.cache.order.volumeName || !BootLoader.cache.order.volumeSize)) {
			printMessage(Locale["ordertemplate.label.tips.volume.vacancy"]);
			return false;
		}else if(BootLoader.cache.order.hasVolume=="true" && !/<!--#echo var='RegExp_Instance'-->/i.test(volumeName)) {
			printMessage(Locale["ordertemplate.label.tips.volume.name.invalid"]);
			return false;
		}else {
			callback && callback();
			return true;
		}
	});
}

function showConfirmDialog(which) {
	var order=$(which).parents(".order-grid").first().data("data");
	
	BootLoader.cache.order=order; // set it to present order cache
	
	// TODO: re-code this section and template fields when servlet is available
	var view=$.tmpl("confirmDialogTemplate", [order]).dialog({
		autoOpen: true,
		resizable: false,
		width: 600,
		modal: true,
		closeOnEscape: false,
		open: function() {
			var ui=this;
			$(ui).find("[name='orderAmount']").unbind("change").bind("change", function(){
				var orderAmount=$(this).val();
				var addprice=(order.needip==true?calIpPrice(order.ipprice, order.provider):0)
					+(order.needvolume==true?parseFloat(order.volumeprice*order.volumesize):0);
				var total=orderAmount*((order.enterprise?0:parseFloat(addprice))+parseFloat(order.vmplanprice));
				
				$(ui).find("[name='feeTotal']").html(total);
				
				order.pricetotal=total;
				
				order.orderamount=orderAmount;
				
				BootLoader.log("Order Amount set to "+orderAmount);
			}).triggerHandler("change");
		},
		close: function(){BootLoader.log("Closing dialog."); $(this).remove();},
		buttons: [
			{
				text: Locale["ordertemplate.button.order.submit"],
				click: function() {
					// check order form first, and then submit it
					if(validateOrder(this, function(){
						if(confirm(Locale["ordertemplate.confirm.order.submit"])) {
							submitOrder();
							$(this).dialog("close");
						}
					}));
				}
			},
			{
				text: Locale["ordertemplate.dialog.close"],
				click: function() {
					$(this).dialog("close");
				}
			}
		]
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
			zone: BootLoader.cache.order.zone,
			quantity: BootLoader.cache.order.orderamount,
			submissiontime: $.now(),
		};
		
		reqCallback=function(data) {
			try{
				data=$.parseJSON(data);
				
				var infostr="";
				switch(data.status) {
					case "done": {
						infostr=Locale["ordertemplate.page.message.buy.inst.ORDERED"];
						break;
					}
					case "error":
					case "exception": {
						infostr=Locale["ordertemplate.page.message.buy.ERROR"];
						break;
					}
					default: {
						infostr=Locale["ordertemplate.message.undefined"].sprintf(data.status);
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
			zone: BootLoader.cache.order.zone,
			SoftwareId: BootLoader.cache.order.softwareid,
			SoftwareResourceID: BootLoader.cache.order.softwareresourceid,
			PaymentAmount: BootLoader.cache.order.pricetotal,
			
			maxCPU: BootLoader.cache.order.maxcpu,
			cpu: BootLoader.cache.order.cpu,
			maxMemo: BootLoader.cache.order.maxmemory,
			memo: BootLoader.cache.order.memory,
			instanceName: BootLoader.cache.order.vmname,
			
			doIP: BootLoader.cache.order.needip,
			userType: BootLoader.cache.order.iptype || "",
			provider: BootLoader.cache.order.provider,
			
			doVolume: BootLoader.cache.order.needvolume,
			hdSize: BootLoader.cache.order.volumesize || 0,
			volumeName: BootLoader.cache.order.volumename,
			
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
	  
				var infostr=Locale["ordertemplate.page.message.buy.prefix"];
				switch(orderId.toUpperCase()) {
	                case 'VM_EXCEEDED':
	                  isok=false;
	                  infostr+=Locale["ordertemplate.page.message.buy.VM_EXCEEDED"]+'<br/>';
	                  break;
	                case 'NO_ENOUGH_STOCK':
	                  isok=false;
	                  infostr+=Locale["ordertemplate.page.message.buy.NO_ENOUGH_STOCK"]+'<br/>';
	                  break;
					case 'NOIDORDER':
						isok=false;
						infostr+=Locale["ordertemplate.page.message.buy.NOIDORDER"]+'<br/>';
					case 'ERROR':
						isok=false;
						infostr+=Locale["ordertemplate.page.message.buy.ERROR"]+'<br/>';
					case 'EXCEPTION':
						isok=false;
						infostr+=Locale["ordertemplate.page.message.buy.EXCEPTION"]+'<br/>';
						break;
					case 'LOWBALANCE':
						isok=false;
						infostr+=Locale["ordertemplate.page.message.buy.LOWBALANCE"]+'<br/>';
						break;
					default : {
					}
				}
				
				if(isok){
					infostr=Locale["ordertemplate.page.message.buy.prefix.inst"];
					switch(instanceResult.toUpperCase()) {
	                  	case 'NO_ENOUGH_STOCK':
		                    isok=false;
		                    infostr+=Locale["ordertemplate.page.message.buy.inst.NO_ENOUGH_STOCK"]+'<br/>';
		                    break;
						case 'FAILED':
						case 'ERROR':
						case 'EXCEPTION':
							isok=false;
							infostr+=Locale["ordertemplate.page.message.buy.inst.EXCEPTION"]+'<br/>';
							break;
						case 'NAMENOTAVAILABLE':
							isok=false;
							infostr+=Locale["ordertemplate.page.message.buy.inst.NAMENOTAVAILABLE"]+'<br/>';
							break;
						case 'OSTYPEERROR':
							isok=false;
							infostr+=Locale["ordertemplate.page.message.buy.inst.OSTYPEERROR"]+'<br/>';
							break;
						case 'CREATED':
							infostr+=Locale["ordertemplate.page.message.buy.inst.CREATED"].sprintf(
								["<font style='color:red;'>", Locale["ordertemplate.page.message.buy.inst.CREATED.keyword"], "</font>"].join("")
							)+'<br/>';
							break;
						case 'LOWBALANCE':
							isok=false;
							infostr+=Locale["ordertemplate.page.message.buy.inst.LOWBALANCE"]+'<br/>';
							break;
						case 'ONEVMONLY':
							isok=false;
							infostr+=Locale["ordertemplate.page.message.buy.inst.ONEVMONLY"]+'<br/>';
							break;
						default:{
		                    if(instanceResult.indexOf("{")==0) {
		                      var rets=$.parseJSON(instanceResult);
		                      infostr+=Locale["ordertemplate.page.message.buy.inst.DONE"].sprintf(rets["success"], rets["failure"]);
		                    }else{									
		                      isok=false;
		                      infostr+=Locale["ordertemplate.page.message.buy.inst.UNKNOWN_ERROR"]+'<br/>';
		                    }
						}
					}
				
	                switch(ipAddressResult.toUpperCase()) {
	                  case 'NO_ENOUGH_STOCK':
	                    isok=false;
	                    infostr+=Locale["ordertemplate.page.message.buy.ip.NO_ENOUGH_STOCK"]+'<br/>';
	                    break;
	                  case 'FAILED':
	                  case 'ERROR':
	                  case 'EXCEPTION':
	                    isok=false;
	                    infostr+=Locale["ordertemplate.page.message.buy.ip.EXCEPTION"]+'<br/>';
	                    break;
	                  case 'NOIP':
	                    isok=false;
	                    infostr+=Locale["ordertemplate.page.message.buy.ip.NOIP"]+'<br/>';
	                    break;
	                  case 'WEB':
	                    infostr+=Locale["ordertemplate.page.message.buy.ip.WEB"]+'<br/>';
	                    break;
	                  case 'LOWBALANCE':
	                    isok=false;
	                    infostr+=Locale["ordertemplate.page.message.buy.ip.LOWBALANCE"]+'<br/>';
	                    break;
	                  case 'SUCCESSFUL':
	                  case 'PERSONAL':
	                    infostr+=Locale["ordertemplate.page.message.buy.ip.PERSONAL"].sprintf(
	                    	[
	                    		"<span style='color:red;vertical-align:vertical-align:bottom;font-family:Microsoft Yahei;'>", 
	                    		Locale["ordertemplate.page.message.buy.ip.PERSONAL.keyword"], 
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
	                      infostr+=Locale["ordertemplate.page.message.buy.ip.BATCH"].sprintf(rets["success"], rets["failure"])+'<br/>';
	                    }else{									
	                      isok=false;
	                      infostr+=Locale["ordertemplate.page.message.buy.ip.UNKNOWN_ERROR"]+'<br/>';
	                    }
	                  }			
	                }
	                
	                switch(volumeResult.toUpperCase()) {
	                  case 'NO_ENOUGH_STOCK':
	                    isok=false;
	                    infostr+=Locale["ordertemplate.page.message.buy.volume.NO_ENOUGH_STOCK"]+'<br/>';
	                    break;
	                  case 'FAILED':
	                  case 'ERROR':
	                  case 'EXCEPTION':
	                    isok=false;
	                    infostr+=Locale["ordertemplate.page.message.buy.volume.EXCEPTION"]+'<br/>';
	                    break;
	                  case 'LOWBALANCE':
	                    isok=false;
	                    infostr+=Locale["ordertemplate.page.message.buy.volume.LOWBALANCE"]+'<br/>';
	                    break;
	                  case 'DONE':
	                    infostr+=Locale["ordertemplate.page.message.buy.volume.DONE"].sprintf(
	                    	["<span style='color:red;font-family:Microsoft Yahei;'>", Locale["ordertemplate.page.message.buy.volume.DONE.keyword"], "</span>"].join("")
	                    )+'<br/>';
	                    break;
	                  case 'NO_VOLUME':
	                  case 'NOTBUY':
	                    break;
	                  default:{
	                    if(volumeResult.indexOf("{")==0) {
	                      var rets=$.parseJSON(volumeResult);
	                      infostr+=Locale["ordertemplate.page.message.buy.volume.BATCH"].sprintf(rets["success"], rets["failure"])+'<br/>';
	                    }else{									
	                      isok=false;
	                      infostr+=Locale["ordertemplate.page.message.buy.volume.UNKNOWN_ERROR"]+'<br/>';
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

function isPagOrder(planid) {
	if(/^PAG/.test(planid)) {
		return true;
	}else{
		return false;
	}
}

function gotoCustomPage() {
	$("#mainPanel").fadeOut().promise().done(function(){
		window.location.href="../GetHost/";
	});
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
	// ipprice should multiply 30*24 here, coz it's in hour
	// and then make it ignore float point
	return (parseInt(ipprice*30*24))*(provider=="BOTH"?2:1);
}

function formatBoolean(b) {
	if(b=="true" || b==true) {
		return Locale["ordertemplate.label.boolean.true"];
	}else if(b=="false" || b==false){
		return Locale["ordertemplate.label.boolean.false"];
	}else {
		return b;
	}
}

function formatNetworkType(type) {
	if(type=="personal") {
		return Locale["ordertemplate.label.network.private"];
	}else if(type=="web") {
		return Locale["ordertemplate.label.network.public"];
	}else{
		return type;
	}
}

function formatVolumeSize(size) {
	return size+" GB";
}

function formatGridIcon(softwarename) {
	if(/win/i.test(softwarename)){
		return '<img style="width:64px;" src="css/image/windows.png"/>';
	}else{
		return '<img style="width:64px;" src="css/image/linux.png"/>';
	}
}



