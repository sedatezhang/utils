# Java Stream API 使用指南

## 一、Stream概述

Stream API是Java 8引入的重要特性，它允许我们以声明式的方式处理数据集合。Stream不是一种数据结构，而是用于操作数据源（如集合、数组等）的API管道。

### Stream的特点：
- 不存储数据
- 操作不会修改数据源
- 支持链式调用
- 可以是顺序执行或并行执行
- 支持函数式编程风格

## 二、创建Stream

### 1. 通过集合创建
```java
List<String> list = Arrays.asList("apple", "banana", "orange");
Stream<String> stream = list.stream(); // 获取顺序流
Stream<String> parallelStream = list.parallelStream(); // 获取并行流
```

### 2. 通过数组创建
```java
String[] array = {"apple", "banana", "orange"};
Stream<String> stream = Arrays.stream(array);
```

### 3. 使用Stream.of()方法
```java
Stream<String> stream = Stream.of("apple", "banana", "orange");
```

### 4. 生成无限流
```java
// 生成递增数字流
Stream<Integer> infiniteStream = Stream.iterate(0, n -> n + 1);

// 生成随机数流
Stream<Double> randomStream = Stream.generate(Math::random);
```

## 三、中间操作

### 1. filter - 过滤元素
```java
// 过滤出以"a"开头的元素
List<String> filteredList = list.stream()
    .filter(item -> item.startsWith("a"))
    .collect(Collectors.toList());
```

### 2. map - 映射转换
```java
// 将字符串列表转换为长度列表
List<Integer> lengths = list.stream()
    .map(String::length)
    .collect(Collectors.toList());
```

### 3. flatMap - 扁平化映射
```java
// 合并多个列表
List<List<String>> lists = Arrays.asList(
    Arrays.asList("a", "b"),
    Arrays.asList("c", "d")
);

List<String> merged = lists.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
```

### 4. distinct - 去重
```java
// 去除重复元素
List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 1);
List<Integer> uniqueNumbers = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
```

### 5. sorted - 排序
```java
// 按字符串长度排序
List<String> words = Arrays.asList("apple", "banana", "cherry", "date");
List<String> sortedByLength = words.stream()
    .sorted((s1, s2) -> s1.length() - s2.length())
    .collect(Collectors.toList());
```

### 6. peek - 查看元素
```java
// 查看处理过程中的元素
List<Integer> processed = list.stream()
    .peek(System.out::println)
    .map(String::length)
    .peek(len -> System.out.println("Length: " + len))
    .collect(Collectors.toList());
```

### 7. limit - 限制数量
```java
// 获取前3个元素
List<String> firstThree = list.stream()
    .limit(3)
    .collect(Collectors.toList());
```

### 8. skip - 跳过元素
```java
// 跳过前3个元素
List<String> skipped = list.stream()
    .skip(3)
    .collect(Collectors.toList());
```

## 四、终止操作

### 1. forEach - 遍历元素
```java
// 打印所有元素
list.forEach(System.out::println);
```

### 2. toArray - 转换为数组
```java
// 转换为数组
String[] array = list.stream()
    .toArray(String[]::new);
```

### 3. collect - 收集结果
```java
// 收集为列表
List<String> collectedList = list.stream()
    .filter(s -> s.startsWith("a"))
    .collect(Collectors.toList());

// 收集为字符串
String joined = list.stream()
    .map(String::toUpperCase)
    .collect(Collectors.joining(", ", "[", "]"));
```

### 4. reduce - 归约操作
```java
// 计算总和
List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
int sum = values.stream()
    .reduce(0, Integer::sum);

// 查找最大值
Optional<Integer> max = values.stream()
    .reduce(Integer::max);
```

### 5. min/max - 查找最值
```java
// 查找最长字符串
Optional<String> longest = list.stream()
    .max(Comparator.comparingInt(String::length));
```

### 6. count - 计算元素数量
```java
// 计算满足条件的元素数量
long count = list.stream()
    .filter(s -> s.startsWith("a"))
    .count();
```

