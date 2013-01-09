<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,sun.misc.BASE64Decoder"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<title>Zhufeng Configuration</title>

<link rel="shortcut icon" href="../../favicon.ico">
<link href="../../css/jquery-ui-1.8.16.enterprise.css" rel="stylesheet" type="text/css" />

<script src="../../locale.jsp" language="javascript"></script>
<script type="text/javascript" src="../../js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="../../js/jquery-ui-1.8.16.enterprise.min.js"></script>
<script type="text/javascript" src="../../js/String.js"></script>
<script type="text/javascript" src="../../js/server.js"></script>

<style>
*{
	font-family: 微软雅黑;
	font-size:14px;
}
table {width:100%;border:1px solid silver;border-collapse:collapse;background:#f7f7f7;}
table td {border:1px solid silver;padding:0px;}
table .thumbnail {max-width:320px;}
table .uploadSpan {display:inline-block;width:160px;text-align:center;vertical-align:middle;cursor:pointer;height:100%;padding-top:25%;}
.highlight {background:orange;}
.processBar {position:absolute;width:0px;height:24px;background:#ffc578;z-index:-1;border-radius:10px;}
</style>

<script>
$(function() {
	$("body").delegate(".uploadSpan", "mouseover", function(){
		$(this).addClass("highlight");
	}).delegate(".uploadSpan", "mouseout", function(){
		$(this).removeClass("highlight");
	});
	
	var elements=$("body").find(".uploadSpan");
	
	for(var i=0; i<elements.length; i++) {
		elements[i].addEventListener("dragenter", dragHandler, false);
		elements[i].addEventListener("dragexit", dragHandler, false);
		elements[i].addEventListener("dragover", dragHandler, false);
		elements[i].addEventListener("drop", dropHandler, false);
	}
});

function dragHandler(event) {
	event.stopPropagation();
	event.preventDefault();
}

function dropHandler(event) {
	event.stopPropagation();
	event.preventDefault();
	
	var files=event.dataTransfer.files;
	var count=files.length;
	uploadType=$(event.target).attr("type");
	
	if(count>0) {
		uploadTotal+=count;
		handleFiles(files);
	}
}

function handleFiles(files) {
	if(files.length>1) {
		alert("I can't afford more than one file once.");
		return;
	}
	
	for(var i=0; i<files.length; i++) {
		var fileName=files[i].name;
		var fileSize=files[i].size;
		
		var subfix=fileName.substring(fileName.lastIndexOf(".")+1);
		if(subfix.toLowerCase()!="png") {
			alert("I wanna a 'png' format image, LoL");
			return;
		}else if(fileSize>1024*1024*2){
			alert("I wanna a file not more than 2MB size, @_@");
			return;
		}
		
		var reader=new FileReader();
		
		reader.onload=handleReaderLoad;
		
		reader.readAsDataURL(files[i]);
	}
}
var uploadType="";
var uploadCount=0;
var uploadTotal=0;
function handleReaderLoad(event) {
	// let's send it to server
	var pd=showProcessingDialog();
	$.ajax({
		url: window.location.pathname,
		type: "POST",
		data: {
			action: "upload",
			type: uploadType,
			data: event.target.result
		},
		cache: false,
		xhr: function() {
			var xhr = jQuery.ajaxSettings.xhr();
			if(xhr.upload) {
				xhr.upload.addEventListener('progress', function(event) {
					var percentage=Math.round(event.loaded*100/event.total);
					$("#processDialog").children("em").html(percentage + "%");
					$("#processDialog").find(".processBar").css("width", (220*percentage/100)+"px");
				}, false);
			}
			return xhr;
		},
		success: function(data) {
			//window.open(data,'_blank','height=300,width=400');
			
			pd.dialog("destroy");
			
			uploadCount++;
			alert("Upload complete!");

			if(uploadCount==uploadTotal) {
				//location.href=location.href;
				window.location.reload();
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			pd.dialog("destroy");
			alert("error!");
		}
	});
}


function showProcessingDialog() {
	var view=$("<div style='text-align:center;' id='processDialog'><div class='processBar'></div><img src='css/img/progress.gif'/>Processing...<em style='color:red;'></em></div>").dialog({
		autoOpen: true,
		width: 240,
		height: 100,
		resizable: false,
		modal: true,
		closeOnEscape: false,
		open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
		buttons: {
		}
	});
	return view;
}

</script>
</head>

<body>
<%
String _SuperAdminLoginBackground="";
String _SuperAdminLoginBackground_RealPath="";
String _SuperAdminLogo="";
String _SuperAdminLogo_RealPath="";
String _EnterpriseLoginBackground="";
String _EnterpriseLoginBackground_RealPath="";
String _EnterpriseLogo="";
String _EnterpriseLogo_RealPath="";
String _EnterpriseBase="";
String _SuperAdminBase="";
	
String path=application.getRealPath("");

File confFile=new File(new File(path), "config.shtml");
String line=null;

{
	if(!confFile.exists()) {
		out.println("Impossible to config it due to I can't find 'config.shtml'. Can you help me?");
	}else{
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(confFile), "utf-8"));
		
		while(null!=(line=reader.readLine())) {
			if(isKeyEquals(line, "EnterpriseBase")) {
				_EnterpriseBase=getValueOfLine(line);
			}else if(isKeyEquals(line, "LoginBackground")) {
				_SuperAdminLoginBackground=getValueOfLine(line);
			}else if(isKeyEquals(line, "Logo")) {
				_SuperAdminLogo=getValueOfLine(line);
			}else if(isKeyEquals(line, "SuperAdminBase")) {
				_SuperAdminBase=getValueOfLine(line);
			}
		}
		
		_SuperAdminLogo_RealPath=new File(path, _SuperAdminLogo).getAbsolutePath();
		_SuperAdminLogo="/"+_SuperAdminBase+"/"+_SuperAdminLogo;
		
		_SuperAdminLoginBackground_RealPath=new File(new File(path, "modules/Login/css"), _SuperAdminLoginBackground).getAbsolutePath();
		_SuperAdminLoginBackground="/"+_SuperAdminBase+"/modules/Login/css/"+_SuperAdminLoginBackground;
		
		// close the stream
		reader.close();
	}
}

// let's get Enterprise's logo and background
{
	path=new File(new File(path).getParentFile(), _EnterpriseBase).getAbsolutePath();
	confFile=new File(new File(path), "config.shtml");

	if(!confFile.exists()) {
		// oops!
	}else{
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(confFile), "utf-8"));
		
		while(null!=(line=reader.readLine())) {
			if(isKeyEquals(line, "LoginBackground")) {
				_EnterpriseLoginBackground=getValueOfLine(line);
			}else if(isKeyEquals(line, "Logo")) {
				_EnterpriseLogo=getValueOfLine(line);
			}
		}
		
		_EnterpriseLogo_RealPath=new File(path, _EnterpriseLogo).getAbsolutePath();
		_EnterpriseLogo="/"+_EnterpriseBase+"/"+_EnterpriseLogo;
		
		_EnterpriseLoginBackground_RealPath=new File(new File(path, "modules/Login/css"), _EnterpriseLoginBackground).getAbsolutePath();
		_EnterpriseLoginBackground="/"+_EnterpriseBase+"/modules/Login/css/"+_EnterpriseLoginBackground;
		
		// close the stream
		reader.close();
	}
}


