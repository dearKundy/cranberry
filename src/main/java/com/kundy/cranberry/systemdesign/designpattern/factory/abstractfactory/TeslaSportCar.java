package com.kundy.cranberry.systemdesign.designpattern.factory.abstractfactory;

/**
 * @author kundy
 * @date 2019/6/19 3:00 PM
 */
public class TeslaSportCar implements TeslaCar {

    @Override
    public void charge() {
        System.out.println("给我特斯拉跑车冲满电");
    }
}
