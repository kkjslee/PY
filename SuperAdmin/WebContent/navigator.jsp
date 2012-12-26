<%@page import="com.google.gson.Gson,java.io.*,java.lang.annotation.*,javax.xml.parsers.*,javax.xml.xpath.*,org.w3c.dom.*,java.util.*,java.lang.reflect.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="false" isThreadSafe="true" %><%
response.setContentType("text/plain");
response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1); //prevents caching at the proxy server

String parent=request.getParameter("parent");
String role=request.getParameter("role");

String path=new File(application.getRealPath("")).getParent();
File file=new File(new File(path, request.getContextPath()), "modules");

Dictionary<String, ArrayList<NavItem>> dict=new Hashtable<String, ArrayList<NavItem>>();

if(null==parent){
	System.out.println("// i need a parent to track");
}else if(null==role){
	System.out.println("// i need a role to track");
}else if(file.exists() && file.isDirectory()) {
	/*
	String line=null;
	BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
	while(null!=(line=reader.readLine())) {
		out.println(line);
	}
	reader.close();
	*/
	
	File[] modules=file.listFiles(new FileFilter(){
		public boolean accept(File modDir) {
			if(modDir.isDirectory()) {
				return true;
			}
			return false;
		}
	});
	
	// parsing all modules' descriptor files
	for(File mod : modules) {
		File moduleDescriptor=new File(mod, "module.xml");
		try{
			if(moduleDescriptor.exists()) {
				//System.out.println("// Found a module descriptor: "+moduleDescriptor);
				
				Document xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(moduleDescriptor);
				
				XPathFactory xPathFactory = XPathFactory.newInstance();
				XPath xPath = xPathFactory.newXPath();
				
				int moduleCount=1;
				
				{
					String expression = "/platform/@count";
					XPathExpression xPathExpression = xPath.compile(expression);
					Object result = xPathExpression.evaluate(xmlDocument);
					
					if(!"".equals(""+result)) {
						int count=Integer.parseInt(""+result);
						moduleCount=count>1?count:moduleCount;
					}
				}
				
				for(int i=1; i<=moduleCount; i++) {
					// ok, let's make a new element
					NavItem item=new NavItem();
					
					{
						String expression = String.format("/platform/module[%d]/@role", i);
						XPathExpression xPathExpression = xPath.compile(expression);
						Object result = xPathExpression.evaluate(xmlDocument);

						String str=""+result;
						String[] roles=str.split(",");
						
						boolean flag=true;
						for(String k : roles) {
							if(role.equals(k.trim())) {
								flag=false;
								break;
							}
						}
						
						if(flag) continue;
					}
					
					{
						String expression = String.format("/platform/module[%d]/parent", i);
						XPathExpression xPathExpression = xPath.compile(expression);
						Object result = xPathExpression.evaluate(xmlDocument);
						item.parent=(""+result).trim();
					}
					
					{
						String expression =  String.format("/platform/module[%d]/name/@source", i);
						XPathExpression xPathExpression = xPath.compile(expression);
						Object result = xPathExpression.evaluate(xmlDocument);
						String source=""+result;
						
						if("local".equals(source)) {
							expression =  String.format("/platform/module[%d]/name", i);
							xPathExpression = xPath.compile(expression);
							result = xPathExpression.evaluate(xmlDocument);
							
							item.name=(""+result).trim();
						}
					}
					
					{
						String expression =  String.format("/platform/module[%d]/license", i);
						XPathExpression xPathExpression = xPath.compile(expression);
						Object result = xPathExpression.evaluate(xmlDocument);
						item.license=(""+result).trim();
					}
					
					{
						String expression =  String.format("/platform/module[%d]/entry", i);
						XPathExpression xPathExpression = xPath.compile(expression);
						Object result = xPathExpression.evaluate(xmlDocument);
						item.entry=(""+result).trim();
					}
					
					{
						String expression =  String.format("/platform/module[%d]/@index", i);
						XPathExpression xPathExpression = xPath.compile(expression);
						Object result = xPathExpression.evaluate(xmlDocument);
						
						if(!"".equals(""+result)) {
							item.index=""+result;
						}else {
							item.index=""+Float.MAX_VALUE;
						}
					}
					
					ArrayList<NavItem> list=dict.get(item.parent);
					if(null==list) {
						list=new ArrayList<NavItem>();
						dict.put(item.parent, list);
					}
					
					list.add(item);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("// Modules descriptor exception: "+moduleDescriptor);
		}
	}
	
	List<NavItem> itemList=dict.get(parent);
	if(null==itemList) {
		itemList=new ArrayList<NavItem>();
	}
	
	Collections.sort(itemList, new Comparator<NavItem>(){
		public int compare(NavItem o1, NavItem o2) {
			int d=(int)((Float.parseFloat(o1.index)-Float.parseFloat(o2.index))*1000000);
			return d;
		}
	});
	
	Gson gson=new Gson();
	String json=gson.toJson(itemList);
	out.print(json);
	
	

}else{
	System.out.println("// Modules directory not found: "+file);
}

%><%!
class NavItem {
	public String parent;
	public String name;
	public String license;
	public String entry;
	public String index;
	
	public NavItem() {
		
	}
}
%>