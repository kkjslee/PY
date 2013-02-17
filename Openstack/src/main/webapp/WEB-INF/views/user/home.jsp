<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>用户云平台</title>
<jsp:include page="../bootstrapLinks.jsp" />
<jsp:include page="userlinks.jsp" />
<link href="<%=request.getContextPath()%>/resource/normaluser/common/css/navigator.css" rel="Stylesheet" type="text/css"  />
<script src="<%=request.getContextPath()%>/user/scripts/navinit" type="text/javascript"></script>
</head>
<body class="mainBody">
<table>
<tr>
    
<td class="left" valign="top">
    <div class="logo"><img src="${pageContext.request.contextPath}/resource/common/image/logo.png"/></div>
    <div class="menu">
        <ul>
        </ul>
    </div>
</td>

<td style="width:100%" valign="top">
<div class="prepare"><spring:message code="user.prepare.module"/></div>
<div class="right">
</div>
</td>

</tr>
</table>

</body>
</html>
