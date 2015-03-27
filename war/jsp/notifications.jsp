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
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>My Notifications</title>
</head>
<body>

	<%
		List<Integer> se = GroupChatNotification
				.getgroupids(UserEntity.currentUser.getEmail());
		for (Integer f : se) {
			String S = String
					.format("<form action='/social/OpenGroupChat' method='POST' >"
							+ "<input type='submit' value='go to groupchat%d'>"
							+ "<input type='hidden' value=%d name='id'>"
							+ "</form>", f, f);
			out.print(S);
		}
	%>
	<%
		List<String> se1 = SingleChatNotification.getsenders(UserEntity.currentUser.getEmail());
		
		for (String f : se1) {
			String S = String
					.format("<form action='/social/GetMessage' method='POST' >"
							+ "<input type='submit' value='go to message%s'>"
							+ "<input type='hidden' value='%s' name='reciever'>"
							+ "</form>", f, f);
			System.out.print(S);
			out.print(S);
		}
	%>
	<%
		String urlParameters = "";

		String retJson = Connection.connect(
				"http://localhost:8888/rest/GetFriendRequestsService",
				urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			if (object.get("Status").equals("OK")) {
				Integer i = 0;
				while (object.containsKey("friend" + i.toString())) {
					String S = String
							.format("<form action='/social/AcceptRequest' method='POST'><input type='hidden' name='friendEmail' value=%s><input type='submit' value='Accept %s'></form>",
									object.get("friend" + i.toString()),object.get("friend" + i.toString()));
					out.print(S);
					i++;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	%>
</body>
</html>