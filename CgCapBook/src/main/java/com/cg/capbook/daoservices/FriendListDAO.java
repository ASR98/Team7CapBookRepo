package com.cg.capbook.daoservices;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cg.capbook.beans.FriendsList;
public interface FriendListDAO extends JpaRepository<FriendsList, Integer>{
	@Query("select  f.senderEmail from FriendsList f  where f.receiverEmail=:receiverEmail or f.senderEmail=:receiverEmail")
	ArrayList<String> getAllFriendsList(@Param("receiverEmail")String receiverEmail); 
	@Query("select  f from FriendsList f  where f.senderEmail=:senderEmail and f.receiverEmail=:receiverEmail")
	FriendsList checkFriendList(@Param("senderEmail")String senderEmail,@Param("receiverEmail")String receiverEmail); 
	@Query("select  f from FriendsList f  where f.receiverEmail=:receiverEmail")
	List<FriendsList> getFriendList(@Param("receiverEmail")String receiverEmail); 

}
