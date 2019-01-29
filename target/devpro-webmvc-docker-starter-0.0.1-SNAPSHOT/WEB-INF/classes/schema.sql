DROP TABLE IF EXISTS SAMPLE_GROUP_USER;
DROP TABLE IF EXISTS SAMPLE_GROUP;
DROP TABLE IF EXISTS SAMPLE_USER;

DROP TABLE IF EXISTS SAMPLE_BOARD;
DROP TABLE IF EXISTS SAMPLE_FILE;

DROP SEQUENCE IF EXISTS SEQ_SAMPLE_FILE_IDX;
DROP SEQUENCE IF EXISTS SEQ_SAMPLE_BOARD_IDX;


		
		CREATE TABLE SAMPLE_USER(
			USER_ID VARCHAR(50) PRIMARY KEY
			, PASSWORD VARCHAR(200)
			, USER_NAME VARCHAR(200)
			, CREATE_DATE TIMESTAMP
		);

		CREATE TABLE SAMPLE_GROUP(
			GROUP_ID VARCHAR(50) PRIMARY KEY
			, GROUP_NAME VARCHAR(50)
			, CREATE_DATE TIMESTAMP
		);

		CREATE TABLE SAMPLE_GROUP_USER(
			GROUP_ID VARCHAR(50)
			, USER_ID VARCHAR(50)
			, CREATE_DATE TIMESTAMP
			,FOREIGN KEY (GROUP_ID) REFERENCES SAMPLE_GROUP(GROUP_ID)
			,FOREIGN KEY (USER_ID) REFERENCES SAMPLE_USER(USER_ID)
		);



CREATE TABLE SAMPLE_BOARD
(
    IDX NUMBER PRIMARY KEY,
    PARENT_IDX NUMBER,
    TITLE VARCHAR2(100) NOT NULL,
    CONTENTS VARCHAR2(4000) NOT NULL,
    HIT_CNT NUMBER NOT NULL,
    DEL_GB VARCHAR2(1) DEFAULT 'N' NOT NULL,
    CREA_DTM DATE DEFAULT SYSDATE NOT NULL,
    CREA_ID VARCHAR2(30) NOT NULL
);
  
COMMENT ON TABLE SAMPLE_BOARD IS '게시판';
COMMENT ON COLUMN SAMPLE_BOARD.IDX IS '인덱스';
COMMENT ON COLUMN SAMPLE_BOARD.PARENT_IDX IS '부모글 인덱스';
COMMENT ON COLUMN SAMPLE_BOARD.TITLE IS '제목';
COMMENT ON COLUMN SAMPLE_BOARD.CONTENTS IS '내용';
COMMENT ON COLUMN SAMPLE_BOARD.HIT_CNT IS '조회수';
COMMENT ON COLUMN SAMPLE_BOARD.DEL_GB IS '삭제구분';
COMMENT ON COLUMN SAMPLE_BOARD.CREA_DTM IS '생성일자';
COMMENT ON COLUMN SAMPLE_BOARD.CREA_ID IS '생성자 ID';

--------------------------------------------------------
--  DDL for Table TB_FILE
--------------------------------------------------------
CREATE TABLE SAMPLE_FILE
(
  IDX   NUMBER,
  BOARD_IDX NUMBER NOT NULL,
  ORIGINAL_FILE_NAME VARCHAR2(260 BYTE) NOT NULL,
  STORED_FILE_NAME VARCHAR2(36 BYTE) NOT NULL,
  FILE_SIZE NUMBER,
  CREA_DTM  DATE DEFAULT SYSDATE NOT NULL,
  CREA_ID   VARCHAR2(30 BYTE) NOT NULL,
  DEL_GB    VARCHAR2(1 BYTE) DEFAULT 'N' NOT NULL,
  PRIMARY KEY (IDX)
);

CREATE SEQUENCE SEQ_SAMPLE_FILE_IDX
  START WITH 1
  INCREMENT BY 1
  NOMAXVALUE
  NOCACHE;

CREATE SEQUENCE SEQ_SAMPLE_BOARD_IDX
  START WITH 1
  INCREMENT BY 1
  NOMAXVALUE
  NOCACHE;
