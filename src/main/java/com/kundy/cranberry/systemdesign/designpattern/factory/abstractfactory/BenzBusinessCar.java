package com.kundy.cranberry.systemdesign.designpattern.factory.abstractfactory;

/**
 * @author kundy
 * @date 2019/6/19 3:00 PM
 */
public class BenzBusinessCar implements BenzCar {

    @Override
    public void gasUp() {
        System.out.println("给我的奔驰商务车加一般的汽油");
    }
}
