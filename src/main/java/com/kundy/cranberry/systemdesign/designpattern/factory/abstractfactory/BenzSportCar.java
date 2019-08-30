package com.kundy.cranberry.systemdesign.designpattern.factory.abstractfactory;

/**
 * @author kundy
 * @date 2019/6/19 3:00 PM
 */
public class BenzSportCar implements BenzCar {

    @Override
    public void gasUp() {
        System.out.println("给我的奔驰跑车加最好的汽油");
    }
}
