<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="user.entry.title"/></title>
<jsp:include page="../jquerybootstrap.jsp" />
<jsp:include page="userlinks.jsp" />
<link href="<%=request.getContextPath()%>/resource/normaluser/common/css/navigator.css" rel="Stylesheet" type="text/css"  />
<script src="<%=request.getContextPath()%>/user/scripts/navinit" type="text/javascript"></script>
</head>
<body class="mainBody">
<div class="mainContainer">
<div class="left" >
    <div class="logo"><img src="${pageContext.request.contextPath}/resource/common/image/logo.png"/></div>
      <div id="sidebar" class="menu">
        <div id="sidebar_menu" class="accordion">
        
            <div class="accordion-group">
                  <div class="accordion-heading">
                     <a class="accordion-toggle" href="#" name="menuItem" isos:module="modules/entry/index"><spring:message code='user.entry.index'/></a>
                   </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <a href="#collapse1" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" ><spring:message code='user.navi.apply'/></a>
                 </div>
                 <div id="collapse1" class="accordion-body collapse">
                    <ul class="accordion-inner">
                        <li><a href="#" name="menuItem" isos:module="cart/modules/volume"><spring:message code='user.cart.volume' /></a></li>
                        <li><a href="#" name="menuItem" isos:module="cart/modules/ip" ><spring:message code='user.cart.ip' /></a></li>
                        <li><a href="#" name="menuItem" isos:module="cinder/modules/index" ><spring:message code='user.navi.volume' /></a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <a href="#collapse2" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" ><spring:message code='user.navi.myproduct'/></a>
                 </div>
                 <div id="collapse2" class="accordion-body collapse">
                    <ul class="accordion-inner">
                        <li><a href="#" name="menuItem" isos:module="instance/modules/index"><spring:message code='user.navi.instance' /></a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <a href="#collapse3" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" ><spring:message code='user.navi.ordermgr'/></a>
                 </div>
                 <div id="collapse3" class="accordion-body collapse">
                    <ul class="accordion-inner">
                        <li><a href="#" name="menuItem" isos:module="order/modules/index"><spring:message code='user.navi.order' /></a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <a href="#collapse4" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" ><spring:message code='user.navi.financemgr'/></a>
                 </div>
                 <div id="collapse4" class="accordion-body collapse">
                    <ul class="accordion-inner">
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <a href="#collapse5" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" ><spring:message code='user.navi.accountmgr'/></a>
                 </div>
                 <div id="collapse5" class="accordion-body collapse">
                    <ul class="accordion-inner">
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <a  class="accordion-toggle" href="<c:url value='/user/doLogout'/>"><spring:message code='user.logout' /></a>
                 </div>
                 <div  class="accordion-body collapse">
                    <ul class="accordion-inner">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="right">
     <div style="font-family:'微软雅黑'; font-weight:bold; font-size:18px; color:#e38967; padding:50px 0 10px 40px; border-bottom:1px solid #ddd;"><spring:message code="user.entry.title"/></div>
    <div style="padding:30px 0 10px 60px;">
        <div style="font-family:'微软雅黑';font-size:14px;"><spring:message code="user.entry.welcome"/></script></div>
    </div>
</div>
</div>

</body>
</html>
