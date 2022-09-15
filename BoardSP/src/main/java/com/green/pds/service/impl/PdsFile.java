package com.green.pds.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.green.pds.vo.FilesVo;

public class PdsFile {

	public static void save(
			HashMap<String, Object> map,
			HttpServletRequest      request) {
		
		// 자료실  파일 저장될 경로(디렉토리)  없으면 생성
		String   filePath =  "c:\\upload\\";
		File     dir      =  new File( filePath );		
		if( !dir.exists() ) {
			dir.mkdir();   // make directory
		}
				
		// 넘어온 파일저장(c:\\upload)처리 (중복파일처리)
		CheckFileName  checkFile = new CheckFileName();
		
		// 파일 저장
		MultipartHttpServletRequest  multipartHttpServletRequest
		  = (MultipartHttpServletRequest) request;
	
		// write.jsp 보낸 file name이 달라야 여러개의 파일을 처리할 수 있다
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		MultipartFile  multipartFile =  null;
		
		List<FilesVo>  filesList   = new ArrayList<>();  
		
		String         fileName     = null;  
		String         orgFileName  = null;  
		String         fileExt      = null;  
		String         sFileName    = null;  
		
		// upload 된 파일마다 반복하여 처리
		// 파일하나당 반복
		while( iterator.hasNext() ) {
			multipartFile    = multipartHttpServletRequest.getFile( iterator.next() );
			
			if( !multipartFile.isEmpty()  ) {
				fileName     =  multipartFile.getOriginalFilename();  // 손.jpg
				orgFileName  =  fileName.substring(0, fileName.lastIndexOf('.')); // 손
			    fileExt      =  fileName.substring(fileName.lastIndexOf('.'));    // .jpg
			    
			    // 손1.jpg
			    sFileName    =  checkFile.getCheckFileName(
			    		filePath, orgFileName, fileExt);
			    
			    FilesVo   vo   = new FilesVo(0, 0, fileName, fileExt, sFileName);   
			    filesList.add( vo );
			   		    
			    // 파일 저장
			    File  file = new File(filePath + sFileName );
			    try {
					multipartFile.transferTo(file);  // 실제 파일 저장
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}			    
			} // if end
		} // while end
		
		map.put("filesList", filesList);
	}
	
	
	
}





