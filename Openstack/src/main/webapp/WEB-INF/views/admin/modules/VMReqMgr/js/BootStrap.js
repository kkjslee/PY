//rshao
var vmReqMgrTable;
var vmReqServletURI="/RedDragonEnterprise/EnterpriseManager";
$(function() {
    registerTemplate();

    setup();
    
    initUi();
    
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("vmReqList", Template_VMReqList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("vmReqRow", Template_VMReqRow);
    $.template("requestAction", Template_ReFuseVMReq);
}

function setup() {
    $("#mainBody").empty();
    $("#banner").html(Locale["vmreqmgr.banner"]);

    var vmReqList = $.tmpl("vmReqList", [{
        id: "vmReqList"
    }]).appendTo("#mainBody");
    $("#filterHead1 select").bind("change",function(){
    	 loadVMReqList();
    });
    $("#filterHead0 input").keyup(function(e){
        if(e.which==13)
        	 loadVMReqList();
    });
    loadVMReqList();
}

function loadVMReqList() {
	if($('.dataTableFile tbody tr').length > 0){
		vmReqMgrTable.fnClearTable();
	}
  
    var params = {};
    var filter = {};
    var count =0;
    var status = $("#filterHead1").find("select[zmlm\\\:item='status']").val();
    if(status.trim().length > 0 ){
    	params["filters"] = "status"+encodeURI(":")+status;
    	count++;
    }
    var vmname = $("#filterHead0").find("input[zmlm\\\:item='vmname']").val();
    if(vmname.trim().length > 0 ){
    	if(count > 0){
    		params["filters"] = params["filters"] + encodeURI(",") + "vmname"+encodeURI(":")+vmname;
    	}else{
    		params["filters"] = "vmname"+encodeURI(":")+vmname;
    	}
    	
    }
    params["operation"]="fetchvmrequests";
    var container = $("#vmReqList tbody");
    $("<tr style='border:none'><td cospan='14'><em>" + Locale["vmreqmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());
    $.ajax({
        url: Server + vmReqServletURI,
        type: "POST",
        data: params,
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();
                if (data.status == "exception") {
                    printMessage(Locale["vmreqmgr.message.data.retrieve.error"]);
                    return;
                }
                if (!data || data.length == 0) {
                    $("<em>" + Locale["vmreqmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                    $.tmpl("vmReqRow", data).appendTo(container);
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

function updateOldRow(status,oldRow, notes){
	var row = vmReqMgrTable.fnGetPosition($(oldRow).get(0));
	if(status == "approve"){
		vmReqMgrTable.fnUpdate(Locale["vmreqmgr.label.status.approved"], row ,2, 0);
		$(oldRow).find("td").eq(2).removeClass("orange");
		$(oldRow).find("td").eq(2).addClass("green");
	}else if(status=="reject"){
		vmReqMgrTable.fnUpdate(Locale["vmreqmgr.label.status.rejected"], row ,2, 0);
		$(oldRow).find("td").eq(2).removeClass("green");
		$(oldRow).find("td").eq(2).addClass("orange");
	}
	vmReqMgrTable.fnUpdate(notes, row ,5, 0);
	$(oldRow).find("input[zmlm\\\:item='notes']").val(notes);
	$(oldRow).find("input[zmlm\\\:item='status']").val(status);
	vmReqMgrTable.fnUpdate("", row ,13, 0);
}
function action(which,requestaction){
	if($("#requestAction").length >0 ){
		$("#requestAction").remove();
	}
    var requestAction = $.tmpl("requestAction", [{
        id: "requestAction"
    }]).appendTo("#mainBody");
    var row = $(which).parents(".vmReqRow").first();
    var title = "";
    var ope = "";
    if(requestaction == 0){
    	title=Locale["vmreqmgr.dialog.approve"];
    	ope = "approve"
    }
    if(requestaction == 1){
    	title=Locale["vmreqmgr.dialog.reject"];
    	ope = "reject"
    }
    requestAction = $(requestAction).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + title,
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "300px",
        buttons: [{
            text: Locale["vmreqmgr.dialog.confirm"],
            click: function() {
                var notes = $(this).find("input[zmlm\\\:item='notes']").val();
                if (isNull(notes)) {
                    printMessage(Locale["vmreqmgr.message.choose.field"].sprintf(Locale["vmreqmgr.label.reject.notes"]));
                    return;
                }
                var vmrequestid = $(row).find("input[zmlm\\\:item='vmrequestid']").val();
                vmAction(row, vmrequestid, notes, ope);
                $(this).dialog("close");
            }
        },
        {
            text: Locale["vmreqmgr.dialog.cancel"],
            click: function() {
                $(this).dialog("close");
            }
        }]
    });
    $("#requestAction").dialog("open");
}
function vmAction(row, vmrequestid, notes, ope) {
    var loginuser = getUsername();
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + vmReqServletURI,
        cache: false,
        data: {
	    	operation: "processvmrequest",
			vmrequestid: vmrequestid,
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
                    msg = Locale["vmreqmgr.message.approve.done"];
                    updateOldRow(ope, row, notes);
                    break;
                case "notadmin":
                    msg = Locale["vmreqmgr.message.approve.notadmin"];
                    break;  
                case "vmrequestnotexists":
                    msg = Locale["vmreqmgr.message.approve.vmrequestnotexists"];
                    break;
                case "lowvminventory":
                    msg = Locale["vmreqmgr.message.approve.lowvminventory"];
                    break;
                case "error":
                    ;
                case "exception":
                    msg = Locale["vmreqmgr.message.approve.fail"];
                    break;
                default:
                    msg = Locale["vmreqmgr.message.undefined"].sprintf(data.status);
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
            text: Locale["vmreqmgr.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["vmreqmgr.dialog.processing"] + "</div>").dialog({
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
	vmReqMgrTable = $('#vmReqList .dataTableFile').dataTable({
		"fnInitComplete": function(oSettings, json){
		$("#dataTableFile").find("#pager").removeAttr("id").appendTo($("#dataTableFile tfoot").find("td:first-child").empty());
		},
	    "sPaginationType": "full_numbers",
	    "bFilter": false,
	    "bSort": true,
	    "aoColumnDefs": [
	                     { 'bSortable': false, 'aTargets': [13] }
	                  ],
	    "bLengthChange": true,
	    "bDestroy":true,
	    "iDisplayLength":10,
	    "sDom": "<'#dataTableFile't<'#pager'lpi>>",
	    "oLanguage": {
	    "sLengthMenu": Locale["vmreqmgr.page.show"],
	    "sInfoEmtpy": Locale["vmreqmgr.page.nodata"],
	    "sZeroRecords": Locale["vmreqmgr.page.foundnodata"], 
	    "sInfoFiltered": Locale["vmreqmgr.page.foundtotal"],
	    "sProcessing": Locale["vmreqmgr.page.loading"],
	    "sInfoEmpty":Locale["vmreqmgr.page.infoempty"],
	    "sInfo": Locale["vmreqmgr.page.startend"],
	    "oPaginate": {
	        "sFirst":  Locale["vmreqmgr.page.first"],
	        "sPrevious": Locale["vmreqmgr.page.previous"],
	        "sNext": Locale["vmreqmgr.page.next"],
	        "sLast": Locale["vmreqmgr.page.last"]
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
    if (null == value || "" == $.trim(value) || value == "n/a") {
        return true;
    } else {
        return false;
    }
}

function formatSize(bytes) {
    if (null == bytes || 0 == bytes) return "--";

    var i = 0;
    while (1023 < bytes) {
        bytes /= 1024; ++i;
    };
    return i ? bytes.toFixed(2) + ["", " KB", " MB", " GB", " TB"][i] : bytes + " bytes";
}
