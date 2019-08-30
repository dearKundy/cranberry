package com.kundy.cranberry.systemdesign.designpattern.factory.factory;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;
import com.kundy.cranberry.systemdesign.designpattern.factory.origin.OperationSub;

/**
 * @author kundy
 * @date 2019/6/19 10:16 AM
 */
public class SubFactory implements IFactory {

    @Override
    public Operation createOperation() {
        return new OperationSub();
    }
}
