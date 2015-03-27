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

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
import com.FCI.SWE.Models.SingleChatNotificationModel;
import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;

@Path("/")
@Produces("text/html")
public class SingleMessageController {
	@GET
	@Path("SendMessagePage")
	public Response sendMessagePage() {
		if (UserEntity.currentUser == null)
			return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("reciever", "");
		map.put("messages", "");
		return Response.ok(new Viewable("/jsp/sendMessage", map)).build();
	}
	
	@POST
	@Path("/SendMessage")
	public Response sendMessage(@FormParam("reciever") String reciever, @FormParam("message") String message) {
		String serviceUrl = "http://localhost:8888/rest/SendMessageService";
		String urlParameters = "reciever=" + reciever + "&message=" + message;
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

			map.put("reciever", reciever);
			map.put("messages", object.get("messages").toString());
			
			return Response.ok(new Viewable("/jsp/sendMessage", map)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/GetMessage")
	public Response getMessage(@FormParam("reciever") String reciever) {
		SingleChatNotificationModel.deletenotification(UserEntity.currentUser.getEmail(), reciever);
		String serviceUrl = "http://localhost:8888/rest/GetMessageService";
		String urlParameters = "reciever=" + reciever;
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

			map.put("reciever", reciever);
			map.put("messages", object.get("messages").toString());
			
			return Response.ok(new Viewable("/jsp/sendMessage", map)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}



