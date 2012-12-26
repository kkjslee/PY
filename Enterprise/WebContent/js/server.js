// JavaScript Document
//Server="192.168.1.145";
Server = "http://203.166.164.7"
cloudUsername="rqshao";
cloudPassword="Password9988!"
//may have multi tenants, here just for test
cloudTenant="demo"
tennantId="239d30eeca6d4df1a012a9a84fd8e9e7";
keyStonePort="5000";
cloudToken="";
tokenExpires=""
/*function getTokens(){
	$.ajax({
		type: "POST",
		url: Server+":" +keyStonePort + "/v2.0/tokens",
		cache: false,
		headers:{
			"Content-type":"application/json",
			"Accept": "application/json"
		},
		data: {
			auth:{
				passwordCredentials:{
				username:cloudUsername,
				password:cloudPassword
				},
				tenantName:cloudTenant
			}
		}
		success: function(data) {
			try{
				if(data.length>0){
					for(var i=0; i<data.length; i++) {
						cloudToken = data.access.token.id;
						//need check expire,but this should done on the server side
						tokenExpires = data.access.token.expires
						break;
					}
				}else{
				printMessage("");
				}
				
			}catch(e) {
				printMessage("Data Broken: ["+e+"]");
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			printError(jqXHR, textStatus, errorThrown);
		}
	});

}

if(cloudToken == ""){
	getTokens();
}*/









