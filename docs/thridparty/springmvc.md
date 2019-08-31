## Hello Word 程序
1. 导入 spring-webmvc 依赖
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
```
![WechatIMG535.png](https://i.loli.net/2019/08/31/wKRPr3HGkodXVba.png)
可以看到我们导入 spring-webmvc 还依赖了 Spring 相关的和一个 spring-web 依赖包。

2. 创建 Servlet 的 Web.xml 配置文件，配置一个 DispatcherServlet 。
```xml
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
    <display-name>Archetype Created Web Application</display-name>

    <servlet>
        <servlet-name>springDispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <!-- 配置 DispatcherServlet 的初始化参数：SpringMVC 配置文件的位置和名称（其实就是 Spring 的配置文件）
            默认值：/WEB-INF/<servlet-name>-servlet.xml
            -->
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <!-- 启动立马加载 -->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!-- 所有的请求都会被 springDispatcherServlet 拦截处理-->
    <servlet-mapping>
        <servlet-name>springDispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>

```
3. 配置 SpringMVC.xml 配置文件
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
   http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context-3.0.xsd
   http://www.springframework.org/schema/mvc
   http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 开启自动扫描包 -->
    <context:component-scan base-package="com.kundy.mvcpractice.*"/>

    <!-- 而<mvc:annotation-driven/>是告知Spring，我们启用注解驱动，为WEB 应用服务(我们就可以使用该标签注册的几个bean的功能)。 -->
    <mvc:annotation-driven/>

    <!-- 配置视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```
4. 创建控制器
```java
@Controller
public class HelloController {

    /**
     * 返回值会通过视图解析器解析为实际的物理视图，对于 InternalResourceViewResolver 视图解析器，会做如下的解析：
     * 通过 prefix + returnVal + suffix 这样的方式得到实际的物理视图，然后做转发操作
     * /WEB-INF/jsp/success.jsp
     */
    @RequestMapping("testView")
    public String testView() {
        return "success";
    }
}
```
- 使用 `@Controller` 标记当前类为一个控制器。
- 使用 `@RestController` 直接标记为一个控制器并返回Json数据，@RestController就是@Controller与@ResponseBody的合体。
- 使用 `@RequestMapping` 注解为控制器指定可以处理那些URL请求，可以使用 method 属性指定请求方式。
     - `@RequestMapping` 在控制器的类定义及方法定义出都可以标注
    * 类定义处：提供初步的请求映射信息。相对于WEB应用的根目录
    * 方法处：提供进一步的细分映射信息。
- 使用 `@PostMapping` 或者 `@GetMapping` 注解，这样就不用使用 method 属性指定请求方式了。

### @PathVariable 
```java
    /**
     * @PathVariable 映射 URL 绑定的占位符
     * 带占位符的 URL 是 Spring3.0 新增的功能，该功能在 SpringMVC 的 REST 目标挺进发展过程中具有里程碑的意义
     * 通过@PathVariable可以将URL中占位符参数绑定到控制器处理方法的入参中。
     */
    @ResponseBody
    @GetMapping("/hi/{id}")
    public String hi(@PathVariable("id") String id) {
        System.out.println("id：" + id);
        return id;
    }
```

## @RequestParam
```java
        /**
     * 当我们使用基本类型基本类型去接收参数时，如果客户端没有传入该参数会报以下异常：
     * cannot be translated into a null value due to being declared as a primitive type.
     * 原因是SpringMvc 会把没有传入的参数赋予 null 值，但是基本类型不能赋null值，自然就报错了。
     * 
     * required：指定该参数是否必须 defaultValue：默认值
     */
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(@RequestParam(name = "id", required = false, defaultValue = "123") Integer id) {
        System.out.println("id：" + id);
        return "success";
    }
```

## @RequestHeader 与 @CookieValue
- `@RequestHeader`：获取请求头的值。
- `@CookieValue`：绑定请求中的 Cookie 值。
```java
    /**
     * HTTP Status 400 – Bad Request 一般都是传入参数有问题
     * 想要拿到 Content-Type 的值，客户端请求 header 中必须要加上该属性值Content-Type : 234
     */
    @ResponseBody
    @GetMapping("/testHeader")
    public String testHeader(@RequestHeader("Content-Type") String contentType, @CookieValue("JSESSIONID") String cookie) {
        return contentType + cookie;
    }
```

## @ResponseBody 与 @RequestBody
- `@ResponseBody`：用来返回json数据，用在方法名上。
- `@RequestBody`：用来接收json数据，用在参数上。

