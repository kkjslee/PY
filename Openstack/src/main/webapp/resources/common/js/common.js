
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
	if(typeof(response) == 'object' && response.status == 1){
		return true;
	}
	
	return false;
}

function getResult(response){
	if(typeof(response) == 'object' && response.hasOwnProperty('result')){
		return response.result;
	}
	
	return response;
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
	
	this.show = function(conf){
		if(form == null){
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
	        	var d = container.dialog({
	        		title : conf.title,
	        		modal: true,
	                resizable: false,
	                show: "slide",
	                hide: "slide",
	        		buttons : conf.buttons
	        	});
	        	
	        	if(success == true){
	        		form = d;
	        	}
	        }
	    });
	}
}