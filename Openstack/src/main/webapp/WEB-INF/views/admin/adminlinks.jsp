<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.inforstack.openstack.utils.SecurityUtils"%>
<!-- admin common link for modules -->
<link rel="Stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resource/admin/common/addons/dataTables/jquery.dataTables.css" />

<script src="<%=request.getContextPath()%>/resource/admin/common/addons/dataTables/jquery.dataTables.min.js" language="javascript"></script>
<script src="<%=request.getContextPath()%>/admin/scripts/bootloader" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resource/admin/common/js/server.js" type="text/javascript"></script>
<%
    String userName =  SecurityUtils.getUserName();
%>
<script>

function getUsername(){
	/*return "<%=userName%>";*/
	return "admin";
}
</script>