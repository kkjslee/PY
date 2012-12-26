// JavaScript Document
// Author: Bill, 2011

jQuery(window).bind("load", function(){
	jQuery(window).setup();
});

(function($){
	$.fn.setup=setup;
})(jQuery);

function setup() {
	var requestContent=getURLParameter("content");
	
	$("#content").empty();
	
	if(requestContent=="vmman") {
		getVMZoneList();	
		
	}else if(requestContent=="diskman") {
		getDiskZoneList();
		
	}else {
		$("#content").append("bad request :-(");
		
	}
	
	$(".refresh").html(Locale["stock.button.refresh"]);
	
	$("button").button();
}

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function printError(jqXHR, textStatus, errorThrown) {
	printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}

function printMessage(msg) {
	return $(['<div>','<p style="display:block;text-align:center;font-size:12px;">',msg,'</p>','</div>'].join(""))
		.appendTo(document.body).dialog({
		resizable: false,
		modal: true,
		buttons: [
			{
				text: Locale["stock.button.close"],
				click: function() {
					$(this).dialog("destroy");
				}
			}
		]
	});
}

function refreshDisk() {
	loadDiskData($(".ui-tabs-panel").not(".ui-tabs-hide"), $(".ui-tabs-panel").not(".ui-tabs-hide").attr("id"));
}

function refreshVM() {
	loadVMData($(".ui-tabs-panel").not(".ui-tabs-hide"), $(".ui-tabs-panel").not(".ui-tabs-hide").attr("id"));
}

