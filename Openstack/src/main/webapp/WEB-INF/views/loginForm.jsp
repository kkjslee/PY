<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.inforstack.openstack.utils.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="user.login.page.title"/></title>
	
	<jsp:include page="jquerybootstrap.jsp" />
	<link href="<c:url value='/resource/common/css/theme_enterprise.css' />" rel="stylesheet" type="text/css" />
		
	<script src="<%=request.getContextPath()%>/user/scripts/template" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/user/scripts/bootstrap" type="text/javascript"></script>
		
	<style>
	.green{
	   color:red;
	}
	</style>
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
		 $(function(){
             $("#userRegBtn").bind("click",function(){
            	 $("#message").html("");
            	 var regForm=new CustomForm();
            	 regForm.show({
            		 title:'<spring:message code="user.user.signup"/>',
            		 container:$('#showRegForm'),
            		 url:'<c:url value="/user/regForm"/>',
            		 width:420,
       				 buttons: [
       				           {   
       				        	  text: '<spring:message code="confirm.button"/>', 
       				        	  click:function(){
       				        	    doUserReg(regForm);
       				        	  }},
       				          {
       				            text: '<spring:message code="cancel.button"/>',
       				            click: function() {
       				            	regForm.close();
       				            }
       				           }
       				           ]
            	 });
            	 });
             
             $("#userForgetPswBtn").bind("click",function(){
                 $("#message").html("");
                 var forPswForm=new CustomForm();
                 forPswForm.show({
                     title:'<spring:message code="user.getpassword"/>',
                     container:$('#showForgetPasswordForm'),
                     url:'<c:url value="/user/forgetPswForm"/>',
                     width:420,
                     buttons: [
                               {   
                                  text: '<spring:message code="confirm.button"/>', 
                                  click:function(){
                                	  resetPassword(forPswForm);
                                  }},
                              {
                                text: '<spring:message code="cancel.button"/>',
                                click: function() {
                                	forPswForm.close();
                                }
                               }
                               ]
                 });
                 });
         });
		 
		 function resetPassword(forPswForm){
             var form = forPswForm.getForm();
             var username = $(form).find("#username").val();
             var email = $(form).find("#email").val();
             if(isNull(username) || isNull(email)){
                 alert("<spring:message code='all.required'/>");
                 return;
             }
             $.ajax({
                 type: "POST",
                    dataType: "json",
                    cache: false,
                    url:  '<c:url value="/user/resetPassword" />',
                    data:{
                            username: username,
                            email: email
                    },
                    success: function(data) {
                        if (data.error) {
                            info = data.error;
                            alert(info);
                        }else if(data.success){
                        	forPswForm.close();
                            alert(data.success);
                       }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                    },
                    }
                    
                );
         }
		 
		 function doUserReg(regForm){
			 var form = regForm.getForm();
			 var username = $(form).find("#username").val();
			 var password = $(form).find("#password").val();
			 var confirmPassword= $(form).find("#confirmPassword").val();
			 var email = $(form).find("#email").val();
			 if(isNull(username) || isNull(password) || isNull(confirmPassword) || isNull(email)){
				 alert("<spring:message code='all.required'/>");
				 return;
			 }
			 if(username.length<6 || username.length>45 || password.length<6 || password.length >45){
                 alert("<spring:message code='username.password.notvalid'/>");
                 return;
             }
			 if(password != confirmPassword){
				 alert("<spring:message code='password.notequal'/>");
				 return;
			 }
			 var pd=showProcessingDialog();
			 $.ajax({
				 type: "POST",
		            dataType: "json",
		            cache: false,
		            url:  '<c:url value="/user/userReg" />',
		            data:{
	                        username: username,
	                        password: password,
	                        email: email
		            },
		            success: function(data) {
		            	  pd.dialog("destroy");
		            	if (data.error) {
                            info = data.error;
                            alert(info);
                        }else if(data.success){
                        	regForm.close();
                        	$("#message").attr("class","green");
                        	$("#message").html(data.success);
                       }
		            },
		            error: function(jqXHR, textStatus, errorThrown) {
		            	  pd.dialog("destroy");
		            },
		            }
			        
			    );
		 }
	</script>
	<style>
	#userForgetPswBtn{
	    padding:2px 1px 2px 3px;
	}
	</style>
</head>
<body >
<div id="splash">
${request}
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
				<form id="logonForm" action="<c:url value='/${enterpoint}/doLogin' />" name="logonForm" method="post">
					<div class="modal-body clearfix">
						<fieldset>
							<div class="control-group form-field clearfix">
								<label for="id_username"><spring:message code="user.name.lable" /></label>
								<span class="help-block"></span>
								<div class="input">
									<input id="j_username"  class="iwidth" type="text" name="j_username" maxlength="40" />
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
						  	<a id="userForgetPswBtn" href="#" onclick="return false;" class="pull-right"><spring:message code="user.forgetpassword"/></a>
						     <a type="button" id="userRegBtn" href="#"  onclick="return false;" class="regBtn btn btn-warning pull-right"><spring:message code="user.user.signup"/></a>
						 </c:if>
						 <c:if test='${"agent" eq enterpoint }'>
						 	<a type="button" id="agentRegBtn" href="<c:url value='/${enterpoint}/reg'/>" class="regBtn btn btn-warning pull-right"><spring:message code="user.user.signup"/></a>
						 </c:if>
			       <div id="message" class="msg">
				       <c:if test="${not empty param.error && param.error=='true'}">
				            <p class="error" style="color:red"><spring:message code="user.login.fail"></spring:message></p>
				       </c:if>
				        <c:if test="${not empty param.reg && param.reg=='success'}">
                            <p class="success" style="color:green"><spring:message code="user.reg.success"></spring:message></p>
                       </c:if>
			        </div>  
			        <div id="showRegForm"></div>
			        <div id="showForgetPasswordForm"></div>
              </div> 
          </form>
    </div> 
   </div> 
  </div> 
  </div>
  <script>$("select").selectmenu();</script>
 </body>
</html>