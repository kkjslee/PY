<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

$(function() {
    
     $.template("messageBoxTemplate", Template_MessageBox);
       $.template("confirmBoxTemplate", Template_ConfirmBox);
         
});

function showProcessingDialog() {
    var view=$("<div title='<spring:message code='dialogue.process.title'/>' style='text-align:center;'><img src='<%=request.getContextPath()%>/resource/common/image/progress.gif'/>"+"<span class='process_message'><spring:message code='admin.vm.dialog.processing'/></span>"+"</div>").dialog({
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
                text: "<spring:message code='dialog.close'/>",
                click: function() {
                    $(this).dialog("destroy");
                }
            }
        ]
    });
}


function jConfirm(msg, callback,call2){
   return $.tmpl("confirmBoxTemplate", [{
        message: msg
    }]).appendTo("#mainBody").dialog({
        modal: true,
        resizable: false,
        buttons: [
        {
            text: "<s:text name='confirm.button'/>",
            click: function() {
             $(this).dialog("destroy");
            callback.call();
            }
        },
        {
            text: "<s:text name='cancel'/>",
            click: function() {
               if(typeof(call2)!="undefined"){
                call2.call();
               }else{
               $(this).dialog("destroy");
               }
                
            }
        }]
    });
}