function getVMZoneList() {
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getdiskzones",
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				$("<div id='tabs'><ul></ul></div>").appendTo("#content");
				$("#tabs").tabs({
					tabTemplate: "<li><a href='#{href}'>#{label}</a></li>",
					select: function(event, ui) {
						loadVMData(ui.panel, ui.panel.id);
					}
				}).css("border", "none");
				
				$(data).each(function(index, element){
					var name="#"+this.name;
					var label=this.displayName;
					$("#tabs").tabs("add", name, label);
				});
				
				refreshVM();
				
			}catch(e) {
				printError("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function getDiskZoneList() {
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getdiskzones",
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);
				
				$("<div id='tabs'><ul></ul></div>").appendTo("#content");
				$("#tabs").tabs({
					tabTemplate: "<li><a href='#{href}'>#{label}</a></li>",
					select: function(event, ui) {
						loadDiskData(ui.panel, ui.panel.id);
					}
				}).css("border", "none");
				
				$(data).each(function(index, element){
					var name="#"+this.name;
					var label=this.displayName;
					$("#tabs").tabs("add", name, label);
				});
				
				refreshDisk();
				
			}catch(e) {
				printError("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function loadVMData(container, zone) {
	$(container).empty();
	
	var tab_content="<table class='tableList' name='table'></table>";
	var header=(
		["<tr>",
			"<th>@imagename/@imageid</th>",
			"<th>@imageamount</th>",
			"<th></th>",
			"<th></th>",
			"<th>@imagezone</th>",
			"<th>@imageoperation</th>",
			"<th colspan='3'>@imageauto</th>",
		"</tr>"].join(""))
		.replace(/@imageid/g, Locale["stock.table.column.image.id"])
		.replace(/@imagename/g, Locale["stock.table.column.image.name"])
		.replace(/@imageamount/g, Locale["stock.table.column.image.amount"])
		.replace(/@imagezone/g, Locale["stock.table.column.image.zone"])
		.replace(/@imageauto/g, Locale["stock.table.column.image.autostock"])
		.replace(/@imageoperation/g, Locale["stock.table.column.image.operation"]);
	
	var view=$(tab_content).appendTo(container);
	$(header).appendTo(view);
	
	var row=(
		["<tr>",
			"<td>",
				"<span class='imageName'>${imageName}</span>",
				"<span name='imageId' class='imageId'>${imageId}</span>",
			"</td>",
			"<td style='color:green;'>${number}</td>",
			"<td>{{if number==0}}◆{{/if}}</td>"
			,"<td style='width:100px;'>",
				"<span class='progress' value='${number}' max='20'></span>",
			"</td>",
			"<td name='zone'>${zone}</td>",
			"<td>",
				"<button onclick='increaseVM(\"${imageId}\", \"${zone}\")'>@increase</button>",
				"<button onclick='decreaseVM(\"${imageId}\", \"${zone}\")'>@decrease</button>",
			"</td>",
			"<td class='imageAuto'>",
				"<input name='auto' type='checkbox' {{if (auto+'')=='true'}}checked{{/if}} title='Enable/Disable' />",
			"</td>",
			"<td class='imageQuota'>",
				"<input name='quota' {{if (auto+'')!='true'}}disabled{{/if}} value='${quota}'/>",
				"@labelAmount",
			"</td>",
			"<td class='apply'>",
				"<button onclick='setVMAuto(this)'>@apply</button>",
			"</td>",
		"</tr>"].join(""))
		.replace(/@increase/g, Locale["stock.table.cell.image.operation.increase"])
		.replace(/@decrease/g, Locale["stock.table.cell.image.operation.decrease"])
		.replace(/@apply/g, Locale["stock.table.cell.image.operation.apply"])
		.replace(/@labelAmount/g, Locale["stock.table.cell.image.label.amount"]);
		
	$.template("imageRow", row);
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getvmquota",
			zone: zone
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);

				var list=$.tmpl("imageRow", data).appendTo($(container).find("[name='table']"));
				
				$(list).find(".progress").each(function(index, element){
					var v=$(this).attr("value");
					var m=$(this).attr("max");
					
					$(this).progressbar({value: v*100/m});
				});
				
				$(list).find("[name='auto']").bind("change", function(e){
					if($(this).is(":checked")) {
						$(this).parents("tr").first().find("[name='quota']").removeAttr("disabled");
					}else{
						$(this).parents("tr").first().find("[name='quota']").attr("disabled","disabled");
					}
				});
				
				$(list).find("button").button();
				
			}catch(e) {
				printError("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
}

function setVMAuto(which) {
	var params={
		operation: "setvmquota",
		imageid: $(which).parents("tr").first().find("[name='imageId']").html(),
		zone: $(which).parents("tr").first().find("[name='zone']").html(),
		auto: $(which).parents("tr").first().find("[name='auto']").is(":checked").toString(),
		quota: $(which).parents("tr").first().find("[name='quota']").val(),
		type: "vm"
	};
	
	if(params.auto==='true' && (isNaN(parseInt(params.quota)) || parseInt(params.quota)<=0)) {
		printMessage(Locale["stock.message.illegal.number"]);
		return;
	}else if(params.auto==='true') {
		$(which).parents("tr").first().find("[name='quota']").val(parseInt(params.quota));
	}
	
	if(confirm(Locale["stock.confirm.volume.auto"])) {
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: params,
			success: function(data) {
				try{
					data=$.parseJSON(data);
					
					switch(data.status) {
						case "done": printMessage(Locale["stock.message.done"]);break;
						case "failed": refreshVM(); printMessage(Locale["stock.message.failed"]); break;
						default: refreshVM(); printMessage("Undefined Return: "+data.status);
					}
					
				}catch(e) {
					refreshVM();
					printMessage("Data Broken: ["+e+"]");
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			}
		});
	}
}

