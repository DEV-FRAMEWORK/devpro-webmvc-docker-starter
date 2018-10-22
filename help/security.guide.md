# Spring Security Guide
---
>스프링 시큐리티는 스프링 프로젝트 중 하나로 보안에 관련된 설정을 쉽게 제공해주는 오픈소스 라이브러리이다. 
인증과 권한에 대한 설정을 기본적으로 제공해주어 쉽게 설정을 할 수 있게 되어있다. 
>* 작동방식 : security 관련 라이브러리 만 추가해도 애플리케이션의 모든 URL에 인증이 필요하게 되고 spring boot 내(autoconfiguration) 설정을 통해서 인증 및 권한 처리 프로세스를 구현 해야한다.



* 라이브러리 : build.gradle  

~~~groovy  
compile('org.springframework.boot:spring-boot-starter-security')
~~~

설정파일에 추가하면 두개 라이브러리가 빌드에 추가됨.

1. spring-security-config.jar  
1. spring-security-web.jar

라이브러리 추가후 모든 요청이 인증방식(로그인)으로 제어된다.



* 설정 파일
security 설정 작업은 WebSecurityConfigurerAdapter 를 확장해서 작성하고  
어노테이션 @EnableWebSecurity 를 사용하면 security 설정파일로 자동 호출된다.

~~~java
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{
	

~~~

~~~java
@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/css/**","/js/**", "/" , "/actuator/**").permitAll() //해당 패턴에 대해서 허용한다.
			.antMatchers("/sample/**", "/console/**").hasRole("ADMIN") //해당권한에 대해서만 허용한다.
//			.anyRequest().permitAll() //모든요청에 대해서 허용한다.
            .and()
			.formLogin().loginPage("/sample/login").permitAll() //로그인페이지는 모두 허용한다.		
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
			.and().csrf().ignoringAntMatchers("/console/**","/actuator/**") //csrf 처리 무시 패턴 (h2 콘솔 및 액츄에이터 url )
			.and().headers().frameOptions() .disable() //h2 console접근을 위한 설정
			;
		
	}
~~~
http 응답에 대한 처리 설정은 해당 매소드를 오버라이딩 해서 작성한다. 

~~~java
@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
			//메모리에 임의(샘플) 계정을 저장하고 인증에 사용한다.
//			.inMemoryAuthentication()
//				.withUser("admin").password("{noop}1234").roles("ADMIN")
				;
	}
~~~
로그인 인증처리를 위한 메소드를 오버라이딩 해서 작성한다. 해당 소스는 기본적인 유저 로그인 처리를 위한 샘플 소스를 작성하였다. user 기반의 로그인 인증 처리를 위해선  userDetailService 를 구현해야한다.
~~~java 
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ...
    }
    ...
}
~~~

userDetailService 를 구현하기위해선 UserDetailsService 상속받아 서비스로 구현해야하고 loadUserByUsername() 메소드를 오버라이딩해서 기본적으로 구현해야한다.

~~~java
private SampleUserVo findUserbyUername(String username) {
	    if(username.equalsIgnoreCase("admin")) {
	      return new SampleUserVo(username, "1234", "ADMIN");
	    }
	    return null;
      }
    
~~~
샘플코드에선 테스트 인증처리를 위해 UserDetails 객체를 생성하고 
아래 샘플 코드 에서 보다시피 UserBuilder 를 이용하여 
UserDetails 객체를 생성하여 리턴하였다.
~~~java
SampleUserVo user = findUserbyUername(username);
		UserBuilder builder = null;

		if(user !=null) {
			builder = User.withUsername(username);
			builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
			builder.roles(user.getRoles());
			
		}else {
			throw new UsernameNotFoundException("User not found!");
		}
		return builder.build();
~~~
비니지스 구현시 요구사항에 맞는 인증방식을 구현해야 하며, 해당 구현방법에 맞는 설정값을 설정하여야 한다.

* 추가 사항
  - csrf  방어를 위해 UI에서 폼으로 데이타 전송시 또는 json 형태로 데이타 전송시 csrf token 포함하여 전송하여야함.  
  포함하여 데이타 전송하여야 csrf 에 위배되지 않는다.  

~~~html
	<meta name="_csrf" content="${_csrf.token}">
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}">
	메타값 소스 상단에 선언후

	<form>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
~~~  

~~~javascript
	<script type="text/javascript">
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			 ...
		beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
			...
	</script>
 ~~~  
