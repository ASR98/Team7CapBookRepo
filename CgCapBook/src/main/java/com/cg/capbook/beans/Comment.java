package com.cg.capbook.beans;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private int Id;
	private String emailId, commentText;
	@ManyToOne
	Post post;
	//parametric constr
	public Comment(String emailId, String commentText) {
		super();
		this.emailId = emailId;
		this.commentText = commentText;
	}
	//def. constr
	public Comment() {
		super();
	}
	//GnS
	public int getId() {
		return Id;
	}	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
}