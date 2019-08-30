package com.kundy.cranberry.javabasis.spi;

/**
 * @author kundy
 * @date 2019/8/27 6:04 PM
 */
public class Dog implements Animal {

    @Override
    public void shout() {
        System.out.println("wang wang");
    }
}
