<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
//this is for user dashboard
$(function(){
    initUI();
});


function initUI() {
    
    // set up menu
    var menu=$("body").find(".menu");
    $(".accordion-heading a").hover(
		function () {
		$(this).animate({paddingLeft:"15px"}, {queue:false});
		$(this).css({"color":"#FFF"});
		},
		function () {
		$(this).animate({paddingLeft:"10px"}, {queue:false});
		$(this).css({"color":"#BBB"});
		}
	); 

    $("a[name='menuItem']").each(function(){
        $(this).bind("click", function(){
           $(".menu").find(".accordion-inner li").removeClass("active");
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

