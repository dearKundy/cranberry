package com.kundy.cranberry.systemdesign.designpattern.decorator;

/**
 * 【调料】
 *
 * @author kundy
 * @date 2019/6/1 1:43 PM
 */
public class Whip extends CondimentDecorator {

    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + " ,Whip";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.88;
    }

}
