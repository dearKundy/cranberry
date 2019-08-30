package com.kundy.cranberry.systemdesign.designpattern.strategy;

/**
 * @author kundy
 * @date 2019/5/31 4:34 PM
 */
public class MallardDuck extends Duck {

    /**
     * 动态传入Fly与quack的具体行为（实现）
     */
    public MallardDuck(Fly fly, Quack quack) {
        super(fly, quack);
    }

    @Override
    public void display() {
        System.out.println("I am a real Mallard duck");
    }

}
