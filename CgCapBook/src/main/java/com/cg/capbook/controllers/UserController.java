package com.cg.capbook.controllers;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.capbook.beans.FriendRequest;
import com.cg.capbook.beans.FriendsList;
import com.cg.capbook.beans.Message;
import com.cg.capbook.beans.User;
import com.cg.capbook.exceptions.EmptyFriendListException;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
import com.cg.capbook.services.UserServices;
@RestController
@CrossOrigin
public class UserController {
	@Autowired
	UserServices userServices;
	@RequestMapping(value="/signUp",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<String> acceptUserDetails(@RequestBody  User user) throws NoSuchAlgorithmException, UserNotFoundException{
		userServices.acceptUserDetails(user);
		return new ResponseEntity<String>("User details accepted", HttpStatus.OK);
	}
	@RequestMapping(value="/signIn",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserDetails(@RequestParam("emailid") String emailid,@RequestParam("password") String password) throws UserNotFoundException, IncorrectPasswordException, NoSuchAlgorithmException{
		User user=userServices.getUserDetails(emailid,password);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value="/getFriendList",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<List<FriendsList>> getUserFriendDetails(@RequestParam("emailid") String emailid) throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
		List<FriendsList> friendList =userServices.getUserFriendList(emailid);
		return new ResponseEntity<>(friendList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllUsers",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<ArrayList<User>> getAllUsers() throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
		ArrayList<User> userList =userServices.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	@RequestMapping(value="/getNotification",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FriendRequest>> getNotifications(@RequestParam("emailid") String emailid) throws UserNotFoundException{
		List<FriendRequest> request=userServices.getNotifications(emailid);
		return new ResponseEntity<>(request, HttpStatus.OK);
	}
	@RequestMapping(value="/profile",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserDetailsByEmail(@RequestParam("emailid") String emailid) throws UserNotFoundException{
		User user=userServices.getUserDetailsByEmail(emailid);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@RequestMapping(value="/sendMessage",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> sendMessage(@RequestParam("senderEmail") String senderEmail,@RequestParam("receiverEmail") String receiverEmail,@RequestParam("textMessage") String textMessage) throws UserNotFoundException{
	Message message=userServices.sendMessage(senderEmail, receiverEmail, textMessage);
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	@RequestMapping(value="/getSentMessage",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Message>> getSentMessage(@RequestParam("senderEmail") String senderEmail) throws UserNotFoundException{
		ArrayList<Message>message=userServices.getSentMessage(senderEmail);
		return new ResponseEntity<ArrayList<Message>>(message, HttpStatus.OK);
	}
	@RequestMapping(value="/getReceivedMessage",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Message>> getReceivedMessage(@RequestParam("receiverEmail") String receiverEmail) throws UserNotFoundException{
		ArrayList<Message>message=userServices.getReceivedMessage(receiverEmail);
		return new ResponseEntity<ArrayList<Message>>(message, HttpStatus.OK);
	}
	@RequestMapping(value="/updateUserDetails",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUserDetails(@RequestBody  User user) throws UserNotFoundException{
		userServices.updateUser(user);
		return new ResponseEntity<String>("Details Updated", HttpStatus.OK);
}
}