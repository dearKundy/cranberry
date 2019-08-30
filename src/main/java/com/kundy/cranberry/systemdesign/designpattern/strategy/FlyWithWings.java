package com.kundy.cranberry.systemdesign.designpattern.strategy;

/**
 * @author kundy
 * @date 2019/5/31 4:32 PM
 */
public class FlyWithWings implements Fly {

    @Override
    public void fly() {
        System.out.println("I am flying!!");
    }
}
