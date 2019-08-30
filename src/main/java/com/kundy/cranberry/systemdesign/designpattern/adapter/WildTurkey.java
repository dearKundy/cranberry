package com.kundy.cranberry.systemdesign.designpattern.adapter;

/**
 * @author kundy
 * @date 2019/6/2 2:10 PM
 */
public class WildTurkey implements Turkey {

    @Override
    public void gobble() {
        System.out.println("Gobble gobble");
    }

    @Override
    public void fly() {
        System.out.println("I am flying a short distance");
    }

}
