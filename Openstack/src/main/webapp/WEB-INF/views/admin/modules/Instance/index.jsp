<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Instance</title>
<jsp:include page="../../../commonlinks.jsp" />
<jsp:include page="../../adminlinks.jsp" /> 

<script src="<%=request.getContextPath()%>/resource/admin/instance/scripts/exporting.js" language="javascript"></script>
<script src="<%=request.getContextPath()%>/admin/instance/scripts/template" language="javascript"></script>
<script src="<%=request.getContextPath()%>/admin/instance/scripts/bootstrap" language="javascript"></script>
<link href="<%=request.getContextPath()%>/resource/admin/instance/css/main.css" rel="stylesheet" type="text/css" />

</head>
<body>

<div style=" padding:40px 0 0 0;">
	<label style="text-align:left;display:block;line-height:24px;background:url(<%=request.getContextPath()%>/resource/admin/instance/css/image/bg2.png)">
		<span id="banner" style="position: relative;color:#222;font-size:20px;font-family:'微软雅黑';padding:5px; background-color:#fff; font-weight:bold; margin-left:100px;"></span>
	</label>
</div>

<div id="mainBody" style="margin:30px 20px 0 20px;">
	<img src="<%=request.getContextPath()%>/resource/common/image/progress.gif"/>
	Loading...
</div>
</body>
</html>
