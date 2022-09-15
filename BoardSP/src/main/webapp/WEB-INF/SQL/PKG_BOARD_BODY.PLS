create or replace NONEDITIONABLE PACKAGE BODY PKG_BOARD AS

  -- 게시물 목록
  PROCEDURE PROC_BOARD_LIST(
     IN_MENU_ID   IN    VARCHAR2,
     O_CUR        OUT   SYS_REFCURSOR
  ) AS
  BEGIN
    OPEN O_CUR FOR
     SELECT   IDX,
             MENU_ID,
             DECODE( LVL, 0, '', '[Re] ' ) || TITLE  AS TITLE,
             WRITER,
             TO_CHAR(REGDATE, 'YYYY-MM-DD') REGDATE,
             READCOUNT,
             BNUM, LVL, STEP, NREF    
     FROM  
        BOARD
     WHERE 
        MENU_ID = IN_MENU_ID
     ORDER BY
        NREF   DESC,  STEP ASC;

  END PROC_BOARD_LIST;

  -- 게시물 내용보기
  PROCEDURE  PROC_BOARD_VIEW(
     IN_IDX      IN    NUMBER,
     O_CUR       OUT   SYS_REFCURSOR
  ) AS
  BEGIN
  
    UPDATE  BOARD
     SET    READCOUNT = READCOUNT + 1
     WHERE  IDX = IN_IDX;
  
    OPEN  O_CUR  FOR  
      SELECT
          IDX,
          MENU_ID,
          TITLE,
          NVL(CONT, ' ') CONT,
          WRITER,
          REGDATE,
          READCOUNT,
          BNUM,
          LVL,
          STEP,
          NREF      
       FROM  
          BOARD
       WHERE
          IDX      =   IN_IDX;
      
  END PROC_BOARD_VIEW;
  
  -- 게시물 삭제
  PROCEDURE  PROC_BOARD_DELETE(
     IN_IDX     IN    NUMBER
  ) AS
      V_NREF    NUMBER(5);
      V_LVL     NUMBER(5);
      V_STEP    NUMBER(5);  
  BEGIN
    
    SELECT   NREF,    LVL,    STEP  
     INTO    V_NREF,  V_LVL,  V_STEP  
     FROM    BOARD
     WHERE   IDX = IN_IDX;     
    
    DELETE   FROM BOARD
     WHERE   IDX  IN 
     (
        SELECT   IDX  
          FROM   BOARD
          WHERE  NREF =   V_NREF
           AND   LVL  >=  V_LVL
           AND   STEP >=  V_STEP
     );    
     COMMIT;
        
  END PROC_BOARD_DELETE;

 --  게시물 수정
  PROCEDURE  PROC_BOARD_UPDATE(
     IN_IDX     IN     NUMBER,
     IN_TITLE   IN     VARCHAR2,
     IN_CONT    IN     VARCHAR2
  ) AS
  BEGIN
   
    UPDATE   BOARD
     SET     TITLE  =  IN_TITLE,
             CONT   =  IN_CONT
     WHERE   IDX    =  IN_IDX;
   COMMIT;  
   
   
  END PROC_BOARD_UPDATE;

  -- 게시물 추가
  PROCEDURE  PROC_BOARD_INSERT(
     IN_MENU_ID  IN   VARCHAR2, 
     IN_BNUM     IN   NUMBER,
     IN_LVL      IN   NUMBER,
     IN_STEP     IN   NUMBER, 
     IN_NREF     IN   NUMBER,
     IN_TITLE    IN   VARCHAR2,
     IN_CONT     IN   VARCHAR2,
     IN_WRITER   IN   VARCHAR2
  ) AS
     V_IDX     NUMBER(10);
     V_BNUM    NUMBER(6);
     V_LVL     NUMBER(6);
     V_STEP    NUMBER(6);
     V_NREF    NUMBER(6);
  BEGIN
    
     -- IDX : 새글 번호 , SEQUENCE , AUTO_INCREMENT, IDENTITY(1,1)
     SELECT  NVL( MAX(IDX), 0) + 1 
      INTO   V_IDX
      FROM   BOARD;    
     
      IF IN_BNUM = 0  THEN   
         --  새글
        -- 해당 메뉴의 글번호 
        SELECT   NVL( MAX(BNUM), 0 ) + 1
         INTO    V_BNUM 
         FROM    BOARD
         WHERE   MENU_ID = IN_MENU_ID;
         
        V_LVL  :=   0;
        V_STEP :=   0;
    
        SELECT   NVL( MAX(NREF), 0 ) + 1
         INTO    V_NREF 
         FROM    BOARD
         WHERE   MENU_ID = IN_MENU_ID; 
        
      ELSE 
        --  답글
        V_BNUM  :=  IN_BNUM;
        V_LVL   :=  IN_LVL  + 1;
        V_STEP  :=  IN_STEP + 1;
        
        UPDATE   BOARD
         SET     STEP    = STEP + 1
         WHERE   MENU_ID = IN_MENU_ID
          AND    NREF    = IN_NREF
          AND    STEP    > IN_STEP;
        
        V_NREF  :=  IN_NREF;
      END IF;
     
  
    INSERT INTO BOARD (
        IDX,
        MENU_ID,
        TITLE,
        CONT,
        WRITER,
        REGDATE,
        READCOUNT,
        BNUM,
        LVL,
        STEP,
        NREF
    ) values (
        V_IDX,
        IN_MENU_ID,
        IN_TITLE,
        IN_CONT,
        IN_WRITER,
        SYSDATE,
        0,
        V_BNUM,
        V_LVL,
        V_STEP,
        V_NREF
    );
    
      
    
  END PROC_BOARD_INSERT;

END PKG_BOARD;