function loadDiskData(container, zone) {
	$(container).empty();
	
	var tab_content=[
	"<div class='subtitle'>@basicInfo</div>",
	"<table class='tableInfo'>",
		"<tr>",
			"<td class='labelLeft'>@totalVolumeSize</td>",
			"<td class='labelRight unitGB' name='totalVolumeSize'></td>",
		"</tr>",
		"<tr>",
			"<td class='labelLeft'>@VMSize</td>",
			"<td class='labelRight unitGB' name='VMSize'></td>",
		"</tr>",
		"<tr>",
			"<td class='labelLeft'>@usedDiskSize</td>",
			"<td class='labelRight unitGB' name='usedDiskSize'></td>",
		"</tr>",
		"<tr>",
			"<td class='labelLeft'>@totalFreeDiskSize</td>",
			"<td class='labelRight unitGB' name='totalFreeDiskSize'></td>",
		"</tr>",
	"</table>",
	"<hr/>",
	"<div class='subtitle'>@stockInfo</div>",
	"<table class='tableList' name='table'></table>",
	"<div class='diskCreate'>",
		"<span>@volumeSize</span>",
		"<input maxlength='15' name='size' />",
		"<span style='color:#777;'>(Bytes)</span>",
		"<input name='zone' type='hidden' value='@zone' />",
		"<button onclick='createDisk(this)'>@createVolumeType</button>",
		"<span name='convertor' style='color:#777;'></span>",
	"</div>",
	"<hr/>"
	].join("");
	tab_content=tab_content.replace(/@totalVolumeSize/g, Locale["stock.label.volume.totalVolumeSize"])
		.replace(/@zone/g, zone)
		.replace(/@createVolumeType/g, Locale["stock.button.createVolumeType"])
		.replace(/@volumeSize/g, Locale["stock.label.volume.size"])
		.replace(/@basicInfo/g, Locale["stock.label.volume.basicInfo"])
		.replace(/@stockInfo/g, Locale["stock.label.volume.stockInfo"])
		.replace(/@VMSize/g, Locale["stock.label.volume.VMSize"])
		.replace(/@usedDiskSize/g, Locale["stock.label.volume.usedDiskSize"])
		.replace(/@totalFreeDiskSize/g, Locale["stock.label.volume.totalFreeDiskSize"]);
	
	var header=("<tr><th>@volumesize</th><th>&nbsp;</th><th>@volumeamount</th><th>@volumeoperation</th></tr>")
		.replace(/@volumesize/g, Locale["stock.table.column.volume.size"])
		.replace(/@volumeamount/g, Locale["stock.table.column.volume.amount"])
		.replace(/@volumeoperation/g, Locale["stock.table.column.volume.operation"]);
	
	var view=$(tab_content).appendTo(container).find("[name='table']");
	$(header).appendTo(view);
	
	$(container).find(".diskCreate").find("input[name='size']").bind("keyup", function(event){
		var nice=formatSize(parseInt($(this).val()));
		$(this).siblings("[name='convertor']").html(nice);
	});
	
	var date=([
		"<table class='tableSell'>",
			"<tr>",
				"<td>",
					"<div class='subtitle'>@sellstatus</div>",
					"<span>@startdate</span>",
					"<input type='text' class='labelRight' name='dateStart'></input>",
					"<span>@stopdate</span>",
					"<input type='text' class='labelRight' name='dateEnd'></input>",
					"<button onclick='getAlloc(this, \"@zone\")'>@check</button>",
					"<div name='graph'></div>",
				"</td>",
				"<td>",
					"<div id='graphArea_@zone' class='graphArea'></div>",
				"</td>",
			"</tr>",
		"</table>"
		].join(""))
		.replace(/@zone/g, zone)
		.replace(/@sellstatus/g, Locale["stock.label.volume.sellstatus"])
		.replace(/@startdate/g, Locale["stock.label.volume.startdate"])
		.replace(/@stopdate/g, Locale["stock.label.volume.stopdate"])
		.replace(/@check/g, Locale["stock.button.volume.check"]);
	
	$(date).appendTo(container).find("[name='dateStart'],[name='dateEnd']").datepicker().datepicker("option", "dateFormat", "yy-mm-dd");
				
	var row=([
		"<tr>",
			"<td>",
				"<input type='hidden' name='zone' value='@zone' />",
				"<input type='hidden' name='size' value='${size}' />",
				"<input type='hidden' name='poolName' value='${poolName}' />",
			"</td>",
			"<td>${name}</td>",
			"<td style='width:100px;'>",
				"<span class='progress' value='${number}' max='20'></span>",
			"</td>",
			"<td style='color:green;'>${number}</td>",
			"<td>",
				"<button onclick='increaseDisk(\"${size}\", \"${zone}\", \"${poolName}\")'>@increase</button>",
				"<button onclick='decreaseDisk(\"${size}\", \"${zone}\")'>@decrease</button>",
			"</td>",
			"<td class='diskRemove'>",
				"<a onclick='removeDisk(this);return false;' href='javascript:void' title='Remove'></a>",
			"</td>",
			"<td class='diskAuto'>",
				"<span>@labelAuto</span>",
				"<input name='auto' type='checkbox' {{if (auto+'')=='true'}}checked{{/if}} title='Enable/Disable' /></td>",
			"<td class='diskQuota'><input name='quota' {{if (auto+'')!='true'}}disabled{{/if}} value='${quota}'/>@labelAmount</td>",
			"<td class='apply'>",
				"<button onclick='setDiskAuto(this)'>@apply</button>",
			"</td>",
		"</tr>"
		].join(""))
		.replace(/@increase/g, Locale["stock.button.volume.increase"])
		.replace(/@decrease/g, Locale["stock.button.volume.decrease"])
		.replace(/@labelAuto/g, Locale["stock.label.volume.autoStock"])
		.replace(/@zone/g, zone)
		.replace(/@apply/g, Locale["stock.table.cell.volume.operation.apply"])
		.replace(/@labelAmount/g, Locale["stock.table.cell.volume.label.amount"]);
	$.template("diskRow", row);
	
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getdiskquota",
			zone: zone
		},
		success: function(data) {
			try{
				data=$.parseJSON(data);

				var tableContainer=$(container).find("[name='table']");
				var aRow=$.tmpl("diskRow", data).appendTo(tableContainer);
				
				$(aRow).find("[name='auto']").bind("change", function(e){
					if($(this).is(":checked")) {
						$(this).parents("tr").first().find("[name='quota']").removeAttr("disabled");
					}else{
						$(this).parents("tr").first().find("[name='quota']").attr("disabled","disabled");
					}
				});

				$(aRow).find(".progress").each(function(index, element){
					var v=$(this).attr("value");
					var m=$(this).attr("max");
					
					$(this).progressbar({value: v*100/m});
				});
				
				$(data).each(function(index, element){
					$("<div style='border-bottom:1px dashed silver;padding:5px;'>"+this.name+":<span class='area' name='area'></span></div>")
						.appendTo($(container).find("div[name='graph']"))
						.data("size", this.size);
				});
				
				$("button").button();
				
			}catch(e) {
				printError("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});
	
	
	/* load disk digest info */
	getUsedDiskSize($(container).find("[name='usedDiskSize']"), zone);
	getVMSize($(container).find("[name='VMSize']"), zone);
	getTotalFreeDiskSize($(container).find("[name='totalFreeDiskSize']"), zone);
	getVolumeSize($(container).find("[name='totalVolumeSize']"), zone);
}


function setDiskAuto(which) {
	var params={
		operation: "setdiskquota",
		size: $(which).parents("tr").first().find("[name='size']").val(),
		zone: $(which).parents("tr").first().find("[name='zone']").val(),
		auto: $(which).parents("tr").first().find("[name='auto']").is(":checked").toString(),
		quota: $(which).parents("tr").first().find("[name='quota']").val(),
		type: "volume"
	};
	
	if(params.auto==='true' && (isNaN(parseInt(params.quota)) || parseInt(params.quota)<=0)) {
		printMessage(Locale["stock.message.illegal.number"]);
		return;
	}else if(params.auto==='true') {
		$(which).parents("tr").first().find("[name='quota']").val(parseInt(params.quota));
	}
	
	if(confirm(Locale["stock.confirm.volume.auto"])) {
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: params,
			success: function(data) {
				try{
					data=$.parseJSON(data);
					
					switch(data.status) {
						case "done": printMessage(Locale["stock.message.done"]);break;
						case "failed": refreshDisk(); printMessage(Locale["stock.message.failed"]);break;
						default: refreshDisk(); printMessage("Undefined Return: "+data.status);
					}
					
				}catch(e) {
					refreshDisk();
					printMessage("Data Broken: ["+e+"]");
				}
				
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			}
		});
	}
}



