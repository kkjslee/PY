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
    <td class="flavorName"><!-- to be compatible with ie6,hidden input should be place in td element  -->
    <input type="hidden" id="pageTotal" value="${pageTotal}">
    <input isos="flavorId" type="hidden" value="${item.flavorId}" />
    <input isos="vcpus" type="hidden" value="${item.vcpus}" />
     <input isos="flavorName" type="hidden" value="${item.flavorName}" />
    <input isos="ram" type="hidden" value="${item.ram}" />
    <input isos="disk" type="hidden" value="${item.disk}" />
    ${item.flavorName}
    </td>
    <td class="flavorVcpus">${item.vcpus}</td>
    <td class="flavorRam">${item.ram}</td>
    <td class="flavorRdisk">
         ${item.disk}
    </td>
    <td class="flavorStatus">
         ${item.status}
    </td>
    <td class="flavorOperation moduleOperation">
       <div class="btn-group">
         <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
            <spring:message code="common.operation" />
             <span class="caret"></span>
         </a>
         <ul class="dropdown-menu">
         <li><a  href="#" onclick="removeFlavor(this);return false;"><spring:message code="remove.button" /></a></li>
         </ul>
       </div>
        <!-- <span ><a  href="#" onclick="showEditFlavor(this);return false;"><spring:message code="edit.button" /></a></span> -->
    </td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
    <tr><td colspan="6"><spring:message code="data.norecords"/></td></tr>
</c:otherwise>
</c:choose>