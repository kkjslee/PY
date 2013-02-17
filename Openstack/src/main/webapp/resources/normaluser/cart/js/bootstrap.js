var Server="";
var cart_imgSelected_UUID = "";
var cart_flavorSelected_UUID="";

$(function(){
	setup();
});
//this should be called first in jsp file
function setServer(server){
	Server = server;
}
function setup(){
	$(".imgList").tableSelect({
		onClick: function(row) {
		    window.console.log(row);
		    	var uuid = $(row).find("input[name='imgId']").attr("UUID");
		    	if(isNull(uuid)){
		    		window.console.log("uuid is null" );
		    		addImgToCart($(row).find("input[name='imgId']").val());
		    	}else if(uuid == cart_imgSelected_UUID){
		    		//update
		    	}else{
		    		//remove
		    	}
		}
	});
	
	$(".flavorList").tableSelect({
		onClick: function(row) {
		    window.console.log(row);
		    	var uuid = $(row).find("input[name='flavorId']").attr("UUID");
		    	if(isNull(uuid)){
		    		window.console.log("uuid is null" );
		    		addImgToCart($(row).find("input[name='imgId']").val());
		    	}else if(uuid == cart_imgSelected_UUID){
		    		//update
		    	}else{
		    		//remove
		    	}
		}
	});
}

function addImgToCart(imgId){
	window.console.log("imgid:" +  imgId);
		$.ajax({
	        url: Server + "/add",
	        type: "POST",
	        dataType:"json",
	        data: {
	        	itemSpecificationId:imgId,
	        	number:1
	        },
	        cache: false,
	        success: function(data) {
	            try {

	                if(data.status == "success"){
	                	window.console.log(data.data.uuid);
	                	cart_imgSelected_UUID = data.data.uuid;
	                }
	                if(data.status == "error"){
	                    printMessage(data.msg);
	                }

	            } catch(e) {
	                printMessage("Data Broken: [" + e + "]");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            printError(jqXHR, textStatus, errorThrown);
	            return false;
	        }
	    });
}

function removeImgFromCart(imgId){
	
}