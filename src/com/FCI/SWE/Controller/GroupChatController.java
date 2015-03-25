package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.GroupChatNotificationModel;
import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class GroupChatController {
	@GET
	@Path("/GroupChatData")
	public Response createGroupChat() {
		if (UserEntity.currentUser == null)
			return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
		return Response.ok(new Viewable("/jsp/createGroupChat")).build();
	}
	
	@POST
	@Path("/sendGroupChatMessage")
	public Response sendGroupChatMessage(@FormParam("id") String id, @FormParam("message") String message) {
		String serviceUrl = "http://localhost:8888/rest/SendGroupChatMessageService";
		String urlParameters = "id=" + id + "&message=" + message;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed")) {
				return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("chatName", object.get("chatName").toString());
			map.put("messages", object.get("messages").toString());
			
			return Response.ok(new Viewable("/jsp/groupChat", map)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/OpenGroupChat")
	public Response openGroupChatMessage(@FormParam("id") String id) {
		GroupChatNotificationModel.deletenotification(UserEntity.currentUser.getEmail(), Integer.parseInt(id));
		String serviceUrl = "http://localhost:8888/rest/GetGroupChatDataService";
		String urlParameters = "id=" + id;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed")) {
				return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("chatName", object.get("chatName").toString());
			map.put("messages", object.get("messages").toString());
			
			return Response.ok(new Viewable("/jsp/groupChat", map)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/CreateGroupChat")
	public Response groupChatPage(@FormParam("participants") String participantsEmails, @FormParam("chatName") String chatName) {
		String serviceUrl = "http://localhost:8888/rest/CreateGroupChatService";
		String urlParameters = "participants=" + participantsEmails + "&chatName=" + chatName;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed")) {
				return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", object.get("id").toString());
			map.put("chatName", object.get("chatName").toString());
			map.put("messages", object.get("messages").toString());
			
			return Response.ok(new Viewable("/jsp/groupChat", map)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}




