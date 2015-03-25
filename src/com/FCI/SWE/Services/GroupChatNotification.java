package com.FCI.SWE.Services;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.GroupChatNotificationModel;
import com.FCI.SWE.ServicesModels.GroupEntity;

public class GroupChatNotification {
	String userEmail;
	Integer groupChatId;
	public GroupChatNotification(String userEmail,Integer groupChatId){
		this.userEmail=userEmail;
		this.groupChatId=groupChatId;
	}
	public void nnotify(){
		GroupChatNotificationModel.saveGroupChatNotification(userEmail, groupChatId);
	}
	public static List<Integer>getgroupids(String useremail){
		return GroupChatNotificationModel.groupchatids(useremail);

	}
}
