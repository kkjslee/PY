<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
<div class="banner">
    <label>
        <span id="banner" ></span>
    </label> 
</div>

<div id="mainBody" >
    <h4><spring:message code="volume.list.label"/></h4>
    <c:url value='/user/cinder/getPagerVolumeList' var="paginationUrl"/>
    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
    <c:set target="${pageMap}" property=".content" value="dataTable"/>
    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
    <c:set target="${pageMap}" property=".pageSize" value="10"/>
    <c:set target="${pageMap}" property=".pagination" value="pagination"/>
    <c:set target="${pageMap}" property=".colspanLeft" value="4"/>
    <c:set target="${pageMap}" property=".colspanRight" value="3"/>
    <c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
        <jsp:param name="pagination.configuration" value="pageMap"/>
    </jsp:include>
    <br/><br/>
     <h4><spring:message code="volumetype.list.label"/></h4>
    <c:url value='/user/cinder/getPagerVolumeTypeList' var="paginationUrl"/>
    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
    <c:set target="${pageMap}" property=".content" value="dataTable2"/>
    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
    <c:set target="${pageMap}" property=".pageSize" value="10"/>
    <c:set target="${pageMap}" property=".pagination" value="pagination2"/>
    <c:set target="${pageMap}" property=".colspanLeft" value="4"/>
    <c:set target="${pageMap}" property=".colspanRight" value="3"/>
    <c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
        <jsp:param name="pagination.configuration" value="pageMap"/>
    </jsp:include>
</div>
</body>
</html>