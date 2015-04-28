<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>User Home</title>
</head>

<body>
	<p>
		Friend Request Report: <br>
	
	</p>
	<form action="/social/SendRequest" method="post">
		Send Friend Request To Email: <input type="text" name="receiverEmail" />
		<input type="submit" value="Send Friend Request">
	</form>

	<form action="/social/GroupChatData" method="get">
		<input type="submit" value="Start Group Chat">
	</form>
	<a href="/social/LogOut/">Log out</a>
	<a href="/social/myPages"> my pages</a>
	<br>
	<a href="/social/notifications">My Notifications</a>
	<a href="/social/createPage">Create Page</a>
	<br>
  	<form action="/social/SendMessagePage" method="get">
  		<input type="submit" value="Send Message">
  	</form>
  	<form action="/social/GroupChatData" method="get">
  		<input type="submit" value="Start Group Chat">
  	</form>
  	<form action="/social/HashTagPage" method="get">
  		<input type="submit" value="HashTag Search">
  	</form>
  	<a href="/social/LogOut/">Log out</a> <br>

</body>
</html>