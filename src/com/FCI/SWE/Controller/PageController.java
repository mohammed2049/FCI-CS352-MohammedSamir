package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.PageLike;
import com.FCI.SWE.Models.PageModel;
import com.FCI.SWE.Models.SingleChatNotificationModel;
import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;

@Path("/")
@Produces("text/html")
public class PageController {
	@GET
	@Path("/createPage")
	public Response createPage() {
		if (UserEntity.currentUser != null) {
			return Response.ok(new Viewable("/jsp/PageViews/createPage")).build();
		}
		return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
	}

	@POST
	@Path("/savePage")
	public Response savePage(@FormParam("name") String name) {
		
		UserEntity user = UserEntity.currentUser;
		if (UserEntity.currentUser == null)
			return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
		String key = PageModel.savePage(user.getEmail(), name);
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		map.put("page_name", name);
		

		return Response.ok(new Viewable("/jsp/PageViews/Page", map)).build();
	}
	@POST
	@Path("/LikePage")
	public Response LikePage(@FormParam("page_name") String page_name){
		PageLike.saveLike(UserEntity.currentUser.getEmail(), page_name);
		Map<String, String> map = new HashMap<String, String>();
		map.put("page_name", page_name);
		return Response.ok(new Viewable("/jsp/PageViews/Page", map)).build();
	}
}
