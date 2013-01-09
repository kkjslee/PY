//rshao
//all fields of app public excepts imageid sftmodeltype and vmplanids
var appFields = new Array( "sftname", "sftdescription", "sftintroduction", "sftpricingdes", "sftlicenselength", "sftlicenseamount", "sftprice", "resdescription","maxcpu", "defaultcpu", "maxmem", "defaultmem");
var zoneList = new Array();
$(function() {

    registerTemplate();

    setup();

});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("appMgrTabs", Template_AppMgrTabs);
    $.template("tab-publishapp", Template_PublishApp);
    $.template("tab-listapp", Template_AppList);
    $.template("vmImageList", Template_VMImageList);
    $.template("vmImageRow", Template_VMImagekRow);
    $.template("createAppItem", Template_CreateAppItem);
    $.template("resourceConfig", Template_ResourceConfig);
    $.template("vmChargeList", Template_VMChargeTypeList);
    $.template("vmChargeRow", Template_VMChargeTypeRow);
    $.template("publishAppList", Template_PublishedAppList);
    $.template("appRow", Template_PublishedAppRow);
    $.template("modelOption", Template_modelOption);
    $.template("resourceConfigList", Template_ResourceConfigList);
    $.template("resourceConfigRow", Template_ResourceConfigRow);
}

function setup() {
    $("#mainBody").empty();
    $("#banner").html(Locale["appmgr.banner"]);

    var tabs = $.tmpl("appMgrTabs", [{
        id: "appMgrTabs"
    }]).appendTo("#mainBody");
    tabs.tabs().css("border", "0");
    addTabPanel(tabs, "tab-publishapp");
    addTabPanel(tabs, "tab-listapp");
    $(tabs).bind("tabsshow",
    function(event, ui) {
        if (null == ui || ui.panel.id == "tab-publishapp") {
            loadPublishApp();
            initUi();
        } else if (ui.panel.id == "tab-listapp") {
            loadAppList();
        }
    }).triggerHandler("tabsshow");
}

