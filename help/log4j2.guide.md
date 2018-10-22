# log4j2 설정 #

### 설정 파일명
아래는 샘플 소스 log4j2-local.xml의 설정값에 대한 설명이다.<br/>
실행 환경에 따라 build.gradle에서 (개발) log4j2-local.xml, (운영) log4j2-stage.xml 등으로  설정할 수 있다. 

* 로그패턴을 정의한다.
%m : 로그 이벤트에서 설정된 메시지를 출력/
%n : 플랫폼에서 정의한 개행 문자를 출력한다
```java
        <Console name="STDOUT" target="SYSTEM_OUT">
            <PatternLayout pattern="%m%n"/>
        </Console>
```

| Name     | Description | 
| :-------: | :---- |
| fileName | 로그 파일명< | 
| immediateFlush | true로 설정하게 되면 로그를 디스크에 flush하게 저장한다. |  
| append  | true일때 로그 파일의 마지막에 추가된다 (false, 기존 내용이 삭제되고 추가)|  
| filePattern  |  로그 파일명의 형식 지정 |  
| PatternLayout  | 로그 출력 형식을 지정|   
| TimeBasedTriggeringPolicy  | 날짜/시간 패턴이 더 이상 활성 파일에 적용하면 롤오버가 발생하지 않습니다|  
| SizeBasedTriggeringPolicy  | 파일이 지정된 크기에 도달하면 롤오버가 발생합니다.  | 
| DefaultRolloverStrategy  | SizeBased기반 롤오버가 트리거 될때 최대 max값 만큼 파일이 생성되지 않는다 | 


```java
        <RollingRandomAccessFile name="RollingRandomAccessFile"
                                 fileName="logs/devpro.log" -->(1)
                                 immediateFlush="true"      -->(2)
                                 append="true"              -->(3)
                                 filePattern="logs/${date:yyyy-MM}/app-%d{MM-dd-yyyy}-%i.log.gz">       -->(4)
            <PatternLayout> 
                <Pattern>[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%thread] %-5level %logger{36}| %msg%n</Pattern> -->(5)
            </PatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy />               -->(6)
                <SizeBasedTriggeringPolicy size="250 MB"/>  -->(7)
            </Policies>
            <DefaultRolloverStrategy max="20"/>             -->(8) 
        </RollingRandomAccessFile>
```
### 3.Log 관련 설정
* AsyncLogger : AsyncAppender를 통해 이벤트를 비동기적으로 기록할수 있다.
* Logger name : 스프링 프레임워크 자체 로그의 레벨을 설정한다.
* Root level  

| Log Level     | Description | 
| :-------: | :---- |
| fetal | 심각한 에러, 어플리케이션이 동작 불가능한 상황 | 
| error | 요청 처리 중의 에러   |  
| warn  | 경고성 메시지  |  
| info  |  정보성 메시지   |  
| debug  | 개발 디버그 용 메시지   |   
| trace  | 상세 상태 메시지   |  
```java
    <Loggers>

        <AsyncLogger name="com.ktds" level="info" includeLocation="false">
            <AppenderRef ref="RollingRandomAccessFile"/>
        </AsyncLogger>


        <Logger name="org.springframework" level="info" >
            <AppenderRef ref="STDOUT"/>
        </Logger>

        <Root level="debug" includeLocation="true">
            <AppenderRef ref="STDOUT" />
            <AppenderRef ref="RollingRandomAccessFile"/>
        </Root>
    </Loggers>
```

### 4.Profile 전략


```java
    <springProfile name="console-logging">
    <appender name="CONSOLE" class="..."></appender>
    </springProfile>

    <springProfile name="file-logging">
        <appender name="FILE" class="..."></appender>
    </springProfile>

    <springProfile name="remote-logging">
    <appender name="REMOTE_LOG_SERVER" class="..."></appender>
    </springProfile>

    <root>
    <springProfile name="console-logging">
        <appender-ref ref="CONSOLE"/>
    </springProfile>
    
    <springProfile name="file-logging">
        <appender-ref ref="FILE"/>
    </springProfile>
    
    <springProfile name="remote-logging">
        <appender-ref ref="REMOTE_LOG_SERVER"/>
    </springProfile>
    </root>
```