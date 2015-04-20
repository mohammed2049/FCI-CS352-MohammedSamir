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
<title>HashTag Search</title>
</head>
<body>
	<form action="/social/GetHashTag" method="post">
		HashTag: <input type = "text" name = "name" value = "${it.name}"> <br>
  		<input type="submit" value="HashTag Search">
  	</form>
	------------------------------------------<br>
	${it.posts} <br>
	------------------------------------------<br>
</body>
</html>

