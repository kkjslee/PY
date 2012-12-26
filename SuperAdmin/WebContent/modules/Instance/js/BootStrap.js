// JavaScript Document
// Author: Bill, 2011~2012
 /*<!--#include virtual="../../../config.shtml" -->*/

var _DEBUG_=false;

$(function(){
	if(readPersonalInfo(getUsername(), "Instance")==="simple") {
		$(document.head).append('<link href="css/simple.css" rel="stylesheet" type="text/css" />');
	}else {
		$(document.head).append('<link href="css/main.css" rel="stylesheet" type="text/css" />');
	}
	
	BootLoader.init();
	
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
	$.template("vmPanelTemplate", Template_VmPanel);
	$.template("vmRowTemplate", Template_VmRow);
	$.template("vmDetailPanelTemplate", Template_VmDetailPanel);
	$.template("chartPanelTemplate", Template_ChartPanel);
	$.template("messageBoxTemplate", Template_MessageBox);
	$.template("migratePanelTemplate", Template_MigratePanel);
	$.template("clonePanelTemplate", Template_ClonePanel);
}

// ----------- Context Menu ------------
function showContextMenu(e, ui) {
	e.preventDefault();		
	e.stopPropagation();
	
	var menu=$("#contextmenu").length>0?$("#contextmenu"):makeContextMenu();
	//$(menu).css("left", e.pageX).css("top", e.pageY);
	//$(menu).animate({left: e.pageX, top: e.pageY, }, {duration:200, queue:false});
	$(menu).data("target", e.target);
	$(menu).data("_x", e.pageX);
	$(menu).data("_y", e.pageY);
	$(menu).triggerHandler("show");
}

function hideContextMenu(e) {
	if($(e.target).parents("#contextmenu").andSelf().filter("#contextmenu").length==0) {
		$("#contextmenu").triggerHandler("hide");
	}
}

function makeContextMenu() {
	$("head").append("<style>.contextMenu {font-family:Arial,'微软雅黑',Helvetica,sans-serif;font-size:12px;position:absolute;top:0;left:0;padding:0;background:white;display:inline-block;width:132px;border:1px solid silver;z-index:24;-webkit-box-shadow: rgb(120, 120, 120) 2px 2px 4px;-moz-box-shadow: rgb(120, 120, 120) 2px 2px 4px;-ms-box-shadow: rgb(120, 120, 120) 2px 2px 4px;-o-box-shadow: rgb(120, 120, 120) 2px 2px 4px;}</style>");
	$("head").append("<style>.contextMenuItem {-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;-o-user-select:none;display:list-item;line-height:22px;height:22px;padding:2px;cursor:default;font-size:12px;line-height:22px;white-space:nowrap;list-style:none;text-align:-webkit-match-parent;padding:0;margin:0;} .contextMenuItem > label{display:inline-block;line-height:20px;vertical-align:bottom;}</style>");
	$("head").append("<style>.contextMenuItemIcon {vertical-align:middle;display:inline-block;margin-right:10px;margin-left:5px;border:0;width:16px;height:16px;}</style>");
	$("head").append("<style>.contextMenuItemIconDetail {background:url(css/image/context-menu-properties.png);}</style>");
	$("head").append("<style>.contextMenuItemIconCamera {background:url(css/image/context-menu-camera.png);}</style>");
	$("head").append("<style>.contextMenuItemIconFancyVNC {background:url(css/image/context-menu-fancy-vnc.png);}</style>");
	$("head").append("<style>.contextMenuItemHighlight {background:url(css/image/context-menu-highlight.png) 0 bottom repeat-x;} .contextMenuItemHighlight>label{color:#ffffff;}</style>");
	$("head").append("<!--[if IE]><style>.contextMenuItemHighlight {background:#224488;} .contextMenuItemHighlight>label{color:#ffffff;}</style><![endif]-->");
	
	// construct context menu body
	var menu=$("<span id='contextmenu' class='contextMenu'></span>").appendTo("body").hide();
	
	$(menu).bind("show", function(){
		var x=$(menu).data("_x");
		var y=$(menu).data("_y");
		
		if($(menu).is(":visible")) {
			$(menu).slideUp(100, function(){
				$(menu).css("left", x).css("top", y);
				$(menu).slideDown(200);
			});
		}else{
			$(menu).css("left", x).css("top", y);
			$(menu).slideDown(200);
		}
	}).bind("hide", function(){
		$(menu).slideUp(100);
	});
	
	$(menu).delegate(".contextMenuItem","mouseover",function(){
		$(this).addClass("contextMenuItemHighlight");
	}).delegate(".contextMenuItem","mouseout", function(){
		$(this).removeClass("contextMenuItemHighlight");
	});
	
	// construct items
	var itemDetail=$("<span class='contextMenuItem'><span class='contextMenuItemIcon contextMenuItemIconDetail'></span><label>"+Locale["vm.template.vm.detail"]+"</label></span>").appendTo(menu);
	$(itemDetail).bind("click", function(){
		$(menu).hide();
		var target=$(menu).data("target");
		detail(target);
	});
	
	var itemVNC=$("<span class='contextMenuItem'><span class='contextMenuItemIcon contextMenuItemIconFancyVNC'></span><label>Fancy-VNC (Lab)</label></span>").appendTo(menu);
	$(itemVNC).bind("click", function(){
		$(menu).hide();
		var target=$(menu).data("target");
		var data=$(target).parents(".vmRow").andSelf().data("data");
		
		try{
			window.open(getWebVNCLink(data));
		}catch(e){
			//maybe creating or not exist
		}
	});
	
	return $(menu);
}
// ----------- Context Menu ------------

function getWebVNCLink(data) {
	var host=decodeURI((RegExp('HOST=' + '(.+?)(&|$)').exec(data.accesspoint.substring(data.accesspoint.indexOf("?")))||[,null])[1]);
	var port=decodeURI((RegExp('PORT=' + '(.+?)(&|$)').exec(data.accesspoint.substring(data.accesspoint.indexOf("?")))||[,null])[1]);
	var timestamp=$.now();
	
	var key="key="+[Base64.encode(host),Base64.encode(port),Base64.encode(""+timestamp)].join("ZZ");
	
	return "../WebVNC/vnc.jsp?"+key;
}

function setup() {
	$("#mainBody").empty();
	
	var panel=$.tmpl("vmPanelTemplate", [{id:"vmPanel"}]).appendTo("#mainBody");
	
	var detailPanel=$.tmpl("vmDetailPanelTemplate", [{id:"vmDetailPanel"}]).appendTo("#mainBody");
	
	// set up highlight & selection effect for [task]
	$(panel).delegate(".vmRow", "mouseover", function() {
		$(this).addClass("hover");
	}).delegate(".vmRow", "mouseout", function() {
		$(this).removeClass("hover");
	}).delegate(".vmRow", "contextmenu", function(e) {
		showContextMenu(e, this);
	});
	
	// set up detail panel dialog
	$(detailPanel).dialog({
		title: Locale["vm.dialog.title.detail"],
		autoOpen: false,
		width: 600,
		resizable: false,
		modal: true,
		buttons: [
			{
				text: Locale["vm.dialog.close"],
				click: function() {
					$(this).dialog("close");
				}
			}
		]
	});
	
	$(panel).delegate("[name='filter_vmname'],[name='filter_vmzone'],[name='filter_vmpvip'],[name='filter_vmuser']", "keyup", function(event) {
		if(event.keyCode==13) {filter(panel);}
	});

	// init table to dataTable
	var dataTable=$(panel).find("[zmlm\\\:item='vmList']");
	$(dataTable).dataTable(BootLoader.conf.dataTable(dataTable));
	
	// load data to display
	loadVm();
}

function filter(panel) {
	var rows=$(panel).find(".vmRow");
	var vmprefix=$.trim($(panel).find("[name='filter_vmname']").val().toLowerCase());
	var zoneprefix=$.trim($(panel).find("[name='filter_vmzone']").val().toLowerCase());
	var userprefix=$.trim($(panel).find("[name='filter_vmuser']").val().toLowerCase());
	
	var column=[0,1,2];
	$([vmprefix, zoneprefix, userprefix]).each(function(index, element){
		$(panel).find("[zmlm\\\:item='vmList']").dataTable().fnFilter(element,column[index],true,true,null,true);
	});
}

function initUi() {
	// jquery ui init
	$("button").button();
	
	$("#banner").html(Locale["vm.banner"]);
	
	$(document).click(function(e){
		hideContextMenu(e);
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

function printError(jqXHR, textStatus, errorThrown) {
	printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}

function printMessage(msg) {
	return $.tmpl("messageBoxTemplate", [{message: msg}]).appendTo("#mainBody").dialog({
		resizable: false,
		modal: true,
		buttons: [
			{
				text: Locale["vm.dialog.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function showProcessingDialog() {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+Locale["vm.dialog.processing"]+"</div>").dialog({
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
function loadVm() {
	var list=$("#vmPanel").find("[zmlm\\\:item='vmList'] tbody").empty();
	$("<em class='vmTipsLabel'>"+Locale["vm.message.loading"]+"</em>").appendTo(list);
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "vmdetails",
			matchflag: "all",
			loginuser: getUsername()
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				// clear table before inserting new data
				var dataTable=$(list).parents("table").first();
				$(dataTable).dataTable().fnDestroy();
				
				if(data.length>0){
					$(list).empty();
					
					// instance it, and bind data by the way
					for(var i=0; i<data.length; i++) {
						var row=$.tmpl("vmRowTemplate", data[i]).appendTo(list);
						row.data("data", data[i]);
					}
					
					$(list).find("[name='migrateopt']").bind("change", function(){
						$(this).val($(this).attr("default"));
						window.console && window.console.log("Migrate to ...");
						
						var vmid=$(this).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
						var vmzone=$(this).parents(".vmRow").first().find("input[zmlm\\\:item='vmZone']").val();
						var vmzonedisplay=$(this).parents(".vmRow").first().find("input[zmlm\\\:item='vmZonedisplay']").val();
						
						showMigrateDialog(vmid, vmzone, vmzonedisplay, $(this).attr("default"), this);
					});
					
				}else{
					$("<em class='vmTipsLabel'>"+Locale["vm.message.no_data"]+"</em>").appendTo($(list).empty());
				}
				
				// (re)initialize the table
				$(dataTable).dataTable(BootLoader.conf.dataTable(dataTable));
				
				// trigger filter	
				filter($("#vmPanel"));
					
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function cloneVM(vmid, vmname, userpass) {
	if(!/<!--#echo var='RegExp_Instance'-->/i.test(vmname)) {printMessage(Locale["vm.message.clone.tips.vmname"]);return;}
	
	if(confirm(Locale["vm.confirm.clone"].sprintf(vmname))) {
		var pd=showProcessingDialog();
		$.ajax({
			type: "post",
			url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
			cache: false,
			data: {
				methodtype: "launchsimilarvm",
				vmuuid: vmid,
				newvmname: vmname,
				passwd: userpass
			},
			success: function(data) {
				try{
					data=$.parseJSON(data);
					
					switch(data.status) {
						case "done": msg=Locale["vm.message.clone.done"]; break;
						case "vmnotexist": msg=Locale["vm.message.clone.vmnotexists"]; break;
						case "failed":
						case "error":
						case "exception": msg=Locale["vm.message.clone.error"]; break;
						default: msg=Locale["vm.message.undefined"].sprintf(data.status);
					}
					
					// print message
					printMessage(msg);
					
					// refresh
					loadVm();
					
				}catch(e){printMessage("Data broken: "+e);}
			},
			failure: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			},
			complete: function(jqXHR, textStatus) {
				$(pd).dialog("destroy");
			}
		});
	}
}

function showCloneDialog(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var vmuser=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmUser']").val();
	
	// fill in template vm info
	var pd=showProcessingDialog();
	$.ajax({
		type:"post",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "displayvmtemplate",
			vmuuid: vmid
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				// init panel
				var panel=$("#clonePanel");
				panel && delete panel;
				panel=$.tmpl("clonePanelTemplate", [{id: "clonePanel"}]).appendTo("#mainBody").dialog({
					resizable: false,
					modal: true,
					autoOpen: false,
					width: 480,
					buttons: [
						{
							text: Locale["vm.dialog.clone"],
							click: function() {
								var userpass="<!--#echo var='SuperPassword' -->"; // get user password to clone
								var vmname=$(panel).find(".vmname .value input").val();
								
								cloneVM(vmid, vmname, userpass);
							}
						},
						{
							text: Locale["vm.dialog.close"],
							click: function() {
								$(this).dialog("destroy");
							}
						}
					]
				});
				
				// fill in info
				$(panel).find(".vmtemp .value").html(data.basevm);
				$(panel).find(".vmuser .value").html(vmuser); // only display in SuperAdmin
				$(panel).find(".vmname input").val(""); // empty input of vmname
				$(panel).find(".vmzone .value").html(data.zonedisplay);
				$(panel).find(".vmcpu .value").html([data.cpu," ",Locale["vm.template.clone.unit.cpu"]," / ",data.maxcpu," ",Locale["vm.template.clone.unit.cpu"]].join(""));
				$(panel).find(".vmmem .value").html([formatSize(parseFloat(data.memory)*1024,2), " / ", formatSize(parseFloat(data.maxmemory)*1024,2)].join(""));
				$(panel).find(".vmsoft .value").html(data.softwarename);
				$(panel).find(".vmplan .value").html(data.vmplanname);
				$(panel).find(".vmprice .value").html(parseFloat(data.paymentamount).toFixed(2));
	
				// show dialog
				$(panel).dialog("open");
				
			}catch(e){printMessage("Data broken: "+e);}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		},
		complete: function(jqXHR, textStatus) {
			$(pd).dialog("destroy");
		}
	});
	
}

function showMigrateDialog(vmid, vmzone, vmzonedisplay, formerServer, view) {
	var panel=$("#migratePanel");
	panel && delete panel;
	
	panel=$.tmpl("migratePanelTemplate", [{id: "migratePanel"}]).appendTo("#mainBody").dialog({
		resizable: false,
		modal: true,
		autoOpen: false,
		height: 120,
		buttons: [
			{
				text: Locale["vm.dialog.apply"],
				click: function() {
					var target=$(panel).find("[name='targetServer']").val();
					
					if(confirm(Locale["vm.confirm.migrate"].sprintf(formerServer, target))) {
						migrate(vmid, target, view);
						
						$(this).dialog("destroy");
					}
				}
			},
			{
				text: Locale["vm.dialog.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
	
	var poolscontainer=$(panel).find("[name='targetServer']").empty().append("<option value=''>"+Locale["vm.message.loading"]+"</option>");
	if(BootLoader.cache[vmzone]) {
		var data=BootLoader.cache[vmzone];
		var groups=$("<optgroup label='"+vmzonedisplay+"'></optgroup>").appendTo(poolscontainer.empty());
		for(var i=0; i<data.length; i++) {
			var ip=data[i].ip;
			(formerServer!=ip) && $(groups).append(["<option value='", ip, "'>", ip, "</option>"].join(""));
		}
	}else{
		$.ajax({
			url: Server+"/RedDragonEnterprise/VMPoolManagement",
			type: "POST",
			data: {
				methodtype: "fetchvmpools",
				loginuser: getUsername(),
				zone: vmzone
			},
			cache: false,
			success: function(data) {
				try {
					data=$.parseJSON(data);
					
					// try to cache the data
					BootLoader.cache[vmzone]=data;
					
					var groups=$("<optgroup label='"+vmzonedisplay+"'></optgroup>").appendTo(poolscontainer.empty());
					for(var i=0; i<data.length; i++) {
						var ip=data[i].ip;
						(formerServer!=ip) && $(groups).append(["<option value='", ip, "'>", ip, "</option>"].join(""));
					}
					
				}catch(e) {printMessage("Data Broken: ["+e+"]");}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			}
		});
	}
	
	$(panel).dialog("open");
	
}

function migrate(vmid, target, view) {
	var pd=showProcessingDialog();
	
	$.ajax({
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		type: "post",
		cache: false,
		data: {
			methodtype: "migratevm",
			vmid: vmid,
			targetserverip: target
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": {
						msg=Locale["vm.message.migrate.done"];
						// if success, refresh the component
						$(view).attr("default", target).children().first().replaceWith("<option value='"+target+"'>"+target+"</option>");
						break;
					}
					case "vmnotexists": Locale["vm.message.migrate.vmnotexists"]; break;
					case "vmnotoff": Locale["vm.message.migrate.vmnotoff"]; break;
					case "resourcenotenough": Locale["vm.message.migrate.resourcenotenough"]; break;
					case "failed":
					case "error":
					case "exception": msg=Locale["vm.message.migrate.error"]; break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {printError(jqXHR, textStatus, errorThrown);},
		complete: function(jqXHR, textStatus) {pd.dialog("destroy");}
	});
}

function unassign(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var vmAssignedTo=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmAssignedTo']").val();
	
	if(!confirm(Locale["vm.confirm.unassign"])) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/GroupManager",
		cache: false,
		data: {
			methodtype: "assignvm",
			loginuser: getUsername(),
			sublogin: vmAssignedTo,
			vmid: vmid,
			status: "false"
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.unassign.done"];break;
					case "error": ;
					case "exception": msg=Locale["vm.message.unassign.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
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

function resetpass(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var vmuser=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmUser']").val();
	var vmname=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmName']").val();
	
	if(!confirm(Locale["vm.confirm.reset.password"].sprintf(vmname))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "rmvmpasswd",
			loginuser: getUsername(),
			vmuuid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=[Locale["vm.message.done"], Locale["vm.message.resetpass.tips"]].join("<br/>");break;
					case "failed": ;
					case "error": ;
					case "exception": msg=Locale["vm.message.error"];break;
					case "vmnotexists": msg=Locale["vm.message.notexist"]; break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
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

function poweron(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var zone=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmZone']").val();
	var vmname=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmName']").val();
	
	if(!confirm(Locale["vm.confirm.power.on"].sprintf(vmname))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "executecommand",
			loginuser: getUsername(),
			password: getPassword(),
			executecommand: "poweron",
			zone: zone,
			vmid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.done"];break;
					case "error": ;
					case "exception": msg=Locale["vm.message.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
				
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

function poweroff(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var zone=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmZone']").val();
	var vmname=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmName']").val();
	
	if(!confirm(Locale["vm.confirm.power.off"].sprintf(vmname))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "executecommand",
			loginuser: getUsername(),
			password: getPassword(),
			executecommand: "poweroff",
			zone: zone,
			vmid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.done"];break;
					case "error": ;
					case "exception": msg=Locale["vm.message.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
				
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

function pause(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var zone=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmZone']").val();
	var vmname=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmName']").val();
	
	if(!confirm(Locale["vm.confirm.pause"].sprintf(vmname))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "executecommand",
			loginuser: getUsername(),
			password: getPassword(),
			executecommand: "pause",
			zone: zone,
			vmid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.done"];break;
					case "error": ;
					case "exception": msg=Locale["vm.message.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
				
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

function resume(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var zone=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmZone']").val();
	var vmname=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmName']").val();
	
	if(!confirm(Locale["vm.confirm.resume"].sprintf(vmname))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "executecommand",
			loginuser: getUsername(),
			password: getPassword(),
			executecommand: "resume",
			zone: zone,
			vmid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.done"];break;
					case "error": ;
					case "exception": msg=Locale["vm.message.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
				
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

function remove(which) {
	var vmid=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmId']").val();
	var zone=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmZone']").val();
	var vmname=$(which).parents(".vmRow").first().find("input[zmlm\\\:item='vmName']").val();
	
	if(!confirm(Locale["vm.confirm.vm.remove"].sprintf(vmname))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "removevm",
			loginuser: getUsername(),
			password: getPassword(),
			zone: zone,
			vmid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.remove.done"];break;
					case "failtostop": msg=Locale["vm.message.remove.failtostop"];break;
					case "vlanmember": msg=Locale["vm.message.remove.vlanmember"];break;
					case "backupjobexists": msg=Locale["vm.message.remove.backupjobexists"];break;
					case "failtodelete": ;
					case "exception": msg=Locale["vm.message.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
				
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

function detail(which) {
	var vmid=$(which).parents(".vmRow").andSelf().first().find("input[zmlm\\\:item='vmId']").val();
	var zone=$(which).parents(".vmRow").andSelf().first().find("input[zmlm\\\:item='vmZone']").val();
	var vmname=$(which).parents(".vmRow").andSelf().first().find("input[zmlm\\\:item='vmName']").val();
	
	var panel=$("#vmDetailPanel");
	
	var data=$(which).parents(".vmRow").andSelf().data("data");
	if(null!=data) {
		// bind up data first
		$(panel).data("data", data);
		
		$(panel).find("[name='vmname']").html(data.vmname);
		$(panel).find("[name='zonedisplay']").html(data.zonedisplay);
		$(panel).find("[name='ostype']").html(data.ostype);
		$(panel).find("[name='cpus']").html(data.cpus);
		$(panel).find("[name='memory']").html(formatSize(data.memory*1024, 2));
		$(panel).find("[name='starttime']").html(new Date(data.starttime.time).toLocaleString());
		$(panel).find("[name='expiretime']").html(new Date(data.expiretime.time).toLocaleString());
		$(panel).find("[name='publicips']").html(formatPublicIps(data.publicips));
		$(panel).find("[name='privateip']").html(data.privateip);
		$(panel).find("[name='disksize']").html(formatSize(data.disksize));
		$(panel).find("[name='maxmemory']").html(formatSize(data.maxmemory*1024, 2));
		$(panel).find("[name='maxcpus']").html(data.maxcpus);
		if(data.status=="RUNNING") {
			$(panel).find("[name='statusicon']").attr("class", "ui-icon ui-icon-play");
		}else {
			$(panel).find("[name='statusicon']").attr("class", "ui-icon ui-icon-power");
		}
		$(panel).find("[name='statusdisplay']").html(data.statusdisplay);
		$(panel).find("[name='accesspoint']").children("a[name='javavnc']").attr("href", data.accesspoint);
		$(panel).find("[name='accesspoint']").children("a[name='webvnc']").attr("href", getWebVNCLink(data));
		$(panel).find("[name='vmpasswd']").children("a").data("privateip", data.privateip);
		$(panel).find("[name='vmpasswd']").children("a").data("ostype", data.ostype);
		
		// set up CPU slider
		$(panel).find("[name='slider_cpu']").slider("destroy").slider({
			value: data.cpus,
			min: 1,
			max: data.maxcpus,
			step: 1,
			animate: true,
			range: "min",
			slide: function(event, ui) {
				$(panel).find("[name='cpus']").html(ui.value);
			}
		});
		
		// set up memory slider
		$(panel).find("[name='slider_mem']").slider("destroy").slider({
			value: data.memory, // KB unit
			min: 1024*1024, // =1GB
			max: data.maxmemory,
			step: 1024*256, // 256 MB per step
			animate: true,
			range: "min",
			slide: function(event, ui) {
				$(panel).find("[name='memory']").html(formatSize(ui.value*1024, 2));
			}
		});
	
		$(panel).dialog("open");
	}
}

function applyCpu(which) {
	var data=$(which).parents(".vmDetail").first().data("data");
	var vmid=data.vmid;
	var vmname=data.vmname;
	var zone=data.zone;
	var cpus=$(which).parents(".vmDetail").first().find("[name='slider_cpu']").slider("value");
	
	if(!confirm(Locale["vm.confirm.change.cpu"].sprintf(vmname, cpus))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "changevmsetup",
			loginuser: getUsername(),
			password: getPassword(),
			resourcetype: "cpu",
			value: cpus,
			zone: zone,
			vmid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.done"];break;
					case "error": ;
					case "exception": msg=Locale["vm.message.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
				
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

/**
 * Illustrate chart data of VM.
 */
function showChart(ui, type) {
	var data=$(ui).parents(".vmDetail").first().data("data");
	var vmid=data.vmid;
	var vmname=data.vmname;
	
	// set up chart panel
	var chartPanel=$.tmpl("chartPanelTemplate", [{vmname: vmname, chartid: "chartView"}]).appendTo("#mainBody").dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		width: 800,
		height: 550,
		close: function(event, ui) {
			$(this).dialog("destroy");
			$(this).remove();
		},
		open: function(event, ui){
			$(chartPanel).find("select[name='duration']").bind("change", function(){
				if(parseInt($(this).val())>=2592000000) {
					var opt=$(chartPanel).find("select[name='interval']").empty();
					$(opt).append("<option value='86400000'>"+Locale["vm.chart.option.1day"]+"</option>");
				}else{
					var opt=$(chartPanel).find("select[name='interval']").empty();
					$(opt).append("<option value='300000'>"+Locale["vm.chart.option.5min"]+"</option>");
					$(opt).append("<option value='600000'>"+Locale["vm.chart.option.10min"]+"</option>");
					$(opt).append("<option value='900000'>"+Locale["vm.chart.option.15min"]+"</option>");
					$(opt).append("<option value='1800000'>"+Locale["vm.chart.option.30min"]+"</option>");
					$(opt).append("<option value='3600000'>"+Locale["vm.chart.option.60min"]+"</option>");
				}
			}).triggerHandler("change");
			
			$(this).find("button").button();
			$(this).find("button[name='reloadChart']").bind("click", function(){
				// disable the button to avoid duplicated calling
				$(this).button("disable");
	
				// set up parameters
				var startDate=new Date().getTime()-parseInt($(chartPanel).find("select[name='duration']").val());
				var interval=parseInt($(chartPanel).find("select[name='interval']").val());
				var endDate=new Date().getTime();
				var yAxisTitle="N/A";
				var chartTitle=Locale["vm.chart.chartTitle"];
				var yUnit="";
				switch(type) {
					case "cpu": yAxisTitle=Locale["vm.chart.yAxiesTitle.cpu"]; yUnit=Locale["vm.chart.yAxiesTitle.cpu.unit"]; chartTitle=chartTitle.sprintf(Locale["vm.chart.icon.cpu"], vmname); break;
					case "mem": yAxisTitle=Locale["vm.chart.yAxiesTitle.mem"]; yUnit=Locale["vm.chart.yAxiesTitle.mem.unit"]; chartTitle=chartTitle.sprintf(Locale["vm.chart.icon.mem"], vmname); break;
					case "vol": yAxisTitle=Locale["vm.chart.yAxiesTitle.vol"]; yUnit=Locale["vm.chart.yAxiesTitle.vol.unit"]; chartTitle=chartTitle.sprintf(Locale["vm.chart.icon.vol"], vmname); break;
					case "net": yAxisTitle=Locale["vm.chart.yAxiesTitle.net"]; yUnit=Locale["vm.chart.yAxiesTitle.net.unit"]; chartTitle=chartTitle.sprintf(Locale["vm.chart.icon.net"], vmname); break;
				}
				var chartSubtitle=Locale["vm.chart.chartSubtitle"].sprintf(new Date(startDate).toLocaleString(), new Date(endDate).toLocaleString());
				
				// draw it
				drawChart("chartView", type, vmid, vmname, startDate, endDate, interval, yAxisTitle, chartTitle, yUnit, chartSubtitle, 
					function(){$("button[name='reloadChart']").button("enable");});
				
			}).triggerHandler("click");
		},
		buttons: [
			{
				text: Locale["vm.dialog.close"],
				click: function() {
					$(this).dialog("close");
				}
			}
		]
	});
	
	$(chartPanel).dialog("open");

}

function drawChart(container, type, vmid, vmname, startDate, endDate, interval, yAxisTitle, chartTitle, yUnit, chartSubtitle, callback) {
	$("#"+container).empty().append("<img style='display:inline-block;position:absolute;top:50%;left:50%;' src='css/image/progress_large.gif'/>");
	
	$.ajax({
		url: Server+"/RedDragonEnterprise/InformationRetriverServlet",
		type: "POST",
		data: {
			methodtype: "getvmusages",
			vmid: vmid,
			startdate: startDate,
			enddate: endDate,
			timeinterval: parseInt(interval/1000)
		},
		success: function(data) {
			callback();
			
			if($("#"+container).length==0) return; // if the view not existed further more, do nothing
			
			try{
				data=$.parseJSON(data);
				
				if(data.status!="valid") printMessage(Locale["vm.chart.label.message.no_vm_data"]);
				
				// convert the raw data
				var cpupercentage=new Array();
				var datadownloaded=new Array();
				var datauploaded=new Array();
				var ioreadtimes=new Array();
				var iowritetimes=new Array();
				var memorypercentage=new Array();
				for(var i=0; "valid"==data.status && i<data.usages.length; i++) {
					datadownloaded.push(parseFloat(data.usages[i].datadownloaded) || 0);
					datauploaded.push(parseFloat(data.usages[i].datauploaded) || 0);
					ioreadtimes.push(parseFloat(data.usages[i].ioreadtimes) || 0);
					iowritetimes.push(parseFloat(data.usages[i].iowritetimes) || 0);
				}
				for(var i=0; "valid"==data.status && i<data.percentages.length; i++) {
					cpupercentage.push(parseFloat(data.percentages[i].cpudpercentage) || 0);
					memorypercentage.push(parseFloat(data.percentages[i].memorypercentage) || 0);
				}
				
				var chartUsage = new Highcharts.Chart({
					chart: {renderTo: container,type: "bar"},
					title: {text: chartTitle},
				    subtitle: {text: chartSubtitle},
					xAxis: {type: "datetime",maxZoom: 3600*1000,title: {text: null}},
					yAxis: {title:{text: yAxisTitle}, min:0, startOnTick:false, showFirstLabel:true, labels: {formatter: function(){return this.value+yUnit;}}},
					tooltip: {shared: true},
					legend: {enabled: true},
					credits: {enabled: false},
					showLastLabel: true,
					lang: {
						"downloadPNG": Locale["vm.chart.component.downloadPNG"],
						"downloadJPEG": Locale["vm.chart.component.downloadJPEG"],
						"downloadPDF": Locale["vm.chart.component.downloadPDF"],
						"downloadSVG": Locale["vm.chart.component.downloadSVG"],
						"printButtonTitle": Locale["vm.chart.component.printButtonTitle"],
						"exportButtonTitle": Locale["vm.chart.component.exportButtonTitle"],
						"months": [Locale["vm.chart.component.Jan"], Locale["vm.chart.component.Feb"], Locale["vm.chart.component.Mar"],
							Locale["vm.chart.component.Apr"], Locale["vm.chart.component.May"], Locale["vm.chart.component.Jun"],
							Locale["vm.chart.component.Jul"], Locale["vm.chart.component.Aug"], Locale["vm.chart.component.Sep"],
							Locale["vm.chart.component.Oct"], Locale["vm.chart.component.Nov"], Locale["vm.chart.component.Dec"]],
						"shortMonths": [Locale["vm.chart.component.Jan.short"], Locale["vm.chart.component.Feb.short"], Locale["vm.chart.component.Mar.short"],
							Locale["vm.chart.component.Apr.short"], Locale["vm.chart.component.May.short"], Locale["vm.chart.component.Jun.short"],
							Locale["vm.chart.component.Jul.short"], Locale["vm.chart.component.Aug.short"], Locale["vm.chart.component.Sep.short"],
							Locale["vm.chart.component.Oct.short"], Locale["vm.chart.component.Nov.short"], Locale["vm.chart.component.Dec.short"]],
						"weekdays": [Locale["vm.chart.component.Sunday"], Locale["vm.chart.component.Monday"], Locale["vm.chart.component.Tuesday"],
							Locale["vm.chart.component.Wednesday"], Locale["vm.chart.component.Thursday"], Locale["vm.chart.component.Friday"],
							Locale["vm.chart.component.Saturday"]]
					},
					plotOptions: {
						area: {
							fillColor: {
								linearGradient: [0, 0, 0, 300],
								stops: [
									[0, Highcharts.getOptions().colors[0]],
									[1, "rgba(2,0,0,0)"]
								]
							},
							lineWidth: 1,
							marker: {
								enabled: false,
								states: {
									hover: {
										enabled: true,
										radius: 5
									}
								}
							},
							shadow: false,
							states: {
								hover: {
									lineWidth: 1						
								}
							}
						}
					}
				});
				
				if(type=="cpu") {
					chartUsage.addSeries({
						type: 'area',
						name: Locale["vm.chart.series.cpu"],
						pointInterval: (endDate-startDate)/(cpupercentage.length-1 || 1),
						pointStart: startDate,
						data: cpupercentage
					});
				}else if(type=="mem") {
					chartUsage.addSeries({
						type: 'area',
						name: Locale["vm.chart.series.mem"],
						pointInterval: (endDate-startDate)/(memorypercentage.length-1 || 1),
						pointStart: startDate,
						data: memorypercentage
					});
				}else if(type=="vol") {
					chartUsage.addSeries({
						type: 'area',
						name: Locale["vm.chart.series.vol.in"],
						pointInterval: (endDate-startDate)/(ioreadtimes.length-1 || 1),
						pointStart: startDate,
						data: ioreadtimes
					});
					chartUsage.addSeries({
						type: 'area',
						name: Locale["vm.chart.series.vol.out"],
						pointInterval: (endDate-startDate)/(iowritetimes.length-1 || 1),
						pointStart: startDate,
						data: iowritetimes
					});
				}else if(type=="net") {
					chartUsage.addSeries({
						type: 'area',
						name: Locale["vm.chart.series.net.in"],
						pointInterval: (endDate-startDate)/(datadownloaded.length-1 || 1),
						pointStart: startDate,
						data: datadownloaded
					});
					chartUsage.addSeries({
						type: 'area',
						name: Locale["vm.chart.series.net.out"],
						pointInterval: (endDate-startDate)/(datauploaded.length-1 || 1),
						pointStart: startDate,
						data: datauploaded
					});
				}
				
			}catch(e){
				printMessage("Data Broken: ["+e+"]");
			};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			callback();
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function showVmPasswd(ui) {
	var privateip=$(ui).data("privateip");
	var ostype=$(ui).data("ostype");
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InformationRetriverServlet",
		cache: false,
		data: {
			methodtype: "getvmpasswd",
			loginuser: getUsername(),
			password: getPassword(),
			privateip: privateip
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg="<span class='ui-icon ui-icon-locked' style='float:left;'></span>"+Locale["vm.template.info.vmpasswd"].sprintf(data.vmpasswd);break;
					case "error": msg=Locale["vm.template.info.vmpasswd.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				var theSpan="";
				
				if(data.status=="done") {
					if(ostype.toLowerCase().match("win")) {
						msg="<span class='ui-icon ui-icon-person' style='float:left;'></span>"+Locale["vm.template.info.vmaccount"].sprintf("Administrator")+"<br/><br/>"+msg;
					}else{
						msg="<span class='ui-icon ui-icon-person' style='float:left;'></span>"+Locale["vm.template.info.vmaccount"].sprintf("root")+"<br/><br/>"+msg;
					}
					
					theSpan="<div class='ui-widget ui-state-highlight ui-corner-all' style='margin-top: 20px; padding: 1em .7em;'><table style='border:0;'><tr><td>"+msg+"</td></tr></table></div>";
				}else{
					theSpan="<div class='ui-widget ui-state-highlight ui-corner-all' style='margin-top: 20px; padding: 1em .7em;'><table style='border:0;'><tr><td><span class='ui-icon ui-icon-info' style='display:inline-block;margin-right:4px;float:left;'></span></td><td>"+msg+"</td></tr></table></div>";	
				}
				
				printMessage(theSpan);
				
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

function applyMem(which) {
	var data=$(which).parents(".vmDetail").first().data("data");
	var vmid=data.vmid;
	var vmname=data.vmname;
	var zone=data.zone;
	var mem=$(which).parents(".vmDetail").first().find("[name='slider_mem']").slider("value");
	
	if(!confirm(Locale["vm.confirm.change.mem"].sprintf(vmname, formatSize(mem*1024, 2)))) return;
	
	var pd=showProcessingDialog();
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InstanceCtrlServlet",
		cache: false,
		data: {
			methodtype: "changevmsetup",
			loginuser: getUsername(),
			password: getPassword(),
			resourcetype: "memory",
			value: mem,
			zone: zone,
			vmid: vmid
		},
		success: function(data) {
			pd.dialog("destroy");
			try{
				data=$.parseJSON(data);
				
				var msg="";
				switch(data.status) {
					case "done": msg=Locale["vm.message.done"];break;
					case "error": ;
					case "exception": msg=Locale["vm.message.error"];break;
					default: msg=Locale["vm.message.undefined"].sprintf(data.status);
				}
				
				printMessage(msg);
				
				loadVm();
				
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

function formatPublicIps(publicips) {
	if(typeof(publicips)=="string") {
		return publicips;
	}else {
		var pips=[];
		for(var i=0; i<publicips.length; i++) {
			pips.push(publicips[i].publicip);
		}
		return pips.join(", ");
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

function formatStatus(status, statusdisplay) {
	switch(status) {
		case "RUNNING": statusdisplay="<span class='ui-icon ui-icon-play' style='float:left; margin:0 0 0 0;'></span><span style='color:#25a300;font-weight:bold;'>"+statusdisplay+"</span>"; break;
		case "SHUTOFF": statusdisplay="<span class='ui-icon ui-icon-power' style='float:left; margin:0 0 0 0;'></span><span style='color:#777;'>"+statusdisplay+"</span>"; break;
		case "CREATING": statusdisplay="<span class='ui-icon ui-icon-refresh' style='float:left; margin:0 0 0 0;'></span><span style='color:red;'>"+statusdisplay+"</span>"; break;
		case "PAUSED": statusdisplay="<span class='ui-icon ui-icon-locked' style='float:left; margin:0 0 0 0;'></span><span style='color:orange;'>"+statusdisplay+"</span>"; break;
	}
	
	return statusdisplay;
}

function formatOstype(ostype) {
	if(ostype.toLowerCase().match("win")) {
		return "<img style='width:16px;margin-right:4px;vertical-align:middle;' src='css/image/windows.png'/>"+ostype;
	}else if(ostype.toLowerCase().match("linux|centos|ubunto|ubunto|debian")){
		return "<img style='width:16px;margin-right:4px;vertical-align:middle;' src='css/image/linux.png'/>"+ostype;
	}else {
		return "<span style='height:16px;display:inline-block;'><span class='ui-icon ui-icon-help' style='float:left; margin:0 4px 0 0;'></span></span>"+ostype;
	}
}


