<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title><spring:message code="user.user.signup"/></title>
<c:url value="/resource/common" var="rPath"></c:url>
<link rel="stylesheet" href="${rPath}/css/template.css" type="text/css" />
<link rel="stylesheet" href="${rPath}/css/register.css" type="text/css" />
<link type="text/css" href="${rPath}/css/jquery-ui-1.8.16.enterprise.css" rel="stylesheet" />

<script type="text/javascript" src="${rPath}/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="${rPath}/js/jquery-ui-1.8.16.enterprise.min.js"></script>
<script type="text/javascript" src="${rPath}/js/check.js"></script>
<script type="text/javascript" src="${rPath}/js/xml.parser.js"></script>
<script type="text/javascript" src="${rPath}/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="${rPath}/js/jquery.lightbox-0.5.pack.js"></script>
<script type="text/javascript" src="${rPath}/js/util.js"></script>
<script type="text/javascript" src="${rPath}/js/jquery.bt.min.js"></script>
<script type="text/javascript" src="${rPath}/js/jquery.poshytip.js"></script>

<script language="javascript">
$(document).ajaxStop($.unblockUI);


var g_username='';
var g_email='';

$(function(){
    // 初始化各种component
    // Button
    //$("#subBtn").button();
    //$("#radioset").buttonset();

/* 
    // 注册验证码
    $('#verifyimg').click(function() {
        $(this).attr('src', Server+"/RedDragonEnterprise/VerificationServlet"+'?'+Math.random());
        return false;
    }).triggerHandler("click");
    
    // 重发验证码
    $('#resendimg').click(function(){
        $(this).attr('src', Server+"/RedDragonEnterprise/VerificationServlet"+'?'+Math.random());
        return false;
    }); */
    
    $('#etc_10').attr('checked','false');
    // Dialog
    $('#checkDialog').dialog({
        autoOpen: false,
        width: 680,
        //position: ['center',200],
        dragable: false,
        resizable: false,
        modal: true,
        buttons: {
            "返回继续填写": function() { 
                $(this).dialog("close"); 
            }
        }
    });
    
    $('#okDialog').dialog({
        autoOpen: false,
        width: 680,
        modal: true,
        draggable: false,
        //position: ['center',200],
        resizable: false,
        buttons: {
            "关闭": function() { 
                $(this).dialog("close"); 
            }
        }
    });
    
    
    
    // Success Dialog
    $('#successDialog').dialog({
        autoOpen: false,
        width: 600,
        resizable: false,
        buttons: {
            "确认": function() { 
            	  $(this).dialog("close"); 
            }
        }
    });
    
    // 协议窗口
    $('#protocol').click(function() { 
        
        $.blockUI({
            message: $('#protocolContent'), 
            css: {
                cursor: 'default',
                margin: 'auto',
                top: '100px',
                width: '500px',
                height: '400px'
            }
        });
        
        $('.blockOverlay').click($.unblockUI); 
        return false;
    }); 
    
    var tips="";
    // Dialog Link
    // 正确性检测, 并dialog提示
    $('#regBtn').click(function(){
        //gotoPage('#main_content_1', '#main_content_2');
        //return false;
        
        //alert($('#question_ex').val());
        //return false;
        
        var isok=true;
       
        /* var tDisplayName = jQuery.trim($('#tenant_display_name').val());
        var tPhone = jQuery.trim($('#tenant_phone').val());
        var tEmail = jQuery.trim($('#tenant_email').val());
		var tEmail2 = jQuery.trim($('#tenant_email_2').val());
		var tPostcode= jQuery.trim($('#tenant_postcode').val());
		var username = jQuery.trim($('#username').val());
		var uEmail = jQuery.trim($('#user_email').val());
        var uEmail2 = jQuery.trim($('#user_email_2').val());
        var uPassword =  jQuery.trim($('#user_password').val());
        var uPassword2 =  jQuery.trim($('#user_password_2').val());
        var uMobile = jQuery.trim($('#user_mobile').val());
        var uPhone = jQuery.trim($('#user_phone').val());
        if(tDisplayName == '') {
            isok=false;
            tips='公司组织不能为空';
        }else if(tPhone == '') {
        	isok=false;
        	tips='联系电话不能为空(组织)';
        }else if(jQuery.checkstr(tPhone, 'tel')==false){
        	isok=false;
        	tips='联系电话不合法(组织)';
        }else if(tEmail == ''){
			isok=false;
        	tips='邮箱不能为空(组织)';
		}else if(tEmail != tEmail2){
			isok=false;
        	tips='邮箱和确认邮箱不一致(组织)';
		}else if(jQuery.checkstr(tEmail, 'email')==false){
			isok=false;
        	tips='邮箱不合法(组织)';
		}else if(jQuery.trim($('#tenant_country').val()) == '') {
            isok=false;
            tips='国家不能为空(组织)';
        }else if(jQuery.trim($('#tenant_province').val()) == '') {
            isok=false;
            tips='省能为空(组织)';
        }else if(jQuery.trim($('#tenant_city').val()) == '') {
            isok=false;
            tips='城市不能为空(组织)';
        }else if(jQuery.trim($('#tenant_address').val()) == '') {
            isok=false;
            tips='地址不能为空(组织)';
        }else if(tPostcode == '') {
            isok=false;
            tips='邮编不能为空(组织)';
        }else if(jQuery.checkstr(tPostcode, 'postcode') == false){
        	isok=false;
            tips='邮编不合法(组织)';
        }else if(jQuery.checkstr(username, 'username')==false 
                || username.length>45
                || username.length<6) {
            isok=false;
            tips='登录用户名只能使用字母，数字或者下划线，并且至少6个字符，最多45个字符';
        }else if(jQuery.trim($('#user_firstname').val())=='') {
            isok=false;
            tips='姓不能为空';
        }else if(jQuery.trim($('#user_lastname').val())=='') {
            isok=false;
            tips='名不能为空';
        }else if(uEmail=='') {
            isok=false;
            tips='邮箱不能为空(用户)';
        }else if(uEmail != uEmail2){
        	isok=false;
            tips='邮箱和确认邮箱不一致(用户)';
        }else if(jQuery.checkstr(uEmail, 'email')==false){
        	isok=false;
        	tips="邮箱不合法(用户)";
        }else if(uPassword.length<6 || uPassword.length>45) {
            isok=false;
            tips='密码最少为6位, 最多为45位(用户)';
        }else if(uPassword != uPassword2) {
            isok=false;
            tips='密码和确认密码不一致(用户)';                      
        }else if( jQuery.trim($('#user_question_ex').val()).length<6) {
            isok=false;
            tips='密码找回提示问题最少为6位(用户)';
        }else if( jQuery.trim($('#user_answer').val()).length<4) {
            isok=false;
            tips='问题答案最少为4位(用户)';              
        }else if(uMobile == '' && uPhone == '') {
            isok=false;
            tips='电话号码/手机号码不能全为空(用户)';
        }else if(uPhone != '' && jQuery.checkstr(uPhone, 'tel')==false){
        	isok=false;
        	tips='联系电话不合法(组织)';
        }else if(uMobile != '' && jQuery.checkstr(uMobile, 'mobile')==false){
        	isok=false;
        	tips='手机号码不合法(组织)';
        } */
       /* else if($('#etc_10').attr('checked')==false){
            isok=false;
            tips='请阅读并接受有关条款。';
        } */
        /* else if(jQuery.checkstr($('#user_mobile').val(), 'mobile')==false) {
	        isok=false;
	        tips='手机号码不正确。';
	    } */ 
	    	/* if(jQuery.trim($('#verifycode').val())==''){
	        isok=false;
	        tips='请填写验证码。';
	    }else  */
		
        
        if(!isok) {
            $('#tips').html(tips);
            $('#checkDialog').dialog('open');
        }else {
            $('#c_user_username').html($('#username').val());
            $('#c_user_firstname').html($('#user_firstname').val());
            $('#c_user_lastname').html($('#user_lastname').val());
            $('#c_user_email').html($('#user_email').val());
            $('#c_user_question').html($('#user_question_ex').val());
            $('#c_user_answer').html($('#user_answer').val());
            $('#c_user_phone').html($('#t#user_phone').val());
            $('#c_user_mobile').html($('#user_mobile').val());
            $('#c_user_country').html($('#user_country').val());
            $('#c_user_province').html($('#user_province').val());
            $('#c_user_city').html($('#user_city').val());
            $('#c_user_address').html($('#user_address').val());
            $('#c_user_postcode').html($('#user_postcode').val());
            
            $('#c_tenant_display_name').html($('#tenant_display_name').val());
            $('#c_tenant_phone').html($('#tenant_phone').val());
            $('#c_tenant_email').html($('#tenant_email').val());
            $('#c_tenant_country').html($('#tenant_country').val());
            $('#c_tenant_province').html($('#tenant_province').val());
            $('#c_tenant_city').html($('#tenant_city').val());
            $('#c_tenant_address').html($('#tenant_address').val());
            $('#c_tenant_postcode').html($('#tenant_postcode').val());
            
            gotoPage('#main_content_1', '#main_content_2');
            //register_submit();
        }
        
        return false;
    });
    $('#retBtn').click(function() {
        $('#verifyimg').click();
        gotoPage('#main_content_2', '#main_content_1');
        return false;
    });
    $('#subBtn').click(function() {
        register_submit();
        return false;
    });

    // Datepicker
    $('#datepicker').datepicker({
        inline: true
    });
    
    // Slider
    $('#slider').slider({
        range: true,
        values: [17, 67]
    }); 
    
    
});

