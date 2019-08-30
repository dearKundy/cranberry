package com.kundy.cranberry.systemdesign.designpattern.factory.origin;


/**
 * 存在问题：当我们需要执行减法运算时，就需要创建一个 OperationSub 类。我们想要
 * 使用不同的运算的时候就要创建不同的类，并且要明确知道该类的名字。
 * <p>
 * 这种重复的创建类的工作其实可以放到一个统一的工厂类中（简单工厂模式）
 *
 * @author kundy
 * @date 2019/6/18 5:28 PM
 */
public class Main {

    public static void main(String[] args) {
        OperationAdd operationAdd = new OperationAdd();
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResult());

    }

}
