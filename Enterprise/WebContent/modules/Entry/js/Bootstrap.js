// JavaScript Document
 /*<!--#include virtual="../../../config.shtml" -->*/

$(function(){
	
	if(""==getUsername() || ""==getPassword()) {
		window.top.location.replace("../../");
	}
	
	$("#banner").html(Locale["entry.banner"]);
	$("#entry_title").html(Locale["entry.title"]);
	$("#entry_notice_title").html(Locale["entry.notice.title"]);
	$("#entry_dock_title").html(Locale["entry.dock.title"]);
	
	
	$("#getVM").children("label").html(Locale["entry.getVM"]);
	
	$("#getForm").children("label").html(Locale["entry.getForm"]);
	
	$("#VMMan").children("label").html(Locale["entry.VMMan"]);
	
	$("#formMan").children("label").html(Locale["entry.formMan"]);
	
	$("#VMManEndUser").children("label").html(Locale["entry.VMManEndUser"]);
	
	if("<!--#echo var='IsShowBalance'-->"=="true"){
		showBalance();
	}
	
	// notice what you want
	//$("#entry_notice_content").html("");
	
	/* Senior User */
	
	//vmmanagement
	if(hasModule("vmmanagement")) {
		$("#getVM").delegate(".icon,.iconName","click",function(){
			window.parent.loadModule("modules/Legacy/host_index.html");
		});
		
		$("#VMMan").delegate(".icon,.iconName","click",function(){
			window.parent.loadModule("modules/Instance/index.html");
		});
	}else{
		$("#getVM,#VMMan").hide();
	}
	
	if(hasModule("apptemplatemanagement")) {
		$("#getForm").delegate(".icon,.iconName","click",function(){
			window.parent.loadModule("modules/Bedivere/index.html");
		});
		
		$("#formMan").delegate(".icon,.iconName","click",function(){
			window.parent.loadModule("modules/Bedivere/index.html?action=instance");
		});
	}else{
		$("#getForm,#formMan").hide();
	}
	
	/* Junior User */
	if(hasModule("vmmanagement")) {
		$("#VMManEndUser").delegate(".icon,.iconName","click",function(){
			window.parent.loadModule("modules/Instance/index.html");
		});
	}else{
		$("#VMManEndUser").hide();
	}
});

function showBalance() {
	$("#balance").show().html(Locale["entry.balance"].sprintf(
		"N/A",
		"<font style='color:red;'>"+Locale["entry.balance.loading"]+"</font>"));
	
	if(getRole()=="senior"){
		$.ajax({
			url: Server+"/billingCN/BillingServlet",
			cache: false,
			type: "POST",
			data: {
				RequestType: "getUserInfo",
				Password: getPassword(),
				LoginUsername: getUsername()
			},
			success: function(data){
				try{
					data=$.parseXML("<userinfo>"+data+"</userinfo>");
					
					var bal=parseFloat($(data).find("Balance").text()).toFixed(2);
		
					$("#balance").html(Locale["entry.balance"].sprintf(
						"<font style='color:red;'>"+bal+"</font>",
						"<a href='#' onclick='showBalance();return false;' style='font-size:12px;'>"+Locale["entry.balance.refresh"]+"</a>"));
					
				}catch(e){};
			},
			error: function(jqXHR, textStatus, errorThrown) {
				// hum hum, no consider...
				alert("Can not access user balance from server, contact your administrator!");
			}
		});
	}
}

function appendParams(module) {
	return module+"?"+["user="+getUsername(), "role=is_user"].join("&");
}

