<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
<div class="banner">
        <span id="banner" >用户充值</span>
</div>
<body>
    <div id="mainBody">
                 <form >
                    <input type="" name="price" id="price" value="">
                 </form>
                <div style="margin:20px;text-align:center;">
                   <input type="button" onclick="submitBasic();" class="btn btn-primary btn-large"  value="确认充值">
                </div>
    </div>
    <script>
    function submitBasic(){
        var pd=showProcessingDialog();
        $.ajax({
            type: "POST",
            url: '<c:url value="/user/pay/topup"/>',
            cache: false,
            data: {
            	price: $("#price").val(),
                
            },
            success: function(data) {
                pd.dialog("destroy");
                try{
                    printMessage(data.result);
                    
                }catch(e) {
                    printMessage("Data Broken: ["+e+"]");
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                pd.dialog("destroy");
                printError(jqXHR, textStatus, errorThrown);
            }
        });
    }
    </script>
</body>
</html>