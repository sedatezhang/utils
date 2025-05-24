# Swagger 常用注解说明

Swagger 是一个用于生成、描述、调用和可视化 RESTful 风格 Web 服务的框架。它提供了一整套注解来帮助开发者更好地描述 API 接口信息。

## 常用注解列表

### `@Api`
- **用途**: 描述一个 Controller 类的功能。
- **常用属性**:
  - `value`：简要说明该类的作用。
  • `tags`：指定接口分组标签。

### `@ApiParam`
- **用途**: 描述方法参数的信息。
- **常用属性**:
  - `name`：参数名。
  - `value`：参数的简要说明。
  - `required`：是否为必填项。
  - `example`：参数示例值。

### `@ApiModelProperty`
- **用途**: 描述 Model 类中的字段信息。
- **常用属性**:
  - `value`：字段的简要说明。
  - `example`：字段的示例值。
  - `required`：是否为必填字段。
  - `hidden`：设置为 `true` 可隐藏该字段，不在文档中显示。



### `@ApiOperation`
- **用途**: 描述一个方法（API 接口）的具体功能。
- **常用属性**:
  - `value`：接口功能的简短描述。
  - `notes`：接口的详细说明。
  - `httpMethod`：HTTP 请求方法（如 GET, POST 等）。


### `@ApiModel`
- **用途**: 描述一个 Model 类的功能，通常用于请求或响应对象。
- **常用属性**:
  - `value`：模型的简要说明。
  - `description`：模型的详细描述。


### `@ApiResponses`
- **用途**: 描述接口可能返回的错误码或其他状态码。
- **常用属性**:
  - `value`：包含多个 `@ApiResponse` 注解。

### `@ApiResponse`
- **用途**: 描述单个响应状态码及其含义。
- **常用属性**:
  - `code`：HTTP 状态码（如 200, 404 等）。
  - `message`：状态码的描述信息。
  - `response`：返回的数据类型。

## 示例代码片段

```java
/**
 * @program: MyProject
 * @description: 用户管理相关接口
 * @author: 痞子
 * @create: 2023-03-24 21:36:29
 */
@Api(value = "用户管理", tags = {"用户管理"})
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "根据ID查询用户", notes = "返回单个用户信息")
    @ApiResponses({
        @ApiResponse(code = 200, message = "成功获取用户信息"),
        @ApiResponse(code = 404, message = "用户不存在")
    })
    @GetMapping("/{id}")
    public User getUserById(@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable String id) {
        return userService.getUserById(id);
    }
}