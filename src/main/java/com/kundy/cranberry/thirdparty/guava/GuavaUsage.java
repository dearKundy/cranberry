package com.kundy.cranberry.thirdparty.guava;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

/**
 * guava 常用方法
 * <p>
 * https://www.jianshu.com/p/97778b21bd00
 *
 * @author kundy
 * @date 2020/5/30 8:27 PM
 */
public class GuavaUsage {

    // -------------------------------- Joiner/Splitter --------------------------------

    /**
     * 连接器
     */
    private static final Joiner JOINER = Joiner.on(",").skipNulls();
    private static final Joiner JOINER_1 = Joiner.on(",").useForNull("default");

    /**
     * 分割器
     */
    private static final Splitter SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

    public static void main(String[] args) {
        usageNine();
    }

    /**
     * 以面向对象思想处理字符串:Joiner/Splitter
     * <p>
     * Joiner是连接器，Splitter是分割器，通常我们会把它们定义为static final，
     * 利用on生成对象后在应用到String进行处理，这是可以复用的。
     */
    private static void usageOne() {

        String join = JOINER.join(Lists.newArrayList("a", null, "b"));
        System.out.println(String.format("join=%s", join));

        Iterable<String> split = SPLITTER.split(" a,     ,b,,");

        for (String s : split) {
            System.out.println(String.format("|%s|", s));
        }

    }

    // -------------------------------- CharMatcher --------------------------------

    private static final CharMatcher CHAR_MATCHER_DIGIT = CharMatcher.digit();
    private static final CharMatcher CHAR_MATCHER_ANY = CharMatcher.any();

    private static void usageTwo() {

        // 只保留匹配的字符，其他移除
        System.out.println(CHAR_MATCHER_DIGIT.retainFrom("abe2def123f!"));

        // 移除匹配的字符
        System.out.println(CHAR_MATCHER_DIGIT.removeFrom("yes,i love u 1234"));

        System.out.println(CharMatcher.inRange('a', 'f').or(CharMatcher.is('n')).replaceFrom("kundy", "*"));

    }

    // -------------------------------- 对基本类型进行支持 --------------------------------

    /**
     * guava 提供了 Bytes/Shorts/Ints/Iongs/Floats/Doubles/Chars/Booleans 这些基本数据类型的扩展支持
     */
    private static void usageThree() {

        // 快速完成到集合的转换
        List<Integer> list = Ints.asList(1, 2, 3, 4, 5, 6);

        System.out.println(Ints.join(",", 1, 3, 1, 4));

        // 原生类型数组快速合并
        int[] newIntArray = Ints.concat(new int[]{1, 2}, new int[]{2, 3, 4});
        System.out.println(newIntArray.length);

        // 最大/最小
        System.out.println(Ints.max(newIntArray) + "," + Ints.min(newIntArray));

        // 是否包含
        System.out.println(Ints.contains(newIntArray, 6));

        // 集合到数组的转换
        int[] ints = Ints.toArray(list);

    }

    // -------------------------------- 对JDK集合的有效补充【Multiset】 --------------------------------

    /**
     * Multiset就是无序的，但是可以重复的集合
     */
    private static void usageFour() {

        Multiset<String> multiset = HashMultiset.create();

        HashMultiset.create();
//        HashMultiset.create(new ArrayList<>());

        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");

        System.out.println(multiset.size());
        // 跟踪每个对象的数量
        System.out.println(multiset.count("a"));

    }

    // -------------------------------- 对JDK集合的有效补充 Immutable vs unmodifiable --------------------------------

