// JavaScript Document
// Author: Bill, 2011

$(function(){
	$(window).bind("resize", fixSize);
	
	getComponentList();
});

function getComponentList() {
	$.ajax({
		url: Server+"/LicenseManager/LicenseManager",
		type: "POST",
		cache: false,
		data: {
			methodtype: "checklicense"
		},
		success: function(data) {
			var menu=$("body").find(".menu");
			
			try {
				// store license to cookie
				if(null!=data) {
					$.cookie("__l", Base64.encode(data), {path:"/"});
				}
				
				// parsing data
				data=$.parseJSON(data);
				//window.console.log("privatekey is "+data.privatekey);
				
				var map={};
				if(null==data.privatekey || ""==data.privatekey 
						|| "invalid_private_key"==data.privatekey 
						|| "private_key_missing"==data.privatekey
						|| "ignore"==data.privatekey) {
							
					alert(Locale["navigation.message.invalid_license"]);
					
					// goto the license page
					window.top.location="/SuperAdmin/debut.html";
					
				}else {
					dumpMenu($(menu).children("ul").first(), $(menu).attr("type"));
					
					// -------------- init -------------
					initUI();
  
					$("a[zmlm\\\:module][zmlm\\\:type='flex']").each(function(){
						$(this).attr("zmlm:module", $(this).attr("zmlm:module")+"?"+["user="+getUsername(), "role=is_user", "username="+getUsername(),].join("&"));
					});
				  
					// ok, let's expand it acquiescently
					$("#menu1").trigger("click");
				  
					// and then open default page
					$("#menu0").trigger("click");
					
					$(".menu").delegate(".logout", "click", function(){
						signout();
					});
				}
			}catch(e) {
				alert(Locale["navigation.message.loading_failed"]);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert(Locale["navigation.message.loading_failed"]);
		}
	});
}

function dumpMenu(container, type) {
	if(type=="junior") {
		$("<li><a id=\"menu0\" class=\"noelements\" name=\"menuItemTitle\" zmlm:module=\"modules/Entry/junior.html\">"+Locale["navigation.menu.junior.index"]+"</a></li>").appendTo(container);
		
		var subMenu=$("<li class=\"optmenu\"><a id=\"menu1\" name=\"menuItemTitle\">"+Locale["navigation.menu.junior.myproduct"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
		dumpMenuItem(hasModule("vmmanagement"), subMenu, menuli("modules/Instance/index.jsp", Locale["navigation.menu.junior.vmman"]));
		dumpMenuItem(hasModule("ipmanagement"), subMenu, menuli("modules/Network/index.jsp", Locale["navigation.menu.junior.ipman"]));
		dumpMenuItem(hasModule("volumemanagement"), subMenu, menuli("modules/Volume/index.jsp", Locale["navigation.menu.junior.diskman"]));
		
		$("<li><a id=\"menu3\" class=\"logout\">"+Locale["navigation.menu.junior.signout"]+"</a></li>").appendTo(container);
		
	}else if(type=="senior") {
		$("<li><a id=\"menu0\" class=\"noelements\" name=\"menuItemTitle\" zmlm:module=\"modules/Entry/senior.html\">"+Locale["navigation.menu.junior.index"]+"</a></li>").appendTo(container);
		
		var subMenu;
		subMenu=$("<li class=\"optmenu\"><a id=\"menu1\" name=\"menuItemTitle\">"+Locale["navigation.menu.senior.buyproduct"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
		dumpMenuItem(hasModule("vmmanagement"), subMenu, menuli("modules/OrderTemplate/index.jsp", Locale["navigation.menu.senior.buyvm"]));
		dumpMenuItem(hasModule("ipmanagement"), subMenu, menuli("modules/Legacy/ipaddr.html", Locale["navigation.menu.senior.buyip"]));
		dumpMenuItem(hasModule("volumemanagement"), subMenu, menuli("modules/Legacy/volume.html", Locale["navigation.menu.senior.buydisk"]));
		dumpMenuItem(hasModule("apptemplatemanagement"), subMenu, menuli("modules/Bedivere/index.jsp", Locale["navigation.menu.senior.buytemplate"]));
		dumpMenuItem(hasModule("projectmanagement"), subMenu, menuli("modules/Project/index.jsp", Locale["navigation.menu.senior.buyproject"]));
		
		subMenu=$("<li class=\"optmenu\"><a id=\"menu2\" name=\"menuItemTitle\">"+Locale["navigation.menu.senior.myproduct"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
		dumpMenuItem(hasModule("vmmanagement"), subMenu, menuli("modules/Instance/index.jsp", Locale["navigation.menu.senior.vmman"]));
		dumpMenuItem(hasModule("ipmanagement"), subMenu, menuli("modules/Network/index.jsp", Locale["navigation.menu.senior.ipman"]));
		dumpMenuItem(hasModule("volumemanagement"), subMenu, menuli("modules/Volume/index.jsp", Locale["navigation.menu.senior.diskman"]));
		dumpMenuItem(hasModule("vlanmanagement"), subMenu, menuli("modules/VLan/index.jsp", Locale["navigation.menu.senior.vlanman"]));
		dumpMenuItem(hasModule("groupmanagement"), subMenu, menuli("modules/Group/index.jsp", Locale["navigation.menu.senior.groupman"]));
		// dumpMenuItem(hasModule("groupmanagement"), subMenu, menuli("/flex_enterprise/MyGroup.html", Locale["navigation.menu.senior.groupman"], "flex"));
		dumpMenuItem(hasModule("backup"), subMenu, menuli("modules/Backup/index.jsp", Locale["navigation.menu.senior.backup"]));
		dumpMenuItem(hasModule("apptemplatemanagement"), subMenu, menuli("modules/Bedivere/index.jsp?action=instance", Locale["navigation.menu.senior.templateman"]));
		dumpMenuItem(hasModule("projectmanagement"), subMenu, menuli("modules/Project/index.jsp?action=manage", Locale["navigation.menu.senior.projectman"]));
		dumpMenuItem(true, subMenu, menuli("modules/Monitor/index.jsp", Locale["navigation.menu.senior.monitor"]));
		dumpMenuItem(true, subMenu, menuli("modules/Elastic/index.jsp", Locale["navigation.menu.senior.elastic"]));
		
		subMenu=$("<li class=\"optmenu\"><a id=\"menu4\" name=\"menuItemTitle\">"+Locale["navigation.menu.senior.myrequest"]+"</a><ul class=\"menuItem\"></ul></li>").appendTo(container).children(".menuItem");
		dumpMenuItem(hasModule("requestmanagement"), subMenu, menuli("modules/MyNetReqMan/index.jsp", Locale["navigation.menu.senior.mynetreqman"]));
		dumpMenuItem(hasModule("requestmanagement"), subMenu, menuli("modules/VMReqClient/index.jsp", Locale["navigation.menu.senior.vmreqclient"]));
		dumpMenuItem(hasModule("requestmanagement"), subMenu, menuli("modules/VolumeReqClient/index.jsp", Locale["navigation.menu.senior.volumereqclient"]));
		//dumpMenuItem(true, subMenu, menuli("", Locale["navigation.menu.senior.ipreqclient"]));
		
		$("<li><a id=\"menu3\" class=\"logout\">"+Locale["navigation.menu.senior.signout"]+"</a></li>").appendTo(container);
		
		// hide empty menu
		var subItem=$(".menu").find(".optmenu").find(".menuItem");
		for(var i=0; i<$(subItem).length; i++) {
			if($(subItem[i]).children().length==0) {
				$(subItem[i]).parents("li").first().hide();
			}
		}
		
	}
}

function menuli(module, label, type) {
	if(null!=type)
		return "<li><a href=\"#\" zmlm:module=\""+module+"\" zmlm:type=\""+type+"\">"+label+"</a></li>";
	else
		return "<li><a href=\"#\" zmlm:module=\""+module+"\">"+label+"</a></li>";
}

function dumpMenuItem(serv, parent, htmlText) {
	if(serv) {
		$(htmlText).appendTo(parent);
	}
}

function initUI() {
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
				alert(Locale["navigation.message.invalid_module"]);
				return false;
			}
			
			var modulePath=$(this).attr("zmlm:module");
			loadModule(modulePath);
      
			return false;
		});
	});
}

function loadModule(modulePath) {
	var oldModulePath=$("#contentFrame").attr("src");
  
	//if(!modulePath || modulePath==oldModulePath) return;

	var view=$("<iframe id='contentFrame' style='height:100%;width:100%;border:0;overflow:auto;' frameborder=0 src='normalUser/"+modulePath+"' scrolling='auto' onload='fixSize()'></iframe>").appendTo($(".right").empty());
  
}

function fixSize() {
  
	var height=$(window).height();
	var width=$(window).width()-$(".left").outerWidth();
  
  
	$("#contentFrame").height(height);
	$("#contentFrame").width(width);
  
}

