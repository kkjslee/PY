<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
    <c:set target="${pageMap}" property=".colspanLeft" value="4"/>
    <c:set target="${pageMap}" property=".colspanRight" value="3"/>
    <c:set target="${pageMap}" property=".loadSuccessCall" value="loadSuccessCall"/>
    <c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
        <jsp:param name="pagination.configuration" value="pageMap"/>
    </jsp:include>
    <div id="showCreateNetworkForm"></div>
     <div id="showEditNetworkForm"></div>
</div>
<script>
function loadSuccessCall(){
	$(".fbuttons").append("<a class='button' href='#' onclick='showCreatNetWork();return false;'><spring:message code='create.button'/></a>");
	$( ".button").button();
}

function showNetWorkDetails(id){
	var pd = showProcessingDialog();
	   $("#mainBody").empty();
	    $.ajax({
	        type: "get",
	        url: '<c:url value="/admin/quantum/getNetworkDetails"/>',
	        cache: false,
	        dataType:"html",
	        data:{
	        	networkId:id
	        },
	        success: function(data) {
	            try{
	            	$("#banner").text("<spring:message code="network.details.label"/>");
	                $("#mainBody").html(data);
	            }catch(e) {
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            window.console.log(jqXHR.status);
	            alert("<spring:message code="module.load.error"/>");
	        },
	        complete:function(){
	            $(pd).dialog("close");
	        }
	    });
}
function showCreatNetWork(){
	var createDiag=new CustomForm();
	createDiag.show({
        title:'<spring:message code="admin.network.create"/>',
        container:$('#showCreateNetworkForm'),
        url:'<c:url value="/admin/quantum/showCreateNetworkForm"/>',
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
     var pd=showProcessingDialog();
     $.ajax({
         type: "POST",
            dataType: "json",
            cache: false,
            url:  '<c:url value="/admin/quantum/createNetwork" />',
            data:{
                    name: name,
                    adminStateUp: adminStateUp,
                    shared: shared,
                    external: external
            },
            success: function(data) {
            	  pd.dialog("destroy");
                if (data.status=="error") {
                    info = data.msg;
                    printWarn(info);
                }else if(data.status=="success"){
                	printMessage(data.msg);
                	dataDiag.close();
                	g_loadPagerDataList(g_pageIndex, g_pageSize);
               }
            },
            error: function(jqXHR, textStatus, errorThrown) {
            	  pd.dialog("destroy");
            	  printError(jqXHR, textStatus, errorThrown);
            },
            }
            
        );
}

function showEditNetwork(id){showEditNetworkForm
	var editDiag=new CustomForm();
   editDiag.show({
	    title:'<spring:message code="admin.network.edit"/>',
	    container:$('#showEditNetworkForm'),
	    url:'<c:url value="/admin/quantum/showEditNetworkForm"/>',
	    width:260,
	    data:{
           id:id
        },
	    buttons: [
	              {   
	                 text: '<spring:message code="confirm.button"/>', 
	                 click:function(){
	                   editNetWork(id,editDiag);
	                 }},
	             {
	               text: '<spring:message code="cancel.button"/>',
	               click: function() {
	            	   editDiag.close();
	               }
	              }
	              ]
	});
}

function editNetWork(id,dataDiag){
    var form = dataDiag.getForm();
    var name = $(form).find("#name").val();
    var adminStateUp = $(form).find("#adminStateUp").is(":checked");
    var shared = $(form).find("#shared").is(":checked");
    var external = $(form).find("#external").is(":checked");
    if(isNull(name)){
        alert("<spring:message code='name.required.label'/>");
        return;
    }
    var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
           dataType: "json",
           cache: false,
           url:  '<c:url value="/admin/quantum/editNetwork" />',
           data:{
        	       id:id,
                   name: name,
                   adminStateUp: adminStateUp,
                   shared: shared,
                   external: external
           },
           success: function(data) {
        	   pd.dialog("destroy");
               if (data.status=="error") {
                   info = data.msg;
                   printWarn(info);
               }else if(data.status=="success"){
                   printMessage(data.msg);
                   dataDiag.close();
                   g_loadPagerDataList(g_pageIndex, g_pageSize);
              }
           },
           error: function(jqXHR, textStatus, errorThrown) {
        	   pd.dialog("destroy");
        	   printError(jqXHR, textStatus, errorThrown);
           },
           }
           
       );
}

function showRemoveNetwork(id){
	 if(!confirm("<spring:message code='remove.confirm'/>")) return;
	 var pd=showProcessingDialog();
	    $.ajax({
	        type: "POST",
	        url: '<c:url value="/admin/quantum/removeNetwork" />',
	        cache: false,
	        data: {
	            id: id
	        },
	        success: function(data) {
	            pd.dialog("destroy");
	            try{
	                var msg="";
	               if(data.status == "success"){
	                    msg="<spring:message code='remove.success'/>";
	                }else if(data.status == "error"){
	                   msg="<spring:message code='remove.failed'/>";
	               } 
	               g_loadPagerDataList(g_pageIndex, g_pageSize);
	                printMessage(msg);
	                
	            }catch(e) {
	                printMessage("Data Broken: ["+e+"]");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            pd.dialog("destroy");
	            printError(jqXHR, textStatus, errorThrown);
	        }
	    });
}
</script>
</body>
</html>