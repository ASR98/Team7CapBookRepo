package com.cg.capbook.controllers;
import java.util.ArrayList;
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
	public ResponseEntity<String> acceptUserDetails(@RequestBody  User user){
		userServices.acceptUserDetails(user);
		return new ResponseEntity<String>("User details accepted", HttpStatus.OK);
	}
	@RequestMapping(value="/signIn",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<String> getUserDetails(@RequestParam String emailid,@RequestParam String password) throws UserNotFoundException, IncorrectPasswordException{
		User user=userServices.getUserDetails(emailid,password);
		return new ResponseEntity<String>(user.toString(), HttpStatus.OK);
	}

	@RequestMapping(value="/getFriendList",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<ArrayList<String>> getUserFriendDetails(@RequestParam("emailid") String emailid) throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
		ArrayList<String> friendList =userServices.getUserFriendList(emailid);
		return new ResponseEntity<>(friendList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllUsers",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<ArrayList<User>> getAllUsers() throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
		ArrayList<User> userList =userServices.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
	
}