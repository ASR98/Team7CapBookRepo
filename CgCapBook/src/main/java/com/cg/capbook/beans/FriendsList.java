package com.cg.capbook.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class FriendsList {
	
	@Id
	@SequenceGenerator(initialValue=111,sequenceName="friendListReq_seq",name="friendListReq_seq",allocationSize=1)
	@GeneratedValue(generator="friendListReq_seq",strategy=GenerationType.AUTO)
	private int listId;
	
	private String senderEmail,fullName,receiverEmail;
	
	public FriendsList() {
		super();
	}
	public FriendsList(String senderEmail, String receiverEmail) {
		super();
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public FriendsList(String senderEmail, String fullName, String receiverEmail) {
		super();
		this.senderEmail = senderEmail;
		this.fullName = fullName;
		this.receiverEmail = receiverEmail;
	}
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	@Override
	public String toString() {
		return "FriendsList [listId=" + listId + ", senderEmail=" + senderEmail + ", receiverEmail=" + receiverEmail
				+ "]";
	}
	



}
