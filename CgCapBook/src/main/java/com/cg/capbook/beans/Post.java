package com.cg.capbook.beans;
import java.util.Date;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int Id;
	Date timeOfPosting;
	private String postText, postCreator;
	private int likes,dislikes;
	@OneToMany(mappedBy="post")
	Map<Integer, Comment> comments;
	//paracon
	public Post(Date timeOfPosting, String postText, String postCreator, int likes, int dislikes, Map<Integer, Comment> comments) {
		super();
		this.timeOfPosting = timeOfPosting;
		this.postText = postText;
		this.likes = likes;
		this.dislikes = dislikes;
		this.comments = comments;
		this.postCreator = postCreator;
	}
	//defcon
	public Post() {
		super();
	}
	//GnS
	public Date getTimeOfPosting() {
		return timeOfPosting;
	}
	public void setTimeOfPosting(Date timeOfPosting) {
		this.timeOfPosting = timeOfPosting;
	}
	public int getId() {
		return Id;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	public Map<Integer, Comment> getComments() {
		return comments;
	}
	public void setComments(Map<Integer, Comment> comments) {
		this.comments = comments;
	}
	public String getPostCreator() {
		return postCreator;
	}
	public void setPostCreator(String postCreator) {
		this.postCreator = postCreator;
	}
	
}