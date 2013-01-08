<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.inforstack.openstack.utils.Constants"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="user.login.page.title"/></title>
	<link href="${requestScope.contextPath}/resource/common/css/theme_enterprise.css" rel="stylesheet" type="text/css" />
	<script>
		function changeLocale(language){
			if(language == -1){
	            return false;            
	        }
		    var url = window.location.href;
		    if(url.indexOf("languageCode=1") >0){
		            url = url.replace(/languageCode=1/g,"languageCode="+language); 
		    }else if(url.indexOf("languageCode=2") >0){
		            url = url.replace(/languageCode=2/g,"languageCode="+language); 
		    }else if(url.indexOf("languageCode") == -1){
		            if(url.indexOf("?") >0){
		                    url = url + "&languageCode="+language;
		                }else{
		                    url = url + "?languageCode="+language;
		                    }
		        }
		    window.location.href=url;
		}
	</script>
</head>
<body id="splash">
	<div class="container">
		<div class="row large-rounded">
			<div style="right: 15px; top: 15px; position: absolute;">
				<select name="language" id="language" style="min-width: 120px;" onchange="changeLocale(document.getElementById('language').value)" >
					<option><spring:message code="user.language.choose"/></option>
					<option value="2">简体中文</option>
					<option value="1">English</option>
					<!-- <option value="ja_JP">日本語</option> -->
				</select>
			</div>
			<div id="" class="login ">
				<div class="modal-header">
					<h3>
						<spring:message code="system.name" />
					</h3>
				</div>
				<form id="" action="<c:url value='/${enterpoint}/doLogin' />" name="logonForm">
					<div class="modal-body clearfix">
						<fieldset>
							<div class="control-group form-field clearfix">
								<label for="id_username"><spring:message code="user.name.lable" /></label>
								<span class="help-block"></span>
								<div class="input">
									<input id="j_username" class="iwidth" type="text" name="j_username" maxlength="40" />
								</div>
							</div>
							<div class="control-group form-field clearfix">
								<label for="id_password"><spring:message code="user.password.lable" /></label>
								<span class="help-block"></span>
								<div class="input">
									<input id="j_password" class="iwidth"  type="password" name="j_password" maxlength="40" />
								</div>
							</div>
							<%
							  pageContext.setAttribute("agentKey", Constants.SESSION_ATTRIBUTE_NAME_AGENT);
							%>
							<c:if test='${"user" eq enterpoint && sessionScope[agentKey] != null}'>
								<div class="control-group form-field clearfix">
									<label for="id_password"><spring:message code="user.agent.lable" /> : ${sessionScope[agentKey].tenant.name}</label> 
									   <span class="help-block"></span>
								</div>
							</c:if>
						</fieldset>
					</div>
					<div class="modal-footer">
						<input type="submit" id="logBtn" tabindex="3" class="logBtn btn btn-primary pull-right" value='<spring:message code="user.login.lable"/>'/>
						<c:if test='${"user" eq enterpoint}'>
						  	<a type="button" id="regBtn" href="<c:url value='/${enterpoint}/reg'/>" class="regBtn btn btn-warning pull-right"><spring:message code="user.user.signup"/></a>
						 </c:if>
						 <c:if test='${"agent" eq enterpoint }'>
						 	<a type="button" id="regBtn" href="<c:url value='/${enterpoint}/reg'/>" class="regBtn btn btn-warning pull-right"><spring:message code="user.user.signup"/></a>
						 </c:if>
			       <div id="message" class="msg">
				       <c:if test="${not empty param.error && param.error=='true'}">
				            <p class="error"><spring:message code="user.login.fail"></spring:message></p>
				       </c:if>
			        </div>  
              </div> 
          </form> 
    </div> 
   </div> 
  </div> 
 </body>
</html>