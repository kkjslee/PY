<%@page import="java.io.*"%>
<%@page import="java.lang.annotation.*,java.sql.*,java.util.*,org.apache.commons.lang.*,java.lang.reflect.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="false" isThreadSafe="true" %>
<%
response.setContentType("text/plain");
response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1); //prevents caching at the proxy server

try{
	String path=new File(application.getRealPath("")).getParent();
	File reqDoc=new File(request.getRequestURI());
	
	String exUrl="";
	exUrl="extension/"+reqDoc.getName().substring(0, reqDoc.getName().lastIndexOf("."))+".ex";
	File file=new File(new File(path, reqDoc.getParent()), exUrl);
	
	//out.println(file.getAbsolutePath());
	
	if(file.exists()) {
		String line=null;
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		while(null!=(line=reader.readLine())) {
			out.println(line);
		}
		reader.close();
	}else {
		out.println("// extension file not found");
	}
}catch(Exception e){
	// ...
}

/* append an empty pair to avoid not "," end */
out.println("\"\":\"\"");


%>


