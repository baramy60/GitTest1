<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico" />
<link rel="stylesheet" href="/css/common.css" />
<style>
  #board  td:nth-of-type(1),
  #board  th:nth-of-type(1) { width:100px; text-align:center;}
  #board  td:nth-of-type(2),
  #board  th:nth-of-type(2) { width:500px; }
  #board  td:nth-of-type(3), 
  #board  th:nth-of-type(3) { width:100px; text-align:center; }
  #board  td:nth-of-type(4), 
  #board  th:nth-of-type(4) { width:120px; text-align:center; }
  #board  td:nth-of-type(5), 
  #board  th:nth-of-type(5) { width:100px; text-align:center; }
</style>
</head>
<body>
  <div>login : ${ login }</div>
  <div id="main"> 
  <!--  메뉴목록  -->
  <%@ include file="/WEB-INF/include/menus.jsp" %>
    
  <!--  게시물 목록 -->
  <table id="board">
  <caption><h2>${ currMenu.menu_name } 게시판</h2></caption>
  <tr> 
    <td colspan="5" class="right">
       [<a href="/Board/WriteForm?menu_id=${ currMenu.menu_id } ">새글 쓰기</a>]
    </td> 
  </tr>
  <tr>
    <th>번호</th>
    <th>제목</th>
    <th>글쓴이</th>
    <th>날짜</th>
    <th>조회수</th>
  </tr>
  <c:forEach  var="board" items="${ boardList }">
    <tr>
      <td>
      <c:if test="${ board.lvl eq 0 }">
      	${ board.bnum }
      </c:if>
      </td>
      <td>
        <b style="display:inline-block;width:${board.lvl*20}px"></b>
        <a href="/Board/View?menu_id=${board.menu_id}&idx=${board.idx}&menu_name=${currMenu.menu_name}">${ board.title }</a>
      </td>
      <td>${ board.writer }</td>
      <td>${ board.regdate }</td>
      <td>${ board.readcount }</td>
    </tr>
  </c:forEach>  
  </table>
 
 </div> 
</body>
</html>







