package com.cg.capbook.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ReferFriend {
	@Id
	@SequenceGenerator(initialValue=11,sequenceName="friendRef_seq",name="friendRef_seq",allocationSize=1)
	@GeneratedValue(generator="friendRef_seq",strategy=GenerationType.AUTO)
	private int referId;
	private String senderEmail, receiverEmail,referredEmail;
	
	public ReferFriend() {
		super();
	}

	public ReferFriend(int requestId, String senderEmail, String receiverEmail, String referredEmail) {
		super();
		this.referId = requestId;
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.referredEmail = referredEmail;
	}

	public ReferFriend(String senderEmail, String receiverEmail, String referredEmail) {
		super();
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.referredEmail = referredEmail;
	}

	public int getReferId() {
		return referId;
	}

	public void setReferId(int requestId) {
		this.referId = requestId;
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

	public String getReferredEmail() {
		return referredEmail;
	}

	public void setReferredEmail(String referredEmail) {
		this.referredEmail = referredEmail;
	}
	
}
