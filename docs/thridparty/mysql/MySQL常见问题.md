#### 前言
今天楼主给大家列一下关于数据库几个常见问题的要点，如果大家对其中的问题感兴趣，可以自行扩展研究。
#### 1. union all 与 union 的区别
- UNION和UNION ALL关键字都是将两个结果集合并为一个。

- UNION在进行表链接后会筛选掉重复的记录，所以在表链接后会对所产生的结果集进行排序运算，删除重复的记录再返回结果。

- 而UNION ALL只是简单的将两个结果合并后就返回。

- 由于union需要排序去重，所以 union all 的效率比 union 好很多。

#### 2. TRUNCATE 与 DELETE 区别
- TRUNCATE 是DDL语句，而 DELETE 是DML语句。
- TRUNCATE 是先把整张表drop调，然后重建该表。而 DELETE 是一行一行的删除，所以 TRUNCATE 的速度肯定比 DELETE 速度快。
- TRUNCATE 不可以回滚，DELETE 可以。
- TRUNCATE 执行结果只是返回`0 rows affected`，可以解释为没有返回结果。
- TRUNCATE 会重置水平线（自增长列起始位），DELETE 不会。
- TRUNCATE 只能清理整张表，DELETE 可以按照条件删除。
- 一般情景下，TRUNCATE性能比DELETE好一点。

#### 3. datetime 与timestamp 的区别
`相同点`
- TIMESTAMP 列的显示格式与 DATETIME 列相同。显示列宽固定在19字符，并且格式为`YYYY-MM-DD HH:MM:SS`。

`不同点`
- `TIMESTAMP`
    - 4个字节存储，时间范围：`1970-01-01 08:00:01~2038-01-19 11:14:07`。
    - 值以UTC格式保存，涉及时区转化，存储时对当前的时区进行转换，检索时再转换回当前的时区。
- `DATETIME`
    - 8个字节存储，时间范围：`1000-10-01 00:00:00~9999-12-31 23:59:59`。
    - 实际格式存储，与时区无关。


#### 4. 什么是联合索引
两个或更多个列上的索引被称作联合索引，联合索引又叫复合索引。

#### 5. 为什么要使用联合索引
- `减少开销`：建一个联合索引(col1,col2,col3)，实际相当于建了(col1),(col1,col2),(col1,col2,col3)三个索引。减少磁盘空间的开销。
- `覆盖索引`：对联合索引(col1,col2,col3)，如果有如下的sql: select col1,col2,col3 from test where col1=1 and col2=2。那么MySQL可以直接通过遍历索引取得数据，而**无需回表**，这减少了很多的随机io操作。覆盖索引是主要的提升性能的优化手段之一。
- `效率高`：索引列越多，通过索引筛选出的数据越少。有1000W条数据的表，有如下sql `select from table where col1=1 and col2=2 and col3=3`，假设假设每个条件可以筛选出10%的数据，如果只有单值索引，那么通过该索引能筛选出`1000W*10%=100w`条数据，然后再回表从100w条数据中找到符合col2=2 and col3= 3的数据，然后再排序，再分页；如果是联合索引，通过索引筛选出`1000w*10%*10%*10%=1w`，效率得到明显提升。

#### 6. MySQL 联合索引最左匹配原则
- 在 MySQL 建立联合索引时会遵循最左前缀匹配的原则，即最左优先，在检索数据时从联合索引的最左边开始匹配。
- MySQL 会一直向右匹配直到遇到范围查询(>、<、between、like)就停止匹配，比如`a = 1 and b = 2 and c > 3 and d = 4` 如果建立`(a,b,c,d)`顺序的索引，d是用不到索引的，如果建立`(a,b,d,c)`的索引则都可以用到，a,b,d的顺序可以任意调整。
- = 和 in 可以乱序，比如`a = 1 and b = 2 and c = 3` 建立`(a,b,c)`索引可以任意顺序，mysql的查询优化器会帮你优化成索引可以识别的形式。



#### 7. 什么是聚集和非聚集索引
- 聚集索引就是以主键创建的索引。
- 非聚集索引就是以非主键创建的索引。

