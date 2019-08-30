package com.kundy.cranberry.systemdesign.designpattern.factory.factory;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;
import com.kundy.cranberry.systemdesign.designpattern.factory.origin.OperationDiv;

/**
 * @author kundy
 * @date 2019/6/19 10:12 AM
 */
public class DivFactory implements IFactory {

    @Override
    public Operation createOperation() {
        return new OperationDiv();
    }

}
