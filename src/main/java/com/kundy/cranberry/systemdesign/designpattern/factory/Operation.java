package com.kundy.cranberry.systemdesign.designpattern.factory;

/**
 * @author kundy
 * @date 2019/6/18 5:21 PM
 */
public abstract class Operation {

    private double value1 = 0;
    private double value2 = 0;

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public abstract double getResult();

}
