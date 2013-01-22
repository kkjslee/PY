<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Template_ImageDetails = '\
    <div id="\${id}">\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.image.name"/>:</span>\
            <span class="dialogLineRight" isos="imgName">\
            </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.image.user"/>:</span>\
            <span class="dialogLineRight" isos="user">\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.image.status"/>:</span>\
            <span class="dialogLineRight" isos="statusDisplay" >\
            </span>\
         </span>\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.image.minRam"/>:</span>\
            <span class="dialogLineRight" isos="minRam">\
           </span>\
        </span>\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.image.minDisk"/>:</span>\
            <span class="dialogLineRight" isos="minDisk">\
           </span>\
        </span>\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.image.created"/>:</span>\
            <span class="dialogLineRight" isos="createdTime">\
           </span>\
        </span>\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.image.updated"/>:</span>\
            <span class="dialogLineRight" isos="updatedTime">\
           </span>\
        </span>\
    </div>';
    
