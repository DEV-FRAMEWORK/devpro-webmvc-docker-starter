# application 설정 #

### WAS 설정
* 아래와 같이 서버 포트와 Context정보를 설정한다
* gradle bootRun 실행 후 http://localhost:8888/devpro에 접속하여 실행 가능하다
```java
server:
  port: 8888
  contextPath: /devpro
```

### Spring 환경설정 
* 샘플 소스는 경량 로컬,테스트용 데이터베이스 h2를 사용하고 있다.
* (데이터베이스 조회 콘솔) http://localhost:8888/devpro/console 


```java
  datasource:

    h2:
      platform: h2
      driverClassName: org.h2.Driver
      url: jdbc:h2:~/test;AUTO_SERVER=TRUE
      username: sa
      password:

    hsql:
      platform: hsql
      driverClassName: org.hsqldb.jdbc.JDBCDriver
      url: jdbc:hsqldb:file:~/hsqltest;encoding=UTF-8
      username: sa
      password:
```
### Logging 설정
* log4j2 설정파일을 지정한다. 
* 실행환경에 따라 (개발) log4j2-local.xml, (운영) log4j2-stage.xml등으로 설정한다.
```java
logging:
  config: classpath:log4j2-local.xml
  level: debug
```

### 웹 관련 설정
* 한 페이지에 표시할 records갯수를 지정한다. 
```java
web:
  config:
    default-page-size: 10
```