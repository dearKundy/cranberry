package com.kundy.cranberry.systemdesign.designpattern.singleton;

/**
 * @author kundy
 * @date 2019/6/3 2:01 PM
 */
public class SingletonSix {

    private static class SingletonHolder {
        private static final SingletonSix INSTANCE = new SingletonSix();
    }

    private SingletonSix() {
    }

    public static SingletonSix getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