function getAlloc(view, zone) {
	var dateStart=$(view).parent().find("[name='dateStart']").val();
	var dateEnd=$(view).parent().find("[name='dateEnd']").val();

	var container=$(view).parent().find("[name='graph']");
	$(container).children("div").each(function(index, element){
		var size=$(this).data("size");
		var graphArea=$(this).find("[name='area']").empty();
		
		getAllocationLog(graphArea, zone, size, dateStart, dateEnd);
	});
}

function increaseDisk(size, zone, poolName) {
	var q=prompt(Locale["stock.confirm.volume.increase"], "1");
	
	if(!q) return;
	
	try{
		q=parseInt(q);
		if(!q) throw "Illegal Number";
		
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: {
				operation: "addDisk",
				zone: zone,
				disksize: size,
				poolname: poolName,
				allocated: "notyet",
				quantity: q
			},
			success: function(data) {
				data=$.parseXML(data);
				printMessage($(data).find("result").text());
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			}
		});
		
	}catch(e){
		printMessage(Locale["stock.message.illegal.number"]);
	}
}

function decreaseDisk(size, zone) {
	if(confirm(Locale["stock.confirm.volume.decrease"])) {
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: {
				operation: "deleteDisk",
				zone: zone,
				size: size
			},
			success: function(data) {
				data=$.parseXML(data);
				printMessage($(data).find("result").text());
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			}
		});
	}
}

