package com.kundy.cranberry.javabasis.newfeature.streamapi;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Java 8 Stream
 * <p>
 * Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
 * 这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。
 * <p>
 * +--------------------+       +------+   +------+   +---+   +-------+
 * | stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
 * +--------------------+       +------+   +------+   +---+   +-------+
 * <p>
 * 以上的流程转换为 Java 代码为：
 * <p>
 * List<Integer> transactionsIds =
 * widgets.stream()
 * .filter(b -> b.getColor() == RED)
 * .sorted((x,y) -> x.getWeight() - y.getWeight())
 * .mapToInt(Widget::getWeight)
 * .sum();
 *
 * @author kundy
 * @date 2019/8/29 11:39 AM
 */
public class StreamApiTest {

    public static void main(String[] args) {
        collectors();
    }

    /**
     * 在 Java 8 中, 集合接口有两个方法来生成流：
     * - stream() − 为集合创建串行流。
     * - parallelStream() − 为集合创建并行流。
     * <p>
     * 对于 普通数组可以使用以下两个方法生成流：
     * - Stream.of();
     * - Arrays.stream();
     */
    public static void generateStream() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println(filtered);
    }

    /**
     * Stream forEach：
     * Stream 提供了新的方法 'forEach' 来迭代流中的每个数据
     * 随机产生 10 个 1-10 的整数
     */
    public static void streamForEach() {
        new Random().ints(1, 11).limit(10).forEach(System.out::println);
    }

    /**
     * map 方法用于映射每个元素到对应的结果，以下代码片段使用 map 输出了元素对应的平方数：
     */
    public static void map() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println(squaresList);
    }

    /**
     * filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串：
     */
    public static void filter() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 获取空字符串的数量
        long count = strings.stream().filter(String::isEmpty).count();
        System.out.println(count);
    }

    /**
     * limit 方法用于获取指定数量的流。
     */
    public static void limit() {
        new Random().ints().limit(10).forEach(System.out::println);
    }

    /**
     * sorted 方法用于对流进行排序
     */
    public static void sorted() {
        new Random().ints().limit(10).sorted().forEach(System.out::println);
    }

    /**
     * Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
     */
    public static void collectors() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("筛选列表: " + filtered);

        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(","));
        System.out.println("合并字符串: " + mergedString);
    }

    /**
     * 统计：
     * 主要用于int、double、long等基本类型上，它们可以用来产生类似如下的统计结果。
     */
    public static void statistics() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics statistics = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + statistics.getMax());
        System.out.println("列表中最小的数 : " + statistics.getMin());
        System.out.println("所有数之和 : " + statistics.getSum());
        System.out.println("平均数 : " + statistics.getAverage());
    }

}
