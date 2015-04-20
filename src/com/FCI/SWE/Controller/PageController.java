package com.FCI.SWE.Controller;

import java.io.*;

import java.net.*;

import java.util.*;

import javax.ws.rs.*;
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
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

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
		boolean exist= PageModel.savePage(user.getEmail(), name);
		if(!exist)
			return Response.ok(new Viewable("/jsp/PageViews/createPage")).build();
		Map<String, String> map = new HashMap<String, String>();
		map.put("page_name", name);
		return Response.ok(new Viewable("/jsp/PageViews/Page", map)).build();
	}
	
	@GET
	@Path("/myPages")
	public Response myPages() {
		if (UserEntity.currentUser == null) 
			return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
		
		return Response.ok(new Viewable("/jsp/PageViews/myPages")).build();
	}
	
	@POST
	@Path("/page")
	public Response page(@FormParam("page_name") String page_name) {
		if (UserEntity.currentUser == null) 
			return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
		Map<String, String> map = new HashMap<String, String>();
		map.put("page_name", page_name);
		map.put("user_email", UserEntity.currentUser.getEmail());
		return Response.ok(new Viewable("/jsp/PageViews/Page", map)).build();
	}
	
	
	@POST
	@Path("/LikePage")
	public Response LikePage(@FormParam("page_name") String page_name){
		PageLike.saveLike(UserEntity.currentUser.getEmail(), page_name);
		Map<String, String> map = new HashMap<String, String>();
		map.put("page_name", page_name);
		map.put("user_email", UserEntity.currentUser.getEmail());
		return Response.ok(new Viewable("/jsp/PageViews/Page", map)).build();
	}
	@POST
	@Path("/PageLikes")
	public Response PageLikes(@FormParam("page_name") String page_name){
		Map<String, String> map = new HashMap<String, String>();
		map.put("page_name", page_name);
		ArrayList<String> ar= PageLike.pagesLikers(page_name);
		for(Integer i=0;i<ar.size();i++)
			map.put(i.toString(), ar.get(i));

		return Response.ok(new Viewable("/jsp/PageViews/PageLikes", map)).build();
	}
	
}
