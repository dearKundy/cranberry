package com.kundy.cranberry.systemdesign.designpattern.state.master;

/**
 * @author kundy
 * @date 2019/6/3 3:50 PM
 */
public class HasQuarterState implements State {

    private GumballMachineMaster gumballMachineMaster;

    public HasQuarterState(GumballMachineMaster gumballMachineMaster) {
        this.gumballMachineMaster = gumballMachineMaster;
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
