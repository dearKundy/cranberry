package com.kundy.cranberry.systemdesign.designpattern.decorator;

/**
 * @author kundy
 * @date 2019/6/1 1:28 PM
 */
public abstract class Beverage {

    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();

}
