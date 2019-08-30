package com.kundy.cranberry.systemdesign.designpattern.factory.abstractfactory;

/**
 * @author kundy
 * @date 2019/6/19 3:01 PM
 */
public class TeslaBusinessCar implements TeslaCar {

    @Override
    public void charge() {
        System.out.println("不用给我特斯拉商务车冲满电");
    }

}
