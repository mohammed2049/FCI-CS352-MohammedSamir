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

@Path("/")
@Produces("text/html")
public class HashTagController {
	
	@GET
	@Path("HashTagPage")
	public Response HashTagPage() {
		if (UserEntity.currentUser == null)
			return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("name", "");
		map.put("postId", "");
		return Response.ok(new Viewable("/jsp/hashtag", map)).build();
	}
	
	@POST
	@Path("/GetHashTagPosts")
	public Response gethashtag(@FormParam("name") String name) {
		String serviceUrl = "http://localhost:8888/rest/GetHashTagService";
		String urlParameters = "HashTag=" + name;
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

			map.put("name", object.get("name").toString());
			map.put("posts", object.get("posts").toString());
			
			return Response.ok(new Viewable("/jsp/hashtag", map)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
