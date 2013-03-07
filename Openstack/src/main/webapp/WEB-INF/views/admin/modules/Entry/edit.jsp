<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.inforstack.openstack.utils.OpenstackUtil" %>
<%@ page import="com.inforstack.openstack.i18n.lang.Language" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
<%
  Language language = OpenstackUtil.getLanguage();
  String lang = language.getLanguage() + "-" + language.getCountry();
  lang = lang.toLowerCase();
  if(!lang.equals("zh_cn")){
      lang = "en";
  }
%>
window.UEDITOR_HOME_URL = "<%=request.getContextPath()%>/resource/common/addon/ueditor/";
</script>
<script src="<%=request.getContextPath()%>/resource/common/addon/ueditor/editor_config.js"  charset="utf-8" language="javascript"></script>
<script src="<%=request.getContextPath()%>/resource/common/addon/ueditor/editor_all.js"  charset="utf-8" language="javascript"></script>
<style type="text/css">
        .clear {
            clear: both;
        }
    </style>
</head>
<body>
    <div class="banner"><span ><spring:message code="admin.entry.edit"/></span></div>

    <div id="mainBody" >
    <div>
        <script  id="editor" type="text/plain">${content}</script>
    </div>
    <div class="clear"></div>
    <div id="btns" style="margin:20px">
       <input type="button" class="btn btn-primary btn-large" onclick="saveContent()"  value="<spring:message code="submit.button"/>">
    </div>
    </div>
    <script>
    window.UEDITOR_CONFIG.lang="<%=lang%>";
    var ue = UE.getEditor('editor');

    ue.addListener('ready',function(){
        this.focus();
    });
    function saveContent(){
    	var allhtml = UE.getEditor('editor').getAllHtml();
        var content = UE.getEditor('editor').getContent();
        var pd=showProcessingDialog();
        $.ajax({
            type: "POST",
            dataType: "json",
            cache: false,
            url: '<c:url value="/admin/modules/entry/save"/>',
            data: {
                html:allhtml,
                content: content
            },
            success: function(data) {
                 pd.dialog("destroy");
                 if(data.status=="success"){
                     printMessage(data.msg);
                 }
                 if(data.status=="error"){
                	 printMessage(data.msg);
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