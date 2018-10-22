package com.ktds.devpro.sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

/**
 *
 * Controller / Service 테스트 예제
 * <p>
 *
 * <pre>
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2017. 3. 16.   kt ds     최초작성
 * </pre>
 *
 * @author kt ds A.CoE(kuhong@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("local")
class SampleSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    SampleUserService service

//    def 'restTemplate를 사용한 RESTFul 호출'() {
//        when:
//        def hello = restTemplate.getForEntity('/sample/hello', String.class)
//        def list = restTemplate.getForEntity('/sample/listSampleUser', ArrayList.class)
//
//        then:
//        hello.statusCode == HttpStatus.OK
//        hello.body == 'hello'
//        list.statusCode == HttpStatus.OK
//        list.body.size() == 5
//    }
//
//    def '서비스 직접 호출'() {
//        given:
//        Map<String, Integer> params = new HashMap<>()
//        params.put("startRow", 1)
//        params.put("endRow", 10)
//
//        when:
//        def userList = service.getSampleUserList(params)
//
//        then:
//        userList.size() == 10
//    }


}
