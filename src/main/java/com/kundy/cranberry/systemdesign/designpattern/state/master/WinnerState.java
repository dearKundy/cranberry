package com.kundy.cranberry.systemdesign.designpattern.state.master;


import com.kundy.cranberry.systemdesign.designpattern.state.GumballMachine;

/**
 * @author kundy
 * @date 2019/6/3 3:52 PM
 */
public class WinnerState implements State {

    private GumballMachine gumballMachine;

    public WinnerState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {

    }

    @Override
    public void ejectQuarter() {

    }

    @Override
    public void turnCrank() {

    }

    @Override
    public void dispense() {

    }

}
