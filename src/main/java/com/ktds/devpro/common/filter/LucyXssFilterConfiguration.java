package com.ktds.devpro.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

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

	@Bean
	public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
	    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
	    filter.setIncludeClientInfo(true);
	    filter.setIncludeHeaders(true);
	    filter.setIncludePayload(true);
	    filter.setIncludeQueryString(true);
	    filter.setMaxPayloadLength(1000);
	    return filter;
	}



	
	
}
