//rshao
//all fields of vm charge
var chargeFields = new Array("planname", "subscriptionfee", "description", "cpuallow", "cpulimit", "cpuprice", "memallow", "memlimit", "memprice", "downloadallow", "downloadlimit", "downloadprice", "uploadallow", "uploadlimit", "uploadprice", "ioreadallow", "ioreadlimit", "ioreadprice", "iowriteallow", "iowritelimit", "ioreadallow", "iowriteprice", "invoiceinterval", "invoiceduedays", "firstnotice", "suspendremoveinterval");
var zoneList={};
$(function() {

    registerTemplate();

    setup();

    initUi();
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("vmChargeList", Template_VMChargeList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("vmChargeRow", Template_VMChargeRow);
    $.template("createVMChargeItem", Template_CreateVMChargeItem);
    $.template("updateVMChargeItem", Template_CreateVMChargeItem);
    $.template("modelOption", Template_modelOption);

}

function setup() {
    $("#mainBody").empty();

    $("#banner").html(Locale["vmcharge.banner"]);

    var vmChargeList = $.tmpl("vmChargeList", [{id: "vmChargeList"}]).appendTo("#mainBody");
    // set up highlight & selection effect for image list
    $(vmChargeList).delegate(".vmChargeRow", "mouseover",  function() {
        $(this).addClass("hover");
    }).delegate(".vmChargeRow", "mouseout",  function() {
        $(this).removeClass("hover");
    });

    $(vmChargeList).delegate("[name='filter_zone']", "change", function() {
        filter(vmChargeList);
    });
    
    $(vmChargeList).delegate("[name='filter_planname']", "keyup", function() {
        filter(vmChargeList);
    });
    loadVMChargeList();

}

function loadVMChargeList() {
    var container = $("#vmChargeList tbody");
    $("<tr style='border:none'><td colspan='7'><em>" + Locale["vmcharge.message.loading"] + "</em></td></tr>").appendTo(container.empty());

    $.ajax({
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        type: "POST",
        data: {
            methodtype: "fetchallpricingmodels"
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();

                if (!data || data.length == 0) {
                    $("<em>" + Locale["vmcharge.message.no_data"] + "</em>").appendTo(container);
                } else {
                    var list = $.tmpl("vmChargeRow", data).appendTo(container);
                    for (var i = 0; i < list.length; i++) {
                        i % 2 == 0 ? $(list[i]).addClass("rowEven") : $(list[i]).addClass("rowOdds");
                    }
                    //add filter zone list
                    initZoneList();
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });

}
function initZoneList(){
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
				$("#filtermodel").empty();
				$("#filtermodel").append("<option value='' selected>" + Locale['vmcharge.message.choose.type.filter'] + "</option>");
				for(var i=0; i<data.length; i++) {
					 $("#filtermodel").append("<option value='" + data[i].zone + "'></option>");
	                 $("#filtermodel").find("option[value='" + data[i].zone + "']").text(data[i].zonename);
	                 zoneList[data[i].zone] = data[i].zonename;
				}
				
			}catch(e){printMessage("Data Broken ["+e+"]");};
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});

}
function filter(panel) {
    var rows = $(panel).find(".vmChargeRow");
    var zoneprefix = $.trim($(panel).find("[name='filter_zone']").val().toLowerCase());
    var plannameprefix = $.trim($(panel).find("[name='filter_planname']").val().toLowerCase());
    var count = 0;
    for (var i = 0; i < rows.length; i++) {
        var zone = $(rows[i]).find("input[zmlm\\\:item='zone']").val().toLowerCase();
        var planname = $(rows[i]).find("input[zmlm\\\:item='planname']").val().toLowerCase();
        if (0 != zone.indexOf(zoneprefix) || 0 != planname.indexOf(plannameprefix)) {
            $(rows[i]).hide();
        } else {
            $(rows[i]).show();
            interpolated(count++, rows[i]);
        }
    }
}
function hasType(id, typeprefix) {
    if (typeprefix == "pfm") {
        if (startWith(id, "pfm-year")) {
            return false;
        } else if (startWith(id, "pfm")) {
            return true;
        }
    }

    return id.indexOf(typeprefix) == 0 ? true: false;
}
function showCreateVMChargeItem() {
	if($("#updateVMChargeItem").length >0){
		$("#updateVMChargeItem").remove();
	}
	if($("#createVMChargeItem").length >0){
		$("#createVMChargeItem").remove();
	}
    var createVMChargeItem = $.tmpl("createVMChargeItem", [{
        id: "createVMChargeItem" }]).appendTo("#mainBody");
    $(".typelabel").show();
    $(".typeselect").show();
    $(".zonelabel").show();
    $(".zoneselect").show();
    //init software models
    fetchSoftWareModels();
    createVMChargeItem = $(createVMChargeItem).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + Locale["vmcharge.dialog.createcharge"],
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "850px",
        buttons: [{
            text: Locale["vmcharge.dialog.confirm"],
            click: function() {
                var chargeFieldsValues = $.map(chargeFields, function(value, index) {
                    var fieldValue = $(createVMChargeItem).find("input[zmlm\\\:item='" + value + "']").val();
                    return fieldValue;
                });
                
                var zones = $(this).find("select[name='zones']").val();
                if (isNull(zones)) {
                    printMessage(Locale["vmcharge.message.choose.field"].sprintf(Locale["vmcharge.label.field.zone"]));
                    return;
                }
                var zonesString;
                if(zones.length>1){
                	zonesString = zones.join(":");
                }else{
                	zonesString = zones[0];
                }
                if(!checkValidField(chargeFields,chargeFieldsValues)){
                	return ;
                }
                var newValueArray = buildStringArray(chargeFields, chargeFieldsValues);
                //return;
                var type = $(this).find("select[zmlm\\\:item='softwaremodel']").val();
                if (isNull(type)) {
                    printMessage(Locale["vmcharge.message.choose.field"].sprintf(Locale["vmcharge.label.field.softwaremodel"]));
                    return;
                }
              
                newValueArray["softwaremodel"] = type;

                if (!checkNum(newValueArray)) {
                    printMessage(Locale["vmcharge.message.choose.num"]);
                    return;
                }
                 
                newValueArray["zones"] = zonesString;
                
                createVMChargeItemWithArray(newValueArray);
                $(this).dialog("close");
            }
        },
        {
            text: Locale["vmcharge.dialog.cancel"],
            click: function() {
                $(this).dialog("close");
            }
        }]
    });
    //init zone list
    $("#selzone").empty();
    for(var zone in zoneList){
    	 $("#selzone").append("<option value='" + zone + "'></option>");
         $("#selzone").find("option[value='" + zone + "']").text(zoneList[zone]);
    }
    //clear init data
    $("#selzone").multiselect("destroy");
    $("#selzone").multiselect({
    	header: false,
    	height:120,
		minWidth: 140, 
		selectedList: 0,
		position: {
		      my: 'left bottom',
		      at: 'left top'
		   },
		noneSelectedText: Locale["vmcharge.label.field.type.choose"],
		selectedText: '# '+Locale["vmcharge.label.field.type.choosen"]
    	});
    $(createVMChargeItem).dialog("open");
}

