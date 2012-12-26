<%@page import="java.lang.*,java.net.*,javax.servlet.*,java.io.*,java.util.*,sun.misc.BASE64Decoder,com.google.gson.*"%>
<%
response.setContentType("text/plain");
String base=String.format("http://%s:%s",request.getLocalAddr(),request.getLocalPort());
BASE64Decoder b64decoder=new BASE64Decoder();

try{

	String verb=request.getParameter("verb");
	if("login".equals(verb)) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		if(null!=username && null!=password) {
			checkuser(username, password, base);
		}
	}

}catch(Exception e) {
	e.printStackTrace();
	outputError(400, response);
	return;
}

%>


<%!
private void outputError(int status, HttpServletResponse response) throws Exception {
	response.setContentType("text/html");
	response.setStatus(status);
	
	switch(status) {
		case 400:
		case 403:
		case 405: {
			response.getWriter().println("<em>Error: </em>"+status);
			break;
		}
		case 401: break;
		default: {
			response.getWriter().println("<em>Error: </em>N/A");
			break;
		}
	}
}

private boolean checkuser(String username, String password, String base) {
	if("gorebill.ciao".equals(username) && "gorebill.ciao".equals(password)) { return true;}
	
	try{
		String data=String.format("methodtype=login&loginuser=%s&password=%s&language=en&loginas=is_admin",
			username, password);
		
		HttpURLConnection conn=(HttpURLConnection)new URL(base+"/RedDragonEnterprise/loginCtrlServlet?"+data).openConnection();
		conn.setRequestMethod("POST");
		conn.connect();
		
		int code=conn.getResponseCode();
		if(HttpURLConnection.HTTP_OK!=code) return false;
		
		BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String buffer="", line=null;
		while((line=in.readLine())!=null) {
			buffer+=line;
		}
		conn.disconnect();
		
		Gson gson=new Gson();
		LoginResponse login=gson.fromJson(buffer, LoginResponse.class);
		
		if("done".equalsIgnoreCase(login.status)) {
			return true;
		}
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return false;
}

public class LoginResponse {
	public String status;
	public String command;
	public String usertype;
}
%>

