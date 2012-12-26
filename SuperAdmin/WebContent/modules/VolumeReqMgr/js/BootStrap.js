//rshao
var volumeReqMgrTable;
var volumeReqServletURI="/RedDragonEnterprise/EnterpriseManager";
$(function() {
    registerTemplate();

    setup();
    
    initUi();
    
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("volumeReqList", Template_VolumeReqList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("volumeReqRow", Template_VolumeReqRow);
    $.template("volumeRequestAction", Template_ReFuseVolumeReq);
}

function setup() {
    $("#mainBody").empty();
    $("#banner").html(Locale["volumereqmgr.banner"]);

    var volumeReqList = $.tmpl("volumeReqList", [{
        id: "volumeReqList"
    }]).appendTo("#mainBody");
    $("#filterHead1 select").bind("change",function(){
   	 loadVolumeReqList();
   });
   $("#filterHead0 input").keyup(function(e){
       if(e.which==13)
       	 loadVolumeReqList();
   });
    loadVolumeReqList();
}

function loadVolumeReqList() {
	if($('.dataTableFile tbody tr').length > 0){
		volumeReqMgrTable.fnClearTable();
	}
	var params = {};
	var filter = {};
	var count =0;
    var status = $("#filterHead1").find("select[zmlm\\\:item='status']").val();
    if(status.trim().length > 0 ){
    	params["filters"] = "status"+encodeURI(":")+status;
    	count++;
    }
    var volumename = $("#filterHead0").find("input[zmlm\\\:item='volumename']").val();
    if(volumename.trim().length > 0 ){
    	if(count > 0){
    		params["filters"] = params["filters"] + encodeURI(",") + "volumename"+encodeURI(":")+volumename;
    	}else{
    		params["filters"] = "volumename"+encodeURI(":")+volumename;
    	}
    }
    params["operation"]="fetchvolumerequests";
    var container = $("#volumeReqList tbody");
    $("<tr style='border:none'><td cospan='8'><em>" + Locale["volumereqmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());
    
    $.ajax({
        url: Server + volumeReqServletURI,
        type: "POST",
        data: params,
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();
                if (data.status == "exception") {
                    printMessage(Locale["volumereqmgr.message.data.retrieve.error"]);
                    return;
                }
                if (!data || data.length == 0) {
                    $("<em>" + Locale["volumereqmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                    $.tmpl("volumeReqRow", data).appendTo(container);
                }
                loadPage();
                fixBannerWidth();

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
    
}

function fixBannerWidth() {
    var tableWidth = $(".dataTableFile").width();
    var bannerWidth = $(".bannerheader").width();
    //margin is 20
  if (tableWidth + 40 > bannerWidth) $(".bannerheader").width(tableWidth + 40);
}

function approve(which) {
	var row = $(which).parents(".volumeReqRow").first();
    var volumerequestid = $(row).find("input[zmlm\\\:item='volumerequestid']").val();
    if (!confirm(Locale["volumereqmgr.confirm.request.approve"])) return;
    var loginuser = getUsername();
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + volumeReqServletURI,
        cache: false,
        data: {
    		operation: "processvolumerequest",
    		volumerequestid: volumerequestid,
    		action:"approve",
    		adminlogin:loginuser
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["volumereqmgr.message.approve.done"];
                   // updateOldRow();
                    break;
                case "notadmin":
                    msg = Locale["volumereqmgr.message.approve.notadmin"];
                    break;  
                case "volumerequestnotexists：":
                    msg = Locale["volumereqmgr.message.approve.volumerequestnotexists"];
                    break;
                case "lowvolumeinventory/":
                    msg = Locale["volumereqmgr.message.approve.lowvminventory"];
                    break;
                case "error":
                    ;
                case "exception":
                    msg = Locale["volumereqmgr.message.approve.fail"];
                    break;
                default:
                    msg = Locale["volumereqmgr.message.undefined"].sprintf(data.status);
                }
                printMessage(msg);
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

function updateOldRow(status,oldRow, notes){
	var row = volumeReqMgrTable.fnGetPosition($(oldRow).get(0));
	if(status == "approve"){
		volumeReqMgrTable.fnUpdate(Locale["volumereqmgr.label.status.approved"], row ,2, 0);
		$(oldRow).find("td").eq(2).removeClass("orange");
		$(oldRow).find("td").eq(2).addClass("green");
	}else if(status=="reject"){
		volumeReqMgrTable.fnUpdate(Locale["volumereqmgr.label.status.rejected"], row ,2, 0);
		$(oldRow).find("td").eq(2).removeClass("green");
		$(oldRow).find("td").eq(2).addClass("orange");
	}
	volumeReqMgrTable.fnUpdate(notes, row ,5, 0);
	$(oldRow).find("input[zmlm\\\:item='notes']").val(notes);
	$(oldRow).find("input[zmlm\\\:item='status']").val(status);
	volumeReqMgrTable.fnUpdate("", row ,7, 0);
	
}

function action(which,requestaction){
	if($("#volumeRequestAction").length >0 ){
		$("#volumeRequestAction").remove();
	}
    var volumeRequestAction = $.tmpl("volumeRequestAction", [{
        id: "volumeRequestAction"
    }]).appendTo("#mainBody");
    var row = $(which).parents(".volumeReqRow").first();
    var title = "";
    var ope = "";
    if(requestaction == 0){
    	title=Locale["volumereqmgr.dialog.approve"];
    	ope = "approve"
    }
    if(requestaction == 1){
    	title=Locale["volumereqmgr.dialog.reject"];
    	ope = "reject"
    }
    volumeRequestAction = $(volumeRequestAction).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + title,
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "300px",
        buttons: [{
            text: Locale["volumereqmgr.dialog.confirm"],
            click: function() {
                var notes = $(this).find("input[zmlm\\\:item='notes']").val();
                if (isNull(notes)) {
                    printMessage(Locale["volumereqmgr.message.choose.field"].sprintf(Locale["volumereqmgr.label.reject.notes"]));
                    return;
                }
                var volumerequestid = $(row).find("input[zmlm\\\:item='volumerequestid']").val();
                volumeAction(row, volumerequestid, notes, ope);
                $(this).dialog("close");
            }
        },
        {
            text: Locale["volumereqmgr.dialog.cancel"],
            click: function() {
                $(this).dialog("close");
            }
        }]
    });
    $("#volumeRequestAction").dialog("open");
}
function volumeAction(row, volumerequestid, notes, ope) {
    var loginuser = getUsername();
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + volumeReqServletURI,
        cache: false,
        data: {
	    	operation: "processvolumerequest",
	    	volumerequestid: volumerequestid,
			action:ope,
			adminlogin:loginuser,
			notes:notes
			
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["volumereqmgr.message.approve.done"];
                    updateOldRow(ope, row, notes);
                    break;
                case "notadmin":
                    msg = Locale["volumereqmgr.message.approve.notadmin"];
                    break;  
                case "volumerequestnotexists":
                    msg = Locale["volumereqmgr.message.approve.volumerequestnotexists"];
                    break;
                case "lowvolumeinventory":
                    msg = Locale["volumereqmgr.message.approve.lowvminventory"];
                    break;
                case "error":
                    ;
                case "exception":
                    msg = Locale["volumereqmgr.message.approve.fail"];
                    break;
                default:
                    msg = Locale["volumereqmgr.message.undefined"].sprintf(data.status);
                }
                printMessage(msg);
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
            text: Locale["volumereqmgr.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["volumereqmgr.dialog.processing"] + "</div>").dialog({
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
function loadPage(){
	volumeReqMgrTable = $('#volumeReqList .dataTableFile').dataTable({
		"fnInitComplete": function(oSettings, json){
		$("#dataTableFile").find("#pager").removeAttr("id").appendTo($("#dataTableFile tfoot").find("td:first-child").empty());
		},
	    "sPaginationType": "full_numbers",
	    "bFilter": true,
	    "bSort": true,
	    "aoColumnDefs": [
	                     { 'bSortable': false, 'aTargets': [7] }
	                  ],
	    "bLengthChange": true,
	    "bDestroy":true,
	    "iDisplayLength":10,
	    "sDom": "<'#dataTableFile't<'#pager'lpi>>",
	    "oLanguage": {
	    "sLengthMenu": Locale["volumereqmgr.page.show"],
	    "sInfoEmtpy": Locale["volumereqmgr.page.nodata"],
	    "sZeroRecords": Locale["volumereqmgr.page.foundnodata"], 
	    "sInfoFiltered": Locale["volumereqmgr.page.foundtotal"],
	    "sProcessing": Locale["volumereqmgr.page.loading"],
	    "sInfoEmpty":Locale["volumereqmgr.page.infoempty"],
	    "sInfo": Locale["volumereqmgr.page.startend"],
	    "oPaginate": {
	        "sFirst":  Locale["volumereqmgr.page.first"],
	        "sPrevious": Locale["volumereqmgr.page.previous"],
	        "sNext": Locale["volumereqmgr.page.next"],
	        "sLast": Locale["volumereqmgr.page.last"]
	    }
	}
	});
}

function initUi() {
    $("button").button();
}


function formatDate(longtime) {
    return new Date(longtime).format("yyyy-MM-dd hh:mm:ss");
}

function checkValidFieldForRecharge(amount, notes, reason, type) {
    if (isNull(amount) || isNull(notes) || isNull(reason) || isNull(type)) {
        return false;
    } else {
        return true;
    }
}

function isNull(value) {
    if (null == value || "" == $.trim(value) || value == "n/a") {
        return true;
    } else {
        return false;
    }
}


