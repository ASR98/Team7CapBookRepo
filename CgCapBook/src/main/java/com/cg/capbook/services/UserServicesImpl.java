package com.cg.capbook.services;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cg.capbook.beans.FriendRequest;
import com.cg.capbook.beans.FriendsList;
import com.cg.capbook.beans.UploadFile;
import com.cg.capbook.beans.User;
import com.cg.capbook.daoservices.FriendListDAO;
import com.cg.capbook.daoservices.FriendRequestDAO;
import com.cg.capbook.daoservices.UploadFileDAO;
import com.cg.capbook.daoservices.UserDAO;
import com.cg.capbook.exceptions.EmptyFriendListException;
import com.cg.capbook.exceptions.FriendRequestException;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
@Component
public class UserServicesImpl implements UserServices{
	@Autowired
	UserDAO userDAO;
	@Autowired
	FriendRequestDAO friendRequestDAO;
	@Autowired
	FriendListDAO friendListDAO;
	@Autowired
	UploadFileDAO uploadFileDAO;
	private String passwordHashing(String actualPassword) throws NoSuchAlgorithmException {
		MessageDigest hashingMethod= MessageDigest.getInstance("MD5");
		byte[] messageDigest = hashingMethod.digest(actualPassword.getBytes());
		BigInteger signum = new BigInteger(1, messageDigest);
		String hashPassword = signum.toString(16);
		while(hashPassword.length()<32) {
			hashPassword = "0" + hashPassword;
		}
		return hashPassword;
	}
	@Override
	public User acceptUserDetails(User user) throws NoSuchAlgorithmException, UserNotFoundException {
		if(!userDAO.findAll().isEmpty())
		if(userDAO.findById(user.getEmailid()).isPresent()) throw new UserNotFoundException("Account already exists with existing emailid");
		user.setPassword(passwordHashing(user.getPassword()));
		user.setConfirmPassword(user.getPassword());
		user=userDAO.save(user);
		return user;
	}
	@Override
	public User getUserDetails(String emailId,String password) throws UserNotFoundException, IncorrectPasswordException, NoSuchAlgorithmException {
		User user=userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		password = passwordHashing(password);
		if(!user.getPassword().equals(password)) throw new IncorrectPasswordException("Incorrect Password");
		return user;
	}
	@Override
	public ArrayList<User> getAllUsers() {
		return (ArrayList<User>) userDAO.findAll();
	}
	@Override
	public void sendFriendRequest(String senderEmail, String receiverEmail) throws UserNotFoundException, FriendRequestException {
		User sender=userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		if(senderEmail.equals(receiverEmail)) throw new FriendRequestException("Cannot send to your own account");
		/*	if(sender.getFriendList()!=null){
			for(String email:sender.getFriendList())
				if(receiverEmail.equals(email)) throw new FriendRequestException("Already friends");}*/
		FriendRequest request1=friendRequestDAO.getFriendRequestId(senderEmail, receiverEmail);
		FriendRequest request2=friendRequestDAO.getFriendRequestId(receiverEmail,senderEmail);
		if(request1!=null) throw new FriendRequestException("Request already sent");
		if(request2!=null) throw new FriendRequestException("Request pending from sender");
		FriendRequest friendRequest=new FriendRequest(senderEmail,receiverEmail);
		friendRequestDAO.save(friendRequest);
	}
	@Override
	public void acceptFriendRequest(String senderEmail, String receiverEmail) throws UserNotFoundException, FriendRequestException {
		User sender=userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("Sender Details Not found"));
		User receiver=userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("Receiver Details Not found"));
		FriendRequest request=friendRequestDAO.getFriendRequestId(senderEmail, receiverEmail);
		if(request==null) throw new FriendRequestException("Request not found exception");

		/*	if(sender.getFriendList()==null) 
			sender.setFriendList(new ArrayList<>());
		if(receiver.getFriendList()==null) 
			receiver.setFriendList(new ArrayList<>());*/
		FriendsList friendsList1 = new FriendsList(receiverEmail, senderEmail);
		//FriendsList friendsList2 = new FriendsList(senderEmail,receiverEmail);
		/*sender.getFriendList().add(receiverEmail);
		receiver.getFriendList().add(senderEmail);
		userDAO.save(sender);
		userDAO.save(receiver);*/
		friendListDAO.save(friendsList1);
		//	friendListDAO.save(friendsList2);
		friendRequestDAO.delete(request);
	}
	@Override
	public ArrayList<String> getUserFriendList(String emailId) throws UserNotFoundException, EmptyFriendListException {
		User user= userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException());
		ArrayList<String> friendList = friendListDAO.getAllFriendsList(emailId);
		ArrayList<String> friendsList = new ArrayList<>();
		if(friendList.isEmpty()) throw new EmptyFriendListException("Friend list is empty");
		for(String email : friendList)
			friendsList.add(userDAO.findById(email).get().getFullName());
		return friendsList;
	}
	@Override
	public void deleteFriendRequest(String senderEmail, String receiverEmail)
			throws UserNotFoundException, FriendRequestException {
		userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("Sender Details Not found"));
		userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("Receiver Details Not found"));
		FriendRequest request=friendRequestDAO.getFriendRequestId(senderEmail, receiverEmail);
		if(request==null) throw new FriendRequestException("No Requests Found");
		friendRequestDAO.delete(request);
	}
	@Override
	public void changePassword(String emailId,String oldPassword, String newPassword, String confirmNewPassword) throws UserNotFoundException, IncorrectPasswordException {
		User user=userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException("User not found"));
		if(!oldPassword.equals(user.getPassword())) throw new IncorrectPasswordException("Incorrect Password");
		if(oldPassword.equals(newPassword)) throw new IncorrectPasswordException("Please Enter password that is different from old password");
		if(newPassword.equals(confirmNewPassword)) {
			user.setPassword(newPassword);
			userDAO.save(user);}
		else throw new IncorrectPasswordException("Password Mismatch");
	}
	@Override
	public void forgotPassword(String emailId, String securityQuestion, String securityAnswer, String newPassword) throws UserNotFoundException, IncorrectPasswordException {
		User user=userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException("User not found"))	;
		if(user.getSecurityQuestion().equals(securityQuestion) && user.getSecurityAnswer().equals(securityAnswer)) {
			user.setPassword(newPassword);
			userDAO.save(user);}
		else throw new IncorrectPasswordException("Incorrect Question or Answer");
	}
	@Override
	public ArrayList<String> getAllFriendRequestSent(String receiverEmail) throws UserNotFoundException, EmptyFriendListException {
		User user=userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("User not found"))	;
		ArrayList<String> friendList = friendRequestDAO.getAllFriendRequestSent(receiverEmail);
		if(friendList.isEmpty()) throw new EmptyFriendListException("Friend list is empty");
		ArrayList<String> friendListName = new ArrayList<>();
		for(String email : friendList)
			friendListName.add(userDAO.findById(email).get().getFullName());
		return friendListName;

	}
	@Override
	public ArrayList<String> getAllFriendRequestReceived(String senderEmail)
			throws UserNotFoundException, EmptyFriendListException {
		User user=userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("User not found"))	;
		ArrayList<String> friendList = friendRequestDAO.getAllFriendRequestReceived(senderEmail);
		if(friendList.isEmpty()) throw new EmptyFriendListException("Friend list is empty");
		ArrayList<String> friendListName = new ArrayList<>();
		for(String email : friendList)
			friendListName.add(userDAO.findById(email).get().getFullName());
		return friendListName;
	}
	@Override
	public void uploadProfilePicture(CommonsMultipartFile image, String emailId) throws UserNotFoundException {
		User user=userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException("User Not Found"));
		if (image != null) {
			CommonsMultipartFile file = image;
			UploadFile uploadFile = new UploadFile();
			uploadFile.setFileName(emailId);
			uploadFile.setUserName(user.getFullName());
			uploadFile.setData(file.getBytes()); // image
			uploadFileDAO.save(uploadFile);
		
	}	
	
}
	@Override
	public byte[] getProfilePicture(String emailId) throws UserNotFoundException {
		User user = userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException("User not found"));
		UploadFile uploadFile = uploadFileDAO.getFile(emailId);
		String name = uploadFile.getFileName();
		byte[] imagefiles = uploadFile.getData();
		return imagefiles;
	}
}