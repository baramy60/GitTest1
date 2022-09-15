package com.green.boardsp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.boardsp.service.BoardSpService;
import com.green.boardsp.vo.BoardVo;
import com.green.menus.service.MenuService;
import com.green.menus.vo.MenuVo;

@Controller
public class BoardSPController {
	
	@Autowired
	private  MenuService     menuService;
	
	@Autowired
	private  BoardSpService  boardSpService;
	
	// http://localhost:9090/BoardSp/List?menu_id=MENU03
	@RequestMapping("/BoardSp/List")
	public  ModelAndView  list( @RequestParam HashMap<String, Object> map ) {
		
		// 메뉴 목록 조회
		List<MenuVo>  menuList =  menuService.getMenuList();
		
		// menu_id 로 조회한 현재 메뉴 정보
		String   menu_id = (String) map.get("menu_id");
		MenuVo   currMenu;
		if( menu_id != null)
			currMenu = menuService.menuView( menu_id );
		else
			currMenu = new MenuVo(null, "전체", 0);
		
		// 게시물 목록
		List<BoardVo>  boardList = boardSpService.getBoardList( map );
		
		ModelAndView   mv = new ModelAndView();
		mv.addObject("currMenu",  currMenu  );
		mv.addObject("menuList",  menuList  );
		mv.addObject("boardList", boardList );		
		mv.setViewName("boardSp/list");  // /WEB-INF/views/board/list.jsp
		return mv;
	}
	
	//http://localhost:8080/BoardSp/View?menu_id=MENU01&idx=34&menu_name=JAVA
	@RequestMapping("/BoardSp/View")
	public  ModelAndView  view(@RequestParam HashMap<String, Object> map) {
		
		String       menu_id  =  (String) map.get("menu_id");
		
		List<MenuVo> menuList =  menuService.getMenuList();
		
		BoardVo      boardVo  =  boardSpService.getView( map );
		// "\n" -> "<br>";
		boardVo.setCont( boardVo.getCont().replace("\n", "<br>") );
						
		ModelAndView  mv  =   new ModelAndView();
		mv.addObject("menu_id",  menu_id);
		mv.addObject("menuList", menuList );
		mv.addObject("boardVo",  boardVo );
		mv.setViewName("boardSp/view");
		return mv;		
	}
	
	// http://localhost:8080/BoardSp/UpdateForm?idx=34&menu_id=MENU01
	// 수정
	@RequestMapping("/BoardSp/UpdateForm")
	public  ModelAndView   updateForm(@RequestParam  HashMap<String, Object> map) {
		
		String         menu_id   =  (String) map.get("menu_id");
		List<MenuVo>   menuList  =  menuService.getMenuList();
		BoardVo        boardVo   =  boardSpService.getView( map );
		
		ModelAndView  mv = new ModelAndView();
		mv.addObject("menu_id",   menu_id);
		mv.addObject("menuList",  menuList);	
		mv.addObject("boardVo",   boardVo);
		
		mv.setViewName("boardSp/update");
		return    mv;
		
	}
	
	@RequestMapping("/BoardSp/Update")
	public   ModelAndView   update(@RequestParam  HashMap<String, Object> map) {
		String   menu_id   =  (String) map.get("menu_id"); 
		
		// 수정
		boardSpService.updateBoard( map );
		
		ModelAndView   mv = new ModelAndView();
		mv.setViewName("redirect:/BoardSp/List?menu_id=" + menu_id);
		return  mv;
	}   
	
	// http://localhost:8080/BoardSp/Delete?idx=34&menu_id=MENU01
	// 삭제
	@RequestMapping("/BoardSp/Delete")
	public   ModelAndView   delete(@RequestParam  HashMap<String, Object> map ) {
		
		String   menu_id  =  (String) map.get("menu_id");
		
		boardSpService.deleteBoard( map );
		
		ModelAndView   mv = new ModelAndView();
		mv.setViewName("redirect:/BoardSp/List?menu_id=" + menu_id);
		return  mv;
		
	}
	
	// /BoardSp/WriteForm?menu_id=MENU01&bnum=0&lvl=0&step=0&nref=0
	@RequestMapping("/BoardSp/WriteForm")
	public  ModelAndView   writeForm(@RequestParam HashMap<String, Object> map) {
		
		String         menu_id   =  (String) map.get("menu_id");
		int            bnum      =  Integer.parseInt( map.get("bnum") + "" ) ;
		int            lvl       =  Integer.parseInt( map.get("lvl") + "" ) ;
		int            step      =  Integer.parseInt( map.get("step") + "" ) ;
		int            nref      =  Integer.parseInt( map.get("nref") + "" ) ;
		
		List<MenuVo>   menuList  =  menuService.getMenuList();
	
		BoardVo        boardVo   =  new BoardVo(); // 넘어온 파라미터 전달
		boardVo.setMenu_id(menu_id);
		boardVo.setBnum( bnum );
		boardVo.setLvl( lvl );
		boardVo.setStep( step );
		boardVo.setNref( nref );		
		
		ModelAndView  mv = new ModelAndView();
		mv.addObject("menuList", menuList);
		mv.addObject("menu_id", menu_id);
		mv.addObject("vo",      boardVo); // 넘어온 파라미터 저장	
		mv.setViewName("board/write");
		return mv;
	}
	
	@RequestMapping("/BoardSp/Write")
	public  ModelAndView   write(@RequestParam HashMap<String, Object> map) {
		
		String         menu_id   =  (String) map.get("menu_id");
		
		boardSpService.insertBoard( map );	
		
		ModelAndView  mv = new ModelAndView();			
		mv.setViewName("redirect:/BoardSp/List?menu_id=" + menu_id);
		return mv;
	}
	
	
}












