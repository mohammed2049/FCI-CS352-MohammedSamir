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
	<%
		long usrid = UserEntity.currentUser.getId();
		long timeLineID = TimeLineEntity.getTimeLineID(usrid);
		
		String urlParameters = "timelineid=" + new Long(timeLineID).toString();

		String retJson = Connection.connect(
				"http://localhost:8888/rest/GetTimeLinePosts",
				urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	%>
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