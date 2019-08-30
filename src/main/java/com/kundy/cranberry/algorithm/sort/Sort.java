package com.kundy.cranberry.algorithm.sort;

/**
 * @author kundy
 * @date 2019/8/15 1:56 PM
 */
public interface Sort {

    void go(Integer[] array);

    /**
     * 交换值
     */
    default void swap(Integer[] array, Integer num1, Integer num2) {
        Integer tmp = array[num1];
        array[num1] = array[num2];
        array[num2] = tmp;
    }

    /**
     * 打印数组
     */
    default void print(Integer[] array) {
        for (Integer integer : array) {
            System.out.print(integer + " ");
        }
    }

}
