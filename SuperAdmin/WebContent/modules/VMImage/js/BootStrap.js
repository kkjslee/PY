//rshao
$(function() {
    registerTemplate();

    setup();

    initUi();
});

function getURLParameter(name) {
    return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]);
}

function registerTemplate() {
    $.template("vmImageList", Template_VMImageList);
    $.template("messageBoxTemplate", Template_MessageBox);
    $.template("vmImageRow", Template_VMImagekRow);
    $.template("createImageItem", Template_CreateVMImageItem);
}

function setup() {
    $("#mainBody").empty();

    $("#banner").html(Locale["vmimage.banner"]);

    var vmImageList = $.tmpl("vmImageList", [{id: "vmImageList"}]).appendTo("#mainBody");
    // set up highlight & selection effect for image list
    $(vmImageList).delegate(".vmImagekRow", "mouseover", function() {
        $(this).addClass("hover");
    }).delegate(".vmImagekRow", "mouseout", function() {
        $(this).removeClass("hover");
    });

    loadVMImageList();
}

function loadVMImageList() {
    var container = $("#vmImageList tbody");
    $("<tr style='border:none'><td cospan='7'><em>" + Locale["vmimage.message.loading"] + "</em></td></tr>").appendTo(container.empty());

    $.ajax({
        url: Server + "/RedDragonEnterprise/VMImageManagement",
        type: "POST",
        data: {
            methodtype: "fetchvmimages"
        },
        cache: false,
        success: function(data) {
            try {
                data = $.parseJSON(data);

                container.empty();

                if (!data || data.length == 0) {
                    $("<em>" + Locale["vmimage.message.no_data"] + "</em>").appendTo(container);
                } else {
                    var list = $.tmpl("vmImageRow", data).appendTo(container);
                    for (var i = 0; i < list.length; i++) {
                        i % 2 == 0 ? $(list[i]).addClass("rowEven") : $(list[i]).addClass("rowOdds");
                    }
                }

            } catch(e) {
                printMessage("Data Broken: [" + e + "]");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            printError(jqXHR, textStatus, errorThrown);
        }
    });
}

function showCreateVMImageItem() {
    var createImageItem = $.tmpl("createImageItem", [{
        id: "createImageItem"
    }]).appendTo("#mainBody");

    createImageItem = $(createImageItem).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + Locale["vmimage.dialog.createimage"],
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: Locale["vmimage.dialog.confirm"],
            click: function() {
                var imagepath = $(this).find("input[zmlm\\\:item='imagepath']").val();
                var imagefolder = $(this).find("input[zmlm\\\:item='imagefolder']").val();
                var imagename = $(this).find("input[zmlm\\\:item='imagename']").val();
                var imageos = $(this).find("input[zmlm\\\:item='imageos']").val();

                var imagesize = $(this).find("input[zmlm\\\:item='imagesize']").val();
                var imagetype = $(this).find("input[zmlm\\\:item='imagetype']").val();

                if (!checkValidField(imagepath, imagefolder, imagename, imageos, imagesize, imagetype)) {
                    printMessage(Locale["vmimage.message.choose.field"]);
                    return;
                }

                if (!checkNum(imagesize)) {
                    printMessage(Locale["vmimage.message.choose.size"]);
                    return;
                }
                createVMImageItem(imagepath, imagefolder, imagename, imageos, imagesize, imagetype);
                $(this).dialog("close");
            }
        },
        {
            text: Locale["vmimage.dialog.cancel"],
            click: function() {
                $(this).dialog("close");
            }
        }]
    });
    $("#createImageItem").dialog("open");
    selectChangeBind("createImageItem", "selimagetype");
}