// 提交注册
function register_submit() {
    //gotoPage('#main_content_2', '#main_content_3');
    //return false;
    
    g_username=$('#username').val();
    g_email=$('#user_email').val();
    
    $.blockUI();
    //alert(jQuery.trim($('#verifycode').val()));
    $.post(
       '<c:url value="/user/doReg" />',
        {
            username: jQuery.trim($('#username').val()),
            password: jQuery.trim($('#user_password').val()),
            firstname: jQuery.trim($('#user_firstname').val()),
            lastname: jQuery.trim($('#user_lastname').val()),
            email: jQuery.trim($('#user_email').val()),
            question: jQuery.trim($('#user_question_ex').val()),
            answer: jQuery.trim($('#user_answer').val()),
            phone: jQuery.trim($('#user_phone').val()),
            mobile: jQuery.trim($('#user_mobile').val()),
            country: jQuery.trim($('#user_country').val()),
            province: jQuery.trim($('#user_province').val()),
            city: jQuery.trim($('#user_city').val()),
            address: jQuery.trim($('#user_address').val()),
            postcode: jQuery.trim($('#user_postcode').val()),
            tenantDisplayName: jQuery.trim($('#tenant_display_name').val()),
            tenantPhone : jQuery.trim($('#tenant_phone').val()),
            tenantEmail : jQuery.trim($('#tenant_email').val()),
            tenantCountry: jQuery.trim($('#tenant_country').val()),
            tenantProvince: jQuery.trim($('#tenant_province').val()),
            tenantCity: jQuery.trim($('#tenant_city').val()),
            tenantAddress: jQuery.trim($('#tenant_address').val()),
            tenantPostcode: jQuery.trim($('#tenant_postcode').val())
            //code: jQuery.trim($('#verifycode').val())
        },
        function(data) {
            try {
                if (data.error) {
                    info = data.error;
                    $('#tips').html(info);
                    $('#checkDialog').dialog('open');
                }else if(data.success){
                	 $('#p3_username').html(g_username);
                     $('#p3_email').html(g_email);
                     $('#resendimg').attr('src', Server+"/RedDragonEnterprise/VerificationServlet"+'?'+Math.random());
                     gotoPage('#main_content_2', '#main_content_3');
               }
                
            }catch(e){}
        }
    );
};
            