function removeDisk(which) {
	var size=$(which).parents("tr").first().find("[name='size']").val();
	var zone=$(which).parents("tr").first().find("[name='zone']").val();
		
	if(confirm(Locale["stock.confirm.volume.remove"].sprintf(formatSize(size)))) {
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: {
				operation: "deletediskquota",
				zone: zone,
				size: size,
				type: "volume"
			},
			success: function(data) {
				try{
					data=$.parseJSON(data);
					
					switch(data.status) {
						case "inventorynotempty": printMessage(Locale["stock.message.inventorynotempty"]);break;
						case "done": refreshDisk(); printMessage(Locale["stock.message.done"]);break;
						case "failed": printMessage(Locale["stock.message.failed"]);break;
						default: printMessage("Undefined Return: "+data.status);
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
}

function createDisk(which) {
	var size=$(which).parents("div").first().find("[name='size']").val();
	var zone=$(which).parents("div").first().find("[name='zone']").val()
	
	if(isNaN(parseInt(size)) || parseInt(size)<=0) {
		printMessage(Locale["stock.message.illegal.number"]);
		return;
	}else {
		$(which).parents("div").first().find("[name='size']").val(parseInt(size));
	}
	
	var params={
		operation: "setdiskquota",
		size: size,
		zone: zone,
		auto: "false",
		quota: 0,
		type: "volume"
	};
	
	if(confirm(Locale["stock.confirm.volume.create"])) {
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: params,
			success: function(data) {
				try{
					data=$.parseJSON(data);
					
					switch(data.status) {
						case "done": refreshDisk(); printMessage(Locale["stock.message.done"]);break;
						case "failed": printMessage(Locale["stock.message.failed"]);break;
						default: printMessage("Undefined Return: "+data.status);
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
}

function increaseVM(imageId, zone) {
	var q=prompt(Locale["stock.message.input.stock"], "1");
	
	if(!q) return;
	
	try{
		q=parseInt(q);
		if(!q) throw "Illegal Number";
		
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: {
				operation: "addvmbase",
				zone: zone,
				imageId: imageId,
				allocated: "not yet",
				quantity: q
			},
			success: function(data) {
				data=$.parseXML(data);
				printMessage($(data).find("result").text());
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			}
		});
		
	}catch(e){
		printMessage(Locale["stock.message.illegal.number"]);
	}
}

function decreaseVM(imageId, zone) {
	if(confirm(Locale["stock.confirm.image.decrease"])) {
		$.ajax({
			type: "POST",
			url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
			cache: false,
			dataType: "text",
			data: {
				operation: "deletevmbase",
				zone: zone,
				imageId: imageId
			},
			success: function(data) {
				data=$.parseXML(data);
				printMessage($(data).find("result").text());
			},
			error: function(jqXHR, textStatus, errorThrown) {
				printError(jqXHR, textStatus, errorThrown);
			}
		});
	}
}

function getTotalFreeDiskSize(container, zone) {
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getTotalFreeDiskSize",
			zone: zone
		},
		success: function(data) {
			try{
				data=$.parseXML(data);
				$(container).append($(data).find("result").text());
			}catch(e){};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if(window.console){console.error("Get total free disk size failed!");}
		}
	});
}

function getUsedDiskSize(container, zone) {
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getTotalDiskSize",
			zone: zone
		},
		success: function(data) {
			try{
				data=$.parseXML(data);
				$(container).append($(data).find("result").text());
			}catch(e){};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if(window.console){console.error("Get used disk size failed!");}
		}
	});
}

