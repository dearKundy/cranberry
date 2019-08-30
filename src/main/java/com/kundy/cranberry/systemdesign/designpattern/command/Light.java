package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * @author kundy
 * @date 2019/6/2 9:20 AM
 */
public class Light implements Equipment {

    @Override
    public void on() {
        System.out.println("light is turning on...");
    }

    @Override
    public void off() {
        System.out.println("light is turning off...");
    }

}
