# Java 8 新特性全面解析

## 一、Lambda表达式

### 1. 基本概念

Lambda表达式是Java 8最重要的新特性之一，它允许我们将函数作为一个方法的参数或将一段代码视为数据来处理。

```java
// Lambda表达式基本语法：(参数) -> { 表达式或语句 }
List<String> list = Arrays.asList("apple", "banana", "orange");

// 使用Lambda表达式遍历列表
list.forEach(item -> System.out.println(item));

// 更复杂的Lambda表达式
list.forEach(item -> {
    String upperCaseItem = item.toUpperCase();
    System.out.println("Processing: " + upperCaseItem);
});
```

### 2. 函数式接口

函数式接口是指仅包含一个抽象方法的接口。Java 8提供了很多内置的函数式接口，如Function<T,R>, Predicate<T>, Consumer<T>等。

```java
// Predicate<T> 示例
Predicate<String> startsWithA = s -> s.startsWith("a");
System.out.println(startsWithA.test("apple")); // true

// Function<T,R> 示例
Function<String, Integer> stringLength = String::length;
System.out.println(stringLength.apply("hello")); // 5

// Consumer<T> 示例
Consumer<String> printer = s -> System.out.println(s.length());
printer.accept("Java 8");
```

### 3. 方法引用

方法引用提供了一种更简洁的语法来引用已有方法。

```java
// 方法引用示例
list.forEach(System.out::println);

// 构造函数引用
Supplier<List<String>> listCreator = ArrayList::new;
List<String> newList = listCreator.get();
```

## 二、Stream API

### 1. 创建Stream

```java
// 通过集合创建Stream
List<String> list = Arrays.asList("apple", "banana", "orange");
Stream<String> stream = list.stream();

// 通过数组创建
String[] array = {"apple", "banana", "orange"};
Stream<String> arrayStream = Arrays.stream(array);

// 使用Stream.of()
Stream<String> ofStream = Stream.of("apple", "banana", "orange");
```

### 2. 中间操作

```java
// filter过滤
List<String> filtered = list.stream()
    .filter(s -> s.startsWith("a"))
    .collect(Collectors.toList());

// map映射
List<Integer> lengths = list.stream()
    .map(String::length)
    .collect(Collectors.toList());

// flatMap扁平化
List<List<String>> lists = Arrays.asList(
    Arrays.asList("a", "b"),
    Arrays.asList("c", "d")
);
List<String> merged = lists.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
```

### 3. 终止操作

```java
// forEach遍历
list.forEach(System.out::println);

// reduce归约
Optional<Integer> sum = numbers.stream()
    .reduce(Integer::sum);

// collect收集
List<String> filteredAndUpperCased = list.stream()
    .filter(s -> s.startsWith("a"))
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// 分组统计
Map<Character, List<String>> groupedByFirstLetter = list.stream()
    .collect(Collectors.groupingBy(s -> s.charAt(0)));
```

## 三、默认方法

### 1. 接口中定义默认方法

```java
// 定义默认方法
interface MyInterface {
    default void defaultMethod() {
        System.out.println("Default method implementation");
    }
}

// 多个默认方法冲突的解决
interface InterfaceA {
    default void method() {
        System.out.println("Interface A method");
    }
}

interface InterfaceB {
    default void method() {
        System.out.println("Interface B method");
    }
}

class MyClass implements InterfaceA, InterfaceB {
    // 必须显式重写解决冲突
    @Override
    public void method() {
        InterfaceB.super.method(); // 明确选择调用哪一个实现
    }
}
```

## 四、新的日期时间API

### 1. 核心API

```java
// LocalDate表示日期
LocalDate today = LocalDate.now();
int year = today.getYear();
int monthValue = today.getMonthValue();
int dayOfMonth = today.getDayOfMonth();

// LocalTime表示时间
LocalTime now = LocalTime.now();
int hour = now.getHour();
int minute = now.getMinute();
int second = now.getSecond();

// LocalDateTime表示日期和时间
LocalDateTime current = LocalDateTime.now();

// ZonedDateTime表示带时区的时间
ZoneId zone = ZoneId.of("Europe/London");
ZonedDateTime zonedNow = ZonedDateTime.now(zone);
```

### 2. 日期计算

```java
// 添加一天
LocalDate tomorrow = today.plusDays(1);

// 减去一周
LocalDate lastWeek = today.minusWeeks(1);

// 时间调整器
LocalDate customDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
```

### 3. 日期格式化

```java
// 日期转字符串
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String formatted = current.format(formatter);

// 字符串转日期
String input = "2023-09-15 10:30:00";
LocalDateTime parsed = LocalDateTime.parse(input, formatter);
```

## 五、Optional类

### 1. 创建Optional对象

```java
// 创建空的Optional
Optional<String> emptyOpt = Optional.empty();

// 创建非空Optional
Optional<String> optWithValue = Optional.of("Hello");

// 创建可能为空的Optional
Optional<String> optional = Optional.ofNullable(null);
```

### 2. 使用Optional

