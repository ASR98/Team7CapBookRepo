package com.cg.capbook.services;

import java.awt.List;
import java.util.ArrayList;

import com.cg.capbook.beans.User;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;

public interface UserServices {

	User acceptUserDetails(User user);
	User getUserDetails(String emailid,String password) throws UserNotFoundException, IncorrectPasswordException;
	User searchUserByName(String fullName);
	void sendFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException;
	void acceptFriendRequest(String senderEmail,String receiverEmail) throws UserNotFoundException;
	ArrayList<String> getUserFriendList(String emailId) throws UserNotFoundException;
}
