<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
var Template_EditProduct = '\
    <div id="\${id}">\
        <c:forEach items="${lList}" var="item" varStatus="status"><span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="product.name.label"/>[${item.name}]:</span>\
            <span class="dialogLineRight i18n">\
                <input class="edit_content isos_content" isos_lang_id="${item.id}" type="text" value=""/><input class="isos_id" type="hidden" value="${item.id}"/>\
            </span>\
        </span></c:forEach>\
        <span class="dialogLine ">\
            <span class="dialogLineLeft"><spring:message code="category.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <select id="categoriesEditSelect" multiple="multiple">\
                </select>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="product.type.label"/>:</span>\
            <span class="dialogLineRight">\
                 <span class="osType"></span>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft infoLable"></span>\
            <span class="dialogLineRight refName">\
           </span>\
        </span>\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="product.price.label"/>:</span>\
            <span class="dialogLineRight">\
                <input isos="defaultPrice" type="text" value=""/>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="status.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                 <select id="available" isos="available">\
                    <option value="true"><spring:message code="product.status.enabled"/></option>\
                    <option value="false"><spring:message code="product.status.disabled"/></option>\
                </select>\
           </span>\
        </span>\
    </div>';
var Template_CreateProduct = '\
    <div id="\${id}">\
        <c:forEach items="${lList}" var="item" varStatus="status"><span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="product.name.label"/>[${item.name}]:</span>\
            <span class="dialogLineRight i18n">\
                <input class="isos_content" isos_lang_id="${item.id}" type="text" value=""/><input class="isos_id" type="hidden" value="${item.id}"/>\
            </span>\
        </span></c:forEach>\
        <span class="dialogLine ">\
            <span class="dialogLineLeft"><spring:message code="category.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <select id="categoriesSelect" multiple="multiple">\
                </select>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="product.type.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <select id="osType">\
                    <option value="-1"><spring:message code="choose.label"/></option>\
                     <option value="2"><spring:message code="image.type"/></option>\
                    <option value="1"><spring:message code="flavor.type"/></option>\
                    <option value="7"><spring:message code="datacenter.type"/></option>\
                    <option value="6"><spring:message code="plan.type"/></option>\
                    <option value="5"><spring:message code="usage.type"/></option>\
                    <option value="3"><spring:message code="storage.type"/></option>\
                    <option value="4"><spring:message code="network.type"/></option>\
                </select>\
           </span>\
        </span>\
        <span class="dialogLine ">\
            <span class="dialogLineLeft infoLabel"></span>\
            <span class="dialogLineRight typeSelect">\
                <select id="refId">\
                </select>\
           </span>\
        </span>\
        <span class="dialogLine flavorDetails" style="display:none">\
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
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="product.price.label"/>:</span>\
            <span class="dialogLineRight">\
                <input isos="defaultPrice" type="text" value=""/>\
           </span>\
        </span>\
        <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="status.label"/>:</span>\
            <span class="dialogLineRight typeSelect">\
                <select id="available" isos="available">\
                    <option value="true"><spring:message code="product.status.enabled"/></option>\
                    <option value="false"><spring:message code="product.status.disabled"/></option>\
                </select>\
           </span>\
        </span>\
    </div>';    
 
 var Template_EditPrice = '\
    <div id="\${id}">\
         <span class="dialogLine">\
            <span class="dialogLineLeft"><spring:message code="product.price.label"/>:</span>\
            <span class="dialogLineRight">\
                <input isos="defaultPrice" type="text" value=""/>\
           </span>\
        </span>\
    </div>'; 
    
    
var Template_ImgModelOption = '<option value="\${imgId}">\${imgName}</option>';
var Template_FlavorModelOption = '<option value="\${flavorId}">\${flavorName}</option>';
var Template_PlanOption = '<option value="\${id}">\${name}</option>';
var Template_StorageOption = '<option value="\${id}">\${name}</option>';
var Template_CategoryModelOption = '<option value="\${id}">\${currentLocaleName}</option>';