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
  .td1 { width:150px; text-align: center; }
  .td2 { width:400px; text-align: left; }
  .td3 { width:150px; text-align: center; }
  .td4 { width:400px; text-align: left; }
  
  [type=text]  { width:100%;  }
  textarea     { width:100%; height: 300px;  }
</style>
<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $( function() {
	  $('form').on('submit', function(e) {
		  if( $('[name=title]').val().trim() == ''   ) {
			  alert('제목을 입력하세요');
		  	  return false;
		  }
		  // jqeury 에서는 return false는 아래 두 문장과 동일
		  //e.preventDefault();
		  //e.stopPropagation();
	  });
	  
	  $('#btnList').on('click', function() {
		  alert('button click');
		  let    url     =  '/Board/List?menu_id=${menu_id}';
		  location.href  =  url;
	  } )
	  	
	  $('#btnLinkList').on('click', function(e){
		  e.preventDefault(); // 먼저수행하고 기능 구현
		  e.stopPropagation();
		  
		  alert('a click');
		//  let    url     =  '/Board/List?menu_id=${menu_id}';
		  let    url     =  $(this).prop('href');
		  location.href  =  url;		  
	  })
	
	   // a tag 를 버튼 으로 사용하려면
	   // a tag는 클릭하면 href 로 이동한다, href가 비어있으면 새로고침
	   // 새로고침을 막아야 버튼 사용가능
	   $('#btn1').on('click', function(e) {
		   e.preventDefault(); // 먼저수행하고 기능 구현
		   e.stopPropagation();
		   
		   alert('aaa');
		   
	   })
	  
  })
</script>
</head>
<body>
  <div id="main">
    <!-- 메뉴 목록 -->
    <%@include file="/WEB-INF/include/menus.jsp"  %>
    
    <!-- 게시물 수정 -->
    <form action="/Board/Update" method="POST">
    <input type="hidden" name="idx"     value="${ boardVo.idx  }" /> 
    <input type="hidden" name="menu_id" value="${ menu_id } " /> 
    <table  id="boardUpdate">
     <caption><h2>${ menu_id } 게시물 수정</h2></caption>
     <tr>
       <td class="td1">메뉴 아이디</td>
       <td class="td2" colspan="3">${ menu_id }</td>
     </tr>
     <tr>
       <td class="td1">번호</td>
       <td class="td2">${ boardVo.idx  }</td> <%--  <%=boardVo.getidx() %> --%>
       <td class="td3">날짜</td>
       <td class="td4">${ boardVo.regdate }</td>
     </tr>
     <tr>
       <td class="td1">글쓴이</td>
       <td class="td2">${ boardVo.writer }</td>
       <td class="td3">조회수</td>
       <td class="td4">${ boardVo.readcount }</td>
     </tr>
     <tr>
       <td class="td1">제목</td>
       <td class="td2"  colspan="3">
       	 <input type="text" name="title" value="${ boardVo.title }" />
       </td>
     </tr>
     <tr>
       <td class="td1">내용</td>
       <td class="td2"  colspan="3">
       	 <textarea name="cont">${ boardVo.cont }</textarea>
       </td>
     </tr>
     <tr>
       <td class="td1" colspan="4">
         <input type="submit"  value="수정" />
         <input type="button"  value="글 목록" id="btnList" />
         <a href="/Board/List?menu_id=${menu_id}" id="btnLinkList">글 목록</a>
         <a href='#' id="btn1">단추</a>
       </td>      
     </tr>
    </table>
    </form>
   
  </div>
</body>
</html>














