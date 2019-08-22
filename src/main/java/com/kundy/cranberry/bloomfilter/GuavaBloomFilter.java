package com.kundy.cranberry.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * guava 中布隆过滤器的使用
 *
 * @author kundy
 * @date 2019/8/22 4:48 PM
 */
public class GuavaBloomFilter {

    public static void main(String[] args) {
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 10000000, 0.01);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("添加元素耗时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(filter.mightContain(9));
        System.out.println(filter.mightContain(99));
        System.out.println(filter.mightContain(999));
        System.out.println(filter.mightContain(9999));
        System.out.println(filter.mightContain(99999));
        System.out.println(filter.mightContain(100000001));
        end = System.currentTimeMillis();
        System.out.println("通过 guava 布隆过滤器判断元素是否存在耗时：" + (end - start) + "ms");

    }
}
