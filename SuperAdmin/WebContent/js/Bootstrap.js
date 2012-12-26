// JavaScript Document
// Author: Bill, 2011
// <!--#config timefmt="%A %B %d, %Y %H:%M:%S" -->
// This file last modified <!--#echo var="LAST_MODIFIED" -->

$(function(){
	//authorize();
	
	$.ajax({
		url: Server+"/LicenseManager/LicenseManager",
		type: "POST",
		cache: false,
		data: {
			methodtype: "checklicense"
		},
		success: function(data) {
			// store license to cookie
			if(null!=data) {
				$.cookie("__l", Base64.encode(data), {path:"/"});
			}
			
			// parsing data
			data=$.parseJSON(data);
			window.console && window.console.log("privatekey is "+data.privatekey);
			
			var map={};
			if(null==data.privatekey || ""==data.privatekey 
					|| "invalid_private_key"==data.privatekey 
					|| "private_key_missing"==data.privatekey
					|| "ignore"==data.privatekey) {
						
				window.console && window.console.log("go to debut page due to invalid key");
				
				// goto the license page
				window.top.location="/SuperAdmin/debut.html";
				
			}else {
				// private key is valid, constructs the navigator
				authorize();
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("LicenseManager: "+textStatus);
			window.console && window.console.log("LicenseManager: "+textStatus+", "+errorThrown);
		}
	});
});

// only debug purpose
function __info() {
	try{
    	var license=Base64.decode($.cookie("__l"));    	
		license=$.parseJSON(license);
		
		var list=[];
		var components=license.publickey.components;
		for(var i in components) {
			for(var key in components[i]) {
				list.push(key);
			}
		}
		window.console && window.console.log("license: "+list.join(","));
	}catch(e) { window.console && window.console.log("no license info");}
	
	return;
}

function authorize() {
	var block=$("<div style='position:absolute;left:0;top:0;z-index:999;background:#CCC;width:100%;height:100%;'></div>").appendTo("body");
	
	if(null==getUsername() || ""==getUsername() || null==getPassword() || ""==getPassword()) {
		window.top.location.href="modules/Login/login.html";
	}else {/* load page */
		$(block).remove();
				
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
		
		// process hash
		if(location.hash) {
			var mod=location.hash.substring(1);
			$("a[zmlm\\\:module='"+mod+"']").triggerHandler("click");
		}
	}	
}

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

function expandMenuItems(subMenu, items) {
	for(var i=0; i<items.length; i++) {
		var item=items[i];
		dumpMenuItem(item.license?hasModule(item.license):true, subMenu, menuli(item.entry, Locale[item.name]));
	}
}

function dumpMenu(container, type) {
	$("<li><a id=\"menu0\" class=\"noelements\" name=\"menuItemTitle\" zmlm:module=\"modules/Entry/index.html\">"+Locale["navigation.menu.admin.index"]+"</a></li>").appendTo(container);
	
	var adminrole=$.cookie("adminrole",{path:"/"});
	
	var executor={
		"is_admin": function() {
			var subMenu, items;
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu1\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.resource"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu1&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu2\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.stock"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu2&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu8\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.image"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			dumpMenuItem(hasModule("ApplicationPublishManager"), subMenu, menuli("modules/VMImage/index.html", Locale["navigation.menu.admin.image.manager"]));
			dumpMenuItem(hasModule("ApplicationPublishManager"), subMenu, menuli("modules/VMCharge/index.html", Locale["navigation.menu.admin.image.price"]));
			dumpMenuItem(hasModule("ApplicationPublishManager"), subMenu, menuli("modules/AppMgr/index.html", Locale["navigation.menu.admin.image.appmgr"]));
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu7\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.apply"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu7&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu3\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.apptemplate"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu3&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu4\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.project"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu4&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu9\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.user"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			dumpMenuItem(true, subMenu, menuli("modules/UserInfo/index.html", Locale["navigation.menu.admin.user.account"]));
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu5\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.parameters"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu5&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			//dumpMenuItem(true, subMenu, menuli("modules/Config/personal.html", Locale["navigation.menu.admin.parameters.personal"]));
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu10\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.reslog"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu10&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu6\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.logs"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu6&role=is_admin"-->');
			expandMenuItems(subMenu, items);
			
			$("<li><a id=\"menu7\" class=\"logout\">"+Locale["navigation.menu.admin.signout"]+"</a></li>").appendTo(container);
		},
		"is_2ndadmin": function() {
			var subMenu, items;
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu1\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.resource"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu1&role=is_2ndadmin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu2\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.stock"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu2&role=is_2ndadmin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu8\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.image"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu3\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.apptemplate"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu3&role=is_2ndadmin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu4\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.project"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu4&role=is_2ndadmin"-->');
			expandMenuItems(subMenu, items);
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu9\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.user"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			dumpMenuItem(true, subMenu, menuli("modules/UserInfo/index.html", Locale["navigation.menu.admin.user.account"]));
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu5\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.parameters"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu5&role=is_2ndadmin"-->');
			expandMenuItems(subMenu, items);
			//dumpMenuItem(true, subMenu, menuli("modules/Config/upgrade.html", Locale["navigation.menu.admin.parameters.personal"]));
			
			subMenu=$("<li class=\"optmenu\"><a id=\"menu6\" name=\"menuItemTitle\">"+Locale["navigation.menu.admin.logs"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
			items=$.parseJSON('<!--#include virtual="../navigator.jsp?parent=menu6&role=is_2ndadmin"-->');
			expandMenuItems(subMenu, items);
			
			$("<li><a id=\"menu7\" class=\"logout\">"+Locale["navigation.menu.admin.signout"]+"</a></li>").appendTo(container);
		}
	}
	
	executor[adminrole] && executor[adminrole]();
	
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

	var view=$("<iframe id='contentFrame' style='height:100%;display:none;border:0;overflow:auto;border-collapse:collapse;' frameborder=0 src='"+modulePath+"' scrolling='auto'></iframe>").appendTo($(".right").empty());
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

	/*
	var menuMaxY=$(".menu").offset().top+$(".menu").height();
	if(menuMaxY>$(window).height()) {
		if($("#downScroller").length==0) {
			$(".menu").append("<span id='downScroller' style='position:fixed;bottom:0;text-align:center;background:red;display:block;'>123</span>");
		}
		console.log("1");
	}else {
		$("#downScroller").remove();
		console.log("2");
	}
	*/
}

