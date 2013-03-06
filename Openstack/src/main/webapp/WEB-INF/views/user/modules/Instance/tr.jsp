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
    <td class="vmName"><!-- to be compatible with ie6,hidden input should be place in td element  -->
    <input type="hidden" id="pageTotal" value="${pageTotal}"/>
    <input isos="vmId" type="hidden" value="${item.vmid}" />
    <input isos="vmName" type="hidden" value="${item.vmname}" />
    <c:if test="${not empty attachmentModel}">
        <input isos="vmVolume" type="hidden" value="${attachmentModel.volume}" />
    </c:if>
    <input isos="vmAssignedTo" type="hidden" value="${item.assignedto}" />
    <div class="ititle">${item.vmname}</div>
        <div class="pipvice vice">
        <c:forEach items="${item.addresses}" var="address">[${address.key}:${address.value}]<br/>
        </c:forEach>
        ${item.region}
      </div>
    </td>
    <td class="vmStatus">
        <div class="ititle statusTitle">${item.statusdisplay}</div>
        <c:if test='${item.isProcessing == true}'>
        <div class="statusVice vice"><c:if test='${not empty item.taskStatus}'>${item.taskStatus} </c:if></div>
       </c:if>
    </td>
    
    <td class="vmFlavor">${item.flavorName}</td>
    <td class="vmOstype">${item.ostype}</td>
    <td class="vmPeriod">${item.period}</td>
    <td class="vmStartTime">${item.starttime}</td>
    
    <td class="vmOperation moduleOperation">
        <div class="btn-group">
		  <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
		   <spring:message code="common.operation" />
		    <span class="caret"></span>
		  </a>
		  <ul class="dropdown-menu">
		    <li class="ope vmstopped" <c:if test='${item.status=="stopped"}'> style="display:block" </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'poweron','<spring:message code='poweron.button'/>');return false;"><spring:message code="poweron.button" /></a></li>
	        <li class="ope vmactive"  <c:if test='${item.status=="active"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'poweroff','<spring:message code='poweroff.button'/>');return false;"><spring:message code="poweroff.button" /></a></li>
	        <li class="ope vmunpause"  <c:if test='${item.status=="unpause" || item.status == "active"}'> style="display:block"   </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'pause','<spring:message code='pause.button'/>');return false;"><spring:message code="pause.button" /></a></li>
	        <li class="ope vmpaused" <c:if test='${item.status=="paused"}'> style="display:block" </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'unpause','<spring:message code='unpause.button'/>');return false;"><spring:message code="unpause.button" /></a></li>
	        <li class="ope vmsuspended"  <c:if test='${item.status=="suspended"}'> style="display:block" </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'resume','<spring:message code='resume.button'/>');return false;"><spring:message code="resume.button" /></a></li>
	        <li class="ope vmresuming"    <c:if test='${item.status=="resuming" || item.status == "active"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="ctlInstance(this, 'suspend','<spring:message code='suspend.button'/>');return false;"><spring:message code="suspend.button" /></a></li>
	        <li <c:if test='${item.status!="deleted"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="showDetails(this);return false;"><spring:message code="detail.button" /></a></li>
	        <li class="ope vmdeleted"  <c:if test='${item.status!="deleted"}'> style="display:block"  </c:if>><a class="button" href="#" onclick="showRemoveTips1(this);return false;"><spring:message code="remove.button" /></a></li>
		  </ul>
        </div>
        </td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
    <tr><td colspan="7"><spring:message code="data.norecords"/></td></tr>
</c:otherwise>
</c:choose>