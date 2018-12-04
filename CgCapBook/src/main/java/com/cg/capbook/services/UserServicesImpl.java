package com.cg.capbook.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.capbook.beans.FriendRequest;
import com.cg.capbook.beans.User;
import com.cg.capbook.daoservices.FriendRequestDAO;
import com.cg.capbook.daoservices.UserDAO;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
@Component
public class UserServicesImpl implements UserServices{
	@Autowired
	UserDAO userDAO;
	@Autowired
	FriendRequestDAO friendRequestDAO;
	@Override
	public User acceptUserDetails(User user) {
		user=userDAO.save(user);
		return user;
	}
	@Override
	public User getUserDetails(String emailid,String password) throws UserNotFoundException, IncorrectPasswordException {
		User user=userDAO.findById(emailid).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		if(!user.getPassword().equals(password)) throw new IncorrectPasswordException("Incorrect Password");
		return user;
	}
	@Override
	public User searchUserByName(String fullName) {

		return null;
	}
	@Override
	public void sendFriendRequest(String senderEmail, String receiverEmail) throws UserNotFoundException {
		userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		FriendRequest friendRequest=new FriendRequest(senderEmail,receiverEmail);
		friendRequestDAO.save(friendRequest);
	}
	@Override
	public void acceptFriendRequest(String senderEmail, String receiverEmail) throws UserNotFoundException {
		User sender=userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("Sender Details Not found"));
		User receiver=userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("Receiver Details Not found"));
		FriendRequest request=friendRequestDAO.getFriendRequestId(senderEmail, receiverEmail);
		if(request==null) throw new UserNotFoundException("Request not found exception");
		
		if(sender.getFriendList()==null) 
			sender.setFriendList(new ArrayList<>());
		if(receiver.getFriendList()==null) 
			receiver.setFriendList(new ArrayList<>());
		
		sender.getFriendList().add(receiverEmail);
		receiver.getFriendList().add(senderEmail);
		userDAO.save(sender);
		userDAO.save(receiver);
		friendRequestDAO.delete(request);
	}
	@Override
	public ArrayList<String> getUserFriendList(String emailId) throws UserNotFoundException {
		User user= userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException());
		ArrayList<String> friendList = new ArrayList<>();
		if(user.getFriendList().isEmpty())
			System.out.println("What a loser. No friends");
		for(String email : user.getFriendList())
			friendList.add(userDAO.findById(email).get().getFullName());
		return friendList;
	}
}