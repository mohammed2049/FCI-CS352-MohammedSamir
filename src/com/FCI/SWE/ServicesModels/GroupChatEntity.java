package com.FCI.SWE.ServicesModels;

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

import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;


public class GroupChatEntity {
	private String chatName;
	private String participantsEmails;
	private String messages;
	private String owner;
	private long id;
	
	public String getChatName() {
		return chatName;
	}
	
	public String getParticipantsEmails() {
		return participantsEmails;
	}
	
	public String getMessages() {
		return messages;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public long getId() {
		return id;
	}
	
	public GroupChatEntity(String id) {
		this.id = Integer.parseInt(id);
		messages = "";
		chatName = "";
		participantsEmails = "";
		owner = "";
	}
	
	public GroupChatEntity(String participantsEmails, String chatName) {
		this.chatName = chatName;
		this.participantsEmails = participantsEmails;
		messages = "";
		owner = UserEntity.currentUser.getEmail();
	}

	public Boolean saveGroupChat() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		Query gaeQuery = new Query("groupchat");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		try {
			id = list.size() + 2;
			Entity groupchat = new Entity("groupchat", id);
			
			groupchat.setProperty("messages", messages);
			groupchat.setProperty("owner", owner);
			groupchat.setProperty("chatName", chatName);
			groupchat.setProperty("participantsEmails", participantsEmails);
			
			
			datastore.put(groupchat);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}

	public boolean getGroupChat() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		try {
			Key key = KeyFactory.createKey("groupchat", id);
			try {
				Entity entity = datastore.get(key);
				messages = entity.getProperty("messages").toString();
				chatName = entity.getProperty("chatName").toString();
				participantsEmails = entity.getProperty("participantsEmails").toString();
				owner = entity.getProperty("owner").toString();
				
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}

	public boolean updateMessages(String message) {
		String nmessage = "";
		for (int i = 0; i < message.length(); ++i) {
			if (message.charAt(i) == '\n') {
				nmessage += "<br>";
			} else {
				nmessage += message.charAt(i);
			}
		}
		messages += "<br>" + UserEntity.currentUser.getName() + ": " + nmessage;
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		try {
			Entity groupchat = new Entity("groupchat", id);
			
			groupchat.setProperty("messages", messages);
			groupchat.setProperty("owner", owner);
			groupchat.setProperty("chatName", chatName);
			groupchat.setProperty("participantsEmails", participantsEmails);
			
			
			datastore.put(groupchat);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
}






