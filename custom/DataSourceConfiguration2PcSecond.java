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
 * DataSource Config ?????? ??????
 * <p>
 *
 * <pre>
 * ????????????(Modification Information)??
 * ?????????   ?????????    ????????????
 * ------------------------------------
 * 2017. 3. 16.   kt ds     ????????????
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