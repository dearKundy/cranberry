## 分布式系统的 CAP 理论
### CAP 理论概述
> 一个分布式系统最多只能同时满足一致性（Consistency）、可用性（Availability）和分区容错性（Partition tolerance）这三项中的两项。
> 
![WechatIMG536.jpeg](https://i.loli.net/2019/08/31/DhgeFHB1dYMbcLS.jpg)

#### Consistency 一致性
一致性指：`all nodes see the same data at the same time`。
##### 三种一致性策略
- `强一致性`：对于关系型数据库，要求更新过的数据能被后续的访问都能看到。
- `弱一致性`：能容忍后续的部分或者全部访问不到。
- `最终一致性`：经过一段时间后要求能访问到更新后的数据。

CAP中说，不可能同时满足的这个一致性指的是强一致性。

#### Availability 可用性
可用性指：`Reads and writes always succeed`，即服务一直可用，而且是正常响应时间。

一般我们在衡量一个系统的可用性的时候，都是通过停机时间来计算的。

#### Partition Tolerance 分区容错性
分区容错性指：`the system continues to operate despite arbitrary message loss or failure of part of the system`，即分布式系统在遇到某节点或网络分区故障的时候，仍然能够对外提供满足一致性和可用性的服务。

比如现在的分布式系统中有某一个或者几个机器宕掉了，其他剩下的机器还能够正常运转满足系统需求，或者是机器之间有网络异常，将分布式系统分隔未独立的几个部分，各个部分还能维持分布式系统的运作，这样就具有好的分区容错性。

### 为什么 CAP 理论中只能选其二
> 我的理解呀，主要是分布式系统中 C A 不能共存，所以就只剩 CP AP。那为啥 C A 不能共存。

假设在N1和N2之间网络断开的时候，有用户向N1发送数据更新请求，那N1中的数据V0将被更新为V1，由于网络是断开的，所以N2中的数据依旧是V0；这个时候，有用户向N2发送数据读取请求，由于数据还没有进行同步，应用程序没办法立即给用户返回最新的数据V1，怎么办呢？
- 有二种选择，第一，牺牲数据一致性，保证可用性。响应旧的数据V0给用户；

- 第二，牺牲可用性，保证数据一致性。阻塞等待，直到网络连接恢复，数据更新操作M完成之后，再给用户响应最新的数据V1。

这个过程，证明了要满足分区容错性的分布式系统，只能在一致性和可用性两者中，选择其中一个。

#### CAP 权衡
##### CA without P
这种情况在分布式系统中几乎是不存在的。首先在分布式环境下，网络分区是一个自然的事实。因为分区是必然的，所以如果舍弃P，意味着要舍弃分布式系统。

比如我们熟知的关系型数据库，如My Sql和Oracle就是保证了可用性和数据一致性，但是他并不是个分布式系统。一旦关系型数据库要考虑主备同步、集群部署等就必须要把P也考虑进来。

##### CP without A
一个保证了CP而一个舍弃了A的分布式系统，一旦发生网络故障或者消息丢失等情况，就要牺牲用户的体验，等待所有数据全部一致了之后再让用户访问系统。

##### AP wihtout C
要高可用并允许分区，则需放弃一致性。一旦网络问题发生，节点之间可能会失去联系。为了保证高可用，需要在用户访问时可以马上得到返回，则每个节点只能用本地数据提供服务，而这样会导致全局数据的不一致性。

你在12306买票的时候肯定遇到过这种场景，当你购买的时候提示你是有票的（但是可能实际已经没票了），你也正常的去输入验证码，下单了。但是过了一会系统提示你下单失败，余票不足。这其实就是先在可用性方面保证系统可以正常的服务，然后在数据的一致性方面做了些牺牲，会影响一些用户体验，但是也不至于造成用户流程的严重阻塞。

但是，我们说很多网站牺牲了一致性，选择了可用性，这其实也不准确的。就比如上面的买票的例子，其实舍弃的只是强一致性。退而求其次保证了最终一致性。也就是说，虽然下单的瞬间，关于车票的库存可能存在数据不一致的情况，但是过了一段时间，还是要保证最终一致性的。

### 分布式系统的 BASE 理论
> BASE理论是对CAP理论的延伸，核心思想是即使无法做到强一致性（Strong Consistency，CAP的一致性就是强一致性），但应用可以采用适合的方式达到最终一致性（Eventual Consitency）。

BASE是指基本可用（Basically Available）、软状态（ Soft State）、最终一致性（ Eventual Consistency）。

#### 基本可用（Basically Available）
基本可用是指分布式系统在出现故障的时候，允许损失部分可用性，即保证核心可用。

电商大促时，为了应对访问量激增，部分用户可能会被引导到降级页面，服务层也可能只提供降级服务。这就是损失部分可用性的体现。

#### 软状态（ Soft State）
软状态是指允许系统存在中间状态，而该中间状态不会影响系统整体可用性。分布式存储中一般一份数据至少会有三个副本，允许不同节点间副本同步的延时就是软状态的体现。mysql replication的异步复制也是一种体现。

#### 最终一致性（ Eventual Consistency）
最终一致性是指系统中的所有数据副本经过一定时间后，最终能够达到一致的状态。弱一致性和强一致性相反，最终一致性是弱一致性的一种特殊情况。

### 分布式、集群
1）分布式：不同的多台服务器上面部署**不同的服务模块（工程）**，他们之间通过Rpc/Rmi之间通信和调用，对外提供服务和组内协作

2）集群：不同的多态服务器上面部署**相同的服务模块**，通过分布式调度软件进行统一的调度，对外提供服务和访问
简单来说，分布式是以缩短单个任务的执行时间来提升效率的，而集群则是通过提高单位时间内执行的任务数来提升效率。

参考文章：
- [分布式系统的BASE理论](分布式系统的BASE理论)