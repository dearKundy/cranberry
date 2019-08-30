package com.kundy.cranberry.systemdesign.designpattern.strategy;

/**
 * @author kundy
 * @date 2019/5/31 4:31 PM
 */
public class FlyNoWay implements Fly {

    @Override
    public void fly() {
        System.out.println("I can't fly");
    }

}
