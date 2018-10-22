package com.ktds.devpro.biz.sample.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*
* REST CRUD ?????? Controller
* <p>
*
* <pre>
* ????????????(Modification Information)??
* ?????????   ?????????    ????????????
* ------------------------------------
* 2017. 9. 28.   ?????????     ????????????
* </pre>
*
* @author ????????? (shineljh@kt.com)
* @since 2017. 9. 28.
* @version 1.0.0
*
*/
@RestController
@RequestMapping("/api/sample/users")
public class SampleUserRestController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<SampleUserVo> getSampleUserList(SampleUserVo paramVo) {
		logger.debug("getSampleUserList- param({}", paramVo);
		
		return userService.getSampleUserList(paramVo);
	}
	
	@GetMapping("/size")
	public int getSampleUserListSize(SampleUserVo paramVo) {
		logger.debug("getSampleUserListSize- param({}", paramVo);
		return userService.getSampleUserListSize(paramVo);
	}
	
	@GetMapping("/{userId}")
	public SampleUserVo getSampleUser(@PathVariable String userId) {
		SampleUserVo paramVo = new SampleUserVo();
		paramVo.setUserId(userId);
		logger.debug("getSampleUser- param({})", paramVo);
		
		return userService.getSampleUser(paramVo);

	}
	
	@PostMapping
	public HttpStatus registerSampleUser(@RequestBody SampleUserVo paramVo) {
		userService.registerSampleUser(paramVo);
		
		return HttpStatus.OK;
	}
	
	@PutMapping("/{userId}")
	public HttpStatus modifySampleUser(@PathVariable String userId, @RequestBody SampleUserVo paramVo) {
		paramVo.setUserId(userId);
		logger.debug("modifySampleUser- param({})", paramVo);
		userService.modifySampleUser(paramVo);
		return HttpStatus.OK;	
	}
}
