package com.kundy.cranberry.algorithm.sort;

/**
 * 插入排序：
 * 将后面的元素直接插入到前面已经有序的数组中。就像我们打牌，一张一张的摸牌，整理的时候直接插入到正确的位置。（从第二个位置开始比较）
 *
 * @author kundy
 * @date 2019/8/15 2:51 PM
 */
public class InsertSort implements Sort {


    @Override
    public void go(Integer[] array) {

        // 抽出第 i 个数
        for (int i = 1; i < array.length; i++) {
            // 将第 i 个数插到前面去
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                }
                // 如果不需要交换，直接break，因为前面的元素已经有序，如果不 break，时间复杂度还是 n平方
                else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        InsertSort insertSort = new InsertSort();
        insertSort.go(array);
        insertSort.print(array);
    }
}
