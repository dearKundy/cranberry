package com.kundy.cranberry.algorithm.sort;

/**
 * 选择排序：
 * 一遍一遍的遍历数组，把最小的元素放在第一位，次小的放在第二位，以此类推。【先标记最小元素位置，比完再交换】
 *
 * @author kundy
 * @date 2019/8/15 11:08 PM
 */
public class SelectSort implements Sort {

    @Override
    public void go(Integer[] array) {

        // i < array.length - 1：最后一个数肯定是最大的，所以最后一个元素不用比较
        for (int i = 0; i < array.length - 1; i++) {
            int currentIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] > array[currentIndex]) {
                    currentIndex = j;
                }
            }
            this.swap(array, i, currentIndex);
        }
    }


    public static void main(String[] args) {
        Integer[] array = new Integer[]{5, 2, 3, 1, 4};
        SelectSort selectSort = new SelectSort();
        selectSort.go(array);
        selectSort.print(array);
    }

}
