<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  input[type=button] {
      display : inline-block;
      height: 100px; width:100px;
  }
</style>
</head>
<body>
   <h2>사용자 목록</h2>
   <c:forEach var="user" items="${ userList }">
   <ul>
      <li>${ user.userid   }</li>
      <li>${ user.username }</li>
      <li>${ user.indate   }</li>
   </ul>
   </c:forEach>
   
   <c:forEach var="msg" items = "${ msgList }">
   <input type="button" name="v" value="${ msg }" />
   </c:forEach>
</body>
</html>