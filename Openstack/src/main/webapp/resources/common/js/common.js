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
	if(typeof(repsonse) == 'object' && repsonse.status == 1){
		return true;
	}
	
	return false;
}

function getResult(response){
	if(typeof(repsonse) == 'object' && repsonse.hasOwnProperty('result')){
		return response.result;
	}
	
	return response;
}