<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
    <c:if test="${not empty network}">
    <jsp:useBean id="formMap" class="java.util.LinkedHashMap" scope="request" />
    <c:set target="${formMap}" property=".form" value="start_end" />
    <c:set target="${formMap}" property=".title" value="<spring:message code='network.details.label'/>" />
    <c:set target="${formMap}" property="form.id" value="[plain]${network.id}" />
    <c:set target="${formMap}" property="form.idhidden" value="[hidden]${network.id}" />
    <c:set target="${formMap}" property="idhidden.label" value=" " />
    <c:set target="${formMap}" property="form.name" value="[plain]${network.name}" />
    <c:set target="${formMap}" property="form.status" value="[plain]${network.status}" />
    <c:set target="${formMap}" property="form.adminStateUp" value="[plain]${network.adminStateUpDisplay}" />
    <c:set target="${formMap}" property="form.shared" value="[plain]${network.shareDisplay}" />
    <c:set target="${formMap}" property="form.external" value="[plain]${network.externalDisplay}" />
    <c:import url="/WEB-INF/views/templates/form.jsp">
        <c:param name="form.configuration" value="formMap" />
    </c:import>
    </c:if>
    <div id="subnetList">
	    <c:url value='/admin/quantum/getPagerSubnetList?networkId=${networkId}' var="paginationUrl1"/>
	    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
	    <c:set target="${pageMap}" property=".content" value="dataTable"/>
	    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
	    <c:set target="${pageMap}" property=".pageSize" value="5"/>
	    <c:set target="${pageMap}" property=".pagination" value="pagination"/>
	    <c:set target="${pageMap}" property=".colspanLeft" value="3"/>
	    <c:set target="${pageMap}" property=".colspanRight" value="2"/>
	    <c:set target="${pageMap}" property=".loadSuccessCall" value="loadSubnetSuccessCall"/>
	    <c:set target="${pageMap}" property=".url" value="${paginationUrl1}"/>
	    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
	        <jsp:param name="pagination.configuration" value="pageMap"/>
	    </jsp:include>
	    <div id="showCreateSubnetForm"></div>
	    <div id="showEditSubnetForm"></div>
     </div>
     <div id="portList">
        <c:url value='/admin/quantum/getPagerPortList?networkId=${networkId}' var="paginationUrl2"/>
        <jsp:useBean id="pageMap2" class="java.util.HashMap" scope="request" />
        <c:set target="${pageMap2}" property=".content" value="dataTable2"/>
        <c:set target="${pageMap2}" property=".pageIndex" value="0"/>
        <c:set target="${pageMap2}" property=".pageSize" value="5"/>
        <c:set target="${pageMap2}" property=".pagination" value="pagination2"/>
        <c:set target="${pageMap2}" property=".colspanLeft" value="4"/>
        <c:set target="${pageMap2}" property=".colspanRight" value="2"/>
        <c:set target="${pageMap2}" property=".loadSuccessCall" value="loadPortSuccessCall"/>
        <c:set target="${pageMap2}" property=".url" value="${paginationUrl2}"/>
        <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
            <jsp:param name="pagination.configuration" value="pageMap2"/>
        </jsp:include>
        <div id="showCreatePortForm"></div>
        <div id="showEditPortForm"></div>
     </div>
     
<script>
function loadSubnetSuccessCall(){
    $("#subnetList").find(".fbuttons").append("<a class='button' href='#' onclick='showCreatSubnet();return false;'><spring:message code='create.button'/></a>");
    $( ".button").button();
}

function loadPortSuccessCall(){
    $("#portList").find(".fbuttons").append("<a class='button' href='#' onclick='showCreatPort();return false;'><spring:message code='create.button'/></a>");
    $( ".button").button();
}
var netWorkId = $("#idhidden").val();
function showCreatSubnet(){
    var createSubnetDiag=new CustomForm();
    createSubnetDiag.show({
        title:'<spring:message code="admin.subnet.create"/>',
        container:$('#showCreateSubnetForm'),
        url:'<c:url value="/admin/quantum/showCreateSubnetForm"/>',
        width:420,
        buttons: [
                  {   
                     text: '<spring:message code="confirm.button"/>', 
                     click:function(){
                       createSubnet(createSubnetDiag);
                     }},
                 {
                   text: '<spring:message code="cancel.button"/>',
                   click: function() {
                	   createSubnetDiag.close();
                   }
                  }
                  ]
    });
}

