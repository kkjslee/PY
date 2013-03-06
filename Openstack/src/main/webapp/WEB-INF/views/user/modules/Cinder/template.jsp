<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Template_ServerSelPanel = '\
    <div id="\${id}">\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="server.choose"/>:</span>\
        </span>\
        <span class="dialogLine">\
         <ul class="selectable">\
         </ul>\
        </span>\
     </div>';
Template_ServerRow = '<li><input type="hidden" value="\${uuid}" name="uuid"/>\${name}</li>';