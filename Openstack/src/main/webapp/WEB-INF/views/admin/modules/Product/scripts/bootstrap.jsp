<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var Server="<%=request.getContextPath()%>/admin/product";
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
   $.template("createProduct", Template_CreateProduct);
   $.template("editProduct", Template_EditProduct);
   $.template("createImgOption",  Template_ImgModelOption);
   $.template("createFlavorOption",  Template_FlavorModelOption);
   $.template("editPrice",  Template_EditPrice);
}

function setup() {
    window.document.title = '<spring:message code="admin.product.title"/>';
    loadProducts(pageIndex, pageSize);
}
//server返回的数据html,应该为tbody中的内容，父层结构：<div id="mainBody"><table class="dataTable"><thead></thead><tbody>json返回的内容<tbody><tfoot></tfoot></table></div>
function loadProducts(pageIndex, pageSize) {
    var tableBodyContainer=$(".dataTable").find("tbody").empty();
    $("<span class='loadingTips'>"+"<spring:message code='message.loading.data'/>"+"</span>").appendTo(tableBodyContainer);
    $.ajax({
        type: "POST",
        dataType: "html",
        cache: false,
        url: Server + "/listForJsp",  
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
	loadProducts(index, pageSize);
}

function showCreatProduct(){
    //remove old elements
    if($("#createProduct").length > 0){
        $("#createProduct").remove();
    }
    var createProduct = $.tmpl("createProduct", [{
        id: "createProduct"
    }]).appendTo("#mainBody");

    createProduct = $(createProduct).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span><spring:message code="product.create.title"/>",
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
                var available = $(this).find("select[isos='available']").val();
                 /* if(!jQuery.checkstr(name,"vmname")) {
                     printMessage("<spring:message code='vmname.check'/>");
                    return false;
                 } */
                 jsonData["available"] = available;
                 //ostype
                 var osType = $(this).find("select[id='osType']").val();
                 if(osType != -1){
                    jsonData["osType"] = osType
                 }
                 var refId = $(this).find("select[id='refId']").val();
                 if(!isNull(refId)){
                    jsonData["refId"] = refId;
                 }
                  var defaultPrice = $(this).find("input[isos='defaultPrice']").val();
                 if(!isNull(defaultPrice)){
                    jsonData["defaultPrice"] = defaultPrice;
                 }
                 
                var jsonString = $.toJSON(jsonData);
                createProductItem(jsonString);
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
    bindOSTypeSelect();
    $(createProduct).dialog("open");
   }




function createProductItem(jsonString) {
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
                    printMessage('<spring:message code="product.create.success"/>');
                    loadProducts(pageIndex, pageSize);
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

function showEditPrice(which){
    //remove old elements
    if($("#editPrice").length > 0){
        $("#editPrice").remove();
    }
    var row = $(which).parents(".dataRow").first();
    var id = $(row).find("input[isos='id']").val();
    
    var defaultPrice =  $(row).find("input[isos='defaultPrice']").val();
    var editPrice = $.tmpl("editPrice", [{
        id: "editPrice"
    }]).appendTo("#mainBody");

    editPrice = $(editPrice).dialog({
        title: '<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span><spring:message code="product.edit.title"/>',
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
               var defaultPrice = $(this).find("input[isos='defaultPrice']").val();
               if(!isNull(defaultPrice)){
                  jsonData["defaultPrice"] = defaultPrice;
               }
               jsonData["itemSpecificationId"] = id;
               var jsonString = $.toJSON(jsonData);
                
               updateProductPrice(jsonString);
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
    $(editPrice).find("input[isos='defaultPrice']").val(defaultPrice);
    $(editPrice).dialog("open");
}
function showEditProduct(which){
    //remove old elements
    if($("#editProduct").length > 0){
        $("#editProduct").remove();
    }
    var row = $(which).parents(".dataRow").first();
    var id = $(row).find("input[isos='id']").val();
    
    var available =  $(row).find("input[isos='available']").val();
    var editProduct = $.tmpl("editProduct", [{
        id: "editProduct"
    }]).appendTo("#mainBody");

    editProduct = $(editProduct).dialog({
        title: '<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span><spring:message code="product.edit.title"/>',
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
                var available = $(this).find("select[isos='available']").val();
                 /* if(!jQuery.checkstr(name,"vmname")) {
                     printMessage("<spring:message code='vmname.check'/>");
                    return false;
                 } */
                 jsonData["available"] = available;
                 jsonData["id"] = id;
                var jsonString = $.toJSON(jsonData);
                
               updateProductItem(jsonString);
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
        $(editProduct).find(".edit_content").each(function(){
            if($(this).attr("isos_lang_id") == langId){
                $(this).val(content);
            }
        });
    });
    $(editProduct).find("input[isos='available']").val(available);
    $("select").selectmenu();
    $(editProduct).dialog("open");
}

//dataInit("<%=request.getContextPath()%>/admin" + "/image/imgList","selImageModel","createImgOption");
//    dataInit("<%=request.getContextPath()%>/admin" + "/flavor/flavorList","selFlavorModel","createFlavorOption");
//dataInit(url,"typeInfo");
//now get images and flavors if necessary
function getOSTyeList(url,selectId,optionModel){
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        dataType:"json",
        success: function(data) {
            try{
               $("#" + selectId).empty();
               $("#" + selectId).append('<option value="-1" selected><spring:message code="choose.label"/></option>');
               if (!data || data.length == 0) {
                   
               }else{
                    $.tmpl(optionModel, data).appendTo("#" + selectId);
                    $("#" + selectId).selectmenu();
                    if(optionModel == "createImgOption"){
                       bindFlavorSelect();
                    }
               }
                
            }catch(e){printMessage("Data Broken ["+e+"]");};
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}
//bind type select and get update refId  
function bindOSTypeSelect(){
    $("#osType").change(function(){
        var type = $(this).val();
        if( type == 1){
             getOSTyeList("<%=request.getContextPath()%>/admin" + "/flavor/flavorList","refId","createFlavorOption");
        }else if(type == 2){
            getOSTyeList("<%=request.getContextPath()%>/admin" + "/image/imgList","refId","createImgOption");
        }
    });
}
function bindFlavorSelect(){
    $("#refId").change(function(){
        getFlavorDetails($(this).val());
    });
}

function getFlavorDetails(flavorId){
   if(isNull(flavorId) || flavorId == "-1"){
       return;
   }
  var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
        url: "<%=request.getContextPath()%>/admin/flavor/getFlavorDetails",
        cache: false,
        data:{
            flavorId: flavorId
        },
        dataType:"json",
        success: function(data) {
            pd.dialog("destroy");
            try{
                if (!data || data.length == 0) {
                    $(".flavorDetails").hide();
                }else{
                    $(".flavorDetails").show();
                    $(".flavorDetails").find("span[isos='flavorName']").text(data.flavorName);
                    $(".flavorDetails").find("span[isos='vcpus']").text(data.vcpus);
                    $(".flavorDetails").find("span[isos='ram']").text(data.ram);
                    $(".flavorDetails").find("span[isos='disk']").text(data.disk);
                }
            }catch(e){printMessage("Data Broken ["+e+"]");};
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });


}

function updateProductItem(jsonString) {
 
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
                    loadProducts(pageIndex, pageSize);
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


function updateProductPrice(jsonString) {
 
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/editPrice",
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
                    loadProducts(pageIndex, pageSize);
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


function showRemoveProduct(which){
    var id=$(which).parents(".dataRow").first().find("input[isos='id']").val();
    var name=$(which).parents(".dataRow").first().find("input[isos='name']").val();
    
    if(!confirm("<spring:message code='remove.confirm'/>")) return;
    
    var pd=showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server+"/remove",
        cache: false,
        data: {
            productId: id
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
                loadProducts(pageIndex, pageSize);
                
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