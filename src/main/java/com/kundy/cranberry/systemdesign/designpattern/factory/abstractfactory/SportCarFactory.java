package com.kundy.cranberry.systemdesign.designpattern.factory.abstractfactory;

/**
 * @author kundy
 * @date 2019/6/19 3:03 PM
 */
public class SportCarFactory implements CarFactory {

    @Override
    public BenzCar getBenzCar() {
        return new BenzSportCar();
    }

    @Override
    public TeslaCar getTeslaCar() {
        return new TeslaSportCar();
    }
}
