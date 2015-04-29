package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.TimeLineEntity;
import com.google.appengine.api.datastore.*;

public class PageModel {
	
	public static  String curr_page=null;
	public static List<String> getAllPages() {
		List<String> pages = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable())
			pages.add(entity.getProperty("page_name").toString());
		return pages;
	}

	public static List<String> getPages(String user_email) {
		List<String> pages = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable())
			if (entity.getProperty("owner").toString().equals(user_email))
				pages.add(entity.getProperty("page_name").toString());
		return pages;
	}

	public static boolean savePage(String owner, String page_name) {
		TimeLineEntity Tobj = new TimeLineEntity(page_name);
		Tobj.saveTimeLinePage();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (getKey(page_name) != null)
			return false;

		Entity employee = new Entity("Pages");
		employee.setProperty("page_name", page_name);
		employee.setProperty("owner", owner);
		datastore.put(employee);
		return true;
	}

	public static boolean deletePage(String page_name) {
		Key k = getKey(page_name);
		if (k == null)
			return false;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.delete(k);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	public static Key getKey(String page_name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable())
			if (entity.getProperty("page_name").toString().equals(page_name))
				return entity.getKey();
		return null;
	}
}