// process upload image
String action=request.getParameter("action");

if("upload".equals(action)) {
	String type=request.getParameter("type");
	String data=request.getParameter("data");
	
	String imagePath="";
	if(null!=data) {
		if("SuperAdminLogo".equals(type)) {
			imagePath=_SuperAdminLogo_RealPath;
		}else if("SuperAdminBackground".equals(type)) {
			imagePath=_SuperAdminLoginBackground_RealPath;
		}else if("EnterpriseLogo".equals(type)) {
			imagePath=_EnterpriseLogo_RealPath;
		}else if("EnterpriseBackground".equals(type)) {
			imagePath=_EnterpriseLoginBackground_RealPath;
		}else{
			return;
		}
		
		BASE64Decoder decoder=new BASE64Decoder();
		data=data.substring(data.indexOf(",")+1);
		byte[] b=decoder.decodeBuffer(data);
		saveImage(imagePath, b);
		//System.out.println("save image to "+imagePath);
		//saveImage("e:/test_image.png", b);
	}
	
	return;
}


%>

<%!
String getValueOfLine(String line) {
	if(line.matches("^<!--#set +var=\".*\" +value=\".*\".*-->$")) {
		String value;
	
		value=line.replaceFirst("^<!--#set +var=\".*\" +value=\"", "");
		value=value.replaceAll("\".*-->$","");
		
		return value;
	}
	
	return null;
}

Boolean isKeyEquals(String line, String key) {
	if(line.matches("^<!--#set +var=\""+key+"\" +value=\".*\".*-->$")) {
		return true;		
	}
	return false;
}
public void saveImage(String path, byte[] data) {
	File listFile=new File(path);
	FileOutputStream writer=null;

	try{
		if(!listFile.exists()) {
			listFile.createNewFile();
		}
		
		writer=new FileOutputStream(listFile);
		
		writer.write(data);
		writer.flush();
		
		//try{Thread.sleep(2000);}catch(Exception e){};
		
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		if(null!=writer) {try{writer.close();}catch(Exception e){};}
	}
}

%>

<h1>Set up Logo easily</h1>
<hr/>
<p><label style="font-size:12px;color:#AAA;">Attention: This page requires HTML5 supported explorer!</label></p>

<p><label style="font-size:12px;color:red;">
Please backup your origin logo first (You can right click the image you want and choose 'save as...' blah blah.). <br/>
And then ensure your uploading image is in <u>"PNG"</u> format. After doing all of this, you can refresh your cache and check.</label></p>

<table>

<tr>
<td style="white-space:nowrap;">
	<div>The Logo of SuperAdmin</div>
</td><td style="width:100%;text-align:center;">
	<img src="<%=_SuperAdminLogo%>" class="thumbnail"/>
</td><td>
	<span class="uploadSpan" type="SuperAdminLogo">Drag Image Here</span>
</td>
</tr>

<tr>
<td style="white-space:nowrap;">
	<div>The Login Background of SuperAdmin</div>
</td><td style="width:100%;text-align:center;">
	<img src="<%=_SuperAdminLoginBackground%>" class="thumbnail"/>
</td><td>
	<span class="uploadSpan" type="SuperAdminBackground">Drag Image Here</span>
</td>
</tr>

<tr>
<td style="white-space:nowrap;">
	<div>The Logo of Enterprise</div>
</td><td style="width:100%;text-align:center;">
	<img src="<%=_EnterpriseLogo%>" class="thumbnail"/>
</td><td>
	<span class="uploadSpan" type="EnterpriseLogo">Drag Image Here</span>
</td>
</tr>

<tr>
<td style="white-space:nowrap;">
	<div>The Login Background of Enterprise</div>
</td><td style="width:100%;text-align:center;">
	<img src="<%=_EnterpriseLoginBackground%>" class="thumbnail"/>
</td><td>
	<span class="uploadSpan" type="EnterpriseBackground">Drag Image Here</span>
</td>
</tr>
</table>



</body>
</html>