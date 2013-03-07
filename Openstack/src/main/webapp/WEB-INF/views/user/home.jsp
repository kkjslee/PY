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
    <div class="logo"><img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/admin_logo.gif"/></div>
      <div id="sidebar" class="menu">
        <div id="sidebar_menu" class="accordion">
            <div class="accordion-group">
                  <div class="accordion-heading">
                      <img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/dashboard.gif"/>
                      <a href="#ncollapse0" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu">
                        <spring:message code='user.entry.index'/>
                      </a>
                   </div>
                   <div id="ncollapse0" class="accordion-body collapse in">
                    <ul class="accordion-inner">
                        <li class="active"><a href="#" name="menuItem" isos:module="modules/entry/index" ><spring:message code='user.entry.home' /></a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/notes.gif"/>
                    <a href="#ncollapse1" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" >
                        <spring:message code='user.navi.apply'/>
                    </a>
                 </div>
                 <div id="ncollapse1" class="accordion-body collapse">
                    <ul class="accordion-inner">
                        <li><a href="#" name="menuItem" isos:module="cart/modules/index" ><spring:message code='user.navi.product' /></a></li>
                        <li><a href="#" name="menuItem" isos:module="cart/modules/volume"><spring:message code='user.cart.volume' /></a></li>
                        <li><a href="#" name="menuItem" isos:module="cart/modules/ip" ><spring:message code='user.cart.ip' /></a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/posts.gif"/><a href="#ncollapse2" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" ><spring:message code='user.navi.myproduct'/></a>
                 </div>
                 <div id="ncollapse2" class="accordion-body collapse">
                    <ul class="accordion-inner">
                        <li><a href="#" name="menuItem" isos:module="instance/modules/index"><spring:message code='user.navi.instance' /></a></li>
                        <li><a href="#" name="menuItem" isos:module="cinder/modules/index"><spring:message code='user.navi.volume' /></a></li>
                         <li><a href="#" name="menuItem" isos:module="network/modules/index"><spring:message code='user.navi.ip' /></a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/order.gif"/>
                    <a href="#ncollapse3" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" >
                        <spring:message code='user.navi.ordermgr'/>
                    </a>
                 </div>
                 <div id="ncollapse3" class="accordion-body collapse">
                    <ul class="accordion-inner">
                        <li><a href="#" name="menuItem" isos:module="order/modules/index"><spring:message code='user.navi.order' /></a></li>
                    </ul>
                </div>
            </div>
             <div class="accordion-group">
                 <div class="accordion-heading">
                    <img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/coin.gif"/>
                    <a href="#ncollapse4" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" >
                     <spring:message code='user.navi.financemgr'/>
                    </a>
                 </div>
                 <div id="ncollapse4" class="accordion-body collapse">
                    <ul class="accordion-inner">
                       <li><a href="#" name="menuItem" isos:module="order/modules/index">todo</a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/acc.gif"/>
                    <a href="#ncollapse5" class="accordion-toggle" data-toggle="collapse" data-parent="#sidebar_menu" >
                        <spring:message code='user.navi.accountmgr'/>
                    </a>
                 </div>
                 <div id="ncollapse5" class="accordion-body collapse">
                    <ul class="accordion-inner">
                          <li><a href="#" name="menuItem" isos:module="order/modules/index">todo</a></li>
                    </ul>
                </div>
            </div>
            <div class="accordion-group">
                 <div class="accordion-heading">
                    <img src="<%=request.getContextPath()%>/resource/normaluser/common/css/image_navigator/settings.gif"/><a  class="accordion-toggle" href="<c:url value='/user/doLogout'/>"><spring:message code='user.logout' /></a>
                 </div>
                 <div  class="accordion-body collapse">
                    <ul class="accordion-inner">
                          <li></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="right">
      <div style="font-family:'微软雅黑'; font-weight:bold; font-size:18px; color:#e38967; padding:50px 0 10px 40px; border-bottom:1px solid #ddd;"><spring:message code="user.entry.title"/></div>
    <div style="padding:30px 0 10px 60px;">
        <div style="font-family:'微软雅黑';font-size:14px;">${content}</div>
    </div>
</div>
</div>

</body>
</html>
