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

