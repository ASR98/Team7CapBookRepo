package com.cg.capbook.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int messageId;
	private String textMessage,senderFullName,receiverFullName,senderEmail,receiverEmail;
	
	
	public Message() {
		super();
	}
	
	public Message(String textMessage, String senderFullName, String receiverFullName, String senderEmail,
			String receiverEmail) {
		super();
		this.textMessage = textMessage;
		this.senderFullName = senderFullName;
		this.receiverFullName = receiverFullName;
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
	}

	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
	public String getSenderFullName() {
		return senderFullName;
	}
	public void setSenderFullName(String senderFullName) {
		this.senderFullName = senderFullName;
	}
	public String getReceiverFullName() {
		return receiverFullName;
	}
	public void setReceiverFullName(String receiverFullName) {
		this.receiverFullName = receiverFullName;
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
		return "Message [messageId=" + messageId + ", textMessage=" + textMessage + ", senderFullName=" + senderFullName
				+ ", receiverFullName=" + receiverFullName + "]";
	}
	
	

}
