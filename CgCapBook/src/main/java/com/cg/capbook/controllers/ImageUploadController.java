package com.cg.capbook.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cg.capbook.exceptions.EmptyFriendListException;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
import com.cg.capbook.services.UserServices;

@RestController
@CrossOrigin
public class ImageUploadController {
@Autowired
UserServices userServices;
@RequestMapping(value="/uploadProfilePicture",method=RequestMethod.POST,produces=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
public ResponseEntity<String> uploadProfilePicture(@RequestParam("image") CommonsMultipartFile image,@RequestParam("emailId")String emailId) throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
	userServices.uploadProfilePicture(image, emailId);
	return new ResponseEntity<String>("Profile pic uploaded successfully", HttpStatus.OK);
}
@RequestMapping(value="/getProfilePicture",method=RequestMethod.GET,produces=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
public ResponseEntity<byte[]> getProfilePicture(@RequestParam("emailId")String emailId) throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
	byte[] image=userServices.getProfilePicture(emailId);
	return new ResponseEntity<byte[]>(image, HttpStatus.OK);
}
}