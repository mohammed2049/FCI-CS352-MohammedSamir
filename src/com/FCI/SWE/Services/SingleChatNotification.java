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
import com.FCI.SWE.Models.SingleChatNotificationModel;
import com.FCI.SWE.ServicesModels.GroupEntity;

public class SingleChatNotification {
	String userEmail;
	Integer groupChatId;
	public SingleChatNotification(String userEmail,Integer groupChatId){
		this.userEmail=userEmail;
		this.groupChatId=groupChatId;
	}
	public void nnotify(){
		
		SingleChatNotificationModel.saveSingleChatNotification(userEmail, groupChatId);
	}
	public static List<Integer>getmessageid(String useremail){
		return SingleChatNotificationModel.singleChatIds(useremail);
	}
}
