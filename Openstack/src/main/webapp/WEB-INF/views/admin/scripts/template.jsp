<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Template_MessageBox = '\
<div title="<spring:message code="admin.vm.dialog.title.tips"/>">\
    <p class="message">{{html message}}</p>\
</div>\
';