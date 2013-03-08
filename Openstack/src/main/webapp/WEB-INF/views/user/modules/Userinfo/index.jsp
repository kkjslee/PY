<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
<div class="banner">
        <span id="banner" ><spring:message code='user.navi.userinfo' /></span>
</div>
<body>
    <div id="mainBody">
        <ul class="nav nav-tabs" id="userTabs">
		  <li class="active" ><a href="#basic" data-toggle="tab"><spring:message code="userinfo.basic.edit"/></a></li>
		  <li><a href="#email" data-toggle="tab"><spring:message code="userinfo.email.edit"/></a></li>
		  <li><a href="#password" data-toggle="tab"><spring:message code="userinfo.password.edit"/></a></li>
		</ul>
		 
		<div class="tab-content" id="userTabForms">
		  <div class="tab-pane active" id="basic">
                <jsp:useBean id="formMap0" class="java.util.LinkedHashMap" scope="request" />
			    <c:set target="${formMap0}" property=".form" value="start_end" />
			    <c:set target="${formMap0}" property=".action" value="/edit" />
			    <c:set target="${formMap0}" property=".formName" value="user_basic" />
			    <c:set target="${formMap0}" property="form.username" value="[plain]${user.username}" />
			    <c:set target="${formMap0}" property="form.firstname" value="[text]${user.firstname}" />
			    <c:set target="${formMap0}" property="form.lastname" value="[text]${user.lastname}" />
			    <c:set target="${formMap0}" property="form.phone" value="[text]${user.phone}" />
			    <c:set target="${formMap0}" property="form.mobile" value="[text]${user.mobile}" />
			    <c:set target="${formMap0}" property="form.country" value="[text]${user.country}" />
			    <c:set target="${formMap0}" property="form.province" value="[text]${user.province}" />
			    <c:set target="${formMap0}" property="form.city" value="[text]${user.city}" />
			    <c:set target="${formMap0}" property="form.address" value="[text]${user.address}" />
			    <c:set target="${formMap0}" property="form.postcode" value="[text]${user.postcode}" />
			    <c:import url="/WEB-INF/views/templates/form.jsp">
			        <c:param name="form.configuration" value="formMap0" />
			    </c:import>
			    <div style="margin:20px;text-align:center;">
			       <input type="button" onclick="submitBasic();" class="btn btn-primary btn-large"  value="<spring:message code="submit.button"/>">
			    </div>
          </div>
		  <div class="tab-pane" id="email">
                <jsp:useBean id="formMap1" class="java.util.LinkedHashMap" scope="request" />
                <c:set target="${formMap1}" property=".form" value="start_end" />
                <spring:message code='user.newemail.tip' var="newEmailTip"/>
                <c:set target="${formMap1}" property=".title" value="${newEmailTip}" />
                <c:set target="${formMap1}" property=".action" value="/doreg" />
                <c:set target="${formMap1}" property=".titleClass" value="alert" />
                <c:set target="${formMap1}" property=".formName" value="user_email" />
                <c:set target="${formMap1}" property="form.oldEmail" value="[plain]${user.email}" />
                 <spring:message code='user.oldEmail.label' var="oldEmailLabel"/>
                <c:set target="${formMap1}" property="oldEmail.label" value="${oldEmailLabel}" />
                <c:set target="${formMap1}" property="form.email" value="[text]" />
                <spring:message code='user.newemail.label' var="newEmailLabel"/>
                <c:set target="${formMap1}" property="email.label" value="${newEmailLabel}" />
                <c:import url="/WEB-INF/views/templates/form.jsp">
                    <c:param name="form.configuration" value="formMap1" />
                </c:import>
                 <div style="margin:20px;text-align:center;">
                   <input type="button" onclick="submitEmail();" class="btn btn-primary btn-large"  value="<spring:message code="submit.button"/>">
                </div>
          </div>
		  <div class="tab-pane" id="password">
                 <jsp:useBean id="formMap2" class="java.util.LinkedHashMap" scope="request" />
                <c:set target="${formMap2}" property=".form" value="start_end" />
                <c:set target="${formMap2}" property=".action" value="/changePassword" />
                <c:set target="${formMap2}" property=".formName" value="user_password" />
                <c:set target="${formMap2}" property="form.password" value="[password]" />
                <spring:message code='user.oldpassword.label' var="oldPasswordLabel"/>
                <c:set target="${formMap2}" property="password.label" value="${oldPasswordLabel}" />
                <c:set target="${formMap2}" property="form.newPassword" value="[password]" />
                <c:set target="${formMap2}" property="form.confirmPassword" value="[password]" />
                <c:import url="/WEB-INF/views/templates/form.jsp">
                    <c:param name="form.configuration" value="formMap2" />
                </c:import>
                 <div style="margin:20px;text-align:center;">
                   <input type="button" onclick="submitPassword();" class="btn btn-primary btn-large"  value="<spring:message code="submit.button"/>">
                </div>
          </div>
		</div>
    </div>
    <script>
    function submitBasic(){
    	var pd=showProcessingDialog();
    	$("form[name='user_basic']").ajaxSubmit({
    		dataType:"json",
    		succcess:function(data){
    			pd.dialog("destroy");
    			printMessage(data.result);
    		},
    		error:function(){
    			pd.dialog("destroy");
    			printMessage("<spring:message code='operation.failed'/>");
    		}
    	});
    	return false;
    }
    function submitEmail(){
    	var email= $("form[name='user_email']").find("email").val();
        if(isNull(email)){
            alert("<spring:message code='all.required'/>");
            return;
        }
        var pd=showProcessingDialog();
        $("form[name='user_email']").ajaxSubmit({
            dataType:"json",
            succcess:function(data){
            	 pd.dialog("destroy");
            	printMessage(data.result);
            },
            error:function(){
            	 pd.dialog("destroy");
                printMessage("<spring:message code='operation.failed'/>");
            }
        });
        return false;
    }
    function submitPassword(){
    	var password = $("form[name='user_password']").find("password").val();
        var confirmPassword= $("form[name='user_password']").find("confirmPassword").val();
        if(isNull(password) || isNull(confirmPassword)){
            alert("<spring:message code='all.required'/>");
            return;
        }
        if(password != confirmPassword){
            alert("<spring:message code='password.notequal'/>");
            return;
        }
        var pd=showProcessingDialog();
    	 $("form[name='user_password']").ajaxSubmit({
             dataType:"json",
             succcess:function(data){
            	 pd.dialog("destroy");
            	 printMessage(data.result);
             },
             error:function(){
            	 pd.dialog("destroy");
                 printMessage("<spring:message code='operation.failed'/>");
             }
         });
         return false;
    }
    </script>
</body>
</html>