function selectChangeBind(container, id) {
    //change event for image type,default value is hvm
    $(".typeSelect").delegate("#" + id, "change", function() {
        var id = $(this).attr("id") || $(this).attr("name");
        var idDiv = "#" + id + "Input";
        // remove old div
        if ($(idDiv).length > 0) {
            $(idDiv).remove();
        }
        //remove old input for the position has been changed
        var oldSelect = $("#" + container).find("input[zmlm\\\:item='imagetype']");
        if (oldSelect.length > 0) {
            oldSelect.remove();
        }
        // new select div
        var divHtml = "<div style='display:none;overflow:hidden' id='" + id + "Input'><input type='text' zmlm:item='imagetype' value='hvm' /></div>";
        $(this).attr("tabindex", -1).after(divHtml);
        // set former selected value
        $(idDiv).find("input[zmlm\\\:item='imagetype']").val($(this).val());
        if ($(this).val() == "other") {
            $(idDiv).css({
                position: "absolute",
                top: $(this).position().top,
                left: $(this).position().left
            }).show();
            $(idDiv).find("input[zmlm\\\:item='imagetype']").val("").css({
                width: $(this).width() - 19,
                height: $(this).height() - 4
            }).focus();
            // ie6 fix
            $(idDiv).bgIframe();
        }
    });
}
function createVMImageItem(imagepath, imagefolder, imagename, imageos, imagesize, imagetype) {

    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/RedDragonEnterprise/VMImageManagement",
        type: "POST",
        data: {
            methodtype: "savevmimages",
            imagepath: imagepath,
            imagefolder: imagefolder,
            imagename: imagename,
            imageos: imageos,
            imagesize: imagesize,
            imagetype: imagetype
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {
                data = $.parseJSON(data);

                switch (data.status) {
                case "done":
                    printMessage(Locale["vmimage.message.image.new.success"]);
                    loadVMImageList();
                    break;
                case "failed":
                    ;
                case "exception":
                    printMessage(Locale["vmimage.message.image.new.error"]);
                    break;
                default:
                    printMessage(Locale["vmimage.message.image.undefined"].sprintf(data.status));
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

function showUpdateVMImageItem(which) {
    var row = $(which).parents(".vmImagekRow").first();

    var imageid = $(row).find("input[zmlm\\\:item='imageid']").val();
    var imagepath = $(row).find("input[zmlm\\\:item='imagepath']").val();
    var imagefolder = $(row).find("input[zmlm\\\:item='imagefolder']").val();
    var imagename = $(row).find("input[zmlm\\\:item='imagename']").val();
    var imageos = $(row).find("input[zmlm\\\:item='imageos']").val();
    var imagesize = $(row).find("input[zmlm\\\:item='imagesize']").val();
    var imagetype = $(row).find("input[zmlm\\\:item='imagetype']").val();
    var updateVMImagePanel = $.tmpl("createImageItem", [{
        id: "updateImageItem"
    }]).appendTo("#mainBody");
    updateVMImagePanel = $(updateVMImagePanel).dialog({
        title: "<span class=\"ui-icon ui-icon-circle-plus smallIcon\"></span>" + Locale["vmimage.label.operation.updateImage"],
        modal: true,
        autoOpen: false,
        resizable: false,
        show: "slide",
        hide: "slide",
        width: "400px",
        buttons: [{
            text: Locale["vmimage.dialog.confirm"],
            click: function() {
                var imagepath = $(this).find("input[zmlm\\\:item='imagepath']").val();
                var imagefolder = $(this).find("input[zmlm\\\:item='imagefolder']").val();
                var imagename = $(this).find("input[zmlm\\\:item='imagename']").val();
                var imageos = $(this).find("input[zmlm\\\:item='imageos']").val();

                var imagesize = $(this).find("input[zmlm\\\:item='imagesize']").val();
                var imagetype = $(this).find("input[zmlm\\\:item='imagetype']").val();
                if (!checkValidField(imagepath, imagefolder, imagename, imageos, imagesize, imagetype)) {
                    printMessage(Locale["vmimage.message.choose.field"]);
                    return;
                }
                if (!checkNum(imagesize)) {
                    printMessage(Locale["vmimage.message.choose.size"]);
                    return;
                }
                updateVMImageItem(imageid, imagepath, imagefolder, imagename, imageos, imagesize, imagetype);

                $(this).dialog("destroy");

            }
        },
        {
            text: Locale["vmimage.dialog.cancel"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
    // set former data
    $(updateVMImagePanel).find("input[zmlm\\\:item='imagepath']").val(imagepath);
    $(updateVMImagePanel).find("input[zmlm\\\:item='imagefolder']").val(imagefolder);
    $(updateVMImagePanel).find("input[zmlm\\\:item='imagename']").val(imagename);
    $(updateVMImagePanel).find("input[zmlm\\\:item='imageos']").val(imageos);
    $(updateVMImagePanel).find("input[zmlm\\\:item='imagesize']").val(imagesize);
    // set selected value
    var imageSelectType = $(updateVMImagePanel).find("select[zmlm\\\:item='imagetype'] option");
    var count = imageSelectType.length;
    var has = false;
    for (var i = 0; i < count; i++) {
        if (imageSelectType[i].text == imagetype && imagetype != "hvm") {
            imageSelectType[i].selected = true;
            has = true;
            break;
        }
        if (imageSelectType[i].text == 'windows' &&  imagetype == 'hvm') {
            imageSelectType[i].selected = true;
            has = true;
            break;
        }
    }
    //if the selected value is underfined, append it
    if (!has) {
        $(updateVMImagePanel).find("select[zmlm\\\:item='imagetype']").append("<option value='" + imagetype + "' selected='true'></option>");
        $(updateVMImagePanel).find("option[selected='true']").text(imagetype);
    }
    //set input value for select type
    $(updateVMImagePanel).find("input[zmlm\\\:item='imagetype']").val(imagetype);
    $(updateVMImagePanel).dialog("open");
    selectChangeBind("updateImageItem", "selimagetype");
}
function checkValidField(imagepath, imagefolder, imagename, imageos, imagesize, imagetype) {

    if (isNull(imagepath) || isNull(imagefolder) || isNull(imagename) || isNull(imagesize) || isNull(imagetype)) {
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
function updateVMImageItem(imageid, imagepath, imagefolder, imagename, imageos, imagesize, imagetype) {

    var pd = showProcessingDialog();
    $.ajax({
        url: Server + "/RedDragonEnterprise/VMImageManagement",
        type: "POST",
        data: {
            methodtype: "updatevmimages",
            imageid: imageid,
            imagepath: imagepath,
            imagefolder: imagefolder,
            imagename: imagename,
            imageos: imageos,
            imagesize: imagesize,
            imagetype: imagetype
        },
        cache: false,
        success: function(data) {
            $(pd).dialog("close");
            try {
                data = $.parseJSON(data);

                switch (data.status) {
                case "done":
                    printMessage(Locale["vmimage.message.image.update.success"]);
                    loadVMImageList();
                    break;
                case "failed":
                    ;
                case "exception":
                    printMessage(Locale["vmimage.message.image.update.error"]);
                    break;
                default:
                    printMessage(Locale["vmimage.message.image.undefined"].sprintf(data.status));
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
function remove(which) {
    var row = $(which).parents(".vmImagekRow").first();
    var removeid = $(row).find("input[zmlm\\\:item='imageid']").val();
    var imagename = $(row).find("input[zmlm\\\:item='imagename']").val();
    if (!confirm(Locale["vmimage.confirm.vm.remove"].sprintf(imagename))) return;
    var pd = showProcessingDialog();
    $.ajax({
        type: "POST",
        url: Server + "/RedDragonEnterprise/VMImageManagement",
        cache: false,
        data: {
            methodtype: "deletevmimages",
            imageid: removeid
        },
        success: function(data) {
            pd.dialog("destroy");
            try {
                data = $.parseJSON(data);
                var msg = "";
                switch (data.status) {
                case "done":
                    msg = Locale["vmimage.message.remove.done"];
                    break;
                case "failed":
                    ;
                case "exception":
                    msg = Locale["vmimage.message.remove.fail"];
                    break;
                default:
                    msg = Locale["vmimage.message.remove.undefined"].sprintf(data.status);
                }
                printMessage(msg);
                loadVMImageList();
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
            text: Locale["vmimage.dialog.close"],
            click: function() {
                $(this).dialog("destroy");
            }
        }]
    });
}

function showProcessingDialog() {
    var view = $("<div style='text-align:center;'><img src='css/image/progress.gif'/>" + Locale["vmimage.dialog.processing"] + "</div>").dialog({
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

function initUi() {
    $("button").button();
}

function formatSize(bytes) {
    if (null == bytes || 0 == bytes) return "--";

    var i = 0;
    while (1023 < bytes) {
        bytes /= 1024; ++i;
    };
    return i ? bytes.toFixed(2) + ["", " KB", " MB", " GB", " TB"][i] : bytes + " bytes";
}

function formatOstype(ostype) {
    if (ostype.toLowerCase().match("win")) {
        return "<img style='width:16px;margin-right:4px;' src='css/image/windows.png'/>" + ostype;
    } else if (ostype.toLowerCase().match("linux|centos|ubuntu|debian")) {
        return "<img style='width:16px;margin-right:4px;' src='css/image/linux.png'/>" + ostype;
    } else {
        return "<span class='ui-icon ui-icon-help' style='float:left; margin:0 4px 0 0;'></span>" + ostype;
    }
}
function limitString(limit, value) {
    var breakValue = value;
    if (!isNull(breakValue)) {
        var valueLength = breakValue.length;
        if (valueLength > limit) {
            breakValue = value.substring(0, limit) + "...";
        }
    }
    return breakValue;
}

function checkNum(num) {
    var re = /^[1-9]\d*$/;
    if (!re.test(num)) {
        return false;
    } else return true;
}