package com.cg.capbook.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
public class FriendsList{
	@Id
	@SequenceGenerator(initialValue=1,sequenceName="friendList_seq",name="friendList_seq",allocationSize=1)
	@GeneratedValue(generator="friendList_seq",strategy=GenerationType.AUTO)
private int friendId;
private String userEmail,friendEmail;
public FriendsList() {
	super();
}
public FriendsList(int friendId, String userEmail, String friendEmail) {
	super();
	this.friendId = friendId;
	this.userEmail = userEmail;
	this.friendEmail = friendEmail;
}
public FriendsList(String userEmail, String friendEmail) {
	super();
	this.userEmail = userEmail;
	this.friendEmail = friendEmail;
}
public int getFriendId() {
	return friendId;
}
public void setFriendId(int friendId) {
	this.friendId = friendId;
}
public String getUserEmail() {
	return userEmail;
}
public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
}
public String getFriendEmail() {
	return friendEmail;
}
public void setFriendEmail(String friendEmail) {
	this.friendEmail = friendEmail;
}
@Override
public String toString() {
	return "FriendsList [friendId=" + friendId + ", userEmail=" + userEmail + ", friendEmail=" + friendEmail + "]";
}


}
