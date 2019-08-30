package com.kundy.cranberry.systemdesign.designpattern.factory.factory;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;

/**
 * 工厂方法模式：
 * 优点：把不同的对象分开不同的工厂。在系统中加入新产品时，无须修改原来的工厂类，符合【开发-封闭原则】
 * 缺点：增加新产品的同时需要增加新的工厂，导致系统类的个数成对增加。
 *
 * @author kundy
 * @date 2019/6/19 11:29 AM
 */
public class Main {

    public static void main(String[] args) {
        IFactory factory = new AddFactory();
        Operation operationAdd = factory.createOperation();
        operationAdd.setValue1(10);
        operationAdd.setValue2(4);
        System.out.println(operationAdd.getResult());
    }

}
