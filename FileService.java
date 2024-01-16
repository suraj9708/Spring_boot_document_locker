package com.demo.project.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.demo.project.dto.DocStatusDto;
import com.demo.project.dto.ResponseData;
import com.demo.project.dto.ResponseData2;
import com.demo.project.model.UserDocs;

public interface FileService {

	List<ResponseData> getListFiles(long userId);

	UserDocs saveFile(MultipartFile file, String comments, long userId) throws Exception;

	ResponseData updateDocs(MultipartFile file, String comments, String doc_id, long userId) throws Exception;

	ResponseData getFile(String id, long userId) throws Exception;

	ResponseData2 updateStatus(DocStatusDto docStatusDto, String docId, long userId);

}
