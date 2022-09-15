package com.green.pdssp.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.menus.service.MenuService;
import com.green.menus.vo.MenuVo;
import com.green.pds.service.impl.PdsFile;
import com.green.pdssp.dao.PdsSpDao;
import com.green.pdssp.service.PdsSpService;
import com.green.pdssp.vo.FilesVo;
import com.green.pdssp.vo.PdsVo;

@Controller
public class PdsSpController {
	
	@Autowired
	private  MenuService   menuService;
	
	@Autowired
	private  PdsSpService  pdsSpService;
	
	@Autowired
	private  PdsSpDao  pdsSpDao;

	// /PdsSp/List?menu_id=MENU01&nowpage=1&pagecount=10&pagegrpnum=1
	@RequestMapping("/PdsSp/List")
	public   ModelAndView   list(@RequestParam  HashMap<String, Object> map) {
		// map { menu_id=MENU01&nowpage=1&pagecount=10&pagegrpnum=1} 
		// 메뉴 목록  menuList
		List<MenuVo>  menuList =  menuService.getMenuList();
		
		// 자료 목록 pdsList
		// import com.green.pdssp.vo.PdsVo; // delnum, ...
		List<PdsVo>   pdsList  =  pdsSpService.getPdsList( map );  
		
		// pagePdsVo <- pdsSpService.getPdsList( map ) 실행후
		//   pdsSpService 에서 변경된 값을 돌려받아서 pagePdsVo에 저장해준다
		PdsVo         pagePdsVo  =  (PdsVo) map.get("pagePdsVo"); 
		
		// menu_id
		String        menu_id    =  (String) map.get("menu_id");
		
		// menu_name
		//String        menu_name  =  pagePdsVo.getMenu_name();
		
		ModelAndView  mv = new ModelAndView();
		mv.addObject("menuList",   menuList );
		mv.addObject("pdsList",    pdsList );
		mv.addObject("menu_id",    menu_id );
	//	mv.addObject("menu_name",  menu_name );
		
		mv.addObject("pagePdsVo",  pagePdsVo );
		mv.setViewName("pdsSp/list");
		return  mv;
	}
	
	// 내용보기
	@RequestMapping("/PdsSp/View")
	public   ModelAndView   view(@RequestParam HashMap<String, Object> map) {
		
		//  메뉴 목록
		List<MenuVo>    menuList    =  menuService.getMenuList();
		
		// idx 로 검색된  pdsVo  글 정보
		PdsVo           pdsVo       =  pdsSpService.getPdsView( map );
		System.out.println("CONTR VIEW():" + pdsVo);
		// idx 로 조회된  filesList 파일목록
		List<FilesVo>   filesList   =  pdsSpService.getFileList( map );
		
		String          menu_id     = (String)  map.get("menu_id"); 
		
		ModelAndView    mv  = new ModelAndView();
		mv.addObject("menuList",   menuList );
		mv.addObject("pdsVo",      pdsVo );
		mv.addObject("filesList",  filesList );
		mv.addObject("menu_id",    menu_id );
		mv.addObject("map",        map );
		
		mv.setViewName("pdsSp/view");
		return mv;
	}

	
	//-------------------------------------------------------
	@RequestMapping("/PdsSp/WriteForm")
	public   ModelAndView   writeForm(
		@RequestParam  HashMap<String, Object> map	) {
		
		// map 
		// http://localhost:9090/PDS/WriteForm
		// ?menu_id=MENU01, &bnum=0,  &lvl=0, &step=0, &nref=0
		// &nowpage=1 ,  &pagecount=5,  &pagegrpnum=1 
		
		// 메뉴리스트
		List<MenuVo>  menuList = menuService.getMenuList();
		
		// 페이지 이동
		ModelAndView   mv  =  new ModelAndView();
		mv.addObject("menuList", menuList);
		mv.addObject("map",      map);		
		mv.setViewName("pdsSp/write");
		return  mv;		
	}
	
	// 파일정보를 받으려면 HttpServletRequest request 추가 필요 - 파일받으려고
	@RequestMapping("/PdsSp/Write")
	public   ModelAndView   write(
		@RequestParam  HashMap<String, Object>  map,
		HttpServletRequest request) {
		
		// System.out.println("write() map:" + map);  // 뱐수값 만 전달
		// map 
		// ?menu_id=MENU01,
        // 새글 &bnum=0,  &lvl=0, &step=0, &nref=0
		// &nowpage=1 ,  &pagecount=5,  &pagegrpnum=1
		// writer=, title= ,  cont=,   upfile=, upfile1 , ....
		
		// 새글 저장 : MBoard       - 게시글  저장 
		//             Files        - 첨부파일 목록
		//             c:\\upload\\ - 첨부파일 저장
			
		String menu_id   =  (String) map.get("menu_id");
		
		pdsSpService.setWrite(map, request);
				
		int  nowpage     =  Integer.parseInt((String) map.get("nowpage"));
		int  pagecount   =  Integer.parseInt((String) map.get("pagecount"));
		int  pagegrpnum  =  Integer.parseInt((String) map.get("pagegrpnum"));
		String  loc = "redirect:/PdsSp/List?menu_id=" + menu_id;
		loc        += "&nowpage="    + nowpage;
		loc        += "&pagecount="  + pagecount;
		loc        += "&pagegrpnum=" + pagegrpnum;
		ModelAndView  mv  = new ModelAndView();
		mv.addObject("map", map);
		mv.setViewName( loc );
		return mv;
		
	}
	
}




