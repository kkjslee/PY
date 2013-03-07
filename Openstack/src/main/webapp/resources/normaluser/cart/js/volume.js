var Server="";
var cart_volumeTypeSelected_UUID="";
var cart_dataCenterSelected_UUID="";
var volumeMsg = "";
var selPayMsg =  "";
//this should be called first in jsp file
function setServer(server,_volumeMsg, _selPayMsg){
	Server = server;
	volumeMsg = _volumeMsg;
	selPayMsg = _selPayMsg;
}
$(function(){
	setup();
	$(".submitorder").bind("click", function(e){
		window.console.log("submit cart");
		if(validOrderCondition()){
			checkOutOrder(showPayMethods);
		}
		
	});
	$(".selectPayMethods").delegate(".buyorder", "click", function(e){
		buyOrder();
	});
});

function buyOrder(){
	window.console.log("buy order");
	if(validOrderPay()){
		return;
		window.open(Server + "/buyorder?orderId="+$("#orderId").val());
	}else{
		printMessage(selPayMsg);
	}
}
function validOrderPay(){
	if(!isNull($("input[name='payMethod']:checked").val())){
		window.console.log("paymethod :" + $("input[name='payMethod']:checked").val());
		return true;
	}else{
		return false;
	}
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
            	 $("#mainBody").remove();
                 $(".selectPayMethods").fadeIn("slow");
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
	if($(".volumeTypeList").val()==-1 || $(".dataCenterList").val()==-1){
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
				var itemPrice = 0;
				if(typeof($(this).find("option:selected").attr("defaultprice"))!="undefined"){
					itemPrice =  $(this).find("option:selected").attr("defaultprice");
				}
				
				sendCartRequest(itemCategory,itemValue,itemPrice);
			}
		});
	});
	
}

function sendCartRequest(itemCategory,itemId,itemPrice){
			var toAdd = false;
			var toUUID = "";
				if(itemCategory == "volumeType"){
					if(isNull(cart_volumeTypeSelected_UUID)){
						toAdd = true;
					}else{
						toUUID = cart_volumeTypeSelected_UUID;
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
function cancelSelect(itemCategory){
	$("." + itemCategory + "List").selectmenu("value", "-1");
}
function addItemToCart(itemId,itemPrice,itemCategory){
	var name = "";
	if(itemCategory =="volumeType"){
		name=$("#volumeName").val();
		if(isNull(name)){
			printMessage(volumeMsg);
			cancelSelect("volumeType");
			return;
		}
	}
	var pd = showProcessingDialog();
	$.ajax({
        url: Server + "/add",
        type: "POST",
        dataType:"json",
        data: {
        	itemSpecificationId:itemId,
        	price:itemPrice,
        	name:name,
        	number:1
        },
        cache: false,
        success: function(data) {
            try {
            	 $(pd).dialog("close");
                if(data.status == "success"){
                	var price  = data.data.amount;
                	udpateAmount(price);
                	var uuid = data.data.currentItemUUID;
                	window.console.log("set "+ itemCategory + " with uuid: " + uuid);
                	setCategorySelctedIdValue(itemCategory,uuid);	                	
                }
                if(data.status == "error"){
               	 cancelSelect(itemCategory);
                    printMessage(data.msg);
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
        	$(pd).dialog("close");
        	cancelSelect(itemCategory);
            printError(jqXHR, textStatus, errorThrown);
            return false;
        }
    });
}


function updateCartItem(itemId,uuid, itemPrice,itemCategory){
	var name = "";
	if(itemCategory =="volumeType"){
		name=$("#volumeName").val();
		if(isNull(name)){
			printMessage(volumeMsg);
			cancelSelect("volumeType");
			return;
		}
	}
	var pd = showProcessingDialog();
	$.ajax({
	    url: Server + "/update",
	    type: "POST",
	    dataType:"json",
	    data: {
	    	itemSpecificationId:itemId,
	    	uuid:uuid,
	    	name:name,
	    	price:itemPrice
	    },
	    cache: false,
	    success: function(data) {
	        try {
	        	 $(pd).dialog("close");
	            if(data.status == "success"){
	            	var price  = data.data.amount;
	            	udpateAmount(price);
	            	var uuid = data.data.currentItemUUID;
	            	setCategorySelctedIdValue(itemCategory,uuid);	
	            }
	            if(data.status == "error"){
	           	 cancelSelect(itemCategory);
	                printMessage(data.msg);
	            }
	
	        } catch(e) {
	            printMessage("Data Broken: [" + e + "]");
	        }
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	    	$(pd).dialog("close");
	    	cancelSelect(itemCategory);
	        printError(jqXHR, textStatus, errorThrown);
	        return false;
	    }
	});
}

function removeCartItem(toId,itemCategory){
	var pd = showProcessingDialog();
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
	        	 $(pd).dialog("close");
	            if(data.status == "success"){
	            	var price  = data.data.amount;
	            	udpateAmount(price);
	            	setCategorySelctedIdValue(itemCategory, "");
	            }
	            if(data.status == "error"){
	           	 cancelSelect(itemCategory);
	                printMessage(data.msg);
	            }
	
	        } catch(e) {
	            printMessage("Data Broken: [" + e + "]");
	        }
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	    	$(pd).dialog("close");
	    	cancelSelect(itemCategory);
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
	if(itemCategory == "volumeType"){
		cart_volumeTypeSelected_UUID = value;
	}else if(itemCategory == "dataCenter"){
		cart_dataCenterSelected_UUID = value;
	}
}
function udpateAmount(price){
	$(".cartTotal").text(price);
}


