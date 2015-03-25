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



public class SendMessageEntity {
	private String sender;
	private String reciever;
	private String messages;
	private long id;
	
	public String getMessage() {
		return messages;
	}
	public String getSender() {
		return sender;
	}

	public String getreciever() {
		return reciever;
	}

	public long getId() {
		return id;
	}
	
	public SendMessageEntity(String id) {
		this.id = Integer.parseInt(id);
		messages = "";
		reciever = "";
		sender = "";
	}
	
	public SendMessageEntity(String reciever ,String messages) {
		this.reciever = reciever;
		this.messages = messages;
		sender = UserEntity.currentUser.getEmail();
	}
	
	public Boolean saveMessage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		Query gaeQuery = new Query("singlemessage");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		
		try {
			id = list.size() + 2;
			Entity sendmessage = new Entity("singlemessage", id);
			
			sendmessage.setProperty("messages", messages);
			sendmessage.setProperty("sender", sender);
			sendmessage.setProperty("reciever", reciever);
			
			
			datastore.put(sendmessage);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
	
	public String getMessages() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		try {
			Query gaeQuery = new Query("singlemessage");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			
			String ret = "";

			for (Entity entity : pq.asIterable()) {
				if (entity.getProperty("sender").equals(reciever) || entity.getProperty("sender").equals(sender))
					ret += entity.getProperty("sender") + ": " + entity.getProperty("messages").toString() + "\n";
			}
			
			String nret = "";
			
			for (int i = 0; i < ret.length(); ++i) {
				if (ret.charAt(i) == '\n') {
					nret += "<br>";
				} else {
					nret += ret.charAt(i);
				}
			}
			
			txn.commit();
			return nret;
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
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
			Entity sendmessage = new Entity("sendmessage", id);
			
			sendmessage.setProperty("messages", messages);
			sendmessage.setProperty("sender", sender);
			sendmessage.setProperty("reciever", reciever);
			
			datastore.put(sendmessage);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
}
