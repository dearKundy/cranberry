# MySQL中的重做日志（redo log），回滚日志（undo log），以及二进制日志（binlog）

## binlog

> binlog是Mysql sever层维护的一种二进制日志，与innodb引擎中的redo/undo log是完全不同的日志；
其主要是用来记录对mysql数据更新或潜在发生更新的SQL语句，并以"事务"的形式保存在磁盘中；

作用主要有：

- 复制：MySQL Replication在Master端开启binlog，Master把它的二进制日志传递给slaves并回放来达到master-slave数据一致的目的
- 数据恢复：通过mysqlbinlog工具恢复数据
- 增量备份

Mysql binlog日志有三种格式，分别为 Statement、ROW、MiXED
1. Statement：每一条会修改数据的sql都会记录在binlog中。
2. ROW：记录每一条记录被修改成什么了。
3. MiXED：是以上两种level的混合使用。

## undo log
> undo日志用于存放数据修改被修改前的值，假设修改 tba 表中 id=2的行数据，把Name='B' 修改为Name = 'B2' ，
那么undo日志就会用来存放Name='B'的记录，如果这个修改出现异常，可以使用undo日志来实现回滚操作，保证事务的一致性。

## redo log
确保事务的持久性，防止在发生故障的时间点，尚有脏页未写入磁盘，在重启mysql服务的时候，根据redo log进行重做，从而达到事务的持久性这一特性。

参考文章：
- [Mysql Binlog日志详解](https://www.cnblogs.com/huajiezh/p/6046576.html)
- [MySQL中的重做日志（redo log），回滚日志（undo log），以及二进制日志（binlog）的简单总结]()https://www.cnblogs.com/xinysu/p/6555082.html)

