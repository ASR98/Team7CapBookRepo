package com.cg.capbook.services;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.cg.capbook.beans.FriendRequest;
import com.cg.capbook.beans.FriendsList;
import com.cg.capbook.beans.Message;
import com.cg.capbook.beans.Post;
import com.cg.capbook.beans.ReferFriend;
import com.cg.capbook.beans.UploadFile;
import com.cg.capbook.beans.User;
import com.cg.capbook.daoservices.CommentDAO;
import com.cg.capbook.daoservices.FriendListDAO;
import com.cg.capbook.daoservices.FriendRequestDAO;
import com.cg.capbook.daoservices.MessageDAO;
import com.cg.capbook.daoservices.PostDAO;
import com.cg.capbook.daoservices.ReferFriendDAO;
import com.cg.capbook.daoservices.UploadFileDAO;
import com.cg.capbook.daoservices.UserDAO;
import com.cg.capbook.exceptions.EmptyFriendListException;
import com.cg.capbook.exceptions.FriendRequestException;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.PostNotFoundException;
import com.cg.capbook.exceptions.ReferFriendsListEmpty;
import com.cg.capbook.exceptions.UserNotFoundException;
import com.cg.capbook.util.SortByDateAsc;
@Component
public class UserServicesImpl implements UserServices {
	public static String sessionEmailId=null;
	@Autowired
	UserDAO userDAO;
	@Autowired
	FriendRequestDAO friendRequestDAO;
	@Autowired
	FriendListDAO friendListDAO;
	@Autowired
	UploadFileDAO uploadFileDAO;
	@Autowired
	CommentDAO commentDAO;
	@Autowired
	PostDAO postDAO;
	@Autowired
	ReferFriendDAO referFriendDAO;
	@Autowired
	MessageDAO messageDAO;
	
	private String passwordHashing(String actualPassword) throws NoSuchAlgorithmException {
		MessageDigest hashingMethod = MessageDigest.getInstance("MD5");
		byte[] messageDigest = hashingMethod.digest(actualPassword.getBytes());
		BigInteger signum = new BigInteger(1, messageDigest);
		String hashPassword = signum.toString(16);
		while (hashPassword.length() < 32) {
			hashPassword = "0" + hashPassword;
		}
		return hashPassword;
	}

	// Login and sign-up related
	@Override
	public User acceptUserDetails(User user) throws NoSuchAlgorithmException, UserNotFoundException {
		if (!userDAO.findAll().isEmpty())
			if (userDAO.findById(user.getEmailid()).isPresent())
				throw new UserNotFoundException("Account already exists with existing emailid");
		user.setPassword(passwordHashing(user.getPassword()));
		user.setConfirmPassword(user.getPassword());
		user = userDAO.save(user);
		return user;
	}

	@Override
	public User getUserDetails(String emailId, String password)
			throws UserNotFoundException, IncorrectPasswordException, NoSuchAlgorithmException {
		User user = userDAO.findById(emailId).orElseThrow(() -> new UserNotFoundException("User Details Not found"));
		password = passwordHashing(password);
		if (!user.getPassword().equals(password))
			throw new IncorrectPasswordException("Incorrect Password");
		return user;
	}
	
	// Sending and getting friend requests
	@Override
	public ArrayList<User> getAllUsers() {
		return (ArrayList<User>) userDAO.findAll();
	}

	@Override
	public FriendRequest sendFriendRequest(String senderEmail, String receiverEmail)
			throws UserNotFoundException, FriendRequestException {
		userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		if(senderEmail.equals(receiverEmail)) throw new FriendRequestException("Cannot send to your own account");
		FriendsList friendsList=friendListDAO.checkFriendList(senderEmail,receiverEmail);
		if(friendsList!=null)throw new FriendRequestException("Already friends"); 
		FriendRequest request1=friendRequestDAO.getFriendRequestId(senderEmail, receiverEmail);
		FriendRequest request2=friendRequestDAO.getFriendRequestId(receiverEmail,senderEmail);
		if(request1!=null) throw new FriendRequestException("Request already sent");
		if(request2!=null) throw new FriendRequestException("Request pending from sender");
		FriendRequest friendRequest=new FriendRequest(senderEmail,receiverEmail);
		friendRequestDAO.save(friendRequest);
		return friendRequest;		
	}