    /**
     * guava提供了很多Immutable集合，比如 ImmutableList/ImmutableSet/ImmutableSortedSet/ImmutableMap/......
     */
    private static void usageFive() {

        // 先看一个unmodifiable的例子
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        // 这种视图不够安全，不是真正意义上的快照（也即是说我们改变源集合，导致不可变视图（unmodifiable View）也会发生变化）
        List<String> readOnlyList = Collections.unmodifiableList(list);

        list.add("c");

        System.out.println(readOnlyList.size());

        // 不使用 guava 的解决办法
        List<String> readOnlyList2 = Collections.unmodifiableList(new ArrayList<>(list));

        // 使用 guava 的解决办法
        ImmutableList<String> immutableList = ImmutableList.of("a", "b", "c");

        ImmutableList<String> immutableList1 = ImmutableList.copyOf(list);

        list.add("d");

        // 视图不随着源而改变 guava 只读设置安全可靠
        System.out.println(list.size() + " " + immutableList1.size());

    }

    // -------------------------------- 对JDK集合的有效补充 Multimap --------------------------------

    /**
     * JDK提供给我们的Map是键值一对一的，那么在实际开发中，显然存在一个KEY多个VALUE的情况（比如一个分类下的书本），
     * 我们往往这样表达：Map<k,List<v>>，好像有点臃肿
     * <p>
     * Multimap的实现类有：ArrayListMultimap/HashMultimap/LinkedHashMultimap/TreeMultimap/ImmutableMultimap/.....
     * https://blog.csdn.net/yaomingyang/article/details/80955872
     */
    private static void usageSix() {

        Multimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("key1", "value1");
        multimap.put("key1", "value2");
        multimap.put("key1", "value2");
        multimap.put("key2", "value3");

        System.out.println(multimap.get("key1"));

    }

    // -------------------------------- 对JDK集合的有效补充 BiMap --------------------------------

    /**
     * find key by value
     * <p>
     * 实际上，当你创建BiMap的时候，在内部维护了2个map，一个forward map，一个backward map
     * <p>
     * biMap.inverse()  != biMap ； biMap.inverse().inverse() == biMap
     */
    private static void usageSeven() {

        BiMap<String, String> biMap = HashBiMap.create();

        biMap.put("name", "kundy");

        // value 重复会报错
        biMap.put("nick", "kundy");

        // 强制覆盖
        biMap.forcePut("nick", "kundy");

        biMap.put("123", "kundy@163.com");

        // result=123
        System.out.println(biMap.inverse().get("kundy@163.com"));

    }

    // -------------------------------- 对JDK集合的有效补充 Table --------------------------------

    /**
     * Map<k1,Map<k2,v2>>
     */
    private static void usageEight() {

        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("张三", "计算机", 80);
        table.put("张三", "数学", 90);
        table.put("张三", "语文", 70);
        table.put("李四", "计算机", 70);
        table.put("李四", "数学", 60);
        table.put("李四", "语文", 50);

        // 最小单位 cell
        Set<Table.Cell<String, String, Integer>> cells = table.cellSet();
        for (Table.Cell<String, String, Integer> cell : cells) {
            System.out.println(cell.getRowKey() + "," + cell.getColumnKey() + "," + cell.getValue());
        }

        // row set
        Set<String> rowSet = table.rowKeySet();
        System.out.println(rowSet);

        // column set
        Set<String> columnKeySet = table.columnKeySet();
        System.out.println(columnKeySet);

        // 根据 rowKey 获得信息
        System.out.println(table.row("张三"));

        // 根据 column 获得信息
        System.out.println(table.column("计算机"));

    }

    // -------------------------------- Functions --------------------------------

    private static void usageNine() {

        ArrayList<String> list = Lists.newArrayList("helloword", "yes", "kundy");

        Function<String, String> function1 = new Function<String, String>() {

            @Override
            public String apply(@Nullable String input) {
                return input.length() <= 5 ? input : input.substring(0, 5);
            }

        };

        Function<String, String> function2 = new Function<String, String>() {

            @Override
            public String apply(@Nullable String input) {
                return input.toUpperCase();
            }

        };

        Function<String, String> function3 = Functions.compose(function1, function2);

        Collection<String> transform = Collections2.transform(list, function3);
        for (String s : transform) {
            System.out.println(s);
        }

    }

}
