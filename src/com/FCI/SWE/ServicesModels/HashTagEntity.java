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



public class HashTagEntity {
	private long id;
	private String name;
	private String postId;
	
	public HashTagEntity(String id) {
		// TODO Auto-generated constructor stub
		this.id = Integer.parseInt(id);
		this.name = "";
		this.postId = "";
	}
	public HashTagEntity(long postId , String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.postId = Long.toString(postId) + " ";
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)) {
				this.id = entity.getKey().getId();
				getHashTag();
				updateHashTag(postId);
				return;
			}
		}
		saveHashTag();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	public Boolean saveHashTag() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		Query gaeQuery = new Query("hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		try {
			id = list.size() + 2;
			Entity hashtag = new Entity("hashtag", id);
			
			hashtag.setProperty("name", name);
			hashtag.setProperty("postId", postId);
			
			
			datastore.put(hashtag);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
	
	public boolean getHashTag() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		try {
			Key key = KeyFactory.createKey("hashtag", id);
			try {
				Entity entity = datastore.get(key);
				name = entity.getProperty("name").toString();
				postId = entity.getProperty("postId").toString();
				
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

	public boolean updateHashTag(long PostId) {
		if(postId.indexOf(Long.toString(PostId)) == -1)
			postId += Long.toString(PostId) + " ";
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		try {
			Entity hashtag = new Entity("hashtag", id);
			
			hashtag.setProperty("name", name);
			hashtag.setProperty("postId", postId);
			
			
			datastore.put(hashtag);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
	
	public String printPosts(){
		String ret = "";
		String PostId = "" , ID = postId;
		for(int i = 0; i < ID.length() ; i++){
			if(ID.charAt(i) == ' '){
				PostEntity postEntity = new PostEntity();
				postEntity.getPost(Long.parseLong(PostId));
				ret += "<br>" + postEntity.getOwner() + "<br>" + 
			       "___________________________________________" + "<br>"
			       + postEntity.getContent() + "<br>" +
			       "___________________________________________" + "<br>" +
			       "Likes " + postEntity.getNumberOfLikes() + "<br>" + 
			       "___________________________________________" + "<br>";
				PostId = "";
			}
			else PostId += ID.charAt(i);
		}
		return ret;
	}
	
}
