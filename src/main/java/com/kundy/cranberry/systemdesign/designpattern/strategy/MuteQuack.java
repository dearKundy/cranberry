package com.kundy.cranberry.systemdesign.designpattern.strategy;

/**
 * @author kundy
 * @date 2019/5/31 4:32 PM
 */
public class MuteQuack implements Quack {

    @Override
    public void quack() {
        System.out.println("<Silence>");
    }
}
