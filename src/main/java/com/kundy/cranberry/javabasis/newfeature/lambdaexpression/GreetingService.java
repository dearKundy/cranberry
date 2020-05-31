package com.kundy.cranberry.javabasis.newfeature.lambdaexpression;

/**
 * FunctionalInterface：该注解只是提示编译器去检查接口是否仅包含一个抽象方法，即，是否符合函数式编程的定义。
 *
 * @author kundy
 * @date 2019/8/29 10:53 AM
 */
@FunctionalInterface
public interface GreetingService {

    void sayHi(String message);

}
