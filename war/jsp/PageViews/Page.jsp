<%@page import="com.FCI.SWE.Models.PageModel"%>
<%@page import="com.FCI.SWE.ServicesModels.TimeLineEntity"%>
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
	
	<form action="/social/CreatePost" method="post">
		<input type="hidden" name="owner" value="${it.name}">
		Content:
		<textarea rows="6" cols="50" name="content"></textarea>
		<br> <input type="hidden" name="timelineid"
			value="${it.time_line_id}"> Post Privacy: <input type="text"
			name="privacy" /> <br> <input type="submit" value="Create Post">
	</form>
	
	<br/>
	<form method="POST" action="/social/LikePage" >
		<input value="Like" type="submit">
		<input value="${it.page_name}" name="page_name" type="hidden">
	</form>
	<form method="POST" action="/social/PageLikes" >
		<input value="Who Likes" type="submit">
		<input value="${it.page_name}" name="page_name" type="hidden">
	</form>
	<%
		
		long timeLineID = TimeLineEntity.getTimeLinePageID(PageModel.curr_page);

		String urlParameters = "timelineid="
				+ new Long(timeLineID).toString();
		String retJson = Connection.connect(
				"http://localhost:8888/rest/GetTimelinePostsService",
				urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			if (object.get("Status").equals("Ok")) {
				Integer size = Integer.parseInt(object.get("Size")
						.toString());
				for (Integer i = 0; i < size; ++i) {
					String S = String.format(
							"<p>POST:%s</p><br/><p>LIKES:%s</p>",
							object.get("content" + i.toString()),
							object.get("numberoflikes" + i.toString()));
					out.print(S);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	%>
	
</body>
</html>