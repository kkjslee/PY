<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="<%=request.getContextPath()%>/user/cinder/modules/template" language="javascript"></script>
</head>
<body>
<div class="banner">
    <span id="banner" ><spring:message code='user.navi.volume' /></span>
</div>

<div id="mainBody" >
    <c:url value='/user/cinder/getPagerVolumeList' var="paginationUrl"/>
    <jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
    <c:set target="${pageMap}" property=".content" value="dataTable"/>
    <c:set target="${pageMap}" property=".pageIndex" value="0"/>
    <c:set target="${pageMap}" property=".pageSize" value="10"/>
    <c:set target="${pageMap}" property=".pagination" value="pagination"/>
    <c:set target="${pageMap}" property=".loadSuccessCall" value="updateVolumeDisplay"/>
    <c:set target="${pageMap}" property=".colspanLeft" value="4"/>
    <c:set target="${pageMap}" property=".colspanRight" value="2"/>
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
    function showDetachorAttachVolume(command,openDesc, volumeId, serverId,which){
    	jConfirm("<spring:message code='volume.detachorattch.tip'/>".sprintf(openDesc),function(){
    		if(!isNull(serverId)){
    			ctrlVolume(command,volumeId, serverId, which);
    		}else{
    			showServerSelPanel(command,volumeId, which);
    		}
        });
    }
    
    function showServerSelPanel(command,volumeId, which){
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
    	            		ctrlVolume(command,volumeId, uuid,which);
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
    	 $(serverPanel).dialog("open");
    	 getInstancesWidthStatus(serverPanel);
    }
    
    function ctrlVolume(command,volumeId, serverId,which){
    	var pd=showProcessingDialog();
        $.ajax({
            type: "POST",
            url: '<c:url value="/user/cinder/volumecontrol"/>',
            cache: false,
            data: {
                executecommand: command,
                volumeId: volumeId,
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
            	excludeStatus: "deleted"
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

                        $(serverPanel).dialog("open");
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
    function updateVolumeDisplay(){
    	$("tbody").find("tr").each(function(e){
            var statusV = $(this).find("input[name='statusV']").val();
            if(statusV == "in-use"){
                $(this).find("li[name='attach']").hide();
            }else if(statusV == "available"){
                $(this).find("li[name='detach']").hide();
            }else{
            	$(this).find("li[name='attach']").hide();
            	$(this).find("li[name='detach']").hide();
            }
        })
    }
    
    var rTask ;
    function checkPageVMStatus(){
        rTask  = setInterval(refreshTaskStatus,2500);

    }
    function refreshTaskStatus(){
         var hasTask = false;
         $(".dataTable").find("input[name='statusV']").each(function(){
             hasTask = true;
             var row =  $(this).parents(".dataRow").first();
             var vmId = $(row).find("input[name='id']").val();
             getTaskStatus(row, vmId);
         });
         if(!hasTask && !isNull(rTask)){
             clearInterval(rTask);     
         }
     }

    function getTaskStatus(row,id){
        $.ajax({
            type: "POST",
            url: '<c:url value="/user/cinder/getVolumeDetail"/>',
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
                $(row).find("li[name='attach']").hide();
            }else if(status == "available"){
                $(row).find("li[name='detach']").hide();
            }else{
                $(row).find("li[name='attach']").hide();
                $(row).find("li[name='detach']").hide();
            }
        	 $(row).find("input[name='statusV']").val(status);
        	 $(row).find("span[name='status']").html(statusDisplay);
        }
       
    }
    </script>
    
</div>
</body>
</html>