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
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
import com.cg.capbook.services.UserServices;

@RestController
@CrossOrigin
public class PasswordServicesController {
@Autowired
UserServices userServices;
@RequestMapping(value="/changePassword",method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
public ResponseEntity<String> changePassword(@RequestParam String emailId,@RequestParam String oldPassword,@RequestParam String newPassword,@RequestParam String confirmNewPassword) throws UserNotFoundException, FriendRequestException, IncorrectPasswordException{
	userServices.changePassword(emailId, oldPassword, newPassword, confirmNewPassword);
	return new ResponseEntity<String>("Password Changed Successfully", HttpStatus.OK);
}
@RequestMapping(value="/forgotPassword",method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
public ResponseEntity<String> forgotPassword(@RequestParam String emailId,@RequestParam String securityQuestion,@RequestParam String securityAnswer,@RequestParam String newPassword) throws UserNotFoundException, FriendRequestException, IncorrectPasswordException{
	userServices.forgotPassword(emailId, securityQuestion, securityAnswer, newPassword);
	return new ResponseEntity<String>("Password Changed Successfully", HttpStatus.OK);
}
}