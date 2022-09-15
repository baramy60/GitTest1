package com.green.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.user.service.UserService;
import com.green.user.vo.UserVo;

@Controller
public class HomeController {
	
	@Autowired
	private  UserService   userService;
	
	@RequestMapping("/")
	public  String  home() {
		return "home";
	}
	
	@RequestMapping("/login")
	public  String  login() {
		return "user/login"; //WEB-INF/views/user/login.jsp
	}
	
	@RequestMapping("/loginProcess")
	public  String   loginProcess(
		HttpSession     session,
		@RequestParam   HashMap<String, Object> map) {
		
		String returnURL = "";
		if( session.getAttribute("login") != null ) {
			// 기존 login 이란 session 에 값이 존재한다면
			session.removeAttribute("login");  // 기존값을 제거한다
		}
		
		// 로그인을 성공하면 UserVo 객체를 반환함
		UserVo  vo  = userService.login( map );
		if ( vo != null ) {
			session.setAttribute("login", vo);
			returnURL = "redirect:/";        // 로그인 성공시
		} else {
			returnURL = "redirect:/login";	 // 로그인 실패시		
		}
		return returnURL;		
	}
	
	// 로그아웃
	@RequestMapping("/logout") 
	public  String  logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";  // 로그아웃시 이동할 주소 -> /login
	}
	
	@RequestMapping("/User/List")
	public  String  list( Model model ) {
		
		List<UserVo> userList = userService.getUserList();
		List<String> msgList  = new ArrayList<>(); 
		
		for (UserVo userVo : userList) {
			String msg = userVo.getUserid() + "\n" + userVo.getUsername();
			msgList.add( msg );
		}
		
		model.addAttribute("userList", userList);
		model.addAttribute("msgList",  msgList);
		
		
		return "user/list"; 
	} 
	
	// html 로 이동
	/*
	@RequestMapping("/a")
	public  String  a() {
		return "redirect:/static/html/ajax01.html";
		//return  "ajax01.html";  // 오류  /WEB-INF/ajax01.html.jsp
	}
	@RequestMapping("/b")
	public  String  b() {
		return "redirect:/static/html/ajax02.html";
	}*/
	
	@RequestMapping("/{val}")
	public  String  html( @PathVariable String val ) {
		String loc = "redirect:/static/html/";
		switch(val) {
		case "a" : loc += "ajax01.html"; break; 
		case "b" : loc += "ajax02.html"; break; 
		case "c" : loc += "ajax03.html"; break; 
		}
		return loc;
	}
	
	//-------------------------------------
	@RequestMapping("/ajax")
	public  void   ajax(  String v, HttpServletResponse response  ) {
		
		String  fmt  = "{"
				+ "   \"id\"   : \"sky\","
				+ "   \"pass\" : \"234\","
				+ "   \"v\"    : \"%s\" "
				+ "}";   // json
		String  data = String.format(fmt, v);
		
		try {		
			response.setContentType("application/json;charset=utf-8");
			PrintWriter  out = response.getWriter();
			out.print( data );
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// json library 사용 : map data -> json으로 출력
	// json library 추가해야함 pom.xml : jackson-databind-2.12.0.jar
	
	// @ResponseBody 사용 : viewresolver 를 거치지 않고 xml, json을 출력가능하게 해준다	
	@RequestMapping("/ajax2")
	@ResponseBody
	public  Map<String, Object>   ajax2(  String v ) {
		Map<String, Object> map = new HashMap<>(); 
		map.put("id",   "sky");
		map.put("pass", "234");
		map.put("v",     v);
		return   map;   // map -> .json
	}
	
	@RequestMapping("/ajaxlist")
	@ResponseBody
	public  Map<Integer, Object > ajaxlist() {
		
		List<UserVo> userList  =  userService.getUserList();
		// arraylist -> map
		
		Map<Integer, Object> map = new HashMap<>();
		for (int i = 0; i < userList.size(); i++) {
			map.put(i, userList.get(i) );
		}		
		return map;
	}
	
	@RequestMapping("/ajaxlist2") 
	@ResponseBody
	public  Map<String, Object> ajaxlist2() {
		
		List<UserVo> userList = userService.getUserList();
		Map<String, Object> map = new HashMap<>();
		map.put("data", userList);		
		return map;
	}
	
}