```java
// 使用isPresent检查是否有值
if (optional.isPresent()) {
    System.out.println(optional.get().toUpperCase());
}

// 使用ifPresent处理值
optional.ifPresent(value -> System.out.println(value.toUpperCase()));

// 获取值或提供默认值
String result = optional.orElse("Default Value");

// 使用map转换值
Optional<Integer> length = optional.map(String::length);
```

## 六、新的JavaScript引擎Nashorn

```java
// 使用Nashorn执行JavaScript代码
ScriptEngineManager manager = new ScriptEngineManager();
ScriptEngine engine = manager.getEngineByName("JavaScript");

// 执行简单的JavaScript代码
try {
    engine.eval("print('Hello from JavaScript')");
    
    // 在Java中执行更复杂的JavaScript
    engine.eval("function multiply(a, b) { return a * b; }");
    Invocable invocable = (Invocable) engine;
    Object result = invocable.invokeFunction("multiply", 5, 6);
    System.out.println("Result: " + result); // Result: 30
} catch (Exception e) {
    e.printStackTrace();
}
```

## 七、CompletableFuture（异步编程）

```java
// 创建CompletableFuture
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // 模拟耗时操作
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return "Hello World";
});

// 处理结果
future.thenAccept(result -> System.out.println("Received: " + result));

// 异常处理
future.exceptionally(ex -> {
    System.out.println("Error: " + ex.getMessage());
    return "Fallback value";
});
```

## 八、其他重要改进

### 1. HashMap性能优化

在Java 8中，当HashMap发生哈希碰撞时，在链表长度大于阈值（默认是8）时会自动转为红黑树结构，提高查找效率。

### 2. 新的集合排序方法

```java
// 对列表进行排序
List<String> names = Arrays.asList("John", "Adam", "Eve");
names.sort((o1, o2) -> o1.compareTo(o2));

// 或者更简洁的写法
names.sort(Comparator.naturalOrder());
```

### 3. 重复注解

```java
// 定义可重复注解
@Repeatable(Schedules.class)
@interface Schedule {
    String time();
}

@interface Schedules {
    Schedule[] value();
}

// 使用重复注解
@Schedule(time = "10:00")
@Schedule(time = "15:00")
public class Task {
    // ...
}
```

### 4. 类型注解

```java
// 在任何使用类型的场合都可以添加注解
List<@NotNull String> strings = new ArrayList<>();

// 自定义类型注解
@Target(ElementType.TYPE_USE)
@interface NonNull {
    String value() default "non-null";
}

// 使用类型注解
public class Example {
    // 在创建对象时使用类型注解
    List<@NonNull String> createList() {
        return new ArrayList<>();
    }
}
```

## 九、Java 8新特性总结

| 特性 | 主要优势 | 应用场景 |
| --- | --- | --- |
| Lambda表达式 | 简化匿名内部类，提升代码简洁性和可读性 | 集合操作、并行处理、事件监听等 |
| Stream API | 提供声明式的数据处理方式，支持顺序和并行操作 | 数据过滤、转换、聚合等操作 |
| 默认方法 | 使接口能够向后兼容地添加新方法 | 接口演化、多重继承 |
| 新的日期时间API | 不变性设计，线程安全，更好的API设计 | 日期计算、格式化、时区处理 |
| Optional类 | 提高null值处理的安全性，减少空指针异常 | 空值处理、Optional返回值 |
| Nashorn | 提供高性能的JavaScript运行环境 | 动态语言集成、轻量级脚本处理 |
| CompletableFuture | 支持功能强大的异步编程模型 | 异步任务处理、多个任务组合编排 |
| 重复注解 | 支持对同一元素多次应用相同类型的注解 | 多配置选项、多规则校验 |
| 类型注解 | 在更多上下文中使用注解 | 编译期检查、类型安全性验证 |

## 十、最佳实践建议

### 1. Lambda表达式使用原则
- 保持Lambda表达式简短（建议不超过3行）
- 优先使用方法引用
- 避免在Lambda表达式中修改外部变量
- 对复杂逻辑仍应使用普通方法

### 2. Stream API使用时机
- 对大数据量集合使用parallelStream
- 对小数据集谨慎使用Stream（避免过度开销）
- 短路操作（如findFirst）在并行流中表现更好
- 在终端操作中避免副作用

### 3. 默认方法的设计考量
- 解决接口演进问题
- 提供默认实现以简化开发
- 注意多重继承的冲突解决
- 尽量避免在业务接口中大量使用

### 4. 新日期时间API的优势
- 不可变对象设计，线程安全
- 设计更加直观易用
- 支持本地线程安全的格式化
- 清晰的API设计，易于理解

### 5. Optional类的注意事项
- 不应在实体类中使用Optional
- 不应用于作为集合元素类型
- 不替代所有null检查
- 避免过度嵌套使用

### 6. 关于新特性的常见反模式
- 过度使用链式调用导致代码难以调试
- 在简单操作中使用Steam带来的额外开销
- Lambda表达式中包含过多业务逻辑
- 过度依赖并行流导致资源争用
- 错误地使用默认方法引发的多重继承问题