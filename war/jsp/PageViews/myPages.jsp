<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.FCI.SWE.ServicesModels.UserEntity"%>
<%@page import="com.FCI.SWE.Models.PageModel"%>
<%@page import="com.google.apphosting.utils.servlet.DatastoreViewerServlet.Page"%>
<%@page import="com.FCI.SWE.Services.SingleChatNotification"%>
<%@page import="com.FCI.SWE.ServicesModels.UserEntity"%>
<%@page import="com.FCI.SWE.Services.GroupChatNotification"%>
<%@page import="com.FCI.SWE.Services.*"%>
<%@page import="com.FCI.SWE.Models.User"%>
<%@ page import="java.util.*"%>
<%@ page
	import="java.net.URI , 
javax.ws.rs.client.Client , 
javax.ws.rs.client.ClientBuilder , 
javax.ws.rs.client.WebTarget , 
javax.ws.rs.core.MediaType , 
javax.ws.rs.core.Response ,
javax.ws.rs.core.UriBuilder , 
org.glassfish.jersey.client.ClientConfig ,
org.json.simple.JSONObject,
com.FCI.SWE.Controller.Connection,
org.json.simple.parser.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>my Pages</title>
</head>
<body>
<%	
	List<String> pages = PageModel.getPages(UserEntity.currentUser.getEmail());	
	for(String page1:pages){
		String s= String.format("<form action='/social/page' method='POST'> <input type='submit' value='%s' name='page_name'/></form>",page1);
		out.print(s);
	}
%>
		
</body>
</html>