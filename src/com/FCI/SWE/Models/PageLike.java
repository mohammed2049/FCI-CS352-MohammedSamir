package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.*;

public class PageLike {
	public static boolean saveLike(String user_email, String page_name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		if(exist(user_email,page_name))
			return false;
		Entity employee = new Entity("PagesLikes");
		employee.setProperty("page_name", page_name);
		employee.setProperty("user_email", user_email);
		datastore.put(employee);
		return true;
	}

	public static boolean exist(String user_email, String page_name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("PagesLikes");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable())
			if (entity.getProperty("page_name").toString().equals(page_name)
					&& entity.getProperty("user_email").equals(user_email))
				return true;
		return false;
	}

	public static ArrayList<String> pagesLikers(String page_name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		ArrayList<String> users = new ArrayList<String>();
		Query gaeQuery = new Query("PagesLikes");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable())
			if (entity.getProperty("page_name").toString().equals(page_name))
				users.add(entity.getProperty("user_email").toString());
		return users;
	}

	public static List<String> userLikes(String user_email) {
		List<String> pages = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("PagesLikes");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable())
			if (entity.getProperty("user_email").toString().equals(user_email))
				pages.add(entity.getProperty("page_name").toString());
		return pages;
	}

}
