package com.kundy.cranberry.systemdesign.designpattern.factory.abstractfactory;

/**
 * @author kundy
 * @date 2019/6/19 3:02 PM
 */
public interface CarFactory {

    BenzCar getBenzCar();

    TeslaCar getTeslaCar();

}
