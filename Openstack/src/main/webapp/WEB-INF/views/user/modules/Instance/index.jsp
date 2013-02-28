<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/resource/normaluser/instance/css/main.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/user/instance/scripts/bootstrap" language="javascript"></script>
</head>
<body>
<div class="banner">
	<label>
		<span id="banner" ></span>
	</label>
</div>

<div id="mainBody" >
	<table class="dataTable imList">
        <thead>
            <tr class="headerRow">
                <th class="vmName"><spring:message code="admin.vm.name" /> </th>
                <th class="vmStatus"><spring:message code="admin.vm.status" /> </th>
                <th class="vmStatus"><spring:message code="user.vm.flavor" /> </th>
                <th class="vmStatus"><spring:message code="admin.vm.ostype" /> </th>
                <th class="vmStatus"><spring:message code="user.vm.period" /> </th>
                <th class="vmStatus"><spring:message code="user.vm.create_time" /> </th>
                <th class="vmOperation"> </th>
            </tr>
        </thead>
        <tbody>
        </tbody>
        <tfoot>
            <tr class="footerRow">
            <td colspan="6" class="pagination"></td>
            <td align="left"></td>
            </tr>
        </tfoot>
    </table>
</div>
<div id="instanceDetails"></div>
</body>
</html>
