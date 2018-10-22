package com.ktds.devpro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ktds.devpro.sample.SampleUserDetailServiceImpl;

@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SampleUserDetailServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
//			.inMemoryAuthentication()
//				.withUser("admin").password("{noop}1234").roles("ADMIN")
				;
	
	}
		

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/css/**","/js/**", "/" , "/actuator/**").permitAll()
			.antMatchers("/sample/**", "/console/**").hasRole("ADMIN")
//			.anyRequest().permitAll()
            .and()
			.formLogin().loginPage("/sample/login").permitAll()		
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
			.and().csrf().ignoringAntMatchers("/console/**","/actuator/**")
			.and().headers().frameOptions().disable()
			;
		
	}
	
	
	
	
	
	

}
