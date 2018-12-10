package com.cg.capbook.controllers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cg.capbook.beans.StorageService;
import com.cg.capbook.beans.UploadFile;
import com.cg.capbook.daoservices.UploadFileDAO;
import com.cg.capbook.exceptions.EmptyFriendListException;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
import com.cg.capbook.services.UserServices;
@RestController
@CrossOrigin
public class ImageUploadController {
@Autowired
UserServices userServices;
@Autowired
StorageService storageService;
@PostMapping(value="/setProfilePic",consumes= {MediaType.ALL_VALUE},produces=MediaType.ALL_VALUE)
public ResponseEntity<byte[]> setImage(@RequestParam("Image") MultipartFile image) throws IOException {
	System.out.println("Image");
	storageService.store(image);
	File file=new File("D:\\159971_SunandanRaj\\Angular\\angular application\\CapBook\\src\\userimages"+image.getOriginalFilename());
	image.transferTo(file);
	FileInputStream fin=new FileInputStream(file);
    byte[] bytes = StreamUtils.copyToByteArray(fin);
    userServices.insertProfilePic(bytes);
    System.out.println(bytes);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
}

@RequestMapping(value = "/getProfilePic", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
public ResponseEntity<byte[]> getImage() throws IOException {
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(userServices.fetchProfilePic());
}
@RequestMapping(value="/uploadProfilePicture",method=RequestMethod.POST,produces=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
public ResponseEntity<String> uploadProfilePicture(@RequestParam("image") CommonsMultipartFile image,@RequestParam("emailId")String emailId) throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
	userServices.uploadProfilePicture(image, emailId);
	return new ResponseEntity<String>("Profile pic uploaded successfully", HttpStatus.OK);
}
@RequestMapping(value="/getProfilePicture",method=RequestMethod.GET,produces=MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers="Accept=application/json")
public ResponseEntity<byte[]> getProfilePicture(@RequestParam("emailId")String emailId) throws UserNotFoundException, IncorrectPasswordException, EmptyFriendListException{
	//byte[] image=userServices.getProfilePicture(emailId);
	return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(userServices.getProfilePicture(emailId));
}
/*@PostMapping("/")
public String uploadMultipartFile(@RequestParam("files") MultipartFile[] files,String emailId) {
	List<String> fileNames = new ArrayList<String>();
	
	try {
		List<UploadFile> storedFile = new ArrayList<UploadFile>();
		
		for(MultipartFile file: files) {
			UploadFile uploadFile = UploadFileDAO.findByName(file.getOriginalFilename());
			if(uploadFile != null) {
				// update new contents
				uploadFile.setPic(file.getBytes());
			}else {
				uploadFile = new UploadFile(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			}
			
			fileNames.add(file.getOriginalFilename());				
			storedFile.add(uploadFile);
		}
		
		// Save all Files to database
    	fileRepository.saveAll(storedFile);
    	
		model.addAttribute("message", "Files uploaded successfully!");
		model.addAttribute("files", fileNames);
	} catch (Exception e) {
		model.addAttribute("message", "Fail!");
		model.addAttribute("files", fileNames);
	}
	
    return "uploadform";
}*/
}
