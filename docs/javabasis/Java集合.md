
## List
List 可以根据索引随机访问元素。
- `线程不安全`：
    - `ArrayList`：基于数组实现。
    - `LinkedList`：基于链表实现。
- `线程安全`：
    - `Vector`：即使需要保证List集合线程安全，也同样不推荐使用Vector实现类。（直接加锁）
    - `Stack`：Stack 是 Vector的一个子类，用于模拟 `栈` 这种数据结构。它也是一个非常古老的类，如果程序需要使用“栈”这种数据结构，则可以考虑使用 `ArrayDeque`。
    - `CopyOnWriteArrayList`：通过 `写时复制` 实现写加锁，读不用加锁。

## Queue
通常队列不允许随机访问队列中的元素。
- `线程不安全`
    - `PriorityQueue`（Queue的实现类）：元素加入队列时，会按队列元素大小进行重新排序。
    - `Deque接口`（Queue的子接口）：`双端队列`，可同时从两端添加、删除元素，Deque的实现类既可以当成 `队列` 使用（通过offer（入队），poll（出队）），也可以当成 `栈` 使用（通过pop(出栈)、push(入栈)）。
        - `ArrayDeque`（Deque实现类）：基于数组实现。
        - `LinkedList`（Deque实现类）：基于链表实现。
- `线程安全`
    1. `非阻塞队列`
        - `ConcurrentLinkedQueue`：是一个基于链接节点的 `无界非阻塞线` 程安全队列。采用 `CAS` 保障线程安全。`size()` 方法是要遍历一遍集合，慎用。
        - `ConcurrentLinkedDeque`：是一个基于链接节点的 `无界非阻塞线` 程安全双端队列。
        
    2. `阻塞队列`
    Java5提供了一个 `BlockingQueue` 接口，它是 `Queue` 的子接口，但它的主要用途并不是作为容器，而是作为 `线程同步` 的工具，使用阻塞队列可以大大降低多线程环境下的编程难度。
        - `ArrayBlockingQueue`：是一个 `有界` 阻塞队列，初始化的时候必须要指定队列长度，且指定长度之后不允许进行修改。
        - `LinkedBlockingQueue`：LinkedBlockingQueue 基于链表的 `先进先出` 的阻塞队列。可以指定容量，也可以不指定，不指定的话，默认最大是Integer.MAX_VALUE。（不指定容量时可以理解为 `无界` 队列）
        - `LinkedBlockingDeque`：LinkedBlockingDeque 是基于链表的、线程安全的 `双端` 阻塞队列。（不指定容量时可以理解为 `无界` 队列）
        - `PriorityBlockingQueue`：加入元素按大小排序的 `无界` 阻塞队列。
        - `DelayQueue`：队列中的元素到达延迟时间时才会被取出的 `无界`  阻塞队列。队列元素会按照最终执行时间在队列中进行排序，队列元素则需要实现 Delayed 接口。
        - `SynchronousQueue`：SynchronousQueue `没有存储功能` ，因此 put 和 take 会一直阻塞，直到有另一个线程已经准备好参与到交付过程中。仅当有足够多的消费者，并且总是有一个消费者准备好获取交付的工作时，才适合使用同步队列。（相当于生产者直接把元素交给消费者，而不是放在队列中让生产者去取）
        - `LinkedTransferQueue`：
            - `LinkedTransferQueue` 是 `SynchronousQueue` 和 `LinkedBlockingQueue` 的合体，性能比 `LinkedBlockingQueue` 更高（没有锁操作），比 `SynchronousQueue` 能存储更多的元素。
            - 当 `put` 时，如果有等待的线程，就直接将元素 “交给” 等待者，否则直接进入队列。
            - `put` 和 `transfer` 方法的区别是，`put` 是立即返回的， `transfer` 是阻塞等待消费者拿到数据才返回。`transfer` 方法和 `SynchronousQueue` 的 `put` 方法类似。
        
- `Queue 常用方法`：
    - 定义在 `Queue` 中的有：
        - `offer()`：将指定元素加入次队列的队尾。
        - `poll()`：获取队列头部的元素，并删除该元素。
        - `peek()`：获取队列头部元素，但是不删除该元素。
    - 定义在 `Deque` 中的有：
        - `push()`：将一个元素push进该双端列队所表示的栈的栈顶。
        - `pop()`：pop出该双端队列的最后一个元素。 

