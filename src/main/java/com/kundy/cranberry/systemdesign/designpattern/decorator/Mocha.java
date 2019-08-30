package com.kundy.cranberry.systemdesign.designpattern.decorator;

/**
 * 【调料】
 *
 * @author kundy
 * @date 2019/6/1 1:37 PM
 * CondimentDecorator 也是继承于Beverage
 */
public class Mocha extends CondimentDecorator {

    /**
     * 把需要被装饰的对象保存到装饰器对象中
     */
    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        // 饮料的描述 + 调料的描述
        return beverage.getDescription() + ",Mocha";
    }

    @Override
    public double cost() {
        // 调料的价格 + 饮料的价格
        return .20 + beverage.cost();
    }

}
