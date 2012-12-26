// JavaScript Document
// Author: Bill, 2011~2012
$(function(){
	var scriptsToLoad=[
		"../../locale.jsp",
		"../../js/server.js",
		"../../js/account.js"
	];
	
	Booter.init().prepare(scriptsToLoad, init);
	
});

function init() {
	jQuery.fn.extend({
		classfier: classfier,
		itemize: itemize,
		filebox: filebox
	});
	
	$(".panel").bind("update", function(){
		$(this).find(".classfier").classfier();
		$(this).find(".listItem").itemize();
	});
	
	$(".panel").find(".anchor_sa").bind("click", function(){
		var view=$(this).parents(".panel").first().find(".panelList");
		
		$(view).find(".listItem").each(function(){
			$(this).addClass("selected");
		});
		
		return false; 
	});
	
	$(".panel").find(".anchor_da").bind("click", function(){
		var view=$(this).parents(".panel").first().find(".panelList");
		
		$(view).find(".listItem").each(function(){
			$(this).removeClass("selected");
		});
		
		return false; 
	});
	
	$("input[type='submit']").bind("click", function(){
		showAuthDialog($(this).parents(".panel").first().find(".panelList"));
		return false;
	});
	
	$("button[name='upgradedb']").bind("click", function() {
		upgradedb($(this).parents(".panel").first().find(".panelList input[name='dbname']").val());
	});
	
	$("input[type='file']").filebox();
	
	loadAppServers();
	loadZoneServers();
	loadDiskServers();
	loadPoolServers();
	
}

function upgradedb(dbname) {
	window.console && window.console.log("Upgrade DB: "+dbname);
	
	if(!$.trim(dbname)) {
		printMessage("Please specify a databse name to upgrade.");
	}else if(confirm("Do you wish to upgrade Database: "+dbname+"?")){
		var pd=showProcessingDialog();
		$.ajax({
			url: Server+"/LicenseManager/UpdateManager",
			type: "post",
			cache: false,
			data: {
				methodtype: "updatedb",
				dbname: dbname
			},
			success: function(data) {
				try{
					data=$.parseJSON(data);
					
					var msg="";
					switch(data.status) {
						case "done": msg="Upgrade done!"; break;
						default: msg="Upgrade failed with: "+data.status;
					}
					
					printMessage(msg);
					
				}catch(e){printMessage("Data broken: "+e);};
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR,textStatus, errorThrown);
			},
			complete: function(jqXHR, textStatus) {$(pd).dialog("destroy");}
		});
	}
	
}

function loadAppServers() {
	var container=$("[zmlm\\\:item='asPanel']");
	var view=$(container).find(".panelList").empty();
			
	$(view).append("<div class='classfier'>Web Service Server</div>");
	$(view).append("<div class='listItem' nodetype='appserver'><!--#echo var='SERVER_ADDR'--></div>");
	
	$(container).triggerHandler("update");
}

function loadZoneServers() {
	var container=$("[zmlm\\\:item='zonePanel']");
	var view=$(container).find(".panelList").empty().html("<em>Loading...</em>");
				
	$.ajax({
		url: Server+"/LicenseManager/UpdateManager",
		cache: false,
		type: "POST",
		data: {
			methodtype: "fetchserverinfor",
			servertype: "zoneservers"
		},
		success: function(data) {
			$(view).empty();
			
			try{
				data=$.parseJSON(data);
				
				var map=arrange(data, "zonedisplayname", "zoneentryip");
				
				for(var key in map) {
					var elements=map[key];
					
					$(view).append("<div class='classfier'>"+key+"</div>");
						
					for(var i=0; i<elements.length; i++) {
						var value=elements[i];
						$(view).append("<div class='listItem' nodetype='zonecontroller' zonedisplay='"+key+"'>"+value+"</div>");
					}
				}
				
				$(container).triggerHandler("update");
				
			}catch(e){alert("Data Broken: "+e);};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$(view).empty().html("error: "+textStatus);
		}
	});
}

function loadDiskServers() {
	var container=$("[zmlm\\\:item='diskPanel']");
	var view=$(container).find(".panelList").empty().html("<em>Loading...</em>");
	
	$.ajax({
		url: Server+"/LicenseManager/UpdateManager",
		cache: false,
		type: "POST",
		data: {
			methodtype: "fetchserverinfor",
			servertype: "diskzoneservers"
		},
		success: function(data) {
			$(view).empty();
			
			try{
				data=$.parseJSON(data);
				
				var map=arrange(data, "zonedisplayname", "zoneentryip");
				
				for(var key in map) {
					var elements=map[key];
					
					$(view).append("<div class='classfier'>"+key+"</div>");
						
					for(var i=0; i<elements.length; i++) {
						var value=elements[i];
						$(view).append("<div class='listItem' nodetype='vmnode' zonedisplay='"+key+"'>"+value+"</div>");
					}
				}
				
				$(container).triggerHandler("update");
				
			}catch(e){alert("Data Broken: "+e);};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$(view).empty().html("error: "+textStatus);
		}
	});
}

