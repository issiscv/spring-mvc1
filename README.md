## Spring MVC 구조

![mvc](https://user-images.githubusercontent.com/66157892/147829522-b9b81d73-f3c2-40ec-9309-06a4e966e1f9.PNG)

### MVC 흐름 정리
1. 사용자의 요청이 들어오면 DispatcherServlet 에서 HandlerMapping 과정을 거쳐 사용자의 요청 URI를 처리해줄 Handler를 조회
2. HandlerMapping을 통해 찾은 URI를 처리할 수 있는 HandlerAdapter를 조회한다.
3. Handler를 처리할 수 있는 HandlerAdpater를 실행한다.
4. HandlerAdapter에서 Handler를 실행
5. HandlerAdapter에서 Handler를 실행 후 반환받은 ModelAndView를 DispatcherServlet에게 반환한다.
6. DispatcherServlet은 해당 ModelAndView에서 ViewPath를 ViewResolver에게 반환
7. viewPath의 논리적 주소를 물리적 주소로 받을 수 있는 View 객체를 반환받는다.
8. DispatcherServlet에서 반환받은 View 객체를 통해 화면을 렌더링해주기 위해 이전에 HandlerAdapter를 통해 반환받은 ModelAndView에서 Model을 view를 rendering 하는데 있어 model을 인자로 넣어 사용자에게 data가 담긴 화면을 응답한다. 

### HandlerMapping
0. RequestMappingHandlerMapping : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용<br>
1. BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러를 찾는다.<br>

### HandlerAdapter
0. RequestMappingHandlerAdapter : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용.<br>
1. HttpRequestHandlerAdapter : HttpRequestHandler 처리.<br>
2. SimpleControllerHandlerAdapter : Controller 인터페이스(애노테이션X, 과거에 사용) 처리.<br>

## @RequestMapping
이전 스프링은 컨트롤러가 HandlerMapping의 기준이 되었다면 @RequestMapping 어노테이션의 등장으로 메서드가 HandlerMapping의 기준이 됨.

### RequestMappingHandlerMapping과 RequestMappingHandlerAdapter
1. 디스패처 서블릿이 앞에서 HTTP 요청을 먼저 받는다.
2. 디스패처 서블릿이 RequestMappingHandlerMapping를 통해 등록된 <b>HandlerMethod</b> 중에서 해당 요청에 매핑되는 <b>HandlerMethod</b>를 반환한다.
3. 디스패처 서블릿이 핸들러 어댑터(Handler Adapter)인 RequestMappingHandlerAdapter를 통해 <b>HandlerMethod</b>의 정보를 이용해 요청에 매핑되는 메소드를 실행한다

## Spring MVC의 실제 흐름
![image](https://user-images.githubusercontent.com/66157892/147830527-efbfc842-1378-4d69-8fb9-c543f3bf6ff4.png)
