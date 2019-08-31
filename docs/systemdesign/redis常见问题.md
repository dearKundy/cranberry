### redis内存结构
> 可以把 Redis 理解为一个巨大的 Map。

Redis 的存储是以 `key-value` 的形式的。Redis中的 key 一定是字符串，value 可以是 string、list、hash、set、sortset 这几种常用的。

Redis 使用对象来表示数据库中的键和值。每次我们在Redis 数据库中新创建一个键值对时，至少会创建出两个对象。一个是键对象，一个是值对象。

Redis 中的每个对象都由一个 redisObject 结构来表示：
```c
typedef struct redisObject{

    // 对象的类型
    unsigned type 4:;

    // 对象的编码格式
    unsigned encoding:4;

    // 指向底层实现数据结构的指针
    void * ptr;

    //.....

}robj;
```

#### 数据结构对应的类型与编码
同一种数据类型，有不同的编码，不同的编码对应的底层实现是不一样的。

|类型|编码|对象|
|---|---|---|
|REDIS_STRING|REDIS_ENCODING_INT|使用整数值实现的字符串对象|
|REDIS_STRING|REDIS_ENCODING_EMBSTR|使用embstr编码的简单动态字符串实现的字符串对象|
|REDIS_STRING|REDIS_ENCODING_RAW|使用简单动态字符串实现的字符串对象|
|REDIS_LIST|REDIS_ENCODING_ZIPLIST|使用压缩列表实现的列表对象|
|REDIS_LIST|REDIS_ENCODING_LINKEDLIST|使用双端链表实现的列表对象|
|REDIS_HASH|REDIS_ENCODING_ZIPLIST|使用压缩列表实现的哈希对象|
|REDIS_HASH|REDIS_ENCODING_HT|使用字典实现的哈希对象|
|REDIS_SET|REDIS_ENCODING_INTSET|使用整数集合实现的集合对象|
|REDIS_SET|REDIS_ENCODING_HT|使用字典实现的集合对象|
|REDIS_ZSET|REDIS_ENCODING_ZIPLIST|使用压缩列表实现的有序集合对象|
|REDIS_ZSET|REDIS_ENCODING_SKIPLIST|使用跳跃表和字典实现的有序集合对象|

