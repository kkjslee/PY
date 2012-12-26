<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,sun.misc.BASE64Decoder"%>


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
			}else if(isKeyEquals(line, "SuperAdminBase")) {
				_SuperAdminBase=getValueOfLine(line);
			}
		}
	}
}

// process request
String action=request.getParameter("action");

if("update".equals(action)) {
	String type=request.getParameter("normal");
	String data=request.getParameter("data");
	
	File fileToUpdate=null;
	if(null!=data) {
		fileToUpdate=new File(new File(confFile.getParentFile().getParentFile(), _EnterpriseBase), "notice.partial");
		
		if(!saveFile(fileToUpdate, data)){
			response.setStatus(503);
		}
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

public Boolean saveFile(File file, String data) {
	File listFile=file;
	BufferedWriter writer=null;
	Boolean status=true;
	
	try{
		if(!listFile.exists()) {
			listFile.createNewFile();
		}
		
		data=new String(data.getBytes("iso-8859-1"), "utf-8");

		writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(listFile), "utf-8"));

		writer.write(data);
		writer.flush();

		//try{Thread.sleep(2000);}catch(Exception e){};

	}catch(Exception e) {
		e.printStackTrace();
		status=false;
	}finally {
		if(null!=writer) {try{writer.close();}catch(Exception e){};}
	}
	
	return status;
}

%>
