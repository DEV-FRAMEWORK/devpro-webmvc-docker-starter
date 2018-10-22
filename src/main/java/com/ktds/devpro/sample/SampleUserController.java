package com.ktds.devpro.sample;

import com.ktds.devpro.common.message.GenericMessage;
import com.ktds.devpro.common.message.LocaleAwareMessageService;
import com.ktds.devpro.common.message.RestMessage;
import com.ktds.devpro.common.mvc.PageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 *
 * 샘플 컨트롤러 클래스
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
@Controller
@RequestMapping(value = "/sample")
public class SampleUserController extends PageController{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SampleUserService sampleUserService;

    @Autowired
    private LocaleAwareMessageService messageSource;

    /**
     * 샘플 메인페이지
     * @return path
     */
    @RequestMapping(value = "user")
    public String user(){
        return "sample/index";
    }

    @RequestMapping(value = "settings")
    public String setFrameworks(){
        return "sample/settings";
    }

    /**
     * 사용자 정보 메인 페이지
     * @return path
     */
    @RequestMapping(value = "main")
    public String main(){
        return "sample/user";
    }

    /**
     * 사용자 정보 리스트 조회
     * @param message
     * @param params 검색 조건
     * @return 사용자 정보 리스트
     */
    @RequestMapping(value = "listSampleUser", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage list(GenericMessage message, @RequestParam Map<String, Object> params) {

        // 페이징 처리
        initPageConfig(params);

        // 조회 파라미터
        logger.info("SampleUserController.list() 검색 조건 = {}" , params);

        // 데이터 조회
        List<Map<String, Object>> sampleUserList = sampleUserService.getSampleUserList(params);

        // 조회 건수
        logger.info("SampleUserController.list() 조회 건수 = {}" , sampleUserList.size());

        message.setData(sampleUserList);
        return message;
    }

    /**
     * 사용자 정보 리스트 건수 조회
     * @param message
     * @param params 검색 조건
     * @return 사용자 정보 건수
     */
    @RequestMapping(value = "listSampleUserCount", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage listCount(GenericMessage message, @RequestParam Map<String, Object> params) {

        // 조회 파라미터
        logger.info("SampleUserController.list() 검색 조건 = {}" , params);

        // 데이터 조회
        int sampleUserListCount = sampleUserService.getSampleUserListCount(params);

        message.setData(sampleUserListCount);
        return message;
    }

    /**
     * User Id 에 해당하는 사용자 정보 조회
     * @param message
     * @param sampleUserDomain
     * @return User Id 에 해당하는 사용자 정보
     */
    @RequestMapping(value = "viewSampleUser", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage view(GenericMessage message, SampleUserDomain sampleUserDomain){

        SampleUserVo sampleUserVo = sampleUserService.getSampleUserById(sampleUserDomain);

        // 조회 정보
        logger.info("SampleUserController.list() 조회 데이터 = {}" , sampleUserVo);

        message.setData(sampleUserVo);
        return message;
    }

    /**
     * 사용자 정보 수정
     * @param message
     * @param sampleUserDomain 사용자 정보
     * @return 사용자 정보 수정 결과
     */
    @RequestMapping(value = "modifySampleUser", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage modify(GenericMessage message, SampleUserDomain sampleUserDomain){

        int count = sampleUserService.modifySampleUser(sampleUserDomain);
        if(count==0){
            message.setNG();
            message.setMessage(messageSource.getMessage("CM000040") + ":" + count);
        }else{
            message.setMessage(messageSource.getMessage("CM000050") + ":" + count);
        }
        return message;
    }

    /**
     * 사용자 정보 등록
     * @param message
     * @param sampleUserDomain 사용자 정보
     * @return 사용자 정보 등록 결과
     */
    @RequestMapping(value = "registSampleUser", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage regist(GenericMessage message, SampleUserDomain sampleUserDomain){

        int count = sampleUserService.registSampleUser(sampleUserDomain);
        if(count==0){
            message.setNG();
            message.setMessage(messageSource.getMessage("noCode", "[등록실패] 등록 건수 -> ") + ":" + count);
        }else{
            message.setMessage(messageSource.getMessage("noCode", "[등록성공] 등록 건수 -> ") + ":" + count);
        }
        return message;
    }

    /**
     * 사용자 정보 삭제
     * @param message
     * @param sampleUserDomain 삭제할 조건
     * @return 사용자 정보 삭제 결과
     */
    @RequestMapping(value = "deleteSampleUser", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage delete(GenericMessage message, SampleUserDomain sampleUserDomain){

        int count = sampleUserService.deleteSampleUser(sampleUserDomain);
        if(count==0){
            message.setNG();
            message.setMessage(messageSource.getMessage("CM000060", "삭제실패","코드 있음") + ":" + count);
        }else{
            message.setMessage(messageSource.getMessage("CM000060", "삭제완료","코드 없음") + ":" + count);
        }
        return message;
    }

    /**
     * Two Phase Commit 샘플 페이지
     * @return message
     */
    @RequestMapping(value = "twoPc")
    public String twoPc(){
        return "sample/2pc";
    }

    /**
     * 샘플 테이블 생성하기
     * @param message
     * @return
     */
    @RequestMapping(value = "initTable", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage createSampleTable(GenericMessage message){
        sampleUserService.createSampleUser();
        message.setMessage("테이블이 생성(초기화) 되었습니다.");
        return message;
    }

    /**
     * 샘플 데이터 생성하기
     * @param message
     * @return
     */
    @RequestMapping(value = "initData", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    @ResponseBody
    public GenericMessage createSampleData(GenericMessage message){
        sampleUserService.createSampleData();
        message.setMessage("샘플 데이터가 생성(초기화) 되었습니다.");
        return message;
    }

    /**
     * 샘플 게시판 리스트 조회
     * @return message
     */
    @RequestMapping(value = "hello")
    @ResponseBody
    public String list() {
        return "hello";
    }


    @RequestMapping(value = "test")
    public String tmp(){
        return "sample/test";
    }
}
