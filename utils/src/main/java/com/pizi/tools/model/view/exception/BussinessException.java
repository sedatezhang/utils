package com.pizi.tools.model.view.exception;


import com.pizi.tools.model.view.ReturnCode;

/**
 * FEBS 系统内部异常
 * <p>
 * 业务异常类，用于表示在业务逻辑执行过程中出现的异常情况。
 * 通过此异常类，可以携带具体的错误代码和错误信息，以便于前端进行错误展示或后端进行错误日志记录。
 */
public class BussinessException extends RuntimeException {

    private static final long serialVersionUID = -994962710559017255L;

    /**
     * 构造函数，用于创建一个带有错误信息的业务异常。
     * <p>
     * 构造函数public BussinessException(String message)：通过传入的message参数初始化父类RuntimeException的message属性。
     *
     * @param message 错误信息，描述了异常的具体情况。
     */
    public BussinessException(String message) {
        super(message);
    }

    /**
     * 错误代码，用于标识具体的错误类型。
     */
    private Integer code;

    /**
     * 构造函数，用于创建一个带有错误代码和错误信息的业务异常。
     *
     * @param resultEnum 结果码枚举，包含了错误代码和错误信息。
     */
    private String message;

    /**
     * 构造函数，用于创建一个带有错误代码和错误信息的业务异常。
     *
     * @param resultEnum 结果码枚举，包含了错误代码和错误信息。
     */
    public BussinessException(ReturnCode resultEnum) {
        // 调用父类RuntimeException的构造函数，传入错误信息
        super(resultEnum.getMsg());
        // 将错误代码赋值给当前对象的code属性
        this.code = resultEnum.getCode();
    }

    /**
     * 构造函数，用于创建一个带有错误代码和错误信息的业务异常。
     *
     * @param code    错误代码，用于标识具体的错误类型。
     * @param message 错误信息，描述了异常的具体情况。
     */
    public BussinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
