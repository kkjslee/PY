<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p class="ptitle"><spring:message code="paymnet.select.title"/></p>
<input type="hidden" id="orderId" name="orderId" value="${orderId}">
<ul class="paymethods">
<c:forEach items="${paymethods}" var="pay" varStatus="status">
    <li>
        <span class="pitem">
            <label class="${pay.icon} btn <c:choose><c:when test="${status.index ==0}">btn-warning</c:when>
            <c:when test="${status.index ==1}">btn-info</c:when><c:otherwise>btn-inverse</c:otherwise></c:choose>" >
                <input type="radio" name="payMethod" id="payMethod" value="${pay.id}"/>${pay.text.i18nContent}
            </label>
        </span>
    </li>
    </c:forEach>
</ul>
