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

	private String name;
	private long postId;
	
	
	public HashTagEntity(String id) {
		this.name = id;
		this.postId = -1;
	}
	
	public HashTagEntity(long postId , String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.postId = postId;
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
		System.out.println("Yes");
		Query gaeQuery = new Query("hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		try {
			long id = list.size() + 2;
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
	
	public String getHashTag() {
		System.out.println("kdjkf");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		String ret = "";
			Query gaeQuery = new Query("hashtag");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			

			for (Entity entity : pq.asIterable()) {
				if (entity.getProperty("name").equals(name)){
								
					name = entity.getProperty("name").toString();
					String aa = entity.getProperty("postId").toString();
					postId = 0;
					System.out.println(postId);
					for(int i = 0 ; i < aa.length() ; i++){
						postId *= 10;
						postId += aa.charAt(i);
					}
					System.out.println(postId);
					ret += printPosts();
				}
			}
				System.out.println(name + "   " + postId);
				
		
		return ret;
	}

	
	public String printPosts(){
		System.out.println(postId);
		String ret = "";
				PostEntity postEntity = new PostEntity();
				postEntity.getPost(this.postId);
				ret += "<br>" + postEntity.getOwner() + "<br>" + 
			       "___________________________________________" + "<br>"
			       + postEntity.getContent() + "<br>" +
			       "___________________________________________" + "<br>" +
			       "Likes " + postEntity.getNumberOfLikes() + "<br>" + 
			       "___________________________________________" + "<br>";
		return ret;
	}
	
}
