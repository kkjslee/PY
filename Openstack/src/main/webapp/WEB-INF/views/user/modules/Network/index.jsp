<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="<%=request.getContextPath()%>/user/network/modules/template" language="javascript"></script>
</head>
<body>
<div class="banner">
        <span id="banner"><spring:message code='user.navi.ip' /></span>
</div>

<div id="mainBody" >
    <c:url value='/user/network/getPagerIPList' var="paginationUrl"/>
    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
    <c:set target="${pageMap}" property=".content" value="dataTable"/>
    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
    <c:set target="${pageMap}" property=".pageSize" value="10"/>
    <c:set target="${pageMap}" property=".pagination" value="pagination"/>
    <c:set target="${pageMap}" property=".loadSuccessCall" value="updateIPDisplay"/>
    <c:set target="${pageMap}" property=".colspanLeft" value="3"/>
    <c:set target="${pageMap}" property=".colspanRight" value="1"/>
    <c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
    <jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
        <jsp:param name="pagination.configuration" value="pageMap"/>
    </jsp:include>
    <br/><br/>
    <script>
    $(function(){
        $.template("serverPanel",  Template_ServerSelPanel);
        $.template("serverRow",  Template_ServerRow);
    })
    function showAssociateOrDe(command,openDesc, ipId, serverId,which){
        jConfirm("<spring:message code='ip.detachorattch.tip'/>".sprintf(openDesc),function(){
        	if(command == "remove"){
        		 ctrlIP(command,ipId, serverId, which);
        		 return;
        	}
            if(!isNull(serverId)){
                ctrlIP(command,ipId, serverId, which);
            }else{
                showServerSelPanel(command,ipId, which);
            }
        });
    }
    
    function showServerSelPanel(command,ipId, which){
         var serverPanel = $.tmpl("serverPanel", [{
                id: "serverPanel"
            }]).appendTo("#mainBody");
         serverPanel = $(serverPanel).dialog({
                modal: true,
                autoOpen: false,
                resizable: false,
                show: "slide",
                hide: "slide",
                width: "420px",
                buttons: [{
                    text: '<spring:message code="confirm.button"/>',
                    click: function() {
                        var uuid = $(serverPanel).find(".ui-selected").find("input[name='uuid']").val();
                        if(!isNull(uuid)){
                            ctrlIP(command,ipId, uuid,which);
                        }else{
                            printMessage("<spring:message code='choose.server.tip'/>");
                            return;
                        }
                        
                        $(this).dialog("destroy");
                        
                    }

                },
                {
                    text: '<spring:message code="cancel.button"/>',
                    click: function() {
                        $(this).dialog("destroy");
                    }
                }]
            });
         //set server list
         getInstancesWidthStatus(serverPanel);
    }
    
    function ctrlIP(command,ipId, serverId,which){
        var pd=showProcessingDialog();
        $.ajax({
            type: "POST",
            url: '<c:url value="/user/network/ipcontrol"/>',
            cache: false,
            data: {
                executecommand: command,
                ipId: ipId,
                serverId:serverId
            },
            success: function(data) {
                pd.dialog("destroy");
                try{
                    var msg="";
                    switch(data.status) {
                        case "success": msg="<spring:message code='operation.success'/>";
                        break;
                        case "error": msg="<spring:message code='operation.failed'/>";break;
                    }
                    
                    printMessage(msg);
                    g_dataTable_loadPagerDataList(g_dataTable_pageIndex, g_dataTable_pageSize);
                    
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
    
    function getInstancesWidthStatus(container){
        var pd=showProcessingDialog();
        var selContainer = $(container).find(".selectable").empty();
        $.ajax({
            type: "POST",
            url: '<c:url value="/user/instance/getInstancesWidthStatus"/>',
            cache: false,
            data: {
                includeStatus: "",
                excludeStatus: "deleted",
                hasIp:false
            },
            success: function(data) {
                pd.dialog("destroy");
                try{
                    if(data.status == "success"){
                        data = data.data;
                        if (data.length == 0) {
                            $("<em><spring:message code='message.no_data'/></em>").appendTo(selContainer);
                        } else {
                            var list = $.tmpl("serverRow", data).appendTo(selContainer);
                            $(selContainer).tableSelect({
                            });
                        }

                        $(container).dialog("open");
                    }else if(data.status =="error"){
                        printMessage(data.msg);
                    }
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
    function updateIPDisplay(){
        $("tbody").find("tr").each(function(e){
            var statusV = $(this).find("input[name='statusV']").val();
            if(statusV == "in-use"){
                $(this).find("li[name='associate']").hide();
                $(this).find("li[name='remove']").hide();
            }else if(statusV == "available"){
                $(this).find("li[name='disassociate']").hide();
            }else{
                $(this).find("li[name='associate']").hide();
                $(this).find("li[name='disassociate']").hide();
            }
        })
        checkPageIPStatus();
    }
    
    var rTask ;
    function checkPageIPStatus(){
        rTask  = setInterval(refreshTaskStatus,2500);

    }
    function refreshTaskStatus(){
         var hasTask = false;
         $(".dataTable").find("tr").each(function(){
             if($(this).find("input[name='statusV']").val() == "associating" || $(this).find("input[name='statusV']").val() == "pending"
            		 || $(this).find("input[name='statusV']").val() == "disassociating" || $(this).find("input[name='statusV']").val() == "deleting"){
                 hasTask = true;
                 var targetId = $(this).find("input[name='id']").val();
                 getTaskStatus($(this), targetId);
             }
             
         });
         if(!hasTask && !isNull(rTask)){
             clearInterval(rTask);     
         }
     }

    function getTaskStatus(row,id){
        $.ajax({
            type: "POST",
            url: '<c:url value="/user/network/getIPDetail"/>',
            cache: false,
            async:false,
            data:{
                uuid: id
            },
            dataType:"json",
            success: function(data) {
                try{
                    if(data.status=="success"){
                        data = data.data;
                        updateButtonWidthStatus(row,data.status,data.statusDisplay);
                    }else if(data.status=="error"){
                        printMessage(data.msg);
                    }
                }catch(e){clearInterval(rTask);printMessage("Data Broken ["+e+"]");};
            },
            error: function(jqXHR, textStatus, errorThrown) {
                clearInterval(rTask);     
                printError(jqXHR, textStatus, errorThrown);
            }
        });
    }
    function updateButtonWidthStatus(row,status,statusDisplay){
        if(!isNull(status)){
            if(status == "in-use"){
                $(row).find("li[name='associate']").hide();
                $(row).find("li[name='disassociate']").show();
                $(this).find("li[name='remove']").hide();
            }else if(status == "available"){
            	$(row).find("li[name='associate']").show();
                $(row).find("li[name='disassociate']").hide();
            }else if(status == "deleted"){
                $(row).find("li[name='associate']").hide();
                $(row).find("li[name='disassociate']").hide();
                $(row).find("li[name='remove']").hide();
            }else{
                $(row).find("li[name='associate']").hide();
                $(row).find("li[name='disassociate']").hide();
            }
             $(row).find("input[name='statusV']").val(status);
             $(row).find("span[name='status']").text(statusDisplay);
        }
    }
    </script>
    
</div>
</body>
</html>