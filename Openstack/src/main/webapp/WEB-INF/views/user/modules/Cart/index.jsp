<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="<%=request.getContextPath()%>/resource/normaluser/cart/js/bootstrap.js" language="javascript"></script>
</head>
<body>
<div class="banner">
    <label>
        <span id="banner" ></span>
    </label>
</div>

<div id="mainBody" >
    <div id="cartCheck">
        <table class="table">
        <tbody>
        <tr>
            <td class="cartLabel"><spring:message code="cart.name.title"/>: </td>
            <td class="cartCategory">
                <input type="text" name="name" id="name" maxlength="40"/>
            </td>
            <td colspan="2"></td>
       </tr>
        <tr>
            <td class="cartLabel"><spring:message code="cart.image.title"/>: </td>
            <td class="cartCategory">
                <select class="imgList selectable" isos="img">
                <option value="-1" selected><spring:message code="choose.label"/></option>
                    <c:forEach items="${imgList}" var="img" varStatus="status">
                                <option value="${img.id}" defaultprice="${img.defaultPrice}">
                                <c:forEach items="${img.name}" var="i18Name">
                                ${i18Name.content}
                                </c:forEach> -- 
                                <c:forEach items="${img.details}" var="detail">
                                ${detail.key} : ${detail.value}
                                </c:forEach>
                                </option>
                            </c:forEach>
                </select>
            </td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td class="cartLabel"><spring:message code="cart.flavor.title"/>: </td>
            <td class="cartCategory">
                <select class="flavorList selectable" isos="flavor">
                <option value="-1" selected><spring:message code="choose.label"/></option>
                    <c:forEach items="${flavorList}" var="flavor" varStatus="status">
                                <option value="${flavor.id}" defaultprice="${flavor.defaultPrice}">
                                <c:forEach items="${flavor.name}" var="i18Name">
                                ${i18Name.content}
                                </c:forEach> -- 
                                <c:forEach items="${flavor.details}" var="detail">
                                ${detail.key} : ${detail.value}
                                </c:forEach>
                                </option>
                            </c:forEach>
                </select>
            </td>
             <td colspan="2"></td>
        </tr>
        <tr>
            <td class="cartLabel"><spring:message code="cart.plan.title"/>: </td>
            <td class="cartCategory">
                <select class="planList selectable" isos="plan">
                 <option value="-1" selected><spring:message code="choose.label"/></option>
                    <c:forEach items="${planList}" var="plan" varStatus="status">
                                <option value="${plan.id}" defaultprice="${plan.defaultPrice}">
                                <c:forEach items="${plan.name}" var="i18Name">
                                ${i18Name.content}
                                </c:forEach> -- 
                                <c:forEach items="${plan.details}" var="detail">
                                ${detail.key} : ${detail.value}
                                </c:forEach>
                                </option>
                            </c:forEach>
                </select>
            </td>
             <td colspan="2"></td>
        </tr>
        <tr>
            <td class="cartLabel"><spring:message code="cart.volume.title"/>: </td>
            <td class="cartCategory">
                <select class="volumeTypeList selectable" isos="volumeType">
                <option value="-1" selected><spring:message code="choose.label"/></option>
                    <c:forEach items="${volumeTypeList}" var="volumeType" varStatus="status">
                                <option value="${volumeType.id}" defaultprice="${volumeType.defaultPrice}">
                                <c:forEach items="${volumeType.name}" var="i18Name">
                                ${i18Name.content}
                                </c:forEach> -- 
                                <c:forEach items="${volumeType.details}" var="detail">
                                ${detail.key} : ${detail.value}
                                </c:forEach>
                                </option>
                            </c:forEach>
                </select>
            </td>
            <td width="18%" style="text-align:left;">
                <spring:message code="volume.name"/>: <input type="text" name="volumeName" style="width:80px" id="volumeName" maxlength="40"/>
            </td>
            <td width="22%" style="text-align:left;">
                 <spring:message code="volume.location"/>: <input type="text" name="volumeLocation" style="width:110px" id="volumeLocation" maxlength="90"/>
            </td>
        </tr>
        </tbody>
        </table>
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
	<div class="selectPayMethods" >
	   <p class="cartSubmitted"><spring:message code="cart.submitted"/></p>
	   <div class="payMethodsContainer">
	   </div>
	</div>
    <script>setServer("<%=request.getContextPath()%>/user/cart");$("select").selectmenu();</script>
</body>
</html>
