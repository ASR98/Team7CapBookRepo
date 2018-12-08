package com.cg.capbook.daoservices;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.capbook.beans.FriendRequest;
import com.cg.capbook.beans.ReferFriend;

public interface ReferFriendDAO extends JpaRepository<ReferFriend, Integer>{
	@Query("select f from ReferFriend f  where f.senderEmail=:senderEmail and f.receiverEmail=:receiverEmail and f.referredEmail=:referredEmail")
	ReferFriend getReferFriendRequestId(@Param("senderEmail")String senderEmail,@Param("receiverEmail")String receiverEmail,@Param("referredEmail")String referredEmail);
	@Query("select f.referredEmail from ReferFriend f  where f.receiverEmail=:receiverEmail")
	ArrayList<String> getReferredFriendsList(@Param("receiverEmail")String receiverEmail);

}
