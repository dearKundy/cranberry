package com.kundy.cranberry.systemdesign.designpattern.state;

/**
 * @author kundy
 * @date 2019/6/3 3:23 PM
 */
public class GumballMachineTestDrive {

    public static void main(String[] args) {
        GumballMachine gumballMachine = new GumballMachine(5);
        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        System.out.println(gumballMachine);
    }

}
