<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Template_EditFlavor = '\
    <div id="\${id}">\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.name"/>:</span>\
            <span class="dialogLineRight">\
                <input isos="flavorName" type="text" value=""/><label id="nameCheck"></label>\
            </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.vcpus"/>:</span>\
            <span class="dialogLineRight ">\
                <input isos="vcpus" type="text" value=""/>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.ram"/>:</span>\
            <span class="dialogLineRight ">\
                <select isos="ram">\
                    <option value="512">512MB</option>\
                    <option value="1024">1GB</option>\
                    <option value="2048">2GB</option>\
                    <option value="4096">4GB</option>\
                    <option value="8192">8GB</option>\
                    <option value="16384">16GB</option>\
                </select>\
            </span>\
         </span>\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="admin.flavor.rdisk"/>:</span>\
            <span class="dialogLineRight ">\
                 <input isos="disk" type="text" value=""/> GB\
           </span>\
        </span>\
    </div>';
    
