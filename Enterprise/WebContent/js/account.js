// JavaScript Document

function getUsername() {
	try{
		var ss=Base64.decode($.cookie("_ss"));
		var username=ss.substring(0, ss.indexOf(":"));
		return username;
	}catch(e) {
		return "";
	}
}

function getPassword() {
	try{
    	var ss=Base64.decode($.cookie("_ss"));
		var password=ss.substring(ss.indexOf(":")+1);
		return password;
	}catch(e) {
		return "";
	}
}

function signout() {
	try{
    	var ss=$.cookie("_ss","",{path:"/"});
		top.location.replace("index.html");
	}catch(e) {}
}

function getRole() {
	try{
    	var role=$.cookie("role");
    	if(null==role || ""==role) {
    		return "senior";
    	}else{
    		return role;
    	}
	}catch(e) {}
}

function hasModule(module) {	
	try{
    	var license=Base64.decode($.cookie("__l"));    	
		license=$.parseJSON(license);
		
		var map={};
		var components=license.publickey.components;
		for(var i in components) {
			for(var key in components[i]) {
				map[key]=components[i][key];
			}
		}
		
		if(map[module]!=null) return true;
	}catch(e) {}
	
	return false;	
}

function isEnterpriseVersion() {
	try{
    	var license=Base64.decode($.cookie("__l"));    	
		license=$.parseJSON(license);
		
		var map={};
		var ver=license.publickey.cloudversion;
		
		if(ver && ver.toLowerCase()==="enterprise") {
			return true;
		}
	}catch(e) {}
	
	return false;
}









