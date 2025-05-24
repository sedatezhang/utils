# Spring Boot + MyBatis-Plus 项目创建教程

## 前提条件
- 已安装 JDK 1.8+
- 已安装 Maven
- 已安装 IDE（如 IntelliJ IDEA）

## 创建步骤

### 方法一：使用 Spring Initializr 网站
1. 打开 https://start.spring.io/
2. 选择以下配置：
   - Project: Maven
   - Language: Java
   - Spring Boot Version: 选择最新稳定版
   - Group: com.example
   - Artifact: demo
   - Name: demo
   - Package Name: com.example.demo
   - Packaging: Jar
   - Java Version: 1.8 或更高
3. 在 Dependencies 中搜索并添加：
   - Spring Web
   - MyBatis Framework
   - MySQL Driver
4. 点击 "Generate" 下载生成的项目压缩包
5. 解压压缩包并在 IDE 中打开项目

### 方法二：使用 IDEA 插件（以 IntelliJ IDEA 为例）
1. 打开 IntelliJ IDEA
2. 点击 "New Project"
3. 选择左侧的 "Spring Initializr"
4. 设置 JDK 版本
5. 填写 Group、Artifact 等信息
6. 在 Dependencies 中选择需要的依赖（同上）
7. 点击 "Next" -> "Finish" 完成创建

### 方法三：手动创建
1. 创建 Maven 项目
2. 在 pom.xml 中添加以下依赖：
```xml
<!-- Spring Boot Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<!-- Spring Boot Web Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- MyBatis-Plus Boot Starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.1</version>
</dependency>

<!-- MySQL Driver -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

3. 创建完成后的目录结构：
```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── demo
│   │               ├── DemoApplication.java
│   │               └── controller
│   │               ├── service
│   │               ├── mapper
│   │               └── entity
│   │
│   └── resources
│       ├── application.yml
│       └── Data
└── test
    └── java
        └── com
            └── example
                └── demo
                    └── DemoApplicationTests.java
```

### 配置数据库连接
在 application.yml 文件中添加数据库连接配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 配置 MyBatis-Plus（可选）
在 application.yml 文件中添加 MyBatis-Plus 配置：
```yaml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # 开启 SQL 日志输出
  mapper-locations: classpath*:mapper/**/*.xml  # 指定 mapper xml 文件位置
```

### 创建第一个实体类和 Mapper
1. 在 entity 包中创建实体类：
```java
package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_table")  // 对应数据库表名
public class User {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private Integer age;
    
    private String email;
}
```

2. 在 mapper 包中创建 Mapper 接口：
```java
package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;

public interface UserMapper extends BaseMapper<User> {
    // BaseMapper 已经提供了基本的 CRUD 方法
}
```

### 创建 Service 层
1. 在 service 包中创建 Service 接口：
```java
package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}
```

2. 在 service.impl 包中创建实现类：
```java
package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    // 这里可以添加自定义的业务逻辑
    public List<User> getAllUsers() {
        return query().list();
    }
    
    public User getUserById(Long id) {
        return getById(id);
    }
    
    public void saveUser(User user) {
        save(user);
    }
    
    public void updateUser(User user) {
        updateById(user);
    }
    
    public void deleteUser(Long id) {
        removeById(id);
    }
}
```

### 创建 Controller 层
在 controller 包中创建控制器：
```java
package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @PostMapping
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }
    
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
```

### 运行项目
1. 运行 DemoApplication.java 中的 main 方法
2. 访问 http://localhost:8080/users 测试 API 接口

## 总结
以上就是创建 Spring Boot 和 MyBatis-Plus 项目的完整教程。通过这几种不同的创建方式，您可以根据自己的需求选择最适合的方法。