//rshao
var vmLogMgrTable;
var vmLogServletURI="/RedDragonEnterprise/EnterpriseManager";
$(function() {
    registerTemplate();

    setup();
    
    initUi();
    
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("vmLogList", Template_VMLogList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("vmLogRow", Template_VMLogRow);
}

function setup() {
    $("#mainBody").empty();
    $("#banner").html(Locale["vmlogmgr.banner"]);

    var vmLogList = $.tmpl("vmLogList", [{
        id: "vmLogList"
    }]).appendTo("#mainBody");
    $("#filterHead1 select").bind("change",function(){
    	loadVMLogList();
      });
    $("#filterHead0 input").keyup(function(e){
	      if(e.which==13)
	    	  loadVMLogList();
	  });
	$("#filterHead4 input").keyup(function(e){
	      if(e.which==13)
	    	  loadVMLogList();
	  });
    loadVMLogList();
}

function loadVMLogList() {
	if($('.dataTableFile tbody tr').length > 0){
		vmLogMgrTable.fnClearTable();
	}
	var params = {};
	var filter = {};
	var count =0;
    var action = $("#filterHead1").find("select[zmlm\\\:item='action']").val();
    if(action.trim().length > 0 ){
    	params["filters"] = "action"+encodeURI(":")+action;
    	count++;
    }
    var login = $("#filterHead0").find("input[zmlm\\\:item='adminlogin']").val();
    if(login.trim().length > 0 ){
    	if(count > 0){
    		params["filters"] = params["filters"] + encodeURI(",") + "login"+encodeURI(":")+login;
    	}else{
    		params["filters"] = "login"+encodeURI(":")+login;
    		count++;
    	}
    }
    
    var userlogin = $("#filterHead4").find("input[zmlm\\\:item='userlogin']").val();
    if(userlogin.trim().length > 0 ){
    	if(count > 0){
    		params["filters"] = params["filters"] + encodeURI(",") + "userlogin"+encodeURI(":")+userlogin;
    	}else{
    		params["filters"] = "userlogin"+encodeURI(":")+userlogin;
    	}
    }
    var starttime = $("#filterHead2").find("input[zmlm\\\:item='starttime']").val();
    var current = new Date().getTime();
    if(starttime.trim().length>0){
    	params["starttime"] = parseInt(Date.parse(starttime));
    }else{
    	$("#filterHead2").find("input[zmlm\\\:item='starttime']").val(new Date(current-604800000).format("yyyy-MM-dd"));
    	starttime = $("#filterHead2").find("input[zmlm\\\:item='starttime']").val();
    	params["starttime"] = parseInt(Date.parse(starttime));
    	
    }
    var endtime = $("#filterHead3").find("input[zmlm\\\:item='endtime']").val();
    if(endtime.trim().length>0){
    	params["endtime"] = parseInt(Date.parse(endtime))+86399999;
    }else{
    	$("#filterHead3").find("input[zmlm\\\:item='endtime']").val(new Date(current).format("yyyy-MM-dd"));
    	endtime = $("#filterHead3").find("input[zmlm\\\:item='endtime']").val();
    	params["endtime"] = parseInt(Date.parse(endtime))+86399999 ;
    	
    }
    if(params["starttime"] >= params["endtime"]){
    	printMessage(Locale["vmlogmgr.label.time.compare"]);
    	return;
    }
    var loginuser = getUsername();
    params["adminlogin"] = loginuser;
    params["operation"]="fetchvmadminactionlog";
    
    var container = $("#vmLogList tbody");
    $("<tr style='border:none'><td cospan='9'><em>" + Locale["vmlogmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());
    
    $.ajax({
        url: Server + vmLogServletURI,
        type: "POST",
        data: params,
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();
                if (data.status == "exception") {
                    printMessage(Locale["vmlogmgr.message.data.retrieve.error"]);
                    return;
                }
                if (!data || data.length == 0) {
                    $("<em>" + Locale["vmlogmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                    $.tmpl("vmLogRow", data).appendTo(container);
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
            text: Locale["vmlogmgr.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["vmlogmgr.dialog.processing"] + "</div>").dialog({
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
	vmLogMgrTable = $('#vmLogList .dataTableFile').dataTable({
		"fnInitComplete": function(oSettings, json){
		$("#dataTableFile").find("#pager").removeAttr("id").appendTo($("#dataTableFile tfoot").find("td:first-child").empty());
		},
	    "sPaginationType": "full_numbers",
	    "bFilter": true,
	    "bSort": true,
	    "bLengthChange": true,
	    "bDestroy":true,
	    "sDom": "<'#dataTableFile't<'#pager'lpi>>",
	    "iDisplayLength":10,
	    "oLanguage": {
	    "sLengthMenu": Locale["vmlogmgr.page.show"],
	    "sInfoEmtpy": Locale["vmlogmgr.page.nodata"],
	    "sZeroRecords": Locale["vmlogmgr.page.foundnodata"], 
	    "sInfoFiltered": Locale["vmlogmgr.page.foundtotal"],
	    "sProcessing": Locale["vmlogmgr.page.loading"],
	    "sInfoEmpty":Locale["vmlogmgr.page.infoempty"],
	    "sInfo": Locale["vmlogmgr.page.startend"],
	    "oPaginate": {
	        "sFirst":  Locale["vmlogmgr.page.first"],
	        "sPrevious": Locale["vmlogmgr.page.previous"],
	        "sNext": Locale["vmlogmgr.page.next"],
	        "sLast": Locale["vmlogmgr.page.last"]
	    }
	}
	});
	    
}

function initUi() {
	$('#starttime').datepicker({
		inline: true,
		showOn: 'both',
		buttonText: Locale["vmlogmgr.label.search.selecttime"],
		dateFormat: 'yy-mm-dd'
		}); 
	$('#endtime').datepicker({
		inline: true,
		showOn: 'both',
		buttonText: Locale["vmlogmgr.label.search.selecttime"],
		dateFormat: 'yy-mm-dd'
		}); 
    $("button").button();
}

function formatDate(longtime) {
    return new Date(longtime).format("yyyy-MM-dd hh:mm:ss");
}

