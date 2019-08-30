package com.kundy.cranberry.systemdesign.designpattern.decorator;

/**
 * 【饮料】
 *
 * @author kundy
 * @date 2019/6/1 1:32 PM
 */
public class Espresso extends Beverage {

    public Espresso() {
        // 覆盖父类的描述信息
        description = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }

}
