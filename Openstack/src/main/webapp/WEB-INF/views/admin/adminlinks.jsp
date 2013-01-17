<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.inforstack.openstack.utils.SecurityUtils"%>
<!-- admin common link for modules -->
<link href="<%=request.getContextPath()%>/resource/admin/common/css/common.css" rel="Stylesheet" type="text/css"  />
<script src="<%=request.getContextPath()%>/resource/admin/common/js/common.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/admin/scripts/template" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/admin/scripts/bootstrap" type="text/javascript"></script>
<%
    String userName =  SecurityUtils.getUserName();
%>
<script>

function getUsername(){
	/*return "<%=userName%>";*/
	return "admin";
}
</script>