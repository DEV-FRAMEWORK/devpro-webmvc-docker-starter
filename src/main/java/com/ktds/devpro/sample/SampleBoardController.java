package com.ktds.devpro.sample;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.devpro.common.utils.FileUtil;



@Controller
@RequestMapping(value = "/sample")
public class SampleBoardController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

	

	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private SampleBoardService sampleBoardService;
	
	
	@RequestMapping("/login")
	public String login() {
		return "sample/login";
	}
	
	@RequestMapping("/listBoard")
	public ModelAndView listBoard(@RequestParam Map<String, Object> params) {
    	ModelAndView mv = new ModelAndView("sample/boardList");
    	
    	List<Map<String,Object>> list = sampleBoardService.getSampleBoardList(params);
    	mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value="/writeBoard")
	public ModelAndView writeBoard() {
		ModelAndView mv = new ModelAndView("sample/boardWrite");
		
		return mv;
	}
	
	
	@RequestMapping(value="/detailBoard")
	public ModelAndView detailBoard(@RequestParam Map<String, Object> params , HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("sample/boardDetail");
		Map<String,Object> map = sampleBoardService.getBoardDetail(params);
		//mv.addObject("map", map);
		//?????? ?????? ?????? ??????. 
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		
		return mv;
	}
	
	@PostMapping(value="/registBoard")
	public String registBoard( @ModelAttribute @Valid SampleBoardDomain sampleBoardDomain, MultipartHttpServletRequest request) {
		
		logger.debug(request.getRequestURI());
		
		
		sampleBoardService.registSampleBoard(sampleBoardDomain, request);
		
		
		return "redirect:listBoard";
	}
    	
	@RequestMapping(value="/downloadFile")
	public void downloadFile(@RequestParam Map<String, Object> params , HttpServletResponse res) throws Exception {
		Map<String , Object> map = sampleBoardService.getFileInfo(params);
		
		String storedFileName = (String)map.get("storedFileName");
		String originalFileName = (String)map.get("originalFileName");
		
		Boolean isAuthenticated = true; //???????????? ?????? ?????? ????????????....
		
		if(isAuthenticated) {
			
			fileUtil.downloadFile(storedFileName,originalFileName,res);
			

		}else {
			//todo...
			
		}
		
		
	}	
	
}
