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
public class ReferFriendController {
	@Autowired
	UserServices userServices;
	@RequestMapping(value="/referFriend",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<String> referFriend(@RequestParam String senderEmail,@RequestParam String receiverEmail,@RequestParam String referredEmail) throws UserNotFoundException, FriendRequestException{
		userServices.referFriend(senderEmail, receiverEmail, referredEmail);
		return new ResponseEntity<String>("Friend Request Sent", HttpStatus.OK);
	}
		@RequestMapping(value="/sendRequestTorefferedFriend",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
		public ResponseEntity<String> sendRequestTorefferedFriend(@RequestParam String senderEmail,@RequestParam String referredEmail) throws UserNotFoundException, FriendRequestException{
			userServices.sendFriendRequest(senderEmail, referredEmail);
			return new ResponseEntity<String>("Friend Request Sent", HttpStatus.OK);

		}
	}