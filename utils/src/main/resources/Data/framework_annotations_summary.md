# Spring、Spring Boot、MyBatis和MyBatis-Plus框架对比

## 框架简介

### Spring
Spring是一个开源的Java/Java EE全功能应用程序框架，提供了企业级应用程序开发的全面基础设施支持。它的核心特性包括依赖注入（DI）和面向切面编程（AOP）。

### Spring Boot
Spring Boot是基于Spring框架的扩展，旨在简化新Spring应用的初始搭建以及开发。它通过自动配置和起步依赖减少了大量的XML配置和样板代码。

### MyBatis
MyBatis是一个优秀的持久层框架，它支持定制化SQL、存储过程以及高级映射。MyBatis避免了几乎所有的JDBC代码和手动设置参数以及获取结果集的工作。

### MyBatis-Plus
MyBatis-Plus是在MyBatis基础上的增强工具，旨在简化开发、提高效率。它提供了许多便捷的功能，如通用CRUD操作、条件构造器、分页插件等。

## 相同点

1. **Java生态**：这四个框架都属于Java生态系统的一部分，用于构建Java应用程序。
2. **模块化设计**：它们都采用了模块化的设计理念，允许开发者根据需要选择使用哪些模块。
3. **开源**：都是开源项目，拥有活跃的社区支持。
4. **与数据库交互**：Spring和MyBatis系列框架都可以与数据库进行交互，提供数据持久化的解决方案。
5. **注解支持**：都支持使用注解来简化配置和开发。
6. **可集成性**：这些框架之间可以相互集成，例如Spring Boot可以很容易地集成MyBatis或MyBatis-Plus。

## 不同点

| 特性/框架         | Spring                          | Spring Boot                      | MyBatis                         | MyBatis-Plus                    |
|------------------|----------------------------------|----------------------------------|----------------------------------|----------------------------------|
| **主要用途**      | 全功能应用程序框架               | 快速开发Spring应用                | 持久层框架                       | MyBatis增强工具                  |
| **配置复杂度**    | 配置相对繁琐                     | 自动配置，配置简单                | 需要手动配置SQL映射              | 提供默认配置，简化MyBatis使用     |
| **学习曲线**      | 较陡峭                           | 相对平缓                          | 中等                             | 简单                            |
| **启动方式**      | 需要外部容器                     | 内嵌服务器，可以直接运行          | 依赖于其他框架或容器             | 可以独立运行也可以集成其他框架    |
| **ORM支持**       | 提供ORM支持（如与Hibernate集成） | 提供ORM支持（默认使用Spring Data）| 不是ORM框架，需要手动编写SQL      | 提供基本的ORM功能                |
| **SQL控制**       | 通过各种模板引擎间接控制           | 通过各种模板引擎间接控制           | 完全控制SQL语句                   | 完全控制SQL语句                  |
| **CRUD操作**      | 提供基础支持                     | 提供基础支持                      | 需要手动实现                      | 提供通用CRUD操作                 |
| **条件查询**      | 提供基础支持                     | 提供基础支持                      | 需要手动拼接SQL                   | 提供条件构造器                   |
| **分页支持**      | 需要额外配置                     | 需要额外配置                      | 需要手动实现                      | 提供分页插件                     |
| **性能**          | 适中                             | 适中                              | 高                               | 高                              |
| **适合场景**      | 大型企业级应用                   | 快速开发微服务或小型应用          | 需要精细控制SQL的场景            | 需要快速开发且能控制SQL的场景     |

## 总结

Spring是一个全面的企业级应用开发框架，提供了广泛的基础设施支持；
Spring Boot在Spring的基础上进一步简化了开发，特别适合快速开发微服务和中小型应用；
MyBatis是一个轻量级的持久层框架，给予开发者完全的SQL控制权；
MyBatis-Plus则是在MyBatis基础上的增强，提供了更多便捷功能，提高了开发效率。

这四个框架各有特点，可以根据项目的具体需求和技术栈选择合适的框架组合。

# 框架注解总结

本文件汇总了Spring、Spring Boot、MyBatis和MyBatis-Plus框架中常用的注解。

## Spring框架注解

### 核心容器注解
- `@Component`：标记一个类为Spring组件
- `@Service`：标记一个类为服务层组件
- `@Repository`：标记一个类为数据访问层组件
- `@Controller`：标记一个类为控制器组件（返回视图名称）
- `@RestController`：结合`@Controller`和`@ResponseBody`
- `@Bean`：在配置类中定义Bean
- `@Configuration`：标记配置类
- `@Scope`：定义Bean的作用域

### 自动注入注解
- `@Autowired`：自动注入依赖
- `@Qualifier`：配合`@Autowired`使用，指定Bean名称
- `@Resource`：按名称注入Bean
- `@Value`：注入属性值

### AOP相关注解
- `@Aspect`：定义切面类
- `@Pointcut`：定义切入点
- `@Before`：前置通知
- `@After`：后置通知
- `@Around`：环绕通知
- `@AfterReturning`：返回通知
- `@AfterThrowing`：异常通知

### 测试相关注解
- `@RunWith(SpringRunner.class)`：集成测试运行器
- `@SpringBootTest`：集成测试加载上下文

## Spring Boot框架注解

### 启动与配置注解
- `@SpringBootApplication`：组合注解，包含`@Configuration`、`@EnableAutoConfiguration`和`@ComponentScan`
- `@EnableAutoConfiguration`：启用自动配置
- `@ConditionalOnClass`：当类路径上有指定类时才生效
- `@ConditionalOnMissingClass`：当类路径上缺少指定类时才生效
- `@ConditionalOnBean`：当容器中有指定Bean时才生效
- `@ConditionalOnMissingBean`：当容器中没有指定Bean时才生效
- `@ConditionalOnProperty`：当配置文件中指定的属性存在且值匹配时才生效

### Web应用注解
- `@EnableWebMvc`：启用Web MVC功能
- `@RestControllerAdvice`：全局异常处理
- `@ControllerAdvice`：全局控制器增强
- `@ExceptionHandler`：方法级别的异常处理

## MyBatis框架注解

### 映射注解
- `@Select`：定义查询SQL
- `@Insert`：定义插入SQL
- `@Update`：定义更新SQL
- `@Delete`：定义删除SQL
- `@Results`：定义结果映射
- `@Result`：单个结果映射
- `@One`：一对一关联映射
- `@Many`：一对多关联映射

### 配置注解
- `@Mapper`：标记接口为MyBatis Mapper
- `@Param`：指定参数名称
- `@Options`：设置操作选项（如主键生成策略）

## MyBatis-Plus框架注解

### 实体类注解
- `@Data`：Lombok注解，生成getter/setter等方法
- `@TableName`：指定实体类对应的表名
- `@TableId`：标记主键字段并指定主键类型
- `@TableField`：标记普通字段，可设置字段名、是否为逻辑删除字段等
- `@Version`：标记乐观锁版本号字段
- `@LogicDelete`：标记逻辑删除字段

### 查询条件构造器注解
- `@QueryWrapper`：查询条件构造器
- `@UpdateWrapper`：更新条件构造器

### 其他工具注解
- `@SqlParser`：SQL解析控制
- `@KeySequence`：序列主键生成策略（适用于Oracle等数据库）

## 总结

以上是对Spring、Spring Boot、MyBatis和MyBatis-Plus框架中常用注解的分类总结。这些注解可以帮助开发者更高效地进行开发工作，提高代码质量和可维护性。