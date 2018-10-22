### Data Source 설정
---

 DevPro 2.0은 기본적으로 application.yml에 데이터소스 설정을 기본으로 한다.
 하나의 데이터베이스 사용 설정은 Spring boot의 가이드에 따르며, 멀티 데이터소스와 분산 트랙잭션을 위한 설정을 지원한다.(초기 프레임워크는 멀티 데이터소스 설정임)
 

### 기본 한 개 데이터소스 설정
* application.yml 설정
```java
spring:
  datasource:
      platform: h2
      driverClassName: org.h2.Driver
      url: jdbc:h2:~/test;AUTO_SERVER=TRUE
      username: sa
      password:

# MyBatis 환경 설정
mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  configuration:
    lazyLoadingEnabled: true
    aggressiveLazyLoading: false
    mapUnderscoreToCamelCase: true

```

### 멀티 데이터소스 설정
* application.yml 설정
```java
spring:
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
      
# MyBatis 설정은 Java Data Source 설정에 따르기 때문에 여기서는 삭제한다.
```

* Data Source Configuration 설정
com.ktds.devpro.config 경로에 다음과 같이 두 개의 설정 파일을 추가한다.(/custom/ 경로에서 복사)

1. 메인 데이터 소스 파일 샘플(DataSourceConfiguration.java)
```java
package com.ktds.devpro.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 *
 * DataSource Config 관련 설정
 * <p>
 *
 * <pre>
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2017. 3. 16.   kt ds     최초작성
 * </pre>
 *
 * @author kt ds A.CoE(blue.park@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@MapperScan(basePackages="com.ktds.devpro.sample", sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConfiguration {

    @Primary
    @Bean(name="dataSource", destroyMethod = "close")
    @ConfigurationProperties(prefix="spring.datasource.h2")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ktds.devpro.sample");
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/sample/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate", destroyMethod = "clearCache")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}
```

2. 두 번째 데이터 소스 파일 샘플 (HsqldbDataSourceConfiguration.java)
```java
package com.ktds.devpro.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 *
 * DataSource Config 관련 설정
 * <p>
 *
 * <pre>
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2017. 3. 16.   kt ds     최초작성
 * </pre>
 *
 * @author kt ds A.CoE(blue.park@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@MapperScan(basePackages="com.ktds.devpro.other", sqlSessionFactoryRef="hsqldbSqlSessionFactory")
public class HsqldbDataSourceConfiguration {

    @Bean(name="hsqldbDataSource")
    @ConfigurationProperties(prefix="spring.datasource.hsql")
    public DataSource hsqldbDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "hsqldbSqlSessionFactory")
    public SqlSessionFactory hsqldbSqlSessionFactory(@Qualifier("hsqldbDataSource") DataSource hsqldbDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(hsqldbDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/other/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "hsqldbSqlSessionTemplate", destroyMethod = "clearCache")
    public SqlSessionTemplate hsqldbSqlSessionTemplate(SqlSessionFactory hsqldbSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(hsqldbSqlSessionFactory);
    }
}
```


### 분산 트랜잭션(2PC) 데이터소스 설정
* build.gradle 설정
```java
// 분산 트랙잭션 라이브러리
compile('org.springframework.boot:spring-boot-starter-jta-atomikos')
```

* application.yml 설정
```java
spring:
  jta:
    enabled: true
    service: com.atomikos.icatch.standalone.UserTransactionServiceFactory
    max-actives: 200
    enable-logging: falseps -ef

    atomikos:
      datasource:
        primary:
          unique-resource-name: dataSource
          max-pool-size: 5
          min-pool-size: 1
          max-life-time: 20000
          borrow-connection-timeout: 10000
          xa-data-source-class-name: org.h2.jdbcx.JdbcDataSource
          xa-properties:
            user: sa
            password:
            URL: jdbc:h2:~/test;DB_CLOSE_DELAY=-1

        second:
          unique-resource-name: secondDataSource
          max-pool-size: 5
          min-pool-size: 1
          max-life-time: 20000
          borrow-connection-timeout: 10000
          xa-data-source-class-name: org.h2.jdbcx.JdbcDataSource
          xa-properties:
            user: sa
            password:
            URL: jdbc:h2:~/test2;DB_CLOSE_DELAY=-1
            
# MyBatis 설정은 Java Data Source 설정에 따르기 때문에 여기서는 삭제한다.

```

* Data Source Configuration 설정
com.ktds.devpro.config 경로에 다음과 같이 두 개의 설정 파일을 추가한다.(/custom/ 경로에서 복사)

1. 메인 데이터 소스 파일 샘플 (DataSourceConfiguration2PcPrimary.java)
```java
package com.ktds.devpro.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *
 * DataSource Config 관련 설정
 * <p>
 *
 * <pre>
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2017. 3. 16.   kt ds     최초작성
 * </pre>
 *
 * @author kt ds A.CoE(blue.park@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})
@MapperScan(basePackages="com.ktds.devpro.sample", sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConfiguration2PcPrimary {

    @Primary
    @Bean(name="dataSource", destroyMethod = "close")
    @ConfigurationProperties(prefix="spring.jta.atomikos.datasource.primary")
    public DataSource dataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ktds.devpro.sample");
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/sample/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate", destroyMethod = "clearCache")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
```

2. 두 번째 데이터 소스 파일 샘플 (DataSourceConfiguration2PcSecond.java)
```java
package com.ktds.devpro.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 * DataSource Config 관련 설정
 * <p>
 *
 * <pre>
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2017. 3. 16.   kt ds     최초작성
 * </pre>
 *
 * @author kt ds A.CoE(blue.park@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})
@MapperScan(basePackages="com.ktds.devpro.other", sqlSessionFactoryRef="secondSqlSessionFactory")
public class DataSourceConfiguration2PcSecond {

    @Bean(name="secondDataSource")
    @ConfigurationProperties(prefix="spring.jta.atomikos.datasource.second")
    public DataSource secondDatasource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource secondDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(secondDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/other/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "secondSqlSessionTemplate", destroyMethod = "clearCache")
    public SqlSessionTemplate secondSqlSessionTemplate(SqlSessionFactory secondSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(secondSqlSessionFactory);
    }
}
```


