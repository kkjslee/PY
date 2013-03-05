var Server="";
var cart_imgSelected_UUID = "";
var cart_flavorSelected_UUID="";
var cart_planSelected_UUID="";
var cart_volumeTypeSelected_UUID="";
var cart_networkSelected_UUID="";
var cart_dataCenterSelected_UUID="";
var instangMsg = "";
var volumeMsg = "";
var selPayMsg =  "";
//this should be called first in jsp file
function setServer(server,_instangMsg, _volumeMsg, _selPayMsg){
	Server = server;
	instangMsg = _instangMsg;
	volumeMsg = _volumeMsg;
	selPayMsg = _selPayMsg;
}
$(function(){
	$( ".cartForm" ).tooltip();
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


function setup(){
	$(".accordion-inner").each(function(e){
			var inner = $(this).find("ul.selectable");
			if(typeof($(inner).attr("isos") != "undefined")){
				var itemCategory = $(inner).attr("isos");
	
				bindTableSelect(itemCategory);
			}
			
		});
	$(".accordion-inner").delegate(".li-remove","click",function(e){
		var row = $(this).parent();
		var ul = $(row).parent();
		var itemCategory = $(ul).attr("isos");
		var toUUId= "";
		if(itemCategory == "volumeType"){
				toUUId = cart_volumeTypeSelected_UUID;
		}else if(itemCategory == "network"){
				toUUId = cart_networkSelected_UUID;
		}
		window.console.log("to remove uuid: " +toUUId);
		if(!isNull(toUUId)){
			removeCartItem(toUUId,row,updateInputRowUUIDAttr,itemCategory,ul,removeSel);
		}
	});
}

function removeSel(ul ,row){
	$(ul).find(row).removeClass("ui-selected");
}

function bindTableSelect(itemCategory){
$("."+itemCategory + "List").tableSelect({
		cancelTag:"a",
		onClick: function(row) {
			window.console.log("on click trigger");
			var uuid = $(row).find("input[name='" +itemCategory +"Id']").attr("uuid");
    		var itemId = $(row).find("input[name='" +itemCategory +"Id']").val();
    		var price = $(row).find("input[name='defaultPrice']").val();
    		var toAdd = false;
	    	var toUUId= "";
			if(itemCategory == "img"){
				if(isNull(cart_imgSelected_UUID)){
					toAdd = true;
				}else{
					toUUId = cart_imgSelected_UUID;
				}
			}else if(itemCategory == "flavor"){
				if(isNull(cart_flavorSelected_UUID)){
					toAdd = true;
				}else{
					toUUId = cart_flavorSelected_UUID;
				}
			}else if(itemCategory == "plan"){
				if(isNull(cart_planSelected_UUID)){
					toAdd = true;
				}else{
					toUUId = cart_planSelected_UUID;
				}
			}else if(itemCategory == "volumeType"){
				if(isNull(cart_volumeTypeSelected_UUID)){
					toAdd = true;
				}else{
					toUUId = cart_volumeTypeSelected_UUID;
				}
			}else if(itemCategory == "network"){
				if(isNull(cart_networkSelected_UUID)){
					toAdd = true;
				}else{
					toUUId = cart_networkSelected_UUID;
				}
			}else if(itemCategory == "dataCenter"){
				if(isNull(cart_dataCenterSelected_UUID)){
					toAdd = true;
				}else{
					toUUId = cart_dataCenterSelected_UUID;
				}
			}
				window.console.log("toUUid: " +toUUId);
			
	    	if(toAdd){
	    		window.console.log("to add itemid: " +itemId);
	    		addItemToCart(itemId,price,row,updateInputRowUUIDAttr,itemCategory,$(row).parent());
	    		
	    	}else if(!isNull(toUUId)){
	    		window.console.log("to update itemid: " +itemId);
	    		updateCartItem(itemId,toUUId, price,itemCategory,updateInputRowUUIDAttr,row,$(row).parent());
	    	}else{
	    		window.console.log("to remove itemid: " +itemId);
	    		removeCartItem(toUUId,row,updateInputRowUUIDAttr,itemCategory,$(row).parent());
	    	}
	    	activeCartSubmitBtn();
		
		}});
   
			
}

function updateInputRowUUIDAttr(row, uuid,isAddorUpdate,itemCategory,container){
	if(!isNull(itemCategory)){
		$(container).find("li").each(function(e){
			$(this).removeAttr("uuid");
		});
		if(isAddorUpdate){
			window.console.log(" add uuid for " + itemCategory);
			$(container).find(row).attr("uuid",uuid);
		}
			setCategorySelctedIdValue(itemCategory, uuid);
	}else{
		alert("category id not defined");
	}
}
function addItemToCart(itemId,itemPrice,row,callBack,itemCategory,container){
	var name = "";
	if(itemCategory == "flavor"){
		name = $("#name").val();
		if(isNull(name)){
			$(container).find(row).removeClass("ui-selected");
			printMessage(instangMsg);
			return;
		}
	}
	if(itemCategory =="volumeType"){
		name=$("#volumeName").val();
		if(isNull(name)){
			$(container).find(row).removeClass("ui-selected");
			printMessage(volumeMsg);
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
                	callBack(row,uuid,true,itemCategory,container);                	
                }
                if(data.status == "error"){
                	removeSel(container ,row);
                    printMessage(data.msg);
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
        	 $(pd).dialog("close");
        	removeSel(container ,row);
            printError(jqXHR, textStatus, errorThrown);
            return false;
        }
    });
}


function updateCartItem(itemId,uuid, itemPrice,itemCategory,callBack,row,container){
	
	var name = "";
	if(itemCategory == "flavor"){
		name = $("#name").val();
		if(isNull(name)){
			$(container).find(row).removeClass("ui-selected");
			printMessage(instangMsg);
			return;
		}
	}
	if(itemCategory =="volumeType"){
		name=$("#volumeName").val();
		if(isNull(name)){
			$(container).find(row).removeClass("ui-selected");
			printMessage(volumeMsg);
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
	            	callBack(row,uuid,true,itemCategory,container);
	            }
	            if(data.status == "error"){
	            	removeSel(container ,row);
	                printMessage(data.msg);
	            }
	
	        } catch(e) {
	            printMessage("Data Broken: [" + e + "]");
	        }
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	    	 $(pd).dialog("close");
	    	 removeSel(container ,row);
	        printError(jqXHR, textStatus, errorThrown);
	        return false;
	    }
	});
}

function removeCartItem(toId,row, callBack,itemCategory,container,removeCall){
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
	            	callBack(row,"",false,itemCategory,container);
	            	if(typeof(removeCall) != "undefined"){
	            		removeCall(container,row);
	            	}
	            }
	            if(data.status == "error"){
	            	removeSel(container ,row);
	                printMessage(data.msg);
	            }
	
	        } catch(e) {
	            printMessage("Data Broken: [" + e + "]");
	        }
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	    	 $(pd).dialog("close");
	    	 removeSel(container ,row);
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



function buyOrder(){
	window.console.log("buy order");
	//todo
	if(validOrderPay()){
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
	var valid = true;
	$(".cartRequired").each(function(e){
		if($(this).find("li.ui-selected").length ==0){
			valid = false;
		};
	});
	return valid;
}