$(document).ready(function(){
    // 用户名check
    /* $('#username').blur(function() {
        $.post(
            Server+"/RedDragonEnterprise/loginCtrlServlet",
            {
                methodtype: 'checkuser',
                loginuser: $('#username').val()
            },
            function(data) {
                try{
                    var obj=$.parseJSON(data);
                    var result=obj.status;
                    if(result.toLowerCase()=='userexist' 
                        || jQuery.trim($('#username').val())==''
                        || $('#username').val().length>45
                        || $('#username').val().length<6
                        || jQuery.checkstr($('#username').val(), 'username')==false) {                  
                        $('#tips_username').html('<span class="ui-icon ui-icon-close"></span>');
                        $('#tips_username_2').html('用户名已存在或无效');
                    }else if(result.toLowerCase()=='checkpass') {
                        $('#tips_username').html('<span class="ui-icon ui-icon-check"></span>');
                        $('#tips_username_2').html('');
                    }
                }catch(e){}
            }
        );
    }); */
    
    // 邮箱check
    /* $('#user_email').blur(function() {
        $.post(
            Server+"/RedDragonEnterprise/loginCtrlServlet",
            {
                methodtype: 'checkemail',
                email: $('#user_email').val()
            },
            function(data) {
                try{
                    var obj=$.parseJSON(data);
                    var result=obj.status;
                    if(result.toLowerCase()=='emailexist ' 
                        || jQuery.trim($('#user_email').val())==''
                        || jQuery.checkstr($('#user_email').val(), 'email')==false) {
                        
                        $('#tips_user_email').html('<span class="ui-icon ui-icon-close"></span>');
                        $('#tips_user_email_t2').html('邮箱地址已存在或无效');
                    }else if(result.toLowerCase()=='checkpass') {
                        $('#tips_user_email').html('<span class="ui-icon ui-icon-check"></span>');
                        $('#tips_user_email_t2').html('');
                    }
                }catch(e){}
            }
        );
    }); */
    
    // 邮箱确认验证
    $('#user_email_2').blur(function() {
        if($('#user_email').val() != $('#user_email_2').val() || jQuery.trim($('#user_email_2').val())=='' ) {
            $('#tips_user_email_2').html('<span class="ui-icon ui-icon-close"></span>');
        }else {
            $('#tips_user_email_2').html('<span class="ui-icon ui-icon-check"></span>');
        }
    });
    
    $('#tenant_email_2').blur(function() {
        if($('#tenant_email').val() != $('#tenant_email_2').val() || jQuery.trim($('#tenant_email_2').val())=='' ) {
            $('#tips_tenant_email_2').html('<span class="ui-icon ui-icon-close"></span>');
        }else {
            $('#tips_tenant_email_2').html('<span class="ui-icon ui-icon-check"></span>');
        }
    });
    
    // 条款文本初始化
    //$('#closeProtocol').button();
   /*  $.get('txt/agreement.txt', 
        function(data) {
            $('#agreeTxt').val(data);
        }
    ); */
    /**
    $('#closeProtocol').click(function() {
        $.unblockUI();
    });
    */
    
    $('input[type=text],input[type=password]').attr('style', '{border:1px solid silver;height:16px;}');
    
    //$('#main_content').append($('#main_content_1'));
    
});

