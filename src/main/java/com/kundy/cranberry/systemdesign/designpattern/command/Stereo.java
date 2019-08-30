package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * @author kundy
 * @date 2019/6/2 10:01 AM
 */
public class Stereo implements Equipment {

    @Override
    public void on() {
        System.out.println("Stereo is turning on...");
    }

    @Override
    public void off() {
        System.out.println("Stereo is turning off...");
    }

}
