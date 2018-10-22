# Error 설정 #
---
### Spring Boot Error 처리
* 스프링 부트 기본설정에 의해서 에러발생시 whitelabel error page 를 호출해준다. 하지만 기본 에러 설정에는 사용하지 않으니 property 를 통해서 disable 시켜준다.
~~~yml
server:
  error:
    whitelabel:
      enabled: false
~~~
* 스프링 부트는 내부적으로 에러가 발생하게 되면 
org.springframework.boot.autoconfigure.web.BasicErrorController 에 의해서 대부분의 예외 처리를 하게 되고 
내부적으로 '/error' 를 호출 하게 되어있다. 
~~~yml
spring:
  mvc:
    view:
      prefix: /WEB-INF/jsp/
      suffix: .jsp
~~~
devpro 뷰 설정으로 해당 경로 하위에 custom된 error.jsp 파일이 존재하면 해당 파일을 호출해서 화면에 보여주게 된다.
### Error 처리 확장
org.springframework.boot.autoconfigure.web.ErrorController 인터페이스를 상속 받은 Bean이 존재하면 비활성화 처리 된다.
~~~java
public class CustomErrorController implements ErrorController {
	
	private static final String PATH= "/error";

	
	@RequestMapping(value = PATH)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
         
        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
 log.debug("CustomErrorController.error httpstatusCode : {}", httpErrorCode);
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
~~~
ErrorController 상속 받아서 구현하면되고 "/error" 기본 매핑에 대해서 처리 해주면 된다. 응답 코드에 대한 예제이며 해당 처리방식에 맞게 처리 해주면 된다.