使用前先导入 相关的 Json 依赖
```xml
        <!-- Json 相关依赖 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.8</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.9.8</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.9.8</version>
        </dependency>
```
```java
    /**
     * 想要使用 @RequestBody 得先加入相关Jackson依赖，否则会报 HTTP 415 错误
     * <p>
     * 想要返回 xml 格式的参数 要加上produces = {"application/xml; charset=UTF-8"} 和 导入 jackson-dataformat-xml 依赖
     * 但是要注意，请求该接口的客户端需要在 header 加上 Accept: application/xml 这个属性
     * 否则会包 HTTP 406 Not Acceptable 异常
     *
     */
    @ResponseBody
    @PostMapping(value = "/testBody", produces = {"application/xml; charset=UTF-8"})
    public User testBody(@RequestBody User user) {
        User user1 = new User();
        user1.setAge(123);
        user1.setId(46);
        user1.setName("tom");
        return user1;
    }
```

## 接收 Servlet-api 参数
```java
    /**
     * 需要导入 servlet-api 依赖
     */
    @ResponseBody
    @GetMapping("/testServletParam")
    public void testServletParam(HttpServletRequest request) {
        System.out.println(request.getMethod());
        System.out.println(request);
    }
```
想要使用servlet-api参数，得先导入 servlet-api 依赖。因为单独引入 spring-webmvc 的依赖，你会发现 servlet-api 依赖是没有的，
因为 servlet-api 的 scope 是 provided，打包的时候不会打进去，web 容器会提供 servlet-api 。

## HttpMessageConverter
> SpringMVC 会根据请求头中的 Accept 属性选择合适的 HttpMessageConverter 去将标有@ResponseBody 的方法中的返回结果转换成Json格式。将根据请求头中content-type的属性选择合适的 HttpMessageConverter 将传入的参数转换成相应的对象。

## 拦截器
实现拦截器类，一般功能实现 HandlerInterceptor 即可。
```java
public class FirstInterceptor implements HandlerInterceptor {

    /**
     * 处理请求之前被调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("【FirstInterceptor】执行 preHandle。。。。");
        return true;
    }

    /**
     * 处理完请求，响应之前被调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("【FirstInterceptor】执行 postHandle。。。");
    }

    /**
     * 返回响应之后被调用，用于处理一些资源的释放
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("【FirstInterceptor】执行 afterCompletion。。。");
    }
}
```
在 springmvc.xml 中配置拦截器
```xml
    <!-- 定义拦截器 -->
    <mvc:interceptors>
        <!-- 使用bean定义一个Interceptor，直接定义在mvc:interceptors根下面的Interceptor将拦截所有的请求 -->
        <bean class="com.kundy.mvcpractice.interceptor.AllInterceptor"/>

        <!-- 定义在mvc:interceptor下面的表示是对特定的请求才进行拦截的 -->
        <mvc:interceptor>
            <mvc:mapping path="/hello"/>
            <bean class="com.kundy.mvcpractice.interceptor.FirstInterceptor"/>
        </mvc:interceptor>

        <mvc:interceptor>
            <mvc:mapping path="/hello"/>
            <bean class="com.kundy.mvcpractice.interceptor.SecondInterceptor"/>
        </mvc:interceptor>

    </mvc:interceptors>
```
> 【AllInterceptor】执行 preHandle。。。。
【FirstInterceptor】执行 preHandle。。。。
【SecondInterceptor】执行 preHandle。。。。
id：123
【SecondInterceptor】执行 postHandle。。。
【FirstInterceptor】执行 postHandle。。。
【AllInterceptor】执行 postHandle。。。
【SecondInterceptor】执行 afterCompletion。。。
【FirstInterceptor】执行 afterCompletion。。。
【AllInterceptor】执行 afterCompletion。。。
- 多个生效拦截器按照定义的顺序执行，先执行完所有的preHandle，再执行请求，然后按照相反的顺序postHandle，执行完全部的 postHandle 后，再按照相反的顺序执行 afterCompletion
- 如果某个拦截器的preHandle方法返回false，后续的拦截器不会被调用，目标方法也不会被调用。

## 异常处理
### 局部异常处理
```java
    @GetMapping("testException")
    public String testException() {
//        int i = 10 / 0;
        throw new IllegalArgumentException();

    }

    /**
     * 捕获指定类型范围内的异常，该异常处理器仅在当前控制器有效
     * 如果存在多个异常处理器，优先匹配精确度高的，例如抛出一个数学异常，
     * 存在一个RuntimeException与ArithmeticException异常处理器，则会匹配ArithmeticException异常处理器
     *
     * @ControllerAdvice：如果当前控制器中找不到@ExceptionHandler 方法出来处理当前方法出现的异常，
     * 则将去ControllerAdvice标记的类中查找@ExceptionHandler标记的方法来处理异常
     */
    @ResponseBody
    @ExceptionHandler({ArithmeticException.class})
    public String handleArithmeticException(ArithmeticException e){
        System.out.println("出现算术异常");
        e.printStackTrace();
        return "error";
    }
```


### 全局异常处理
```java
@ControllerAdvice
public class HandleException {


    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public String handleArithmeticException(Exception e){
        System.out.println("【HandleException】出现算术异常");
        e.printStackTrace();
        return "error";
    }
}
```

