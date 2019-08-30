package com.kundy.cranberry.systemdesign.designpattern.strategy;

/**
 * @author kundy
 * @date 2019/5/31 4:34 PM
 */
public class MiniDuckSimulator {

    public static void main(String[] args) {
        // 动态指定 MallardDuck 的行为，具体的行为从算法族中取
        Duck mallard = new MallardDuck(new FlyWithWings(), new Squeak());
        mallard.performFly();
        mallard.performQuack();
    }

}
