<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
var Template_CreateCategory = '\
    <div id="\${id}">\
        <c:forEach items="${lList}" var="item" varStatus="status"><span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="category.name.label"/>[${item.name}]:</span>\
            <span class="dialogLineRight i18n">\
                <input class="isos_content" type="text" value=""/><input class="isos_id" type="hidden" value="${item.id}"/>\
            </span>\
        </span></c:forEach>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="category.status.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                 <select isos="enable">\
                    <option value="true"><spring:message code="category.status.enabled"/></option>\
                    <option value="false"><spring:message code="category.status.disabled"/></option>\
                </select>\
           </span>\
        </span>\
    </div>';
var Template_EditCategory = '\
    <div id="\${id}">\
        <c:forEach items="${lList}" var="item" varStatus="status"><span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="category.name.label"/>[${item.name}]:</span>\
            <span class="dialogLineRight i18n">\
                <input class="edit_content isos_content" isos_lang_id="${item.id}" type="text" value=""/><input class="isos_id" type="hidden" value="${item.id}"/>\
            </span>\
        </span></c:forEach>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="category.status.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <select isos="enable">\
                    <option value="true"><spring:message code="category.status.enabled"/></option>\
                    <option value="false"><spring:message code="category.status.disabled"/></option>\
                </select>\
           </span>\
        </span>\
    </div>';    
