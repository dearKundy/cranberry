package com.kundy.cranberry.systemdesign.designpattern.adapter;

/**
 * @author kundy
 * @date 2019/6/2 2:09 PM
 */
public class MallarDuck implements Duck {

    @Override
    public void quack() {
        System.out.println("Quack");
    }

    @Override
    public void fly() {
        System.out.println("I'm flying");
    }

}
