package com.ktds.devpro;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * Spring Application ????????? ?????????
 * <p>
 *
 * <pre>
 * ????????????(Modification Information)??
 * ?????????   ?????????    ????????????
 * ------------------------------------
 * 2017. 3. 16.   kt ds     ????????????
 * </pre>
 *
 * @author kt ds A.CoE(yu.chae@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebMvcStarterApplication.class);
	}

}
