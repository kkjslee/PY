<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Server="<%=request.getContextPath()%>/admin/flavor";
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
   $.template("editFlavor", Template_EditFlavor);
}

function setup() {
    window.document.title = '<spring:message code="admin.flavor.title"/>';
    loadFlavors(pageIndex, pageSize);
}
function loadFlavors(pageIndex, pageSize) {
    var tableBodyContainer=$(".dataTable").find("tbody").empty();
    $("<span class='loadingTips'>"+"<spring:message code='message.loading.data'/>"+"</span>").appendTo(tableBodyContainer);
    $.ajax({
        type: "POST",
        dataType: "html",
        cache: false,
        url: Server + "/getPagerFlavorList",  
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("<span class='loadingError'>"+"<spring:message code='message.loading.data.error'/>"+"</span>").appendTo($(tableBodyContainer).empty());
        }
    });
    
}

function pageCallback(index,jq){
    pageIndex = index;
    loadFlavors(index, pageSize);
}

function showEditFlavor(which){
    //remove old elements
    if($("#editFlavor").length > 0){
        $("#editFlavor").remove();
    }
    var row = $(which).parents(".dataRow").first();
    var flavorId = $(row).find("input[isos='flavorId']").val();
    var vcpus =  $(row).find("input[isos='vcpus']").val();
    var flavorName =  $(row).find("input[isos='flavorName']").val();
    var ram =  $(row).find("input[isos='ram']").val();
    var disk =  $(row).find("input[isos='disk']").val();
    var editFlavor = $.tmpl("editFlavor", [{
        id: "editFlavor"
    }]).appendTo("#mainBody");

    editFlavor = $(editFlavor).dialog({
        title: '<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span><spring:message code="flavor.edit.title"/>',
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: '<spring:message code="confirm.button"/>',
            click: function() {
               var flavorName = $(this).find("input[isos='vmname']").val();
               var ram = $(this).find("input[isos='ram']").val();
               var disk = $(this).find("input[isos='disk']").val();
               var vcpus = $(this).find("input[isos='vcpus']").val();
               
                if(!jQuery.checkstr(flavorName,"vmname")) {
                   printMessage("<spring:message code='vmname.check'/>");
                   return false;
                }
                if(!jQuery.checkstr(ram, "positive_number")){
                   printMessage("<spring:message code='positive.required'/>".sprintf('<spring:message code="admin.flavor.ram"/>'));
                   return false;
                }
                if(!jQuery.checkstr(disk, "positive_number")){
                    printMessage("<spring:message code='positive.required'/>".sprintf('<spring:message code="admin.flavor.rdisk"/>'));
                    return false;
                 }
                if(!jQuery.checkstr(vcpus, "positive_number")){
                    printMessage("<spring:message code='positive.required'/>".sprintf('<spring:message code="admin.flavor.vcpus"/>'));
                    return false;
                 }

               updateFlavorItem(flavorId, flavorName, ram, disk, vcpus);
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
    //set former data
    $(editFlavor).find("input[isos='flavorName']").val(flavorName);
    $(editFlavor).find("input[isos='vcpus']").val(vcpus);
    $(editFlavor).find("input[isos='ram']").val(ram);
    $(editFlavor).find("input[isos='disk']").val(disk);
    $(editFlavor).dialog("open");
   }

function showCreatFlavor(){
	if($("#addFlavor").length > 0){
        $("#addFlavor").remove();
    }
    var addFlavor = $.tmpl("editFlavor", [{
        id: "addFlavor"
    }]).appendTo("#mainBody");
	addFlavor = $(addFlavor).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span><spring:message code='flavor.add.title'/>",
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: '<spring:message code="confirm.button"/>',
            click: function() {
               var flavorName = $(this).find("input[isos='flavorName']").val();
               var ram = $(this).find("input[isos='ram']").val();
               var disk = $(this).find("input[isos='disk']").val();
               var vcpus = $(this).find("input[isos='vcpus']").val();
               
                if(!jQuery.checkstr(flavorName,"vmname")) {
                   printMessage("<spring:message code='vmname.check'/>");
                   return false;
                }
                if(!jQuery.checkstr(ram, "positive_number")){
                   printMessage("<spring:message code='positive.required'/>".sprintf('<spring:message code="admin.flavor.ram"/>'));
                   return false;
                }
                if(!jQuery.checkstr(disk, "positive_number")){
                    printMessage("<spring:message code='positive.required'/>".sprintf('<spring:message code="admin.flavor.rdisk"/>'));
                    return false;
                 }
                if(!jQuery.checkstr(vcpus, "positive_number")){
                    printMessage("<spring:message code='positive.required'/>".sprintf('<spring:message code="admin.flavor.vcpus"/>'));
                    return false;
                 }

               createFlavorItem(flavorName, ram, disk, vcpus);
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
	$(addFlavor).dialog("open");
}

function removeFlavor(which) {
    var flavorId=$(which).parents(".dataRow").first().find("input[isos='flavorId']").val();
    var flavorName = $(which).parents(".dataRow").first().find("input[isos='flavorName']").val();
    if(!confirm("<spring:message code='flavor.remove.confirm'/>".sprintf(flavorName))) return;
    
    var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server+"/removeFlavor",
        cache: false,
        data: {
        	flavorId: flavorId
        },
        success: function(data) {
            pd.dialog("destroy");
            try{
                var msg="";
                switch(data.status) {
                    case "done": msg="<spring:message code='remove.success'/>";
                    break;
                    case "error": ;
                    case "exception": msg="<spring:message code='remove.failed'/>";break;
                }
                
                printMessage(msg);
                loadFlavors(pageIndex, pageSize);
                
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
function createFlavorItem(flavorName, ram, disk, vcpus){

    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/createFlavor",
        type: "POST",
        dataType:"json",
        data: {
            flavorName:flavorName,
            ram:ram,
            disk:disk,
            vcpus:vcpus
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {

                if(data.success){
                    printMessage('<spring:message code="create.success"/>');
                    loadFlavors(pageIndex, pageSize);
                }
                if(data.error){
                    printMessage('<spring:message code="create.failed"/>');
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

function updateFlavorItem(flavorId, flavorName, ram, disk, vcpus) {
 
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/updateFlavor",
        type: "POST",
        dataType:"json",
        data: {
        	flavorId:flavorId,
        	flavorName:flavorName,
        	ram:ram,
        	disk:disk,
        	vcpus:vcpus
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {

                if(data.success){
                    printMessage('<spring:message code="update.success"/>');
                    loadFlavors(pageIndex, pageSize);
                }
                if(data.error){
                    printMessage('<spring:message code="update.failed"/>');
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


function initUI(){
     $( ".button").button();
}