function loadPoolServers() {
	var container=$("[zmlm\\\:item='poolPanel']");
	var view=$(container).find(".panelList").empty().html("<em>Loading...</em>");
	
	$.ajax({
		url: Server+"/RedDragonEnterprise/VMPoolManagement",
		type: "POST",
		cache: false,
		data: {
			methodtype: "fetchvmpools",
			loginuser: getUsername(),
			zone: "all"
		},
		success: function(data) {
			$(view).empty();
			
			try{
				data=$.parseJSON(data);
				
				var map=arrange(data, "zonedisplay", "ip");
				
				for(var key in map) {
					var elements=map[key];
					
					$(view).append("<div class='classfier'>"+key+"</div>");
						
					for(var i=0; i<elements.length; i++) {
						var value=elements[i];
						$(view).append("<div class='listItem' nodetype='vmnode' zonedisplay='"+key+"'>"+value+"</div>");
					}
				}
				
				$(container).triggerHandler("update");
				
				
			}catch(e){alert("Data Broken: "+e);};
		},
		failure: function(jqXHR, textStatus, errorThrown) {
			$(view).empty().html("error: "+textStatus);
		}
	});
}

function arrange(jsonArray, keyField, valField) {
	var map={};
	
	for(var i=0; i<jsonArray.length; i++) {
		var elements=map[jsonArray[i][keyField]] || [];
		
		elements.push(jsonArray[i][valField]);
		
		map[jsonArray[i][keyField]]=elements;
	}
	
	return map;
}

function classfier() {
	$(this).each(function(index, element){
		var text=$(element).html();
		
		$(element).empty().append("<span class='banner'>"+text+"</span>");
		
		$(element).bind("click", function(){
			$(element).siblings("[zonedisplay='"+text+"']").addClass("selected");
		})
	});
}

function itemize() {
	$(this).each(function(index, element){
		var ip=$(this).html();
		$(this).attr("value", ip);
		var nodetype=$(this).attr("nodetype");
		
		/*
		var add_1=$("<span class='itemAddition'>[<a href='#'>Log</a>]</span>").appendTo(this);
		$(add_1).find("a").bind("click", function(e){
			e.preventDefault();		
			e.stopPropagation();
			window.open("viewlog.html?"+[ip, nodetype].join("&"));
			return false;
		});
		*/
		
		$(element).bind("click", function(){
			$(this).toggleClass("selected");
		});
	});
}

function filebox() {
	$(this).each(function(index, element){
		$(element).bind("change", function(){
			var filename=$(this).val().match("[^/^\\\\]+$").toString()
			$(this).parents(".filebox").first().siblings("[name='displayfilename']").html(filename).attr("title", filename);
		});
		
		$(element).wrap("<span></span>");
		var wrapper=$(element).parents("span").first();
		$(wrapper).addClass("filebox");
		
		$(wrapper).after("<span class='filename' name='displayfilename'></span>");
			
		$(wrapper).append("<span>"+"Choose File..."+"</span>");
		
	});
}

function showProcessingDialog() {
	var dialogTemplate=
		"<div class='processDialog' id='processDialog'><table><tr><td>"
			+"<div class='processBar'><span name='progressValue'></span></div>"
			+"<img src='css/img/progress.gif'/>"
			+"Processing..."
			+"<em></em>"
		+"</td></tr></table></div>";
	
	var view=$(dialogTemplate).dialog({
		autoOpen: true,
		width: 400,
		height: 100,
		resizable: false,
		modal: true,
		closeOnEscape: false,
		open: function(event, ui) { $(this).parents(".ui-dialog").first().find(".ui-dialog-titlebar").hide(); },
		buttons: {
		}
	});
	return view;
}


function printError(jqXHR, textStatus, errorThrown) {
	printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}

