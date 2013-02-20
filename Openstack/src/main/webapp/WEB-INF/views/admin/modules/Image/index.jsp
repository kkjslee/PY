<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/resource/admin/image/css/main.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/admin/image/scripts/template" language="javascript"></script>
<script src="<%=request.getContextPath()%>/admin/image/scripts/bootstrap" language="javascript"></script>
</head>
<body>
<div class="banner">
    <label>
        <span id="banner" ></span>
    </label>
</div>

<div id="mainBody" style="margin:30px 20px 0 20px;">
    <table class="dataTable imList">
        <thead>
            <tr class="headerRow">
                <th class="imageName"><spring:message code="admin.image.name" /> </th>
                <th class="imageUser"><spring:message code="admin.image.user" /> </th>
                <th class="imageStatus"><spring:message code="admin.image.status" /> </th>
                <th class="imageMinRam"><spring:message code="admin.image.minRam" /> (MB)</th>
                <th class="imageMinDisk"><spring:message code="admin.image.minDisk" /> (GB)</th>
                <th class="imageOperation"><spring:message code="common.operation" /> </th>
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

</body>
</html>