function fetchSoftWareModels() {
    $.ajax({
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        type: "POST",
        data: {
            methodtype: "fetchsoftwaremodels"
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);
                if (data.length > 0) {
                	$("#selplantype").empty();
                	$("#selplantype").append('<option value="" selected>' + Locale["vmcharge.label.field.type.choose"] + '</option>');
                	var list = $.tmpl("modelOption", data).appendTo($("#selplantype"));
                }
            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function createVMChargeItemWithArray(newValueArray) {
    var pd = showProcessingDialog();
    var param = newValueArray;
    param["methodtype"] = "savepricingmodel";
    param["submittype"] = "save";
    //for create function, id is generated by server,but need the parameter name
    param["vmplanid"] = "1";
    param["deductfrombalance"] = "false";
    param["suspendfrombalance"] = "false";
    $.ajax({
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        type: "POST",
        data: param,
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {
                data = $.parseJSON(data);

                switch (data.status) {
                case "done":
                    printMessage(Locale["vmcharge.message.charge.new.success"]);
                    loadVMChargeList();
                    break;
                case "missingdata":
                    printMessage(Locale["vmcharge.message.charge.new.missingdata"]);
                    break;
                case "failed":
                    ;
                case "exception":
                    printMessage(Locale["vmcharge.message.charge.new.error"]);
                    break;
                default:
                    printMessage(Locale["vmcharge.message.charge.undefined"].sprintf(data.status));
                    break;
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function showUpdateVMChargeItem(which, isUpdate) {
    var row = $(which).parents(".vmChargeRow").first();
    if($("#updateVMChargeItem").length >0){
		$("#updateVMChargeItem").remove();
	}
	if($("#createVMChargeItem").length >0){
		$("#createVMChargeItem").remove();
	}
    var updateVMChargeItemPanel = $.tmpl("updateVMChargeItem", [{
        id: "updateVMChargeItem"
    }]).appendTo("#mainBody");
    var titleTip = Locale["vmcharge.label.operation.updateCharge"];
    if (!isUpdate) {
        titleTip = Locale["vmcharge.dialog.deailcharge"];
    }
    var okButton = Locale["vmcharge.label.operation.updateCharge"];
    if (!isUpdate) {
        okButton = Locale["vmcharge.dialog.update"];
    }
    var cancelButton = Locale["vmcharge.dialog.cancel"];
    if (!isUpdate) {
        cancelButton = Locale["vmcharge.dialog.close"];
    }
    updateVMChargeItemPanel = $(updateVMChargeItemPanel).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + titleTip,
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "850px",
        buttons: [{
            text: okButton,
            click: function() {
                var chargeFieldsValues = $.map(chargeFields,  function(value, index) {
                    var fieldValue = $(updateVMChargeItemPanel).find("input[zmlm\\\:item='" + value + "']").val();
                    return fieldValue;
                });
                if(!checkValidField(chargeFields,chargeFieldsValues)){
                	return ;
                }
                var newValueArray = buildStringArray(chargeFields, chargeFieldsValues);
                if (!checkNum(newValueArray)) {
                    printMessage(Locale["vmcharge.message.choose.num"]);
                    return;
                }
                newValueArray["vmplanid"] = $(row).find("input[zmlm\\\:item='vmplanid']").val();
                newValueArray["deductfrombalance"] = $(row).find("input[zmlm\\\:item='deductfrombalance']").val();
                newValueArray["suspendfrombalance"] = $(row).find("input[zmlm\\\:item='suspendfrombalance']").val();
                newValueArray["zones"] = $(row).find("input[zmlm\\\:item='zone']").val();
                updateVMChargeItem(newValueArray);

                $(this).dialog("destroy");

            }
        },
        {
            text: cancelButton,
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
    //del type
    $("#selplantype").empty();
	//$("#selplantype").append("<option value='' selected>" + Locale['vmcharge.label.field.type.choose'] + "</option>");
    // set former data
    $(".typelabel").hide();
    $(".typeselect").hide();
    $(".zonelabel").hide();
    $(".zoneselect").hide();
    var rowValues = $.map(chargeFields, function(value, index) {
        var formerValue = $(row).find("input[zmlm\\\:item='" + value + "']").val();
        $(updateVMChargeItemPanel).find("input[zmlm\\\:item='" + value + "']").val(formerValue);
    });
   $(updateVMChargeItemPanel).dialog("open");
}

function updateVMChargeItem(newValueArray) {

    var pd = showProcessingDialog();
    var param = newValueArray;
    param["methodtype"] = "savepricingmodel";
    param["submittype"] = "update";
    $.ajax({
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        type: "POST",
        data: param,
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {
                data = $.parseJSON(data);

                switch (data.status) {
                case "done":
                    printMessage(Locale["vmcharge.message.charge.update.success"]);
                    loadVMChargeList();
                    break;
                case "missingdata":
                    printMessage(Locale["vmcharge.message.charge.new.missingdata"]);
                    break;
                case "failed":
                    ;
                case "exception":
                    printMessage(Locale["vmcharge.message.charge.update.error"]);
                    break;
                default:
                    printMessage(Locale["vmcharge.message.charge.undefined"].sprintf(data.status));
                    break;
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function remove(which) {
    var row = $(which).parents(".vmChargeRow").first();
    var vmplanid = $(row).find("input[zmlm\\\:item='vmplanid']").val();
    var planname = $(row).find("input[zmlm\\\:item='planname']").val();
    if (!confirm(Locale["vmcharge.confirm.remove"].sprintf(planname))) return;
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        cache: false,
        data: {
            methodtype: "removepricingmodel",
            vmplanid: vmplanid
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["vmcharge.message.remove.done"];
                    break;
                case "failed":
                    ;
                case "exception":
                    msg = Locale["vmcharge.message.remove.fail"];
                    break;
                default:
                    msg = Locale["vmcharge.message.remove.fail"].sprintf(data.status);
                }
                printMessage(msg);
                loadVMChargeList();
            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function printError(jqXHR, textStatus, errorThrown) {
    printMessage("Connection Broken: " + textStatus + ", " + errorThrown);
}

function printMessage(msg) {
    return $.tmpl("messageBoxTemplate", [{
        message: msg
    }]).appendTo("#mainBody").dialog({
        resizable: false,
        modal: true,
        buttons: [{
            text: Locale["vmcharge.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["vmcharge.dialog.processing"] + "</div>").dialog({
        autoOpen: true,
        width: 240,
        height: 100,
        resizable: false,
        modal: true,
        closeOnEscape: false,
        open: function(event, ui) {
            $(".ui-dialog-titlebar-close").hide();
        },
        buttons: {}
    });
    return view;
}

function initUi() {
    $("button").button();
}

function checkValidField(chargeFields,chargeFieldsValues) {
    for (i in chargeFieldsValues) {
        if (isNull(chargeFieldsValues[i])) {
        	 var name = "vmcharge.label.field."+ chargeFields[i];
        	 printMessage(Locale["vmcharge.message.choose.field"].sprintf(Locale[name]));
             return false;
        }
    }
    return true;
}

function isNull(value) {
    if (null == value || "" == $.trim(value) || value.length == 0 || value == "n/a") {
        return true;
    } else {
        return false;
    }
}

function buildStringArray(index, val) {
    var i = 0;
    var length = index.length;
    var newStringArray = {};
    if (length == val.length) {
        for (i; i < length; i++) {
            newStringArray[index[i]] = val[i];
        }
    }
    return newStringArray;
}

function interpolated(i, row) {
    $(row).removeClass("rowEven").removeClass("rowOdds");
    i % 2 == 0 ? $(row).addClass("rowEven") : $(row).addClass("rowOdds");
}

function checkNum(fieldsValueArray) {
    var i = 0;
    var length = chargeFields.length;
    for (i; i < length; i++) {
        if (chargeFields[i] == "planname" || chargeFields[i] == "description" ) {
            continue;
        } else {
            var value = fieldsValueArray[chargeFields[i]]
            var re = /^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|([1-9][0-9]*)|([0][.][0-9]+[1-9]*)|(0))$/;
            if (!re.test(value)) {
                return false;
            } else continue;
        }
    }
    return true;
}
function formatModelType(type) {
    if (startWith(type, "PAG")) {
        return Locale["vmcharge.label.field.type.pag"];
    }
    if (startWith(type, "PFM-year")) {
        return Locale["vmcharge.label.field.type.pfmyear"];
    }
    if (startWith(type, "PFM")) {
        return Locale["vmcharge.label.field.type.pfm"];
    }
    if (startWith(type, "TRY")) {
        return Locale["vmcharge.label.field.type.try"];
    }
    return type;

}
function startWith(value, begin) {
    if (begin == null || begin == "" || value.length == 0 || begin.length > value.length) return false;
    if (value.substr(0, begin.length) == begin) return true;
    else return false;
    return true;
}

function makeUUID() {
    var S4 = function () {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    };
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}