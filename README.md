Mybatis联合查询
==============
1、实现多表查询的方式<br>
>>1.1、业务装配，对两个表编写单表查询语句，在业务层（service）吧查询的两个结果关联<br>
>>1.2、使用Auto Mapping特性，在实现两表联合查询时通过别名完成映射<br>
>>1.3、使用mybatis的<resultMap>标签进行实现<br>

2、多表查询时，类中包含另一个类的对象的分类<br>
>>2.1、单个对象<br>
>>2.2、多个对象<br>

resultMap标签
=============
1、<resultMap>标签写在mapper.xml中，由程序员控制sql查询结果和实体类的映射关系<br>
>>1.1、注：默认Mybatis使用Auto Mapping特性<br>

2、使用<resultMap>标签时，<select>标签不写resultType属性，而是使用resultMap属性引用resultMap标签<br>
3、使用resultMap实现单表映射关系<br>
>>3.1、mapper.xml,实体类的中的属性名可以和数据库中的属性名不一样<br>
```xml
 <resultMap type="com.liyan.pojo.Teacher" id="mymap" >
        <id column="id" property="id1" jdbcType="INTEGER"/>
        <result column="name" property="name1" jdbcType="VARCHAR"/>
    </resultMap>
```
>>3.2、数据库<br>
```sql
    create table teacher(
     id int not null PRIMARY KEY auto_increment COMMENT '教师编号',
     name varchar(30)not null COMMENT '教师名称'
    )COMMENT '教师表'
```
4、使用resultMap实现关联单个对象（N+1方式）
>>4.1、N+1查询方式，先查询出某个表的全部信息，根据这个表的信息查询另外一个表的信息<br>
>>4.2、与业务装配的区别：在service中写的代码由mybatis完成<br>
>>4.3、在student实体类中包含一个teacher对象<br>
```java
   
```
>>4.4、在teachermapper.xml中提供一个查询<br>
```xml
 <select id="findById" resultType="teacher" parameterType="int">
        select * from teacher where id=#{0}
    </select>
```
>>4.5、在studentmapper.xml中,resultMap标签中:property:表示对象在类中的属性名；association:装配一个对象使用;select:通过哪个方法查询出这个对象信息;column:当前表的哪个列的值作为参数传递给另外一个查询;注：前提使用N+1的方式时，如果列名和属性名相同可以不配置，使用Auto mapping特性，但是mybatis默认只会给列装配一次<br>
```xml
<resultMap type="com.liyan.pojo.Student" id="stumap" >
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="age" property="age"/>
        <result column="tid" property="tid"/>
        <!--如果关联一个对象-->
        <association property="teacher" select="mapper.TeacherMapper.findById" column="tid">

        </association>
    </resultMap>
    <select id="findAll1" resultMap="stumap">
        select * from student
    </select>
```
>>4.6、N+1方式和联合查询方式对比<br>
>>4.7、>>N+1不确定时<br>
>>4.8、联合查询：需求中确定查询时两个表一定都要查询<br>、

5、resultMap查询关联集合对象N+1<br>
>>5.1、在teacher实体类中添加一个List<Student>
```java
   
```
>>5.2、在teachermapper.xml;collection:当属性是集合类型时使用；ofType：指定对象类型的<br>
```xml
<resultMap type="com.liyan.pojo.Teacher" id="mymap" >
        <id column="id" property="id" jdbcType="INTEGER"/>
        <result column="name" property="name" jdbcType="VARCHAR"/>
        <collection property="list" select="mapper.StudentMapper.findByTid" column="id" ofType="student">

        </collection>
    </resultMap>
 <select id="findAll" resultMap="mymap">
            select * from teacher 
        </select>
```
>>5.3、在studentmapper.xml中<br>
```xml
  <select id="findByTid" resultType="student" parameterType="int" >
        select * from student where tid=#{0}
    </select>
```
6、resultMap查询关联集合对象（联合查询方式）<br>
>>6.1、在teacher实体类中添加;mybatis可以通过主键判断对象是否被加载过，不需担心创建重复的对象<br>
```xml
<resultMap id="mymap1" type="com.liyan.pojo.Teacher">
        <id column="tid" property="id"/>
        <result column="tname" property="name"/>
        <collection property="list" ofType="student" >
            <id column="sid" property="id"/>
            <result column="sname" property="name"/>
            <result column="age" property="age"/>
            <result column="tid" property="tid"/>
        </collection>
    </resultMap>
<select id="findAll1" resultMap="mymap1">
        select t.id tid,t.`name` tname,s.id sid,s.`name` sname,age,tid from teacher t LEFT JOIN student s ON t.id=s.tid
    </select>
```