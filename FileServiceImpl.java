package com.demo.project.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.demo.project.config.storage.s3Service;
import com.demo.project.dto.DocStatusDto;
import com.demo.project.dto.ResponseData;
import com.demo.project.dto.ResponseData2;
import com.demo.project.model.User;
import com.demo.project.model.UserDocs;
import com.demo.project.repository.FileRepository;
import com.demo.project.repository.UserRepository;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileRepository fileRepository;
	@Autowired(required = true)
	private UserRepository userRepository;

	@Autowired
	private s3Service awsService;
	
	@Value("{project.image}")
	private String paths;
	// UPLOAD FILE
	@Override
	public UserDocs saveFile(MultipartFile file, String comments, long userId) throws Exception {
		User user = this.userRepository.findById(userId);

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		String sUrl = awsService.uploadFiles(file,user);

		UserDocs userDocs = new UserDocs();
		userDocs.setFileName(fileName);
		userDocs.setFileType(getExt(fileName));
		userDocs.setComments(comments);
		userDocs.setUser(user);
		userDocs.sets3Url(sUrl);
		try {
			return fileRepository.save(userDocs);
		} catch (Exception e) {
			throw new Exception("file not uploaded");

		}

	}

	public String getExt(String fileName) {
		int index = fileName.lastIndexOf('.');
		String extension = fileName.substring(index + 1);
		return extension;
	}

	// GET FILE
	@Override
	public ResponseData getFile(String id, long userId) throws Exception {
		User user = this.userRepository.findById(userId);

		UserDocs userDoc = fileRepository.getByIdAndUser(id, user);
		if (Objects.isNull(userDoc)) {
			return null;
		}
		return this.docToResponse(userDoc);

	}

	// MARK ACTIVE INACTIVE
	@Override
	public ResponseData2 updateStatus(DocStatusDto docStatusDto, String docId, long userId) {

		User user = this.userRepository.findById(userId);
		UserDocs userDoc1 = this.fileRepository.getByIdAndUser(docId, user);
		if (Objects.isNull(userDoc1)) {
			return null;
		}
		userDoc1.setIsActive(docStatusDto.isIsActive());
		this.fileRepository.save(userDoc1);
		return this.responseData2(userDoc1);

	}

	public ResponseData2 responseData2(UserDocs userDoc) {
		ResponseData2 responseData2 = new ResponseData2();
		responseData2.setDocId(userDoc.getId());
		responseData2.setFileName(userDoc.getFileName());
		responseData2.setDocS3Url(userDoc.gets3Url());
		responseData2.setComments(userDoc.getComments());
		responseData2.setIsActive(userDoc.isisActive());
		return responseData2;
	}

//GET LIST OF FILES	
	@Override
	public List<ResponseData> getListFiles(long userId) {

		User user = this.userRepository.findById(userId);
		 
		List<UserDocs> listofDocs = this.fileRepository.findAll(user);
		List<ResponseData> listofdocs = (List<ResponseData>) (listofDocs.stream().map((doc) -> this.docToResponse(doc)))
				.collect(Collectors.toList());
		return listofdocs;
	}

	public ResponseData docToResponse(UserDocs userDocs) {
		ResponseData responseFile = new ResponseData();
		responseFile.setDocId(userDocs.getId());
		responseFile.setFileName(userDocs.getFileName());
		responseFile.setDocS3Url(userDocs.gets3Url());
		responseFile.setComments(userDocs.getComments());
		return responseFile;
	}

	// UPDATE DOCUMENT

	@Override
	public ResponseData updateDocs(MultipartFile file, String comments, String docId, long userId) throws Exception {
		User user = this.userRepository.findById(userId);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		UserDocs ussrDocs = this.fileRepository.getByIdAndUser(docId, user);
		if (Objects.isNull(ussrDocs)) {
			return null;
		}
		Path localPath = Paths.get(paths + ussrDocs.getFileName());
		String localPaths=localPath.toString();
		File oldFile = new File(localPaths);
		boolean t=awsService.deleteFile(ussrDocs.getFileName());
		if(t) {
			System.out.println("Old file deleted successfully from s3 bucket");
		}
	    if (oldFile.delete()) {
	        System.out.println("Old file deleted successfully.");
	    } else {
	        System.out.println("Failed to delete the old file.");
	    }
		String sUrl = awsService.uploadFiles(file,user);

		ussrDocs.setFileName(fileName);
		ussrDocs.setFileType(getExt(fileName));
		ussrDocs.setComments(comments);
		ussrDocs.sets3Url(sUrl);
		UserDocs usrDoc = this.fileRepository.save(ussrDocs);
		return this.docToResponse(usrDoc);
	}
}
