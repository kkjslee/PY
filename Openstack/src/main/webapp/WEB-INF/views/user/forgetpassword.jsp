<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
    <link href="<c:url value='/resource/common/css/theme_enterprise.css' />" rel="stylesheet" type="text/css" />
</head>
<body>
	<center><h4 style="padding:10px;"><spring:message code='user.forgetpassword.title' /></h4></center>
	<div style="margin:40px;">
		<form id="user_password" class="form-horizontal" style="margin:0 auto;width:410px;" method="post" name="user_password" action="<c:url value='/user/doresetpassword'/>">
			<div class="control-group">
			 <label class="control-label" style="width:150px;text-align:right"   for="password"><spring:message code="newPassword.label" />:</label>
             <div class="controls"><input type="password" name="password" id="password" value="" /></div>
			</div>
			<div class="control-group">
				<label class="control-label"  style="width:150px;text-align:right" for="confirmPassword"><spring:message code="confirmPassword.label" />: </label>
				<div class="controls"><input type="password" name="confirmPassword" value="" id="confirmPassword" /></div>
			</div>
			<input type="hidden" id="random" name="random" value="${random}">
				<div style="margin: 20px; text-align: center;">
					<input type="button" onclick="submitNewPassword();" class="btn btn-primary btn-large"
						value="<spring:message code="submit.button"/>">
				</div>
				<c:if test="${not empty errorMessage}">
            <p class="error" style="color: red">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty message}">
            <p class="success" style="color: green">${message}</p>
        </c:if>
		</form>
		
	</div>
	<script>
		function submitNewPassword() {
			var newPassword = document.getElementById("password").val;
			var confirmPassword = document.getElementById("confirmPassword").val;
			if (newPassword == "" || confirmPassword == "") {
				alert("<spring:message code='all.required'/>");
				return;
			}
			if (newPassword != confirmPassword) {
				alert("<spring:message code='password.notequal'/>");
				return;
			}
			document.forms["user_password"].submit();
		}
	</script>
</body>
</html>