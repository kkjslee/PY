//rshao
var volumeReqClientTable;
var volumeReqClientServletURI="/RedDragonEnterprise/EnterpriseManager";
var volumeReqClientLoginUser = getUsername();
$(function() {
    registerTemplate();

    setup();
    
    initUi();
    
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("volumeReqClientList", Template_VolumeReqClientList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("volumeReqClientRow", Template_VolumeReqClientRow);
}

function setup() {
    $("#mainBody").empty();
    $("#banner").html(Locale["volumeReqClient.banner"]);

    var volumeReqClientList = $.tmpl("volumeReqClientList", [{
        id: "volumeReqClientList"
    }]).appendTo("#mainBody");
    // set up highlight & selection effect for image list
    $(volumeReqClientList).delegate(".volumeReqClientRow", "mouseover", function() {
        $(this).addClass("hover");
    }).delegate(".volumeReqClientRow", "mouseout", function() {
        $(this).removeClass("hover");
    });

    loadVolumeReqClientList();
}
function loadVolumeReqClientList() {
    var container = $("#volumeReqClientList tbody");
    $("<tr style='border:none'><td cospan='7'><em>" + Locale["volumeReqClient.message.loading"] + "</em></td></tr>").appendTo(container.empty());
    var loginuser = getUsername();
    $.ajax({
        url: Server + volumeReqClientServletURI,
        type: "POST",
        data: {
    		operation: "fetchvolumerequests",
    		filters:"login"+encodeURI(":") + volumeReqClientLoginUser
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();
                if (data.status == "exception") {
                    printMessage(Locale["volumeReqClient.message.data.retrieve.error"]);
                    return;
                }
                if(volumeReqClientTable){
            		volumeReqClientTable.fnClearTable();
            	}
                if (!data || data.length == 0) {
                    $("<em>" + Locale["volumeReqClient.message.no_data"] + "</em>").appendTo(container);
                } else {
                    $.tmpl("volumeReqClientRow", data).appendTo(container);
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
	var row = $(which).parents(".volumeReqClientRow").first();
    var volumerequestid = $(row).find("input[zmlm\\\:item='volumerequestid']").val();
    if (!confirm(Locale["volumeReqClient.confirm.request.remove"])) return;
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + volumeReqClientServletURI,
        cache: false,
        data: {
    		operation: "deletevolumerequest",
    		volumerequestid: volumerequestid
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["volumeReqClient.message.remove.done"];
                    loadVolumeReqClientList();
                    break;
                case "adminactiontaken":
                    msg = Locale["volumeReqClient.message.remove.adminactiontaken"];
                    break;  
                case "error":
                    ;
                case "exception":
                    msg = Locale["volumeReqClient.message.remove.fail"];
                    break;
                default:
                    msg = Locale["volumeReqClient.message.undefined"].sprintf(data.status);
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
            text: Locale["volumeReqClient.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["volumeReqClient.dialog.processing"] + "</div>").dialog({
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
	volumeReqClientTable = $('#volumeReqClientList .dataTableFile').dataTable({
		"fnInitComplete": function(oSettings, json){
		$("#dataTableFile").find("#pager").removeAttr("id").appendTo($("#dataTableFile tfoot").find("td:first-child").empty());
		},
	    "sPaginationType": "full_numbers",
	    "bFilter": true,
	    "bSort": true,
	    "aoColumnDefs": [
	                     { 'bSortable': false, 'aTargets': [6] }
	                  ],
	    "bLengthChange": true,
	    "bDestroy":true,
	    "iDisplayLength":10,
	    "sDom": "<'#dataTableFile't<'#pager'lpi>>",
	    "oLanguage": {
	    "sLengthMenu": Locale["volumeReqClient.page.show"],
	    "sInfoEmtpy": Locale["volumeReqClient.page.nodata"],
	    "sZeroRecords": Locale["volumeReqClient.page.foundnodata"], 
	    "sInfoFiltered": Locale["volumeReqClient.page.foundtotal"],
	    "sProcessing": Locale["volumeReqClient.page.loading"],
	    "sInfoEmpty":Locale["volumeReqClient.page.infoempty"],
	    "sInfo": Locale["volumeReqClient.page.startend"],
	    "oPaginate": {
	        "sFirst":  Locale["volumeReqClient.page.first"],
	        "sPrevious": Locale["volumeReqClient.page.previous"],
	        "sNext": Locale["volumeReqClient.page.next"],
	        "sLast": Locale["volumeReqClient.page.last"]
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


function isNull(value) {
    if (null == value || "" == $.trim(value) || value == "n/a" || value.length == 0) {
        return true;
    } else {
        return false;
    }
}

function checkNum(num) {
    var re = /^[1-9]\d*$/;
    if (!re.test(num)) {
        return false;
    } else return true;
}

function interpolated(i, row) {
    $(row).removeClass("rowEven").removeClass("rowOdds");
    i % 2 == 0 ? $(row).addClass("rowEven") : $(row).addClass("rowOdds");
}