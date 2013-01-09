//rshao
var userOTable;
$(function() {
    registerTemplate();

    setup();
    
    initUi();
    
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("userInfoList", Template_UserInfoList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("userInfoRow", Template_UserInfoRow);
    $.template("updateUserItem", Template_UpdateUserItem);
    $.template("rechargeUserItem", Template_RechargeUserItem);
}

function setup() {
    $("#mainBody").empty();

    $("#banner").html(Locale["userinfo.banner"]);

    var userinfoList = $.tmpl("userInfoList", [{
        id: "userInfoList"
    }]).appendTo("#mainBody");

    loadUserInfoList();
}

function getUserInfo(requestedloginname,oldRow) {
	
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/RedDragonEnterprise/UserAccountManagement",
        type: "POST",
        data: {
            methodtype: "getuserinfo",
            loginuser: requestedloginname
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            
            try {
                data = $.parseJSON(data);
              
                if (data.status == "Usernotexists") {
                    printMessage(Locale["userinfo.message.usernotexists"]);
                    return;
                }
               
                if (data.status == "exception") {
                    printMessage(Locale["userinfo.message.userinfo.retrieve.error"]);
                    return;
                }
                if (!data || data.length == 0) {
                	 printMessage(Locale["userinfo.message.data.userinfo.error"].sprintf(loginid));
                     return;
                } else {
                	 updateOldRow(data,oldRow);
                	 printMessage(Locale["userinfo.message.userinfo.retrieve.succees"]);
                }
               

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function updateOldRow(data, oldRow){
	var row = userOTable.fnGetPosition($(oldRow).get(0));
	var count=0;
	if(data.loginid){
		userOTable.fnUpdate(data.loginid, row ,count, 0);
	}
	count++;
	if(data.firstname){
		userOTable.fnUpdate(data.firstname, row ,count, 0);
	}
	count++
	if(data.lastname){
		userOTable.fnUpdate(data.lastname, row ,count, 0);
	}
	count++
	if(data.email){
		userOTable.fnUpdate(data.email, row ,count, 0);
	}
	count++
	if(data.phone){
		userOTable.fnUpdate(data.phone, row ,count, 0);
	}
	count++
	if(data.balance){
		userOTable.fnUpdate(data.balance.toFixed(2), row ,count, 0);
	}
	count++
	if(data.loginstatusdisplay){
		userOTable.fnUpdate(data.loginstatusdisplay, row ,count, 0);
	}
	count++
	if(data.regtime.time){
		userOTable.fnUpdate(formatDate(data.regtime.time), row ,count, 0);
	}
	count++;
	if(data.organisation){
		userOTable.fnUpdate(data.organisation, row ,count, 0);
	}
	count++;
	if(data.role){
		userOTable.fnUpdate(data.role, row ,count, 0);
	}
	
	$(oldRow).find("input[zmlm\\\:item='loginid']").val(data.loginid);
	$(oldRow).find("input[zmlm\\\:item='role']").val(data.role);
	$(oldRow).find("input[zmlm\\\:item='loginstatus']").val(data.loginstatus);
	$(oldRow).find("input[zmlm\\\:item='loginstatusdisplay']").val(data.loginstatusdisplay);
	$(oldRow).find("input[zmlm\\\:item='firstname']").val(data.firstname);
	$(oldRow).find("input[zmlm\\\:item='lastname']").val(data.lastname);
	$(oldRow).find("input[zmlm\\\:item='email']").val(data.email);
	$(oldRow).find("input[zmlm\\\:item='phone']").val(data.phone);
	$(oldRow).find("input[zmlm\\\:item='status']").val(data.status);
	$(oldRow).find("input[zmlm\\\:item='organisation']").val(data.organisation);
	$(oldRow).find("input[zmlm\\\:item='regtime']").val(data.regtime);
	$(oldRow).find("input[zmlm\\\:item='balance']").val(data.balance);
	
}
function loadUserInfoList() {
	if($('.dataTableFile tbody tr').length > 0){
		userOTable.fnClearTable();
	}
    var container = $("#userInfoList tbody");
    $("<tr style='border:none'><td cospan='11'><em>" + Locale["userinfo.message.loading"] + "</em></td></tr>").appendTo(container.empty());
   
    $.ajax({
        url: Server + "/RedDragonEnterprise/UserAccountManagement",
        type: "POST",
        data: {
            methodtype: "getalluserinfo",
            loginuser: getUsername()
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();
                if (data.status == "exception") {
                    printMessage(Locale["userinfo.message.userinfo.retrieve.error"]);
                    return;
                }
                if (!data || data.length == 0) {
                    $("<em>" + Locale["userinfo.message.no_data"] + "</em>").appendTo(container);
                } else {
                    var list = $.tmpl("userInfoRow", data).appendTo(container);
                }
                loadPage();
                fixBannerWidth();

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
    
}

function fixBannerWidth() {
    var tableWidth = $(".dataTableFile").width();
    var bannerWidth = $(".bannerheader").width();
    //margin is 20
  if (tableWidth + 40 > bannerWidth) $(".bannerheader").width(tableWidth + 40);
}
function showUpdateUserInfoItem(which) {
	if($("#updateUserItem").length >0){
		$("#updateUserItem").remove();
	}
    var row = $(which).parents(".userInfoRow").first();

    var loginid = $(row).find("input[zmlm\\\:item='loginid']").val();
    var role = $(row).find("input[zmlm\\\:item='role']").val();
    var loginstatus = $(row).find("input[zmlm\\\:item='loginstatus']").val();
    var loginstatusdisplay = $(row).find("input[zmlm\\\:item='loginstatusdisplay']").val();
    var firstname = $(row).find("input[zmlm\\\:item='firstname']").val();
    var lastname = $(row).find("input[zmlm\\\:item='lastname']").val();
    var email = $(row).find("input[zmlm\\\:item='email']").val();
    var phone = $(row).find("input[zmlm\\\:item='phone']").val();
    var organisation = $(row).find("input[zmlm\\\:item='organisation']").val();
    var updateUserInfoPanel = $.tmpl("updateUserItem", [{
        id: "updateUserItem"
    }]).appendTo("#mainBody");
    updateUserInfoPanel = $(updateUserInfoPanel).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + Locale["userinfo.label.operation.updateUser"],
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: Locale["userinfo.dialog.confirm"],
            click: function() {
                var role2 = $(this).find("select[zmlm\\\:item='role']").val();
                var loginstatus2 = $(this).find("select[zmlm\\\:item='loginstatus']").val();
                var firstname2 = $(this).find("input[zmlm\\\:item='firstname']").val();
                var lastname2 = $(this).find("input[zmlm\\\:item='lastname']").val();
                var email2 = $(this).find("input[zmlm\\\:item='email']").val();
                var phone2 = $(this).find("input[zmlm\\\:item='phone']").val();
                var organisation2 = $(this).find("input[zmlm\\\:item='organisation']").val();

                if (!checkValidFieldForUserInfo(role2, loginstatus2, firstname2, lastname2, email2, phone2, organisation2)) {
                    printMessage(Locale["userinfo.message.choose.field"]);
                    return;
                }
                if (!validEmail(email2)) {
                    printMessage(Locale["userinfo.message.choose.emailinvalid"]);
                    return;
                }

                if (!checkNum(phone2)) {
                    printMessage(Locale["userinfo.message.choose.phoneisno"]);
                    return;
                }

                var ifchangepw = $(this).find("input[zmlm\\\:item='ifchangepw']").attr("checked");
                var newpassword = $(this).find("input[zmlm\\\:item='newpassword']").val();
                var confirmpw = $(this).find("input[zmlm\\\:item='confirmpw']").val();
                if (ifchangepw == "checked") {
                    ifchangepw = "true";
                    if (isNull(newpassword) || isNull(confirmpw)) {
                        printMessage(Locale["userinfo.message.choose.pswnull"]);
                        return;
                    } else if (newpassword != confirmpw) {
                        printMessage(Locale["userinfo.message.choose.pswnotequal"]);
                        return;
                    }
                } else {
                    ifchangepw = "false";
                }
                updateUserInfoItem(loginid, role2, loginstatus2, firstname2, lastname2, email2, phone2, organisation2, ifchangepw, newpassword, row);
                $(this).dialog("destroy");
            }

        },
        {
            text: Locale["userinfo.dialog.cancel"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
    // set former data
    $(updateUserInfoPanel).find("span[zmlm\\\:item='loginid']").html(loginid);
    $(updateUserInfoPanel).find("select[zmlm\\\:item='role']").val(role);
    $(updateUserInfoPanel).find("select[zmlm\\\:item='loginstatus']").val(loginstatus);
    $(updateUserInfoPanel).find("input[zmlm\\\:item='firstname']").val(firstname);
    $(updateUserInfoPanel).find("input[zmlm\\\:item='lastname']").val(lastname);
    $(updateUserInfoPanel).find("input[zmlm\\\:item='email']").val(email);
    $(updateUserInfoPanel).find("input[zmlm\\\:item='phone']").val(phone);
    $(updateUserInfoPanel).find("input[zmlm\\\:item='organisation']").val(organisation);
    $(".ifchangepw").bind("click",
    function() {
        var newpassword = $("input[zmlm\\\:item='newpassword']");
        var confirmpw = $("input[zmlm\\\:item='confirmpw']");
        if ($(this).attr("checked")) {
            $(newpassword).removeAttr("disabled").focus();
            $(confirmpw).removeAttr("disabled");
        } else {
            $(newpassword).val("").attr("disabled", "");
            $(confirmpw).val("").attr("disabled", "");
        }
    });
    $(updateUserInfoPanel).dialog("open");
}

function updateUserInfoItem(loginid, role, loginstatus, firstname, lastname, email, phone, organisation, ifchangepw, newpassword, oldRow) {
    var loginuser = getUsername();
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/RedDragonEnterprise/UserAccountManagement",
        type: "POST",
        data: {
            methodtype: "changeuserinfo",
            loginid: loginid,
            loginuser: loginuser,
            role: role,
            loginstatus: loginstatus,
            firstname: firstname,
            lastname: lastname,
            email: email,
            phone: phone,
            organisation: organisation,
            ifchangepw: ifchangepw,
            newpassword: newpassword
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {
                data = $.parseJSON(data);

                switch (data.status) {
                case "done":
                	getUserInfo(loginid,oldRow);
                    break;
                case "missingdata":
                    printMessage(Locale["userinfo.message.choose.field"]);
                    break;
                case "usernotexist":
                    printMessage(Locale["userinfo.message.user.notexist"].sprintf(loginid));
                    break;
                case "failed":
                    ;
                case "exception":
                    printMessage(Locale["userinfo.message.user.update.error"]);
                    break;
                default:
                    printMessage(Locale["userinfo.message.user.undefined"].sprintf(data.status));
                    break;
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function showRechargeUserInfoItem(which) {
	if($("#rechargeUserItem").length >0){
		$("#rechargeUserItem").remove();
	}
    var row = $(which).parents(".userInfoRow").first();

    var loginuser = getUsername();
    var requestedloginname = $(row).find("input[zmlm\\\:item='loginid']").val();
    var balance = $(row).find("input[zmlm\\\:item='balance']").val();
    var rechargeUserInfoPanel = $.tmpl("rechargeUserItem", [{
        id: "rechargeUserItem"
    }]).appendTo("#mainBody");
    rechargeUserInfoPanel = $(rechargeUserInfoPanel).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + Locale["userinfo.label.operation.recharge.user"],
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: Locale["userinfo.dialog.confirm"],
            click: function() {
                var amount = $(this).find("input[zmlm\\\:item='amount']").val();
                var notes = $(this).find("input[zmlm\\\:item='notes']").val();
                var reason = $(this).find("input[zmlm\\\:item='reason']").val();
                var type = $(this).find("select[zmlm\\\:item='type']").val();

                if (!checkValidFieldForRecharge(amount, notes, reason, type)) {
                    printMessage(Locale["userinfo.message.choose.field"]);
                    return;
                }

                if (!checkAmount(amount)) {
                    printMessage(Locale["userinfo.message.choose.amount"]);
                    return;
                }

                rechargeUserInfoItem(requestedloginname, amount, notes, reason, loginuser, type, row);
                $(this).dialog("destroy");
            }

        },
        {
            text: Locale["userinfo.dialog.cancel"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
    // set former data
    $(rechargeUserInfoPanel).find("span[zmlm\\\:item='requestedloginname']").html(requestedloginname);
    $(rechargeUserInfoPanel).find("span[zmlm\\\:item='balance']").html(balance);
    $(rechargeUserInfoPanel).dialog("open");
}

function rechargeUserInfoItem(requestedloginname, amount, notes, reason, loginuser, type, oldRow) {
    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/RedDragonEnterprise/UserAccountManagement",
        type: "POST",
        data: {
            methodtype: "updatebalance",
            requestedloginname: requestedloginname,
            amount: amount,
            notes: notes,
            reason: reason,
            loginuser: loginuser,
            type: type
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {
                data = $.parseJSON(data);

                switch (data.status) {
                case "done":
                    getUserInfo(requestedloginname,oldRow)
                    break;
                case "missingdata":
                    printMessage(Locale["userinfo.message.choose.field"]);
                    break;
                case "missingadminlogin":
                    printMessage(Locale["userinfo.message.admin.notexist"].sprintf(loginuser));
                    break;
                case "unknownrequesttype":
                    printMessage(Locale["userinfo.message.type.notexist"]);
                    break;
                case "usernotexist":
                    printMessage(Locale["userinfo.message.user.notexist"].sprintf(requestedloginname));
                    break;
                case "failed":
                    ;
                case "exception":
                    printMessage(Locale["userinfo.message.user.recharge.error"]);
                    break;
                default:
                    printMessage(Locale["userinfo.message.user.undefined"].sprintf(data.status));
                    break;
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            pd.dialog("destroy");
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function printError(jqXHR, textStatus, errorThrown) {
    printMessage("Connection Broken: " + textStatus + ", " + errorThrown);
}

function printMessage(msg) {
    return $.tmpl("messageBoxTemplate", [{
        message: msg
    }]).appendTo("#mainBody").dialog({
        resizable: false,
        modal: true,
        buttons: [{
            text: Locale["userinfo.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["userinfo.dialog.processing"] + "</div>").dialog({
        autoOpen: true,
        width: 240,
        height: 100,
        resizable: false,
        modal: true,
        closeOnEscape: false,
        open: function(event, ui) {
            $(".ui-dialog-titlebar-close").hide();
        },
        buttons: {}
    });
    return view;
}
function loadPage(){
	userOTable = $('#userInfoList .dataTableFile').dataTable({
		"fnInitComplete": function(oSettings, json){
		$("#dataTableFile").find("#pager").removeAttr("id").appendTo($("#dataTableFile tfoot").find("td:first-child").empty());
		},
	    "sPaginationType": "full_numbers",
	    "bFilter": true,
	    "bSort": true,
	    "aoColumnDefs": [
	                     { 'bSortable': false, 'aTargets': [6,10] }
	                  ],
	    "bLengthChange": true,
	    "bDestroy":true,
	    "iDisplayLength":50,
	    "sDom": "<'#dataTableFile't<'#pager'lpi>>",
	    "oLanguage": {
	    "sLengthMenu": Locale["userinfo.page.show"],
	    "sInfoEmtpy": Locale["userinfo.page.nodata"],
	    "sZeroRecords": Locale["userinfo.page.foundnodata"], 
	    "sInfoFiltered": Locale["userinfo.page.foundtotal"],
	    "sProcessing": Locale["userinfo.page.loading"],
	    "sInfoEmpty":Locale["userinfo.page.infoempty"],
	    "sInfo": Locale["userinfo.page.startend"],
	    "oPaginate": {
	        "sFirst":  Locale["userinfo.page.first"],
	        "sPrevious": Locale["userinfo.page.previous"],
	        "sNext": Locale["userinfo.page.next"],
	        "sLast": Locale["userinfo.page.last"]
	    }
	}
	});
	 $("#filterHead0 input").keyup( function () {
	        /* Filter on the column (the index) of this element */
		 userOTable.fnFilter( this.value, 0);
	    } );
	 $("#filterHead1 input").keyup( function () {
	        /* Filter on the column (the index) of this element */
		 userOTable.fnFilter( this.value, 4);
	    } );
	 $("thead select").change( function () {
	        /* Filter on the column (the index) of this element */
		 userOTable.fnFilter( this.value, 6);
	    } )
	    
}

function initUi() {
    $("button").button();
}

function validEmail(email) {
    var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    if (!reg.test(email)) {
        return false;
    } else return true;

}

function formatDate(longtime) {
    return new Date(longtime).format("yyyy-MM-dd hh:mm:ss");
}

function checkValidFieldForUserInfo(role, loginstatus, firstname, lastname, email, phone, organisation) {

    if (isNull(role) || isNull(loginstatus) || isNull(firstname) || isNull(lastname) || isNull(email) || isNull(phone)) {
        return false;
    } else {
        return true;
    }
}

function checkValidFieldForRecharge(amount, notes, reason, type) {
    if (isNull(amount) || isNull(notes) || isNull(reason) || isNull(type)) {
        return false;
    } else {
        return true;
    }
}

function isNull(value) {
    if (null == value || "" == $.trim(value) || value == "n/a") {
        return true;
    } else {
        return false;
    }
}

function checkNum(num) {
    var re = /^[1-9]\d*$/;
    if (!re.test(num)) {
        return false;
    } else return true;
}
function checkAmount(num) {
    var re = /^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|([1-9][0-9]*)|([0][.][0-9]+[1-9]*)|(0))$/;
    if (!re.test(num)) {
        return false;
    } else return true;
}

