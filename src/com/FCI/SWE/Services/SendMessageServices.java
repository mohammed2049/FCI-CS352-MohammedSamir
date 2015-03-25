package com.FCI.SWE.Services;

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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.GroupChatEntity;
import com.FCI.SWE.ServicesModels.*;
import com.google.appengine.api.datastore.Key;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class SendMessageServices {
	@POST
	@Path("/SendMessageService")
	public String SendMessageService(@FormParam("reciever") String reciever,
			@FormParam("message") String message) {
		JSONObject object = new JSONObject();
		if (UserEntity.currentUser == null) {
			object.put("Status", "Failed");
		} else {

			SendMessageEntity sendmessage = new SendMessageEntity(reciever,message);
			sendmessage.saveMessage();

//			SingleChatNotification singleMessageNotification = new SingleChatNotification(reciever, (int) sendmessage.getId());
//			singleMessageNotification.nnotify();

			object.put("Status", "Ok");
			object.put("messages", sendmessage.getMessages());
		}
		return object.toString();
	}

	@POST
	@Path("/GetMessageService")
	public String getMessageService(@FormParam("reciever") String reciever) {
		JSONObject object = new JSONObject();
		if (UserEntity.currentUser == null) {
			object.put("Status", "Failed");
		} else {

			SendMessageEntity sendmessage = new SendMessageEntity(reciever, "");

			object.put("Status", "Ok");
			object.put("messages", sendmessage.getMessages());
		}
		return object.toString();
	}
}
