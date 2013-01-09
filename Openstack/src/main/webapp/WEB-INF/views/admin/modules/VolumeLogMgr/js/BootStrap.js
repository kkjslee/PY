//rshao
var volumeLogMgrTable;
var volumeLogServletURI="/RedDragonEnterprise/EnterpriseManager";
$(function() {
    registerTemplate();

    setup();
    
    initUi();
    
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("volumeLogList", Template_VolumeLogList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("volumeLogRow", Template_VolumeLogRow);
}

function setup() {
    $("#mainBody").empty();
    $("#banner").html(Locale["volumelogmgr.banner"]);

    var volumeLogList = $.tmpl("volumeLogList", [{
        id: "volumeLogList"
    }]).appendTo("#mainBody");
    $("#filterHead1 select").bind("change",function(){
    	loadVolumeLogList();
      });
    $("#filterHead0 input").keyup(function(e){
	      if(e.which==13)
	    	  loadVolumeLogList();
	  });
	$("#filterHead4 input").keyup(function(e){
	      if(e.which==13)
	    	  loadVolumeLogList();
	  });
    loadVolumeLogList();
}

function loadVolumeLogList() {
	if($('.dataTableFile tbody tr').length > 0){
		volumeLogMgrTable.fnClearTable();
	}
	var params = {};
	var filter = {};
	var count =0;
    var action = $("#filterHead1").find("select[zmlm\\\:item='action']").val();
    if(action.trim().length > 0 ){
    	params["filters"] = "action"+encodeURI(":")+action;
    	count++;
    }
    var userlogin = $("#filterHead0").find("input[zmlm\\\:item='userlogin']").val();
    if(userlogin.trim().length > 0 ){
    	if(count > 0){
    		params["filters"] = params["filters"] + encodeURI(",") + "userlogin"+encodeURI(":")+userlogin;
    	}else{
    		params["filters"] = "userlogin"+encodeURI(":")+userlogin;
    		count++;
    	}
    }
    var adminlogin = $("#filterHead4").find("input[zmlm\\\:item='adminlogin']").val();
    if(adminlogin.trim().length > 0 ){
    	if(count > 0){
    		params["filters"] = params["filters"] + encodeURI(",") + "login:"+encodeURI(":")+adminlogin;
    	}else{
    		params["filters"] = "login"+encodeURI(":")+adminlogin;
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
    	printMessage(Locale["volumelogmgr.label.time.compare"]);
    	return;
    }
    var loginuser = getUsername();
    params["adminlogin"] = loginuser;
    params["operation"]="fetchvolumeadminactionlog";
    
    var container = $("#volumeLogList tbody");
    $("<tr style='border:none'><td cospan='9'><em>" + Locale["volumelogmgr.message.loading"] + "</em></td></tr>").appendTo(container.empty());
    
    $.ajax({
        url: Server + volumeLogServletURI,
        type: "POST",
        data: params,
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();
                if (data.status == "exception") {
                    printMessage(Locale["volumelogmgr.message.data.retrieve.error"]);
                    return;
                }
                if (!data || data.length == 0) {
                    $("<em>" + Locale["volumelogmgr.message.no_data"] + "</em>").appendTo(container);
                } else {
                   $.tmpl("volumeLogRow", data).appendTo(container);
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
            text: Locale["volumelogmgr.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["volumelogmgr.dialog.processing"] + "</div>").dialog({
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
	volumeLogMgrTable = $('#volumeLogList .dataTableFile').dataTable({
		"fnInitComplete": function(oSettings, json){
		$("#dataTableFile").find("#pager").removeAttr("id").appendTo($("#dataTableFile tfoot").find("td:first-child").empty());
		},
	    "sPaginationType": "full_numbers",
	    "bFilter": true,
	    "bSort": true,
	    "bLengthChange": true,
	    "sDom": "<'#dataTableFile't<'#pager'lpi>>",
	    "bDestroy":true,
	    "iDisplayLength":10,
	    "oLanguage": {
	    "sLengthMenu": Locale["volumelogmgr.page.show"],
	    "sInfoEmtpy": Locale["volumelogmgr.page.nodata"],
	    "sZeroRecords": Locale["volumelogmgr.page.foundnodata"], 
	    "sInfoFiltered": Locale["volumelogmgr.page.foundtotal"],
	    "sProcessing": Locale["volumelogmgr.page.loading"],
	    "sInfoEmpty":Locale["volumelogmgr.page.infoempty"],
	    "sInfo": Locale["volumelogmgr.page.startend"],
	    "oPaginate": {
	        "sFirst":  Locale["volumelogmgr.page.first"],
	        "sPrevious": Locale["volumelogmgr.page.previous"],
	        "sNext": Locale["volumelogmgr.page.next"],
	        "sLast": Locale["volumelogmgr.page.last"]
	    }
	}
	});
	    
}

function initUi() {
	$('#starttime').datepicker({
		inline: true,
		showOn: 'both',
		buttonText: Locale["volumelogmgr.label.search.selecttime"],
		dateFormat: 'yy-mm-dd'
		}); 
	$('#endtime').datepicker({
		inline: true,
		showOn: 'both',
		buttonText: Locale["volumelogmgr.label.search.selecttime"],
		dateFormat: 'yy-mm-dd'
		}); 
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

function checkNum(num) {
    var re = /^[1-9]\d*$/;
    if (!re.test(num)) {
        return false;
    } else return true;
}
