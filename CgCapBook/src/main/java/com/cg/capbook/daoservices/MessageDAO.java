package com.cg.capbook.daoservices;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.capbook.beans.Message;


public interface MessageDAO extends JpaRepository<Message, Integer>{
	@Query("select a from Message a where senderEmail=:senderEmail")
	ArrayList<Message> getSentMessage(@Param("senderEmail") String senderEmail);
	@Query("select a from Message a where receiverEmail=:receiverEmail")
	ArrayList<Message> getReceivedMessage(@Param("receiverEmail") String receiverEmail);

}
