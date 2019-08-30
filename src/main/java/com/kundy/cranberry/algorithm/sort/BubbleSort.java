package com.kundy.cranberry.algorithm.sort;

/**
 * 冒泡排序
 * <p>
 * 当前最大或者最小的数会一直往上冒泡。。。【一直做交换，所以一直冒泡】
 *
 * @author kundy
 * @date 2019/8/15 12:29 PM
 */
public class BubbleSort implements Sort {


    @Override
    public void go(Integer[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] < array[j + 1]) {
                    this.swap(array, j, j + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{5, 2, 3, 1, 4};
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.go(array);
        bubbleSort.print(array);
    }

}
