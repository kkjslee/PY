<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/resource/normaluser/instance/css/main.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/user/instance/scripts/template" language="javascript"></script>
<script src="<%=request.getContextPath()%>/user/instance/scripts/bootstrap" language="javascript"></script>
</head>
<body>
<div class="banner">
    <span id="banner" ><spring:message code='user.navi.instance' /></span>
</div>

<div id="mainBody" >
    <c:url value='/user/instance/getPagerInstanceStatusList' var="paginationUrl"/>
    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
    <c:set target="${pageMap}" property=".content" value="dataTable"/>
    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
    <c:set target="${pageMap}" property=".pageSize" value="20"/>
    <c:set target="${pageMap}" property=".pagination" value="pagination"/>
    <c:set target="${pageMap}" property=".colspanLeft" value="7"/>
    <c:set target="${pageMap}" property=".colspanRight" value="3"/>
    <c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
        <jsp:param name="pagination.configuration" value="pageMap"/>
    </jsp:include>
    <div id="showPayForm"></div>
</body>
</html>
