<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p class="ptitle"><spring:message code="paymnet.select.title"/></p>
<input type="hidden" id="orderId" name="orderId" value="${orderId}">
<ul class="paymethods">
<c:forEach items="${paymethods}" var="pay">
    <li>
        <span class="pitem">
            <label class="${pay.icon}" >
                <input type="radio" name="payMethod" value="${pay.id}"/>
            </label>
        </span>
    </li>
    </c:forEach>
</ul>
 <div class="cartButton">
    <a class="btn  btn-warning btn-large buyorder">
      <spring:message code="cart.buy"/>
    </a>
</div>