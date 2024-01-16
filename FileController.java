package com.demo.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.project.config.storage.s3Service;
import com.demo.project.dto.DocStatusDto;
import com.demo.project.dto.ResponseData;
import com.demo.project.dto.ResponseData2;
import com.demo.project.dto.ResponseEntityDto;
import com.demo.project.dto.ResponseFile;
import com.demo.project.model.User;
import com.demo.project.model.UserDocs;
import com.demo.project.repository.FileRepository;
import com.demo.project.repository.UserRepository;
import com.demo.project.service.FileService;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private s3Service awsService;

	@Autowired(required = true)
	private UserRepository userRepository;
	
	@Autowired
	private FileRepository fileRepository;

	// to upload one doc
	@PostMapping("/users/{userId}/docs")
	public ResponseEntityDto uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("comments") String comments, @PathVariable("userId") long userId)
			throws IOException, Exception {
		if (file==null||file.isEmpty()) {
			return ResponseEntityDto.create400Response("Please choose a file to upload");
		}
		User user = this.userRepository.findById(userId);
		if (Objects.isNull(user)) {
			return ResponseEntityDto.create400Response("user not registered by entered User Id");
		}
		UserDocs userDocs = this.fileService.saveFile(file, comments, userId);

		try {
			ResponseFile ResponseFile = new ResponseFile();
			ResponseFile.setFileName(userDocs.getFileName());
			ResponseFile.setComments(userDocs.getComments());
			ResponseFile.setS3Url(userDocs.gets3Url());
			return ResponseEntityDto.create200Response(ResponseFile);

		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}
	}

	// get all doc by user user id
	@GetMapping("/users/{userId}/docs")
	public ResponseEntityDto getListFiles(@PathVariable("userId") long userId) {
		
		User user = this.userRepository.findById(userId);
		if(Objects.isNull(user)) {
			return ResponseEntityDto.create400Response("User not available by this Id");
		}
		List<ResponseData> files = fileService.getListFiles(userId);
		
		if (files.isEmpty()) {
			return ResponseEntityDto.create400Response("Documents not available");
		}
		try {
			return ResponseEntityDto.create200Response(files);
		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}

	}

	// mark inactive active doc
	@PutMapping("/users/{userId}/docs/{docId}/status")
	public ResponseEntityDto updateStatus(@RequestBody DocStatusDto docStatusDto, @PathVariable("docId") String docId,
			@PathVariable("userId") long userId)

	{
		ResponseData2 userDoc1 = this.fileService.updateStatus(docStatusDto, docId, userId);
		if (Objects.isNull(userDoc1)) {
			return ResponseEntityDto.create404Response("No document available");
		}
		try {
			return ResponseEntityDto.create200Response(userDoc1);

		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}
	}

	// to get one doc details
	@GetMapping("/users/{userId}/docs/{docId}")
	public ResponseEntityDto getFile(@PathVariable("docId") String docId, @PathVariable("userId") long userId)
			throws Exception {
		ResponseData doc = this.fileService.getFile(docId, userId);
		if (Objects.isNull(doc)) {
			return ResponseEntityDto.create404Response("No document available");
		}
		try {
			return ResponseEntityDto.create200Response(doc);

		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}
	}

	// Updating the documents
	@PutMapping("/users/{userId}/docs/{docId}")

	public ResponseEntityDto updateDocs(@RequestParam("file") MultipartFile file,
			@RequestParam("comments") String comments, @PathVariable("docId") String docId,
			@PathVariable("userId") long userId) throws Exception {
		ResponseData userDoc = this.fileService.updateDocs(file, comments, docId, userId);

		if (Objects.isNull(userDoc)) {
			return ResponseEntityDto.create404Response("Either document id or user id is invalid");
		}
		try {
			return ResponseEntityDto.create200Response(userDoc);

		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}

	}

	
	
	// Download the file
	@GetMapping("/{userId}/{docName}")
	public ResponseEntity<ByteArrayResource> loadFiles(@PathVariable("userId") long userId,
			@PathVariable("docName") String docName) {
		 
		User user = this.userRepository.findById(userId); 
		
		UserDocs userDoc = this.fileRepository.getBydocAndUser(docName,user);
		
		
		if(Objects.isNull(userDoc)){ 
			String msg= "Either User or document not found!!!!";
			byte[] msg1= msg.getBytes();
			ByteArrayResource resource = new ByteArrayResource(msg1);
 
			return ResponseEntity.badRequest().body(resource);
			
		}
		
		byte[] bytes = awsService.downloadFile(docName);
		ByteArrayResource resource = new ByteArrayResource(bytes);

		return ResponseEntity.ok().contentLength(bytes.length).header("content-type", "application/octet-stream")
				.header("content-disposition", "attachment;fileName\"" + docName).body(resource);
	}

}
