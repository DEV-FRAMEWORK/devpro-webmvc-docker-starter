package com.ktds.devpro.sample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper()
public interface SampleBoardMapper {
	
	
	/**
     * 게시글 리스트 조회
     * @param params
     * @return 게시글 리스트 조회 결과
     */
     List<Map<String,Object>> selectSampleBoardList(Map<String, Object> params) ;
     
     /**
      *  게시 정보 등록
      * @param sampleBoardDomain
      * @return insert row 카운트
      */
     int insertSampleBoard(SampleBoardDomain sampleBoardDomain);
     
     /**
      *  게시 정보 조회 
      * @param params
      * @return 게시글 상세 조회 결과
      */
     Map<String, Object>selectBoardDetail(Map<String, Object> params);
     
     /**
      * 파일 정보 저장 
      * @param sampleFile
      * @return insert row 카운트
      */
     int insertSampleFileInfo(Map<String, Object> params);
     
     /**
      * 파일 목록 조희  
      * @param map
      * @return 파일 목록  
      */
	  List<Map<String, Object>> selectFileList(Map<String, Object> params);
	  
	  /**
      * 파일 정보 조희  
      * @param map
      * @return 파일 정보 
      */
	  Map<String, Object> selectFileInfo(Map<String, Object> params);

}
