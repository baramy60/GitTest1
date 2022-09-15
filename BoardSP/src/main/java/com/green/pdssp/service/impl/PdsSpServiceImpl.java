package com.green.pdssp.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.pds.service.impl.PdsFile;
import com.green.pdssp.dao.PdsSpDao;
import com.green.pdssp.service.PdsSpService;
import com.green.pdssp.vo.FilesVo;
import com.green.pdssp.vo.PdsVo;

@Service("pdsSpService")
public class PdsSpServiceImpl implements PdsSpService {

	@Autowired
	private  PdsSpDao  pdsSpDao;
	
	@Override
	public List<PdsVo> getPdsList(HashMap<String, Object> map) {
		
		//  db 조회 :  paging data
		List<PdsVo>   pdsSpList  =  pdsSpDao.getPdsList( map );
		// map 돌아온 정보 {pdsList, menu_id, nowpage, pagecount, pagegrpnum, totalpage }
		
		// 페이징을 위한 내용 추가
		
		// 한페이지에 뵤여줄 페이지번호의 갯수 : 10 
		// 1 2 3 4 5 6 7 8 9 10 [다음] : 10
		// 1 2 3 4 5            [다음] : 5
		int  pagetotalcount   =  5;
		
		// 한페이지에 보여줄 자료수 : pagecount : 10
		int  pagecount  =  Integer.parseInt(
			String.valueOf(	 map.get("pagecount") ) );
		
		// 현재 페이지 정보
		int  nowpage   =   Integer.parseInt(
				String.valueOf(	 map.get("nowpage") ) );
		
		// pagegrpnum
		int  pagegrpnum  =   Integer.parseInt(
				String.valueOf(	 map.get("pagegrpnum") ) );
		
		//		totalcount : 현재 메뉴의 전체 자료수
		int  totalcount  =   Integer.parseInt(
				String.valueOf(	 map.get("totalcount") ) );
		
		BoardPaging  bp    =  new BoardPaging(
			nowpage,  pagecount, totalcount,
			pagetotalcount, pagegrpnum);
		
		PdsVo       pdsVo  =  bp.getPdsPagingInfo();
		
		// 추가
		pdsVo.setMenu_id( (String) map.get("menu_id") );
		
		// map 에 정보를 추가 (pdsSp/list.jsp 가 사용할 정보)
		map.put("pagePdsVo", pdsVo );
		System.out.println("service impl pagePdsVo:" + pdsVo);
		
		return        pdsSpList;
	}

	@Override
	public PdsVo getPdsView(HashMap<String, Object> map) {
		
		PdsVo   pdsVo  =  pdsSpDao.getPdsView( map );
		return  pdsVo;
		
	}

	@Override
	public List<FilesVo> getFileList(HashMap<String, Object> map) {
		
		List<FilesVo>   filesList   =  pdsSpDao.getFileList( map );
		return          filesList;
	}
		
	//----------------------------------------

	// setWrite()
	@Override
	public void setWrite(HashMap<String, Object> map, HttpServletRequest request) {
		
		System.out.println("pdsServiceImpl setWrite() map:" + map);
		
		// db 관련 없는 로직 처리 
		// 1.request 처리 - 넘어온 파일 처리  D:\\upload\\
		// 폴더 생성 D:\\UPLOAD\\ 
		// 파일저장을 휘한 라리브러리 추기
		// commons-io 2.0.1 추가
		// commons-fileupload  1.2.2 추가
		PdsFile.save(map, request);  // map +( filenames, fileexts, sfilenames)
		
		// 2.넘어온 정보(map data) db 저장 - dao 
		pdsSpDao.setWrite( map );   // Mboard, Files
		
	}
	
}







