package com.kundy.cranberry.systemdesign.designpattern.decorator;

/**
 * 【调料】：必须让CondimentDecorator（调料装饰者）能够取代Beverage，所以将CondimentDecorator扩展自Beverage类，（而不是为了继承行为）
 *
 * @author kundy
 * @date 2019/6/1 1:29 PM
 */
public abstract class CondimentDecorator extends Beverage {

    /**
     * 所有的调料装饰者都必须重新实现getDescription方法
     */
    @Override
    public abstract String getDescription();

}
