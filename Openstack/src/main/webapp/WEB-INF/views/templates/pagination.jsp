<%--
property			value				comment
.container			html元素的 id		分页元素将在此元素中显示
.target				html元素的id			列表将在此元素中显示
.pageIndex			int					当前页
.pageSize			int					每页显示数
.url				url					通过此列表获取列表

例：
	<div id="form"></div>
	<div id="pagination"></div>
	
	<c:url value='/admin/instance/getPagerInstanceList' var="paginationUrl"/>
	<jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
	<c:set target="${pageMap}" property=".container" value="pagination"/>
	<c:set target="${pageMap}" property=".pageIndex" value="0"/>
	<c:set target="${pageMap}" property=".pageSize" value="20"/>
	<c:set target="${pageMap}" property=".target" value="form"/>
	<c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
	<jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
		<jsp:param name="pagination.configuration" value="pageMap"/>
	</jsp:include>
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link href="<c:url value='/resource/common/css/pagination.css'/>"  rel="stylesheet" type="text/css" />
<c:set value="${requestScope[param['pagination.configuration']]}" var="conf" />
<script src="<c:url value='/resource/common/js/jquery.pagination.js'/>" type="text/javascript" ></script>
<script src="<c:url value='/resource/common/js/common.js'/>" type="text/javascript" ></script>
<script>

var pageIndex = ${conf[".pageIndex"]};
var pageSize = ${conf[".pageSize"]};

$(function(){

	loadInstances(pageIndex, pageSize);
})

function loadInstances(pageIndex, pageSize) {
    var target=$("#${conf['.target']}").empty();
    $("<span class='loadingTips'><spring:message code='message.loading.data'/></span>").appendTo(target);
    $.ajax({
        type: "POST",
        dataType: "html",
        cache: false,
        url: "${conf['.url']}",
        data: {
            pageIndex: pageIndex,
            pageSize: pageSize
        },
        success: function(data) {
            var result = getResult(data);
        	if(isSuccess(data)){
        		 target.html(result.html);
                 $('#${conf[".container"]}').pagination(result.pageTotle, {
                    callback: pageCallback,
                    prev_text: '<spring:message code="pager.previous"/>',    
                    next_text: '<spring:message code="pager.next"/>', 
                    items_per_page: pageSize,
                    num_display_entries: 6,
                    //count from 0
                    current_page: pageIndex,
                    num_edge_entries: 2
                });
            }else{
            	$("<span class='loadingError'>"+result+"</span>").appendTo(target.empty());
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("<span class='loadingError'><spring:message code='message.loading.data.error'/></span>").appendTo(target.empty());
        }
    });
    
}

function pageCallback(index,jq){
    pageIndex = index;
	loadInstances(index, pageSize);
}

</script>