function gotoPage(from, to) {
    if(to=='#main_content_1') {
        //$('#main_frame').attr('style', '{height:1200px;}');//1200
        window.scrollTo(0,480);
    }else if(to=='#main_content_2') {
        //$('#main_frame').attr('style', '{height:700px;}');//700
        window.scrollTo(0,100);
    }else if(to=='#main_content_3'){
        //$('#main_frame').attr('style', '{height:80px;}');
        window.scrollTo(0,0);
    }
            
    $(from).hide('slide');
    $(to).show('slide');
    
}


(function( $ ) {
    $.widget( "ui.combobox", {
        _create: function() {
            var self = this,
                select = this.element.hide(),
                selected = select.children( ":selected" ),
                value = selected.val() ? selected.text() : "";
            var input = this.input = $( "<input>" )
                .insertAfter( select )
                .attr('id', select.attr('id')+'_ex')
                .css({width:'180px', "vertical-align":"baseline",height:"20px", "border-right":"0"})
                .val( value )
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: function( request, response ) {
                        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                        response( select.children( "option" ).map(function() {
                            var text = $( this ).text();
                            if ( this.value && ( !request.term || matcher.test(text) ) )
                                return {
                                    label: text.replace(
                                        new RegExp(
                                            "(?![^&;]+;)(?!<[^<>]*)(" +
                                            $.ui.autocomplete.escapeRegex(request.term) +
                                            ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                        ), "<strong>$1</strong>" ),
                                    value: text,
                                    option: this
                                };
                        }) );
                    },
                    select: function( event, ui ) {
                        ui.item.option.selected = true;
                        self._trigger( "selected", event, {
                            item: ui.item.option
                        });
                    },
                    change: function( event, ui ) {
                        if ( !ui.item ) {
                            var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" );
                            valid = false;
                            select.children( "option" ).each(function() {
                                if ( $( this ).text().match( matcher ) ) {
                                    this.selected = valid = true;
                                    return false;
                                }
                            });
                            /*
                            if ( !valid ) {
                                // remove invalid value, as it didn't match anything
                                $( this ).val( "" );
                                select.val( "" );
                                input.data( "autocomplete" ).term = "";
                                return false;
                            }
                            */
                        }
                    }
                })
                .addClass( "ui-widget ui-widget-content ui-corner-left" );

            input.data( "autocomplete" )._renderItem = function( ul, item ) {
                return $( "<li></li>" )
                    .data( "item.autocomplete", item )
                    .append( "<a>" + item.label + "</a>" )
                    .appendTo( ul );
            };

            this.button = $( "<button type='button'>&nbsp;</button>" )
                .attr( "tabIndex", -1 )
                .attr( "title", "常用的提示问题" )
                .insertAfter( input )
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    },
                    text: false
                })
                .css({width:'20px', height:'23px', 'vertical-align':'baseline'})
                .removeClass( "ui-corner-all" )
                .addClass( "ui-corner-right ui-button-icon" )
                .click(function() {
                    // close if already visible
                    if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
                        input.autocomplete( "close" );
                        return;
                    }

                    // pass empty string as value to search for, displaying all results
                    input.autocomplete( "search", "" );
                    input.focus();
                });
        },

        destroy: function() {
            this.input.remove();
            this.button.remove();
            this.element.show();
            $.Widget.prototype.destroy.call( this );
        }
    });
})( jQuery );

$(function() {
    $( "#user_question" ).combobox();
});

function resend() {
    if(jQuery.trim($('#resendcode').val())=='') {
        $('#tips').html('请先填写验证码，再点击重发注册邮件。');
        $('#checkDialog').dialog('open');
        return false;
    }
    
    $.blockUI();
    
    $.post(
        Server+"/RedDragonEnterprise/loginCtrlServlet",
        {
            methodtype: 'resendactivation',
            loginuser: $('#username').val(),
            code: $('#resendcode').val()
        },
        function(data){
            try{
                var obj=$.parseJSON(data);
                var result2=obj.status;
    
                $('#resendimg').click();    
                if(result2=='usernotexist') {
                    $('#tips').html('用户名已不存在，请与管理员联系。');
                    $('#checkDialog').dialog('open');
                }else if(result2=='checkpass') {
                    $('#ok_tips').html('注册邮件已发送至您的邮箱，请注意查收。');
                    $('#okDialog').dialog('open');  
                }else if(result2=='wrongcode') {
                    $('#tips').html('验证码错误，请重新填写。');
                    $('#checkDialog').dialog('open');
                }else if(result2=='invalidemail') {
                    $('#tips').html('邮件地址无效，请填写正确的邮箱地址。');
                    $('#checkDialog').dialog('open');
                }else{
                    //alert(data);
                    $('#tips').html('系统错误（'+result2+'），请与联系管理员。');
                    $('#checkDialog').dialog('open');                       
                }
            }catch(e){}
        }
    );  
}


