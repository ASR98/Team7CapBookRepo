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

import com.cg.capbook.beans.Post;
import com.cg.capbook.exceptions.PostNotFoundException;
import com.cg.capbook.exceptions.UserNotFoundException;
import com.cg.capbook.services.UserServices;

@RestController
@CrossOrigin
public class WallController {
	@Autowired
	UserServices userServices;

	@RequestMapping(value = "/createPost", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> createPost(@RequestParam("posterEmail") String posterEmail,
			@RequestParam("wallOwnerEmail") String wallOwnerEmail, @RequestParam("postText") String postText)
			throws UserNotFoundException {
		userServices.createPost(posterEmail, wallOwnerEmail, postText);
		return new ResponseEntity<>("Post created", HttpStatus.OK);
	}
	@RequestMapping(value = "/deletePost", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> deletePost(@RequestParam("posterId") int postId)
			throws UserNotFoundException, PostNotFoundException {
		userServices.deletePost(postId);
		return new ResponseEntity<>("Post deleted", HttpStatus.OK);
	}
	@RequestMapping(value = "/getWall", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<ArrayList<Post>> getUserWall(@RequestParam("userEmailId") String userEmailId)
			throws UserNotFoundException {
		ArrayList<Post> posts = userServices.getUserWall(userEmailId);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	@RequestMapping(value = "/updatePost", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> updatePost(@RequestBody Post post)
			throws UserNotFoundException, PostNotFoundException {
		userServices.updatePost(post);
		return new ResponseEntity<>("Post updated", HttpStatus.OK);
	}
}