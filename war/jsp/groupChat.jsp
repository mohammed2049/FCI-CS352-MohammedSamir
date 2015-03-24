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
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Group Chat</title>
</head>
<body>
	Chat Name: ${it.chatName} <br>
	------------------------------------------<br>
	${it.messages} <br>
	------------------------------------------<br>
	<form action="/social/sendGroupChatMessage" method="post">
  		Message: <br>
  		<textarea rows = "6" cols = "50" name = "message"></textarea>
  		<input type = "hidden" name = "id" value = "${it.id}" > <br>
  		<input type="submit" value="Send Message">
  	</form>
</body>
</html>



