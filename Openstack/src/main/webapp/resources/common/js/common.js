function isNull(value) {
    if (typeof(value) == "undefined" ||null == value|| "" == $.trim(value) || value == "n/a" || value.length == 0) {
        return true;
    } else {
        return false;
    }
}
