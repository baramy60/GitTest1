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
    <h2>메뉴 추가2</h2>
    <form action="/Menus/Write2" method="POST">
    <div>
    메뉴이름:<input type="text" name="menu_name" />
    </div>
    <div>
      <input type="submit" value="추가" />
    </div>
    </form>  
  </div>
</body>
</html>