function createSubnet(dataDiag){
     var form = dataDiag.getForm();
     var name = $(form).find("#name").val();
     var cidr = $(form).find("#cidr").val();
     var ipVersion = $(form).find("#ipVersion").val();
     var gateway = $(form).find("#gateway").val();
     var disableGateway = $(form).find("#disableGateway").is(":checked");
     var enableDHCP = $(form).find("#enableDHCP").is(":checked");
     var poolString = $(form).find("#poolString").val();
     var dnsNamesString = $(form).find("#dnsNamesString").val();
     var hostRoutesString = $(form).find("#hostRoutesString").val();
     if(isNull(name)){
         alert("<spring:message code='name.required.label'/>");
         return;
     }
     var pd=showProcessingDialog();
     $.ajax({
         type: "POST",
            dataType: "json",
            cache: false,
            url:  '<c:url value="/admin/quantum/createSubnet" />',
            data:{
                    network:netWorkId,
                    name: name,
                    cidr: cidr,
                    ipVersion: ipVersion,
                    gateway: gateway,
                    disableGateway:disableGateway,
                    enableDHCP:enableDHCP,
                    poolString:poolString,
                    dnsNamesString:dnsNamesString,
                    hostRoutesString:hostRoutesString
            },
            success: function(data) {
                  pd.dialog("destroy");
                if (data.status=="error") {
                    info = data.msg;
                    printWarn(info);
                }else if(data.status=="success"){
                    printMessage(data.msg);
                    dataDiag.close();
                    g_dataTable_loadPagerDataList(g_dataTable_pageIndex, g_dataTable_pageSize);
               }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                  pd.dialog("destroy");
                  printError(jqXHR, textStatus, errorThrown);
            },
            }
            
        );
}

