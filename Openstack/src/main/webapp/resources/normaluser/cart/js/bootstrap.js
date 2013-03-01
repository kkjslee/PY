var Server="";
var cart_imgSelected_UUID = "";
var cart_flavorSelected_UUID="";
var cart_planSelected_UUID="";
var cart_volumeTypeSelected_UUID="";
var cart_networkSelected_UUID="";
var cart_dataCenterSelected_UUID="";
var instangMsg = "";
var volumeMsg = "";
//this should be called first in jsp file
function setServer(server,_instangMsg, _volumeMsg){
	Server = server;
	instangMsg = _instangMsg;
	volumeMsg = _volumeMsg;
}
$(function(){
	setup();
	$(".submitorder").bind("click", function(e){
		window.console.log("submit cart");
		if(validOrderCondition()){
			checkOutOrder(showPayMethods);
		}
		
	});
	$(".payMethodsContainer").delegate(".buyorder", "click", function(e){
		buyOrder();
	});
});

function buyOrder(){
	window.console.log("buy order");
	//todo
	window.open(Server + "/showPayMethods");
}
function showPayMethods(orderId){
	$.ajax({
        url: Server + "/showPayMethods",
        type: "POST",
        dataType:"html",
        data:{
        	orderId:orderId
        },
        cache: false,
        success: function(data) {
            try {
            	$(".payMethodsContainer").html(data);
            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}
function checkOutOrder(callBack){
	var pd = showProcessingDialog();
	$.ajax({
        url: Server + "/checkout",
        type: "POST",
        dataType:"json",
        cache: false,
        success: function(data) {
            try {
            	$(pd).dialog("close");
                if(data.status == 1){
                var orderId  = data.result;
                $("#mainBody").remove();
                $(".selectPayMethods").fadeIn("slow");
                //select pay methods
                callBack(orderId);
                }
                if(data.status == 0){
                    printMessage(data.result);
                }

            } catch(e) {
            	$(pd).dialog("close");
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
        	 $(pd).dialog("close");
            printError(jqXHR, textStatus, errorThrown);
            return false;
        }
    });
}

function validOrderCondition(){
	if($(".imgList").val()==-1 || $(".flavorList").val()==-1 || $(".planList").val()==-1 ){
		return false;
	}else{
		return true;
	}
	
}

function setup(){
	$(".selectable").each(function(e){
		$(this).change(function(e){
			if(typeof($(this).attr("isos") != "undefined")){
				var itemCategory = $(this).attr("isos");
				var itemValue = $(this).val();
				window.console.log("sel itemid: "+itemValue);
				var itemPrice = 0;
				if(typeof($(this).find("option:selected").attr("defaultprice"))!="undefined"){
					itemPrice =  $(this).find("option:selected").attr("defaultprice");
				}
				
				window.console.log("sel itemprice: " +itemPrice);
				sendCartRequest(itemCategory,itemValue,itemPrice);
			}
		});
	});
	
}

function sendCartRequest(itemCategory,itemId,itemPrice){
			var toAdd = false;
			var toUUID = "";
				if(itemCategory == "img"){
					if(isNull(cart_imgSelected_UUID)){
						toAdd = true;
					}else{
						window.console.log("page img uuid:" + cart_imgSelected_UUID);
						toUUID = cart_imgSelected_UUID;
					}
				}else if(itemCategory == "flavor"){
					if(isNull(cart_flavorSelected_UUID)){
						toAdd = true;
					}else{
						toUUID = cart_flavorSelected_UUID;
					}
				}else if(itemCategory == "plan"){
					if(isNull(cart_planSelected_UUID)){
						toAdd = true;
					}else{
						toUUID = cart_planSelected_UUID;
					}
				}else if(itemCategory == "volumeType"){
					if(isNull(cart_volumeTypeSelected_UUID)){
						toAdd = true;
					}else{
						toUUID = cart_volumeTypeSelected_UUID;
					}
				}else if(itemCategory == "network"){
					if(isNull(cart_networkSelected_UUID)){
						toAdd = true;
					}else{
						toUUID = cart_networkSelected_UUID;
					}
				}else if(itemCategory == "dataCenter"){
					if(isNull(cart_dataCenterSelected_UUID)){
						toAdd = true;
					}else{
						toUUID = cart_dataCenterSelected_UUID;
					}
				}
			window.console.log("to UUID" + toUUID);
	    	if(itemId!=-1 && toAdd){
	    		addItemToCart(itemId,itemPrice,itemCategory);
	    		window.console.log("add itemid: " + itemId);
	    	}else if(itemId!=-1 && toUUID!=""){
	    		window.console.log("update with last uuid:" + toUUID + " new itemid:" + itemId);
	    		updateCartItem(itemId,toUUID,itemPrice,itemCategory);
	    	}else if(itemId==-1  && toUUID!=""){
	    		window.console.log("remove uuid:" + toUUID);
	    		removeCartItem(toUUID,itemCategory);
	    	}
	    	activeCartSubmitBtn();
}

function addItemToCart(itemId,itemPrice,itemCategory){
	var name = "";
	var extra = "";
	if(itemCategory == "flavor"){
		name = $("#name").val();
		if(isNull(name)){
			printMessage(instangMsg);
			return;
		}
	}
	if(itemCategory =="volumeType"){
		name=$("#volumeName").val();
		if(isNull(name)){
			printMessage(volumeMsg);
			return;
		}
		extra = $("#volumeLocation").val();
	}
	$.ajax({
        url: Server + "/add",
        type: "POST",
        dataType:"json",
        data: {
        	itemSpecificationId:itemId,
        	price:itemPrice,
        	extra:extra,
        	name:name,
        	number:1
        },
        cache: false,
        success: function(data) {
            try {

                if(data.status == "success"){
                	var price  = data.data.amount;
                	udpateAmount(price);
                	var uuid = data.data.currentItemUUID;
                	window.console.log("set "+ itemCategory + " with uuid: " + uuid);
                	setCategorySelctedIdValue(itemCategory,uuid);	                	
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


function updateCartItem(itemId,uuid, itemPrice,itemCategory){
	var name = "";
	var extra = "";
	if(itemCategory == "flavor"){
		name = $("#name").val();
	}
	if(itemCategory =="volumeType"){
		name=$("#volumeName").val();
		if(isNull(name)){
			printMessage(volumeMsg);
			return;
		}
		extra = $("#volumeLocation").val();
	}
$.ajax({
    url: Server + "/update",
    type: "POST",
    dataType:"json",
    data: {
    	itemSpecificationId:itemId,
    	uuid:uuid,
    	extra:extra,
    	name:name,
    	price:itemPrice
    },
    cache: false,
    success: function(data) {
        try {

            if(data.status == "success"){
            	var price  = data.data.amount;
            	udpateAmount(price);
            	var uuid = data.data.currentItemUUID;
            	setCategorySelctedIdValue(itemCategory,uuid);	
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

function removeCartItem(toId,itemCategory){
$.ajax({
    url: Server + "/remove",
    type: "POST",
    dataType:"json",
    data: {
    	uuid:toId
    },
    cache: false,
    success: function(data) {
        try {

            if(data.status == "success"){
            	var price  = data.data.amount;
            	udpateAmount(price);
            	setCategorySelctedIdValue(itemCategory, "");
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

function activeCartSubmitBtn(){
	if(validOrderCondition()){
		$(".cartButton").find("a").addClass("btn-primary");
	}else{
		$(".cartButton").find("a").removeClass("btn-primary");
	}
}

function setCategorySelctedIdValue(itemCategory,value){
	if(itemCategory == "img"){
		cart_imgSelected_UUID = value;
	}else if(itemCategory == "flavor" ){
		cart_flavorSelected_UUID = value;
	}else if(itemCategory == "plan"){
		cart_planSelected_UUID = value;
	}else if(itemCategory == "volumeType"){
		cart_volumeTypeSelected_UUID = value;
	}else if(itemCategory == "network"){
		cart_networkSelected_UUID = value;
	}else if(itemCategory == "dataCenter"){
		cart_dataCenterSelected_UUID = value;
	}
}
function udpateAmount(price){
	$(".cartTotal").text(price);
}


