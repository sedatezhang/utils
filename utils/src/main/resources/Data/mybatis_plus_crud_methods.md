# MyBatis-Plus 内置 CRUD 方法详解

MyBatis-Plus 是一个 MyBatis 的增强工具，提供了许多便捷的内置方法来简化数据库操作。以下是一些常用的 CRUD（创建、读取、更新、删除）方法及其使用示例。

## 1. 创建（Create）

### `insert`
- **用途**: 插入一条记录。
- **示例**:
```
User user = new User();
user.setName("张三");
user.setAge(25);
user.setEmail("zhangsan@example.com");
userMapper.insert(user);
```

## 2. 查询（Read）

### `selectById`
- **用途**: 根据主键 ID 查询一条记录。
- **示例**:
```
User user = userMapper.selectById(1L);
System.out.println(user);
```

### `selectList`
- **用途**: 查询所有记录。
- **示例**:
```
List<User> users = userMapper.selectList(null);
users.forEach(System.out::println);
```

### `selectByMap`
- **用途**: 根据 Map 条件查询记录。
- **示例**:
```
Map<String, Object> map = new HashMap<>();
map.put("name", "张三");
List<User> users = userMapper.selectByMap(map);
users.forEach(System.out::println);
```

## 3. 更新（Update）

### `updateById`
- **用途**: 根据主键 ID 更新记录。
- **示例**:
```
User user = userMapper.selectById(1L);
user.setAge(30);
userMapper.updateById(user);
```

## 4. 删除（Delete）

### `deleteById`
- **用途**: 根据主键 ID 删除记录。
- **示例**:
```
userMapper.deleteById(1L);
```

### `deleteByMap`
- **用途**: 根据 Map 条件删除记录。
- **示例**:
```
Map<String, Object> map = new HashMap<>();
map.put("name", "张三");
userMapper.deleteByMap(map);
```

## 总结

以上是 MyBatis-Plus 中一些常用的 CRUD 方法。通过这些方法可以快速实现对数据库的基本操作，而无需编写复杂的 SQL 语句。