#### 什么是跳跃表
参考文章：[浅析SkipList跳跃表原理及代码实现](https://blog.csdn.net/ict2014/article/details/17394259)

> Redis 的数据库就是使用字典(哈希表)来作为底层实现的，对数据库的增删改查都是构建在字典(哈希表)的操作之上的


参考文章：[redis从青铜到王者](https://juejin.im/post/5c3c1df2e51d45207f54b189)

### Redis为什么这么快
#### Redis 有多快
> Redis采用的是基于内存的采用的是单进程单线程模型的 KV 数据库，由C语言编写，官方提供的数据是可以达到100000+的QPS（每秒内查询次数）。

#### Redis 为什么这么快
1. 完全基于内存操作。
2. 单线程操作，避免了频繁的上下文切换。也不用考虑各种锁问题。
3. 采用了非阻塞I/O多路复用机制。

#### 为什么单线程性能会更好？
Redis 是基于内存的操作，几乎可以当做是没有 I/O 操作，也就是计算密集型。对于计算密集型的程序，多个线程的切换反而浪费资源，所以单线程足以。

但是，我们使用单线程的方式是无法发挥多核CPU 性能，不过我们可以通过在单机开多个Redis 实例来完善！

因为是单一线程，所以同一时刻只有一个操作在进行，所以，耗时的命令会导致并发的下降，不只是读并发，写并发也会下降。

#### key * ?
redis官方文档是这么说的：
> Warning: consider KEYS as a command that should only be used in production environments with extreme care. It may ruin performance when it is executed against large databases. This command is intended for debugging and special operations, such as changing your keyspace layout. Don’t use KEYS in your regular application code. If you’re looking for a way to find keys in a subset of your keyspace, consider using SCAN or sets.

如果数据庞大的话，执行keys命令，可能需要几秒或更长，由于 redis 是单线程的，这个时候不能处理其他的请求，这对于生产服务器上锁定几秒这绝对是灾难了。

#### 过期策略
> Redis 是 key-value 数据库，我们可以设置 Redis 中缓存的 key 的过期时间。Redis 的过期策略就是指当 Redis 中缓存的 key 过期了，Redis 如何处理。

过期策略通常有以下三种：
- `定时过期`：每个设置过期时间的key都需要创建一个定时器，到过期时间就会立即清除。该策略可以立即清除过期的数据，对内存很友好；但是会占用大量的CPU资源去处理过期的数据，从而影响缓存的响应时间和吞吐量。
- `惰性过期`：只有当访问一个key时，才会判断key是否已过期，过期则清除。该策略可以最大化地节省CPU资源。却对内存非常不友好。极端情况可能出现大量的过期key没有再次被访问，从而不会被清除，占用大量内存。
- `定期过期`：每隔一定的时间，会扫描一定数量的数据库的expires字典中一定数量的key，并清除其中已过期的key。该策略是前两者的一个折中方案。通过调整定时扫描的时间间隔和每次扫描的限定耗时，可以在不同情况使得CPU和内存资源达到最优的平衡效果。

Redis采用的是 惰性删除+定期删除 两种策略，所以说，在 Redis 里边如果过期键到期了过期的时间，未必被立马删除。

PS:expires字典会保存所有设置了过期时间的key的过期时间数据，其中，key是指向键空间中的某个键的指针，value是该键的毫秒精度的UNIX时间戳表示的过期时间。键空间是指该Redis集群中保存的所有键。

一句话：
- 定时过期：立马过期，对CPU不友好，内存友好。
- 惰性过期：用到才删除，对CPU友好，内存不友好。
- 定期过期：上面两种的折中方法。

#### 内存淘汰机制
> Redis 的内存淘汰策略是指在 Redis 的用于缓存的内存不足时，怎么处理需要新写入且需要申请额外空间的数据。

在redis中，允许用户设置最大使用内存大小maxmemory（需要配合 maxmemory-policy  使用），设置为0表示不限制。当 redis 内存数据集快到达 maxmemory 时，redis 会实行数据淘汰策略。

|策略|描述|
|---|---|
|volatile-lru|从已设置过期时间的数据集中挑选最近最少使用的数据淘汰|
|volatile-ttl|从已设置过期时间的数据集中挑选将要过期的数据淘汰|
|volatile-random|从已设置过期时间的数据集中任意选择数据淘汰|
|allkeys-lru|从所有数据集中挑选最少使用的数据淘汰|
|allkeys-random|从所有数据集中任意选择数据进行淘汰|
|noeviction|禁止驱逐数据|

- 关于 maxmemory 设置，通过在 redis.conf 中 maxmemory 参数设置，或者通过命令 CONFIG SET 动态修改。
- 关于数据淘汰策略的设置，通过在 redis.conf 中的 maxmemory-policy 参数设置，或者通过命令CONFIG SET动态修改。

使用 Redis 缓存数据时，为了提高缓存命中率，需要保证缓存数据都是热点数据。可以将内存最大使用量设置为热点数据占用的内存量，然后启用 allkeys-lru 淘汰策略，将最近最少使用的数据淘汰。

应用：如 mysql 里面有 2000w 条数据，redis 只存 20w 条热点数据，如何保证 redis 中的数据都是热点数据？

设置最大内存，在设置淘汰策略，设置 volatile-lru、allkeys-lru 都可以。

#### 持久化
Redis 是基于内存的，如果不想办法将数据保存在硬盘上，一旦 Redis 重启（退出/故障），内存的数据将会全部丢失。如果我们不希望数据丢失，就需要进行持久化。

Redis提供了两种不同的持久化方法来将数据存储到硬盘里：
- RDB（基于快照）：将某一时刻的所有数据保存到一个RDB文件中。
- AOF（append-only-file）：当 Redis 服务器执行写命令的时候，将执行的写命令保存到 AOF 文件中。

##### RDB(快照持久化)
- 官网介绍：在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话说的Snapshot快照，它恢复时是将快照文件直接读到内存里。
- Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能。如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那 RDB 方式要比 AOP 方式 `更高效`。RDB 的缺点是最后一次持久化后的数据可能丢失。

有两个命令可以生成 RDB 文件：
- `SAVE`：会阻塞 Redis 服务器进程，服务器不能就收任何请求，，直到 RDB 文件创建完毕为止。
- `BGSAVE`：创建出一个 `子进程`，由子进程来负责创建 RDB 文件，服务器进程可以继续接受请求。可以通过 `lastsave` 命令获取最后一次成功执行快照的时间。

Rdb 保存的是 dump.rdb 文件。

Redis 服务器在启动的时候，如果发现有 RDB 文件，就会自动载入 RDB 文件（不需要人工干预）。
- 服务器在载入 RDB 文件期间，会处于阻塞状态，直到载入工作完成。

在默认的配置下，如果以下的条件被触发，就会执行`BGSAVE`命令
> save 900 1  #在900秒之后，至少有1个key发生变化
> save 300 10 #300秒之后，至少有10个key发生变化
> save 60 10000 #在60秒之后，至少有10000个key发生变化

##### AOF(Append Only File)
> 以日志的形式来记录每个写操作，将 Redis 执行过的所有写指令记录下来（读操作不记录），只许追加文件但不可以改写文件（`默认每秒写一次`），redis 启动之初会读取该文件重新构建数据，换言之，redis 重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作。（默认关闭）

Aof 保存的是 appendonly.aof 文件。

RDB 与 AOF 可以协同工作，但是优先读取的是 appendonly.aof 文件，如果 appendonly.aof 文件出错了（例如写日志写到一半，断电了），那么 redis 是启动不起来的。

执行以下命令可以修复 appendonly.aof 文件
`redis-check-aof --fix appendonly.aof`

**优势**
- 每修改同步：appendfsync always 同步持久化，每次发生数据变更会被立即记录到磁盘，性能较差但数据完整性较好。
- 每秒同步：appendfsync everysec 异步操作，每秒记录，如果一秒内宕机，有数据丢失。
- 不同步：appendfsync no 从不同步。

**劣势**
- 相同数据集的数据而言 aof 文件要远大于 rdb 文件，恢复速度慢于 rdb。
- aof运行效率要慢于 rdb，每秒同步策略效率较好，不同步效率和 rdb 相同。

##### 重写（Rewrite）
> AOF 采用文件追加方式，文件会越来越大，为避免出现此种情况，新增了重写机制，当 AOF 文件的大小超过所设定的阈值时，Redis 就会启动 AOF 文件的内容压缩（例如将10个incr 1压缩成1个incr 10），只保留可以恢复数据的最小指令集，可以使用命令 `bgrewriteaof`。

**重写原理**
AOF 文件持续增长而过大时，会 `fork` 出一条新进程来将文件重写（也就是先写临时文件最后再 rename ），遍历新进程的内存中数据，每条记录都有一条的 Set 语句。重写 aof 文件的操作，并没有读取旧的 aof 文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的 aof 文件，这点和快照有点类似。

**触发机制**
Redis 会记录上次重写时的 AOF 大小，默认配置时当 AOP 文件大小是上次 rewrite 后大小的一倍且文件大于64M时触发（一般生产起码3G起步）。

#### 持久化策略使用建议
> 同时开启，AOF 为主，RDB 为辅。

在这种情况下，当 redis 重启的时候回优先载入 AOF 文件来回复原始的数据，因为在通常情况下 AOF 文件保存的数据集要比 RDB 文件保存的数据集要完整，RDB的数据不实时，同时使用两者时服务器重启也只会找 AOF 文件。但是 RDB 更适合用于备份数据库（AOF 在不断变化不好备份），快速重启，而且不会有 AOF 可能潜在的 bug，留着作为一个万一的手段。


**总结：**
RDB
- 每隔一段时间备份一次全量的数据，效率高，但是可能会丢失最后一次需要持久化的数据。

AOF
- AOF 默认每隔一秒写一次操作日志，效率稍低，但是只会丢失最后一秒的数据。
- 重写：因为日志会持续增长，所以需要压缩日志文件。

### Redis 的复制（Master/Slave）
> 主从复制，主机数据更新后根据配置和策略，自动同步到备机的 master/slaver 机制，Master 以写为主，Slave 以读为主。

**能干嘛？**
- 读写分离
- 容灾恢复

`info replication`
`SLAVEOF 127.0.0.1 6379`设置主库。
从库只能读，不能写。
主机挂了，从机也不能上位（但是从机可以执行`slaveof no one`，从机就成为新的主机 - 反客为主）。
主机挂了，恢复之后还是主机。
从机挂了，恢复之后就不是从机了（从机每次与 master 断开之后，都需要重新连接，除非你配置进 redis.conf 文件）。

**复制原理**
- Slave 启动成功连接到 master 后会发送一个 sync 命令。
- Master 接收到命令启动后台的存盘进程，同时收集所有就收到的用于修改数据集命令，在后台进程执行完毕之后，master 将传送整个数据文件到 slave，以完成一次完全同步。
- 全量复制：而 slave 服务在接收到数据库文件数据后，将其存盘并加载到内存（首次全量，以后增量）。
- 增量复制：Master 继续将新的所有收集到的修改命令一次传给 Slave，完成同步，但是只要是重新连接master，一次完全同步（全量复制）将被自动执行。

**复制的缺点**
- 复制延时：由于所有的写操作都是先在 Master 上操作，然后同步更新到 Slave 上，所以从 Master 同步到 Slave 机器有一定的延迟，当系统很繁忙的时候，延迟问题会更加严重，Slave 机器数量的增加也会使这个问题更加严重。

**哨兵模式（sentinel）**
一句话：就是反客为主的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转为主库。

操作步骤：
1. 新建 sentinel.conf 配置文件
    - 内容如下
    sentinel monitor 被监控数据库名字（自定义）127.0.0.1 6379 1
    `sentinel monitor host6379 127.0.0.1 6379 1`
    上面最后一个数字1，表示主机挂掉后 slave 投票看谁接替称为主机，得票数多少后称为主机。老的主机回来之后，会变成从机。
    
2. 启动哨兵
    - `Redis-sentinel /xxx/sentinel.conf`

一组 sentinel 能同时监控多个 Master。

### 缓存雪崩
#### 什么是缓存雪崩
- Redis 挂掉了，请求全部走数据库。
- 对缓存数据设置相同的过期时间，导致某段时间内缓存失效，请求全部走数据库。

#### 如何解决缓存雪崩
对于”同时过期“这种情况，非常好解决：
- 在缓存的时候给过期时间加上一个随机值，这样就会大幅度的减少缓存在同一时间过期。

对于”Redis挂掉“，我们可以有一下的思路
- 事发前：实现 Redis 的高可用（主从架构 + Sentinel 或者 Redis Cluster），尽量避免Redis挂掉这种情况发生。
- 事发中：万一 Redis 真的挂了，我们可以设置本地缓存（ehcache）+限流（hystrix），尽量避免我们的数据库被干掉（起码保证我们的服务还是能正常工作的）。
- 事发后：redis 持久化，重启后自动从磁盘上加载数据，快速恢复缓存数据。

### 缓存穿透

#### 什么是缓存穿透
比如，我们有一张数据表，ID都是从1开始的（正数）

但是可能有黑客想把我们的数据库搞垮，每次请求的ID都是负数。这会导致我们的缓存就没用了，请求全部都找数据库去了，但数据库也没有这个值，所以每次都返回空。

> 缓存穿透是指查询一个一定不存在的数据。由于缓存不命中，并且出于容错考虑，如果从数据库查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到数据库去查询，失去了缓存的意义。

#### 如何解决
两种方案：
- 由于请求的参数是不合法（每次请求不存在的参数），于是我们可以使用布隆过滤器（BloomFilter）或者压缩 filter 提前拦截，不合法就不让这个请求到数据库层。
- 当我们从数据库找不到的时候，我们也将这个空对象设置到缓存中。下次在请求的时候，就可以从缓存中获取了。
    - 这种情况我们一般会将空对象设置一个较短的过期时间。

#### 缓存与数据库双写一致
> 这里主要讨论的是在缓存 `更新的时候` 导致缓存与数据库数据不一致的解决办法，而且讨论的是 `缓存数据` 不准确的解决办法。

先做一个说明，从理论上来说，给缓存设置过期时间，是保证最终一致性的解决方案。也就是说如果数据库写成功，缓存更新失败，那么只要到达过期时间，则后面的读请求自然会从数据库中读取新值然后回填缓存。因此，接下来讨论的思路不依赖于给缓存设置过期时间这个方案。

> 为什么不采用直接加锁的方式？加锁之后所有的写请求串行，就会导致系统的吞吐量大幅度降低。

在这里，我们讨论四种更新策略：
1. 先更新缓存，再更新数据库
2. 先更新数据库，再更新缓存
3. 先删除缓存，再更新数据库
4. 先更新数据库，再删除缓存

##### 1. 先更新缓存，再更新数据库
> 这。。。数据的更新肯定是数据库为驱动，如果先更新缓存，然后更新数据库失败了，就算是在单线程环境下，这 bug 也太明显了。

##### 2. 先更新数据库，再更新缓存
**原因一（线程安全角度）**
同时有请求A和请求B进行更新操作，正常情况下，数据库最终的结果应该是请求B的结果，但是有可能会出现以下这种情况：
1. 线程A更新了数据库
2. 线程B更新了数据库
3. 线程B更新了缓存
4. 线程A更新了缓存

以上这种情况导致线程A把最新的 **缓存数据** 覆盖了，而线程A的数据却是老数据。

**原因二（业务场景角度）**
1. 加入是写多读少场景，会导致数据还没读到，缓存就被频繁的更新，浪费资源。

> 所以一般我们考虑使用更新缓存的策略，而是删除缓存。

##### 3. 先删除缓存，再更新数据库
该方案会导致不一致的原因是:
1. 请求A进行写操作，删除缓存
2. 请求B查询发现缓存不存在
3. 请求B去数据库查询得到旧值
4. 请求B将旧值写入缓存
5. 请求A将新值写入数据库

**解决方案**

采用延时双删策略
```java
public void write(String key,Object data){
        redis.delKey(key);
        db.updateData(data);
        Thread.sleep(1000);
        redis.delKey(key);
    }
```
转化为中文描述就是
1. 先淘汰缓存
2. 再写数据库（这两步和原来一样）
3. 休眠1秒，再次淘汰缓存

这么做，可以将1秒内所造成的缓存脏数据，再次删除。

如果第二次删除失败了，怎么办，采用第四种解决方案。


##### 4. 先更新数据库，再删除缓存 


参考文章：[分布式之数据库和缓存双写一致性方案解析](https://www.cnblogs.com/rjzheng/p/9041659.html)



#### 布隆过滤器
什么场景下面需要使用布隆过滤器呢？

看看下面几个问题
- 字处理软件中，需要检查一个英文单词是否拼写正确
- 在FBI，一个嫌疑人的名字是否已经在嫌疑名单上
- 在网络爬虫里，一个网址是否被访问过
- yahoo，gmail等垃圾邮件过滤功能

> 以上这些场景有个共同的问题：如何查看一个东西是否在有大量数据的池子里面


#### Redis几个重要的健康指标
- 存活情况
所有指标中最重要的当然是检查redis是否还活着，可以通过命令PING的响应是否PONG来判断。

- 连接数
连接的客户端数量，可通过`src/redis-cli info Clients | grep connected_clients`得到，这个值跟使用redis的服务的连接池配置关系比较大，所以在监控这个字段的值时需要注意。另外这个值也不能太大，建议不要超过5000，如果太大可能是redis处理太慢，那么需要排除问题找出原因。
另外还有一个拒绝连接数（rejected_connections）也需要关注，这个值理想状态是0。如果大于0，说明创建的连接数超过了maxclients，需要排查原因。

- 阻塞客户端数量
blocked_clients，一般是执行了list数据类型的BLPOP或者BRPOP命令引起的，可通过命令`src/redis-cli info Clients | grep blocked_clients`得到，很明显，这个值最好应该为0

- 使用内存峰值
监控redis使用内存的峰值，我们都知道Redis可以通过命令config set maxmemory 10737418240设置允许使用的最大内存（强烈建议不要超过20G），为了防止发生swap导致Redis性能骤降，甚至由于使用内存超标导致被系统kill，建议used_memory_peak的值与maxmemory的值有个安全区间，例如1G，那么used_memory_peak的值不能超过9663676416（9G）。另外，我们还可以监控maxmemory不能少于多少G，比如5G。因为我们以前生产环境出过这样的问题，运维不小心把10G配置成了1G，从而导致服务器有足够内存却不能使用的悲剧。

- 内存碎片率
`mem_fragmentation_ratio = used_memory_rss / used_memory`（内存碎片率=从操作系统的角度，返回 Redis 已分配的内存总量（俗称常驻集大小）/  由 Redis 分配器分配的内存总量，以字节（byte）为单位）,这也是一个非常需要关心的指标。如果是redis4.0之前的版本，这个问题除了重启也没什么很好的优化办法。而redis4.0有一个主要特性就是优化内存碎片率问题（Memory de-fragmentation）。在redis.conf配置文件中有介绍即ACTIVE DEFRAGMENTATION：碎片整理允许Redis压缩内存空间，从而回收内存。这个特性默认是关闭的，可以通过命令`CONFIG SET activedefrag yes`热启动这个特性。
    - 当这个值大于1时，表示分配的内存超过实际使用的内存，数值越大，碎片率越严重。
    - 当这个值小于1时，表示发生了swap，即可用内存不够
    - 另外需要注意的是，当内存使用量（used_memory）很小的时候，这个值参考价值不大。所以，建议used_memory至少1G以上才考虑内存碎片率进行监控。
 
- 缓存命中率
    - 在获取数据的时候,会判断key是否存在,如果存在keyspace_hits++,如果不存在keyspace_misses++
    - keyspace_misses/keyspace_hits这两个指标用来统计缓存的命令率，keyspace_misses指未命中次数，keyspace_hits表示命中次数。keyspace_hits/(keyspace_hits+keyspace_misses)就是缓存命中率。视情况而定，建议0.9以上，即缓存命中率要超过90%。如果缓存命中率过低，那么要排查对缓存的用法是否有问题！

- OPS
`instantaneous_ops_per_sec`这个指标表示缓存的OPS（operation per second），如果业务比较平稳，那么这个值也不会波动很大，不过国内的业务比较特性，如果不是全球化的产品，夜间是基本上没有什么访问量的，所以这个字段的监控要结合自己的具体业务，不同时间段波动范围可能有所不同。

- 持久化
rdb_last_bgsave_status/aof_last_bgrewrite_status，即最近一次或者说最后一次RDB/AOF持久化是否有问题，这两个值都应该是"ok"。
另外，由于redis持久化时会fork子进程，且fork是一个完全阻塞的过程，所以可以监控fork耗时即latest_fork_usec，单位是微妙，如果这个值比较大会影响业务，甚至出现timeout。

- 失效KEY
如果把Redis**当缓存**使用，那么建议所有的key都设置了expire属性，通过命令src/redis-cli info Keyspace得到每个db中key的数量和设置了expire属性的key的属性，且expires需要等于keys


- 慢日志
通过命令slowlog get得到Redis执行的slowlog集合，理想情况下，slowlog集合应该为空，即没有任何慢日志，不过，有时候由于网络波动等原因造成set key value这种命令执行也需要几毫秒，在监控的时候我们需要注意，而不能看到slowlog就想着去优化，简单的set/get可能也会出现在slowlog中。


参考文章：[Redis几个重要的健康指标](https://mp.weixin.qq.com/s/D_khsApGkRckEoV75pYpDA)

##### 布隆过滤器介绍
- 巴顿·布隆与一九七零年提出
- 一个很长的二进制向量（位数组）
- 一系列随机函数（哈希）
- 空间效率和查询效率高
- 不会漏判，但是有一定的误判率（哈希表是精确匹配）

##### 布隆过滤器原理
![WechatIMG544.jpeg](https://i.loli.net/2019/08/31/lYzTgePq9QMLsZ6.jpg)

> 以上图为例，具体的操作流程：假设集合里面有3个元素{x, y, z}，哈希函数的个数为3。首先将位数组进行初始化，将里面每个位都设置位0。对于集合里面的每一个元素，将元素依次通过3个哈希函数进行映射，每次映射都会产生一个哈希值，这个值对应位数组上面的一个点，然后将位数组对应的位置标记为1。查询W元素是否存在集合中的时候，同样的方法将W通过哈希映射到位数组上的3个点。如果3个点的其中有一个点不为1，则可以判断该元素一定不存在集合中。反之，如果3个点都为1，则该元素可能存在集合中。注意：此处不能判断该元素是否一定存在集合中，可能存在一定的误判率。可以从图中可以看到：假设某个元素通过映射对应下标为4，5，6这3个点。虽然这3个点都为1，但是很明显这3个点是不同元素经过哈希得到的位置，因此这种情况说明元素虽然不在集合中，也可能对应的都是1，这是误判率存在的原因。

###### 添加元素
- 将要添加的元素给k个哈希函数
- 得到对应于位数组上的k个位置
- 将这k个位置设为1

###### 查询元素
- 将要查询的元素给k个哈希函数
- 得到对应于位数组上的k个位置
- 如果k个位置有一个为0，则肯定不在集合中
- 如果k个位置全部为1，则可能在集合中


总结：
需求：判断某几个元素是否存在海量数据中
解决办法
- 哈希表：虽然查找效率为O(1)，但是要把过亿的数据全都存起来是不科学的
- 布隆过滤器：用比特位存储
怎么初始化海量数据呢？
用hash函数算出某个数值k，然后比特数组第k位置为1，以此类推完成初始化。（上面的hash过程，每个key需要进行k次）
如何查找？
也是用rehash查找，但是我们都知道哈希表会存在哈希冲突，那么布隆过滤器如何解决呢？布隆过滤器不能也不需要把冲突的key用链表连接起来，因为他只需要判断key是否存在。他进行k个rehash，如果k位都是1则判断存在（存在误判），如果有1位为0，则一定不存在

###### 为什么不直接一次hash，而是要多次
降低hash冲突引发的误差

###### 误差率
m:二进制向量数
n:预备数据量
k:hash函数个数
m/n与误差率成反比，k与误差率成反比


###### 为什么不直接使用哈希表存储
使用哈希表存储一亿 个垃圾 email 地址的消耗？哈希表的做法：首先，哈希函数将一个email地址映射成8字节信息指纹；考虑到哈希表存储效率通常小于50%（哈希冲突）；因此消耗的内存：8 * 2 * 1亿 字节 = 1.6G 内存，普通计算机是无法提供如此大的内存。

#### redis的使用场景
- String：缓存、限流、计数器、分布式锁、分布式session
- Hash：存储用户信息、用户主页访问量、组合查询
- List：微博关注人时间轴列表、简单队列
- Set：赞、踩、标签、好友关系
- Zset：排行榜

针对悦头条中邀请有效徒弟数实时排名
1、徒弟阅读时，在收徒奖励明细中存放一条记录
2、每插入一条记录，检查该徒弟阅读次数是等于5（或者可以把已经计算过是有效的徒弟id放在redis中，然后每次先判断是否已存在，再去判断阅读次数，这样可以避免频繁查询数据库，而且使用等于5作为判断条件有可能会出现击穿的情况）
2、如果等于5，将该师傅的id作为member，存放到zSet中，score加1

### redisTemplate 使用
> 先拍醒自己：`redis` 本身只是一个巨大的 `Map`。`key` 都是字符串，`value` 可能是 `string`、`hash`、`list`、`set`、`zset` 这五种数据结构。

RedisTemplate 中定义了对 5 种数据结构操作：
```java
redisTemplate.opsForValue();//操作字符串
redisTemplate.opsForHash();//操作hash
redisTemplate.opsForList();//操作list
redisTemplate.opsForSet();//操作set
redisTemplate.opsForZSet();//操作有序set
```

|接口	|描述
|---|---|
|Key类型操作
|ValueOperations|操作Redis String（或者Value）类型数据
|ListOperations|操作Redis List类型数据
|SetOperations|操作Redis Set类型数据
|ZSetOperations|操作Redis ZSet（或者Sorted Set）类型数据
|HashOperations|操作Redis Hash类型数据
|HyperLogLogOperations|操作Redis HyperLogLog类型数据，比如：pfadd，pfcount，...
|GeoOperations|操作Redis Geospatial类型数据，比如：GEOADD,GEORADIUS,…​)
|Key绑定操作
|BoundValueOperations|Redis字符串（或值）键绑定操作
|BoundListOperations|Redis列表键绑定操作
|BoundSetOperations |Redis Set键绑定操作
|BoundZSetOperations|Redis ZSet（或Sorted Set）键绑定操作
|BoundHashOperations|Redis Hash键绑定操作
|BoundGeoOperations|Redis Geospatial 键绑定操作

#### StringRedisTemplate 与 RedisTemplate 区别
他们两者之间的主要区别在于他们使用的序列化类：
- `RedisTemplate` 使用的是 JdkSerializationRedisSerializer。【默认使用，一般我们会在配置类中更换掉】
- `StringRedisTemplate` 使用的是 StringRedisSerializer。

总结：当你想要存的值本来就是 string 类型的时候，使用 StringRedisTemplate 更佳。如果你想要存复杂的对象类型（自定义对象），使用 RedisTemplate 更佳。

拍醒自己：这里所指的 存的值 指的是真实要存的值的类型，不需要管用的是哪种数据结构去存（Set、Map、list）