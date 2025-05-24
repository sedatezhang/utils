# Lambda表达式对比示例

## 1. 线程创建

### 传统匿名内部类方式
```java
// 创建线程的传统方式（匿名内部类）
Thread thread1 = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello from thread 1");
    }
});
thread1.start();
```

### Lambda表达式方式
```java
// 创建线程的Lambda表达式方式
Thread thread2 = new Thread(() -> {
    System.out.println("Hello from thread 2");
});
thread2.start();
```

## 2. 集合遍历

### 传统循环方式
```java
// 传统方式遍历列表
List<String> list = Arrays.asList("apple", "banana", "orange");
for (String item : list) {
    System.out.println(item);
}
```

### Lambda表达式方式
```java
// 使用Lambda表达式遍历列表
list.forEach(item -> System.out.println(item));
```

### 方法引用简化方式
```java
// 使用方法引用简化遍历
list.forEach(System.out::println);
```

## 3. 事件监听器

### 传统匿名内部类方式
```java
// 传统方式添加按钮点击监听器
button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button clicked!");
    }
});
```

### Lambda表达式方式
```java
// Lambda表达式方式添加按钮点击监听器
button.addActionListener(e -> {
    System.out.println("Button clicked with lambda!");
});
```

## 4. 排序

### 传统比较器方式
```java
// 传统方式使用比较器排序列表
List<String> names = Arrays.asList("John", "Adam", "Eve");
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
});
```

### Lambda表达式方式
```java
// 使用Lambda表达式排序列表
Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
```

## 5. 过滤（Filter）

### 传统循环方式
```java
// 传统方式过滤列表
List<String> filteredList1 = new ArrayList<>();
for (String item : list) {
    if (item.startsWith("a")) {
        filteredList1.add(item);
    }
}
```

### Lambda表达式方式
```java
// Lambda表达式方式过滤列表
List<String> filteredList2 = list.stream()
    .filter(item -> item.startsWith("a"))
    .collect(Collectors.toList());
```

## 6. 映射（Map）

### 传统循环方式
```java
// 传统方式转换列表元素
List<Integer> lengths1 = new ArrayList<>();
for (String item : list) {
    lengths1.add(item.length());
}
```

### Lambda表达式方式
```java
// Lambda表达式方式转换列表元素
List<Integer> lengths2 = list.stream()
    .map(String::length)
    .collect(Collectors.toList());
```

## 7. 归约（Reduce）

### 传统循环方式
```java
// 传统方式计算总和
int sum1 = 0;
for (Integer value : values) {
    sum1 += value;
}
```

### Lambda表达式方式
```java
// Lambda表达式方式计算总和
int sum2 = values.stream()
    .reduce(0, Integer::sum);
```

## 8. 方法引用

### Lambda表达式方式
```java
// 使用Lambda表达式
list.forEach(item -> System.out.println(item));
```

### 方法引用方式
```java
// 使用方法引用
list.forEach(System.out::println);
```

## 9. 构造函数引用

### 传统匿名内部类方式
```java
// 传统方式创建对象
Supplier<List<String>> supplier1 = new Supplier<List<String>>() {
    @Override
    public List<String> get() {
        return new ArrayList<>();
    }
};
```

### 构造函数引用方式
```java
// 使用构造函数引用
Supplier<List<String>> supplier2 = ArrayList::new;
```

## 总结

| 特性               | 传统匿名内部类                     | Lambda表达式                          |
|--------------------|----------------------------------|---------------------------------------|
| 代码简洁性         | 冗长，需要显式实现接口方法       | 简洁，一行代码可完成操作             |
| 可读性             | 易读，但包含很多模板代码         | 更高，关注核心逻辑                   |
| 维护成本           | 较高，需要维护多个匿名类         | 低，代码集中且易于修改               |
| this引用含义       | 指向外部类的实例                 | 直接指向当前作用域的this             |
| 方法引用支持       | 不支持                           | 支持                                |
| 适用于             | 所有Java版本                     | Java 8及以上                         |