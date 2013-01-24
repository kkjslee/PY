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
    <td class="imageName"><!-- to be compatible with ie6,hidden input should be placed in td element  -->
    <input type="hidden" id="pageTotal" value="${pageTotal}">
    <input isos="imgId" type="hidden" value="${item.imgId}" />
     <input isos="imgName" type="hidden" value="${item.imgName}" />
    <input isos="createdTime" type="hidden" value="${item.createdTime}" />
    <input isos="updatedTime" type="hidden" value="${item.updatedTime}" />
    <input isos="status" type="hidden" value="${item.status}" />
    <input isos="statusDisplay" type="hidden" value="${item.statusDisplay}" />
    <input isos="user" type="hidden" value="${item.user}" />
    <input isos="minRam" type="hidden" value="${item.minRam}" />
    <input isos="minDisk" type="hidden" value="${item.minDisk}" />
    <input isos="tenant" type="hidden" value="${item.tenant}" />
    <input isos="progress" type="hidden" value="${item.progress}" />
     ${item.imgName}
    </td>
    <td class="imageUser">${item.user}</td>
    <td class="imageStatus">
         ${item.statusDisplay}
    </td>
    <td class="imageMinRam">
         ${item.minRam}
    </td>
    <td class="imageMinDisk">
         ${item.minDisk}
    </td>
    <td class="imageOperation moduleOperation">
    <span ><a class="button" href="#" onclick="showImageDetails(this);return false;"><spring:message code="detail.button" /></a></span>
    </td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
    <tr><td colspan="7"><spring:message code="data.norecords"/></td></tr>
</c:otherwise>
</c:choose>