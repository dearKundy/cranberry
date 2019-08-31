## 什么是 Spring
Spring 是一个开源框架，主要是为了解决企业应用程序开发复杂性而而创建的。而在我们日常开发中最常使用到的就是 `IOC`、`AOP`、`Spring事务`这三个模块。

## IOC
`IOC` 的字面意思是控制反转，简单的说就是把对象的创建和管理交给 `Spring` 去做。下面先看一下具体怎么使用。
1. 想要使用 IOC ，得先导入 `spring-context` 的依赖包。
2. 创建 Spring 的配置文件，最简单的配置文件如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="creationBeanByConfig" class="ioc.creation.byconfig.CreationBeanByConfig">
        <property name="name" value="hello"/>
    </bean>

</beans>
```
- Spring 配置文件的文件头都表示什么意思呢？
    - xmlns=http://www.springframework.org/schema/beans  和 xmlns:xsi=http://www.w3.org/2001/XMLSchema-instance 是必须有的，所有的spring配置文件都一样
    - `xmlns:xxx` 这个是xml的命名空间，简单的理解其实就是你要使用spring的哪些模块的内容。
    - `xsi:schemaLocation`，这个是为上面配置的命名空间指定xsd规范文件，这样你在进行下面具体配置的时候就会根据这些xsd规范文件给出相应的提示。命名空间与xsd规范文件成对出现。

- bean 元素又怎么理解？
    - `id`：该 bean 的唯一标识，通过 id 来找到该 bean 。
    - `class`：该 bean 的类型。
    - `property`：为该 bean 的成员变量赋予初始值。

3. 创建 IOC 容器对象，调用容器对象的 getBean() 方法获取 bean。
```java
    public static void main(String[] args) {
        // 创建 Spring 的 IOC 容器对象 
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // 从 IOC 容器中获取 Bean 实例
        CreationBeanByConfig creationBeanByConfig = context.getBean("creationBeanByConfig", CreationBeanByConfig.class);
        creationBeanByConfig.sayHi();

    }
```
上面出现了一个 `IOC 容器` 新名词，它主要用来创建对象并管理他们的整个生命周期从创建到销毁。Spring 提供了两种不同类型的 IOC 容器：
- `BeanFactory`：IOC容器的基本实现。
- `ApplicationContext`：提供了更多的高级特性。是 BeanFactory 的子接口。
- BeanFactory 是 Spring 框架的基础设施，面向Spring 本身；ApplicationContex t面向使用 Spring 框架的开发者，几乎所有的应用场合都直接使用 ApplicationContext 而非底层的 BeanFactory 。

ApplicationContext 常用的实现类有如下3个：
- `FileSystemXmlApplicationContext`：该容器从 XML 文件中加载已被定义的 bean。在这里，你需要提供给构造器 XML 文件的完整路径。
- `ClassPathXmlApplicationContext`：该容器从 XML 文件中加载已被定义的 bean。在这里，你不需要提供 XML 文件的完整路径，只需正确配置 CLASSPATH 环境变量即可，因为，容器会从 CLASSPATH 中搜索 bean 配置文件。
- `WebXmlApplicationContext`：该容器会在一个 web 应用程序的范围内加载在 XML 文件中已被定义的 bean。

这里需要注意一个点：创建IOC 容器对象的是会，会一次性初始化 bean.xml 中的所有 单例bean，非单例的不会初始化。

运行结果：
> 正在调用 CreationBeanByConfig 的无参构造函数
正在调用 CreationBeanByConfig 的 setName()方法
hello

可以看到
- 创建 CreationBeanByConfig 的时候，默认会调用它的无参构造函数。
- 为属性 name 赋值的时候需要调用 setName() 方法。

## Bean 的作用域
> <bean id="beanLifecycle" class="ioc.creation.byconfig.BeanLifecycle" scope="prototype">


在 bean 属性中，可以使用 scope 指定 Bean 的作用域，常用有两种作用域：
- `singleton`（默认）：作用域将 bean 的定义的限制在每一个 Spring IoC 容器中的一个单一实例。
- `prototype`：该作用域将单一 bean 的定义限制在任意数量的对象实例。

注意：这里想要看到单例的效果，必须要使用同一个 IOC 容器来获取 Bean，要不然就是两个不同的Bean了。

## Bean 的生命周期
> <bean id="beanLifecycle" class="ioc.creation.byconfig.BeanLifecycle" init-method="init" destroy-method="destroy"/>
> 
在 bean 的定义中使用 `init-method` 属性指定一个方法，在实例化 bean 时，立即调用该方法。同样，`destroy-method` 指定一个方法，只有从容器中移除 bean 之后，才能调用该方法。

至于具体调用的是什么方法需要我们自己去指定，该方法在该 Bean 中在实现。

## 依赖注入
当我们想在一个 Bean 注入一个简单类型的成员变量可以使用 value 属性，但是我们想要传递一个引用对象就需要使用 ref 属性。ref 属性应用的是同一个配置文件中的另一个 bean。通常有两种办法注入，基于构造函数、基于 setter。假设 BeanA 想要注入 BeanB，那么BeanA 必须含有一个带有BeanB参数的函数或者是一个 BeanB 的 setter() 方法。
```xml
<!-- 基于构造函数注入 -->
    <bean id="beanA" class="ioc.injection.byconfig.byconstructor.BeanA">
        <constructor-arg ref="beanB"/>
    </bean>

    <bean id="beanB" class="ioc.injection.byconfig.byconstructor.BeanB"/>

    <!-- 基于设值函数注入 -->
    <bean id="beanC" class="ioc.injection.byconfig.bysetter.BeanC">
        <property name="benaD" ref="beanD"/>
    </bean>

    <bean id="beanD" class="ioc.injection.byconfig.bysetter.BenaD"/>