</script>
<style>*{font-family:"微软雅黑";}td{vertical-align:middle;}</style>
</head>


<body id="body">



<div id="main_frame" class="reg_frame"> <!--124px-->

<div style="margin:0;padding:0;border:0;position:relative;">

  <span style="width:70px;height:20px;position:absolute;right:10px;top:0px;">
    <button style="cursor:pointer;width:100%;background:none;border:0;white-space:nowrap;color:blue;text-decoration:none;" onclick="history.go(-1);">[<u>返回</u>]</button>
  </span>
</div>

<div style="margin:0;padding:0;border:0;height:24px;text-align:left;vertical-align:bottom;cursor:default;">
    <span style="line-height:24px;margin-left:10px;color:#6e6e6e;">当前位置：&nbsp;&nbsp;&nbsp;&nbsp;<label style="color:#00AEEF;cursor:pointer;" onclick="location.href='../../index.html';return false;">首页</label> &gt;&gt; <label style="color:#F60">会员注册</label></span>
</div>

<div id='main_content'></div>

<!-- 主内容 -->
<div id="main_content_1" class="radius5" style="margin:10px;padding:10px;display:block;left:0;position:relative;width:95%;background-color:#EDF2F6">

<div style="margin:20px;padding:0;border:0;text-align:left;">
    <span style="color:#6e6e6e;font-size:12px;font-weight:bold;">提示：请填写你真实有效的个人信息，以便于您办理业务。我们承诺不向第三方提供您的私人信息。</span>
</div>

<div style="margin:20px;padding:0;border:0;text-align:left;color:#F60;font-weight:bold;border-bottom:2px dotted silver;">
<img style="width:20px;border:0;padding:0;margin:0;" src="${rPath}/image/bigimg/reg_img_3.png" />&nbsp;&nbsp;会员登录信息
</div>

<div style="margin:20px;padding:0;border:0;text-align:left;">
    <span style="color:#6e6e6e;font-size:12px;font-weight:bold;">注册成功后，您的用户名将成为您在<!--#echo var="register.platform.name"-->的唯一标识。填写正确的手机号码和E-Mail地址可以使业务沟通更加及时，找回密码更加方便。</span>
</div>

<table class="reg_table">
<tr>
<th></th>
<th></th>
<th width='20px'></th>
<th width='140px'></th>
<th width='160px'></th>
</tr>


<!-- 公司信息 -->
<tr style='text-align:left;'>
    <td colspan='5' style='font-weight:bold;line-height:24px;padding-left:32px;border:1px solid silver;background:url(${rPath}/image/images/menu_downarrow.png) 10px center no-repeat #f0f0f0;'>组织信息</td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="tenant_display_name" class="reg_label">
    	<spring:message code="tenantDisplayName.label" ></spring:message> &nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：
    </label></td>
    <td><input id="tenant_display_name" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div><spring:message code="tenantDisplayName.tip" /></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="tenant_phone" class="reg_label">
    	<spring:message code="tenantPhone.label" ></spring:message> &nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="tenant_phone" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="tenant_email" class="reg_label">
		<spring:message code="tenantEmail.label" ></spring:message> &nbsp;
		<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="tenant_email" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="tenant_email_2" class="reg_label">
 		<spring:message code="confirmEmail.label" ></spring:message>&nbsp;
 		<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="tenant_email_2" class="reg_input" type="text" /></td>
    <td><span id='tips_tenant_email_2'></span></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr>
    <td class="reg_ltd"><label for="tenant_country" class="reg_label">
    	<spring:message code="tenantCountry.label" ></spring:message>&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp; :</label></td>
    <td><input id="tenant_country" class="reg_input" type="text" /></td>
    <td></td>
</tr>
<tr style="border-bottom:1px dashed silver;"><td class="reg_ltd"><label for="tenant_province" class="reg_label">
		<spring:message code="tenantProvince.label" ></spring:message>&nbsp;
		<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="tenant_province" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="tenant_city" class="reg_label">
    	<spring:message code="tenantCity.label" ></spring:message>&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="tenant_city" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="tenant_address" class="reg_label">
    	<spring:message code="tenantAddress.label" ></spring:message>&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="tenant_address" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
	<td class="reg_ltd"><label for="tenant_postcode" class="reg_label">
		<spring:message code="tenantPostcode.label" ></spring:message>&nbsp;
		<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
	<td><input id="tenant_postcode" class="reg_input" type="text" /></td>
	<td></td>
	<td></td>
	<td><div></div></td>
