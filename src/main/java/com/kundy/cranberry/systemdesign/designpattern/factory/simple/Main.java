package com.kundy.cranberry.systemdesign.designpattern.factory.simple;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;

/**
 * 简单工厂 把所有的产品放到一个工厂中
 * 优点：
 * 1. 创建一个对象，只要知道其名称就可以了。
 * 2. 屏蔽产品的具体实现，调用者只关心产品的接口。
 * 缺点：
 * 1. 一个工厂负责所有实例的创建逻辑，违反了高内聚责任分配原则。如果需要添加新的类，就需要改变工厂类。
 *
 * @author kundy
 * @date 2019/6/18 5:46 PM
 */
public class Main {

    public static void main(String[] args) {
        Operation operation = OperationFactory.createOperation("+");
        operation.setValue1(10);
        operation.setValue2(5);
        System.out.println(operation.getResult());
    }


}
