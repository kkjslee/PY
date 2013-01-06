<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">  
<title><spring:message code="user.entry.choose"/></title>
</head>
<body>
    <center><br/><br/>
       <p><spring:message code="user.entry.choose"/>:</p>
        <p><a href="${pageContext.request.contextPath}/admin/login"><spring:message code="entry.admin"/></a></p>
        <p><a href="${pageContext.request.contextPath}/agent/login"><spring:message code="user.agent.lable"/></a></p>
        <p><a href="${pageContext.request.contextPath}/user/1/login"><spring:message code="entry.agent.normaluser"/></a></p>
        <p><a href="${pageContext.request.contextPath}/user/login"><spring:message code="entry.normaluser"/></a></p>
    </center>
</body>
</html>
