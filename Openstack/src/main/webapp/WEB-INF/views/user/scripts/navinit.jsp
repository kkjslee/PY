<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
//this is for user dashboard
$(function(){
    initUI();
    
});


function initUI() {
    
    // set up menu
    var menu=$("body").find(".menu");
    dumpMenu($(menu).children("ul").first(), $(menu).attr("type"));
    
    $("a[name='menuItemTitle']").hover(
        function () {
            $(this).animate({paddingLeft:"22px"}, {queue:false});
        },
        function () {
            $(this).animate({paddingLeft:"12px"}, {queue:false});
        }
    );

    $("a[name='menuItemTitle']").each(function(){
        $(this).bind("click", function(){
        if($(this).is(":not(.noelements)")) {    
            $("a[name='menuItemTitle']").not(this).next(".menuItem").slideUp("medium");
            $(this).next(".menuItem").slideToggle('medium');
        }
      
        var module=$(this).attr("zmlm:module");
        if(module) {
            loadModule(module);
        }
    
        return false;
    }).next(".menuItem").find("a").bind("click", function(){    
    
        $(".menuItem").find("a").removeClass("selected");
            $(this).addClass("selected");
            
            var moduleType=$(this).attr("zmlm:type");
            if("pending"==moduleType) {
                alert("Module not exist!");
                return false;
            }
            
            var modulePath=$(this).attr("zmlm:module");
            loadModule(modulePath);
      
            return false;
        });
    });
}


function dumpMenu(container, type) {
    $("<li><a id=\"menu0\" class=\"noelements\" name=\"menuItemTitle\" zmlm:module=\"modules/entry/index\">"+"<spring:message code='admin.navigation.menu.admin.index'/>"+"</a></li>").appendTo(container);
    
    var executor={
        "is_admin": function() {
            var subMenu, items;
            
            subMenu = $("<li class=\"optmenu\"><a id=\"menu1\" name=\"menuItemTitle\">"+"<spring:message code='user.profile'/>"+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
             dumpMenuItem(true, subMenu, menuli("userinfo/modules/index", "<spring:message code='user.userinfo'/>"));
            //dumpMenuItem(true, subMenu, menuli("flavor/modules/index", "<spring:message code='admin.navi.flavor'/>"));
            
        }
    }
    
    executor["is_admin"]();
    
    // hide empty menu
    var subItem=$(".menu").find(".optmenu").find(".menuItem");
    for(var i=0; i<$(subItem).length; i++) {
        if($(subItem[i]).children().length==0) {
            $(subItem[i]).parents("li").first().hide();
        }
    }
    
}

function menuli(module, label, type) {
    if(null!=type)
        return '<li><a href="#" zmlm:module="'+module+'" zmlm:type="'+type+'">'+label+'</a></li>';
    else
        return '<li><a href="#" zmlm:module="'+module+'">'+label+'</a></li>';
}

function dumpMenuItem(serv, parent, htmlText) {
    if(serv) {
        $(htmlText).appendTo(parent);
    }
}



function loadModule(modulePath) {
    var oldModulePath=$("#contentFrame").attr("src");
  
    $(".prepare").show();

  $(".right").empty()
    $(".right").load("${pageContext.request.contextPath}/admin/"+modulePath,function(response,status,xhr){
        if(status == "success"){
            $(".prepare").hide();
        }else{
        
        }
    })
}

function fixSize() {
  
    var height=$(window).height();
    var width=$(window).width()-$(".left").outerWidth();

    $("#contentFrame").height(height);
    $("#contentFrame").width(width);
}