#### 8. 什么是覆盖索引
- 覆盖索引（covering index）指一个查询语句的执行只用从索引页中就能够取得（如果不是聚集索引，叶子节点存储的是主键+列值，最终还是要**回表**，也就是要通过主键再查找一次），避免了查到索引后，再做回表操作，减少I/O提高效率。
- 可以结合第10个问题更容易理解。

#### 9. 什么是前缀索引
前缀索引就是对文本的前几个字符（具体是几个字符在创建索引时指定）创建索引，这样创建起来的索引更小。但是MySQL不能在ORDER BY或GROUP BY中使用前缀索引，也不能把它们用作覆盖索引。

创建前缀索引的语法：
```sql
ALTER TABLE table_name ADD
KEY(column_name(prefix_length))
```

#### 10. InnoDB 与 MyISAM 索引存储结构的区别
- MyISAM索引文件和数据文件是分离的，索引文件仅保存数据记录的地址。
- 而在InnoDB中，**表数据文件本身就是按B+Tree组织的一个索引结构，这棵树的叶节点data域保存了完整的数据记录**。这个索引的key是数据表的主键，因此InnoDB表数据文件本身就是主索引，所以必须有主键，如果没有显示定义，自动为生成一个隐含字段作为主键，这个字段长度为6个字节，类型为长整型。
- InnoDB的辅助索引（Secondary Index，也就是非主键索引）存储的只是**主键列和索引列**，如果主键定义的比较大，其他索引也将很大。
- MyISAM引擎使用B+Tree作为索引结构，索引文件叶节点的data域存放的是数据记录的地址，指向数据文件中对应的值，每个节点只有该索引列的值。
- MyISAM主索引和辅助索引（Secondary key）在结构上没有任何区别，只是主索引要求key是唯一的，辅助索引可以重复，（由于MyISAM辅助索引在叶子节点上存储的是数据记录的地址，和主键索引一样，所以不需要再遍历一次主键索引）。

**简单的说：**
1. `主索引的区别`：InnoDB的数据文件本身就是索引文件。而MyISAM的索引和数据是分开的。

2. `辅助索引的区别`：InnoDB的辅助索引data域存储相应记录主键的值而不是地址。而MyISAM的辅助索引和主索引没有多大区别。

#### 11. 为什么尽量选择单调递增数值类型的主键
- InnoDB中数据记录本身被存于主索引（B+树）的叶子节点上。这就要求同一个叶子节点内（大小为一个内存页或磁盘页）的各条数据记录按主键顺序存放，因此每当有一条新的记录插入时，MySQL会根据其主键将其插入适当的结点和位置，如果页面达到装载因子（InnoDB默认为15/16），则开辟一个新的页。

- 如果使用自增主键，那么每次插入新的记录，记录就会顺序添加到当前索引结点的后续位置，当一页写满，就会自动开辟一个新的页，这样就会形成一个紧凑的索引结构，近似顺序填满。由于每次插入时也不需要移动已有数据，因此效率很高，也不会增加很多开销在维护索引上。

- 如果使用非自增主键，由于每次插入主键的值近似于随机，因此每次新纪录都要被插入到现有索引页的中间某个位置，此时MySQL不得不为了将新记录查到合适位置而移动元素，甚至目标页可能已经被回写到磁盘上而从缓存中清掉，此时又要从磁盘上读回来，这增加了很多开销，同时频繁的移动、分页操作造成了大量的碎片，得到了不够紧凑的索引结构，后续不得不通过 `OPTIMIZE TABLE` 来重建表并优化填充页面。

**简单的说：**

索引树只能定位到某一页，每一页内的插入还是需要通过比较、移动插入的。所以有序主键可以提升插入效率。

