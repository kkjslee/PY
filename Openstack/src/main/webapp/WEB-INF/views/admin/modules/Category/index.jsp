<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<jsp:include page="../../../commonlinks.jsp" />
<jsp:include page="../../adminlinks.jsp" /> 
<link href="<%=request.getContextPath()%>/resource/admin/category/css/main.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/admin/category/scripts/template" language="javascript"></script>
<script src="<%=request.getContextPath()%>/admin/category/scripts/bootstrap" language="javascript"></script>
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
                <th class="categoryName"><spring:message code="category.name.label" /> </th>
                <th class="categoryStatus"><spring:message code="category.status.label" /> </th>
                <th class="categoryOperation"><spring:message code="common.operation" /> </th>
            </tr>
        </thead>
        <tbody>
        </tbody>
        <tfoot>
            <tr class="footerRow">
            <td colspan="3" class="pagination"></td>
            <td align="left"><a class="button" href="#" onclick="showCreatCategory();return false;"><spring:message code="create.button"/></a></td>
            </tr>
        </tfoot>
    </table>
</div>

</body>
</html>
