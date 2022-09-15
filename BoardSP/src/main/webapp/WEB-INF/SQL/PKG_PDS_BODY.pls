create or replace NONEDITIONABLE PACKAGE BODY PKG_PDS AS

  PROCEDURE   PROC_PDS_PAGINGLIST(
      IN_MENU_ID         IN      VARCHAR2,
      IN_NOWPAGE         IN      NUMBER,
      IN_PAGECOUNT       IN      NUMBER,
      OUT_TOTALCOUNT     OUT     NUMBER,
      O_CUR              OUT     SYS_REFCURSOR 
  ) AS
      V_STARTNUM     NUMBER(10);
      V_ENDNUM       NUMBER(10);
  BEGIN

    -- 페이징 처리를 위한 현재 메뉴의 전체자료수
    SELECT   NVL( COUNT(IDX), 0 )
     INTO    OUT_TOTALCOUNT
     FROM    BOARD
     WHERE   MENU_ID   =  IN_MENU_ID;

   -- 가져올 자료(레코드)의 시작번호, 긑번호를 계산
    V_STARTNUM  :=  (IN_NOWPAGE - 1 ) * IN_PAGECOUNT + 1; 
    V_ENDNUM   :=   IN_NOWPAGE *  IN_PAGECOUNT;

    OPEN O_CUR FOR
     SELECT  *  FROM (
       SELECT
           ROW_NUMBER() OVER (  ORDER BY NREF DESC, STEP ASC) AS RN,
           IDX, MENU_ID,
           TITLE,
           WRITER, REGDATE, READCOUNT,
           ( 
              SELECT   COUNT(FILE_NUM) FROM FILES F
               WHERE   B.IDX = F.IDX
           )
           AS FILESCOUNT,
           BNUM, LVL, STEP, NREF, DELNUM        
          FROM   BOARD B  
      ) T
      WHERE  T.RN BETWEEN V_STARTNUM AND V_ENDNUM;


  END PROC_PDS_PAGINGLIST;


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
  ) AS
      V_BNUM      NUMBER(5, 0);
      V_LVL       NUMBER(5, 0);
      V_STEP      NUMBER(5, 0);
      V_NREF      NUMBER(5, 0);

      V_MAXIDX    NUMBER(5, 0);

  BEGIN

   -- INSERT  BOARD 저장 : 새글 / 답글
    IF   IN_BNUM   =  0  THEN         
        SELECT  NVL( MAX(BNUM), 0 ) + 1
         INTO   V_BNUM
         FROM   BOARD
         WHERE  MENU_ID = IN_MENU_ID;

        V_LVL   :=  0;
        V_STEP  :=  0;

        SELECT  NVL( MAX(NREF), 0 ) + 1
         INTO   V_NREF
         FROM   BOARD
         WHERE  MENU_ID = IN_MENU_ID;
    ELSE  -- 답글 부분
         V_BNUM  :=  IN_BNUM;
         V_LVL   :=  IN_LVL + 1;
         V_STEP  :=  IN_STEP + 1;
         V_NREF  :=  IN_NREF;

         -- 기존글들의 STEP 을 증가
         UPDATE    BOARD
          SET      STEP  =  STEP + 1
          WHERE    NREF  =  IN_NREF
          AND      STEP  >  IN_STEP
          AND      MENU_ID = IN_MENU_ID;
    END IF;

    INSERT  INTO  BOARD
     (
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
        NREF,
        DELNUM
     )
     VALUES 
     (
        (
           SELECT  NVL( MAX(IDX) , 0) + 1
            FROM   BOARD
        ),
        IN_MENU_ID,
        IN_TITLE,
        IN_CONT,
        IN_WRITER,
        SYSDATE,
        0,
        V_BNUM,
        V_LVL,
        V_STEP,
        V_NREF,
        0
   );

    --------------------------------------------
   -- 방금 새로 추가된 글번호(BOARD) 
   SELECT  MAX(IDX)
    INTO   V_MAXIDX
    FROM   BOARD;

  ----------------------------------------------
  --  INSERT FILES 정보 저장
  --   THENMORACLE 배열은 1 부터 시작한다
  IF  IN_FILENAME(1) IS NOT NULL THEN
     FOR  I   IN   IN_FILENAME.FIRST .. IN_FILENAME.LAST
    LOOP
         INSERT  INTO FILES 
           VALUES ( 
             ( SELECT NVL(MAX(FILE_NUM), 0) + 1  FROM FILES ),
             V_MAXIDX,        -- 새로 추가된 글번호
             IN_FILENAME( I ),
             IN_FILEEXT( I ),
             IN_SFILENAME( I )
        );
    END LOOP; 

    COMMIT;
  END IF;

  END PROC_PDS_INSERT;

  -- VIEW
  PROCEDURE   PROC_PDS_VIEW(
      IN_IDX          IN       NUMBER,
      O_CUR           OUT      SYS_REFCURSOR  
  ) AS
  BEGIN
    
      -- 조히수 증가
      UPDATE    BOARD
        SET     READCOUNT = READCOUNT + 1
        WHERE   IDX  =   IN_IDX; 
     COMMIT;   
    
      OPEN  O_CUR  FOR
        SELECT  IDX,
                MENU_ID,
                TITLE,
                CONT,
                WRITER,
                REGDATE,
                READCOUNT,
                BNUM,
                LVL,
                STEP,
                NREF,
                DELNUM
         FROM
                BOARD
         WHERE  
                IDX  = IN_IDX;
    
    
  END PROC_PDS_VIEW;

  --  FILELIST
  PROCEDURE   PROC_PDS_FILELIST(
      IN_IDX          IN       NUMBER,
      O_CUR           OUT      SYS_REFCURSOR  
  ) AS
  BEGIN
    
    OPEN O_CUR  FOR
      SELECT  
            FILE_NUM,
            IDX,
            FILENAME,
            FILEEXT,
            SFILENAME
       FROM  
            FILES
       WHERE   
            IDX  = IN_IDX;
    
  END PROC_PDS_FILELIST;

END PKG_PDS;









