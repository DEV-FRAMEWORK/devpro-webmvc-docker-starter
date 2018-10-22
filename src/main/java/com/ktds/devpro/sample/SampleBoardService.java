package com.ktds.devpro.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.devpro.common.utils.FileUtil;

@Service
public class SampleBoardService {
	
	@Autowired
	private SampleBoardMapper sampleBoardMapper;
	
	@Autowired 
	private FileUtil fileUtils;
	
	/**
     * 게시판  리스트 조회
     * @param params
     * @return 게시판  리스트 조회 결과
     */
    @Transactional(readOnly=true)
    public List<Map<String,Object>> getSampleBoardList(Map<String, Object> params) {
        return sampleBoardMapper.selectSampleBoardList(params);
    } 
    
    
    @Transactional
    public Map<String, Object> getBoardDetail(Map<String, Object> params){
    	
		Map<String, Object> resultMap = new HashMap<String, Object>();

    	Map<String, Object> tempMap = sampleBoardMapper.selectBoardDetail(params);
		resultMap.put("map", tempMap);
		List<Map<String, Object>> list = sampleBoardMapper.selectFileList(params);
		resultMap.put("list", list);
    	
    	return resultMap;
    }
   
    
    /**
     * 게시글  등록
     * @param sampleBoardDomain, MultipartFile
     * @return 게시글  등록 결과
     * @throws Exception 
     */ 
    @Transactional
    public int registSampleBoard(SampleBoardDomain sampleBoardDomain,HttpServletRequest request)  {
        
    	//게시판 글 저장 
    	int cnt1 = sampleBoardMapper.insertSampleBoard(sampleBoardDomain);
    	
    	//파일 저장 및 파일 정보저장  
		List<Map<String, Object>> list;
		try {
			list = fileUtils.parseInsertFileInfo(sampleBoardDomain,  request,"");
			for(int i=0, size=list.size(); i<size; i++){
				sampleBoardMapper.insertSampleFileInfo(list.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //파일저장 메소드...

		
		
		if( cnt1 >1 ) {
    	
    	return cnt1;
		}
		
		return cnt1;
    }
    
    /**
     * 게시글 파일정보 조회 
     * @param map 
     * @return 게시글 파일정보 조회 
     * @throws Exception 
     */
    @Transactional
    public Map<String, Object> getFileInfo(Map<String, Object> params){
    	
    	return sampleBoardMapper.selectFileInfo(params);
    }
    
}
