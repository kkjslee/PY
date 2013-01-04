<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="user.login.page.title"></spring:message> </title>
<style type="text/css" media="screen">
* {
	padding: 0;
	margin: 0;
}
body {
	font: 76%/1.3 tahoma, helvetica, arial, sans-serif;
	color: #222;
}
form {
	width: 320px;
	background: url(<%=request.getContextPath()%>/resources/images/hlogo.png) 2px 2px no-repeat #EDF2F6;
	padding: 2px 2px 5px;
	border: 1px solid #85bafb;
	margin: 80px auto 0;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	-moz-box-shadow: 1px 1px 3px rgba(0,0,0,0.6);
	-webkit-box-shadow: 1px 1px 3px rgba(0,0,0,0.6);
	box-shadow: 1px 1px 3px rgba(0,0,0,0.6);
}
form h1 {
	height: 85px;
	text-indent: -99999px;
}
p {
	width: 110px;
	font-weight: bold;
	text-align: center;
	margin: 15px auto;
}
p a {
	color: #00AEEF;
	text-decoration: none;
}
p a:hover {
	text-decoration:underline;
}
p.error {
	background: url(<%=request.getContextPath()%>/resources/images/warning.png) 0 3px no-repeat;
	color: #c00;
	min-height: 20px;
	line-height:20px;
	vertical-align:middle;
	padding-left: 20px;
}
table {
	width: 100%;
	border-collapse:collapse;
	border-spacing:0;
}
table tr {
	color: #fff;
	font-size: 1.1em;
	font-weight: bold;
}
table tr td{
	line-height:29px;
}
.label {
	width: 88px;
	text-align:right;
	padding-right:5px;
	color:#00AEEF;
}
.tinput input {
	left: 80px;
	width: 170px;
	padding: 4px;
}
.tsubmit{
	text-align:left;
	padding-top:3px;
}
.inforstack_button{
	background: url(<%=request.getContextPath()%>/resources/images/inforstack_buttons.png) left top no-repeat;
	border: none;
	color: #fff;
	font-size: 15px;
	line-height: 29px;
	text-align: center;
	display: inline-block;
	cursor: pointer;
	font-weight: bold;
	text-decoration: none;
}
.inforstack_button:hover {
	text-decoration: none;
}
.blue_button_small {
	background-position: -308px 1px;
	font-size: 15px;
	padding: 0;
	height: 31px;
	width: 72px;
}
.blue_button_small:hover {
	background-position: -308px -31px;
	color: #FFF;
}
.logo{
	padding-top:3px;
}
</style>
</head>
<body>
<form method="post" action="<c:url value='j_spring_security_check' />" name="logonForm">
	<h1></h1>
	<table>
	<tr>
		<td class="label">
			<spring:message code="user.name.lable"></spring:message>
		</td>
		<td class="tinput">
			<input id="j_username" type="text" name="j_username" maxlength="40" autofocus />
		</td>
	</tr>
	<tr>
		<td class="label">
			<spring:message code="user.password.lable"></spring:message>
		</td>
		<td class="tinput">
			<input id="j_password" type="password" name="j_password" maxlength="40" />
		</td>
		</tr>
	<tr>
		<td class="tinput"></td>
		<td class="tsubmit">
			<input class="blue_button_small inforstack_button" type="submit" value="<spring:message code="user.login.lable"></spring:message>"/>
		</td>
	</tr>
	</table>
</form>
	<c:if test="${not empty param.error && param.error=='true'}">
		<p class="error"><spring:message code="user.login.fail"></spring:message></p>
	</c:if>
</body>
</html>
