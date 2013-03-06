<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Template_RemoveTips2 = '\
    <div id="\${id}">\
        <span class="dialogLine"><spring:message code="instance.remove.tip2"/>\
        </span>\
        <span class="dialogLine">\
        <span class="dialogLineLeft"><spring:message code="instance.volume.label"/></span>\
        <span class="dialogLineRight vmVolume"></span>\
        </span>\
    </div>';