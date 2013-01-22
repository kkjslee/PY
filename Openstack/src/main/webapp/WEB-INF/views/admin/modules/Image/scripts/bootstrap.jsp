<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Server="<%=request.getContextPath()%>/admin/image";
//init pager data from boostrap controller
var pageIndex = ${requestScope.pageIndex};
var pageSize = ${requestScope.pageSize};
var pageTotal = 0;
$(function(){
    //load html template if necessary
     registerTemplate();
    
    setup();

    initUI();
});

function registerTemplate() {
   $.template("imageDetails", Template_ImageDetails);
}

function setup() {
    window.document.title = '<spring:message code="admin.image.title"/>';
    loadImages(pageIndex, pageSize);
}
function loadImages(pageIndex, pageSize) {
    var tableBodyContainer=$(".dataTable").find("tbody").empty();
    $("<span class='loadingTips'>"+"<spring:message code='message.loading.data'/>"+"</span>").appendTo(tableBodyContainer);
    $.ajax({
        type: "POST",
        dataType: "html",
        cache: false,
        url: Server + "/getPagerAllImageList",  
        data: {
            pageIndex: pageIndex,
            pageSize: pageSize
        },
        success: function(data) {
             $(tableBodyContainer).html(data);
             var pageTotal = $("#pageTotal").val();
             $(".pagination").pagination(pageTotal, {
                callback: pageCallback,
                prev_text: '<spring:message code="pager.previous"/>',    
                next_text: '<spring:message code="pager.next"/>', 
                items_per_page: pageSize,
                num_display_entries: 6,
                //count from 0
                current_page: pageIndex,
                num_edge_entries: 2
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("<span class='loadingError'>"+"<spring:message code='message.loading.data.error'/>"+"</span>").appendTo($(tableBodyContainer).empty());
        }
    });
    
}

function pageCallback(index,jq){
    pageIndex = index;
    loadImages(index, pageSize);
}

function showImageDetails(which){
    //remove old elements
    if($("#imageDetails").length > 0){
        $("#imageDetails").remove();
    }
    var row = $(which).parents(".dataRow").first();
    var imgId = $(row).find("input[isos='imgId']").val();
    var imgName =  $(row).find("input[isos='imgName']").val();
    var createdTime =  $(row).find("input[isos='createdTime']").val();
    var updatedTime =  $(row).find("input[isos='updatedTime']").val();
    var statusDisplay =  $(row).find("input[isos='statusDisplay']").val();
    var user =  $(row).find("input[isos='user']").val();
    var minRam =  $(row).find("input[isos='minRam']").val();
    var minDisk =  $(row).find("input[isos='minDisk']").val();
    var tenant =  $(row).find("input[isos='tenant']").val();
    var progress =  $(row).find("input[isos='progress']").val();
    var imageDetails = $.tmpl("imageDetails", [{
        id: "imageDetails"
    }]).appendTo("#mainBody");

    imageDetails = $(imageDetails).dialog({
        title: '<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span><spring:message code="image.details.title"/>',
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [
        {
            text: '<spring:message code="confirm.button"/>',
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
    //set former data
    $(imageDetails).find("span[isos='imgName']").text(imgName);
    $(imageDetails).find("span[isos='user']").text(user);
	$(imageDetails).find("span[isos='statusDisplay']").text(statusDisplay);
	$(imageDetails).find("span[isos='minRam']").text(minRam);
	$(imageDetails).find("span[isos='minDisk']").text(minDisk);
	$(imageDetails).find("span[isos='createdTime']").text(formatDate(createdTime));
	$(imageDetails).find("span[isos='updatedTime']").text(formatDate(updatedTime));
    $(imageDetails).dialog("open");
}


function initUI(){
     $( ".button").button();
}