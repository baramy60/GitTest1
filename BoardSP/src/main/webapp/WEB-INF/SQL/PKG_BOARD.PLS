create or replace NONEDITIONABLE PACKAGE PKG_PDS AS 

  -- 목록 조회(페이징)
  PROCEDURE   PROC_PDS_PAGINGLIST(
      IN_MENU_ID         IN      VARCHAR2,
      IN_NOWPAGE         IN      NUMBER,
      IN_PAGECOUNT       IN      NUMBER,
      OUT_TOTALCOUNT     OUT     NUMBER,
      O_CUR              OUT     SYS_REFCURSOR 
  );

  -- 새글 쓰기, 게시물 추가
  PROCEDURE   PROC_PDS_INSERT(
      IN_MENU_ID       IN      VARCHAR2,
      IN_TITLE         IN      VARCHAR2,
      IN_CONT          IN      VARCHAR2,
      IN_WRITER        IN      VARCHAR2,
      IN_BNUM          IN      NUMBER,
      IN_LVL           IN      NUMBER,
      IN_STEP          IN      NUMBER,
      IN_NREF          IN      NUMBER,

      --- 파일 업로드에 대한 추가 항목 (FILE_ARRAY : ORACLE TYPE(배열)을 생성한 것)
      IN_FILENAME      IN      FILE_ARRAY, 
      IN_FILEEXT       IN      FILE_ARRAY, 
      IN_SFILENAME     IN      FILE_ARRAY 
  );
  
  PROCEDURE   PROC_PDS_VIEW(
      IN_IDX          IN       NUMBER,
      O_CUR           OUT      SYS_REFCURSOR  
  );
  
  PROCEDURE   PROC_PDS_FILELIST(
      IN_IDX          IN       NUMBER,
      O_CUR           OUT      SYS_REFCURSOR  
  );

END PKG_PDS;









