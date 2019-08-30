package com.kundy.cranberry.systemdesign.designpattern.state.master;

/**
 * @author kundy
 * @date 2019/6/3 3:32 PM
 */
public interface State {

    /**
     * 顾客投币
     */
    void insertQuarter();

    /**
     * 顾客退币
     */
    void ejectQuarter();

    /**
     * 转动曲柄
     */
    void turnCrank();

    /**
     * 发放糖果
     */
    void dispense();

}
