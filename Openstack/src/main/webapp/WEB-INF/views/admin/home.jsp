<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统云平台管理</title>
<jsp:include page="../commonlinks.jsp" />
<jsp:include page="adminlinks.jsp" /> 
<link href="<%=request.getContextPath()%>/resource/admin/common/css/navigator.css" rel="Stylesheet" type="text/css"  />
<script src="<%=request.getContextPath()%>/admin/scripts/navinit" type="text/javascript"></script>
</head>
<body class="mainBody">
<table>
<tr>
	
<td class="left" valign="top">
	<div class="logo"><img src="${pageContext.request.contextPath}/resource/common/image/logo.png"/></div>
      <div class="menu tabbable tabs-left">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#" name="menuItem" isos:module="modules/entry/index" data-toggle="tab"><spring:message code='admin.navigation.menu.admin.index'/></a></li>
            <li><a href="#" name="menuItem" isos:module="instance/modules/index" data-toggle="tab"><spring:message code='admin.navi.instance' /></a></li>
            <li><a href="#" name="menuItem" isos:module="flavor/modules/index" data-toggle="tab"><spring:message code='admin.navi.flavor' /></a></li>
            <li><a href="#" name="menuItem" isos:module="image/modules/index" data-toggle="tab"><spring:message code='admin.navi.image' /></a></li>
            <li><a href="#" name="menuItem" isos:module="category/modules/index" data-toggle="tab"><spring:message code='admin.navi.category' /></a></li>
            <li><a href="#" name="menuItem" isos:module="product/modules/index" data-toggle="tab"><spring:message code='admin.navi.product' /></a></li>
            <li><a href="<c:url value='/admin/doLogout'/>"><spring:message code='admin.navigation.menu.admin.signout' /></a></li>
        </ul>
    </div>
</td>

<td style="width:100%" valign="top">
<div class="right">
    <div style="font-family:'微软雅黑'; font-weight:bold; font-size:18px; color:#e38967; padding:50px 0 10px 40px; border-bottom:1px solid #ddd;"><spring:message code="admin.entry.title"/></div>
    <div style="padding:30px 0 10px 60px;">
        <div style="font-family:'微软雅黑';font-size:14px;"><spring:message code="admin.entry.welcome"/></script></div>
    </div>
</div>
</td>

</tr>
</table>

</body>
</html>
