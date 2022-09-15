package com.green.pds.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.pds.dao.PdsDao;
import com.green.pds.service.PdsService;
import com.green.pds.vo.FilesVo;
import com.green.pds.vo.PdsVo;

@Service("pdsService")
public class PdsServiceImpl implements PdsService {

	@Autowired
	private	 PdsDao  pdsDao;
	
	@Override
	public void setWrite(HashMap<String, Object> map,
			HttpServletRequest request) {
		// 1. request 처리 - 넘어온 파일 저장, 저장된 파일정보(sfilename) -> map
		PdsFile.save(map, request);  // 별도  클래스 생성
		
		// 2. 넘어온 정보 db 저장
		pdsDao.setWrite( map );

	}

	@Override
	public List<PdsVo> getPdsList(HashMap<String, Object> map) {
		
		List<PdsVo>  pdsList  =  pdsDao.getPdsList( map );
		return       pdsList;
		
	}

	@Override
	public PdsVo getPds(HashMap<String, Object> map) {
		
		PdsVo   pdsVo  =  pdsDao.getPds( map );  
		
		return  pdsVo;
	}

	@Override
	public List<FilesVo> getFilesList(HashMap<String, Object> map) {
		
		List<FilesVo>  filesList  =  pdsDao.getFilesList( map );		
		return         filesList;
		
	}

	@Override
	public void setDelete(HashMap<String, Object> map) {
	
		// 1. db 정보삭제
		pdsDao.setDelete( map );
		
		//System.out.println("Pds Service Impl map:" + map);
		// 삭제후 map 안에 돌아온 정보를 이용해서 실제 파일 삭제 
		// 2. c:\\upload 폴더 파일삭제
		List<FilesVo> filesList = (List<FilesVo>) map.get("filesList");
		for (FilesVo filesVo : filesList) {
			String  delFile =  filesVo.getSfilename();
			System.out.println( delFile );
			File  file  =  new File("c:\\upload\\" + delFile);
			if( file.exists() ) 
				file.delete();
		}
		
	}

	@Override
	public void setUpdate(HashMap<String, Object> map,
			HttpServletRequest request) {
		// 1. request 넘어온 파일만 저장 : C:\\UPLOAD\\ 
		PdsFile.save(map, request);		
		System.out.println("Pds Service setUpdate() map:" + map);
		
		// 2 db 정보룰 수정
		pdsDao.setUpdate( map );
	}

	@Override
	public void deleteUploadedFile(
			HashMap<String, Object> map) {
		// map { file_num, sfilename }
		
		// 1. c:\\upload\\  에서 해당 파일 삭제
		String  sfilename  =  (String)  map.get("sfilename");
		String  filePath   =  "c:\\upload\\";
		File    file       =  new File(filePath + sfilename);
		if ( file.exists()  )
			file.delete();
		
		// 2. Files table 에서 file_num 에 해당하는 파일정보 삭제
		pdsDao.deleteUploadedFile( map ); // {file_num, sfilename}
		
	}

}





