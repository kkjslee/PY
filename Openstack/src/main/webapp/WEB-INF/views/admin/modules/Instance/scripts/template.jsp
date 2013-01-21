<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Template_CreateVM = '\
    <div id="\${id}">\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="vmname.label"/>:</span>\
            <span class="dialogLineRight">\
                <input isos="vmname" type="text" value=""/>\
            </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="imageId.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <select id="selImageModel" isos="selImageModel">\
                </select>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="flavorId.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <select id="selFlavorModel" isos="selFlavorModel">\
                </select>\
            </span>\
         </span>\
         <span class="dialogLine flavorDetails">\
            <span class="dialogLineLeft"><spring:message code="flavorName.label"/>:</span>\
            <span class="dialogLineRight" isos="flavorName">\
            </span>\
            <span class="dialogLineLeft"><spring:message code="vcpus.label"/>:</span>\
            <span class="dialogLineRight" isos="vcpus">\
            </span><br/>\
            <span class="dialogLineLeft"><spring:message code="ram.label"/>:</span>\
            <span class="dialogLineRight" isos="ram">\
            </span>\
            <span class="dialogLineLeft"><spring:message code="disk.label"/>:</span>\
            <span class="dialogLineRight" isos="disk">\
            </span>\
        </span>\
    </div>';
    
var Template_ImgModelOption = '<option value="\${imgId}">\${imgName}</option>';
var Template_FlavorModelOption = '<option value="\${flavorId}">\${flavorName}</option>';