package com.ktds.devpro.sample;

import com.ktds.devpro.other.OtherUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;

/**
 *
 * 샘플 서비스 클래스
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
@Service
public class SampleUserService {

    @Autowired
    private SampleUserMapper sampleUserMapper;

    @Autowired
    private OtherUserMapper otherUserMapper;

    /**
     * 사용자 리스트 조회
     * @param params
     * @return 사용자 리스트 조회 결과
     */
    @Transactional(readOnly=true)
    public List<Map<String,Object>> getSampleUserList(Map<String, Object> params) {
        return sampleUserMapper.selectSampleUserList(params);
    }

    /**
     * 사용자 리스트 건수 조회
     * @param params
     * @return 사용자 리스트 건수
     */
    @Transactional(readOnly=true)
    public int getSampleUserListCount(Map<String, Object> params) {
        return sampleUserMapper.selectSampleUserListCount(params);
    }

    /**
     * 사용자 아이디 조회
     * @param sampleUserDomain 사용자 아이디
     * @return 사용자 아이디 조회 결과
     */
    @Transactional(readOnly=true)
    public SampleUserVo getSampleUserById(SampleUserDomain sampleUserDomain) {
        return sampleUserMapper.selectSampleUserById(sampleUserDomain);
    }

    /**
     * 사용자 정보 수정
     * @param sampleUserDomain
     * @return 사용자 정보 수정 결과
     */
    @Transactional
    public int modifySampleUser(SampleUserDomain sampleUserDomain) {
        return sampleUserMapper.updateSampleUser(sampleUserDomain);
    }

    /**
     * 사용자 등록
     * @param sampleUserDomain
     * @return 사용자 등록 결과
     */
    @Transactional
    public int registSampleUser(SampleUserDomain sampleUserDomain) {
        return sampleUserMapper.insertSampleUser(sampleUserDomain);
    }

    /**
     * 사용자 삭제
     * @param sampleUserDomain
     * @return 사용자 삭제 결과 조회
     */
    @Transactional
    public int deleteSampleUser(SampleUserDomain sampleUserDomain){
        return sampleUserMapper.deleteSampleUser(sampleUserDomain);
    }

    /**
     * 샘플 테이블 생성
     */
    @Transactional
    public void createSampleUser(){
        sampleUserMapper.createSampleTable();
    }

    /**
     * 샘플 데이터 생성
     */
    @Transactional
    public void createSampleData(){
        sampleUserMapper.createSampleData();
    }

    /*
    여기서 부터 2pc 예제
     */

    /**
     * 사용자 정보 수정 (2PC)
     * @param sampleUserDomain
     * @return 사용자 정보 수정 결과
     */
    @Transactional
    public int modifySampleUser2pc(SampleUserDomain sampleUserDomain) {
        return otherUserMapper.updateSampleUser(sampleUserDomain);
    }

    /**
     * 사용자 등록 (2PC)
     * @param sampleUserDomain
     * @return 사용자 등록 결과
     */
    @Transactional
    public int registSampleUser2pc(SampleUserDomain sampleUserDomain) {
        return otherUserMapper.insertSampleUser(sampleUserDomain);
    }

    /**
     * 사용자 등록
     * @param sampleUserDomain
     * @param status
     * @return 사용자 등록 결과
     */
    @Transactional(rollbackFor = {Exception.class})
    public int registSampleUser(SampleUserDomain sampleUserDomain, boolean status) {

        int count = this.registSampleUser2pc(sampleUserDomain);
        count += this.registSampleUser(sampleUserDomain);

        if(!status){
            throw new RuntimeException("CM000030");
        }
        return count;
    }

    /**
     * 사용자 정보 수정
     * @param sampleUserDomain
     * @param status
     * @return 사용자 정보 수정 결과
     */
    @Transactional(rollbackFor = {Exception.class})
    public int modifySampleUser(SampleUserDomain sampleUserDomain, boolean status) {

        int count = this.modifySampleUser(sampleUserDomain);
        count += this.modifySampleUser2pc(sampleUserDomain);

        if(!status){
            throw new RuntimeException("CM000030");
        }

        return count;
    }

    /**
     * 사용자 정보 조회 (2PC)
     * @param sampleUserDomain
     * @return 사용자 정보 조회 결과
     */
    @Transactional(readOnly=true)
    public SampleUserVo getSampleUserById2pc(SampleUserDomain sampleUserDomain) {
        return otherUserMapper.selectSampleUserById(sampleUserDomain);
    }
}
