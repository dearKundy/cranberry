package com.kundy.cranberry.systemdesign.designpattern.state;

/**
 * @author kundy
 * @date 2019/6/3 3:23 PM
 */
public class GumballMachine {

    private static final int SOLD_OUT = 0;

    /**
     * 没25分钱
     */
    private static final int NO_QUARTER = 1;

    /**
     * 有25分钱
     */
    private static final int HAS_QUARTER = 2;

    private static final int SOLD = 3;

    private int state = SOLD_OUT;

    /**
     * 机器内糖果数目
     */
    private int count = 0;

    public GumballMachine(int count) {
        this.count = count;
        // 如果库存不为零，机器就会进入"没有25分钱"状态，等待别人投入25分钱
        if (count > 0) {
            state = NO_QUARTER;
        }
    }

    /**
     * 顾客投币
     */
    public void insertQuarter() {
        if (state == HAS_QUARTER) {
            System.out.println("You can't insert another quarter");
        } else if (state == NO_QUARTER) {
            state = HAS_QUARTER;
            System.out.println("You insert a quarter,the machine is sold out");
        } else if (state == SOLD_OUT) {
            System.out.println("You can't insert a quarter,the machine is sold out");
        } else if (state == SOLD) {
            System.out.println("Please wait,we're already giving you a gumball");
        }
    }

    /**
     * 顾客退回25分钱
     */
    public void ejectQuarter() {
        if (state == HAS_QUARTER) {
            System.out.println("Quarter returned");
            state = NO_QUARTER;
        } else if (state == NO_QUARTER) {
            System.out.println("You haven't inserted a quarter");
        } else if (state == SOLD) {
            System.out.println("Sorry,you already turned the crank");
        } else if (state == SOLD_OUT) {
            System.out.println("You can't eject,you haven't inserted a quarter yet");
        }
    }

    /**
     * 转动曲柄
     */
    public void turnCrank() {
        if (state == SOLD) {
            System.out.println("Turning twice doesn't yet you another gumball");
        } else if (state == NO_QUARTER) {
            System.out.println("You turned but there's no quarter");
        } else if (state == SOLD_OUT) {
            System.out.println("You turned,but there are no gunballs");
        } else if (state == HAS_QUARTER) {
            System.out.println("You turned...");
            state = SOLD;
            dispense();
        }
    }

    /**
     * 发放糖果
     */
    private void dispense() {
        if (state == SOLD) {
            System.out.println("A gumball comes rolling out the slot");
            count -= 1;
            if (count == 0) {
                System.out.println("Oops,out of gumballs!");
                state = SOLD_OUT;
            } else {
                state = NO_QUARTER;
            }
        } else if (state == NO_QUARTER) {
            System.out.println("You need to pay first");
        } else if (state == SOLD_OUT) {
            System.out.println("No gumball dispensed");
        } else if (state == HAS_QUARTER) {
            System.out.println("No gumball dispensed");
        }
    }

    @Override
    public String toString() {
        return "GumballMachine{" +
                "state=" + state +
                ", count=" + count +
                '}';
    }

}
