package com.kundy.cranberry.systemdesign.designpattern.state.master;

/**
 * @author kundy
 * @date 2019/6/3 3:51 PM
 */
public class NoQuarterState implements State {

    private GumballMachineMaster gumballMachineMaster;

    public NoQuarterState(GumballMachineMaster gumballMachineMaster) {
        this.gumballMachineMaster = gumballMachineMaster;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You insert a quarter");
        gumballMachineMaster.setState(gumballMachineMaster.getHasQuarterState());
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
