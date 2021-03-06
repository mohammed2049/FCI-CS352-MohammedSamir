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
import com.FCI.SWE.ServicesModels.TimeLineEntity;
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
public class UserController {
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@POST
	@Path("/doSearch")
	public Response usersList(@FormParam("uname") String uname) {
		System.out.println(uname);
		String serviceUrl = "http://localhost/rest/SearchService";
		String urlParameters = "uname=" + uname;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		return null;
	}

	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	@GET
	@Path("/notifications")
	public Response Notifications() {
		if (UserEntity.currentUser != null) {
			return Response.ok(new Viewable("/jsp/notifications")).build();
		}
		return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();
	}

	@GET
	@Path("/search")
	public Response search() {
		return Response.ok(new Viewable("/jsp/search")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {

		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	// @Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {

		String urlParameters = "uname=" + uname + "&password=" + pass;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;

			Map<String, String> map = new HashMap<String, String>();
			if (UserEntity.currentUser == null)
				return Response.ok(new Viewable("/jsp/youMustBeLoggedIn")).build();

			map.put("name", uname);
			map.put("password", pass);
			long usrid = UserEntity.currentUser.getId();
			long timelineID = TimeLineEntity.getTimeLineID(usrid);
			map.put("timelineid", new Long(timelineID).toString());
			// long usrid = UserEntity.currentUser.getId();
			// map.put("id" , new Long(usrid).toString());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}

	@POST
	@Path("/GetRequests")
	public String getRequests() {
		String urlParameters = "";

		String retJson = Connection.connect(
				"http://localhost:8888/rest/GetFriendRequestsService",
				urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			if (object.get("Status").equals("OK"))
				return object.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/LogOut")
	public Response logOut() {
		String urlParameters = "";

		String retJson = Connection.connect(
				"http://localhost:8888/rest/LogoutService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			if (object.get("Status").equals("OK"))
				return Response.ok(new Viewable("/jsp/entryPoint")).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/SendRequest")
	public Response SendRequest(@FormParam("receiverEmail") String receiverEmail) {
		String urlParameters = "receiverEmail=" + receiverEmail;

		String retJson = Connection.connect(
				"http://localhost:8888/rest/SendFriendRequestService",
				urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;

			Map<String, String> map = new HashMap<String, String>();
			map.put("name", UserEntity.currentUser.getName());
			map.put("email", UserEntity.currentUser.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/AcceptRequest")
	public Response AcceptRequest(@FormParam("friendEmail") String friendEmail) {
		String urlParameters = "friendEmail=" + friendEmail;

		String retJson = Connection.connect(
				"http://localhost:8888/rest/AcceptFriendRequestService",
				urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;

			Map<String, String> map = new HashMap<String, String>();
			map.put("name", UserEntity.currentUser.getName());
			map.put("email", UserEntity.currentUser.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}