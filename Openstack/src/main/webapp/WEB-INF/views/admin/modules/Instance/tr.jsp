<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<input type="hidden" id="pageTotal" value="${pageTotal}">
<c:choose>
<c:when test='${not empty dataList}'>
<c:forEach items="${dataList}" var="item" varStatus="status">
    <c:if test="${status.index%2==0}">
                <tr class="dataRow rowOdd">
    </c:if>
    <c:if test="${status.index%2!=0}">
    <tr class="dataRow  rowEve">
    </c:if>
    <td class="vmRowCell vmName"><!-- to be compatible with ie6,hidden input should be place in td element  -->
    <input isos="vmId" type="hidden" value="${item.vmid}" />
    <input isos="vmName" type="hidden" value="${item.vmname}" />
    <input isos="vmAssignedTo" type="hidden" value="${item.assignedto}" />
    <div class="ititle">${item.vmname}</div>
        <div class="pipvice vice">${item.privateips}</div>
    </td>
    <td class="vmRowCell vmUser">${item.assignedto}</td>
    <td class="vmRowCell vmStatus">
        <div class="ititle statusTitle">${item.statusdisplay}</div>
        <c:if test='${item.isProcessing == true}'>
        <div class="statusVice vice"><c:if test='${not empty item.taskStatus}'>${item.taskStatus} </c:if></div>
       </c:if>
    </td>
    <td class="vmRowCell vmOstype">
         ${item.ostype}
    </td>
    <td class="vmRowCell vmOperation ">
        <span class="ope vmstopped" <c:if test='${item.status=="stopped"}'> style="display:block" </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'poweron','<spring:message code='poweron.button'/>');return false;"><spring:message code="poweron.button" /></a></span>
        <span class="ope vmactive"  <c:if test='${item.status=="active"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'poweroff','<spring:message code='poweroff.button'/>');return false;"><spring:message code="poweroff.button" /></a></span>
        <span class="ope vmunpause"  <c:if test='${item.status=="unpause" || item.status == "active"}'> style="display:block"   </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'pause','<spring:message code='pause.button'/>');return false;"><spring:message code="pause.button" /></a></span>
        <span class="ope vmpaused" <c:if test='${item.status=="paused"}'> style="display:block" </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'unpause','<spring:message code='unpause.button'/>');return false;"><spring:message code="unpause.button" /></a></span>
        <span class="ope vmsuspended"  <c:if test='${item.status=="suspended"}'> style="display:block" </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'resume','<spring:message code='resume.button'/>');return false;"><spring:message code="resume.button" /></a></span>
        <span class="ope vmresuming"    <c:if test='${item.status=="resuming" || item.status == "active"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'suspend','<spring:message code='suspend.button'/>');return false;"><spring:message code="suspend.button" /></a></span>
        <span <c:if test='${item.status!="deleted"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="showDetails(this);return false;"><spring:message code="detail.button" /></a></span>
        <span <c:if test='${item.status!="deleted"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'removevm','<spring:message code='remove.button'/>');return false;"><spring:message code="remove.button" /></a></span>
    </td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
    <tr><td colspan="5"><spring:message code="data.norecords"/></td></tr>
</c:otherwise>
</c:choose>