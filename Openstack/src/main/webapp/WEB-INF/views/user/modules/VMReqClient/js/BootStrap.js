//rshao
var vmReqClientTable;
var vmReqClientServletURI="/RedDragonEnterprise/EnterpriseManager";
var vmReqClientZoneList = new Array();
var vmReqClientLoginUser = getUsername();
$(function() {
    registerTemplate();

    setup();
    
    initUi();
    
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("vmReqClientList", Template_VMReqClientList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("vmReqClientRow", Template_VMReqClientRow);
}

function setup() {
    $("#mainBody").empty();
    $("#banner").html(Locale["vmReqClient.banner"]);

    var vmReqClientList = $.tmpl("vmReqClientList", [{
        id: "vmReqClientList"
    }]).appendTo("#mainBody");

    loadvmReqClientList();
}


function loadvmReqClientList() {
	if($('.dataTableFile tbody tr').length > 0){
		vmReqClientTable.fnClearTable();
	}
    var container = $("#vmReqClientList tbody");
    $("<tr style='border:none'><td cospan='13'><em>" + Locale["vmReqClient.message.loading"] + "</em></td></tr>").appendTo(container.empty());
    var loginuser = getUsername();
    $.ajax({
        url: Server + vmReqClientServletURI,
        type: "POST",
        data: {
    		operation: "fetchvmrequests",
    		filters:"login"+encodeURI(":") + vmReqClientLoginUser
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();
                if (data.status == "exception") {
                    printMessage(Locale["vmReqClient.message.data.retrieve.error"]);
                    return;
                }
                if(vmReqClientTable){
                	vmReqClientTable.fnClearTable();
            	}
                if (!data || data.length == 0) {
                    $("<em>" + Locale["vmReqClient.message.no_data"] + "</em>").appendTo(container);
                } else {
                    $.tmpl("vmReqClientRow", data).appendTo(container);
                }
                loadPage();

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
    
}

function remove(which) {
	var row = $(which).parents(".vmReqClientRow").first();
    var vmrequestid = $(row).find("input[zmlm\\\:item='vmrequestid']").val();
    if (!confirm(Locale["vmReqClient.confirm.request.remove"])) return;
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + vmReqClientServletURI,
        cache: false,
        data: {
    		operation: "deletevmrequest",
    		vmrequestid: vmrequestid
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["vmReqClient.message.remove.done"];
                    loadvmReqClientList();
                    break;
                case "adminactiontaken":
                    msg = Locale["vmReqClient.message.remove.adminactiontaken"];
                    break;  
                case "error":
                    ;
                case "exception":
                    msg = Locale["vmReqClient.message.remove.fail"];
                    break;
                default:
                    msg = Locale["vmReqClient.message.undefined"].sprintf(data.status);
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
            text: Locale["vmReqClient.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["vmReqClient.dialog.processing"] + "</div>").dialog({
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
	vmReqClientTable = $('#vmReqClientList .dataTableFile').dataTable({
		"fnInitComplete": function(oSettings, json){
		$("#dataTableFile").find("#pager").removeAttr("id").appendTo($("#dataTableFile tfoot").find("td:first-child").empty());
		},
	    "sPaginationType": "full_numbers",
	    "bFilter": true,
	    "bSort": true,
	    "aoColumnDefs": [
	                     { 'bSortable': false, 'aTargets': [12] }
	                  ],
	    "bLengthChange": true,
	    "bDestroy":true,
	    "iDisplayLength":10,
	    "sDom": "<'#dataTableFile't<'#pager'lpi>>",
	    "oLanguage": {
	    "sLengthMenu": Locale["vmReqClient.page.show"],
	    "sInfoEmtpy": Locale["vmReqClient.page.nodata"],
	    "sZeroRecords": Locale["vmReqClient.page.foundnodata"], 
	    "sInfoFiltered": Locale["vmReqClient.page.foundtotal"],
	    "sProcessing": Locale["vmReqClient.page.loading"],
	    "sInfoEmpty":Locale["vmReqClient.page.infoempty"],
	    "sInfo": Locale["vmReqClient.page.startend"],
	    "oPaginate": {
	        "sFirst":  Locale["vmReqClient.page.first"],
	        "sPrevious": Locale["vmReqClient.page.previous"],
	        "sNext": Locale["vmReqClient.page.next"],
	        "sLast": Locale["vmReqClient.page.last"]
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
function formatSize(bytes) {
    if (null == bytes || 0 == bytes) return "--";

    var i = 0;
    while (1023 < bytes) {
        bytes /= 1024; ++i;
    };
    return i ? bytes.toFixed(2) + ["", " KB", " MB", " GB", " TB"][i] : bytes + " bytes";
}

function isNull(value) {
    if (null == value || "" == $.trim(value) || value == "n/a" || value.length == 0) {
        return true;
    } else {
        return false;
    }
}

