package com.green.pds.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.menus.service.MenuService;
import com.green.menus.vo.MenuVo;
import com.green.pds.service.PdsService;
import com.green.pds.vo.FilesVo;
import com.green.pds.vo.PdsVo;

@Controller
@RequestMapping("/Pds")
public class PdsController {
	
	@Autowired
	private  MenuService   menuService;
	
	@Autowired
	private  PdsService    pdsService;

	// /Pds/List
	@RequestMapping("/List")
	public  ModelAndView   list(
		@RequestParam HashMap<String, Object>	map) {
		
		// MenuList
		List<MenuVo> menuList = menuService.getMenuList();
		
		String       menu_id  = (String) map.get("menu_id");  
		
		// 현재 메뉴 정보확인
		MenuVo       menuVo;
		if(menu_id != null)
			menuVo = menuService.menuView(menu_id);
		else
			menuVo = new MenuVo(null, "전체", 0);
			
		
		// PdsList
		List<PdsVo>  pdsList =  pdsService.getPdsList( map );
		
		ModelAndView  mv  =  new ModelAndView();
		mv.addObject("menuList",   menuList);
		mv.addObject("pdsList",    pdsList);
		mv.addObject("menu_id",    menuVo.getMenu_id());
		mv.addObject("menu_name",  menuVo.getMenu_name());
		mv.setViewName("pds/list");	
		return   mv;		
	}
	
	// /Pds/WriteForm
	// /Pds/WriteForm?menu_id=MENU01&bnum=0&lvl=0&step=0&nref=0
	@RequestMapping("/WriteForm")
	public  ModelAndView   writeForm(
		@RequestParam HashMap<String, Object> map	) {
		
		// 메뉴 목록 조회
		List<MenuVo>  menuList = menuService.getMenuList();
		
		ModelAndView  mv  =  new ModelAndView();
		mv.addObject("menuList", menuList );
		mv.addObject("map", map);
		mv.setViewName("pds/write");  // /WEB-INF/views/pds/write.jsp
		return        mv;
	}
	
	
	// /Pds/Write
	@RequestMapping("/Write")
	public  ModelAndView  write(
		@RequestParam  HashMap<String, Object> map,
		HttpServletRequest  request) {
		
		String   menu_id  =  (String) map.get("menu_id");
		
		// 새글저장       : Board  Table  - 게시글 저장         - Dao		
		// 파일 정보저장  : Files  Table  - 첨부파일 이름저장   - Dao
		// 실제 파일 저장 : c:\\upload    - 첨부파일 자체 저장 		
		pdsService.setWrite(map, request);
				
		ModelAndView   mv  =  new  ModelAndView();
		mv.addObject("map", map);
		mv.setViewName("redirect:/Pds/List?menu_id=" + menu_id);
		return  mv;
	}
	
	//  /Pds/View
	// /Pds/View?menu_id=${ pds.menu_id }&idx=${pds.idx}
	@RequestMapping("/View")
	public  ModelAndView   view(@RequestParam HashMap<String, Object> map) {

		// 메뉴목록
		List<MenuVo>   menuList = menuService.getMenuList();
		
		// 조회된 글 정보
		PdsVo          pdsVo    = pdsService.getPds( map );
		
		// idx 로 조회된 글 연결된 파일 목록
		List<FilesVo>  filesList =  pdsService.getFilesList( map );
		//System.out.println("Pds Controller pdsFilesList:" +  filesList);
		
		String  menu_id = (String) map.get("menu_id");
		
		ModelAndView  mv  =  new ModelAndView();
		mv.addObject("menuList",   menuList );
		mv.addObject("pdsVo",      pdsVo );
		mv.addObject("filesList",  filesList );
		mv.addObject("menu_id",    menu_id );
				
		mv.addObject("map", map );		
		mv.setViewName("pds/view");
		return  mv;
	}
	
