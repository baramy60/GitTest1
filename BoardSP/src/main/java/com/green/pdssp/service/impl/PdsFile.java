package com.green.pdssp.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class PdsFile {

	public static  void   save(
			HashMap<String, Object> map,
			HttpServletRequest request) {
		
		//    넘어온 파일저장(d:\\upload\\)처리 (중복파일처리)
		CheckFileName  checkFile =  new CheckFileName(); 
		
		// 자료실 파일 저장될 경로
		String   filePath  = "c:\\upload\\";
		
		MultipartHttpServletRequest  multipartHttpServletRequest
		 =  (MultipartHttpServletRequest) request;
		
		Iterator<String> iterator =
				multipartHttpServletRequest.getFileNames();
		
		MultipartFile  multipartFile = null; 
		
		List<String>  filenames  = new ArrayList<String>(); 
		List<String>  fileexts   = new ArrayList<String>(); 
		List<String>  sfilenames = new ArrayList<String>(); 
				
		String         fileName      = null; 
		String         orgFileName   = null; 
		String         fileExt       = null; 
		String         sFileName     = null; 
		
		// upload 된 파일마다 반복하여 처리
		// 파일하나당 반복
		while( iterator.hasNext() ) {
			multipartFile = multipartHttpServletRequest.getFile(
					iterator.next());
			
			if( !multipartFile.isEmpty()  )
			{
				// 김연아.광고.jpg
				fileName    = multipartFile.getOriginalFilename();              // 김연아.광고.jpg
				orgFileName = fileName.substring(
						0, fileName.lastIndexOf(".")); // 김연아.광고
				fileExt     = fileName.substring( 
						fileName.lastIndexOf(".") );  // .jpg
				
				// filePath + orgFileName + fileExt 이 존재하면 
				//  중복되지 않는 새로운 파일명을 생성
				// 김연아.광고2.jpg
				sFileName = checkFile.getCheckFileName(
						filePath, orgFileName, fileExt );
				
				filenames.add( fileName );
				fileexts.add( fileExt );
				sfilenames.add( sFileName );
								
				map.put("filenames",  filenames);
				map.put("fileexts",   fileexts);
				map.put("sfilenames", sfilenames);
				
				// 파일 저장 : c:\\upload\\
				File file = new File(filePath + sFileName);				
				try {
					multipartFile.transferTo(file);   // 실제파일 저장
				} catch( IllegalStateException e ) {
					e.printStackTrace();
				} catch( IOException e ) {
					e.printStackTrace();
				} // try end
			} // if end				
		} // while end
	}
	
}
