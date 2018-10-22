# gradle.build 설정 #
* gradle 빌드 배포 도구.
* gradle.build 파일은 모듈의 빌드 방법이 정의된 빌드 스크립트


* 빌드시 생성되는 WAR파일의 파일명과 버전을 설정한다.
```java
war {
	baseName = 'web-mvc-starter'
	version = '0.0.1-SNAPSHOT'
}
```
* 현재 프로젝트가 쓰고 있는 자바 버전을 설정한다
```java
sourceCompatibility = 1.8
```
* 기본 레파지토리를 설정한다.
```java
repositories {
	mavenCentral()
}
```
* (참고) remote repository 의 경우 아래와 같이 설정
```java
repositories {
    maven {
        url "http://repo.mycompany.com/maven2"
    }
}
```

* 실행 환경별 설정 가능 (local, stage) : 아래의 예시와 같이 설정파일의 접미어로 설정한다.
* application-local.yml : 로컬 환경 설정 
* log4j2-local.xml : 로컬 환경에서의 로그 설정
```java
task local {
	bootRun {
		systemProperty("spring.profiles.active", "local")
	}
}
```
* Gradle은 Maven과 Ivy를 지원한다.
* 빌드 스크립트에서 직접 의존성을 지정한다.

```java
dependencies {

	// 기본 라이브러리
	compile('org.springframework.boot:spring-boot-starter-aop')
	compile('org.mybatis.spring.boot:mybatis-spring-boot-starter:1.3.0')
	compile('org.springframework.boot:spring-boot-starter-web')
	compileOnly('org.projectlombok:lombok') //컴파일시에 Lombok관련 annotation (@Data, @ToString)에 따라 자동으로 get, set메서드가 생성된다
	
	// 서블릿 JSP 라이브러리
	providedRuntime('org.springframework.boot:spring-boot-starter-tomcat')
	compile("javax.servlet:jstl")
	compile("org.apache.tomcat.embed:tomcat-embed-jasper")

	// 스프링 부트 개발 툴(클래스 자동 리로더 등)
	runtime('org.springframework.boot:spring-boot-devtools')

	// Database 초기화 및 JPA 라이브러리
	compile("org.springframework.boot:spring-boot-starter-data-jpa")

	// H2 Database 라이브러리
	compile('com.h2database:h2')

	// HSQL Database 라이브러리
	compile('org.hsqldb:hsqldb:2.3.4')

	// Apache Common 라이브러리
	compile("org.apache.commons:commons-collections4:4.0")

	// Log4j2 라이브러리
	compile('org.springframework.boot:spring-boot-starter-log4j2')
	compile("org.apache.logging.log4j:log4j-core:2.7")
	compile("org.apache.logging.log4j:log4j-slf4j-impl:2.7")
	compile("com.lmax:disruptor:3.3.6")

	// 테스트 프레임워크(Spock) 적용 스크립트(Groovy) 라이브러리
	compile 'org.codehaus.groovy:groovy-all:2.4.10'

	// 테스트 프레임워크(Spock) 라이브러리
	testCompile 'org.springframework.boot:spring-boot-starter-test'
	testCompile 'org.spockframework:spock-core:1.1-groovy-2.4-rc-3'
	testCompile 'org.spockframework:spock-spring:1.1-groovy-2.4-rc-3'
	testRuntime 'cglib:cglib-nodep:3.2.4'

	// Log4j2 충돌 제외 라이브러리
        configurations {
            all*.exclude group: 'commons-logging'
            all*.exclude group: 'slf4j'
            all*.exclude module: 'logback-classic'
        }

	// Log4j2 충돌 제외 라이브러리
	compile('org.springframework.boot:spring-boot-starter'){
		exclude module: 'org.springframework.boot:spring-boot-starter-logging'
		exclude module: 'logback-classic'
	}

	// Log4j2 충돌 제외 라이브러리
	compile('org.springframework.boot:spring-boot-starter-web'){
		exclude module: 'org.springframework.boot:spring-boot-starter-logging'
		exclude module: 'logback-classic'
	}
}
```





