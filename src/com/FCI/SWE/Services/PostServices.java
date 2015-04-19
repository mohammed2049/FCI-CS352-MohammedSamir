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
import com.FCI.SWE.ServicesModels.PostEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.Key;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PostServices {
	@POST
	@Path("/CreatePostService")
	public String CreatePostService(@FormParam("owner") String owner, @FormParam("content") String content, @FormParam("timelineid") String timelineid, @FormParam("privacy") String privacy) {
		JSONObject object = new JSONObject();
		PostEntity postEntity = new PostEntity(owner, content, timelineid, privacy);
		postEntity.savePost();
		/*
		List<String> hashtag = postEntity.getHashTags();
		for (int i = 0; i < hashtag.size(); ++i) {
			HashTagEntity obj = new HashTagEntity(id, hashtag.get(i));
			obj.saveHashTag();
		}
		*/
		object.put("Status", "Ok");
		object.put("owner", owner);
		object.put("content", content);
		object.put("timelineid", timelineid);
		object.put("privacy", privacy);
		object.put("id", postEntity.getId());
		return object.toString();
	}
	
	@POST
	@Path("/GetPostService")
	public String GetPostService(@FormParam("id") String postid) {
		JSONObject object = new JSONObject();
		PostEntity postEntity = new PostEntity();
		
		postEntity.getPost(Long.parseLong(postid));
		
		object.put("Status", "Ok");
		object.put("owner", postEntity.getOwner());
		object.put("content", postEntity.getContent());
		object.put("timelineid", postEntity.getTimelineid());
		object.put("privacy", postEntity.getPrivacy());
		object.put("id", postEntity.getId());
		object.put("numberoflikes", postEntity.getNumberOfLikes());
		return object.toString();
	}
	
	@POST
	@Path("/GetTimelinePostsService")
	public String GetTimelinePostsService(@FormParam("timelineid") String timelineid) {
		JSONObject object = new JSONObject();
		PostEntity postEntity = new PostEntity();
		
		List<PostEntity> post = postEntity.getTimelinePosts(Long.parseLong(timelineid));
		
		object.put("Status", "Ok");
		object.put("Size", post.size());
		for (int i = 0; i < post.size(); ++i) {
			object.put("owner" + i, post.get(i).getOwner());
			object.put("content" + i, post.get(i).getContent());
			object.put("timelineid" + i, post.get(i).getTimelineid());
			object.put("privacy" + i, post.get(i).getPrivacy());
			object.put("id" + i, post.get(i).getId());
			object.put("numberoflikes" + i, post.get(i).getNumberOfLikes());
		}
		return object.toString();
	}
	
	@POST
	@Path("/SharePostService")
	public String SharePostService(@FormParam("id") String postid, @FormParam("timelineid") String timelineid) {
		JSONObject object = new JSONObject();
		PostEntity postEntity = new PostEntity();
		
		postEntity.getPost(Long.parseLong(postid));
		postEntity.saveSharedPost(timelineid, 0);
		
		object.put("Status", "Ok");
		return object.toString();
	}
}






