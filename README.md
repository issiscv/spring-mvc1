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

## HTTP 요청 - GET, POST, HTTP message body
### GET - 쿼리 파라미터
- 예) /url?username=hello&age=20
- 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
- 예) 검색, 필터, 페이징등에서 많이 사용하는 방식
### POST - HTML Form
- content-type: application/x-www-form-urlencoded
- 메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20
- 예) 회원 가입, 상품 주문, HTML Form 사용
### HTTP message body에 데이터를 직접 담아서 요청
- HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON 사용
- POST, PUT, PATCH
#### 요청 파라미터 vs HTTP 메시지 바디
- 요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute
- HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody

## HTTP 응답 - 정적 리소스, 뷰 템플릿
### 정적 리소스
- 예) 웹 브라우저에 정적인 HTML, css, js을 제공할 때는, 정적 리소스를 사용한다.
### 뷰 템플릿 사용
- 예) 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.
### HTTP 메시지 사용
- HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다

## MessageConverter
### 스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.
1. <b>HTTP 요청: @RequestBody , HttpEntity(RequestEntity) </b>
2. <b>HTTP 응답: @ResponseBody , HttpEntity(ResponseEntity)</b>
<br>
- <b>ByteArrayHttpMessageConverter</b> : byte[] 데이터를 처리한다.
    클래스 타입: byte[] , 미디어타입: */* ,<br>
    요청 예) @RequestBody byte[] data <br>
    응답 예) @ResponseBody return byte[] 쓰기 미디어타입 application/octet-stream <br>
- <b>StringHttpMessageConverter</b> : String 문자로 데이터를 처리한다.
클래스 타입: String , 미디어타입: */* <br>
요청 예) @RequestBody String data <br>
응답 예) @ResponseBody return "ok" 쓰기 미디어타입 text/plain <br>
- <b>MappingJackson2HttpMessageConverter</b> : application/json
클래스 타입: 객체 또는 HashMap , 미디어타입 application/json 관련 <br>
요청 예) @RequestBody HelloData data <br>
응답 예) @ResponseBody return helloData 쓰기 미디어타입 application/json 관련 <br>

### HTTP 요청 데이터 읽기
- HTTP 요청이 오고, 컨트롤러에서 @RequestBody , HttpEntity 파라미터를 사용한다.
- 메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해 canRead() 를 호출한다.
- 대상 클래스 타입을 지원하는가.예) @RequestBody 의 대상 클래스 ( byte[] , String , HelloData )
- HTTP 요청의 Content-Type 미디어 타입을 지원하는가. 예) text/plain , application/json , */*
- canRead() 조건을 만족하면 read() 를 호출해서 객체 생성하고, 반환한다.

### HTTP 응답 데이터 생성
- 컨트롤러에서 @ResponseBody , HttpEntity 로 값이 반환된다.
- 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 canWrite() 를 호출한다.
- 대상 클래스 타입을 지원하는가. 예) return의 대상 클래스 ( byte[] , String , HelloData )
- HTTP 요청의 Accept 미디어 타입을 지원하는가.(더 정확히는 @RequestMapping 의 produces ) 예) text/plain , application/json , */*
- canWrite() 조건을 만족하면 write() 를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성한다.

## RequestMappingHandlerAdapter 동작 방식
![arg](https://user-images.githubusercontent.com/66157892/147849787-f55e6dd1-e4e5-41b5-aa16-b8ef37407a0c.PNG)
### ArgumentResolver
-  애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있었다. HttpServletRequest , Model 은 물론이고, @RequestParam , @ModelAttribute 같은 애노테이션 그리고 @RequestBody , HttpEntity 같은 HTTP 메시지를 처리하는 부분까지 매우 큰 유연함을 보여주었다. 이렇게 파라미터를 유연하게 처리할 수 있는 이유가 바로 <b>ArgumentResolver</b> 덕분이다
- RequestMappingHandlerAdaptor 는 바로 이 <b>ArgumentResolver</b> 를 호출해서 컨트롤러(핸들러)가 필요로 하는 다양한 <b>파라미터의 값(객체)을 생성한다.</b>
### 동작 방식
- 동작 방식 ArgumentResolver 의 supportsParameter() 를 호출해서 해당 파라미터를 지원하는지 체크하고, 지원하면 <b>resolveArgument() 를 호출해서 실제 객체를 생성한다.</b> 그리고 이렇게 생성된 객체가 컨트롤러 호출시 넘어가는 것이다
### ReturnValueHandler
HandlerMethodReturnValueHandler 를 줄여서 ReturnValueHandle 라 부른다.
ArgumentResolver 와 비슷한데, 이것은 응답 값을 변환하고 처리한다.
컨트롤러에서 String으로 뷰 이름을 반환해도, 동작하는 이유가 바로 ReturnValueHandler 덕분이다.
어떤 종류들이 있는지 살짝 코드로 확인만 해보자

## HTTP 메시지 컨버터
![cnc](https://user-images.githubusercontent.com/66157892/147850005-e32c1d63-94c2-4510-bf7b-ba3a893261c5.PNG)<br>
### 요청의 경우
- @RequestBody 를 처리하는 ArgumentResolver 가 있고, HttpEntity 를 처리하는 ArgumentResolver 가 있다. 이 <b>ArgumentResolver 들이 HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성하는 것이다.</b>
### 응답의 경우
- @ResponseBody 와 HttpEntity 를 처리하는 ReturnValueHandler 가 있다. 그리고 여기에서 HTTP 메시지 컨버터를 호출해서 응답 결과를 만든다.

