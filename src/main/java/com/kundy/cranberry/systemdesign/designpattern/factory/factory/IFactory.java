package com.kundy.cranberry.systemdesign.designpattern.factory.factory;


import com.kundy.cranberry.systemdesign.designpattern.factory.Operation;

/**
 * @author kundy
 * @date 2019/6/19 10:10 AM
 */
public interface IFactory {

    Operation createOperation();
}
