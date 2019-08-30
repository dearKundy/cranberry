package com.kundy.cranberry.systemdesign.designpattern.factory.factory;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;
import com.kundy.cranberry.systemdesign.designpattern.factory.origin.OperationMul;

/**
 * @author kundy
 * @date 2019/6/19 10:14 AM
 */
public class MulFactory implements IFactory {

    @Override
    public Operation createOperation() {
        return new OperationMul();
    }
}
