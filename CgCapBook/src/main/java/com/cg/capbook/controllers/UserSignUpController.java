package com.cg.capbook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.capbook.beans.User;
import com.cg.capbook.services.UserServices;
@RestController
public class UserSignUpController {
@Autowired
UserServices userServices;
@RequestMapping(value="/signUp",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
public ResponseEntity<String> acceptUserDetails(@ModelAttribute @RequestBody User user){
	userServices.acceptUserDetails(user);
	return new ResponseEntity<String>("User details accepted", HttpStatus.OK);
}
}
