package com.kundy.cranberry.systemdesign.designpattern.decorator;

/**
 * 【饮料】
 *
 * @author kundy
 * @date 2019/6/1 1:35 PM
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        description = "House Blend Coffee";
    }

    @Override
    public double cost() {
        return 1.89;
    }

}
