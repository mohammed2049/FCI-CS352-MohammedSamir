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
public class PostController {
	@POST
	@Path("/PostData")
	public Response getPostData(@FormParam("timelineid") String timelineid, @FormParam("owner") String owner) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("timelineid", timelineid);
		map.put("owner", owner);
		return Response.ok(new Viewable("/jsp/createPost", map)).build();
	}
	
	@POST
	@Path("/CreatePost")
	public Response createPost(@FormParam("owner") String owner, @FormParam("content") String content, @FormParam("timelineid") String timelineid, @FormParam("privacy") String privacy) {
		String serviceUrl = "http://localhost:8888/rest/CreatePostService";
		String urlParameters = "owner=" + owner + "&content=" + content + "&timelineid=" + timelineid + "&privacy=" + privacy;
		System.out.print(urlParameters);
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
			return Response.ok(new Viewable("/jsp/postedSuccessfully")).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/GetTimeLinePosts")
	public String getTimeLinePosts(@FormParam("timelineid") String timelineid) {
		String serviceUrl = "http://localhost:8888/rest/GetTimelinePostsService";
		String urlParameters = "timelineid=" + timelineid;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			return obj.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/SharePost")
	public Response SharePost(@FormParam("id") String postid, @FormParam("timelineid") String timelineid) {
		String serviceUrl = "http://localhost:8888/rest/SharePostService";
		String urlParameters = "id=" + postid + "&timelineid=" + timelineid;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			return Response.ok(new Viewable("/jsp/postedSuccessfully")).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}





