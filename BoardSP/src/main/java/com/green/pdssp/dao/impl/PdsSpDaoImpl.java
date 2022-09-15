package com.green.pdssp.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.pdssp.dao.PdsSpDao;
import com.green.pdssp.vo.FilesVo;
import com.green.pdssp.vo.PdsVo;

@Repository("pdsSpDao")
public class PdsSpDaoImpl implements PdsSpDao {

	@Autowired
	private  SqlSession  sqlSession;
	
	@Override
	public List<PdsVo> getPdsList(HashMap<String, Object> map) {
		
		//System.out.println("DAO pre MAP:" + map);
		
		sqlSession.selectList("PdsSp.PdsPagingList", map);       // stored procedure 실행
		List<PdsVo> pdsList =  (List<PdsVo>) map.get("result");	 // out 인자(cursor)를 가져온다	 
		
		//System.out.println("DAO post MAP:" + map);
		
		return      pdsList;
		
	}

	@Override
	public PdsVo getPdsView(HashMap<String, Object> map) {
		
		// Cursor 처리
		sqlSession.selectList("PdsSp.PdsView", map);
		
		List<PdsVo>   pdsList  =  (List<PdsVo>) map.get("result"); 
		PdsVo         pdsVo    =   pdsList.get(0);
		
		return       pdsVo;
	}

	@Override
	public List<FilesVo> getFileList(HashMap<String, Object> map) {
		
		sqlSession.selectList("PdsSp.FileList", map);
		List<FilesVo>  filesList  =  (List<FilesVo>) map.get("result");
		
		return        filesList;
	}

	//-------------------------------------------------
	// setWrite()
		@Override
		public void setWrite(HashMap<String, Object> map) {
			System.out.println("실행전 map:" +  map ); // stored procedure 실행하기전의 map 정보		
			sqlSession.insert("PdsSp.PdsWrite", map);
			System.out.println("실행후 map:" + map ); // stored procedure 실행한 후의 map 정보		
			
		}		
		
	

}