</tr>

<!-- 
<tr style="border-bottom:1px dashed silver;">
<td class="reg_ltd"><label class="reg_label">验证码&nbsp;<label style="color:#F60;font-weight:bold;font-size:18px;">*</label>&nbsp;：</label></label></td>
<td><input maxlength="4" id="verifycode" class="reg_input" type="text" /></td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;<img id='verifyimg' style="width:80px;height:30px;margin:0;padding:0;border:0;border-collapse:collapse;vertical-align:middle;cursor:pointer;" title="点击换一张" /></td>
<td><div style="line-height:28px;">（点击刷新验证码）</div></td>
<td><div></div></td>
</tr>
 -->


<tr><td style="height:40px;">&nbsp;</td><td>&nbsp;</td></tr>

<!-- 基本信息 -->
<tr style='text-align:left;'>
    <td colspan='5' style='font-weight:bold;line-height:24px;padding-left:32px;border:1px solid silver;background:url(${rPath}/image/images/menu_downarrow.png) 10px center no-repeat #f0f0f0;'>管理员信息</td>
</tr>

<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="username" class="reg_label">
    	<spring:message code="username.label" ></spring:message>&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="username" class="reg_input" type="text" /></td>
    <td><span id='tips_username'></span></td>
    <td><div id='tips_username_2' class="text_tip_grid"></div></td>
    <td><div><spring:message code="example.label" ></spring:message>：CloudUser</div></td>
</tr>
<tr style="">
    <td class="reg_ltd"><label class="reg_label">&nbsp;</label></td>
    <td><label class="reg_tips">
    	<spring:message code="username.tip" ></spring:message><br/>
    	(<spring:message code="size.tip" arguments="6,45"/>)
    </label></td>
    <td>&nbsp;</td>
    <td></td>
    <td></td>
</tr>



<!-- 密码部分 -->
<tr><td style="height:10px;">&nbsp;</td><td>&nbsp;</td></tr>
<!--
<tr><td colspan='3' style="padding-right:100px;"><label class="reg_label">为保护您的帐户安全,请选择一个至少8位的密码</label></td></tr>
-->
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_password" class="reg_label">
    	<spring:message code="password.label" />（<spring:message code="size.min.tip" arguments="6"/>）&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_password" class="reg_input" type="password" /></td>
    <td></td>
    <td></td>
    <td><div><spring:message code="example.label" />：cLouD@example</div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_password_2" class="reg_label">
    	<spring:message code="confirmPassword.label" />&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_password_2" class="reg_input" type="password" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd">
    	<label class="reg_label"><spring:message code="question.label" />
    	（<spring:message code="size.min.tip" arguments="6"/>）&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td style="text-align:left;">
    <select id='user_question' title="密码找回提示问题">
        <option >我就读的第二所学校的名称？</option>
        <option >我最喜欢的宠物？</option>
        <option >我最喜欢的电影人物？</option>
        <option >我最喜欢的歌曲？</option>
        <option >我的初恋日期？</option>
        <option >我的祖父有多少个孩子？</option>
    </select>
    <!--<input id="question" class="reg_input" type="text" />-->
    </td>
    <td></td>
    <td></td>
    <td><div><spring:message code="example.label" />: 我的云主机是？</div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_answer" class="reg_label">
    	<spring:message code="answer.label" />(<spring:message code="size.min.tip" arguments="4"/>)）&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_answer" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div><spring:message code="example.label"/>：CloudUser</div></td>
</tr>

<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_lastname" class="reg_label">
    	<spring:message code="lastname.label" />&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_lastname" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>

<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_firstname" class="reg_label">
    	<spring:message code="firstname.label"/>&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_firstname" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>

<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_email" class="reg_label">
    	<spring:message code="email.label" />&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_email" class="reg_input" type="text" /></td>
    <td><span id='tips_user_email'></span></td>
    <td><span id='tips_user_email_t2' class="text_tip_grid"></span></td>
    <td><div><spring:message code="example.label" />：email@example.com</div></td>
</tr>

<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_email_2" class="reg_label">
    	<spring:message code="confirmEmail.label" />&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_email_2" class="reg_input" type="text" /></td>
    <td><span id='tips_user_email_2'></span></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_mobile" class="reg_label">
    	<spring:message code="mobile.label" />&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_mobile" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div><spring:message code="example.label" />：13400000000</div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_phone" class="reg_label">
    	<spring:message code="phone.label" />&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td><input id="user_phone" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div><spring:message code="example.label" />：66668888</div></td>
</tr>

<tr>
	<td class="reg_ltd"><label for="user_country" class="reg_label">
		<spring:message code="country.label" /> :</label></td>
	<td><input id="user_country" class="reg_input" type="text" /></td>
	<td></td>
