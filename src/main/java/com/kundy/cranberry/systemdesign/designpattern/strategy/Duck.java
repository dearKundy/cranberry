package com.kundy.cranberry.systemdesign.designpattern.strategy;

/**
 * @author kundy
 * @date 2019/5/31 4:28 PM
 */
public abstract class Duck {

    /**
     * 将两个类结合起来使用，这就是组合。这种做法和"继承" 不同的地方在于，
     * 鸭子的行为不是继承来的，而是和适当的行为对象"组合"来的。
     * 使用组合建立系统具有很大的弹性，不仅可将算法族封装成类，还可以在运行时动态改变行为（通过 setter 方法）
     */
    private Fly fly;
    private Quack quack;

    public Duck(Fly fly, Quack quack) {
        this.fly = fly;
        this.quack = quack;
    }

    public void performFly() {
        fly.fly();
    }

    public void performQuack() {
        quack.quack();
    }

    /**
     * 每个鸭子的子类都必须实现自己的display方法
     */
    public abstract void display();

    /**
     * 每个鸭子的子类都必须实现自己的display方法
     */
    public void swim() {
        System.out.println("All ducks float,even decoys");
    }

}
