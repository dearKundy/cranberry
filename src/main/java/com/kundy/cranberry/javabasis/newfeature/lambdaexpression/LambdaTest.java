package com.kundy.cranberry.javabasis.newfeature.lambdaexpression;

/**
 * Java 8 Lambda 表达式
 * <p>
 * Lambda 允许把函数作为一个方法的参数。
 * <p>
 * lambda 表达式的语法格式如下：
 * (parameters) -> expression
 * 或
 * (parameters) ->{ statements; }
 * <p>
 * 以下是lambda表达式的重要特征:
 * - 可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
 * - 可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
 * - 可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
 * - 可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。
 * <p>
 * 注意：
 * - lambda 表达式只能引用标记了 final 的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
 * - lambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
 *
 * @author kundy
 * @date 2019/8/29 10:39 AM
 */
public class LambdaTest {

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    public static void main(String[] args) {

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        LambdaTest lambdaTest = new LambdaTest();
        System.out.println("10 + 5 = " + lambdaTest.operate(10, 5, addition));
        System.out.println("10 - 5 = " + lambdaTest.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + lambdaTest.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + lambdaTest.operate(10, 5, division));


        // 不用括号
        GreetingService greetingService = message -> System.out.println("Hello " + message);

        // 用括号
        GreetingService greetingService1 = (message) -> System.out.println("Hello " + message);

        greetingService.sayHi("China");
        greetingService1.sayHi("HK");

        /*
         * 匿名内部类
         * - 匿名内部类只适合创建那种只需要一次使用的类。
         * - 创建匿名内部类时会立即创建一个该类的实例，这个类定义立即消失。
         */
        Person person = new Person() {

            @Override
            public void walk() {
                System.out.println("walk...");
            }

            @Override
            public void speak() {
                System.out.println("speak...");
            }
        };

    }

}
