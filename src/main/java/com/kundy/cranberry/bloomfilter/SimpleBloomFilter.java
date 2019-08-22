package com.kundy.cranberry.bloomfilter;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * 简单布隆过滤器实现
 * <p>
 * 布隆过滤器主要用于判断某几个元素是否存在海量数据中
 *
 * @author kundy
 * @date 2019/8/22 3:50 PM
 */
public class SimpleBloomFilter {

    private BitSet bitSet = new BitSet();

    public void add(String key) {
        bitSet.set(hashOne(key), true);
        bitSet.set(hashTwo(key), true);
        bitSet.set(hashThree(key), true);
    }

    public boolean isContain(String key) {
        if (!bitSet.get(hashOne(key))) {
            return false;
        }
        if (!bitSet.get(hashTwo(key))) {
            return false;
        }
        return bitSet.get(hashThree(key));
    }


    private int hashOne(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    private int hashTwo(String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    private int hashThree(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }

    public static void bloomFilterTest() {
        SimpleBloomFilter bloomFilters = new SimpleBloomFilter();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            bloomFilters.add(i + "");
        }
        long end = System.currentTimeMillis();
        System.out.println("添加元素耗时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("是否含有元素 1 ：" + bloomFilters.isContain(9999999 + ""));
        System.out.println("是否含有元素 400230340：" + bloomFilters.isContain(400230340 + ""));
        end = System.currentTimeMillis();
        System.out.println("通过布隆过滤器判断元素是否存在耗时：" + (end - start) + "ms");
    }

    public static void traditionalWayTest() {
        List<String> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            list.add(i + "");
        }
        long end = System.currentTimeMillis();
        System.out.println("添加元素耗时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("是否含有元素 9999999 ：" + traditionalIsContain(list, 9999999 + ""));
        System.out.println("是否含有元素 400230340：" + traditionalIsContain(list, 400230340 + ""));
        end = System.currentTimeMillis();
        System.out.println("传统方式判断元素是否存在耗时：" + (end - start) + "ms");

    }

    private static boolean traditionalIsContain(List<String> list, String key) {
        for (String s : list) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }

}