	// /Pds/Delete?menu_id=${menu_id}&idx=${pdsVo.idx}
	@RequestMapping("/Delete")
	public  ModelAndView  delete(
		@RequestParam HashMap<String, Object>  map ) {
		
		// 글삭제
		pdsService.setDelete( map );
		
		String   menu_id  = (String) map.get("menu_id");
		ModelAndView  mv  =  new ModelAndView();		
		mv.addObject("map", map);
		mv.setViewName("redirect:/Pds/List?menu_id=" + menu_id);
		return  mv;
	}
	
	// /Pds/UpdateForm?menu_id=${menu_id}&idx=${pdsVo.idx}
	@RequestMapping("/UpdateForm")
	public   ModelAndView   updateForm(
		@RequestParam  HashMap<String, Object> map ) {
		
		// 메뉴 목록
		List<MenuVo>   menuList   =  menuService.getMenuList();
				
		// idx로 수정할 자료 검섹
		PdsVo          pdsVo      =  pdsService.getPds( map );
		
		// idx로 수정할 파일들정보 검섹
		List<FilesVo>  filesList  =  pdsService.getFilesList(map); 
		
		ModelAndView   mv  =  new ModelAndView();
		mv.addObject("menuList",  menuList );
		mv.addObject("filesList", filesList );
		mv.addObject("pdsVo",    pdsVo   );
		mv.addObject("map", map);
		mv.setViewName("pds/update");
		return  mv;
	}
	
	// /Pds/Update
	@RequestMapping("/Update")
	public   ModelAndView   update(
		@RequestParam  HashMap<String, Object> map,
		HttpServletRequest request ) {
		
		System.out.println("PDsController update() map:" + map);
		
		//    수정 (idx, tite, cont, menu_id, file 정보) 
		pdsService.setUpdate(  map, request  );
		
		String  menu_id = (String) map.get("menu_id");
		// 이동		
		ModelAndView  mv  =  new  ModelAndView();
		mv.addObject("menu_id", menu_id);
		mv.addObject("map", map);
		mv.setViewName("redirect:/Pds/List?menu_id=" + menu_id );
		return mv;
	}	
		
	
	//--------------------------------------------------------------------------
	// 다운로드
	// http://localhost:9090/Pds/download/external/taglibs-standard-impl-1.2.5_3.jar
	// {sfile}    : .jpg와 같은 .포함문자가 들어오면 금 문자를 무시 - 사용금지
	// {sfile:.+} : 정규식문법으로  + (한개이상 존재해야한다)  ".한개 이상"
	//              확장자가 한개이상 존재하는
	@RequestMapping(value  ="/download/{type}/{sfile:.+}",
		            method = RequestMethod.GET	)
	public  void  downloadFile(
		@PathVariable("type")  String type,
		@PathVariable("sfile") String sfile,
		HttpServletResponse    response
			)  throws IOException {
		
		String  INTERNAL_FILE         = sfile;
		String  EXTERNAL_FILE_PATH    = "c:\\upload\\" + sfile;
		
		File    file  = null;
		if ( type.equalsIgnoreCase("internal") ) {   //internal
			ClassLoader  classLoader = 
				Thread.currentThread().getContextClassLoader(); // 현재시스템정보
			file = new File(classLoader.getResource(INTERNAL_FILE).getFile() );
		} else {  // extenal
			file = new File ( EXTERNAL_FILE_PATH );
		}
		
		if( !file.exists() ) {
			String  errorMessage = "죄송합니다. 파일이 없습니다";
			System.out.println( errorMessage );
			OutputStream  outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}
		
		String  mimeType = URLConnection.guessContentTypeFromName(file.getName());
		mimeType   = "application/octet-stream";  // 무조건 다운로드
		
		//String  fname = URLEncoder.encode(file.getName(), "UTF-8");
		// 파일명 한글 처리
		String  fname = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1" );
		response.setContentType( mimeType );
		response.setHeader("Content-Disposition",
			String.format( "inline; filename=\"" + fname  + "\"" )	);
		
		response.setContentLength((int) file.length() );
		
		InputStream  inputStream = new BufferedInputStream(
			new FileInputStream(file) );
		
		FileCopyUtils.copy(inputStream, response.getOutputStream() );
		
		inputStream.close();
		
	}
	
	
}