</tr>
<tr style="border-bottom:1px dashed silver;"><td class="reg_ltd"><label for="user_province" class="reg_label">
		<spring:message code="province.label" />：</label></td>
	<td><input id="user_province" class="reg_input" type="text" /></td>
	<td></td>
	<td></td>
	<td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
	<td class="reg_ltd"><label for="user_city" class="reg_label">
		<spring:message code="city.label" />：</label></td>
	<td><input id="user_city" class="reg_input" type="text" /></td>
	<td></td>
	<td></td>
	<td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;">
    <td class="reg_ltd"><label for="user_address" class="reg_label">
    	<spring:message code="address.label" />: </label></td>
    <td><input id="user_address" class="reg_input" type="text" /></td>
    <td></td>
    <td></td>
    <td><div></div></td>
</tr>
<tr style="border-bottom:1px dashed silver;"><td class="reg_ltd"><label for="user_postcode" class="reg_label">
	<spring:message code="postcode.label" />：</label></td>
<td><input id="user_postcode" class="reg_input" type="text" /></td>
<td></td>
<td></td>
<td><div></div></td>
</tr>

<tr><td colspan="3">
<div style="margin:10px;padding:0;border:0;text-align:center;">
    <span style="color:#6e6e6e;font-size:12px;font-weight:bold;"><label style="color:#F60;font-weight:bold;font-size:18px;vertical-align:middle;">*</label>&nbsp;为必须填写内容</span>
</div>
</td></tr>


<tr>
    <td colspan="5">
        <div style="text-align:center;">
            <input type="checkbox" name="etc_10" id="etc_10" />
            <label for="etc_10" style="font-weight:bold;line-height:48px;font-size:14px;"><i>我已阅读，并理解和接受<a href="#"><!--#echo var="register.platform.name"-->会员注册条款</a></i></label>
            <br/><label style="font-size:12px;">（用户同意此在线注册条款之效力如同用户亲自签字、盖章的书面条款一样，对用户具有法律约束力）</label>
        </div>
    </td>
</tr>


<tr>
    <td colspan="5" style="text-align:center;"><a id="regBtn" style="margin-top:15px;display:inline-block;background:url(${rPath}/image/bigimg/reg_img_4.png);width:165px;height:32px;cursor:pointer;" >&nbsp;</a></td>
</tr>
</table>


</div>






<!-- 主Content2 -->
<div id="main_content_2" style="margin:10px;padding:10px;border:1px solid silver;display:none;left:0;position:relative;width:95%;">

<div style="margin:20px;padding:0;border:0;text-align:left;color:#F60;font-weight:bold;border-bottom:2px dotted silver;">
<img style="width:20px;border:0;padding:0;margin:0;" src="${rPath}/image/bigimg/reg_img_3.png" />&nbsp;&nbsp;请您确认注册信息
</div>

<table class="reg_table">
<tr><th width='200px;'></th><th></th><th width='20px'></th><th width='140px'></th></tr>


<!-- 公司 -->

<tr style='text-align:left;'>
    <td colspan='4' style='font-weight:bold;line-height:24px;padding-left:32px;border:1px solid silver;background:url(${rPath}/image/images/menu_downarrow.png) 10px center no-repeat #f0f0f0;'>组织信息</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="tenantDisplayName.label" ></spring:message>&nbsp;
    	<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_display_name"></label></td>
    <td></td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="tenantPhone.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_phone"></label></td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="tenantEmail.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_phone"></label></td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="tenantCountry.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_country"></label></td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="tenantProvince.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_province"></label></td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
		<spring:message code="tenantCity.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_city"></label></td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="tenantAddress.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_address"></label></td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="tenantPostcode.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_tenant_postcode"></label></td>
    <td>&nbsp;</td><td>&nbsp;</td>
</tr>

<tr><td style="height:40px;">&nbsp;</td><td>&nbsp;</td></tr>
<!-- 基本信息 -->
<tr style='text-align:left;'>
    <td colspan='4' style='font-weight:bold;line-height:24px;padding-left:32px;border:1px solid silver;background:url(${rPath}/image/images/menu_downarrow.png) 10px center no-repeat #f0f0f0;'>基本信息</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="username.label" ></spring:message>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_username"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>

<!-- 密码部分 -->
<tr><td style="height:10px;">&nbsp;</td><td>&nbsp;</td></tr>
<!--
<tr><td colspan='3' style="padding-right:100px;"><label class="reg_label">为保护您的帐户安全,请选择一个至少8位的密码</label></td></tr>
-->
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="question.label" />&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_question"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="answer.label" />&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_answer"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>


<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="lastname.label" />&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_firstname"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>

<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="firstname.label"/>&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_lastname"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>

<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="email.label" />&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_email"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="mobile.label" />&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_mobile"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>

