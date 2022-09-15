package com.green.board.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.board.service.BoardService;
import com.green.board.vo.BoardVo;
import com.green.menus.service.MenuService;
import com.green.menus.vo.MenuVo;

@Controller
@RequestMapping("/Board")
public class BoardController {

	@Autowired
	private  MenuService    menuService;  
	
	@Autowired
	private  BoardService   boardService;
	
	// http://localhost:9090/Board/List?menu_id=MENU03
	@RequestMapping("/List")
	public   ModelAndView   list( String  menu_id ) {
		
		// 전체 메뉴목록
		List<MenuVo>   menuList   =  menuService.getMenuList();
		
		// 게시물 목록
		List <BoardVo> boardList  =  boardService.getBoardList( menu_id );
		
		// menu_id 로 조회한 현재 메뉴 정보
		MenuVo   currMenu;
		if( menu_id != null  )
			currMenu  =  menuService.menuView( menu_id );
		else 
			currMenu  =  new MenuVo(null, "전체", 0); 
		
		ModelAndView   mv  = new ModelAndView();
		mv.addObject("currMenu",  currMenu);		
		mv.addObject("menuList",  menuList);		
		mv.addObject("boardList", boardList);		
		mv.setViewName("board/list");  // /WEB-INF/views/board/list.jsp
		return  mv; 
	} 
	
	// http://localhost:9090/Board/WriteForm?menu_id=MENU03
	// <a href="/Board/WriteForm?menu_id=MENU01&bnum=0&lvl=0&step=0&nref=0">새 글 쓰기</a>
	// <a href="/Board/WriteForm?menu_id=MENU01&idx=8&bnum=6&lvl=0&step=0&nref=6">답 글 쓰기</a>
	@RequestMapping("/WriteForm")
//	public  ModelAndView  writeForm( String menu_id ) {
//	public  ModelAndView  writeForm( @RequestParam BoardVo vo	) { 
	  //@RequestParam 사용시 BAD REQUEST 400 ,   @RequestParam  빼고 사용 oK
	public  ModelAndView  writeForm( BoardVo vo	) {
			
		String menu_id = vo.getMenu_id();
		
		// 메뉴목록조회
		List<MenuVo>  menuList =  menuService.getMenuList();
		
		if( vo.getBnum()  > 0 ) {
			HashMap<String, Object> map = new  HashMap<String, Object>();
			map.put("idx", vo.getIdx());					
			vo  =  boardService.getBoard(map);
			vo.setCont(vo.getCont() + "\n====================\n");
		} 
			
		ModelAndView  mv = new ModelAndView();
		mv.addObject("menuList", menuList);
		mv.addObject("menu_id",  menu_id);
		mv.addObject("vo",       vo);
		mv.setViewName("board/write");		
		return  mv;
		
	}
	
	// /Board/Write
	// menu_id, title, writer, cont, + bnum, lvl, step, nref
	@RequestMapping("/Write")
//	public  ModelAndView  write( @RequestParam BoardVo boardVo ) {
// https://velog.io/@hellozin/RequestParamrequired-false-%EC%A3%BC%EC%9D%98%ED%95%A0-%EC%A0%90
//	https://stackoverflow.com/questions/61520572/spring-throw-bad-request-if-the-request-param-not-available-in-controller-method
	public  ModelAndView  write(  BoardVo boardVo ) {
		// 새글 저장/ 답글 저장
		boardService.boardInsert ( boardVo );
		
		ModelAndView  mv = new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + boardVo.getMenu_id());
		return  mv;
	}
	
	// http://localhost:9090/Board/View?menu_id=MENU01&idx=4
	@RequestMapping("/View")
//	public  ModelAndView   view(String  menu_id, int idx) {
	public  ModelAndView   view(
		@RequestParam  HashMap<String, Object> map	) {
		// HomeController view() map:{menu_id=MENU01, idx=4}
		
		// 메뉴목록
		List<MenuVo>  menuList  =  menuService.getMenuList();
		
		String        menu_id   =  (String) map.get("menu_id");
		
		// idx 로 조회된 보여줄 항목 
		BoardVo       boardVo   =  boardService.getBoard( map ); 
		boardVo.setCont(boardVo.getCont().replace("\n", "<br>" ));
		
		ModelAndView  mv        =  new ModelAndView();
		mv.addObject("menuList",  menuList  );
		mv.addObject("menu_id",   menu_id   );
		mv.addObject("boardVo",   boardVo   );
		mv.setViewName( "board/view" );
		return  mv;
	}
	
	// http://localhost:9090/Board/Delete?idx=7&menu_id=MENU03
	// /Board/Delete?idx=5&menu_id=MENU01"
	@RequestMapping("/Delete")
	public  ModelAndView   delete(
			@RequestParam	HashMap<String, Object> map) {
		
		// idx 해당 글 삭제
		boardService.boardDelete( map );
		
		String  menu_id = (String) map.get("menu_id");		
		ModelAndView  mv  = new  ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id);
		return  mv;
		
	}
	
	// /Board/UpdateForm?idx=5&menu_id=MENU01
	@RequestMapping("/UpdateForm")
	public  ModelAndView   updateForm(
			@RequestParam HashMap<String, Object>  map) {
				
		//  메뉴 목록
		List<MenuVo>  menuList  =  menuService.getMenuList();
		
		String        menu_id   =  (String) map.get("menu_id");   
		
		// 수정할 자료를 idx 로 조회
		BoardVo       boardVo   =  boardService.getBoard( map );
				
		ModelAndView  mv  =  new ModelAndView();
		mv.addObject("menuList",  menuList );
		mv.addObject("menu_id",   menu_id);
		mv.addObject("boardVo",   boardVo);
		mv.setViewName("board/update");
		return  mv;
	} 
	
	// /Board/Update   {idx, menu_id, title, cont }
	@RequestMapping("/Update")
	public  ModelAndView update(
			@RequestParam HashMap <String, Object> map ) {
		
		//System.out.println("map:" + map);
		// 수정
		boardService.boardUpdate( map );
		
		String  menu_id = (String) map.get("menu_id"); 
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id);
		return  mv;
		
	}   
	
}