```

## 自动装配
> 
对于引用类型的成员变量，我们可以连<property>属性都不写，而是使用 bean 的 autowire 属性去进行自动装配。autowire 常用有 byName 与 byType 两种。
```xml
    <!-- 属性设置为 byName。然后，它尝试将它的属性与配置文件中定义为相同名称的 beans 进行匹配和连接-->
    <bean id="beanE" class="ioc.injection.byconfig.byautowire.byName.BeanE" autowire="byName"/>
    <bean id="beanF" class="ioc.injection.byconfig.byautowire.byName.BeanF"/>

    <!-- autowire 属性设置为 byType。然后，如果它的 type 恰好与配置文件中 beans 名称中的一个相匹配，它将尝试匹配和连接它的属性。找不到或者多于一个会抛异常 -->
    <bean id="beanG" class="ioc.injection.byconfig.byautowire.byType.BeanG" autowire="byType"/>
    <bean id="beanH" class="ioc.injection.byconfig.byautowire.byType.BeanH"/>
```

## 基于注解实现自动注入
想要使用注解，得先在配置文件中加入以下配置。
```xml
<context:annotation-config/>
```
### @Autowired
现在我们使用注解来完成属性的注入，这样我们就不需要在 bean 中配置 <property>属性，也不用配置 autowire 属性。使用 @Autowired 注解即可完成。
```java
/**
 * 使用 @Autowired 注解注入 BeanJ
 *
 * @author kundy
 * @date 2019/4/27 4:54 PM
 */
public class BeanI {

    @Autowired
    private BeanJ beanJ;

    public void setBeanJ(BeanJ beanJ) {
        this.beanJ = beanJ;
    }

    private void sayHi() {
        beanJ.sayHi();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotationBean.xml");
        BeanI beanI = context.getBean("beanI", BeanI.class);
        beanI.sayHi();
    }
}
```
- @Autowired 可以作用于 待注入属性上面，（这种方式setter方法可省略）
- @Autowired 也可以作用于 待注入属性的setter方法上
 - 当 Spring遇到一个在 setter 方法中使用的 @Autowired 注释，它会在方法中视图执行 `byType` 自动连接。
 
 注意哦，这里只是使用注解的方式实现了自动注入beanJ，对于 BeanI 还是需要在配置文件中配置该 Bean 的。
 
### @Qualifier
当你创建多个具有相同类型的 bean 时，并且想要用一个属性只为它们其中的一个进行装配，在这种情况下，你可以使用 @Qualifier 注释和 @Autowired 注释通过指定哪一个真正的 bean 将会被装配来消除混乱。
```java
public class Test {

    @Autowired
    @Qualifier("aserviceImplTwo")
    private Aservice aservice;

    public void sayHi() {
        aservice.sayHi();
    }
}
```

### @Resource
Resource和@Autowired都可以来完成注入依赖,@Resource默认是按照名称来装配注入的，只有当找不到与名称匹配的bean才会按照类型来装配注入。

#### @PostConstruct 和 @PreDestroy 
就是上面的init-method 和 destroy-method注解版。

#### 在 classpath 中扫描组件
说了这么多，还是要依赖配置文件啊，能不能彻底摆脱配置文件呢？答案是肯定的，我们可以使用 Stereotype 的注解来实现，在 Bean 的上面加上以下注解，就不需要在配置文件中配置 Bean 了，Spring 容器会自动识别。但是这个注解又分为了很多中，从功能上看他们是没有区别的，但是从逻辑分层上看却有很大的区别，主要用来帮助我们区分层次功能的。
- `@Component`：基本注解，标识了一个受Spring管理的组件
- `@Respository`：标识持久层组件
- `@Service`：标识服务层（业务层）组件
- `@Controller`：标识表现层组件


1. 只是建议这样使用，实际上spring是无法检测你所标记的是什么层，如果你愿意也可以在服务层使用Respository进行注解，但是不太建议这样做。
2. 对于扫描到的组件，Spring有默认的命名策略：使用非限定类名，第一个字母小写，也可以在注解中通过value属性值标识组件的名称。
3. 当在组件类上使用了特定的注解之后，还需要在Spring的配置文件中声明 `<context:component-scan>`：
    * base-package属性指定一个需要扫描的基类包，Spring容器将会扫描这个基类包里及其子包中的所有类。

```xml
<context:component-scan base-package="ioc.*"/>
```

## 基于Java的配置文件
上面都是使用 xml 文件的方式来进行配置 Spring bean，我们也可以使用Java编码的方式代替传统的xml配置文件。使用@Configuration 和 @Bean 注解即可完成。
```java
/**
 * 基于 Java 的配置 （使用基于 Java 的配置 代替 xml 配置文件）
 * <p>
 *
 * @author kundy
 * @import 注解允许从另一个配置类中加载 @Bean 定义。
 * @Bean(initMethod = "init", destroyMethod = "cleanup" ) 指定 初始化、销毁函数
 * @Scope 设置 bean 的scope
 * @date 2019/4/27 5:25 PM
 */
