<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

$(function() {
    
     $.template("messageBoxTemplate", Template_MessageBox);
         
});

function showProcessingDialog() {
    var view=$("<div style='text-align:center;'><img src='<%=request.getContextPath()%>/resource/common/image/progress.gif'/>"+"<spring:message code='dialog.processing'/>"+"</div>").dialog({
        autoOpen: true,
        width: 240,
        height: 100,
        resizable: false,
        modal: true,
        closeOnEscape: false,
        open: function(event, ui) {  $(this).parents(".ui-dialog").first().find(".ui-dialog-titlebar-close").hide(); },
        buttons: {
        }
    });
    return view;
}

function printError(jqXHR, textStatus, errorThrown) {
    printMessage("Connection Broken: "+textStatus+", "+errorThrown);
}
function printWarn(msg){
    printMessage(msg);
}
function printMessage(msg) {
    return $.tmpl("messageBoxTemplate", [{message: msg}]).appendTo("#mainBody").dialog({
        resizable: false,
        modal: true,
        buttons: [
            {
                text: "<spring:message code='admin.vm.dialog.close'/>",
                click: function() {
                    $(this).dialog("destroy");
                }
            }
        ]
    });
}

function getCategoryItemDetailsById(url,data,container,osTypeId,callBack){
    window.console.log("getting flavor details");
     $.ajax({
        type: "POST",
        url: url,
        cache: false,
        dataType:"json",
        data: data,
        success: function(data) {
            try{
                if(!data || data.lengh == 0){
                    return;
                }else{
                     callBack(container,osTypeId,data);
                }
                
            }catch(e) {
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }
    });
}


function getFlavorDetailsById(id,container,osTypeId,callBack){
    window.console.log("getting flavor details");
     $.ajax({
        type: "POST",
        url: "<%=request.getContextPath()%>/admin/flavor/getFlavorDetails",
        cache: false,
         dataType:"json",
        data: {
            flavorId: id
        },
        success: function(data) {
            try{
                if(!data || data.lengh == 0){
                    return;
                }else{
                     callBack(container,osTypeId,data);
                }
                
            }catch(e) {
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }
    });
}


function getImageDetailsById(id,container,osTypeId,callBack){
 window.console.log("getting image details");
     $.ajax({
        type: "POST",
        url: "<%=request.getContextPath()%>/admin/image/retrieveImage",
        cache: false,
        dataType:"json",
        data: {
            imgId: id
        },
        success: function(data) {
            try{
                if(!data || data.lengh == 0){
                }else{
                    callBack(container,osTypeId,data);
                }
                
            }catch(e) {
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }
    });
}

function loadCategories(selectId,categories_id){
    $.ajax({
        type: "POST",
        dataType: "json",
        cache: false,
        url: "<%=request.getContextPath()%>/admin/category" + "/listForJson",  
        data: {
            excludeDisabled:true,
            withItems:true
        },
        success: function(data) {
             try{
               $("#" + selectId).empty();
               if (!data || data.length == 0) {
                   
               }else{
                    $("#"+selectId).empty();
                     $.tmpl("createCategoryOption", data).appendTo("#" + selectId);
                     if(!isNull(categories_id)){
                        $("#" + selectId).val(categories_id);
                     }
                     $("#" + selectId).multiselect({
                           noneSelectedText:"<spring:message code="category.multi.seltext"/>",
						   selectedText: "<spring:message code="multi.seltext"/>",
						   checkAllText:"<spring:message code="multi.all"/>",
						   uncheckAllText:"<spring:message code="multi.none"/>",
						});
               }
            }catch(e){printMessage("Data Broken ["+e+"]");};
        },
        error: function(jqXHR, textStatus, errorThrown) {
        printError(jqXHR, textStatus, errorThrown);
            return false;
        }
    });
    
}