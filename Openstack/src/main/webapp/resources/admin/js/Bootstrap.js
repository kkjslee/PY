// JavaScript Document
// Author: Bill, 2011
// <!--#config timefmt="%A %B %d, %Y %H:%M:%S" -->
// This file last modified <!--#echo var="LAST_MODIFIED" -->

$(function(){
	initUI();
	  
	$(window).bind("resize", fixSize); // we should resize it while the window is resizing 
  
	$("a[zmlm\\\:module][zmlm\\\:type='flex']").each(function(){
		//$(this).attr("zmlm:module", $(this).attr("zmlm:module")+"?"+["user="+getUsername(), "role=is_user", "username="+getUsername(),].join("&"));
	});
  
	// ok, let's expand it acquiescently
	$("#menu1").trigger("click");
  
	// and then open default page
	$("#menu0").trigger("click");
	
	// register logout button
	$(".menu").delegate(".logout", "click", function(){
		signout();
	});
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
	$("<li><a id=\"menu0\" class=\"noelements\" name=\"menuItemTitle\" zmlm:module=\"modules/Entry/index.jsp\">"+Locale["navigation.menu.admin.index"]+"</a></li>").appendTo(container);
	
	var executor={
		"is_admin": function() {
			var subMenu, items;
			
			$("<li class=\"optmenu\"><a id=\"menu1\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.resource"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li class=\"optmenu\"><a id=\"menu2\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.stock"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li class=\"optmenu\"><a id=\"menu8\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.image"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			dumpMenuItem(true, subMenu, menuli("modules/VMImage/index.jsp", Locale["navigation.menu.admin.image.manager"]));
			dumpMenuItem(true, subMenu, menuli("modules/VMCharge/index.jsp", Locale["navigation.menu.admin.image.price"]));
			dumpMenuItem(true, subMenu, menuli("modules/AppMgr/index.jsp", Locale["navigation.menu.admin.image.appmgr"]));
			
			$("<li class=\"optmenu\"><a id=\"menu7\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.apply"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li class=\"optmenu\"><a id=\"menu3\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.apptemplate"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li class=\"optmenu\"><a id=\"menu4\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.project"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li class=\"optmenu\"><a id=\"menu9\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.user"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			dumpMenuItem(true, subMenu, menuli("modules/UserInfo/index.jsp", Locale["navigation.menu.admin.user.account"]));
			
			$("<li class=\"optmenu\"><a id=\"menu5\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.parameters"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li class=\"optmenu\"><a id=\"menu10\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.reslog"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li class=\"optmenu\"><a id=\"menu6\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.logs"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			$("<li><a id=\"menu7\" class=\"logout\">"+Locale["navigation.menu.admin.signout"]+"</a></li>").appendTo(container);
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
  
	//if(!modulePath || modulePath==oldModulePath) return;
	$(".prepare").show();

	var view=$("<iframe id='contentFrame' style='height:100%;display:none;border:0;overflow:auto;border-collapse:collapse;' frameborder=0 src='admin/"+modulePath+"' scrolling='auto'></iframe>").appendTo($(".right").empty());
	$(view).bind("load", function(){
		fixSize();
		$(view).css("display","block");
		$(".prepare").hide();
	});
  
}

function fixSize() {
  
	var height=$(window).height();
	var width=$(window).width()-$(".left").outerWidth();

	$("#contentFrame").height(height);
	$("#contentFrame").width(width);
}

