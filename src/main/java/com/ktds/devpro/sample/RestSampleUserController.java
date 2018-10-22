package com.ktds.devpro.sample;

import java.util.HashMap;
import java.util.Map;

import com.ktds.devpro.common.message.GenericMessage;
import com.ktds.devpro.common.message.LocaleAwareMessageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@RestController
@RequestMapping("/rest/sample")
public class RestSampleUserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SampleUserService sampleUserService;

    @Autowired
    private LocaleAwareMessageService messageService;

    /**
     * 메시지 포맷 테스트
     * @param message
     * @param code
     */
    @RequestMapping("/message")
    public void message(GenericMessage message, String code) {

        // 예외 테스트
        if("1".equals(code)){
            throw new RuntimeException("CM000010");
        }

        Map<String, String> map = new HashMap<>();
        map.put("제목", "메밀꽃 필 무렵");
        map.put("저자", "채영욱");
        message.setMessage(code);
 //       message.enableCustomeMessage();
        message.setData(map);
    }

    @RequestMapping(method = RequestMethod.GET)
    public void queryUser(GenericMessage message) {
        //service.queryUser(message);

    }


    /**
     * 사용자 정보 등록
     * @param message
     * @param sampleUserDomain 사용자 정보
     * @return 사용자 정보 등록 결과
     */
    @RequestMapping(value = "registSampleUser2pc", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    public GenericMessage regist2pc(GenericMessage message, SampleUserDomain sampleUserDomain,
                                    @RequestParam("userId2") String userId,
                                    @RequestParam(value = "userName2", required = false) String userName,
                                    @RequestParam(value = "password2", required = false) String password
    ){

        boolean status = true;
        if(!sampleUserDomain.getUserId().equals(userId)){
            status = false;
        }
        if(!sampleUserDomain.getUserName().equals(userName)){
            status = false;
        }
        if(!sampleUserDomain.getPassword().equals(password)){
            status = false;
        }

        // 파라미터
        logger.info("SampleUserController.regist2pc() {}" , sampleUserDomain);
        logger.info("SampleUserController.regist2pc() {}, {}, {}" , userId, userName, password);

        sampleUserService.registSampleUser(sampleUserDomain, status);
        message.setMessage("CM000010");

        return message;
    }

    /**
     * 사용자 정보 수정
     * @param message
     * @param sampleUserDomain 사용자 정보
     * @return 사용자 정보 수정 결과
     */
    @RequestMapping(value = "modifySampleUser2pc", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    public GenericMessage modify2pc(GenericMessage message, SampleUserDomain sampleUserDomain,
                                    @RequestParam(value = "userId2") String userId,
                                    @RequestParam(value = "userName2", required = false) String userName,
                                    @RequestParam(value = "password2", required = false) String password
    ){

        boolean status = true;
        if(!sampleUserDomain.getUserId().equals(userId)){
            status = false;
        }
        if(!sampleUserDomain.getUserName().equals(userName)){
            status = false;
        }
        if(!sampleUserDomain.getPassword().equals(password)){
            status = false;
        }

        // 파라미터
        logger.info("SampleUserController.modify2pc() {}" , sampleUserDomain);
        logger.info("SampleUserController.modify2pc() {}, {}, {}" , userId, userName, password);

        sampleUserService.modifySampleUser(sampleUserDomain, status);
        message.setMessage("CM000010");

        return message;
    }

    /**
     * User Id 에 해당하는 사용자 정보 조회
     * @param message
     * @param sampleUserDomain 사용자 정보 검색 조건
     * @return 사용자 정보 조회 결과
     */
    @RequestMapping(value = "viewSampleUser2pc", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
    public GenericMessage view2pc(GenericMessage message, SampleUserDomain sampleUserDomain){

        SampleUserVo sampleUserVo = sampleUserService.getSampleUserById2pc(sampleUserDomain);

        // 조회 정보
        logger.info("SampleUserController.view2pc() 조회 데이터 = {}" , sampleUserVo);

        message.setData(sampleUserVo);
        return message;
    }


}