<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label">
    	<spring:message code="phone.label" />&nbsp;<span style="color:#F60;font-weight:bold;font-size:18px;">*</span>&nbsp;：</label></td>
    <td class='reg_rtd'><label id="c_user_phone"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label"><spring:message code="country.label" />：</label></td>
    <td class='reg_rtd'><label id="c_user_country"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label"><spring:message code="province.label" />：</label></td>
    <td class='reg_rtd'><label id="c_user_province"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label"><spring:message code="city.label" />：</label></td>
    <td class='reg_rtd'><label id="c_user_city"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label"><spring:message code="address.label" />：</label></td>
    <td class='reg_rtd'><label id="c_user_address"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr style='border-bottom:1px dashed silver;'>
    <td class="reg_ltd"><label class="reg_label"><spring:message code="postcode.label" />：</label></td>
    <td class='reg_rtd'><label id="c_user_postcode"></label></td><td>&nbsp;</td><td>&nbsp;</td>
</tr>




<tr>
    <td class="reg_ltd">&nbsp;</td>
    <td style='height:48px;vertical-align:bottom;'><a id="subBtn" style="float:left;background:url(${rPath}/image/bigimg/but02.png);width:165px;height:32px;cursor:pointer;" ><!-- 提交注册申请 -->&nbsp;</a>&nbsp;&nbsp;</td>
    <td style='height:48px;vertical-align:bottom;'><a id="retBtn" style="float:left;background:url(${rPath}/image/bigimg/but01.png);width:88px;height:32px;cursor:pointer;" ><!-- 返回 -->&nbsp;</a></td>
    <td>&nbsp;</td>
</tr>
</table>




</div>



<!-- 主Content3 -->
<div id="main_content_3" style="margin:10px;padding:10px;border:1px solid silver;display:none;left:0;position:relative;width:95%;">


<div style="margin:20px;padding:0;border:0;text-align:left;color:#F60;font-weight:bold;border-bottom:2px dotted silver;">
<img style="width:20px;border:0;padding:0;margin:0;" src="${rPath}/image/bigimg/reg_img_3.png" />&nbsp;&nbsp;注册成功
</div>


<div style="margin:20px;padding:0;border:0;text-align:left;font-weight:bold;border-bottom:1px dotted silver;line-height:40px;">
<img src='${rPath}/image/icons/ok.png' />
<label style="color:#0C0;font-size:16px;line-height:40px;">恭喜您<u><span style="color:#F30" id='p3_username'>&nbsp;</span></u>，您的注册已成功！</label><br/>
确认Email已发送至您的邮箱<label style="color:#F30" id='p3_email'>&nbsp;</label>，请您注意查收。若您长时间未收到系统邮件，请检查您邮箱中的过滤设置。<br/>
或者您可以尝试：先填写验证码&nbsp;<input id="resendcode" maxlength="4" type="text" size='4' />
&nbsp;&nbsp;<img id='resendimg' style="width:80px;height:30px;margin:0;padding:0;border:0;border-collapse:collapse;vertical-align:middle;cursor:pointer;" title="点击换一张" />
<label style='font-weight:normal;'>（点击刷新验证码）</label>
，然后&nbsp;[<label title='重发注册邮件到您的邮箱' style='color:orange;text-decoration:underline;cursor:pointer;' onclick='resend();'>重发注册邮件</label>]
<br/>

</div>



<div style="margin:20px;padding:0;border:0;text-align:center;">
<!--<a id="closeBtn" value="提交注册申请" style="float:left;background:url(${rPath}/image/bigimg/but02.png);width:165px;height:32px;cursor:pointer;" >&nbsp;</a>-->
<a href="#" onclick="history.go(-1);">返回</a>
</div>

</div> <!--content 3-->


</div> <!-- main frame -->


<!-- check-dialog -->

<div style='display:none;' id="checkDialog" title="提示">
    <p><img src='${rPath}/image/bigimg/alert.png' style='vertical-align:top;'/>
    <label id="tips" style="line-height:40px;vertical-align:bottom;font-size:18px;font-weight:bold;">&nbsp;</label></p>
</div>

<div style="display:none;" id="successDialog" title="提示">
    <p><label>注册成功</label></p>
</div>

<div style='display:none;' id="okDialog" title="提示">
    <p><img src='${rPath}/image/bigimg/ok.png' style='width:60px;vertical-align:top;'/>
    <label id="ok_tips" style="line-height:40px;vertical-align:bottom;font-size:18px;font-weight:bold;">&nbsp;</label></p>
</div>

<!-- 协议内容 -->
<div style="display:none;" id='protocolContent'>
    <div class='ui-state-highlight ui-corner-all'><span><textarea readonly="readonly" id="agreeTxt" style='background-color:#f0f0ff;width:100%;height:340px;padding-bottom:5px;cursor:default;margin-bottom:5px;border:0;'></textarea></span></div><br/>
    <input type="button" id="closeProtocol" value="关闭" />   
</div>

</body>
</html>