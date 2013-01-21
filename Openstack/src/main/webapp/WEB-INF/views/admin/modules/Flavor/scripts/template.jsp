<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Template_EditFlavor = '\
    <div id="\${id}">\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.name"/>:</span>\
            <span class="dialogLineRight">\
                <input isos="flavorName" type="text" value=""/>\
            </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.vcpus"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <input isos="vcpus" type="text" value=""/>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.ram"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                 <input isos="ram" type="text" value=""/>  MB\
            </span>\
         </span>\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.rdisk"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                 <input isos="disk" type="text" value=""/> GB\
           </span>\
        </span>\
    </div>';
    
