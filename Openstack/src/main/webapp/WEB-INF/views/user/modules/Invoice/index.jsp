<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="user.order.title"/></title>
</head>
<body>
<div class="banner">
      <span id="banner" ><spring:message code='user.navi.invoice' /></span>
</div>

<div id="mainBody" >
    <c:url value='/user/invoice/getPagerInvoiceList' var="paginationUrl"/>
    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
    <c:set target="${pageMap}" property=".content" value="dataTable"/>
    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
    <c:set target="${pageMap}" property=".pageSize" value="20"/>
    <c:set target="${pageMap}" property=".pagination" value="pagination"/>
    <c:set target="${pageMap}" property=".colspanLeft" value="7"/>
    <c:set target="${pageMap}" property=".colspanRight" value="2"/>
    <c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
        <jsp:param name="pagination.configuration" value="pageMap"/>
    </jsp:include>
    <div id="showOrderDetails" ></div>
    <div id="showPaymethods" ></div>
    <script>
    function showPayMethods(orderId){
    	$('#showPaymethods').empty();
        var createDiag=new CustomForm();
        createDiag.show({
            title:'<spring:message code="pay.label"/>',
            container:$('#showPaymethods'),
            url:'<c:url value="/user/cart/showPayMethodNoBtn"/>',
            data:{
            	orderId:orderId
            },
            width:500,
            buttons: [
                      {   
                         text: '<spring:message code="paynow.label"/>', 
                         click:function(){
                        	 showPayDetails(createDiag);
                         }},
                     {
                       text: '<spring:message code="cancel.button"/>',
                       click: function() {
                           createDiag.close();
                       }
                      }
                      ]
        });
    }
    
    function showPays(form){
    	$(form).find(".selectPayMethods").show();
    	$(form).find(".cartButton").remove();
    }
    
    function showPayDetails(dataDiag){
         var form = dataDiag.getForm();
         var orderId = $(form).find("#orderId").val();
         var payId = $(form).find("#payMethod").val();
         var pd=showProcessingDialog();
         var createDiag2=new CustomForm();
         $('#showOrderDetails').empty();
         createDiag2.show({
             title:"Details",
             container:$('#showOrderDetails'),
             url:'<c:url value="/user/order/showOrderDetails"/>',
             width:500,
             data:{
                 orderId:orderId,
                 payId:payId
             },
             buttons: [
                       {   
                          text: 'OK', 
                          click:function(){
                            payOrder(createDiag2);
                          }},
                      {
                        text: 'Cancel',
                        click: function() {
                            createDiag2.close();
                        }
                       }
                       ]
         });
    }
    

    function payOrder(dataDiag){
        var form = dataDiag.getForm();
        var payurl = $(form).find("#endpoint").val();
        dataDiag.close();
        if(!isNull(payurl)){
             window.open(payurl);
        }
    }
    </script>
</div>
</body>
</html>