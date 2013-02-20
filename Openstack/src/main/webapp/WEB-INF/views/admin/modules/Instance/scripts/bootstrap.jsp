<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Server="<%=request.getContextPath()%>/admin/instance";
//init pager data from boostrap controller
var pageIndex = ${requestScope.pageIndex};
var pageSize = ${requestScope.pageSize};
var pageTotal = 0;
$(function(){
	//load html template if necessary
	 registerTemplate();
	
    setup();

    initUI();
});

function registerTemplate() {
   $.template("createVM", Template_CreateVM);
   $.template("createImgOption",  Template_ImgModelOption);
   $.template("createFlavorOption",  Template_FlavorModelOption);
}

function setup() {
    window.document.title = '<spring:message code="admin.instance.title"/>';
    loadInstances(pageIndex, pageSize);
}
//server返回的数据html,应该为tbody中的内容，父层结构：<div id="mainBody"><table class="dataTable"><thead></thead><tbody>json返回的内容<tbody><tfoot></tfoot></table></div>
function loadInstances(pageIndex, pageSize) {
    var tableBodyContainer=$(".dataTable").find("tbody").empty();
    $("<span class='loadingTips'>"+"<spring:message code='message.loading.data'/>"+"</span>").appendTo(tableBodyContainer);
    $.ajax({
        type: "POST",
        dataType: "html",
        cache: false,
        url: Server + "/getPagerInstanceList",  
        data: {
            pageIndex: pageIndex,
            pageSize: pageSize
        },
        success: function(data) {
             $(tableBodyContainer).html(data);
             var pageTotal = $("#pageTotal").val();
             $(".pagination").pagination(pageTotal, {
                callback: pageCallback,
                prev_text: '<spring:message code="pager.previous"/>',    
                next_text: '<spring:message code="pager.next"/>', 
                items_per_page: pageSize,
                num_display_entries: 6,
                //count from 0
                current_page: pageIndex,
                num_edge_entries: 2
            });
             checkPageVMStatus();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("<span class='loadingError'>"+"<spring:message code='message.loading.data.error'/>"+"</span>").appendTo($(tableBodyContainer).empty());
        }
    });
    
}

function pageCallback(index,jq){
    pageIndex = index;
	loadInstances(index, pageSize);
}

