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

import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.Key;

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
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
	
	
	

		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			UserEntity.currentUser = user;
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();

	}
	
	@POST
	@Path("/LogoutService")
	public String logoutService() {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.currentUser;
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			user = null;
		}
		return object.toString();
	}
	
	@POST
	@Path("/SendFriendRequestService")
	public String sendFriendRequest(@FormParam("receiverEmail") String receiverEmail) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.currentUser;
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			user.saveFriendRequest(receiverEmail);
		}
		return object.toString();
	}
	
	@POST
	@Path("/GetFriendRequestsService")
	public String getFriendRequest() {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.currentUser;
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			List<String> requests = user.getFriendRequests();
			
			object.put("Size",requests.size());
			for (int i = 0; i < requests.size(); ++i) {
				object.put("friend" + i, requests.get(i));
			}
		}
		return object.toString();
	}
		
	@POST
	@Path("/AcceptFriendRequestService")
	public String acceptFriendRequest(@FormParam("friendEmail") String friendEmail) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.currentUser;
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			Key k;
			k = user.saveFriends(friendEmail);
			
			user.deleteRecord(k);
			
			if (k == null)
				object.put("state", "No Friend Request Exist with this email.");
			else
				object.put("state", "Request Have been approved.");
		}
		return object.toString();
	}

}