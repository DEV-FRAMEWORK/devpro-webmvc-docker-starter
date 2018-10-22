package com.ktds.devpro.sample;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 * 샘플 맵퍼 클래스
 * <p>
 *
 * <pre>
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2017. 3. 16.   kt ds     최초작성
 * </pre>
 *
 * @author kt ds A.CoE(yu.chae@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
@Mapper()
public interface SampleUserMapper {

    /**
     *  사용자 정보 리스트 조회
     * @param params
     * @return 회원 정보 리스트
     */
    List<Map<String, Object>> selectSampleUserList(Map<String, Object> params);

    /**
     *  사용자 정보 리스트 건수 조회
     * @param params
     * @return 회원 정보 건수
     */
    int selectSampleUserListCount(Map<String, Object> params);

    /**
     * 사용자 아이디로 정보 조회
     * @param sampleUserDomain
     * @return 사용자 정보
     */
    SampleUserVo selectSampleUserById(SampleUserDomain sampleUserDomain);

    /**
     *  사용자 정보 업데이트
     * @param sampleUserDomain
     * @return 업데이트 row 카운트
     */
    int updateSampleUser(SampleUserDomain sampleUserDomain);

    /**
     *  사용자 정보 등록
     * @param sampleUserDomain
     * @return insert row 카운트
     */
    int insertSampleUser(SampleUserDomain sampleUserDomain);

    /**
     *  사용자 정보 삭제
     * @param sampleUserDomain
     * @return delete row 카운트
     */
    int deleteSampleUser(SampleUserDomain sampleUserDomain);

    /**
     *  샘플 테이블 생성
     */
    void createSampleTable();

    /**
     *  샘플 데이터 생성
     */
    void createSampleData();

}
