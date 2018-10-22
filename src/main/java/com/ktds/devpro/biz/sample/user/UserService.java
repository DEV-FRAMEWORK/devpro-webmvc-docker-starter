package com.ktds.devpro.biz.sample.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public List<SampleUserVo> getSampleUserList(SampleUserVo paramVo) {
		return userMapper.selectSampleUserList(paramVo);
	}

	public int getSampleUserListSize(SampleUserVo paramVo) {
		return userMapper.countSampleUserList(paramVo);
	}

	public SampleUserVo getSampleUser(SampleUserVo paramVo) {
		return userMapper.selectSampleUser(paramVo);
	}

	public int registerSampleUser(SampleUserVo paramVo) {
//		org.apache.commons.lang3.StringUtils.isNotEmpty("11");
		return userMapper.insertSampleUser(paramVo);	
	}

	public int modifySampleUser(SampleUserVo paramVo) {
		return userMapper.updateSampleUser(paramVo);	
	}
}
