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
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>${it.page_name}</title>
</head>
<body>
	PageName: ${it.page_name}
	<br/>
	<form method="POST" action="/social/LikePage" >
		<input value="Like" type="submit">
		<input value="${it.page_name}" name="page_name" type="hidden">
	</form>
	<form method="POST" action="/social/PageLikes" >
		<input value="Who Likes" type="submit">
		<input value="${it.page_name}" name="page_name" type="hidden">
	</form>
</body>
</html>