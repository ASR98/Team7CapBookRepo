package com.cg.capbook.daoservices;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cg.capbook.beans.FriendRequest;
public interface FriendRequestDAO extends JpaRepository<FriendRequest, Integer>{
	@Query("delete from FriendRequest f where f.senderEmail=:senderEmail and f.receiverEmail=:receiverEmail" )
	FriendRequest deleteFriendRequest(@Param("senderEmail")String senderEmail,@Param("receiverEmail")String receiverEmail);
	@Query("select f from FriendRequest f  where f.senderEmail=:senderEmail and f.receiverEmail=:receiverEmail")
	FriendRequest getFriendRequestId(@Param("senderEmail")String senderEmail,@Param("receiverEmail")String receiverEmail);
	@Query("select f.senderEmail from FriendRequest f  where f.receiverEmail=:receiverEmail")
	ArrayList<String> getAllFriendRequestReceived(@Param("receiverEmail")String receiverEmail); 
	@Query("select f.receiverEmail from FriendRequest f  where f.senderEmail=:senderEmail")
	ArrayList<String> getAllFriendRequestSent(@Param("senderEmail")String senderEmail); 
}