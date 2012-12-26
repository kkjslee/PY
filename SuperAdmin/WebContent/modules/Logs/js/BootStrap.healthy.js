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
}

function setup() {
	//$("#mainBody").empty();
	var env=$("#env").html();
	$("#env").empty().show();
	
	var envs=env.split("\n");
	
	var varTable=$("<table class='vartable'></table>").appendTo($("#env"));
	for(var i=0; i<envs.length; i++) {
		var key=envs[i].substring(0, envs[i].indexOf("="));
		var val=envs[i].substring(envs[i].indexOf("=")+1);
		var status=true;
		
		if($.trim(key)!="") {
			if(key=="SERVER_SOFTWARE" && val.substring()) {
				var tomcat_version_major=val.match("Tomcat/[0-9]+\.").toString();
				tomcat_version_major=tomcat_version_major.substring("Tomcat/".length, tomcat_version_major.length-1);
				
				try{
					if(parseFloat(tomcat_version_major)<6){
						val="<em>"+val+"</em><notice>[Warning] Tomcat requires 6.x or higher!</notice>";
						status=false;
					}
				}catch(e){};
			}
			
			var img_ok="<img src='css/image/tick.png' />";
			var img_no="<font style='color:red;font-size:16px;padding:0;margin:0;font-family:Gill Sans MT;'>x</font>";
			
 			$(varTable).append("<tr><td>"+key+"</td><td>"+val+"</td><td>"+(status?img_ok:img_no)+"</td></tr>");
		}
	}
	
	var rows=$("#revision").find("tr");
	for(var i=0; i<rows.length; i++) {
		var all=$($(rows[i]).children("td")[1]).html();
		var elements=all.split(",");
		var total=revision(elements);
		$($(rows[i]).children("td")[1]).html(total);
	}
	
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
			try{
				data=$.parseJSON(data);
				
				var ips=[];
				
				for(var i=0; i<data.length; i++) {
					var zone=data[i].zone;
					var ip=data[i].ip;
					var status=data[i].status;
					
					ips.push(ip);
				}
				
				for(var i=0; i<ips.length; i++) {
					var id="track_"+i;
					$("<button id='btn_"+id+"'>Ping "+ips[i]+"<img src='css/image/progress_large.gif' style='width:12px;'></button>").appendTo("#track")
						.data("track_panel_id", id)
						.bind("click", function(e){
							
						$(this).find("img").show();
						document.getElementById($(this).data("track_panel_id")).contentDocument.location.reload(true);
					});
					
					var servSoft=$("#track").attr("serverSoftware");
					
					var track_page_win="tracker_win.shtml";
					var track_page_lin="tracker_lin.shtml";
					var tracker;
					if(servSoft.match(/Windows/i)) {
						tracker=track_page_win;
					}else{
						tracker=track_page_lin;
					}
					
					$('<iframe marginheight="0" marginwidth="0" frameborder="0" id="'+id+'" src="'+tracker+'?'+ips[i]+'" style="border-collapse:collaspe;overflow:hidden; border:0; min-width:200px;width:100%;height: 80px;display:block;position: relative;margin-bottom:10px;"></iframe>').appendTo("#track").bind("load", function(){
						$("#btn_"+$(this).attr("id")).find("img").hide();
					});
				}
				
				$("button").button();
				
			}catch(e){
				printMessage("Data Broken");
			};
		},
		failure: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
	
}

function initUi() {
	// jquery ui init
	$("button").button();
	
	$("#banner").html(Locale["logs.banner.healthy"]);
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




