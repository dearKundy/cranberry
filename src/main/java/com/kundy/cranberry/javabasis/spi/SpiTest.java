package com.kundy.cranberry.javabasis.spi;

import java.util.ServiceLoader;

/**
 * Java Spi 机制测试
 * <p>
 * 要使用Java SPI，需要遵循如下约定：
 * 1、当服务提供者提供了接口的一种具体实现后，在jar包的META-INF/services目录下创建一个以“接口全限定名”为命名的文件，内容为实现类的全限定名；
 * 2、接口实现类所在的jar包放在主程序的classpath中；
 * 3、主程序通过java.util.ServiceLoder动态装载实现模块，它通过扫描META-INF/services目录下的配置文件找到实现类的全限定名，把类加载到JVM；
 * 4、SPI的实现类必须携带一个不带参数的构造方法；
 * <p>
 * 使用Java SPI机制的优势是实现解耦，使得第三方服务模块的装配控制的逻辑与调用者的业务代码分离，
 * 而不是耦合在一起。应用程序可以根据实际业务情况启用框架扩展或替换框架组件。
 *
 * @author kundy
 * @date 2019/8/27 6:07 PM
 */
public class SpiTest {

    public static void main(String[] args) {
        ServiceLoader<Animal> animals = ServiceLoader.load(Animal.class);
        for (Animal animal : animals) {
            animal.shout();
        }
    }

}
