package com.kundy.cranberry.systemdesign.designpattern.adapter;

/**
 * @author kundy
 * @date 2019/6/2 2:11 PM
 * <p>
 * 适配器是实现 Duck 接口，因为最终是把 火鸡 当做 鸭子 来使用
 * 适配器需要保存 火鸡 实例，在适配器中对 Duck 接口的实现实际上调的是 火鸡 的方法
 *
 */
public class TurkeyAdapter implements Duck {

    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        for (int i = 0; i < 5; i++) {
            turkey.fly();
        }
    }

}
