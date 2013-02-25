<%--
property			value				comment
.content			html元素的 id		分页元素将在此元素中显示
.pagination			html元素的id			列表将在此元素中显示
.pageIndex			int					当前页
.pageSize			int					每页显示数
.url				url					通过此列表获取列表

例：
	<c:url value='/admin/instance/getPagerInstanceList' var="paginationUrl"/>
	<jsp:useBean id="pageMap" class="java.util.HashMap" scope="request" />
	<c:set target="${pageMap}" property=".content" value="content"/>
	<c:set target="${pageMap}" property=".pageIndex" value="0"/>
	<c:set target="${pageMap}" property=".pageSize" value="20"/>
	<c:set target="${pageMap}" property=".pagination" value="pagination"/>
	<c:set target="${pageMap}" property=".url" value="${paginationUrl}"/>
	<jsp:include page="/WEB-INF/views/templates/pagination.jsp" >
		<jsp:param name="pagination.configuration" value="pageMap"/>
	</jsp:include>
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set value="${requestScope[param['pagination.configuration']]}" var="conf" />
<c:if test="${conf['.content'] == null}">
	<c:set target="${conf}" property=".content" value="content"></c:set>
</c:if>
<c:if test="${conf['.pagination'] == null}">
	<c:set target="${conf}" property=".pagination" value="pagination"></c:set>
</c:if>
<c:if test="${conf['.pageIndex'] == null}">
	<c:set target="${conf}" property=".pageIndex" value="0"></c:set>
</c:if>
<c:if test="${conf['.pageSize'] == null}">
	<c:set target="${conf}" property=".pageSize" value="10"></c:set>
</c:if>
<!-- <div class="dataTable" id="${conf['.content']}"></div>
<div class="pagination" id="${conf['.pagination']}"></div> -->
<table class="dataTable" id="${conf['.content']}">
</table>
<script>

var g_pageIndex = <c:out value='${conf[".pageIndex"]}' />;
var g_pageSize = <c:out value='${conf[".pageSize"]}' />;

$(function(){
	g_loadPagerDataList(g_pageIndex, g_pageSize);
});

function g_loadPagerDataList(pageIndex, pageSize) {
    var target=$("#${conf['.content']}").empty();
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
            try{
				data = $.parseJSON(data);
            }catch(e){}
            var result = getResult(data);
        	if(isSuccess(data)){
        		 target.html(result.html);
        		 $(target).append("<tfoot><tr class='footerRow'><td class='fpager' colspan='" + ${conf[".colspanLeft"]} + "'></td><td colspan='" + ${conf[".colspanRight"]} + "' class='fbuttons'></td></tr></tfoot>");
                 $('#${conf[".content"]} .fpager').pagination(result.recordTotal, {
                    callback: g_pageCallback,
                    prev_text: '<spring:message code="pager.previous"/>',    
                    next_text: '<spring:message code="pager.next"/>', 
                    items_per_page: pageSize,
                    num_display_entries: 6,
                    //count from 0
                    current_page: pageIndex,
                    num_edge_entries: 2
                });
                <c:if test="${conf['.loadSuccessCall'] != null}">
                 ${conf['.loadSuccessCall']}();
                 </c:if>
            }else{
            	$("<span class='loadingError'>"+result+"</span>").appendTo(target.empty());
            	 <c:if test="${conf['.loadErrorCall'] != null}">
                 ${conf['.loadErrorCall']}();
                 </c:if>
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("<span class='loadingError'><spring:message code='message.loading.data.error'/></span>").appendTo(target.empty());
        }
    });
    
}

function g_pageCallback(index,jq){
	g_pageIndex = index;
	g_loadPagerDataList(index, g_pageSize);
}

</script>