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
import com.FCI.SWE.ServicesModels.HashTagEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.Key;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class HashTagServices {
	
	@POST
	@Path("/GetHashTagService")
	public String GetHashTagData(@FormParam("id") String id) {
		JSONObject ret = new JSONObject();
		
		if (UserEntity.currentUser == null) {
			ret.put("Status", "Failed");
			return ret.toString();
		}

		HashTagEntity hashtag = new HashTagEntity(id);
		hashtag.getHashTag();
		
		ret.put("Status", "Ok");
		ret.put("posts" , hashtag.printPosts());
		return ret.toString();
	}
	
	
}
