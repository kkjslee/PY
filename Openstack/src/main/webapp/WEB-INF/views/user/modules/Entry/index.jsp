<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <div style="font-family:'微软雅黑'; font-weight:bold; font-size:18px; color:#e38967; padding:50px 0 10px 40px; border-bottom:1px solid #ddd;"><spring:message code="user.entry.title"/>,当前余额: ${balance}</div>
    <div style="padding:30px 0 10px 60px;">
        <div style="font-family:'微软雅黑';font-size:14px;">${content}</div>
    </div>
    <div>
        <h4>快捷入口</h4>
        <a href="#" onclick="redirectLink('cart/modules/index');">获取云主机</a>&nbsp;&nbsp;<a href="#" onclick="redirectLink('instance/modules/index');">我的云主机管理</a>  
    </div>
    <script>
    function redirectLink(module){
        $(".accordion-body" ).each(function(e){
            $(this).removeClass("in");
            $(this).css("height","0");
        });
        $("#sidebar_menu").find("li").each(function(){
            if($(this).find("a").attr("isos:module") == module){
                $(this).find("a").trigger("click");
                $(this).parents("li:eq(0)").addClass("active");
                var abody = $(this).parents(".accordion-body:eq(0)");
                 $(abody).addClass("in");
                 $(abody).css("height","auto");
                return;
            }
        });
    }
    </script>
</body>
</html>