function publishApp() {
    var imageid = $("#tab-publishapp").find("input[zmlm\\\:item='imageid'][checked]").val();
    if (isNull(imageid)) {
        printMessage(Locale["appmgr.message.choose.field"].sprintf(Locale["appmgr.label.filed.image"]));
        return;
    }
    var vmplan = new Array();
    $("#tab-publishapp").find("input[zmlm\\\:item='vmplanid']").each(function(i) {
        if (this.checked) {
            vmplan.push($(this).val());
        }
    });
    if (isNull(vmplan)) {
        printMessage(Locale["appmgr.message.choose.field"].sprintf(Locale["appmgr.label.field.modeltype"]));
        return;
    }
    var vmPlanString;
    if (vmplan.length > 1) {
        vmPlanString = vmplan.join(encodeURI(":"));
    } else {
        vmPlanString = vmplan[0];
    }
    var sftmodeltype = $("#tab-publishapp").find("select[zmlm\\\:item='sftmodeltype']").val();
    if (isNull(sftmodeltype)) {
        printMessage(Locale["appmgr.message.choose.field"].sprintf(Locale["appmgr.label.field.chargetype"]));
        return;
    }
    var appFieldsValues = $.map(appFields, function(value, index) {
        var fieldValue = $("#tab-publishapp").find("input[zmlm\\\:item='" + value + "']").val();
        return fieldValue;
    });

    if(!checkValidField(appFields,appFieldsValues)){
    	return ;
    }
    var newValueArray = buildStringArray(appFields, appFieldsValues);
    if (!checkNum(newValueArray)) {
        return;
    }
    var sftlicenseamount = $("#tab-publishapp").find("input[zmlm\\\:item='sftlicenseamount']").val();
    if(!checkNaturalNum(sftlicenseamount)){
    	 printMessage(Locale["appmgr.message.choose.natural"].sprintf(Locale["appmgr.label.field.sftlicenseamount"]));
         return;
    }
    var defaultCpu = $("#tab-publishapp").find("input[zmlm\\\:item='defaultcpu']").val();
    var maxCpu = $("#tab-publishapp").find("input[zmlm\\\:item='maxcpu']").val();
    if(!checkPositiveNum(defaultCpu)){
    	 printMessage(Locale["appmgr.message.positive.integer"].sprintf(Locale["appmgr.label.resource.startcpu"]));
         return;
    }
    if(maxCpu - defaultCpu < 0){
    	 printMessage(Locale["appmgr.message.ge.num"].sprintf(Locale["appmgr.label.field.maxcpu"],Locale["appmgr.label.field.defaultcpu"]));
         return;
    }
    var defaultmem = $("#tab-publishapp").find("input[zmlm\\\:item='defaultmem']").val();
    var maxmem = $("#tab-publishapp").find("input[zmlm\\\:item='maxmem']").val();
    if(defaultmem <= 0){
    	 printMessage(Locale["appmgr.message.choose.positive"].sprintf(Locale["appmgr.label.field.defaultmem"]));
         return;
    }
    if(maxmem - defaultmem < 0){
    	 printMessage(Locale["appmgr.message.ge.num"].sprintf(Locale["appmgr.label.field.maxmem"], Locale["appmgr.label.field.defaultmem"]));
         return;
    }
    var param = newValueArray;
    param["methodtype"] = "publishsoftware";
    param["vmplanids"] = vmPlanString;
    param["sftmodeltype"] = sftmodeltype;
    param["imageid"] = imageid;
    var pd = showProcessingDialog();
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
                    printMessage(Locale["appmgr.message.charge.new.success"]);
                    break;
                case "missingdata":
                    printMessage(Locale["appmgr.message.charge.new.missingdata"]);
                    break;
                case "failed":
                    ;
                case "exception":
                    printMessage(Locale["appmgr.message.charge.new.error"]);
                    break;
                default:
                    printMessage(Locale["appmgr.message.undefined"].sprintf(data.status));
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
function clearAppForm() {
    $(":radio").removeAttr("checked");
    $(":checkbox").removeAttr("checked");
    $(".vmImagekRow").removeClass("rowOdds");
    $(".vmChargeRow").removeClass("rowOdds");
    $("#sftmodeltype").get(0).selectedIndex = 0;
    $.map(appFields,
    function(value, index) {
        var fieldValue = $("#tab-publishapp").find("input[zmlm\\\:item='" + value + "']").val("");
    });
}
// name : app or charge
function initZoneList(name) {
    $.ajax({
        type: "POST",
        url: Server + "/RedDragonEnterprise/InformationRetriverServlet",
        cache: false,
        data: {
            methodtype: "getzonelist"
        },
        success: function(data) {
            try {
                data = $.parseJSON(data);
                $("#filtermodel_" + name).empty();
                for (var i = 0; i < data.length; i++) {
                    $("#filtermodel_" + name).append("<option value='" + data[i].zone + "'></option>");
                    $("#filtermodel_" + name).find("option[value='" + data[i].zone + "']").text(data[i].zonename);
                    zoneList[data[i].zone] = data[i].zonename;
                }
                if (data.length > 0) {
                    $("#filtermodel_" + name).get(0).selectedIndex = 0;
                    if(name == "app"){
                    	loadPublishedAppWithZone($("#filtermodel_"+ name).val());
                    }else if(name == "charge"){
                    	loadVMChargeList($("#filtermodel_"+ name).val());
                    }
                }

            } catch(e) {
                printMessage("Data Broken [" + e + "]");
            };
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });

}
function loadAppList() {
    $("#publishedAppListDiv").empty();
    //init zone
    var publishAppList = $.tmpl("publishAppList", [{
        id: "publishAppList"
    }]).appendTo("#publishedAppListDiv");
    $(publishAppList).delegate(".appRow", "mouseover", function() {
        $(this).addClass("hover");
    }).delegate(".appRow", "mouseout", function() {
        $(this).removeClass("hover");
    });
    initZoneList("app");
    //add zone filter
    $(publishAppList).delegate("[name='filter_zone_app']", "change", function() {
        loadPublishedAppWithZone($(this).val());
    });

}
function loadPublishedAppWithZone(zone) {
    $("#resourceConfigListDiv").empty();
    var container = $("#publishAppList tbody");
    $("<tr style='border:none'><td colspan='6'><em>" + Locale["appmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());

    $.ajax({
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        type: "POST",
        data: {
            methodtype: "fetchsoftware",
            zone: zone
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();

                if (!data || data.length == 0) {
                    $("<em>" + Locale["appmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                    var list = $.tmpl("appRow", data).appendTo(container);
                    $(list).find("td:not(:last)").bind("click", function() {

                        $(this).parents("#publishAppList").find("input[name='softwareid']").removeAttr("checked");
                        $(this).parent().siblings().removeClass("rowOdds");
                        $(this).parent().siblings().toggle();
                        $(this).parent().find("input[name='softwareid']").attr("checked", "checked");
                        $(this).parent().addClass("rowOdds");

                        var softwareid = $(this).parent().find("input[zmlm\\\:item='softwareid']").val();
                        loadResourceConfigList($("#filtermodel_app").val(), softwareid)
                    })
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

function loadResourceConfigList(zone, softwareid) {
    // append the table to  div
    $("#resourceConfigListDiv").empty();
    var publishAppList = $.tmpl("resourceConfigList", [{
        id: "resourceConfigList"
    }]).appendTo("#resourceConfigListDiv");
    var container = $("#resourceConfigList tbody");
    $(publishAppList).delegate(".resourceConfigRow", "mouseover",  function() {
        $(this).addClass("hover");
    }).delegate(".resourceConfigRow", "mouseout", function() {
        $(this).removeClass("hover");
    });
    $("<tr style='border:none'><td colspan='8'><em>" + Locale["appmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());

    $.ajax({
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        type: "POST",
        data: {
            methodtype: "fetchsoftwareresources",
            zone: zone,
            softwareid: softwareid
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();

                if (!data || data.length == 0) {
                    $("<em>" + Locale["appmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                    var list = $.tmpl("resourceConfigRow", data).appendTo(container);
                    for (var i = 0; i < list.length; i++) {
                        i % 2 == 0 ? $(list[i]).addClass("rowEven") : $(list[i]).addClass("rowOdds");
                    }
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

function removeApp(which) {
    var row = $(which).parents(".appRow").first();
    var softwareid = $(row).find("input[zmlm\\\:item='softwareid']").val();
    var softwarename = $(row).find("input[zmlm\\\:item='softwarename']").val();
    if (!confirm(Locale["appmgr.confirm.remove"].sprintf(softwarename))) return;

    var pd = showProcessingDialog();
    var zone = $("#filtermodel_app").val();
    $.ajax({
        type: "POST",
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        cache: false,
        data: {
            methodtype: "deletesoftware",
            softwareid: softwareid,
            zone: zone
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["appmgr.message.remove.done"];
                    break;
                case "orderexists":
                    msg = Locale["appmgr.message.remove.bought"];
                    break;
                case "failed":
                    ;
                case "exception":
                    msg = Locale["appmgr.message.remove.fail"];
                    break;
                default:
                    msg = Locale["appmgr.message.undefined"].sprintf(data.status);
                }
                printMessage(msg);
                loadPublishedAppWithZone(zone);
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
function removeAppResource(which) {
    var row = $(which).parents(".resourceConfigRow").first();
    var sftresid = $(row).find("input[zmlm\\\:item='sftresid']").val();
    var sftresdes = $(row).find("input[zmlm\\\:item='sftresdes']").val();
    if (!confirm(Locale["appmgr.confirm.remove"].sprintf(sftresdes))) return;
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        cache: false,
        data: {
            methodtype: "deletesftresource",
            sftresourceid: sftresid
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["appmgr.message.remove.done"];
                    break;
                case "orderexists":
                    msg = Locale["appmgr.message.remove.bought"];
                    break;
                case "failed":
                    ;
                case "exception":
                    msg = Locale["appmgr.message.remove.fail"];
                    break;
                default:
                    msg = Locale["appmgr.message.undefined"].sprintf(data.status);
                }
                printMessage(msg);
                loadResourceConfigList($("#filtermodel_app").val(), $("input[name='softwareid'][checked]").val())
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

function loadPublishApp() {
    //get vm image
    loadVMImageList();

    //init software information
    initSoftwareInfo();
    //init resource configuration
    initResourceConfig();
    //load vm charge list
    loadVMCharge();
}

function initSoftwareInfo() {
    $("#createAppItemDiv").empty();
    var createAppItem = $.tmpl("createAppItem", [{
        id: "createAppItem"
    }]).appendTo("#createAppItemDiv");
    fetchSoftWareModels();
}

function initResourceConfig() {
    $("#resourceConfigDiv").empty();
    var resourceConfig = $.tmpl("resourceConfig", [{
        id: "resourceConfig"
    }]).appendTo("#resourceConfigDiv");
}

function loadVMImageList() {
    // append the vm image table to image div
    $("#vmImageListDiv").empty();
    var vmImageList = $.tmpl("vmImageList", [{
        id: "vmImageList"
    }]).appendTo("#vmImageListDiv");
    $(vmImageList).delegate(".vmImagekRow", "mouseover",  function() {
        $(this).addClass("hover");
    }).delegate(".vmImagekRow", "mouseout",  function() {
        $(this).removeClass("hover");
    }).delegate(".vmImagekRow", "click",  function() {
        $(this).parents("#vmImageList").find("input[zmlm\\\:item='imageid']").removeAttr("checked");
        $(this).siblings().removeClass("rowOdds");
        $(this).find("input[zmlm\\\:item='imageid']").attr("checked", "checked");
        $(this).addClass("rowOdds");
        //TO GET RELATED DATA WITH SOFTWARE
        fetchsoftwarebyimageid( $(this).find("input[zmlm\\\:item='imageid']").val());
        
    });
    var container = $("#vmImageList tbody");
    $("<tr style='border:none'><td colspan='7'><em>" + Locale["appmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());

    $.ajax({
        url: Server + "/RedDragonEnterprise/VMImageManagement",
        type: "POST",
        data: {
            methodtype: "fetchvmimages"
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();

                if (!data || data.length == 0) {
                    $("<em>" + Locale["appmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                    var list = $.tmpl("vmImageRow", data).appendTo(container);
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
function fetchsoftwarebyimageid(imgid){
	clearFormerAppData();
	var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        cache: false,
        data: {
            methodtype: "fetchsoftwarebyimageid",
            imageid: imgid
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                if(data.status == "softwarenotexists" || data.status == "exception"){
                	return;
                }
                if(data){
                	setFormerAppDataSet(data);
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
function clearFormerAppData(){
	$("#createAppItem").find("input[zmlm\\\:item='sftname']").val("");
	$("#createAppItem").find("input[zmlm\\\:item='sftintroduction']").val("");
	$("#createAppItem").find("input[zmlm\\\:item='sftdescription']").val("");
	$("#createAppItem").find("input[zmlm\\\:item='sftpricingdes']").val("");
	$("#createAppItem").find("input[zmlm\\\:item='sftprice']").val("");
	$("#createAppItem").find("input[zmlm\\\:item='sftlicenselength']").val("").removeAttr("disabled");
	$("#createAppItem").find("input[zmlm\\\:item='sftlicenseamount']").val("").removeAttr("disabled");
	$("#createAppItem").find("select[zmlm\\\:item='sftmodeltype']").val("").removeAttr("disabled");
}
function setFormerAppDataSet(data){
	$("#createAppItem").find("input[zmlm\\\:item='sftname']").val(data.sftname);
	$("#createAppItem").find("input[zmlm\\\:item='sftintroduction']").val(data.sftintroduction);
	$("#createAppItem").find("input[zmlm\\\:item='sftdescription']").val(data.sftdescription);
	$("#createAppItem").find("input[zmlm\\\:item='sftpricingdes']").val(data.sftpricemodeldes);
	$("#createAppItem").find("input[zmlm\\\:item='sftprice']").val(data.sftprice);
	$("#createAppItem").find("input[zmlm\\\:item='sftlicenselength']").val(data.sftcontractlength).attr("disabled", "");
	$("#createAppItem").find("input[zmlm\\\:item='sftlicenseamount']").val(data.sftlicensenumber).attr("disabled", "");
	$("#createAppItem").find("select[zmlm\\\:item='sftmodeltype']").val(data.sftpricemodel).attr("disabled", "");
}
function addTabPanel(tabs, id) {
    var taburl = "#" + id;
    var panel = $(tabs).tabs("widget").children(taburl);
    $.tmpl(id).appendTo(panel);
}
function loadVMCharge() {
	$("#vmChargeListDiv").empty();
    var vmChargeList = $.tmpl("vmChargeList", [{ id: "vmChargeList" }]).appendTo("#vmChargeListDiv");
    $(vmChargeList).delegate(".vmChargeRow", "mouseover",  function() {
        $(this).addClass("hover");
    }).delegate(".vmChargeRow", "mouseout",  function() {
        $(this).removeClass("hover");
    }).delegate(".vmChargeRow", "click",   function() {
        var vmplanid = $(this).find("input[zmlm\\\:item='vmplanid']");
        if ($(vmplanid).attr("checked") == "checked") {
            $(vmplanid).removeAttr("checked");
            $(this).removeClass("rowOdds");
        } else {
            $(vmplanid).attr("checked", "checked");
            $(this).addClass("rowOdds");
        }
    });
    	loadVMChargeList();
}
function loadVMChargeList() {
    
    var container = $("#vmChargeList tbody");
    $("<tr style='border:none'><td colspan='5'><em>" + Locale["appmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());

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
                    $("<em>" + Locale["appmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                    var list = $.tmpl("vmChargeRow", data).appendTo(container);
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
                    $("#sftmodeltype").empty();
                    $("#sftmodeltype").append('<option value="" selected>' + Locale["appmgr.label.field.type.choose"] + '</option>');
                    var list = $.tmpl("modelOption", data).appendTo($("#sftmodeltype"));
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
function changeAppStatus(which, marketsetting) {
    var row = $(which).parents(".appRow").first();
    var softwarestatus = $(row).find("input[zmlm\\\:item='softwarestatus']").val();
    if (marketsetting == 0) {
        marketsetting = "hide";
    } else {
        marketsetting = "show";
    }
    if (softwarestatus == marketsetting) {
        printMessage(Locale["appmgr.confirm.samestatus"]);
        return;
    }
    var softwareid = $(row).find("input[zmlm\\\:item='softwareid']").val();
    if (!confirm(Locale["appmgr.confirm.changestatus"])) return;
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + "/RedDragonEnterprise/ApplicationPublishManager",
        cache: false,
        data: {
            methodtype: "changesftstatus",
            softwareid: softwareid,
            marketsetting: marketsetting
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["appmgr.message.change.done"];
                    break;
                case "failed":
                    ;
                case "exception":
                    msg = Locale["appmgr.message.change.fail"];
                    break;
                default:
                    msg = Locale["appmgr.message.undefined"].sprintf(data.status);
                }
                printMessage(msg);
                loadPublishedAppWithZone($("#filtermodel_app").val());
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
            text: Locale["appmgr.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["appmgr.dialog.processing"] + "</div>").dialog({
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

function checkValidField(appFields,appFieldsValues) {
    for (i in appFieldsValues) {
        if (isNull(appFieldsValues[i])) {
        	 var name = "appmgr.label.field."+ appFields[i];
        	 printMessage(Locale["appmgr.message.choose.field"].sprintf(Locale[name]));
             return false;
        }
    }
    return true;
}

function isNull(value) {
    if (null == value || "" == $.trim(value) || value == "n/a" || value.length == 0) {
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

function formatOstype(ostype) {
    if (ostype.toLowerCase().match("win")) {
        return "<img style='width:16px;margin-right:4px;' src='css/image/windows.png'/>" + ostype;
    } else if (ostype.toLowerCase().match("linux|centos|ubuntu|debian")) {
        return "<img style='width:16px;margin-right:4px;' src='css/image/linux.png'/>" + ostype;
    } else {
        return "<span class='ui-icon ui-icon-help' style='float:left; margin:0 4px 0 0;'></span>" + ostype;
    }
}

function checkNum(fieldsValueArray) {
    var i = 0;
    var length = appFields.length;
    for (i; i < length; i++) {
        if (appFields[i] == "sftname" || appFields[i] == "sftdescription" || appFields[i] == "sftintroduction" || appFields[i] == "sftpricingdes" || appFields[i] == "resdescription") {
            continue;
        } else {
            var value = fieldsValueArray[appFields[i]]
            var re = /^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|([1-9][0-9]*)|([0][.][0-9]+[1-9]*)|(0))$/;
            if (!re.test(value)) {
            	 var name = "appmgr.label.field."+ appFields[i];
            	printMessage(Locale["appmgr.message.choose.num"].sprintf(Locale[name]));
                return false;
            } else continue;
        }
    }
    return true;
}
function startWith(value, begin) {
    if (begin == null || begin == "" || value.length == 0 || begin.length > value.length) return false;
    if (value.substr(0, begin.length) == begin) return true;
    else return false;
    return true;
}

function formatSize(bytes) {
    if (null == bytes || 0 == bytes) return "--";

    var i = 0;
    while (1023 < bytes) {
        bytes /= 1024; ++i;
    };
    return i ? bytes.toFixed(2) + ["", " KB", " MB", " GB", " TB"][i] : bytes + " bytes";
}
function localOperation(ope) {
    if (!isNull(ope)) {
        if (ope == "hide") {
            return Locale["appmgr.label.operation.hide"];
        } else if (ope == "show") {
            return Locale["appmgr.label.operation.show"];
        }
    } else {
        return "";
    }
}

function statusStyle(softwarestatus) {
    if (!isNull(softwarestatus)) {
        if (softwarestatus == "show") {
            return true;
        }
    }
    return false;

}

function checkPositiveNum(num) {
    var re = /^[1-9]\d*$/;
    if (!re.test(num)) {
        return false;
    } else return true;
}
function checkNaturalNum(num) {
    var re = /^\d+$/;
    if (!re.test(num)) {
        return false;
    } else return true;
}