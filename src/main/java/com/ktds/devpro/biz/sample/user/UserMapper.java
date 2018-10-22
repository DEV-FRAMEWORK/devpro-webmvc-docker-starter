package com.ktds.devpro.biz.sample.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
*
* REST CRUD ?????? Mapper 
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

@Mapper
public interface UserMapper {
	public List<SampleUserVo> selectSampleUserList(SampleUserVo paramVo);
	public int countSampleUserList(SampleUserVo paramVo);
	public SampleUserVo selectSampleUser(SampleUserVo paramVo);
	public int insertSampleUser(SampleUserVo paramVo);
	public int updateSampleUser(SampleUserVo paramVo);
	public int deleteSampleUser(List<String> idList);
}