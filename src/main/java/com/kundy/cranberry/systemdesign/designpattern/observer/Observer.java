package com.kundy.cranberry.systemdesign.designpattern.observer;

/**
 * 观察者
 *
 * @author kundy
 * @date 2019/6/1 12:16 PM
 */
public interface Observer {

    /**
     * 当气象观测值改变时，主题会把这些状态值当作方法的参数，传递给观察者
     */
    void update(float temp, float humidity, float pressure);

}
