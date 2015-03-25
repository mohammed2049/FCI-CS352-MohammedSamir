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
	------------------------------------------<br>
	${it.messages} <br>
	------------------------------------------<br>
	<form action="/social/SendMessage" method="post">
		Reciever Email: <input type = "text" name = "reciever" value = "${it.reciever}"> <br>
  		Message: <br>
  		<textarea rows = "6" cols = "50" name = "message"></textarea>
  		<input type="submit" value="Send Message">
  	</form>
</body>
</html>