function printMessage(msg) {
	var view=$("<div title='Message'><p style='text-align:center;'>"+msg+"</p></div>").dialog({
		resizable: false,
		modal: true,
		buttons: [
			{
				text: "Close",
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
	
	return view;
}

function showAuthDialog(listPanel) {
	
	var username="";
	var password="";
	var remember=false;
	var type=$(listPanel).parents(".panel").first().attr("zmlm:item");
	
	if(window["localStorage"]) {
		var currUser=Base64.encode(getUsername());
		var upgradeAuth=JSON.parse(Base64.decode(localStorage[currUser] || "e30="));
		
		remember=JSON.parse(upgradeAuth["upgrade.save_info"] || "false");
		
		if(type=="zonePanel" && remember) {
			username=upgradeAuth["upgrade.zone_user"] || "";
			password=upgradeAuth["upgrade.zone_pass"] || "";
		}else if(type=="diskPanel") {
			username=upgradeAuth["upgrade.disk_user"] || "";
			password=upgradeAuth["upgrade.disk_pass"] || "";
		}else if(type=="poolPanel") {
			username=upgradeAuth["upgrade.pool_user"] || "";
			password=upgradeAuth["upgrade.pool_pass"] || "";
		}else if(type=="asPanel") {
			username=upgradeAuth["upgrade.as_user"] || "";
			password=upgradeAuth["upgrade.as_pass"] || "";
		}
	}
	
	var viewTemplate="<div title='Server Authentication'>"
						+"<table class='authPanel'>"
							+"<tr>"
								+"<td>Username:</td>"
								+"<td><input name='username' value='"+username+"'/></td>"
							+"</tr>"
							+"<tr>"
								+"<td>Password:</td>"
								+"<td><input name='password' type='password' value='"+password+"'/></td>"
							+"</tr>"
							+"<tr>"
								+"<td colspan='2' style='text-align:right;'>"
									+"<label for='remember'>Remember it?</label>"
									+"<input type='checkbox' id='remember' "+(remember?"checked":"")+" style='vertical-align:bottom;'/>"
								+"</td>"
							+"</tr>"
						+"</table>"
					+"</div>";
	
	// let's check it
	var filename=$(listPanel).parents(".panel").first().find("input[type='file']").val();
	if(!filename) {
		printMessage("Take a file to upload please.");
		return;
	}else if($(listPanel).find(".selected").length<=0) {
		printMessage("At least 1 node to upgrade.");
		return;
	}else if(!/.+\.war/i.test(filename)) {
		printMessage("Oops, I wanna a war file (*.war).");
		return;
	}
		
	var view=$(viewTemplate).dialog({
		resizable: false,
		modal: true,
		buttons: [
			{
				text: "Go",
				click: function() {
					var array=$(listPanel).find(".selected");
					
					username=$(this).find("[name='username']").val();
					password=$(this).find("[name='password']").val();
					remember=$(this).find("#remember").is(":checked");
					
					if(window["localStorage"]) { // store the last account information
						var currUser=Base64.encode(getUsername());
						var upgradeAuth=JSON.parse(Base64.decode(localStorage[currUser] || "e30="));
						
						upgradeAuth["upgrade.save_info"]=remember;

						if(remember){
							if(type=="zonePanel") {
								upgradeAuth["upgrade.zone_user"]=username;
								upgradeAuth["upgrade.zone_pass"]=password;
							}else if(type=="diskPanel") {
								upgradeAuth["upgrade.disk_user"]=username;
								upgradeAuth["upgrade.disk_pass"]=password;
							}else if(type=="poolPanel") {
								upgradeAuth["upgrade.pool_user"]=username;
								upgradeAuth["upgrade.pool_pass"]=password;
							}else if(type=="asPanel") {
								upgradeAuth["upgrade.as_user"]=username;
								upgradeAuth["upgrade.as_pass"]=password;
							}
						}else{
							// clear all
							upgradeAuth["upgrade.zone_user"]="";
							upgradeAuth["upgrade.zone_pass"]="";
							upgradeAuth["upgrade.disk_user"]="";
							upgradeAuth["upgrade.disk_pass"]="";
							upgradeAuth["upgrade.pool_user"]="";
							upgradeAuth["upgrade.pool_pass"]="";
							upgradeAuth["upgrade.as_user"]="";
							upgradeAuth["upgrade.as_pass"]="";
						}
						
						localStorage[currUser]=Base64.encode(JSON.stringify(upgradeAuth));
					}
					
					// call submit method...
					var ips=[];
					for(var i=0; i<array.length; i++) {
						ips.push($(array[i]).attr("value"));
					}
					var form=$(listPanel).parents(".panel").first().find("form");
					$(form).find("[name='username']").val(username);
					$(form).find("[name='passwd']").val(password);
					$(form).find("[name='ips']").val(ips.join(":"));
					var queryString=$(form).formSerialize();
					
					var pd=showProcessingDialog();
					$(form).attr("action", Server+"/LicenseManager/UpdateManager?"+queryString)
						.attr("method", "POST")
						.attr("enctype", "multipart/form-data")
						.ajaxSubmit({
							uploadProgress: function(event, position, total, percentComplete) {
								$(pd).find("em").html(percentComplete+"% of ("+formatSize(total, 2)+")");
								$(pd).find("[name='progressValue']").css("width",percentComplete+"%");
							},
							success: function(data) {
								pd.dialog("destroy");
								try{
									data=$.parseJSON(data);
									
									var isarr=$.isArray(data);
									
									if(isarr && data.length>0) {//fail
										printMessage("The node(s) ["+data.join(",")+"] upload failed!");
									}else if(data.status){
										switch(data.status) {
											case "filenotattached":
												printMessage("Upload file not found.");
												break;
											case "fileuploadexception":
												printMessage("Error interupts uploading, retry it plase.");
												break;
											case "exception":
												printMessage("Service exception, contact administrator.");
												break;
											default:
												printMessage("Undefined status: "+data.status);
										}
									}else {
										printMessage("Upload complete!");
									}
									
								}catch(e){printMessage("Data broken: "+e);};
							},
							error: function(jqXHR, textStatus, errorThrown) {
								pd.dialog("destroy");
								if(window["console"]) {
									window.console.log("error: "+textStatus);	
								}
								printError(jqXHR, textStatus, errorThrown);
							}
						});
					
					//$(form).submit();
					
					$(this).dialog("destroy");
				}
			},
			{
				text: "Close",
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
	
	return view;
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


