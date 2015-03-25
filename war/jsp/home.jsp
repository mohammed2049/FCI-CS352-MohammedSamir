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
<%
	String ret = "";
	try {
		String urlParameters = "";

		String retJson = Connection.connect(
				"http://localhost:8888/social/GetRequests",
				urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(retJson.toString());
		JSONObject jsonObj = (JSONObject) obj;
		
		if (jsonObj.get("Status").toString().equals("Ok")) {
			int Size = Integer.parseInt(jsonObj.get("Size").toString());
			if (Size == 0) {
				ret = "No friend requests, You are For Ever Alone";
			} else {
				for (int i = 0; i < Size; ++i) {
					ret += "email: "
							+ jsonObj.get("friend" + i).toString()
							+ " Sent you friend request. <br>";
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<body>
	<p>
		Friend Request Report: <br>
		<%=ret%>
	</p>
	<form action="/social/SendRequest" method="post">
		Send Friend Request To Email: <input type="text" name="receiverEmail" />
		<input type="submit" value="Send Friend Request">
	</form>

	<form action="/social/GroupChatData" method="get">
		<input type="submit" value="Start Group Chat">
	</form>
	<a href="/social/LogOut/">Log out</a>
	<br>
	<a href="/social/notifications">My Notifications</a>
	<br>
  	<form action="/social/SendMessagePage" method="get">
  		<input type="submit" value="Send Message">
  	</form>
  	<form action="/social/GroupChatData" method="get">
  		<input type="submit" value="Start Group Chat">
  	</form>
  	
</body>
</html>