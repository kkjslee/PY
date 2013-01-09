// JavaScript Document
// Author: Bill, 2011

var _DEBUG_=false;

$(function(){
	registerTemplate();
	
	setup();
	
	initAjax();
	
});

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function registerTemplate() {
	$.template("VMPoolTabsTemplate", Template_VMPoolTabs);
	$.template("tabContentTemplate", Template_TabContent);
	$.template("hostRowTemplate", Template_HostRow);
	$.template("messageBoxTemplate", Template_MessageBox);
}

function setup() {
	$("#mainBody").empty();
				
	/*append tips box*/
	$(['<table zmlm:item="finder" class="finder">',
		'<thead class="finder-indicator"><th><span class="ui-icon ui-icon-search"></span></th><th>Tips</th></thead>',
		'<form>',
			'<tr>',
				'<td><span>'+Locale["vmpool.template.option.reconstruct"]+':</span></td>',
				'<td><span>'+Locale["vmpool.dialog.message.reconstruct"]+'</span></td>',
			'</tr>',
			'<tr>',
				'<td><span>'+Locale["vmpool.template.option.recoverstate"]+':</span></td>',
				'<td><span>'+Locale["vmpool.dialog.message.recoverstate"]+'</span></td>',
			'</tr>',
		'</form>',
	'</table>'].join("")).appendTo("#mainBody");
	
	$("#banner").html(Locale["vmpool.banner"]);
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/InformationRetriverServlet",
		cache: false,
		data: {
			methodtype: "getzonelist"
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				var tabs=$.tmpl("VMPoolTabsTemplate", [{id:"vmpoolTabs"}]).appendTo("#mainBody");
				tabs.tabs().css("border", "0").bind("tabsshow", function(event, ui){
					loadVMPool(ui.panel);
				});
				
				for(var i=0; i<data.length; i++) {
					addTab(tabs, data[i]);
				}
				
				initUi();
				
			}catch(e){printMessage("Data Broken ["+e+"]");};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function addTab(ui, item) {
	var zone=item.zone;
	var zonename=item.zonename;
	var zoneprovider=item.zoneprovider;
	
	var taburl="#"+zone;
	$(ui).tabs("add", taburl, zonename);
	
	var panel=$(ui).tabs("widget").children(taburl);
	
	$.tmpl("tabContentTemplate").appendTo(panel);
}

function hostStatus(ui) {
	var row=$(ui).parents(".hostRow").first();
	var hostZone=$(row).find("[name='hostZone']").val();
	var hostEntry=$(row).find("[name='hostEntry']").val();
	
	var oper=$(ui).val();
	BootLoader.log("Request to status "+oper);
	switch(oper) {
		case "online":
		case "offline": {
			if(confirm(Locale["vmpool.dialog.confirm."+oper].sprintf(hostEntry))){
				var pd=showProcessingDialog();
				$.ajax({
					url: Server+"/RedDragonEnterprise/VMPoolManagement",
					type: "post",
					cache: false,
					data: {
						methodtype: "changevmpoolstatus",
						zone: hostZone,
						serverip: hostEntry,
						status: oper
					},
					success: function(data) {
						try{
							data=$.parseJSON(data);
							
							switch(data.status) {
								case "done": {
									printMessage(Locale["vmpool.dialog.message.changestatus.done"]);
									$(ui).attr("defVal", $(ui).val());// trigger to set default value
									break;
								}
								case "failed":
								case "exception":{
									printMessage(Locale["vmpool.dialog.message.changestatus.error"]);
									$(ui).val($(ui).attr("defVal")); // failed, restore the value
									break;
								}
							}
							
						}catch(e){printMessage("Data broken: "+e);}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$(ui).val($(ui).attr("defVal")); // failed, restore the value
						printError(jqXHR, textStatus, errorThrown);
					},
					complete: function(jqXHR, textStatus) {
						$(pd).dialog("destroy");
					}
				});
			}else{$(ui).val($(ui).attr("defVal")); /*cancel, restore the value*/}
			break;
		}
		default: /*do nothing*/;
	}
	
}

function hostOper(ui) {
	var row=$(ui).parents(".hostRow").first();
	var hostZone=$(row).find("[name='hostZone']").val();
	var hostEntry=$(row).find("[name='hostEntry']").val();
	
	var oper=$(ui).val();
	BootLoader.log("Request to "+oper);
	$(ui).val("");
	switch(oper) {
		case "recoverstate":
		case "reconstruct": {
			if(confirm([Locale["vmpool.dialog.confirm."+oper].sprintf(hostEntry), Locale["vmpool.dialog.message."+oper]].join("\n"))){
				var pd=showProcessingDialog();
				$.ajax({
					url: Server+"/RedDragonEnterprise/VMPoolManagement",
					type: "post",
					cache: false,
					data: {
						methodtype: "serverrecovery",
						zone: hostZone,
						serverip: hostEntry,
						operation: oper
					},
					success: function(data) {
						try{
							data=$.parseJSON(data);
							
							switch(data.status) {
								case "done": {
									printMessage(Locale["vmpool.dialog.message.recovery.done"]);
						
									// it's done, thus reload data
									loadVMPool($("#vmpoolTabs").find(".ui-tabs-panel:not('.ui-tabs-hide')")[0]);
									break;
								}
								case "failed":
								case "exception":{
									printMessage(Locale["vmpool.dialog.message.recovery.error"]);
									break;
								}
							}
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
			break;
		}
		default: /*do nothing*/;
	}
	
}

function loadVMPool(panel) {
	var panelid=panel.id; // it's the zone actually
	
	var pd=showProcessingDialog();
	$.ajax({
		url: Server+"/RedDragonEnterprise/VMPoolManagement",
		type: "POST",
		data: {
			methodtype: "fetchvmpools",
			loginuser: getUsername(),
			zone: panelid
		},
		cache: false,
		success: function(data) {
			var container=$(panel).find("[zmlm\\\:item='hostList']").accordion("destroy").empty();
			try{
				data=$.parseJSON(data);
				
				var hostList=$.tmpl("hostRowTemplate", data);
				for(var i=0; i<hostList.length; i++) {
					var row=$(hostList[i]).appendTo(container);
					
					if(i%2==0) {
						var cpurate=100*parseFloat(data[i/2].usedcpu)/parseFloat(data[i/2].maxcpu);
						$(row).find("[name='pbcpu']").progressbar({value:cpurate});
						
						var memrate=100*parseFloat(data[i/2].usedmem)/parseFloat(data[i/2].maxmem);
						$(row).find("[name='pbmem']").progressbar({value:memrate});
						
						var volrate=100*parseFloat(data[i/2].usedspace)/parseFloat(data[i/2].totalspace);
						$(row).find("[name='pbspace']").progressbar({value:volrate});
						
					}else{
						var chartid;
						
						/* We illustrate CPU chart here */
						chartid="_"+panelid+"_"+SeqId.getNext()+"_cpuchart";
						$(row).find("[name='cpuchart']").attr("id", chartid);
						try{
							var chartcpu = new Highcharts.Chart({
								chart: {
									renderTo: chartid,
									type: "column",
									width: 260,
									height: 260
								},
								title: {text: Locale["vmpool.chart.cpu.title"]},
								legend: {enabled: false},
								tooltip: {
									shared: false,
									formatter: function() {
										return Locale["vmpool.chart.cpu.tooltip"].sprintf(this.point.name, this.y);
									}
								},
								credits: {enabled: false},
								xAxis: {categories: [
										Locale["vmpool.chart.cpu.categories.allocated"], 
										Locale["vmpool.chart.cpu.categories.total"]
									]
								},
								yAxis: {min: 0, title: {text: Locale["vmpool.chart.cpu.yAxis.text"]}},
								series: [{
									data: [
										{
											name: Locale["vmpool.chart.cpu.series.allocated"],
											color: "#22A",
											y:data[parseInt(i/2)].usedcpu
										}, 
										{
											name: Locale["vmpool.chart.cpu.series.total"],
											color: "#2A2",
											y:data[parseInt(i/2)].maxcpu
										}
									]
								}]
							});
						}catch(e){};
						
						/* We illustrate memory chart here */
						chartid="_"+panelid+"_"+SeqId.getNext()+"_memchart";
						$(row).find("[name='memchart']").attr("id", chartid);
						try{
							var chartmem = new Highcharts.Chart({
								chart: {
									renderTo: chartid,
									width: 260,
									height: 260
								},
								title: {text: Locale["vmpool.chart.mem.title"].sprintf(data[parseInt(i/2)].maxmem)},
								tooltip: {
									shared: false,
									formatter: function() {
										return Locale["vmpool.chart.mem.tooltip"].sprintf(this.point.name, this.y, parseFloat(this.percentage).toFixed(2));
									}
								},
								credits: {enabled: false},
								plotOptions: {
									pie: {
										allowPointSelect: true,
										cursor: "pointer",
										dataLabels: {
											enabled: true,
											distance: 15,
											formatter: function() {
												return Locale["vmpool.chart.mem.dataLabels"].sprintf(this.y);
											}
										},
										showInLegend:true
									}
								},
								series: [{
									type: "pie",
									data: [
										[Locale["vmpool.chart.mem.series.available"], parseFloat(data[parseInt(i/2)].maxmem-data[parseInt(i/2)].usedmem)],
										[Locale["vmpool.chart.mem.series.used"], parseFloat(data[parseInt(i/2)].usedmem)]
									]
								}]
							});
						}catch(e){};
						
						/* We illustrate volume chart here */
						chartid="_"+panelid+"_"+SeqId.getNext()+"_volchart";
						$(row).find("[name='volchart']").attr("id", chartid);
						try{
							var chartvol = new Highcharts.Chart({
								chart: {
									renderTo: chartid,
									width: 260,
									height: 260
								},
								title: {text: Locale["vmpool.chart.vol.title"].sprintf(data[parseInt(i/2)].totalspace)},
								tooltip: {
									shared: false,
									formatter: function() {
										return Locale["vmpool.chart.vol.tooltip"].sprintf(this.point.name, this.y, parseFloat(this.percentage).toFixed(2));
									}
								},
								credits: {enabled: false},
								plotOptions: {
									pie: {
										allowPointSelect: true,
										cursor: "pointer",
										dataLabels: {
											enabled: true,
											distance: 15,
											formatter: function() {
												return Locale["vmpool.chart.vol.dataLabels"].sprintf(this.y);
											}
										},
										showInLegend:true
									}
								},
								series: [{
									type: "pie",
									data: [
										[Locale["vmpool.chart.vol.series.available"], parseFloat(data[parseInt(i/2)].totalspace-data[parseInt(i/2)].usedspace)],
										[Locale["vmpool.chart.vol.series.used"], parseFloat(data[parseInt(i/2)].usedspace)]
									]
								}]
							});
						}catch(e){};
						
					}
				}
				
				$(container).accordion({
					collapsible:true,
					clearStyle: true
				});
				
				$(container).find(".vmRow").hover(
					function(){
						$(this).addClass("hover");
					},
					function(){
						$(this).removeClass("hover");
					}
				);
				
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
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

var SeqId={
	_id: 0,
	getNext: function() {
		return this._id++;
	}
}

function initUi() {
	// jquery ui init
	$("button").button();
}

function initAjax() {
	jQuery.support.cors = true;
	
	$(document).ajaxStart(function(){
		$("#loadingIcon").show();
	}).ajaxStop(function(){
		$("#loadingIcon").hide();
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

function formatStatus(status) {
	if(/running/i.test(status)) {
		return ["<span style='color:green;'>",status,"</span>"].join("");
	}else if(/shutoff/i.test(status)) {
		return ["<span style='color:red;'>",status,"</span>"].join("");
	}else{
		return status;
	}
}

function formatDate(longtime) {
	return new Date(longtime).toUTCString();
}

function printError(jqXHR, textStatus, errorThrown) {
	printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}

function printMessage(msg) {
	return $.tmpl("messageBoxTemplate", [{message: msg}]).appendTo("#mainBody").dialog({
		resizable: false,
		modal: true,
		buttons: [{
			text: Locale["vmpool.dialog.close"],
			click: function() {
				$(this).dialog("destroy");
			}
		}]
	});
}

function showProcessingDialog() {
	var view=$("<div style='text-align:center;'><img src='css/image/progress.gif'/>"+Locale["vmpool.dialog.processing"]+"</div>").dialog({
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








