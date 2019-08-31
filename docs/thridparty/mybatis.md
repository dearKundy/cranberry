MyBatis3 之前叫做 ibatis。

## MyBatis Hello World
1. 导入 mybatis 依赖。
```xml
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>x.x.x</version>
</dependency>
```

2. 创建 MyBatis 全局配置文件 mybatis-config.xml。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <environments default="development">

        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/yyt"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>

    </environments>


    <!-- 注册sql映射文件 -->
    <mappers>
        <mapper resource="mybatis/mapper/HttAdsCustomMapper.xml"/>
    </mappers>

</configuration>
```
3. 编写 SQL 映射文件。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="mapper.HttAdsCustomMapper">
      <select id="selectAds" resultType="HttAdsCustom" >
        SELECT * FROM htt_ads_custom WHERE adsId = #{adsId}
    </select>
</mapper>
```

## MyBatis 配置文件的 dtd 约束
`sql 映射文件约束` ：http://mybatis.org/dtd/mybatis-3-mapper.dtd
`全局配置文件约束`：http://mybatis.org/dtd/mybatis-3-config.dtd

## 执行 sql
想要执行 sql，首先要获取 SqlSession。然后通过 SqlSession 执行 SQL 。执行 SQL 的方式一般有两种：
- 通过指定 sql 唯一 id 执行。
- 通过接口执行。

## 通过指定 SQL id执行
```java
public class IdWayTest {

    private static void test() throws Exception {
        // 1.根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2.获取 SqlSession 实例，能直接执行已经映射的 SQL 语句 【sqlSession 是非线程安全的，每次使用都应该去获取新的对象，使用完之后关闭】
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            /**
             * 该方法的一个参数：SQL 映射文件的 namespace + sql id 组成
             * 第二个参数：待传入的参数
             */
            HttAdsCustom adsCustom = sqlSession.selectOne("mapper.IdWayTest.selectAds", "100001");
            System.out.println(adsCustom);
        } finally {
            sqlSession.close();
        }
    }
}
```

## 通过接口执行
注意：
- 还是需要全局配置文件中注册映射文件。
- 需要添加 Mapper 接口，sql 映射文件的namespace 与 该 Mapper 接口的全类名对应。
```java
public interface HttAdsCustomMapper {

    public HttAdsCustom selectAds(Integer adsId);

}
```
```java
public class InterfaceWayTest {

    public static void main(String[] args) throws Exception {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            // 获取接口的实现类对象（MyBatis会为接口生成一个代理对象，由代理对象去执行增删改查）
            HttAdsCustomMapper mapper = sqlSession.getMapper(HttAdsCustomMapper.class);
            System.out.println(mapper.getClass());
            HttAdsCustom adsCustom = mapper.selectAds(100001);
            System.out.println(adsCustom);
        } finally {
            sqlSession.close();
        }

    }

}
```

## 全局配置文件 properties
引入配置文件【做了解即可，与 spring 整合之后，这块就没什么意义了】
```xml
    <!--
    MyBatis可以使用properties来引入外部properties配置文件的内容
    resource：引入类路径下的资源
    url：引入网络路径或磁盘路径下的资源
    -->
    <properties resource="dbconfig.properties"/>
```

dbconfig.properties 如下所示
```properties 
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/yyt
jdbc.username=root
jdbc.password=123456
```
这样就可以在全局配置文件中通过 ${} 引用 dbconfig.properties 的属性值
```xml
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
```

