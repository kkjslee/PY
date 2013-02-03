<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:choose>
<c:when test='${not empty dataList}'>
<c:forEach items="${dataList}" var="item" varStatus="status">
    <c:if test="${status.index%2==0}">
                <tr class="dataRow rowOdd">
    </c:if>
    <c:if test="${status.index%2!=0}">
    <tr class="dataRow  rowEve">
    </c:if>
    <td class="categoryName"><!-- to be compatible with ie6,hidden input should be place in td element  -->
    <input type="hidden" id="pageTotal" value="${pageTotal}"/>
    <input isos="id" type="hidden" value="${item.id}" />
    <input isos="enable" type="hidden" value="${item.enable}" />
    <c:forEach items="${item.name}" var="i18Name">
    <input class="langId" lang_isos_id="${i18Name.languageId}" type="hidden" value="${i18Name.content}" />
    ${i18Name.content}<br/>
    </c:forEach>
    </td>
    <td class="categoryStatus">${item.enabledDesc}</td>
    <td class="categoryOperation moduleOperation">
        <span ><a class="button" href="#" onclick="showEditCategory(this);return false;"><spring:message code="edit.button" /></a></span>
        <span ><a class="button" href="#" onclick="showRemoveCategory(this,'<spring:message code="remove.button"/>');return false;"><spring:message code="remove.button" /></a></span>
    </td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
    <tr><td colspan="3"><spring:message code="data.norecords"/></td></tr>
</c:otherwise>
</c:choose>