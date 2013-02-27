var Server="";
var cart_imgSelected_UUID = "";
var cart_flavorSelected_UUID="";
var cart_planSelected_UUID="";
var cart_volumeTypeSelected_UUID="";

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
//this should be called first in jsp file
function setServer(server){
	Server = server;
}
function setup(){
	$(".selectable").each(function(e){
		$(this).change(function(e){
			if(typeof($(this).attr("isos") != "undefined")){
				var itemCategory = $(this).attr("isos");
				var itemValue = $(this).val();
				var itemPrice =  $(this).find("input[name='defaultPrice']").val();
				
				sendCartRequest(itemCategory,itemValue,itemPrice);
			}
		})
		
		
	});
	
}

function sendCartRequest(itemCategory,itemId,itemPrice){
			var toAdd = false;
			var toRemove = true;
			if(itemCategory == "img"){
				if(isNull(cart_imgSelected_UUID)){
					toAdd = true;
				}else{
					toId = cart_imgSelected_UUID;
				}
			}else if(itemCategory == "flavor"){
				if(isNull(cart_flavorSelected_UUID)){
					toAdd = true;
				}else{
					toId = cart_flavorSelected_UUID;
				}
			}else if(itemCategory == "plan"){
				if(isNull(cart_planSelected_UUID)){
					toAdd = true;
				}else{
					toId = cart_planSelected_UUID;
				}
			}else if(itemCategory == "volumeType"){
				if(isNull(cart_volumeTypeSelected_UUID)){
					toAdd = true;
				}else{
					toId = cart_volumeTypeSelected_UUID;
				}
			}
			
	    	if(toAdd){
	    		addItemToCart(itemId,itemPrice,itemCategory);
	    	}else if(itemCategory !="volumeType" && itemId != -1){
	    		updateCartItem(itemId);
	    	}else if(itemId==-1){
	    		removeCartItem(cart_volumeTypeSelected_UUID,itemCategory);
	    	}
	    	activeCartSubmitBtn();
}

function addItemToCart(itemId,itemPrice,itemCategory);{
	$.ajax({
        url: Server + "/add",
        type: "POST",
        dataType:"json",
        data: {
        	itemSpecificationId:itemId,
        	price:price,
        	number:1
        },
        cache: false,
        success: function(data) {
            try {

                if(data.status == "success"){
                	var price  = data.data.amount;
                	udpateAmount(price);
                	var uuid = data.data.currentItemUUID;
                	setCategorySelctedIdValue(categoryId,uuid);	                	
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


function updateCartItem(itemId){
$.ajax({
    url: Server + "/update",
    type: "POST",
    dataType:"json",
    data: {
    	itemSpecificationId:itemId,
    },
    cache: false,
    success: function(data) {
        try {

            if(data.status == "success"){
            	var price  = data.data.amount;
            	udpateAmount(price);
            	var uuid = data.data.currentItemUUID;
            	setCategorySelctedIdValue(categoryId,uuid);	
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
    	uuid:UUId
    },
    cache: false,
    success: function(data) {
        try {

            if(data.status == "success"){
            	var price  = data.data.amount;
            	udpateAmount(price);
            	setCategorySelctedIdValue(categoryId, "");
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
	}
}
function udpateAmount(price){
	$(".cartTotal").text(price);
}


