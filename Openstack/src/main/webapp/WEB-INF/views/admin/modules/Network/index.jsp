<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/resource/normaluser/network/js/bootstrap.js" language="javascript"></script>
<title></title>
</head>
<body>
<div class="banner">
    <label>
        <span id="banner" ></span>
    </label>
</div>

<div id="mainBody" style="margin:30px 20px 0 20px;">

    <c:url value='/admin/quantum/getPagerNetworkList' var="paginationUrl"/>
    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
    <c:set target="${pageMap}" property=".content" value="dataTable"/>
    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
    <c:set target="${pageMap}" property=".pageSize" value="2"/>
    <c:set target="${pageMap}" property=".pagination" value="pagination"/>
    <c:set target="${pageMap}" property=".colspanLeft" value="2"/>
    <c:set target="${pageMap}" property=".colspanRight" value="1"/>
    <c:set target="${pageMap}" property=".loadSuccessCall" value="loadSuccessCall"/>
    <c:set target="${pageMap}" property=".loadErrorCall" value="loadErrorCall"/>
    <c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
        <jsp:param name="pagination.configuration" value="pageMap"/>
    </jsp:include>
    <div id="showCreateNetworkForm"></div>
</div>
<script>
function loadSuccessCall(){
	$(".fbuttons").append("<a class='button' href='#' onclick='showCreatNetWork();return false;'><spring:message code='create.button'/></a>");
}
$(function(){
	initUI();
})

function showCreatNetWork(){
	var createDiag=new CustomForm();
	createDiag.show({
        title:'<spring:message code="admin.network.create"/>',
        container:$('#showCreateNetworkForm'),
        url:'<c:url value="/admin/quantum/networkForm"/>',
        width:260,
        buttons: [
                  {   
                     text: '<spring:message code="confirm.button"/>', 
                     click:function(){
                       createNetWork(createDiag);
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

function createNetWork(dataDiag){
	 var form = dataDiag.getForm();
     var name = $(form).find("#name").val();
     var adminStateUp = $(form).find("#adminStateUp").is(":checked");
     var shared = $(form).find("#shared").is(":checked");
     var external = $(form).find("#external").is(":checked");
     if(isNull(name)){
         alert("<spring:message code='name.required.label'/>");
         return;
     }
     $.ajax({
         type: "POST",
            dataType: "json",
            cache: false,
            url:  '<c:url value="/user/resetPassword" />',
            data:{
                    name: name,
                    adminStateUp: adminStateUp,
                    shared: shared,
                    external: external
            },
            success: function(data) {
                if (data.error) {
                    info = data.error;
                    printWarn(info);
                }else if(data.success){
                	printMessage("<spring:message code='network.create.success'/>");
                	dataDiag.close();
                	g_loadPagerDataList(g_pageIndex, g_pageSize);
               }
            },
            error: function(jqXHR, textStatus, errorThrown) {
            },
            }
            
        );
}
</script>
</body>
</html>