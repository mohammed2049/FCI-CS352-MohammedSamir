package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class TimeLineEntity {
	private long id;
	private long userID;
	private String pageID;

	public TimeLineEntity() {
		userID = UserEntity.currentUser.getId();
	}

	public TimeLineEntity(long userID) {
		this.userID = userID;
	}

	public TimeLineEntity(String pageID) {
		this.pageID = pageID;
	}

	public long getId() {
		return id;
	}

	public String getPageID() {
		return pageID;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public boolean saveTimeLine() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		Query gaeQuery = new Query("TimeLinePage");
		Query gaeQuery2 = new Query("TimeLine");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		PreparedQuery pq2 = datastore.prepare(gaeQuery2);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		List<Entity> list2 = pq2.asList(FetchOptions.Builder.withDefaults());
		try {
			id = list.size() + list2.size() + 2;
			Entity timeLine = new Entity("TimeLine", id);

			timeLine.setProperty("userID", userID);
			datastore.put(timeLine);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	public boolean saveTimeLinePage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		Query gaeQuery = new Query("TimeLinePage");
		Query gaeQuery2 = new Query("TimeLine");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		PreparedQuery pq2 = datastore.prepare(gaeQuery2);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		List<Entity> list2 = pq2.asList(FetchOptions.Builder.withDefaults());
		try {
			id = list.size() + list2.size() + 2;
			Entity timeLine = new Entity("TimeLinePage", id);

			timeLine.setProperty("pageID", pageID);
			datastore.put(timeLine);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	public static long getTimeLineID(long uid) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		long res = 0;
		Query gaeQuery = new Query("TimeLine");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("userID").toString()
					.equals(new Long(uid).toString())) {

				res = entity.getKey().getId();
			}
		}
		return res;
	}

	public static long getTimeLinePageID(String pageName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		long res = 0;
		Query gaeQuery = new Query("TimeLinePage");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("pageID").toString().equals(pageName)) {

				res = entity.getKey().getId();
			}
		}
		return res;
	}
}
