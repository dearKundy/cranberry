ThreadLocal 源码分析就看 set 和 get 方法就可以了。

我们知道 ThreadLocal 变量是每个线程都有单独的一份的，所以 ThreadLocal 实际上是放在当前 Thread 对象中存储的。
一个 Thread 对象可能会存在多个 ThreadLocal 对象，所以用的是 Map（实际是ThreadLocalMap） 数据结构存储。
key 为 当前 ThreadLocal 对象本身的hashcode（实际是threadLocalHashCode），value 为 ThreadLocal set 进去的对象。

## ThreadLocal 为什么会内存泄漏

ThreadLocalMap使用ThreadLocal的弱引用作为key，如果一个ThreadLocal没有外部强引用来引用它，那么系统 GC 的时候，
这个ThreadLocal势必会被回收，这样一来，ThreadLocalMap中就会出现key为null的Entry，就没有办法访问这些key为null的Entry的value，
如果当前线程再迟迟不结束的话，这些key为null的Entry的value就会一直存在一条强引用链：Thread Ref -> Thread -> ThreaLocalMap -> Entry -> value永远无法回收，造成内存泄漏。

其实，ThreadLocalMap的设计中已经考虑到这种情况，也加上了一些防护措施：在ThreadLocal的get(),set(),remove()的时候都会清除线程ThreadLocalMap里所有key为null的value。

但是这些被动的预防措施并不能保证不会内存泄漏：

- 使用static的ThreadLocal，延长了ThreadLocal的生命周期，可能导致的内存泄漏（参考ThreadLocal 内存泄露的实例分析）。
- 分配使用了ThreadLocal又不再调用get(),set(),remove()方法，那么就会导致内存泄漏。

## 为什么使用弱引用
- key 使用强引用：引用的ThreadLocal的对象被回收了，但是ThreadLocalMap还持有ThreadLocal的强引用，如果没有手动删除，ThreadLocal不会被回收，导致Entry内存泄漏。
- key 使用弱引用：引用的ThreadLocal的对象被回收了，由于ThreadLocalMap持有ThreadLocal的弱引用，即使没有手动删除，ThreadLocal也会被回收。value在下一次ThreadLocalMap调用set,get，remove的时候会被清除。


每次使用完ThreadLocal，都调用它的remove()方法，清除数据。

参考文章
- [深入分析 ThreadLocal 内存泄漏问题](https://www.jianshu.com/p/1342a879f523)
