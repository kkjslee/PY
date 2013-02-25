<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
    <c:if test="${not empty network}">
    <jsp:useBean id="formMap" class="java.util.LinkedHashMap" scope="request" />
    <c:set target="${formMap}" property=".form" value="start_end" />
    <c:set target="${formMap}" property=".title" value="<spring:message code='network.details.label'>" />
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
	    <c:set target="${pageMap}" property=".pageSize" value="20"/>
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
        <c:set target="${pageMap2}" property=".pageSize" value="20"/>
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
    $("#subnetList .fbuttons").append("<a class='button' href='#' onclick='showCreatSubnet();return false;'><spring:message code='create.button'/></a>");
    $( ".button").button();
}

function loadPortSuccessCall(){
    $("#portList .fbuttons").append("<a class='button' href='#' onclick='showCreatPort();return false;'><spring:message code='create.button'/></a>");
    $( ".button").button();
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
