package com.kundy.cranberry.systemdesign.designpattern.factory.origin;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;

/**
 * @author kundy
 * @date 2019/6/18 5:23 PM
 */
public class OperationSub extends Operation {

    @Override
    public double getResult() {
        return getValue1() - getValue2();
    }

}