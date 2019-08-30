package com.kundy.cranberry.systemdesign.designpattern.adapter;

/**
 * @author kundy
 * @date 2019/6/2 2:14 PM
 */
public class DuckTestDrive {

    public static void main(String[] args) {

        MallarDuck duck = new MallarDuck();

        WildTurkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

        System.out.println("The Turkey says...");
        turkey.gobble();
        turkey.fly();

        System.out.println("The Duck syas...");
        testDuck(duck);

        System.out.println("The TurkeyAdapter says...");
        testDuck(turkeyAdapter);
    }

    static void testDuck(Duck duck) {
        duck.quack();
        duck.fly();
    }

}