function getVMSize(container, zone) {
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getTotalVMSize",
			zone: zone
		},
		success: function(data) {
			try{
				data=$.parseXML(data);
				$(container).append($(data).find("result").text());
			}catch(e){};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if(window.console){console.error("Get total VM size failed!");}
		}
	});
}

function getVolumeSize(container, zone) {
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getTotalVolumeSize",
			zone: zone
		},
		success: function(data) {
			try{
				data=$.parseXML(data);
				$(container).append($(data).find("result").text());
			}catch(e){};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if(window.console){console.error("Get total volume size failed!");}
		}
	});
}

function getAllocationLog(graphArea, zone, size, start, end) {
	$.ajax({
		type: "POST",
		url: Server+"/RedDragonEnterprise/ConventoryManagerServlet",
		cache: false,
		dataType: "text",
		data: {
			operation: "getAllocationLog",
			zone: zone,
			size: size,
			type: "volume",
			startTime: start,
			endTime: end
		},
		success: function(data) {
			try{
				data=$.parseXML(data);
				
				var amount=$(data).find("result").text();
				$(graphArea).append(amount+" 个");
				
				reloadGraph(zone, size, amount);
			}catch(e){};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if(window.console){console.log(textStatus);}
		}
	});
}

function reloadGraph(zone, key, value) {
	if(!this.data) this.data=new Object();
	if(!this.data[zone]) this.data[zone]=new Array();
	
	var flag=true;
	for(i=0; i<this.data[zone].length; i++) {
		if(this.data[zone][i].key==key) {
			this.data[zone].splice(i, 1, {key: key, value: value});
			flag=false;
		}
	}
	if(flag) this.data[zone].push({key: key, value: value});
	
	var graphData=new Array();
	
	for(i=0; i<this.data[zone].length; i++) {
		var _size=parseInt(this.data[zone][i].value);
		var _label=(parseInt(this.data[zone][i].key) / (1024 * 1024 * 1024)) + " GB " +"("+this.data[zone][i].key+")";
		
		graphData.push([_label, _size]);
		
	}
	
	$("#graphArea_"+zone).empty();
	var plot1=jQuery.jqplot("graphArea_"+zone, [graphData], {
		seriesDefaults: {
			renderer: jQuery.jqplot.PieRenderer,
			rendererOptions: {
				showDataLabels: true
			}
		},
		legend: {
			show: true, 
			location: "e"
		}
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











