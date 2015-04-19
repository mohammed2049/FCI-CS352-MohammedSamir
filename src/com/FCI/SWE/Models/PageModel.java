package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.*;

public class PageModel {
	public static String savePage(String owner, String page_name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key=getKey(page_name);
		if (getKey(page_name) != null)
			return key.toString();
		Entity employee = new Entity("Pages");
		employee.setProperty("page_name", page_name);
		employee.setProperty("owner", owner);
		datastore.put(employee);
		return getKey(page_name).toString();
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
