<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Server="<%=request.getContextPath()%>/admin/category";
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
   $.template("createCategory", Template_CreateCategory);
   $.template("editCategory", Template_EditCategory);
}

function setup() {
    window.document.title = '<spring:message code="admin.category.title"/>';
    loadAllCategories(pageIndex, pageSize);
}
//server返回的数据html,应该为tbody中的内容，父层结构：<div id="mainBody"><table class="dataTable table  table-striped table-hover"><thead></thead><tbody>json返回的内容<tbody><tfoot></tfoot></table></div>
function loadAllCategories(pageIndex, pageSize) {
    var tableBodyContainer=$(".dataTable").find("tbody").empty();
    $("<span class='loadingTips'>"+"<spring:message code='message.loading.data'/>"+"</span>").appendTo(tableBodyContainer);
    $.ajax({
        type: "POST",
        dataType: "html",
        cache: false,
        url: Server + "/listForJsp",  
        data: {
            excludeDisabled:false,
            withItems:true,
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
	loadAllCategories(index, pageSize);
}

function showCreatCategory(){
    //remove old elements
    if($("#createCategory").length > 0){
        $("#createCategory").remove();
    }
    var createCategory = $.tmpl("createCategory", [{
        id: "createCategory"
    }]).appendTo("#mainBody");

    createCategory = $(createCategory).dialog({
        title: "<spring:message code="category.create.title"/>",
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: '<spring:message code="confirm.button"/>',
            click: function() {
               var jsonData = {};
               var nameArray = [];
               $(this).find(".i18n").each(function(){
                    var data = {};
                    var languageId = $(this).find(".isos_id").val();
                    data["languageId"]=languageId;
                    var content = $(this).find(".isos_content").val();
                    data["content"] = content;
                    nameArray.push(data);
               });
               jsonData["name"] = nameArray;
                var enable = $(this).find("select[isos='enable']").val();
                 /* if(!jQuery.checkstr(name,"vmname")) {
                     printMessage("<spring:message code='vmname.check'/>");
                    return false;
                 } */
                 jsonData["enable"] = enable;
                var jsonString = $.toJSON(jsonData);
                createCategoryItem(jsonString);
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
    $("select").selectmenu();
    $(createCategory).dialog("open");
   }




function createCategoryItem(jsonString) {
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/create",
        type: "POST",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        data: jsonString,
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {

                if(data.success){
                    printMessage('<spring:message code="category.create.success"/>');
                    loadAllCategories(pageIndex, pageSize);
                }
                if(data.error){
                    printMessage(data.error);
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("close");
            printError(jqXHR, textStatus, errorThrown);
            return false;
        }
    });
}
function showEditCategory(which){
    //remove old elements
    if($("#editCategory").length > 0){
        $("#editCategory").remove();
    }
    var row = $(which).parents(".dataRow").first();
    var id = $(row).find("input[isos='id']").val();
    
    var enable =  $(row).find("input[isos='enable']").val();
    var editCategory = $.tmpl("editCategory", [{
        id: "editCategory"
    }]).appendTo("#mainBody");

    editCategory = $(editCategory).dialog({
        title: '</span><spring:message code="category.edit.title"/>',
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: '<spring:message code="confirm.button"/>',
            click: function() {
               var jsonData = {};
               var nameArray = [];
               $(this).find(".i18n").each(function(){
                    var data = {};
                    var languageId = $(this).find(".isos_id").val();
                    data["languageId"]=languageId;
                    var content = $(this).find(".isos_content").val();
                    data["content"] = content;
                    nameArray.push(data);
               });
               jsonData["name"] = nameArray;
                var enable = $(this).find("select[isos='enable']").val();
                 /* if(!jQuery.checkstr(name,"vmname")) {
                     printMessage("<spring:message code='vmname.check'/>");
                    return false;
                 } */
                 jsonData["enable"] = enable;
                 jsonData["id"] = id;
                var jsonString = $.toJSON(jsonData);
                
               updateCategoryItem(jsonString);
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
    //set former data
    $(row).find(".langId").each(function(){
        var langId = $(this).attr("lang_isos_id");
        var content = $(this).val();
        $(editCategory).find(".edit_content").each(function(){
            if($(this).attr("isos_lang_id") == langId){
                $(this).val(content);
            }
        });
    });
    $(editCategory).find("input[isos='enable']").val(enable);
    $("select").selectmenu();
    $(editCategory).dialog("open");
}



function updateCategoryItem(jsonString) {
 
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/update",
        type: "POST",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        data: jsonString,
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {

                if(data.success){
                    printMessage('<spring:message code="update.success"/>');
                    loadAllCategories(pageIndex, pageSize);
                }
                if(data.error){
                    printMessage('<spring:message code="update.failed"/>');
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("close");
            printError(jqXHR, textStatus, errorThrown);
            return false;
        }
    });
}

function showRemoveCategory(which){
    var id=$(which).parents(".dataRow").first().find("input[isos='id']").val();
    
    if(!confirm("<spring:message code='remove.confirm'/>")) return;
    
    var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server+"/remove",
        cache: false,
        data: {
            categoryId: id
        },
        success: function(data) {
            pd.dialog("destroy");
            try{
                var msg="";
               if(data.success){
                    msg="<spring:message code='remove.success'/>";
                }else if(data.error){
                   msg="<spring:message code='remove.failed'/>";
               } 
                
                printMessage(msg);
                loadAllCategories(pageIndex, pageSize);
                
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
function initUI(){
	 $( ".button").button();
}