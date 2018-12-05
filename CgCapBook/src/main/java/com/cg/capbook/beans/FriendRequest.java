package com.cg.capbook.beans;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="FriendRequest")
public class FriendRequest {
	@Id
	@SequenceGenerator(initialValue=11,sequenceName="friendReq_seq",name="friendReq_seq",allocationSize=1)
	@GeneratedValue(generator="friendReq_seq",strategy=GenerationType.AUTO)
	private int requestId;
	private String senderEmail, receiverEmail;
	
	public FriendRequest() {
		super();
	}
	public FriendRequest(String senderEmail, String receiverEmail) {
		super();
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
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
		return "FriendRequest [requestId=" + requestId + ", sender=" + senderEmail + ", receiver=" + receiverEmail + "]";
	}
}