## Map
- `线程不安全`：
    - `HashMap`：
        1. 在 `JDK1.7` 中，`HashMap` 的数据结构是：`数组+链表` 。
        2. 在 `JDK1.8` 中，`HashMap` 的数据结构是：`数组+链表+红黑树` 。
        3. HashMap 不保证遍历的顺序和插入的顺序是一致的。（不保证元素插入先后顺序）
    - `LinkedHashMap`：
        1. 它是 HashMap 的一个子类，但是它重新定义了数组中保存的元素 Entry，该 Entry 除了保存当前对象的引用外，还保存了其上一个元素 before 和下一个元素 after 的引用，从而在 `哈希表的基础上又构成了双向链接列表`。
        2. 从而保证了遍历的顺序和插入的顺序是一致的。（保证元素插入先后顺序）
    - `TreeMap`：实现了 `SortedMap` 接口，是一个 `红黑树` 数据结构，可以保证所有的Entry处于有序状态。（保证元素有序）
    - `WeakHashMap`：跟 HashMap 基本相似，但 WeakHashMap 的 `key` 只保留了对实际对象的 `弱引用` 。
    - `IdentityHashMap`：这个Map实现类的实现机制与HashMap基本相似，IdentityHashMap判断两个key是否相等是根据:（key1 == key2），而HashMap：只要key1和key2通过equals()方法比较返回true，且它们的hashCode值相等即可。
    - `EnumMap`：EnumMap中所有key都必须是单个枚举类的枚举值，但它只能使用同一个枚举类的枚举值作为key。
- `线程安全`：
    - `HashTable`：粗暴的直接套上一层 `synchronize`，效率不高。
    - `ConcurrentHashMap`：使用 `分段锁` 提高并发度，多个线程对多个不同的段的操作是不会相互影响的。
    - `ConcurrentSkipListMap`：ConcurrentSkipListMap 和 TreeMap，它们虽然都是有序的哈希表。但是 `ConcurrentSkipListMap` 不是在 `TreeMap` 的基础上简单的加锁，甚至也不是使用红黑树实现，而是使用跳跃表。`TreeMap` 是一棵红黑树，无锁实现的复杂性很高，一般需要加锁。 

## Set
Set 和 Map 的关系什么密切，Java就是先实现了 HashMap、TreeMap 等集合，然后通过包装一个所有的 value 都为 null 的 Map 集合实现了 Set 集合类。（通过Map的key的唯一性，确保了Set元素的唯一性）
- `线程不安全`：
    - `HashSet`：
    - `LinkedHashSet`：
    - `TreeSet`：
    - `EnumSet`：
- `线程安全`：
    - `ConcurrentSkipListSet`：
    - `CopyOnWriteArraySet`

## CopyOnWriteArrayList详解
- `CopyOnWriteArrayList` 使用了一种叫`写时复制`的方法，当有 `新元素添加` 到CopyOnWriteArrayList时，先从原有的数组中 `拷贝`一份出来，然后在`新的数组做写操作`，`写完之后`，再将原来的数组引用 `指向` 到新数组。
- CopyOnWriteArrayList 的整个 add 操作都是在 `锁` 的保护下进行的。这样做是为了避免在多线程并发add的时候，复制出多个副本出来,把数据搞乱了，导致最终的数组数据不是我们期望的。
- 可见，CopyOnWriteArrayList的读操作是可以不用加锁的。

