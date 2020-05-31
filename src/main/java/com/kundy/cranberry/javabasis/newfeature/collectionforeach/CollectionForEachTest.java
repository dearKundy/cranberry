package com.kundy.cranberry.javabasis.newfeature.collectionforeach;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Java 8 Collection forEach() 用法
 *
 * @author kundy
 * @date 2019/8/29 12:20 PM
 */
public class CollectionForEachTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("matt", "john", "gray");

        // 使用 for loop
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // 使用 for-each loop
        for (String s : list) {
            System.out.println(s);
        }

        /*
         * 使用 Java 8 forEach()
         * 查看源码可以看到：forEach 就是将集合元素作为参数，循环调用 Consumer 实现类的 accept 方法。
         */
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        // 使用 Java 8 forEach() 配搭 Lambda 语法
        list.forEach(s -> System.out.println(s));

        // 使用 Java 8 forEach() 配搭 Lambda 即 Method References 语法，相当于 s -> System.out.println(s)
        list.forEach(System.out::println);

    }
}
