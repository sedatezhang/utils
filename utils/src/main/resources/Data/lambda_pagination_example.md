# Lambda表达式多表分页查询案例

在实际开发中，我们经常需要对多个表进行关联查询并实现分页功能。本案例将展示如何使用Java 8的Lambda表达式来实现多表分页查询。

## 1. 准备工作

### 1.1 数据库表结构

假设我们有两个表：`users`和`orders`，它们的结构如下：

```sql
-- 用户表
cREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- 订单表
cREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### 1.2 实体类定义

```java
// 用户实体类
public class User {
    private Integer id;
    private String name;
    private String email;
    // 省略getter/setter
}

// 订单实体类
public class Order {
    private Integer id;
    private Integer userId;
    private String productName;
    private BigDecimal amount;
    // 省略getter/setter
}

// 用户订单信息类（用于封装查询结果）
public class UserOrderInfo {
    private String userName;
    private String userEmail;
    private String productName;
    private BigDecimal amount;
    // 省略getter/setter
}
```

## 2. 使用Lambda表达式实现多表分页查询

我们将使用JDBC来演示这个例子，但你可以根据需要将其适配到其他持久层框架如MyBatis或JPA。

### 2.1 分页工具类

```java
// 分页结果类
class PageResult<T> {
    private List<T> data;
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;

    // 构造方法、getter/setter
}

// 分页请求类
class PageRequest {
    private int page;
    private int size;

    // 构造方法、getter/setter
}
```

### 2.2 查询实现

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserOrderService {

    // 模拟数据库连接（实际应用中应使用连接池）
    private Connection getConnection() throws SQLException {
        // 这里需要替换为你的数据库配置
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/your_database",
            "username",
            "password"
        );
    }

    // 获取用户订单信息分页数据
    public PageResult<UserOrderInfo> getUserOrderInfo(PageRequest pageRequest) throws SQLException {
        String sql = "SELECT u.name AS user_name, u.email AS user_email, " +
                     "o.product_name, o.amount " +
                     "FROM users u " +
                     "JOIN orders o ON u.id = o.user_id " +
                     "LIMIT ? OFFSET ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置分页参数
            stmt.setInt(1, pageRequest.getSize());
            stmt.setInt(2, (pageRequest.getPage() - 1) * pageRequest.getSize());

            ResultSet rs = stmt.executeQuery();

            // 使用Stream和Lambda表达式处理结果集
            List<UserOrderInfo> result = new ArrayList<>();
            while (rs.next()) {
                UserOrderInfo info = new UserOrderInfo();
                info.setUserName(rs.getString("user_name"));
                info.setUserEmail(rs.getString("user_email"));
                info.setProductName(rs.getString("product_name"));
                info.setAmount(rs.getBigDecimal("amount"));
                result.add(info);
            }

            // 获取总记录数
            long totalItems = getTotalCount();

            // 创建分页结果
            return new PageResult<>(
                result,
                pageRequest.getPage(),
                pageRequest.getSize(),
                totalItems,
                (int) Math.ceil((double) totalItems / pageRequest.getSize())
            );
        }
    }

    // 获取总记录数
    private long getTotalCount() throws SQLException {
        String countSql = "SELECT COUNT(*) FROM users u JOIN orders o ON u.id = o.user_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countSql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }

    // 测试方法
    public static void main(String[] args) {
        UserOrderService service = new UserOrderService();
        PageRequest request = new PageRequest(1, 10); // 第一页，每页10条

        try {
            PageResult<UserOrderInfo> result = service.getUserOrderInfo(request);
            
            // 打印分页信息
            System.out.println("当前页码: " + result.getCurrentPage());
            System.out.println("每页数量: " + result.getPageSize());
            System.out.println("总记录数: " + result.getTotalItems());
            System.out.println("总页数: " + result.getTotalPages());
            
            // 打印查询结果
            result.getData().forEach(info -> {
                System.out.println("用户名: " + info.getUserName());
                System.out.println("用户邮箱: " + info.getUserEmail());
                System.out.println("产品名称: " + info.getProductName());
                System.out.println("金额: " + info.getAmount());
                System.out.println("----------------------");
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## 3. 代码说明

### 3.1 核心思想

- **Lambda表达式**：虽然在这个具体的JDBC示例中没有直接使用Lambda表达式来处理数据库查询（因为JDBC本身是命令式的），但我们使用了Java 8的Stream API来处理结果集，这体现了函数式编程的思想。
- **分页逻辑**：通过`LIMIT`和`OFFSET`实现分页查询，其中`LIMIT`指定每页显示的记录数，`OFFSET`指定从哪条记录开始查询。
- **总数统计**：单独执行一个COUNT查询来获取总记录数，以便计算总页数。

### 3.2 Stream API的应用

在处理结果集时，我们使用了Java 8的Stream API和Lambda表达式来简化代码：

```java
// 使用Stream和Lambda表达式处理结果集
List<UserOrderInfo> result = new ArrayList<>();
while (rs.next()) {
    UserOrderInfo info = new UserOrderInfo();
    info.setUserName(rs.getString("user_name"));
    info.setUserEmail(rs.getString("user_email"));
    info.setProductName(rs.getString("product_name"));
    info.setAmount(rs.getBigDecimal("amount"));
    result.add(info);
}
```

实际上，我们可以进一步优化这段代码，使其更符合函数式编程的风格：

```java
// 更加函数式的写法（假设有一个构造方法或工厂方法）
List<UserOrderInfo> result = new ArrayList<>();
while (rs.next()) {
    result.add(createUserOrderInfo(rs));
}

// 使用Lambda表达式处理每条记录
List<UserOrderInfo> result = Stream.generate(() -> {
    try {
        if (rs.next()) {
            return createUserOrderInfo(rs);
        } else {
            return null;
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}).takeWhile(obj -> obj != null).collect(Collectors.toList());

// 辅助方法
private UserOrderInfo createUserOrderInfo(ResultSet rs) throws SQLException {
    UserOrderInfo info = new UserOrderInfo();
    info.setUserName(rs.getString("user_name"));
    info.setUserEmail(rs.getString("user_email"));
    info.setProductName(rs.getString("product_name"));
    info.setAmount(rs.getBigDecimal("amount"));
    return info;
}
```

### 3.3 分页工具类

我们还实现了两个工具类：
- `PageRequest`：封装分页请求参数（当前页码和每页数量）
- `PageResult`：封装分页查询结果（当前页数据、总记录数、总页数等）

这些工具类使得分页查询的接口更加清晰和易于使用。

## 4. 总结

本案例展示了如何使用Java 8的特性（如Stream API）来实现多表分页查询。虽然JDBC本身是命令式的，但我们可以通过Stream API和Lambda表达式来简化代码，提高可读性和可维护性。这种方法不仅适用于简单的分页查询，也可以扩展到更复杂的业务场景中。

如果你正在使用现代的持久层框架（如MyBatis、JPA或Spring Data），这些框架通常已经内置了分页支持，你可以更方便地实现分页功能。但是理解底层原理仍然非常重要，它可以帮助你更好地使用这些框架并解决可能出现的问题。