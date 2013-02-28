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
    <td class="productName"><!-- to be compatible with ie6,hidden input should be place in td element  -->
    <input type="hidden" id="pageTotal" value="${pageTotal}"/>
    <input isos="id" type="hidden" value="${item.id}" />
    <input isos="defaultPrice" type="hidden" value="${item.defaultPrice}" />
    <input isos="osType" type="hidden" value="${item.osType}">
    <input isos="refId" type="hidden" value="${item.refId}">
    <input isos="available" type="hidden" value="${item.available}">
    <c:forEach items="${item.name}" var="i18Name">
    <input class="langId" lang_isos_id="${i18Name.languageId}" type="hidden" value="${i18Name.content}" />
    ${i18Name.content}<br/>
    </c:forEach>
    <c:forEach items="${item.categories}" var="i18Category">
        <input type="hidden" isos="categories_id"  value="${i18Category.id}" />
        <input type="hidden" isos="categories_name"  value="${i18Category.currentLocaleName}" />
    </c:forEach>
    </td>
    <td class="productPrice">${item.enabledDesc}</td>
    <td class="productPrice">${item.defaultPrice}</td>
    <td class="productOperation moduleOperation">
     <div class="btn-group">
          <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
           <spring:message code="common.operation" />
            <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
           <li><a href="#" onclick="showEditProduct(this);return false;"><spring:message code="edit.button" /></a></li>
           <li><a href="#" onclick="showEditPrice(this);return false;"><spring:message code="editPrice.button" /></a></li>
           </ul>
       </div>
    </td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
    <tr><td colspan="4"><spring:message code="data.norecords"/></td></tr>
</c:otherwise>
</c:choose>