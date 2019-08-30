package com.kundy.cranberry.systemdesign.designpattern.factory.simple;

import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;
import com.kundy.cranberry.systemdesign.designpattern.factory.origin.OperationAdd;
import com.kundy.cranberry.systemdesign.designpattern.factory.origin.OperationDiv;
import com.kundy.cranberry.systemdesign.designpattern.factory.origin.OperationMul;
import com.kundy.cranberry.systemdesign.designpattern.factory.origin.OperationSub;

/**
 * @author kundy
 * @date 2019/6/18 5:44 PM
 */
public class OperationFactory {

    public static Operation createOperation(String operation) {
        Operation oper = null;
        switch (operation) {
            case "+":
                oper = new OperationAdd();
                break;
            case "-":
                oper = new OperationSub();
                break;
            case "*":
                oper = new OperationMul();
                break;
            case "/":
                oper = new OperationDiv();
                break;
            default:
                throw new UnsupportedOperationException("不支持该操作");
        }
        return oper;
    }

}