#### 12. 建表时，int后面的长度的意义
int占多少个字节，已经是固定的了，长度代表了显示的最大宽度，如果不够会用0在左边填充，但必须搭配zerofill使用
> MySQL还支持选择在该类型关键字后面的括号内指定整数值的显示宽度(例如，INT(4))。该可选显示宽度规定用于显示宽度小于指定的列宽度的值时从左侧填满宽度。显示宽度并不限制可以在列内保存的值的范围，也不限制超过列的指定宽度的值的显示。 
也就是说，int的长度并不影响数据的存储精度，长度只和显示有关，为了让大家看的更清楚，我们在上面例子的建表语句中，使用了zerofill。
无论是unsigned int(3)或 unsiend int(6)，存储的都是4字节无符号整数， 也就是0~2^32。

#### 13. SHOW INDEX 字段解析
- Table
The name of the table.

- Non_unique
0 if the index cannot contain duplicates, 1 if it can.

- Key_name
The name of the index. If the index is the primary key, the name is always PRIMARY.

- Seq_in_index
The column sequence number in the index, starting with 1.

- Column_name
The column name. See also the description for the Expression column.

- Collation
How the column is sorted in the index. This can have values A (ascending), D (descending), or NULL (not sorted).

- Cardinality
    - An estimate of the number of unique values in the index. To update this number, run ANALYZE TABLE or (for MyISAM tables) myisamchk -a.

    - Cardinality is counted based on statistics stored as integers, so the value is not necessarily exact even for small tables. The higher the cardinality, the greater the chance that MySQL uses the index when doing joins.

- Sub_part
The index prefix. That is, the number of indexed characters if the column is only partly indexed, NULL if the entire column is indexed.

- Packed
Indicates how the key is packed. NULL if it is not.

- Null
Contains YES if the column may contain NULL values and '' if not.

- Index_type
The index method used (BTREE, FULLTEXT, HASH, RTREE).

- Comment
Information about the index not described in its own column, such as disabled if the index is disabled.

- Index_comment
Any comment provided for the index with a COMMENT attribute when the index was created.

#### 14. 如何解决like'%字符串%'时索引不被使用？
使用覆盖索引，可以由ALL变为INDEX，为啥呢？覆盖索引之后就能使用使用索引进行全表扫描。这里要注意一下，使用符合索引的时候，命中一个字段就可以，不用全部命中。
- 字符串不加单引号索引失效
    - 因为MySQL会自动转换类型，就会触发上面所说的索引失效
- 少用or，用它来连接时会索引失效

#### 15. MySQL高效分页
- 存在SQL：`SELECT * FROM ttl_product_info ORDER BY id LIMIT N,M`。`LIMIT N,M` 存在的问题：取出N+M行，丢弃前N行，返回 `N ~ N+M` 行的记录，如果N值非常大，效率极差（表记录1500w，N=10000000,M=30 需要9秒）。
- 解决办法：SQL：`SELECT id FROM ttl_product_info WHERE id > N  LIMIT M`，id 列是索引列，`id > N`属于 `range` 级别，效率自然高，然后从位置开始取30条记录，效率极高（表记录1500w，N=10000000,M=30，需要0.9毫秒）。
- 当然想要实现上述效果的前提是：
    1. id是唯一索引，而且单调递增。   
    2. N 的值是上一次查询的记录的最后一条id，（需要前端保存一下，不能直接用传统的方法获得）
    3. 不支持跨页查询，只能1，2，3，4这样查询。

#### Mybatis 中使用数据库小技巧
- `SELECT` 语句 `WHERE` 条件加上 `ifnull` 判断。为了避免传入都是 null 时，做全表扫描，WHERE 需要增加一个不带 `ifnull` 的查询条件，或者，必须默认按照某个索引列的某个范围去查。

#### 16. left join,right join,inner join,full join 区别
```sql
# 左表全部显示，右表能与左表匹配的数据则显示，否则为null
SELECT * FROM table_a a LEFT JOIN table_b b ON a.id=b.id;

# 右表全部显示，左表能与右表匹配的数据则显示，否则为null
SELECT * FROM table_a a RIGHT JOIN table_b b ON a.id=b.id;

# 左、右两个表匹配的才显示
SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.id;

# 笛卡尔积
SELECT * FROM table_a,table_b;

# 与上面等价，笛卡尔积
SELECT * FROM table_a FULL JOIN table_b 
```


