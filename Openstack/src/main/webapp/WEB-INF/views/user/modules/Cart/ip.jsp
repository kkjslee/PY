<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="<%=request.getContextPath()%>/resource/normaluser/cart/js/ip.js" language="javascript"></script>
</head>
<body>
<div class="banner">
    <span  ><spring:message code="cart.network.banner"/></span>
</div>

<div id="mainBody" >
    <div id="cartCheck">
         <label  class="control-label"><spring:message code="cart.datacenter.title"/>: </label>
         <div class="controls">
             <select class="dataCenterList selectable" isos="dataCenter">
                <option value="-1" selected><spring:message code="choose.label"/></option>
                    <c:forEach items="${dataCenterList}" var="dataCenter" varStatus="status">
                                <option value="${dataCenter.id}" defaultprice="${dataCenter.defaultPrice}">
                                <c:forEach items="${dataCenter.name}" var="i18Name">
                                ${i18Name.content}
                                </c:forEach>
                                </option>
                            </c:forEach>
                </select>  
         </div> <br/>
         <label  class="control-label"><spring:message code="cart.network.title"/>: </label>
         <div class="controls">
             <select class="networkList selectable" isos="network">
                <option value="-1" selected><spring:message code="choose.label"/></option>
                    <c:forEach items="${networkList}" var="network" varStatus="status">
                                <option value="${network.id}" defaultprice="${network.defaultPrice}">
                                <c:forEach items="${network.name}" var="i18Name">
                                ${i18Name.content}
                                </c:forEach>
                                </option>
                            </c:forEach>
                </select>  
         </div>
         
         
        <div class="cartTotalLine">
            <spring:message code="cart.total.label"/> : ￥ <span class="cartTotal">0</span>
        </div>
        <div class="cartButton">
            <a class="btn btn-large submitorder">
              <spring:message code="cart.submit"/>
            </a>
        </div>
    </div>
        
    </div>
    <div class="selectPayMethods" >
       <img src="<%=request.getContextPath()%>/resource/common/image/check.png"/> <p class="cartSubmitted"><spring:message code="cart.submitted"/></p>
       <div class="payMethodsContainer">
       </div>
    </div>
    <div id="showOrderDetails"></div>
    <script>setServer("<%=request.getContextPath()%>/user/cart",'<spring:message code="paymethod.name.required"/>','<%=request.getContextPath()%>/user/order/showOrderDetails');$("select").selectmenu();</script>
</body>
</html>
