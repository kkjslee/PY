var Server="";
var cart_imgSelected_UUID = "";
var cart_flavorSelected_UUID="";
var cart_planSelected_UUID="";
var cart_volumeTypeSelected_UUID="";
var itemSelected = 0;

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
	if($(".imgList").find("li.ui-selected").length == 0 || $(".flavorList").find("li.ui-selected").length == 0||$(".planList").find("li.ui-selected").length == 0){
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
	$(".accordion-inner").each(function(e){
		var inner = $(this).find("ul.selectable");
		if(typeof($(inner).attr("isos") != "undefined")){
			var itemCategory = $(inner).attr("isos");
			var itemUUID = "";
			if(itemCategory == "img"){
				itemUUID = cart_imgSelected_UUID;
			}else if(itemCategory == "flavor"){
				itemUUID = cart_flavorSelected_UUID;
			}
			else if(itemCategory == "plan"){
				itemUUID = cart_planSelected_UUID;
			}
			else if (itemCategory == "volumeType") {
				itemUUID = cart_volumeTypeSelected_UUID;
			}
			
			bindTableSelect(itemCategory,"defaultPrice",itemUUID);
		}
		
	});
	
	/*$(".imgList").tableSelect({
		onClick: function(row) {
		    	var uuid = $(row).find("input[name='imgId']").attr("uuid");
		    	var itemId = $(row).find("input[name='imgId']").val();
		    	var price = $(row).find("input[name='defaultPrice']").val();
		    	if(isNull(cart_imgSelected_UUID)){
		    		addItemToCart(itemId,price,row,updateInputRowUUIDAttr,"imgId",$(row).parent());
		    	}else if(uuid == cart_imgSelected_UUID){
		    		updateCartItem(itemId,uuid,price,row,"imgId");
		    		
		    	}else{
		    		removeCartItem(cart_imgSelected_UUID,row,updateInputRowUUIDAttr,"imgId",$(row).parent());
		    		addItemToCart(itemId,price,row,updateInputRowUUIDAttr,"imgId",$(row).parent());
		    	}
		    	itemSelected++;
		    	activeCartSubmitBtn();
		}
	});
	
	$(".flavorList").tableSelect({
		onClick: function(row) {
			var uuid = $(row).find("input[name='flavorId']").attr("uuid");
	    	var itemId = $(row).find("input[name='flavorId']").val();
	    	var price = $(row).find("input[name='defaultPrice']").val();
	    	if(isNull(cart_flavorSelected_UUID)){
	    		addItemToCart(itemId,price,row,updateInputRowUUIDAttr,"flavorId",$(row).parent());
	    	}else if(uuid == cart_flavorSelected_UUID){
	    		
	    		updateCartItem(itemId,uuid,price,row,"flavorId");
	    		
	    	}else{
	    		removeCartItem(cart_flavorSelected_UUID,row,updateInputRowUUIDAttr,"flavorId",$(row).parent());
	    		addItemToCart(itemId,price,row,updateInputRowUUIDAttr,"flavorId",$(row).parent());
	    	}
	    	itemSelected++;
	    	activeCartSubmitBtn();
		}
	});
	
	$(".planList").tableSelect({
		onClick: function(row) {
			var uuid = $(row).find("input[name='planId']").attr("uuid");
	    	var itemId = $(row).find("input[name='planId']").val();
	    	var price = $(row).find("input[name='defaultPrice']").val();
	    	if(isNull(cart_planSelected_UUID)){
	    		addItemToCart(itemId,price,row,updateInputRowUUIDAttr,"planId",$(row).parent());
	    	}else if(uuid == cart_planSelected_UUID){
	    		
	    		updateCartItem(itemId,uuid,price,row,"planId");
	    		
	    	}else{
	    		removeCartItem(cart_planSelected_UUID,row,updateInputRowUUIDAttr,"planId",$(row).parent());
	    		addItemToCart(itemId,price,row,updateInputRowUUIDAttr,"planId",$(row).parent());
	    	}
	    	itemSelected++;
	    	activeCartSubmitBtn();
		}
	});*/
}

//args:("plan","defaultPrice",cart_planSelected_UUID)
function bindTableSelect(itemCategory,priceId,itemUUID){
	$("."+itemCategory + "List").tableSelect({
		onClick: function(row) {
			var uuid = $(row).find("input[name='" +itemCategory +"Id']").attr("uuid");
	    	var itemId = $(row).find("input[name='" +itemCategory +"Id']").val();
	    	window.console.log("itemId:" + itemId);
	    	var price = $(row).find("input[name='" +priceId +"']").val();
	    	window.console.log("priceId:" + price);
	    	if(isNull(itemUUID)){
	    		addItemToCart(itemUUID,itemId,price,row,updateInputRowUUIDAttr,itemCategory+"Id",$(row).parent());
	    	}else if(uuid == itemUUID){
	    		
	    		updateCartItem(itemId,uuid,price,row,itemCategory+"Id");
	    		
	    	}else{
	    		removeCartItem(itemUUID,row,updateInputRowUUIDAttr,itemCategory+"Id",$(row).parent());
	    		addItemToCart(itemUUID,itemId,price,row,updateInputRowUUIDAttr,itemCategory+"Id",$(row).parent());
	    	}
	    	itemSelected++;
	    	activeCartSubmitBtn();
		}
	});
}

function activeCartSubmitBtn(){
	if(itemSelected == 3){
		$(".cartButton").find("a").addClass("btn-primary");
	}
}
//category id like:imgId,flavorId
function updateInputRowUUIDAttr(row, uuid,isUpdate,categoryId,container){
	if(!isNull(categoryId)){
		
		var item = $(container).find(row).find("input[name='" + categoryId + "']");
		if(isUpdate){
			$(item).attr("uuid",uuid);
		}else{
			$(item).removeAttr("uuid");
		}
	}else{
		alert("category id not defined");
	}
}
function udpateAmount(price){
	$(".cartTotal").text(price);
}
function addItemToCart(itemUUID,itemId,price,row,callBack,categoryId,container){
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
	                	itemUUID = uuid;
	                	callBack(row,uuid,true,categoryId,container);
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


function updateCartItem(itemId,UUId,price,row){
	$.ajax({
        url: Server + "/update",
        type: "POST",
        dataType:"json",
        data: {
        	uuid:UUId,
        	price:price,
        	itemSpecificationId:itemId,
        	number:1
        },
        cache: false,
        success: function(data) {
            try {

                if(data.status == "success"){
                	var price  = data.data.amount;
                	udpateAmount(price);
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

function removeCartItem(UUId,row, callBack,categoryId,container){
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
                	//remove uuid  
                	callBack(row,data.data.currentItemUUID,false,categoryId,container);
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

