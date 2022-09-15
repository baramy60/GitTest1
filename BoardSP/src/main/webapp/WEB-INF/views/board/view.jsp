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
  #board  td:nth-of-type(1) { width:150px; text-align:center;}
  #board  td:nth-of-type(2) { width:400px; text-align:left;}
  #board  td:nth-of-type(3) { width:150px; text-align:center;}
  #board  td:nth-of-type(4) { width:400px; text-align:left;}
  
  #board  tr:nth-of-type(4) { height : 400px; vertical-align: top; }
  
  
  #board  input     { width:100%; }
  #board  textarea  { width:100%; height: 400px;  }
  
  
</style>
<script>

</script>
</head>
<body>
   <div id="main">
    <!--  메뉴목록 (menuList) -->
    <%@ include file="/WEB-INF/include/menus.jsp" %>
    
    <!--  내용보기 -->      
      <table id="board">
       <caption><h2>내용 보기</h2></caption>
       <tr>
        <td>번호</td>        
        <td>${ boardVo.idx } </td>
        <td>조회수</td>        
        <td>${ boardVo.readcount } </td>
       </tr>
       <tr>
        <td>작성일</td>        
        <td>${ boardVo.regdate } </td>
        <td>작성자</td>        
        <td>${ boardVo.writer } </td>
       </tr>
       <tr>
        <td>제목</td>        
        <td colspan="3">${ boardVo.title } </td>        
       </tr>
       <tr>
        <td>내용</td>
        <td colspan="3">${ boardVo.cont }</td>
       </tr>
       <tr>
        <td colspan="4"> 
        [<a href="/Board/WriteForm?menu_id=${menu_id}&bnum=0&lvl=0&step=0&nref=0">새 글 쓰기</a>]  
        [<a href="/Board/WriteForm?menu_id=${menu_id}&idx=${boardVo.idx}&bnum=${boardVo.bnum}&lvl=${boardVo.lvl}&step=${boardVo.step}&nref=${boardVo.nref}">답 글 쓰기</a>]     
        [<a href="/Board/UpdateForm?idx=${boardVo.idx}&menu_id=${menu_id}">수정</a>]        
        [<a href="/Board/Delete?idx=${boardVo.idx}&menu_id=${menu_id}">삭제</a>]        
        [<a href="/Board/List?menu_id=${ menu_id }">목록으로</a>]        
        [<a href="javascript:history.back()">이전으로</a>]        
        [<a href="/">Home</a>]        
        </td>
       </tr>
      </table> 
      
   </div>
   <div style="height:300px;"></div>
</body>
</html>






