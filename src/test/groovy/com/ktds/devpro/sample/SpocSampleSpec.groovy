package com.ktds.devpro.sample

import spock.lang.Specification

/**
 *
 * Spoc Test 예제
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
class SpocSampleSpec extends Specification {

    def '조건값 테스트'() {
        setup:
        def stack = new Stack()
        def elem = "push me"

        when:
        stack.push(elem)

        then:
        !stack.empty
        stack.size() == 1
        stack.peek() == elem
    }

    def '최대값 구하기'() {
        when:
        def x = Math.max(1,2)

        then:
        x == 2

    }

    def '예외 발생 테스트1'() {
        setup:
        def stack = new Stack()
        assert stack.empty

        when:
        stack.pop()

        then:
        thrown(EmptyStackException)
        stack.empty
    }

    def '예외 발생 테스트2'() {
        setup:
        def map = new HashMap()

        when:
        map.put(null, "elem")

        then:
        notThrown(NullPointerException)
    }

    def '더하기 계산하기'() {
        expect:
        x+y == sum

        where:
        x | y | sum
        2 | 3 | 5
        10 | 1 | 11
        44 | 11 | 55
        23 | 2 | 25

    }

    def '데이터 파이프'() {
        expect:
        x+y == sum

        where:
        x << [2,10,44,23]
        y << [3,1,11,2]
        sum << [5,11,55,25]

    }

    def 'Interaction Based Mock test'() {
        given:
        List list

        when:
        list = Mock()

        then:
        0 * list.size()
    }
}
