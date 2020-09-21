# tomcat was 설정 가이드 #
---
* WAS 해당 경로(~\was\apache-tomcat-9.0.37\conf)내 server.xml 파일수정 
```
  <Host name="localhost" appBase="webapps" unpackWARs="true" autoDeploy="false">
        <Context docBase="web-mvc-starter-0.0.1-SNAPSHOT" path="/" reloadable="false"/>
        
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log" suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b" />
  </Host>
```