@Configuration
public class TestConfig {

    @Bean
    public ConfigBean configBean() {
        // 当 @Beans 依赖对方时，表达这种依赖性非常简单，只要有一个 bean 方法调用另一个
        ConfigBean configBean = new ConfigBean(configBeanB());
        configBean.setName("测试");
        return configBean;
    }

    @Bean
    public ConfigBeanB configBeanB(){
        return new ConfigBeanB();
    }
}

/**
 * 上面的类相当于下面的xml配置文件
 * <beans>
 * <bean id="configBean" class="config.byannotation.TestConfig" />
 *   <property name="name" value="测试"/>
 *   <property name="configBeanB" ref="configBeanB"/>
 * <bean>
 *
 * <bean id="configBean" class="config.byannotation.TestConfigB" />
 *
 * </beans>
 */
```

```java
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        ConfigBean configBean = context.getBean("configBean", ConfigBean.class);
        configBean.sayHi();
    }
```
这里需要使用AnnotationConfigApplicationContext这个 IOC 容器来加载Java配置文件。

## AOP
加入以下依赖
```xml
        <!-- AOP -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.2</version>
        </dependency>

        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.9.2</version>
        </dependency>

        <dependency>
            <groupId>aopalliance</groupId>
            <artifactId>aopalliance</artifactId>
            <version>1.0</version>
        </dependency>
```
### 基于xml的AOP
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd

    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop-3.0.xsd ">

    <aop:config>

        <aop:aspect id="log" ref="loggingAspect">

            <aop:pointcut id="logPointcut" expression="execution(* aop.byxml.*.*(..))"/>

            <aop:before method="beforeAdvice" pointcut-ref="logPointcut"/>
            <aop:after method="afterAdvice" pointcut-ref="logPointcut"/>
            <aop:after-returning method="afterReturningAdvice" pointcut-ref="logPointcut" returning="retVal"/>
            <aop:after-throwing method="afterThrowingAdvice" pointcut-ref="logPointcut" throwing="ex"/>
        </aop:aspect>

    </aop:config>

    <bean id="person" class="aop.byxml.Person"/>
    <bean id="loggingAspect" class="aop.byxml.LoggingAspect"/>

</beans>
```

### 基于注解的AOP
需要在配置文件中加入 ` <aop:aspectj-autoproxy/>`
然后再切面类上加上@Aspect和@Component，也可以定义一个pointcut，然后再方法上加上对应的执行时即可。
```java
@Aspect
@Component
public class LoggingAspect2 {

    @Pointcut("execution(* aop.byannotation.Person2.sayHi())")
    private void pointcutSayHi() {
    }

    @Pointcut("execution(* aop.byannotation.Person2.testException())")
    private void pointcutTestException() {
    }

    @Pointcut("execution(* aop.byannotation.Person2.testAround())")
    private void pointcutTestAround() {
    }



    @Before("pointcutSayHi()")
    public void beforeAdvice() {
        System.out.println("正在执行 beforeAdvice()");
    }

    @After("pointcutSayHi()")
    public void afterAdvice() {
        System.out.println("正在执行 afterAdvice()");
    }

    @AfterReturning(pointcut = "pointcutSayHi()", returning = "retVal")
    public void afterReturningAdvice(Object retVal) {
        System.out.println("返回值为:" + retVal.toString());
    }

    @AfterThrowing(pointcut = "pointcutTestException()", throwing = "ex")
    public void afterThrowingAdvice(IllegalArgumentException ex) {
        System.out.println("方法抛出异常: " + ex.toString());
    }

    @Around("pointcutTestAround()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("执行 aroundAdvice() 之前");
        Object result = joinPoint.proceed();
        System.out.println("执行 aroundAdvice() 之后");
        return result;
    }

}
```
想要被切面拦截的 bean 也必须加入到 Spring 容器中。
