package com.cg.capbook.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cg.capbook.exceptions.FriendRequestException;
import com.cg.capbook.exceptions.UserNotFoundException;
import com.cg.capbook.services.UserServices;
@RestController
@CrossOrigin
public class FriendRequestController {
	@Autowired
	UserServices userServices;
	@RequestMapping(value="/sendFriendRequest",method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
	public ResponseEntity<String> sendFriendRequest(@RequestParam String senderEmail,@RequestParam String receiverEmail) throws UserNotFoundException, FriendRequestException{
		userServices.sendFriendRequest(senderEmail, receiverEmail);
		return new ResponseEntity<String>("Friend Request Sent", HttpStatus.OK);
	}
	@RequestMapping(value="/acceptFriendRequest",method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
	public ResponseEntity<String> acceptFriendRequest(@RequestParam String senderEmail,@RequestParam String receiverEmail) throws UserNotFoundException, FriendRequestException{
		userServices.acceptFriendRequest(senderEmail, receiverEmail);
		return new ResponseEntity<String>("Friend Request Accepted", HttpStatus.OK);
	}
	@RequestMapping(value="/deleteFriendRequest",method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
	public ResponseEntity<String> deleteFriendRequest(@RequestParam String senderEmail,@RequestParam String receiverEmail) throws UserNotFoundException, FriendRequestException{
		userServices.deleteFriendRequest(senderEmail, receiverEmail);
		return new ResponseEntity<String>("Request Deleted Succesfully", HttpStatus.OK);
	}
}