function showEditSubnet(id){
    var editDiag=new CustomForm();
   editDiag.show({
        title:'<spring:message code="admin.subnet.edit"/>',
        container:$('#showEditSubnetForm'),
        url:'<c:url value="/admin/quantum/showEditSubnetForm"/>',
        width:420,
        data:{
        	subnetId:id
        },
        buttons: [
                  {   
                     text: '<spring:message code="confirm.button"/>', 
                     click:function(){
                       editSubnet(id,editDiag);
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

function showPortDetail(id){
    var detailDiag=new CustomForm();
   detailDiag.show({
        title:'<spring:message code="port.details"/>',
        container:$('#showEditPortForm'),
        url:'<c:url value="/admin/quantum/showPortDetail"/>',
        width:420,
        data:{
            portId:id
        },
        buttons: [
                  {   
                     text: '<spring:message code="confirm.button"/>', 
                     click:function(){
                    	 detailDiag.close();
                     }}
                  ]
    });
}

function showSubnetDetail(id){
    var detailDiag=new CustomForm();
   detailDiag.show({
        title:'<spring:message code="subnet.details"/>',
        container:$('#showEditSubnetForm'),
        url:'<c:url value="/admin/quantum/showSubnetDetail"/>',
        width:420,
        data:{
            subnetId:id
        },
        buttons: [
                  {   
                     text: '<spring:message code="confirm.button"/>', 
                     click:function(){
                         detailDiag.close();
                     }}
                  ]
    });
}

function editSubnet(id,dataDiag){
    var form = dataDiag.getForm();
    var name = $(form).find("#name").val();
    var gateway = $(form).find("#gateway").val();
    var disableGateway = $(form).find("#disableGateway").is(":checked");
    var enableDHCP = $(form).find("#enableDHCP").is(":checked");
    var dnsNamesString = $(form).find("#dnsNamesString").val();
    var hostRoutesString = $(form).find("#hostRoutesString").val();
    if(isNull(name)){
        alert("<spring:message code='name.required.label'/>");
        return;
    }
    var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
           dataType: "json",
           cache: false,
           url:  '<c:url value="/admin/quantum/editSubnet" />',
           data:{
        	   id:id,
               name: name,
               gateway: gateway,
               disableGateway:disableGateway,
               enableDHCP:enableDHCP,
               dnsNamesString:dnsNamesString,
               hostRoutesString:hostRoutesString
           },
           success: function(data) {
               pd.dialog("destroy");
               if (data.status=="error") {
                   info = data.msg;
                   printWarn(info);
               }else if(data.status=="success"){
                   printMessage(data.msg);
                   dataDiag.close();
                   g_dataTable_loadPagerDataList(g_dataTable_pageIndex, g_dataTable_pageSize);
              }
           },
           error: function(jqXHR, textStatus, errorThrown) {
               pd.dialog("destroy");
               printError(jqXHR, textStatus, errorThrown);
           },
           }
           
       );
}

function showRemoveSubnet(id){
     if(!confirm("<spring:message code='subnet.remove.confirm'/>")) return;
     var pd=showProcessingDialog();
        $.ajax({
            type: "POST",
            url: '<c:url value="/admin/quantum/removeSubnet" />',
            cache: false,
            data: {
                subnetId: id
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
                   g_dataTable_loadPagerDataList(g_dataTable_pageIndex, g_dataTable_pageSize);
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


function showCreatPort(){
    var createPortDiag=new CustomForm();
    createPortDiag.show({
        title:'<spring:message code="admin.port.create"/>',
        container:$('#showCreatePortForm'),
        url:'<c:url value="/admin/quantum/showCreatePortForm"/>',
        width:420,
        buttons: [
                  {   
                     text: '<spring:message code="confirm.button"/>', 
                     click:function(){
                       createPort(createPortDiag);
                     }},
                 {
                   text: '<spring:message code="cancel.button"/>',
                   click: function() {
                       createPortDiag.close();
                   }
                  }
                  ]
    });
}

function createPort(dataDiag){
     var form = dataDiag.getForm();
     var name = $(form).find("#name").val();
     var adminStateUp = $(form).find("#adminStateUp").is(":checked");
     var deviceId = $(form).find("#deviceId").val();
     var deviceOwner = $(form).find("#deviceOwner").val();
     if(isNull(name)){
         alert("<spring:message code='name.required.label'/>");
         return;
     }
     var pd=showProcessingDialog();
     $.ajax({
         type: "POST",
            dataType: "json",
            cache: false,
            url:  '<c:url value="/admin/quantum/createPort" />',
            data:{
                    network:netWorkId,
                    name: name,
                    adminStateUp: adminStateUp,
                    deviceId: deviceId,
                    deviceOwner: deviceOwner
            },
            success: function(data) {
                  pd.dialog("destroy");
                if (data.status=="error") {
                    info = data.msg;
                    printWarn(info);
                }else if(data.status=="success"){
                    printMessage(data.msg);
                    dataDiag.close();
                    g_dataTable2_loadPagerDataList(g_dataTable2_pageIndex, g_dataTable2_pageSize);
               }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                  pd.dialog("destroy");
                  printError(jqXHR, textStatus, errorThrown);
            },
            }
            
        );
}

function showEditPort(id){
    var editDiag=new CustomForm();
   editDiag.show({
        title:'<spring:message code="admin.port.edit"/>',
        container:$('#showEditPortForm'),
        url:'<c:url value="/admin/quantum/showEditPortForm"/>',
        width:420,
        data:{
            portId:id
        },
        buttons: [
                  {   
                     text: '<spring:message code="confirm.button"/>', 
                     click:function(){
                       editPort(id,editDiag);
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

function editPort(id,dataDiag){
	alert("to do");
	return ;
    var form = dataDiag.getForm();
    var name = $(form).find("#name").val();
    var adminStateUp = $(form).find("#adminStateUp").is(":checked");
    if(isNull(name)){
        alert("<spring:message code='name.required.label'/>");
        return;
    }
    var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
           dataType: "json",
           cache: false,
           url:  '<c:url value="/admin/quantum/editPort" />',
           data:{
        	   id:id,
               name: name,
               adminStateUp:adminStateUp
           },
           success: function(data) {
               pd.dialog("destroy");
               if (data.status=="error") {
                   info = data.msg;
                   printWarn(info);
               }else if(data.status=="success"){
                   printMessage(data.msg);
                   dataDiag.close();
                   g_dataTable2_loadPagerDataList(g_dataTable2_pageIndex, g_dataTable2_pageSize);
              }
           },
           error: function(jqXHR, textStatus, errorThrown) {
               pd.dialog("destroy");
               printError(jqXHR, textStatus, errorThrown);
           },
           }
           
       );
}

function showRemovePort(id){
     if(!confirm("<spring:message code='remove.confirm'/>")) return;
     var pd=showProcessingDialog();
        $.ajax({
            type: "POST",
            url: '<c:url value="/admin/quantum/removePort" />',
            cache: false,
            data: {
                portId: id
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
                   g_dataTable2_loadPagerDataList(g_dataTable2_pageIndex, g_dataTable2_pageSize);
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
