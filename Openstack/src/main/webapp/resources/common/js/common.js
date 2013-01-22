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