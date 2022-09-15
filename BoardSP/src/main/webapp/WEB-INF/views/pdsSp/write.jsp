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
   #pdsWriteTable  td:first-child { wdith:150px; text-align: center; }
   #pdsWriteTable  td:last-child  { text-align: left; }
   
   input[type=text]  { width:100%; }
   textarea          { width:100%;  height: 300px;   }
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
   var num = 1;
   $(function() {
	   $('#btnAddFile').on('click', function() {
		   let tag  = '<input type="file" name=\"upfile' + num;
		   tag     += '\" class="upfile" /><br>';
		   $('#tdfile').append( tag  );
		   alert(tag);
		   num++;
	   } );
   });
</script>
</head>
<body>
  <div id="main">
    <!-- 메뉴 목록 -->
    <%@include file="/WEB-INF/include/menus.jsp" %>
    
    <!-- 새글 쓰기 자료실 -->
    <!--  파일전송시 필수 enctype = "multipart/form-data" -->
    <form action  = "/PdsSp/Write" method="POST" id="form1"
          enctype = "multipart/form-data">
      <input type="hidden" name="menu_id"      value="${ map.menu_id }" />   
      <input type="hidden" name="bnum"         value="${ map.bnum }" />   
      <input type="hidden" name="lvl"          value="${ map.lvl  }" />   
      <input type="hidden" name="step"         value="${ map.step }" />   
      <input type="hidden" name="nref"         value="${ map.nref }" />          
      <input type="hidden" name="nowpage"      value="${ map.nowpage }" />          
      <input type="hidden" name="pagecount"    value="${ map.pagecount }" />          
      <input type="hidden" name="pagegrpnum"   value="${ map.pagegrpnum }" />          
          
    <table id="pdsWriteTable">
      <caption><h2> 새 글 쓰기(Sp자료실)</h2></caption>
      <tr>
        <td>작성자</td>
        <td><input type="text" name="writer" id="writer" /></td>
      </tr>
      <tr>
        <td>글제목</td>
        <td><input type="text" name="title" id="title" /></td>
      </tr>
      <tr>
        <td>글내용</td>
        <td><textarea name="cont" id="cont"></textarea></td>
      </tr>
      <tr>
        <td>파일</td>
        <td id="tdfile">
           <input type="button" id="btnAddFile" value="파일 추가"/><br>
           <input type="file" name="upfile" class="upfile" /><br>
        </td>
      </tr>
      <tr>
        <td  colspan="2">
          <input type="submit"  value="저장" />
        </td>
      </tr>
    
    </table>                
          
    </form>
  </div>
</body>
</html>





