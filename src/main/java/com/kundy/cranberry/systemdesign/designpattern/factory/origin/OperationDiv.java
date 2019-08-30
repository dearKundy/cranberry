package com.kundy.cranberry.systemdesign.designpattern.factory.origin;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;

/**
 * @author kundy
 * @date 2019/6/18 5:23 PM
 */
public class OperationDiv extends Operation {

    @Override
    public double getResult() {
        if (getValue2() != 0) {
            return getValue1() / getValue2();
        }
        throw new IllegalArgumentException("除数不能为零");
    }

}