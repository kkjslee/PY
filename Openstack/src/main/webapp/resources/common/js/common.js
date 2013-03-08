
function isNull(value) {
    if (typeof(value) == "undefined" ||null == value|| "" == $.trim(value) || value == "null" || value.length == 0) {
        return true;
    } else {
        return false;
    }
}
function formatDate(time){
	if(isNull(time)){
		return "";
	}else {
		var date = new Date();
		date.setTime(time);
		return date.toLocaleString();
	}
}

function isSuccess(response){
	if(typeof(response) == 'object' && response && response.status == 1){
		return true;
	}
	
	return false;
}

function getResult(response){
	if(typeof(response) == 'object' && response && response.hasOwnProperty('result')){
		return response.result;
	}
	
	if(typeof(response) == 'string'){
		return response;
	}
	return "";
}

/**
 * conf.container 表单容器
 * conf.title 表单标题
 * conf.url 表单内容
 * conf.buttons 表单按钮。对象或列表，包含text，click属性
 * @param conf
 */
function CustomForm(){
	
	var form = null;
	var inited = false;
	
	this.show = function(conf){
		if(inited == false || form == null){
			init(conf);
		}else{
			form.dialog();
		}
	};
	this.close = function(){
		
		if(form!=null){
			form.dialog("close");
		}
	};
	
	this.getForm = function(){
		return form;
	};
	var init = function(conf){
		var container = $(conf.container);
		container.hide();
		
		var success = false;
		$.ajax({
	        type: "POST",
	        dataType: "html",
	        cache: false,
	        url: conf.url,
	        data:conf.data,
	        success: function(data) {
	            try{
					data = $.parseJSON(data);
	            }catch(e){}
	            var result = getResult(data);
	        	if(isSuccess(data)){
	        		container.html(result);
	        		success = true;
	            }else{
	            	$("<span class='loadingError'>"+result+"</span>").appendTo(container);
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            $("<span class='loadingError'>error</span>").appendTo(container.empty());
	        },
	        complete : function(){
	        	form = container.dialog({
	        		title : conf.title,
	        		modal: true,
	        		autoOpen:false,
	                resizable: false,
	                width:conf.width,
	        		buttons : conf.buttons
	        	});
	        	if(typeof(conf.callback)!="undefined"){
	        		callback.call(form);
				}
	        	$(form).dialog("open");
	        	if(success == true){
	        		inited = true;
	        	}
	        }
	    });
	}
}


function fixSize() {
  
    //var height=$(window).height();
    var width=$(window).width()-$(".left").outerWidth()-15;

    //$("#contentFrame").height(height);
    $(".right").width(width);

}