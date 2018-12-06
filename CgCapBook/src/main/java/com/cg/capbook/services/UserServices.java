package com.cg.capbook.services;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cg.capbook.beans.User;
import com.cg.capbook.exceptions.EmptyFriendListException;
import com.cg.capbook.exceptions.FriendRequestException;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
public interface UserServices {
	User acceptUserDetails(User user) throws NoSuchAlgorithmException, UserNotFoundException;
	User getUserDetails(String emailId,String password) throws UserNotFoundException, IncorrectPasswordException, NoSuchAlgorithmException;
	public ArrayList<User> getAllUsers();
	void sendFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException, FriendRequestException;
	void acceptFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException, FriendRequestException;
	ArrayList<String> getUserFriendList(String emailId) throws UserNotFoundException, EmptyFriendListException;
	void deleteFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException,FriendRequestException;
	void changePassword(String emailId,String oldPassword,String newPassword,String confirmNewPassword) throws UserNotFoundException, IncorrectPasswordException;
	void forgotPassword(String emailId,String securityQuestion,String securityAnswer,String newPassword) throws UserNotFoundException, IncorrectPasswordException;
	ArrayList<String> getAllFriendRequestSent(String receiverEmail) throws UserNotFoundException, EmptyFriendListException;
	ArrayList<String> getAllFriendRequestReceived(String senderEmail) throws UserNotFoundException, EmptyFriendListException;
	void uploadProfilePicture(CommonsMultipartFile image,String emailId) throws UserNotFoundException;
	public byte[] getProfilePicture(String emailId) throws UserNotFoundException;
}