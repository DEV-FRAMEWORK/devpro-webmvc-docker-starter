# XSS Filter 설정(보안설정)
---
Devpro 2.0  XSS 공격 방지 필터 적용 가이드.

lucy-xss-servlet-filter 라이브러리 사용하여 설정한다.

>공식 사이트 https://github.com/naver/lucy-xss-servlet-filter
좀더 자세한 내용은 해당 사이트 참조

### 라이브러리 (build.gradle)

~~~groovy
compile("com.navercorp.lucy:lucy-xss-servlet:2.0.0")
compile("com.navercorp.lucy:lucy-xss:1.6.3")
~~~

### config 설정 파일 경로 : /resources/lucy-xss-servlet-filter-rule.xml

* 기본설정  
  필터에 대한 설정은 xml 로 작성한다.
  크게 3부분으로 이루어짐.
  * defenders :  xss filter 정의 ( Preventer, Saxfilter, Xssfilter)
  * default   : filter 기본 설정값
  * url-rules :  url 룰 설정
 
~~~xml
lucy-xss-servlet-filter-rule.xml
 <defenders>
        <!-- XssPreventer 등록 -->
        <defender>
            <name>xssPreventerDefender</name>
            <class>com.navercorp.lucy.security.xss.servletfilter.defender.XssPreventerDefender</class>
        </defender>

        <!-- XssSaxFilter 등록 -->
        <defender>
            <name>xssSaxFilterDefender</name>
            <class>com.navercorp.lucy.security.xss.servletfilter.defender.XssSaxFilterDefender</class>
            <init-param>
                <param-value>lucy-xss-sax.xml</param-value>   <!-- lucy-xss-filter의 sax용 설정파일 -->
                <param-value>false</param-value>        <!-- 필터링된 코멘트를 남길지 여부, 성능 효율상 false 추천 -->
            </init-param>
        </defender>
~~~
~~~xml
    <!-- default defender 선언, 필터링 시 지정한 defender가 없으면 여기 정의된 default defender를 사용해 필터링 한다. -->
    <default>
        <defender>xssPreventerDefender</defender>
    </default>
    <!-- global 필터링 룰 선언 -->
    <global>
        <params>
            <param name="globalparam" usedefender="false" >
        </params>
    </global>
~~~

~~~xml
    <!-- url 별 필터링 룰 선언 -->
<url-rule-set>
    <!-- filtering 예외처리를 위한  ruleset 정의 필 -->
    <!-- url disable이 true이면 지정한 url 내의 모든 파라메터는 필터링 되지 않는다. -->
    <url-rule>
        <url disable="true">/devpro/sample/modifySampleUser</url>
    </url-rule>
    
    <!-- url 내의 파라메터 별로 각각의 defender 적용. -->
    <url-rule>
        <url>/devpro/sample/registBoard</url>
        <params>
            <param name="contents">
            <defender>xssSaxFilterDefender</defender>
            </param>
            <param name="title">
            <defender>xssPreventerDefender</defender>
            </param>
        </params>
    </url-rule>
</url-rule-set>
~~~

### java config 설정 파일 : LucyXssFilterConfiguration.java

~~~java
@Configuration
public class LucyXssFilterConfiguration {
	
    Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> xssEscapeServletFilter() {
	    FilterRegistrationBean<XssEscapeServletFilter> registrationBean = new FilterRegistrationBean<XssEscapeServletFilter>();
	    registrationBean.setFilter(new XssEscapeServletFilter());
	    registrationBean.setOrder(1);
	    registrationBean.addUrlPatterns("/*");
	    return registrationBean;
	}

}
~~~
> 라이브러리 추가후 xssEscapeServletFilter 객체를 Bean 으로 정의 하면 모든요청에 대해서 lucy  filter 설정이 부여된다. 기본 설정은 화이트리스트 방식으로 작동하며 url 별 상세 설정은 lucy-xss-servlet-filter-rule.xml 에서 정의하여야한다.