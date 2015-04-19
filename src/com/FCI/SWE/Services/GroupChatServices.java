package com.FCI.SWE.Services;

import java.io.BufferedReader
;
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
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.Key;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class GroupChatServices {

	@POST
	@Path("/CreateGroupChatService")
	public String createGroupChat(@FormParam("participants") String participants, @FormParam("chatName") String chatName) {
		JSONObject object = new JSONObject();
		if (UserEntity.currentUser == null) {
			object.put("Status", "Failed");
		} else {
			participants += "," + UserEntity.currentUser.getEmail();
			String participantEmail[] = participants.split(",");

			GroupChatEntity groupChat = new GroupChatEntity(participants,
					chatName);
			groupChat.saveGroupChat();

			object.put("Status", "Ok");
			object.put("id", groupChat.getId());
			object.put("messages", groupChat.getMessages());
			object.put("chatName", groupChat.getChatName());
		}
		return object.toString();
	}

	@POST
	@Path("/GetGroupChatDataService")
	public String getGroupChatData(@FormParam("id") String id) {
		JSONObject ret = new JSONObject();

		if (UserEntity.currentUser == null) {
			ret.put("Status", "Failed");
			return ret.toString();
		}

		GroupChatEntity groupChat = new GroupChatEntity(id);
		groupChat.getGroupChat();

		ret.put("Status", "Ok");
		ret.put("messages", groupChat.getMessages());
		ret.put("chatName", groupChat.getChatName());
		return ret.toString();
	}

	@POST
	@Path("/SendGroupChatMessageService")
	public String sendGroupChatMessage(@FormParam("id") String id,
			@FormParam("message") String message) {
		JSONObject ret = new JSONObject();

		if (UserEntity.currentUser == null) {
			ret.put("Status", "Failed");
			return ret.toString();
		}

		GroupChatEntity groupChat = new GroupChatEntity(id);
		groupChat.getGroupChat();
		groupChat.updateMessages(message);

		String participants[] = groupChat.getParticipantsEmails().split(",");
		int n = participants.length;

		List<GroupChatNotification> ls = new ArrayList<GroupChatNotification>();
		for (int i = 0; i < n; ++i){ 
			System.out.println(participants[i]);
			ls.add(new GroupChatNotification(participants[i], Integer.parseInt(id)));
		}
		for (int i = 0; i < n; ++i) 
			ls.get(i).nnotify();
		

		ret.put("Status", "Ok");
		ret.put("messages", groupChat.getMessages());
		ret.put("chatName", groupChat.getChatName());
		return ret.toString();
	}
}
