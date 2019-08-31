
> MySQL explain 可以用来分析 SQL 的执行计划。

explain 结果各列的含义如下:
- `id`: SELECT 查询的标识符. 每个 SELECT 都会自动分配一个唯一的标识符。
- `select_type`: SELECT 查询的类型。
- `table`: 查询的是哪个表。
- `partitions`: 匹配的分区。
- `type`: 显示查询使用了何种类型。
- `possible_keys`: 此次查询中可能选用的索引。
- `key`: 此次查询中确切使用到的索引。
- `ref`: 哪个字段或常数与 key 一起被使用。
- `rows`: 显示此查询一共扫描了多少行. 这个是一个估计值。
- `filtered`: 表示此查询条件所过滤的数据的百分比。
- `extra`: 额外的信息。

## id
- select查询的序列号，包含一组数字，表示查询中执行select子句或操作表的顺序
    - 两种情况
        - id相同，执行顺序由上至下
        - id不同，如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
        
## select_type
查询的类型，主要是用于区分普通查询、联合查询、子查询等的复杂查询。

|select_type|说明|
|---|---|
|SIMPLE|简单的select查询，查询中不包含子查询或者UNION|
|PRIMARY|查询找那个若包含任何复杂的子部分，最外层查询则被标记为PRIMARY|
|SUBQUERY|在 SELECT 或 WHERE 列表中包含了子查询|
|DERIVED|在FROM列表中包含的子查询被标记为DERIVED（衍生）MySQL会递归执行这些子查询，把结果放在临时表|
|UNION|若第二个SELECT出现在UNION之后，则被标记为UNION；若UNION包含在FROM子句的子查询中，外层SELECT将被标记为：DERIVED|
|UNION RESULT|从UNION表获取结果的SELECT|

    
## table
显示这一行的数据是关于哪张表。

## type
显示查询使用了何种类型。
从最好到最差依次是： `system>const>eq_ref>ref>range>index>ALL`
一般来说，得保证查询至少达到 `range` 级别，最好能达到 `ref`。
- `system`：**表只有一行记录**（等于系统表），这是const类型的特例，平时不会出现，这个可以忽略不计。
- `const`：**表示通过一次就找到了，const用于比较primary key或者unique索引。因为只匹配一行数据**，所有很快，如将主键置于where列表中，MySQL就能将该查询转换为一个常量。
- `eq_ref`：唯一性索引扫描，对于每个索引键值，表中只有一条记录与之匹配。常见于主键或唯一索引扫描（此类型通常出现在多表的 join 查询, 表示对于前表的每一个结果, 都只能匹配到后表的一行结果. 并且查询的比较操作通常是 =, 查询效率较高）。
- `ref`：非唯一性索引扫描，返回匹配某个单独值的所有行。本质上也就是一种索引访问，它返回所有匹配某个单独值的行，然而，它可能会找到多个符合条件的行，所以他应该属于查找和扫描的混合体（此类型通常出现在多表的 join 查询, 针对于非唯一或非主键索引, 或者是使用了`最左前缀`规则索引的查询 ）。
- `range`：只检索给定范围的行，使用一个索引来选择行。key列显示使用了哪个索引，一般就是你的where语句中出现了between、<、>、in等的查询，这种范围扫描索引扫描比全表扫描要好，因为它只需要开始于索引的某一点，而结束于另一点，不用扫描全部索引
- `index`：`Full Index Scan`，index与All区别为index类型`只遍历索引树`。这通常比ALL快，**因为索引文件通常比数据文件小**。（也就是说虽然all和Index都是读全表，但index是从索引中读取的，而all是从硬盘中读取的）如下面的例子
`EXPLAIN SELECT phoneNum FROM htt_award_his_child` phoneNum是唯一索引。
- `ALL`：`Full Table Scan`，将遍历全表以找到匹配的行。

## possible_keys
显示可能应用在这张表中的索引，一个或多个。查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询实际使用。

## key
实际使用的索引。如果为null，则没有使用索引。
查询中若使用了覆盖索引，则该索引仅出现在key列表中。

## key_len
表示查询优化器使用了索引的字节数。**这个字段可以评估组合索引是否完全被使用, 或只有最左部分字段被使用到**。

## ref
显示索引的哪一列被使用了，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值。

## rows  
根据表统计信息及索引选用情况，**大致估算**出找到所需的记录需要读取的行数。

## Extra
包含不适合在其他列中显式但十分重要的额外信息。
- `Using filesort`（九死一生）
    - 表示 MySQL 需额外的排序操作, 不能通过索引顺序达到排序效果。
- `Using temporary`（十死无生）
    - 使用了临时表保存中间结果，MySQL在对查询结果排序时使用临时表。常见于排序order by和分组查询group by。

- `Using index`
    - 表示相应的select操作中使用了覆盖索引（Covering Index），避免访问了表的数据行，效率不错！如果同时出现using where，表明索引被用来执行索引键值的查找；如果没有，表明索引用来读取数据而非执行查找动作。
    
- `Using where`
    - 表明使用了where过滤。
- `Using join buffer`
    - 使用了连接缓存。
- `impossible where`
    - where子句的值总是false，不能用来获取任何元组。

