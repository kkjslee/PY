<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
//this is for admin dashboard
$(function(){
    initUI();
     fixSize();
});


function initUI() {
    
    // set up menu
    var menu=$("body").find(".menu");

    $("a[name='menuItem']").each(function(){
        $(this).bind("click", function(){
           $(".menu").find("li").removeClass("active");
            $(this).parent().addClass("active");
            var modulePath=$(this).attr("isos:module");
            loadModule(modulePath);
      
        });
    });
}




function loadModule(modulePath) {
   var pd = showProcessingDialog();
   $(".right").empty()
    $.ajax({
        type: "get",
        url: "${pageContext.request.contextPath}/admin/"+modulePath,
        cache: false,
        dataType:"html",
        success: function(data) {
             $(pd).dialog("close");
            try{
              fixSize();
                $(".right").html(data);
            }catch(e) {
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
             $(pd).dialog("close");
              fixSize();
            window.console.log(jqXHR.status);
            alert("<spring:message code="module.load.error"/>");
        },
        complete:function(){
           
        }
    });
}