function ctlInstance(which, command, opeDesc) {
    var vmid=$(which).parents(".dataRow").first().find("input[isos='vmId']").val();
    var vmname=$(which).parents(".dataRow").first().find("input[isos='vmName']").val();
    
    if(!confirm("<spring:message code='operation.confirm'/>".sprintf(opeDesc, vmname))) return;
    
    var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server+"/imcontrol",
        cache: false,
        data: {
            executecommand: command,
            vmid: vmid
        },
        success: function(data) {
            pd.dialog("destroy");
            try{
                var msg="";
                switch(data.status) {
                    case "done": msg="<spring:message code='operation.request.submited'/>".sprintf(opeDesc);
                    break;
                    case "error": ;
                    case "exception": msg="<spring:message code='operation.request.error'/>";break;
                }
                
                printMessage(msg);
                loadInstances(pageIndex, pageSize)
                
            }catch(e) {
                printMessage("Data Broken: ["+e+"]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });
    
}
function showCreatVM(){
    //remove old elements
    if($("#createVM").length > 0){
        $("#createVM").remove();
    }
    var createVM = $.tmpl("createVM", [{
        id: "createVM"
    }]).appendTo("#mainBody");

    createVM = $(createVM).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span><spring:message code="vm.create.title"/>",
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: '<spring:message code="confirm.button"/>',
            click: function() {
                var vmname = $(this).find("input[isos='vmname']").val();
                var selImageModel = $(this).find("select[isos='selImageModel']").val();
                var selFlavorModel = $(this).find("select[isos='selFlavorModel']").val();
                
                 if(!jQuery.checkstr(vmname,"vmname")) {
                     printMessage("<spring:message code='vmname.check'/>");
                    return false;
                 }
  
                if (!checkValidImageField(selImageModel, selFlavorModel)) {
                    printMessage('<spring:message code="choosen.invalid"/>');
                    return;
                }

                createVMItem(vmname, selImageModel, selFlavorModel);
                $(this).dialog("destroy");
            }
        },
        {
            text: '<spring:message code="cancel.button"/>',
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
    dataInit("<%=request.getContextPath()%>/admin" + "/image/imgList","selImageModel","createImgOption");
    dataInit("<%=request.getContextPath()%>/admin" + "/flavor/flavorList","selFlavorModel","createFlavorOption");
    $(createVM).dialog("open");
   }


//now get images and flavors if necessary
function dataInit(url,selectId,optionModel){
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        dataType:"json",
        success: function(data) {
            try{
               $("#" + selectId).empty();
               $("#" + selectId).append('<option value="-1" selected><spring:message code="choose.label"/></option>');
               if (!data || data.length == 0) {
            	   
               }else{
            	    $.tmpl(optionModel, data).appendTo("#" + selectId);
            	    $("#" + selectId).selectmenu();
            	    if(selectId == "selFlavorModel"){
            	       bindFlavorSelect();
            	    }
               }
                
            }catch(e){printMessage("Data Broken ["+e+"]");};
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}
var rTask ;
function checkPageVMStatus(){
	rTask  = setInterval(refreshTaskStatus,2500);

}
function refreshTaskStatus(){
     var hasTask = false;
     $(".dataTable").find(".statusVice:visible").each(function(){
         hasTask = true;
         var row =  $(this).parents(".dataRow").first();
         var vmId = $(row).find("input[isos='vmId']").val();
         getTaskStatus(row, vmId);
     });
     if(!hasTask && !isNull(rTask)){
         clearInterval(rTask);     
     }
 }

function getTaskStatus(row,id){
	$.ajax({
        type: "POST",
        url: Server+"/getInstance",
        cache: false,
        async:false,
        data:{
        	vmId: id
        },
        dataType:"json",
        success: function(data) {
            try{
            	  if (!data || data.length == 0) {
                      
                  }else{
                       if(!isNull(data.addresses)){
                       var addresses = data.addresses;
                       window.console.log(addresses);
                       var ipText = "[";
                       for(var key in addresses){
                        ipText = ipText + key + ":" + addresses[key] + "]<br/>";
                       }
                        $(row).find(".pipvice").html(ipText);
                       }
                                         
                	  if(!isNull(data.isProcessing) && data.isProcessing == true){
                        $(row).find(".statusVice").text(data.taskStatus);
                       }else{
                           $(row).find(".statusTitle").text(data.statusdisplay);
                    	   $(row).find(".statusVice").remove();
                    	   updateButtonWidthStatus(row,data.status);
                       }
                  }                
            }catch(e){printMessage("Data Broken ["+e+"]");};
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}
function updateButtonWidthStatus(row,status){

    if(!isNull(status)){
     $(row).find(".ope").hide();
     $(row).find(".vm"+status).show();
     if(status =="active"){
        $(row).find(".vmunpause").show();
        $(row).find(".vmresuming").show();
     }
     if(status == "deleted"){
        $(row).fadeOut("slow");
     }
    }
   
}
function bindFlavorSelect(){
	$("#selFlavorModel").change(function(){
		getFlavorDetails($(this).val());
	});
}
function getFlavorDetails(flavorId){
   if(isNull(flavorId) || flavorId == "-1"){
	   return;
   }
  var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
        url: "<%=request.getContextPath()%>/admin/flavor/getFlavorDetails",
        cache: false,
        data:{
            flavorId: flavorId
        },
        dataType:"json",
        success: function(data) {
            pd.dialog("destroy");
            try{
            	if (!data || data.length == 0) {
            		$(".flavorDetails").hide();
            	}else{
            	    $(".flavorDetails").show();
            	    $(".flavorDetails").find("span[isos='flavorName']").text(data.flavorName);
            	    $(".flavorDetails").find("span[isos='vcpus']").text(data.vcpus);
            	    $(".flavorDetails").find("span[isos='ram']").text(data.ram);
            	    $(".flavorDetails").find("span[isos='disk']").text(data.disk);
            	}
            }catch(e){printMessage("Data Broken ["+e+"]");};
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });


}


function createVMItem(vmname, selImageModel, selFlavorModel) {
 
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/createInstance",
        type: "POST",
        dataType:"json",
        data: {
            vmname:vmname,
            imageId:selImageModel,
            flavorId:selFlavorModel
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {

                if(data.success){
                    printMessage('<spring:message code="vm.new.request.success"/>');
                    loadInstances(pageIndex, pageSize);
                }
                if(data.error){
                    printMessage(data.error);
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("close");
            printError(jqXHR, textStatus, errorThrown);
            return false;
        }
    });
}

function checkValidImageField(selImageModel, selFlavorModel) {
     if (selImageModel == "-1" || selFlavorModel =="-1") {
        return false;
    } else {
        return true;
    }
}

function initUI(){
	 $( ".button").button();
}