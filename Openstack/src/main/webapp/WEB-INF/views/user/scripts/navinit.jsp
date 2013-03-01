<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
//this is for user dashboard
$(function(){
    initUI();
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
    /*$(".right").load("${pageContext.request.contextPath}/user/"+modulePath,function(response,status,xhr){
        $(pd).dialog("close");
        if(status == "success"){
        
        }else{
        alert("error");
        }
    })*/
    
    $.ajax({
        type: "get",
        url: "${pageContext.request.contextPath}/user/"+modulePath,
        cache: false,
        dataType:"html",
        success: function(data) {
         $(pd).dialog("close");
            try{
                $(".right").html(data);
            }catch(e) {
                alert(e);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
         $(pd).dialog("close");
            window.console.log(jqXHR.status);
            alert("<spring:message code="module.load.error"/>");
        },
        complete:function(){
        }
    });
}

