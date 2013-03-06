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
    function showDetachorAttachVolume(command,openDesc, volumeId, serverId,which){
    	jConfirm("<spring:message code='volume.detachorattch.tip'/>".sprintf(openDesc),function(){
            ctrlVolume(command,volumeId, serverId, which);
        });
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
                        case "done": msg="<spring:message code='operation.success'/>";
                        break;
                        case "error": ;
                        case "exception": msg="<spring:message code='operation.failed'/>";break;
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
    
    function updateVolumeDisplay(){
    	$("tbody").find("tr").each(function(e){
            var statusV = $(this).find("input[name='statusV']").val();
            if(statusV == "in-use"){
                $(this).find("li[name='attach']").hide();
            }
            if(statusV == "available"){
                $(this).find("li[name='detach']").hide();
            }
        })
    }
    </script>
    
</div>
</body>
</html>