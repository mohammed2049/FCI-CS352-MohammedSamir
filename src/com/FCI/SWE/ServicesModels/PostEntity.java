package com.FCI.SWE.ServicesModels;

import java.util.*;

import javax.ws.rs.Path;

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

public class PostEntity {

	private String owner;
	private String content;
	private long timelineid;
	private String privacy;
	private long id;
	private long numberoflikes;
	public PostEntity(String owner, String content, String timelineid, String privacy) {
		this.owner = owner;
		this.content = content;
		this.timelineid = Integer.parseInt(timelineid);
		this.privacy = privacy;
		numberoflikes = 0;
	}
	public PostEntity() {
		owner = null;
		content = null;
		privacy = null;
		numberoflikes = 0;
	}
	
	public PostEntity(String owner, String content, long timelineid, String privacy, long postid, long numberoflikes) {
		this.owner = owner;
		this.content = content;
		this.timelineid = timelineid;
		this.privacy = privacy;
		this.id = postid;
		this.numberoflikes = numberoflikes;
	}
	public long getId() {
		return id;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public long getTimelineid() {
		return timelineid;
	}
	
	public String getPrivacy() {
		return privacy;
	}
	
	public String getContent() {
		return content;
	}
	
	public long getNumberOfLikes() {
		return numberoflikes;
	}
	public Boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		try {
			id = list.size() + 2;
			Entity post = new Entity("post", id);
			
			post.setProperty("owner", owner);
			post.setProperty("content", content);
			post.setProperty("timelineid", timelineid);
			post.setProperty("privacy", privacy);
			post.setProperty("numberoflikes", numberoflikes);
			
			
			datastore.put(post);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}

	public Boolean saveSharedPost(String timelineid, long numberoflikes) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		try {
			id = list.size() + 2;
			Entity post = new Entity("post", id);
			
			post.setProperty("owner", owner);
			post.setProperty("content", content);
			post.setProperty("timelineid", timelineid);
			post.setProperty("privacy", privacy);
			post.setProperty("numberoflikes", numberoflikes);
			
			
			datastore.put(post);
			txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
	
	public List<String> getHashTags() {
		List<String> ret = new ArrayList<String> ();
		String cur = "";
		
		String s = content + " ";
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) == ' ') {
				if (cur.length() != 0) {
					if (cur.charAt(0) == '#') ret.add(cur);
				}
				cur = "";
			} else {
				cur += s.charAt(i);
			}
		}
		
		return ret;
	}
	public Boolean getPost(long id) {
		this.id = id;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		try {
			Key key = KeyFactory.createKey("post", id);
			try {
				Entity entity = datastore.get(key);
				content = entity.getProperty("content").toString();
				timelineid = Long.parseLong(entity.getProperty("timelineid").toString());
				privacy = entity.getProperty("privacy").toString();
				owner = entity.getProperty("owner").toString();
				numberoflikes = Long.parseLong(entity.getProperty("numberoflikes").toString());
				
			} catch (EntityNotFoundException e) {
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
	public List<PostEntity> getTimelinePosts(long timelineid) {
		List<PostEntity> ret = new ArrayList<PostEntity> ();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("timelineid").toString().equals(new Long(timelineid).toString())) {
				String content, privacy, owner;
				long postid, numberoflikes;
				content = entity.getProperty("content").toString();
				privacy = entity.getProperty("privacy").toString();
				owner = entity.getProperty("owner").toString();
				numberoflikes = Long.parseLong(entity.getProperty("numberoflikes").toString());
				postid = entity.getKey().getId();
				PostEntity obj = new PostEntity(owner, content, timelineid, privacy, postid, numberoflikes);
				ret.add(obj);
			}
		}
		
		return ret;
	}
	
}




