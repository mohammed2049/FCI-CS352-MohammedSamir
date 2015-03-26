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
	String reciever;
	String sender;
	public SingleChatNotification(String reciever,String sender){
		this.sender=sender;
		this.reciever=reciever;
	}
	public void nnotify(){
		SingleChatNotificationModel.saveSingleChatNotification(reciever, sender);
	}
	public static List<String>getsenders(String receiver){
		return SingleChatNotificationModel.singleChatSenders(receiver);
	}
}