参考文章：[线程安全的CopyOnWriteArrayList介绍](https://blog.csdn.net/linsongbin1/article/details/54581787)

## WeakHashMap 与 HashMap 的区别
WeakHashMap与HashMap的用法基本相似。区别在于，HashMap的key保留了对实际对象的强引用，这就意味着只要该HashMap对象不被销毁，该hashMap的所有key所引用的对象就不会被垃圾回收，HashMap也不会自动删除这些key所对应的key-value对。但WeakHashMap的key只保留了对实际对象的弱引用，这意味着如果WeakHashMap对象的key所引用的对象没有被其他强引用变量所引用，则这些key所引用的对象可能被垃圾回收。WeakHashMap也可能自动删除这些key所对应的key-value对。当垃圾回收了该key所对应的实际对象之后，WeakHashMap会自动删除该key对应的key-value对。

## ArrayList在多线程环境中会出现什么问题
`add`操作：
可以看到add元素时，实际做了两个大的步骤：
1. 判断elementData数组容量是否满足需求
2. 在elementData对应位置上设置值

这样也就出现了第一个导致线程不安全的隐患，在多个线程进行add操作时可能会导致elementData数组越界。具体逻辑如下：
1. 列表大小为9，即size=9
2. 线程A开始进入add方法，这时它获取到size的值为9，调用ensureCapacityInternal方法进行容量判断。
3. 线程B此时也进入add方法，它获取到size的值也为9，也开始调用ensureCapacityInternal方法。
4. 线程A发现需求大小为10，而elementData的大小就为10，可以容纳。于是它不再扩容，返回。
5. 线程B也发现需求大小为10，也可以容纳，返回。
6. 线程A开始进行设置值操作， elementData[size++] = e 操作。此时size变为10。
7. 线程B也开始进行设置值操作，它尝试设置elementData[10] = e，而elementData没有进行过扩容，它的下标最大为9。于是此时会报出一个数组越界的异常ArrayIndexOutOfBoundsException.

## HashMap在多线程环境中会出现什么问题
- `插入数据`：现在假如A线程和B线程同时进入addEntry，然后计算出了**相同的哈希值**对应了相同的数组位置，因为此时该位置还没数据，然后对同一个数组位置调用createEntry，两个线程会同时得到现在的头结点，然后A写入新的头结点之后，B也写入新的头结点，那B的写入操作就会覆盖A的写入操作造成A的写入操作丢失。

## SkipList 是什么
![WechatIMG526.jpeg](https://i.loli.net/2019/08/31/W8kAGFrKuyaHLop.jpg)
是一种可以替代 `平衡树` 的数据结构，其数据元素默认按照key值升序，天然有序。SkipList有着不低于红黑树的效率，但是其原理和实现的复杂度要比红黑树简单多了。
1. 将链表中的某些元素提炼出来作为一个“索引”。
2. 由很多层结构组成，level是通过一定的概率随机产生的。
3. 每一层都是一个有序的链表，默认是升序。
4. 最底层(Level 1)的链表包含所有元素。
5. 如果一个元素出现在Level i 的链表中，则它在Level i 之下的链表也都会出现。
6. 通过索引来比较来决定下一个元素是 `往右` 还是 `下走` 。

跟数据库索引有点相似，但是数据库索引是B+树，层数会少很多。

参考文章：
- [【死磕Java并发】-----J.U.C之Java并发容器：ConcurrentSkipListMap](https://blog.csdn.net/chenssy/article/details/75000701)

## BlockingQueue 常见方法
BlockingQueue 提供如下两个支持 `阻塞` 的方法：
- `put(E e)`：尝试把E元素放入BlockingQueue中，如果该队列的元素已满，则阻塞该线程。
- `take()`：尝试从 BlockingQueue 的头部取出元素，如果该队列的元素已空，则阻塞该线程。

BlockingQueue 继承了Queue接口，当然也可以使用 Queue 接口中的方法。这些方法归纳起来可分为如下三组。
- 在队列尾部插入元素。包括 `add(E e)`、`offer(E e)`、`put(E e)` 方法，当队列已满时，这三个方法分别会 `抛出异常`、`返回false`、`阻塞队列`。
- 在队列头部删除并返回元素。包括 `remove()`、`poll()`、`take()` 方法。当队列已空时，这三个方法分别会 `抛出异常`、`返回false`、`阻塞队列`。
- 在队列头部 `取出但不删除` 元素。包括 `element()`和 `peek()`方法，当队列已空时，这两个方法分别 `抛出异常`、`返回false`。

## ConcurrentHashMap
> ConcurrentHashMap 内部使用段（Segment）来表示这些不同的部分，每个段其实就是一个小的 HshTable，它们有自己的锁。只要多个修改操作发生在不同的段上，它们就可以并发进行。这也是在多线程场景时减小锁的粒度从而降低锁竞争的一种方案。