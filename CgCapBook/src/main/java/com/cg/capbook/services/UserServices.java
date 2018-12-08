package com.cg.capbook.services;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cg.capbook.beans.FriendRequest;
import com.cg.capbook.beans.FriendsList;
import com.cg.capbook.beans.Post;
import com.cg.capbook.beans.User;
import com.cg.capbook.exceptions.EmptyFriendListException;
import com.cg.capbook.exceptions.FriendRequestException;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.PostNotFoundException;
import com.cg.capbook.exceptions.UserNotFoundException;
public interface UserServices {
	
	//User data related
	User acceptUserDetails(User user) throws NoSuchAlgorithmException, UserNotFoundException;
	User getUserDetails(String emailId,String password) throws UserNotFoundException, IncorrectPasswordException, NoSuchAlgorithmException;
	public ArrayList<User> getAllUsers();
	void updateUserDetails(User user) throws UserNotFoundException;
	User getUserDetailsByEmail(String emailid) throws UserNotFoundException;
	
	// Friends related
	FriendRequest sendFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException, FriendRequestException;
	FriendRequest acceptFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException, FriendRequestException;
	List<FriendsList> getUserFriendList(String emailId) throws UserNotFoundException, EmptyFriendListException;
	ArrayList<String> getAllFriendRequestSent(String receiverEmail) throws UserNotFoundException, EmptyFriendListException;
	ArrayList<String> getAllFriendRequestReceived(String senderEmail) throws UserNotFoundException, EmptyFriendListException;
	FriendRequest deleteFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException,FriendRequestException;
	void referFriend(String senderEmail,String receiverEmail,String referredEmail) throws UserNotFoundException, FriendRequestException;
	List<FriendRequest> getNotifications(String emailid) throws UserNotFoundException ;
	
	//Password related
	User changePassword(String emailId,String oldPassword,String newPassword,String confirmNewPassword) throws UserNotFoundException, IncorrectPasswordException, NoSuchAlgorithmException;
	User forgotPassword(String emailId,String securityQuestion,String securityAnswer,String newPassword) throws UserNotFoundException, IncorrectPasswordException;
	
	//Profile pic related
	void uploadProfilePicture(CommonsMultipartFile image,String emailId) throws UserNotFoundException;
	public byte[] getProfilePicture(String emailId) throws UserNotFoundException;
	 User insertProfilePic(byte[] profilePic);
	 byte[] fetchProfilePic();
	//Wall Posting related
	void createPost(String originalPosterEmail, String wallOwnerEmail, String postText) throws UserNotFoundException;
	void deletePost(int postId) throws PostNotFoundException;
	ArrayList<Post> getUserWall(String userEmailId) throws UserNotFoundException;
	void updatePost(Post post) throws PostNotFoundException;
}