## 全局配置文件 settings
```xml
    <settings>
        <!-- 开启驼峰命名 -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
```
settings 包含很多重要属性，具体可以参照：
- [官方文档](http://www.mybatis.org/mybatis-3/zh/configuration.html#settings)

## 全局配置文件 typeAliases
类型别名是为 Java 类型设置一个短的名字。 它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。
```xml
    <!--
     typeAliases：别名处理器，为Java类型起别名【别名不区分大小写】
     默认别名为：第一个字母小写，类名
    -->
    <typeAliases>
        <typeAlias type="model.HttAdsCustom" />

        <!-- 批量起别名 -->
        <package name="model.*"/>

        <!-- 批量起别名的情况下，使用@Alias注解为某个类型指定新的别名 -->
    </typeAliases>
```
这是一些为常见的 Java 类型内建的相应的类型别名。它们都是不区分大小写的。
参考文章：
- [http://www.mybatis.org/mybatis-3/zh/configuration.html#settings](http://www.mybatis.org/mybatis-3/zh/configuration.html#settings)

## 全局配置文件 environments
【了解即可，后面还是交给Spring来处理事务】
```xml
   <!--
        environments：可以配置多种环境，default：指定默认环境
       -->
    <environments default="development">

        <!--
         environment：配置一个具体的环境信息，id代表当前环境的唯一标识，必须有如下两个标签
         transactionManager：事务管理器【了解即可，后面还是交给Spring来处理事务】
         dataSource：数据源
        -->
        <environment id="test">
            <transactionManager type="">

            </transactionManager>
            <dataSource type="">

            </dataSource>
        </environment>

        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>

    </environments>
```

## 全局配置文件 注册 SQL 映射
```xml
    <!-- 注册sql映射文件 -->
    <mappers>
        <mapper resource="mybatis/mapper/HttAdsCustomMapper.xml"/>
    </mappers>
```

## insert 获取自增长 ID
```xml
    <!--
    useGeneratedKeys="true" : 使用自增主键取主键值策略
    keyProperty：指定主键字段，也就是说MyBatis 获取到主键之后，将这个值封装给JavaBean的哪个属性。
    执行完 update 之后，在service层直接getId即可获得该自增主键值。
    -->
    <update id="insertAds" useGeneratedKeys="true" keyProperty="id">

    </update>
```

## 参数处理
### 单个参数
- MyBatis 不会做特殊处理，即参数名可以随意，不用一一对应
- #{参数名}：取出参数值

### 多个参数
假如存在 SQL 映射如下：
```xml
    <select id="selectAdsByAdsNameAndAdvertiserId" resultType="model.HttAdsCustom">
        SELECT * FROM htt_ads_custom WHERE adsName = #{adsName} AND advertiserId = #{advertiserId}
    </select>
```
接口：
```java
public HttAdsCustom selectAdsByAdsNameAndAdvertiserId(String adsName,String advertiserId);
```
执行的时候则会出现下面的异常： 
> Parameter 'adsName' not found. Available parameters are [arg1, arg0, param1, param2]

- MyBatis会做特殊处理，多个参数会被封装成一个map
- key 是param1...paramN，或者参数的索引都可以
- #{}就是从map中获取指定的key的值

所以上面的映射文件通过 #{param1} #{param2}的方式去取。

### 命名参数 @Param
通过上面的方式取值，如果参数多了就会很头痛，我们需要见名知其义。

- 明确指定封装参数时map的key：@Param("xxx")
- 多个参数会被封装成一个map
- key：使用 `@Param` 注解指定的值
- value：参数值
- #{指定的key}取出对应的参数

### POJO
如果多个参数正好是我们业务逻辑的数据模型，我们就可以直接传入pojo
- #{属性名}：取出传入的pojo的属性值

### Map
如果多个参数不是业务模型中的数据，没有对应的pojo，为了方便，我们也可以传入map
- #{key}：取出map中对应的值

### 返回值
- 如果返回的是一个集合，resultType要写集合中元素的类型
- 如果想返回Map类型，resultType要写map
- 如果把多条记录封装成一个map：`Map<Integer,Employee>`键是这条记录的主键，值是记录封装后的java对象，resultType还是写Employee，但是要在接口方法上加上@Map("id")注解告诉mybatis封装这个map的时候使用哪个属性作为map的key

### 特殊情况
```java
    // 取值：id=>#{id/param1} lastName=>#{param2}
    public HttAdsCustom getAds(@Param("id") Integer id,String lastName);
    
    // 取值：id=>#{param1} lastName=>#{param2.lastName/e.lastName}
    public HttAdsCustom getAds(Integer id,@Param("ads") HttAdsCustom adsCustom);

    /**
     * 取值：取出第一个id的值：#{list[0]}
     * 如果Collection（List、Set）类型或数组，也会特殊处理。也是把传入的list或数组封装在map中。
     * key：Collection（collection），如果是List还可以使用这个key(list) 数组(array)
     */
    public HttAdsCustom getAds(List<Integer> ids);
```

## #{} 与 ${} 区别
- `#{}`：是以预编译的形式，将参数设置到 SQL 语句中。(#{}会给参数加单引号)
- `${}`：取出的值直接拼装在SQL 语句中。（直接拼进SQL）

例如：`SELECT * FROM table WHERE name = #{name}`，加入传入参数为：Smith。则会转换成`SELECT * FROM table WHERE name = ‘Smith’`，而`SELECT * FROM table WHERE name = ${name}`，则会转换成`SELECT * FROM table WHERE name = Smith`。

一般传参我们使用#{}，但是有些时候是需要${}，例如：
- `SELECT * FROM ${year}_salary `
- `SELECT * FROM table ORDER BY ${XXX}`


MyBatis 对所有的 null 都映射成原生 JDBC 的 OTHER 类型

## select
- `parameterType`：可以不传，MyBatis会根据TypeHandler 自动推断。
- `resultType`：如果返回一个集合，要写集合中元素的类型。

## 自定义结果封装规则
```xml
    <!-- 自定义结果集封装规则 -->
    <resultMap id="baseResult" type="model.HttAdsCustom">
        <id column="id" property="id"/>
        <result column="xxx" property="xxx"/>
    </resultMap>
```

# 动态 SQL

## if
```xml
<!--
test:判断表达式（OGNL）
-->
<if test="id != null">
   id=#{id}
</if>
<!-- ognl会进行字符串与数字的转换判断 “0”==0 -->
<if test="gender == 0">
   AND gender = #{gender}
</if>
```

## where 标签
查询的时候如果某些条件没带可能sql拼装会有问题，解决办法：
- 给where后面加上1=1，以后的条件都and xxx
- mybatis使用where标签来将所有的查询条件包括在内，就会将where标签中拼装的sql，多出来的and或者or去掉
- where只会去掉 **第一个** 多出来的and或者or标签

### trim
```xml
        <!-- 后面多出的and或者or where标签不能解决
        prefix：前缀trim标签体中是整个字符串拼接后的结果
                prefix给拼串后的整个字符串加上一个前缀
        prefixOverrides：
                前缀覆盖：去掉整个字符串前面多余的字串
        suffix：给拼串后的整个字符串加上一个后缀
        suffixOverrides：去掉整个字符串后面多余的字串
         -->
        <trim prefix="" prefixOverrides="" suffix="" suffixOverrides=""/>
```

## choose
分支选择，类似于带了 break 的 swtich-case
只会进入其中的一个条件
```xml
<choose >
  <when test="id != null">
    id = #{id}
  </when>
  <when test="email != null">
    email = #{email}
  </when>
  <otherwise>
     gender = 0
   </otherwise>
</choose>
```

## set标签
把 `更新` 的内容放在 <set> 标签中，会自动把多余的逗号去掉。当然该标签也可以使用 trim 标签代替。

## foreach
遍历集合
```xml
       <!--
         collection：指定要遍历的集合
                list类型的参数会特殊处理封装在map中，map的key就叫做list
         item：将当前遍历出的元素赋值给指定的变量
         separator：每个元素之间的分隔符
         open：遍历出所有结果拼接一个开始的字符
         close：遍历出所有结果拼接一个结束的字符
         #{变量名}就能取出变量的值也就是当前遍历出的元素
         index：遍历list的时候，index就是索引，item就是当前值
                遍历map的时候，index表示的就是map的key，item就是map的值
        -->
        SELECT * FROM htt_ads_url WHERE id in
        <foreach collection="ids" item="item_id" separator="," open="(" close=")" index="">
            #{item_id}
        </foreach>
```

collection属性需要注意一下
> 1. 如果传入的是单参数且参数类型是一个List的时候，collection属性值为list
> 2. 如果传入的是单参数且参数类型是一个array数组的时候，collection的属性值为array
> 3. 如果传入的参数是多个的时候，我们就需要把它们封装成一个Map了，当然单参数也可

对于第三种情况的实例就是
```xml
<select id="dynamicForeach3Test" parameterType="java.util.HashMap" resultType="Blog">
         select * from t_blog where title like "%"#{title}"%" and id in
          <foreach collection="ids" index="index" item="item" open="(" separator="," close=")">
               #{item}
          </foreach>
 </select>
```

传入的参数为Map
```java
final List ids = new ArrayList();
ids.add(1);
ids.add(2);
ids.add(3);
ids.add(6);
ids.add(7);
ids.add(9);
Map params = new HashMap();
params.put("ids", ids);
params.put("title", "中国");
```

## 批量保存
方法一：
```xml
    <!-- public void addEmps(@Param("emps")List<Employee> emps) -->
    <!-- MySQL下批量保存：可以foreach遍历mysql支持values(),(),()语法 -->
    <insert id="addEmps">
        INSERT INTO employee(last_name,email,gender,d_id)
        VALUES 
        <foreach collection="emps" item="emp" separator=",">
            (#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
        </foreach>
    </insert>
```
方法二：
```xml
   <!-- 这种方式需要数据库连接属性allowMultiQueries=true -->
   <insert id="addEmps">
        <foreach collection="emps" item="emp" separator=";">
            INSERT INTO employee(last_name,email,gender,d_id)
            VALUES
            (#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
        </foreach>
    </insert>
```

## 抽取可重用的sql片段
定义sql片段
```xml
    <sql id="insertColumn">
        id,name,email
    </sql>
```
使用sql片段
```xml
<include refid="insertColumn"/>
```