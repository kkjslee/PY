// JavaScript Document

var __account={
	sessionid: "<!--#echo var='UNIQUE_ID'-->"
};

function getUsername() {
	try{
		var ss=Base64.decode($.cookie("_ssa"));
		var username=ss.substring(0, ss.indexOf(":"));
		return username;
	}catch(e) {
		return "";
	}
}

function getPassword() {
	try{
		var ss=Base64.decode($.cookie("_ssa"));
		var password=ss.substring(ss.indexOf(":")+1);
		return password;
	}catch(e) {
		return "";
	}
}

function signout() {
	try{
    	var ss=$.cookie("_ssa","",{path:"/"});
		top.location.replace("index.html");
	}catch(e) {
	}
}

function hasModule(module) {	
	try{
    	var license=Base64.decode($.cookie("__l"));    	
		license=$.parseJSON(license);
		//var license={"publickey":{"username":"PUYUN Test","productID":"3e2905ad-833c-4600-b681-b93b6d35d995","validfrom":"1324339200000","validTo":1355961600000,"components":[{"groupmanagement":"GroupManager"},{"backup":"BackupServlet"},{"ipmanagement":"NetworkManager"},{"projectmanagement":"ProjectManager;ProjectManagementCallback"},{"apptemplatemanagement":"AppTemplateManager;AppTemplateCallBack"},{"ipmanagement":"NetWorkServlet"},{"volumemanagement":"StorageCtrlSevlet"},{"vmmanagement":"InstanceCtrlServlet;InstanceCtrlServletbak;executeVM"},{"inventorymanagement":"ConventoryManagerServlet"},{"vlanmanagement":"VLANManager"},{"applicationpackagemanagement":"ApplicationPackageManagement"}]},"privatekey":"valid"};
		
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


function savePersonalInfo(user, key, value) {
	if(!window.localStorage) {window.console && window.console.log("No available local storage."); return;}
	
	var personal=JSON.parse(window.localStorage.personal || "{}");
	
	!personal[user] && (personal[user]={})
	
	personal[user][key]=value;
	
	window.localStorage.personal=JSON.stringify(personal);
}

function readPersonalInfo(user, key) {
	if(!window.localStorage) {window.console && window.console.log("No available local storage."); return;}
	
	var personal=JSON.parse(window.localStorage.personal || "{}");
	
	if(!personal || !personal[user]) return null;
	
	return personal[user][key];
}







