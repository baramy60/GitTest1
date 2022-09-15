package com.green.pds.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.pds.dao.PdsDao;
import com.green.pds.vo.FilesVo;
import com.green.pds.vo.PdsVo;

@Repository("pdsDao")
public class PdsDaoImpl implements PdsDao {

	@Autowired
	private  SqlSession  sqlSession;
	
	@Override
	public void setWrite(HashMap<String, Object> map) {
		
		// db 정보 저장
		System.out.println("dao map:" + map);
		int  bnum  = Integer.parseInt( map.get("bnum").toString() );
		if( bnum == 0  ) { //  새글   Board
			sqlSession.insert("Pds.PdsInsert",  map );
		} else {		   //  답글 쓰기  	
			sqlSession.insert("Pds.UpdateRef",  map );
			sqlSession.insert("Pds.PdsReply",   map );			
		}
		List<FilesVo>  filesList   = (List<FilesVo>) map.get("filesList");
		System.out.println(filesList.size());
		if(filesList.size() > 0) {
			sqlSession.insert("Pds.FileInsert", map);   // 파일정보 저장
	    }
		
	}

	@Override
	public List<PdsVo> getPdsList(HashMap<String, Object> map) {
		
		List<PdsVo>  pdsList =  sqlSession.selectList("Pds.PdsList", map);
		
		return       pdsList;
		
	}

	@Override
	public PdsVo getPds(HashMap<String, Object> map) {
		
		PdsVo  pdsVo  =  sqlSession.selectOne("Pds.GetPds", map);
		return pdsVo;
		
	}

	@Override
	public List<FilesVo> getFilesList(HashMap<String, Object> map) {
		
		List<FilesVo>  filesList   =  sqlSession.selectList("Pds.PdsFileList", map);
		return         filesList;
		
	}

	@Override
	public void setDelete(HashMap<String, Object> map) {
		
		// 삭제될 파일 목록
		List<FilesVo>  filesList   =  sqlSession.selectList("Pds.PdsFileList", map);
		map.put("filesList", filesList);
		
		sqlSession.delete( "Pds.DeleteFiles", map );  // Files 테이블 정보 삭제(idx)
		sqlSession.delete( "Pds.DeletePds",   map );  // Board 테이블 정보 삭제(idx)
		
	}

	@Override
	public void setUpdate(HashMap<String, Object> map) {
		
		// 1. Board 정보 수정
		sqlSession.update("Pds.UpdatePds", map);
		
		// 2. Files 에 추가된 파일 정보 저장
		sqlSession.insert("Pds.FileInsert", map);   // 파일정보 저장
		
	}

	@Override
	public void deleteUploadedFile(HashMap<String, Object> map) {
		
		sqlSession.delete("Pds.deleteUploadedFile", map);
		
	}

}






