package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.*;

public class SingleChatNotificationModel {
	public static Boolean saveSingleChatNotification(String reciever,
			String sender) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity employee = new Entity("signlechatnotification");
		employee.setProperty("reciever", reciever);
		employee.setProperty("sender", sender);
		datastore.put(employee);
		return true;
	}

	public static List<String> singleChatSenders(String reciever) {

		List<String> ne = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("signlechatnotification");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) {
//			System.out.println(entity.getProperty("useremail").toString());
			if (entity.getProperty("reciever").toString().equals(reciever)) {
				String res = entity
						.getProperty("sender").toString();
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
	public static boolean deletenotification(String reciever, String sender) {
		List<Key> K = getkey(reciever, sender);
//		System.out.printf("Size : %d\n",K.size());
		for (Key k : K) 
			deletenotificationkey(k);
		
		return true;
	}

	public static List<Key> getkey(String reciever, String sender) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		List<Key> ls = new ArrayList<Key>();
		Query gaeQuery = new Query("signlechatnotification");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("reciever").toString().equals(reciever)
					&& entity.getProperty("sender").toString()
							.equals(sender)) {
				ls.add(entity.getKey());
			}
		}
		return ls;
	}
}
