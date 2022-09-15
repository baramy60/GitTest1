<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico" />
<link rel="stylesheet" href="/css/common.css" />
<style>
  #PdsList  td:nth-of-type(1) { width:70px;  text-align: center; }   
  #PdsList  td:nth-of-type(2) { width:300px; text-align: left; }  
  #PdsList  td:nth-of-type(3) { width:80px; text-align: center; }  
  #PdsList  td:nth-of-type(4) { width:80px; text-align: center; }  
  #PdsList  td:nth-of-type(5) { width:100px; text-align: center; }  
  #PdsList  td:nth-of-type(6) { width:100px; text-align: center; }  
</style>
</head>
<body>
  <div id="main">
      <!-- 자료실 메뉴 목록 -->
      <%@include file="/WEB-INF/include/pdsmenus.jsp" %>
      
      <!-- 자료실 pdsList -->   
     <table id="PdsList">
       <caption><h2>${ menu_name } 자료실</h2></caption>
       <!-- 새글 쓰기 -->
       <tr>
         <td class="right" colspan="6">
         <a href="/PdsSp/WriteForm?menu_id=${ menu_id }&bnum=0&lvl=0&step=0&nref=0&nowpage=${pagePdsVo.nowpage}&pagecount=${pagePdsVo.pagecount}&pagegrpnum=${pagePdsVo.pagegrpnum}">[새 글 쓰기]</a>
         </td>
       </tr>
       <tr>
         <td>번호</td>
         <td>제목</td>
         <td>작성자</td>
         <td>조회수</td>
         <td>파일첨부</td>
         <td>작성일</td>       
       </tr>     
     
     
       <c:forEach var="pds"  items="${ pdsList }">
        <tr>
         <!-- 번호 -->
         <td>
	       <c:if test="${ pds.lvl eq 0 }">
	         ${ pds.bnum }
	       </c:if>
         </td>
        
        <!-- 제목 -->        
         <td>        
           <c:choose>                       
            <c:when test="${ pds.lvl eq 0 }">
              <c:choose>
                <c:when test="${ pds.delnum eq 0 }">
	              <a href="/PdsSp/View?menu_id=${ pds.menu_id }&idx=${pds.idx}&nowpage=${pagePdsVo.nowpage}&pagecount=${pagePdsVo.pagecount}&pagegrpnum=${pagePdsVo.pagegrpnum}">
	               ${ pds.title }
	              </a>
	            </c:when>
	            <c:otherwise>  
	              <s>삭제된 글입니다</s>
                </c:otherwise> 
              </c:choose>
            </c:when>    
            <c:otherwise>
             <b style="display:inline-block;width:${pds.lvl*20}px"></b> 
             <c:choose>
                <c:when test="${ pds.delnum eq 0  }">
	              <a href="/PdsSp/View?menu_id=${ pds.menu_id }&idx=${pds.idx}&nowpage=${pagePdsVo.nowpage}&pagecount=${pagePdsVo.pagecount}&pagegrpnum=${pagePdsVo.pagegrpnum}">
	               [답글] ${ pds.title }
	              </a>
	            </c:when>
	            <c:otherwise>  
	              [답글] <s>삭제된 글입니다</s>
                </c:otherwise> 
              </c:choose>
            </c:otherwise>
          </c:choose>
         </td>         
         
         <!-- 작성자  -->
         <td>${ pds.writer }</td>
         <!-- 조회수  -->
         <td>${ pds.readcount }</td>
         <!-- 파일첨부수  -->
         <td>
         <c:choose>
          <c:when test="${pds.filescount eq 0 }">
            &nbsp;
          </c:when>
          <c:otherwise>
        	 ${ pds.filescount }          
          </c:otherwise>         
         </c:choose>
         
         </td>
         <!-- 작성일  -->
         <td>${fn:substring(pds.regdate, 0, 10) }</td>     
         
        </tr>
        
       </c:forEach>
       <!-- 페이징 영역 -->
       <tr>
        <td colspan="6">
           <%@ include file="/WEB-INF/include/paging.jspf"  %>
        </td>
       </tr>
     </table>
  
  </div>
</body>
</html>














