// JavaScript Document
// Author: Bill, 2011

jQuery(window).bind("load", function(){
	jQuery(window).setup();
});

(function($){
	$.fn.setup=setup;
})(jQuery);

function setup() {
	// setup...
	loadLicenseInfo();
	
	$("#submit").html(Locale["license.button.update"]);
	
	$("button").button();
	
	$("#submit").bind("click", function(event){
		var newPrivateKey=$.trim($("#newPrivateKey").val());
		var newLicense=$.trim($("#newLicense").val());
		
		if(""==newPrivateKey || ""==newLicense) {
			alert(Locale["license.message.empty"]);
		}else{
			if(confirm(Locale["license.confirm.update"])) {
				submit(newPrivateKey, newLicense);
			}
		}
	});
}

function submit(newPrivateKey, newLicense) {
	$.ajax({
		url: Server+"/LicenseManager/LicenseManager",
		type: "POST",
		cache: false,
		data: {
			methodtype: "updatelicense",
			privatekey: newPrivateKey,
			license: newLicense
		},
		success: function(data) {
			if(data.match("true")) {
				alert(Locale["license.message.submit.success"]);
				window.location.href=window.location.href;
			}else if(data.match("false")) {
				alert(Locale["license.message.submit.failure"]);
			}else {
				alert(Locale["license.message.submit.failure.undefined"].sprintf(data));
			}		
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert(Locale["license.message.framework.failure"]);
		}
	});
}

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function printError(jqXHR, textStatus, errorThrown) {
	//alert("Connection Broken: "+textStatus+", "+errorThrown);
}


function loadLicenseInfo() {
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
				data=$.parseJSON(data);
				
				var list=[];
				if(data.privatekey=="invalid_private_key" 
						|| data.privatekey=="private_key_missing" 
						|| data.privatekey=="ignore") {
							
					$("[zmlm\\\:item='status']").html(mapPrivateKeyDict(data.privatekey));
					
				}else {
					var components=data.publickey.components;
					for(var i in components) {
						for(var key in components[i]) {
							list.push(key);
						}
					}
					
					var from=new Date(data.publickey.validfrom);
					var to=new Date(data.publickey.validTo);
					var dur=from.getUTCFullYear()+" / "+(from.getMonth()+1)+" / "+from.getDate() 
								+ " ~ " + 
								to.getUTCFullYear()+" / "+(to.getMonth()+1)+" / "+to.getDate();
								
					convertComponents(list);
					
					$("[zmlm\\\:item='status']").html(data.privatekey);
					$("[zmlm\\\:item='vmnumber']").html(data.publickey.vmnumber);
					$("[zmlm\\\:item='productid']").html(data.publickey.productID);
					$("[zmlm\\\:item='username']").html(data.publickey.username);
					$("[zmlm\\\:item='duration']").html(dur);
					$("[zmlm\\\:item='components']").html(list.join("<br/>"));
					
				}
			}catch(e) {
				alert(Locale["license.message.framework.failure"]);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert(Locale["license.message.framework.failure"]);
		}
	});
}

function mapPrivateKeyDict(pk) {
	switch(pk) {
		case "invalid_private_key": return Locale["license.status.invalid_private_key"];
		case "private_key_missing": return Locale["license.status.private_key_missing"];
		case "ignore": return Locale["license.status.ignore"];
		default: return "[N/A]";
	}
}

function convertComponents(list) {
	for(var i in list) {
		switch(list[i]) {
			case "groupmanagement": list[i]=Locale["license.module.groupmanagement"]; break;
			case "backup": list[i]=Locale["license.module.backup"]; break;
			case "projectmanagement": list[i]=Locale["license.module.projectmanagement"]; break;
			case "apptemplatemanagement": list[i]=Locale["license.module.apptemplatemanagement"]; break;
			case "ipmanagement": list[i]=Locale["license.module.ipmanagement"]; break;
			case "volumemanagement": list[i]=Locale["license.module.volumemanagement"]; break;
			case "vmmanagement": list[i]=Locale["license.module.vmmanagement"]; break;
			case "vlanmanagement": list[i]=Locale["license.module.vlanmanagement"]; break;
			case "inventorymanagement": list[i]=Locale["license.module.inventorymanagement"]; break;
			case "applicationpackagemanagement": list[i]=Locale["license.module.applicationpackagemanagement"]; break;
		}
	}
}











