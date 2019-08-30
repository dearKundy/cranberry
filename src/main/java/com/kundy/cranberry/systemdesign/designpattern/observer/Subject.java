package com.kundy.cranberry.systemdesign.designpattern.observer;


/**
 * 主题
 *
 * @author kundy
 * @date 2019/6/1 12:08 PM
 */
public interface Subject {

    /**
     * 注册观察者
     */
    void registerObserver(Observer observer);

    /**
     * 移除观察者
     */
    void removeObserver(Observer observer);

    /**
     * 当主题状态改变时，通知所有的观察者
     */
    void notifyObservers();

}
