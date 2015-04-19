package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.*;

public class GroupChatNotificationModel {
	public static Boolean saveGroupChatNotification(String useremail,
			long groupchatid) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity employee = new Entity("groupchatnotification");
		employee.setProperty("groupchatid", groupchatid);
		employee.setProperty("useremail", useremail);
		datastore.put(employee);
		return true;
	}
	public static List<Integer> groupchatids(String useremail) {
		List<Integer> ne = new ArrayList<Integer>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupchatnotification");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("useremail").toString().equals(useremail)) {
				Integer res = Integer.parseInt(entity
						.getProperty("groupchatid").toString());
				if (!ne.contains(res))
					ne.add(res);
			}
		}
		return ne;
	}
	public static boolean deletenotificationkey(Key k){
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
	public static boolean deletenotification(String email, Integer id) {
		List<Key> K = getkey(email, id);
		System.out.printf("Size : %d\n",K.size());
		for (Key k : K) 
			deletenotificationkey(k);
		
		
		return true;
	}
	public static List<Key> getkey(String email, Integer id) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		List<Key> ls = new ArrayList<Key>();
		Query gaeQuery = new Query("groupchatnotification");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("useremail").toString().equals(email)
					&& entity.getProperty("groupchatid").toString()
							.equals(id.toString())) {
				ls.add(entity.getKey());
			}
		}
		return ls;
	}
}