	@Override
	public FriendRequest acceptFriendRequest(String senderEmail, String receiverEmail)
			throws UserNotFoundException, FriendRequestException {
		User sender=userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("Sender Details Not found"));
		User receiver=userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("Receiver Details Not found"));
		FriendRequest request=friendRequestDAO.getFriendRequestId(senderEmail, receiverEmail);
		if(request==null) throw new FriendRequestException("Request not found exception");
		String fullName1=sender.getFullName();
		FriendsList friendsList=new FriendsList(senderEmail, fullName1, receiverEmail);
		friendListDAO.save(friendsList);
		String fullName2=receiver.getFullName();
		FriendsList friendsList2=new FriendsList(receiverEmail,fullName2, senderEmail);
		friendListDAO.save(friendsList2);
		friendRequestDAO.delete(request);
		return request;
	}

	@Override
	public List<FriendsList> getUserFriendList(String emailId) throws UserNotFoundException, EmptyFriendListException {
		userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException());
		List<FriendsList> friendsList=friendListDAO.getFriendList(emailId);
		return friendsList;
	}

	@Override
	public ArrayList<String> getAllFriendRequestSent(String receiverEmail)
			throws UserNotFoundException, EmptyFriendListException {
		userDAO.findById(receiverEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
		ArrayList<String> friendList = friendRequestDAO.getAllFriendRequestSent(receiverEmail);
		if (friendList.isEmpty())
			throw new EmptyFriendListException("Friend list is empty");
		ArrayList<String> friendListName = new ArrayList<>();
		for (String email : friendList)
			friendListName.add(userDAO.findById(email).get().getFullName());
		return friendListName;
	}

	@Override
	public ArrayList<String> getAllFriendRequestReceived(String senderEmail)
			throws UserNotFoundException, EmptyFriendListException {
		userDAO.findById(senderEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
		ArrayList<String> friendList = friendRequestDAO.getAllFriendRequestReceived(senderEmail);
		if (friendList.isEmpty())
			throw new EmptyFriendListException("Friend list is empty");
		ArrayList<String> friendListName = new ArrayList<>();
		for (String email : friendList)
			friendListName.add(userDAO.findById(email).get().getFullName());
		return friendListName;
	}

	@Override
	public FriendRequest deleteFriendRequest(String senderEmail, String receiverEmail)
			throws UserNotFoundException, FriendRequestException {
		userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("Sender Details Not found"));
		userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("Receiver Details Not found"));
		FriendRequest request=friendRequestDAO.getFriendRequestId(senderEmail, receiverEmail);
		if(request==null) throw new FriendRequestException("No Requests Found");
		 friendRequestDAO.deleteById(request.getRequestId());
		 return  request;                      
	}
	@Override
	public void referFriend(String senderEmail, String receiverEmail, String referredEmail) throws UserNotFoundException, FriendRequestException {
		userDAO.findById(senderEmail).orElseThrow(()->new UserNotFoundException("Sender Details Not found"));
		userDAO.findById(receiverEmail).orElseThrow(()->new UserNotFoundException("Receiver Details Not found"));
		userDAO.findById(referredEmail).orElseThrow(()->new UserNotFoundException("Referred Details Not found"));
		
		FriendsList friendsList = friendListDAO.checkFriendList(senderEmail, receiverEmail);
		if(friendsList==null) throw new FriendRequestException("You are not friends to send request");
		friendsList=friendListDAO.checkFriendList(referredEmail, receiverEmail);
		if(friendsList==null) throw new FriendRequestException("You cannot refer because you are not friends");
		friendsList=friendListDAO.checkFriendList(referredEmail,senderEmail);
		if(friendsList!=null) throw new FriendRequestException("Already Friends");
		ReferFriend request=referFriendDAO.getReferFriendRequestId(senderEmail, receiverEmail,referredEmail);
		if(request!=null) throw new FriendRequestException("Request already sent");
		ReferFriend referFriend = new ReferFriend(senderEmail, receiverEmail, referredEmail);
	referFriendDAO.save(referFriend);
		}
	// password changing
	@Override
	public User changePassword(String emailId, String oldPassword, String newPassword, String confirmNewPassword)
			throws UserNotFoundException, IncorrectPasswordException, NoSuchAlgorithmException {
		User user=userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException("User not found"));
		System.out.println(user.getPassword());
		if(!passwordHashing(oldPassword).equals(user.getPassword())) throw new IncorrectPasswordException("Incorrect Password");
		if(oldPassword.equals(newPassword)) throw new IncorrectPasswordException("Please Enter password that is different from old password");
		if(newPassword.equals(confirmNewPassword)) {
			user.setPassword(newPassword);
		User user1=userDAO.save(user);
		return user1;}
		else throw new IncorrectPasswordException("Password Mismatch");
	}

	@Override
	public User forgotPassword(String emailId, String securityQuestion, String securityAnswer, String newPassword)
			throws UserNotFoundException, IncorrectPasswordException {
		User user=userDAO.findById(emailId).orElseThrow(()->new UserNotFoundException("User not found"))	;
		if(user.getSecurityQuestion().equals(securityQuestion) && user.getSecurityAnswer().equals(securityAnswer)) {
			user.setPassword(newPassword);
			User user1=userDAO.save(user);
			return user1;}
		else throw new IncorrectPasswordException("Incorrect Question or Answer");
	}

	// Profile picture related
	@Override
	public void uploadProfilePicture(CommonsMultipartFile image, String emailId) throws UserNotFoundException {
		User user = userDAO.findById(emailId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
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
		userDAO.findById(emailId).orElseThrow(() -> new UserNotFoundException("User not found"));
		UploadFile uploadFile = uploadFileDAO.getFile(emailId);
		uploadFile.getFileName();
		byte[] imagefiles = uploadFile.getData();
		return imagefiles;
	}
	
	@Override
	public User insertProfilePic(byte[] profilePic) {
		System.out.println(sessionEmailId);
		User profile=userDAO.findById(sessionEmailId).get();
		profile.setProfilePic(profilePic);
		return userDAO.save(profile);
	}
	@Override
	public byte[] fetchProfilePic() {
		User user=userDAO.findById(sessionEmailId).get();
		return user.getProfilePic();
	}

	// Writing on the wall
	@Override
	public void createPost(String originalPosterEmail, String wallOwnerEmail, String postText)
			throws UserNotFoundException {
		User wallOwner = userDAO.findById(wallOwnerEmail)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		// check if friends in front
		Post post = new Post(new Date(), postText, originalPosterEmail, 0, 0, new HashMap<>());
		post = postDAO.save(post);
		wallOwner.getWall().getPosts().put(post.getId(), post);
	}

	@Override
	public void deletePost(int postId) throws PostNotFoundException {
		postDAO.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
		postDAO.deleteById(postId);
	}

	@Override
	public ArrayList<Post> getUserWall(String userEmailId) throws UserNotFoundException {
		User user = userDAO.findById(userEmailId).orElseThrow(() -> new UserNotFoundException("User not found"));
		ArrayList<Post> posts = new ArrayList<Post>(user.getWall().getPosts().values());
		Collections.sort(posts, new SortByDateAsc());
		return posts;
	}

	@Override
	public void updatePost(Post post) throws PostNotFoundException {
		postDAO.findById(post.getId())
				.orElseThrow(() -> new PostNotFoundException("Cannot update a non-existent post"));
		postDAO.save(post);
	}

	@Override
	public User getUserDetailsByEmail(String emailid) throws UserNotFoundException {
		User user=userDAO.findById(emailid).orElseThrow(()->new UserNotFoundException());
		return user;
	}

	@Override
	public List<FriendRequest> getNotifications(String emailid) throws UserNotFoundException {
		userDAO.findById(emailid).orElseThrow(()->new UserNotFoundException());
		List<FriendRequest> request=friendRequestDAO.getNotifications(emailid);
		return request;
	}
	
//Refer Friend
	@Override
	public ArrayList<String> referredFriendsList(String receiverEmail) throws ReferFriendsListEmpty {
		ArrayList<String> referFriendList=referFriendDAO.getReferredFriendsList(receiverEmail);
		if(referFriendList==null) throw new ReferFriendsListEmpty("No Existing Referred Friends");
		ArrayList<String> referFriendsList = new ArrayList<>();
		for(String email:referFriendList)
			referFriendsList.add(userDAO.findById(email).get().getFullName());
		return referFriendsList;
	}
	//Messages
	@Override
	public Message sendMessage(String senderEmail, String receiverEmail, String textMessage) throws UserNotFoundException {
		User sender=userDAO.findById(senderEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
		User receiver=userDAO.findById(receiverEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
		Message message=new Message(textMessage, sender.getFullName(), receiver.getFullName(), senderEmail, receiverEmail);
		return messageDAO.save(message);
	}
	@Override
	public ArrayList<Message> getSentMessage(String senderEmail) throws UserNotFoundException {
		userDAO.findById(senderEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
		ArrayList<Message> messages=messageDAO.getSentMessage(senderEmail);
		return messages;
	}
	@Override
	public ArrayList<Message> getReceivedMessage(String receiverEmail)throws UserNotFoundException  {
		userDAO.findById(receiverEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
		ArrayList<Message> messages=messageDAO.getReceivedMessage(receiverEmail);
		return messages;
	}
	@Override
	public User updateUser(User user) throws UserNotFoundException {
		User user1=userDAO.findById(user.getEmailid()).get();
		if(user.getCity()!=null)
			user1.setCity(user.getCity());
		if(user.getFirstName()!=null)
			user1.setFirstName(user.getFirstName());
		if(user.getLastName()!=null)
			user1.setLastName(user.getLastName());
		if(user.getState()!=null)
			user1.setState(user.getState());
		if(user.getCountry()!=null)
			user1.setCountry(user.getCountry());
		if(user.getCollegeName()!=null)
			user1.setCollegeName(user.getCollegeName());
		if(user.getMaritalStatus()!=null)
			user1.setMaritalStatus(user.getMaritalStatus());
		if(user.getDateOfBirth()!=null)
			user1.setDateOfBirth(user.getDateOfBirth());
		if(user.getPhoneNumber()!=0)
			user1.setPhoneNumber(user.getPhoneNumber());
		if(user.getSecurityQuestion()!=null)
			user1.setSecurityQuestion(user.getSecurityQuestion());
		if(user.getSecurityAnswer()!=null)
			user1.setSecurityAnswer(user.getSecurityAnswer());
		userDAO.save(user1);
		return user1;
	}
}