### 7. anyMatch/allMatch/noneMatch - 匹配检查
```java
// 检查是否有以"a"开头的元素
boolean hasA = list.stream()
    .anyMatch(s -> s.startsWith("a"));

// 检查是否全部以"a"开头
boolean allA = list.stream()
    .allMatch(s -> s.startsWith("a"));

// 检查是否没有以"z"开头的元素
boolean noneZ = list.stream()
    .noneMatch(s -> s.startsWith("z"));
```

## 五、收集器操作

### 1. groupingBy - 分组统计
```java
// 按首字母分组
Map<Character, List<String>> groupedByFirstLetter = list.stream()
    .collect(Collectors.groupingBy(s -> s.charAt(0)));

// 多级分组（按首字母和字符串长度）
Map<Character, Map<Integer, List<String>>> groupedByFirstLetterAndLength = list.stream()
    .collect(Collectors.groupingBy(
        s -> s.charAt(0),
        Collectors.groupingBy(String::length)
    ));
```

### 2. partitioningBy - 分区操作
```java
// 按照长度是否大于5进行分区
Map<Boolean, List<String>> partitioned = list.stream()
    .collect(Collectors.partitioningBy(s -> s.length() > 5));
```

### 3. summarizingInt - 聚合统计
```java
// 获取长度的统计信息（平均值、最大值、最小值、总数）
IntSummaryStatistics statistics = list.stream()
    .mapToInt(String::length)
    .summaryStatistics();

System.out.println("Average length: " + statistics.getAverage());
System.out.println("Max length: " + statistics.getMax());
```

### 4. joining - 字符串连接
```java
// 使用指定分隔符连接字符串
String joined = list.stream()
    .map(String::toUpperCase)
    .collect(Collectors.joining(", "));
```

## 六、并行流处理

### 1. 使用parallelStream
```java
// 并行计算元素总和
int sum = numbers.parallelStream()
    .mapToInt(Integer::intValue)
    .sum();
```

### 2. 自定义并行流
```java
// 自定义线程池执行并行流
ForkJoinPool customThreadPool = new ForkJoinPool(4);
List<String> result = customThreadPool.submit(() ->
    list.parallelStream()
        .map(String::toUpperCase)
        .collect(Collectors.toList())
).join();
```

## 七、高级应用

### 1. 数据处理管道
```java
// 构建一个数据处理管道
List<String> result = list.stream()
    .filter(s -> s.startsWith("a"))
    .map(String::toUpperCase)
    .sorted()
    .limit(10)
    .collect(Collectors.toList());
```

### 2. 文件读取处理
```java
// 读取文件内容并过滤
try (Stream<String> lines = Files.lines(Paths.get("data.txt"))) {
    List<String> filteredLines = lines
        .filter(line -> line.contains("important"))
        .collect(Collectors.toList());
}
```

### 3. 大数据量处理
```java
// 处理大数据时建议使用并行流
List<Record> processedRecords = records.parallelStream()
    .filter(r -> r.getStatus() == Status.ACTIVE)
    .map(r -> updateRecord(r))
    .limit(10000)
    .collect(Collectors.toConcurrentMap(
        Record::getId,
        Function.identity(),
        (existing, replacement) -> existing
    )).values().stream()
    .collect(Collectors.toList());
```

## 八、最佳实践

### 1. 何时使用Stream？
- 当需要对集合进行转换、筛选、聚合等操作时
- 当需要编写更简洁、可读性更高的代码时
- 当需要利用并行流提高处理速度时

### 2. 注意事项
- 避免在小数据量上使用，会增加开销
- 保持Lambda表达式简短（建议不超过3行）
- 无状态操作性能更好
- 短路操作（如findFirst）在并行流中可能更快
- 在终端操作中避免副作用（如修改共享变量）

### 3. 性能优化技巧
- 对大数据量使用并行流
- 对于简单操作考虑使用普通循环
- 使用适当的数据结构（如toSet代替toList来去重）
- 避免不必要的装箱拆箱
- 使用合适的数据预分配大小（如指定初始容量）