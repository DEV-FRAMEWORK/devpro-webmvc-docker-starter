### DevPro 2.0 - web-mvc-start 소개
---

web-mvc-start 프로젝트는 Spring boot 기반으로 Web Application 개발을 빠르게 구축하기 위한 서버단 개발 환경을 제공한다. 


### 1. 프레임워크 기본 구조 

* **Controller**: 사용자의 접속 URL 정보로 요청 내용을 분석하고 그에 대한 서비스를 호출하는 역할을 하며 Web 서비스와 RESTFul 서비스를 위한 두 가지 타입이 있다.
* **Service**: Controller에 의해 호출 되며 비즈니스 로직을 구현하고, 트랜젝션 처리의 단위가 되는 레이어이다.
* **Mapper**: Service에 의해 호출 되는 인터페이스로 MyBatis를 통해 쿼리 처리를 수행한다.

### 2. 프레임워크 설정 및 실행
* **설정 정보**<br> 
빌드: /build.gradle<br>
기본설정: src/main/resources/applicaiton.yml

### 3. 프로젝트 빌드
* **Gradle**: Groovy 표현식을 이용한 빌드 시스템으로 Ant와 Maven의 단점을 보완한 툴이다.
* 명령어를 통한 빌드&실행 방법
~~~vi
$ gradle bootRun
~~~
* Gradle Build Icon > Task>application>bootRun 클릭

### 4. 프레임워크 샘플
* **<a href="">사용자 등록, 목록, 수정, 삭제 기능</a>**
* **Two Phase Commit 샘플** 

### Locale 테스트 ###
* Korean : /message?code=OK&lang=ko
* English : /message?code=OK&lang=en

### 기본 환경 설정 ###
[여기](help/application.guide.md)를 클릭하세요

### Gradle 설정 ###
[여기](help/gradle.guide.md)를 클릭하세요

### 예외 처리 가이드 ###
[여기](help/exception.guide.md)를 클릭하세요

### 데이터 소스 설정 ###
[여기](help/datasource.guide.md)를 클릭하세요

### log4j2 설정 ###
[여기](help/log4j2.guide.md)를 클릭하세요

### xss 설정 ###
[여기](help/xss.guide.md)를 클릭하세요

### security 설정 ###
[여기](help/security.guide.md)를 클릭하세요

### 파일업로드 가이드 ###
[여기](help/fileupload.guide.md)를 클릭하세요

