package com.demo.project.config.storage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.demo.project.model.User;

@Service
public class s3Service {

	@Value("${bucket.name}")
	private String bktName;
	@Value("${bucket.access-key}")
	private String bktAccessKey;

	@Value("${bucket.region}")
	private String bktRegion;
	private static AmazonS3 s3;
	 @Value("${project.image}")
	private String paths ;
	public s3Service(AmazonS3 s3) {
		this.s3 = s3;
	}
/*
	public String uploadFile(MultipartFile file) {
		String orgFileName = file.getOriginalFilename();

		File file1;
		try {
			file1 = convertMultiPartToFile(file);
			s3.putObject(bktName, orgFileName, file1);
			URL url = s3.getUrl(bktRegion, orgFileName);

			return url.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convertedfile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedfile);
		fos.write(file.getBytes());
		fos.close();
		return convertedfile;
	}*/

	public byte[] downloadFile(String fileName) {

		S3Object object = s3.getObject(bktName, fileName);
		S3ObjectInputStream s3Object = object.getObjectContent();
		try {
			return IOUtils.toByteArray(s3Object);
		} catch (IOException e) {

			throw new RuntimeException(e);
		}

	}

	public String uploadFiles(MultipartFile file,User user) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       String s3FilePath=user.getId()+"/"+fileName;
		URL urls = s3.getUrl(bktRegion, s3FilePath);
		String s3RelativeUrl = urls.toString();

		Path localPath = Paths.get(paths + fileName);

		try {
			Files.copy(file.getInputStream(), localPath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("file uploaded in local system");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String localfilespath = localPath.toString();
		File docFile = new File(localfilespath);

		s3.putObject(bktName, s3FilePath, docFile);

		return s3RelativeUrl;

	}
	
	
	 public boolean deleteFile(String fileName) { 
		 s3.deleteObject(new DeleteObjectRequest(bktName, fileName));
		return true;
		 
	 }
	
	

}
