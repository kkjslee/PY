<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<p class="ptitle"><spring:message code="paymnet.select.title"/></p>
<input type="hidden" id="orderId" name="orderId" value="${orderId}">
<ul class="paymethods">
    <li>
        <span class="pitem">
            <label class="zfb" title="<spring:message code='zfb.payment.label'/>">
                <input type="radio" name="payMethod"  value="zfb"/>
            </label>
        </span>
    </li>
</ul>
 <div class="cartButton">
        <a class="btn  btn-warning btn-large buyorder">
          <spring:message code="cart.buy"/>
        </a>
</div>