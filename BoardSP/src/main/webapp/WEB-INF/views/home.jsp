<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico" />
<link rel="stylesheet" href="/css/common.css" />
<style>
   h2 { margin : 20px;  }
</style>
</head>
<body>
  <div id="main">
	<h2>Home</h2>
	<a href="/User/List">사용자 목록</a><br>
	<hr />
	
	<h2>게시판</h2>
	<a href="/Board/List?menu_id=MENU01">게시물 목록</a><br>
	<hr />
	
	<h2>Sp 게시판(Stored Procedure)</h2>
	<a href="/BoardSp/List?menu_id=MENU01">게시물 목록</a><br>
	<hr />
		
	<h2>자료실</h2>
	<a href="/Pds/List?menu_id=MENU01">자료실 목록</a><br>
	<a href="/Pds/WriteForm?menu_id=MENU01&bnum=0&lvl=0&step=0&nref=0">자료실 추가</a><br>
	<hr />
	
	<h2>Sp 자료실(Stored Porcedure)</h2>
	<a href="/PdsSp/List?menu_id=MENU01&nowpage=1&pagecount=10&pagegrpnum=1">자료실 목록</a><br>
	<a href="/PdsSp/WriteForm?menu_id=MENU01&bnum=0&lvl=0&step=0&nref=0">자료실 추가</a><br>
	<hr />
		
	<hr />
	<h2>메뉴</h2>
	<a href="/Menus/List">메뉴 관리</a>
	
	<hr />
	${ sessionScope.login.username } 님 환영합니다.<br>
    당신의 포인트 ${ login.indate } 입니다.<br>
    <a href="/logout">로그아웃</a>
	
	<hr />
	<h2>Ajax Test</h2>
	<a href="/a">ajax01.html</a><br>
	<a href="/b">ajax02.html</a><br>
	<a href="/c">ajax01.html</a><br>
  </div>	
</body>
</html>