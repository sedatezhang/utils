package com.pizi.tools.model.view;


/**
 *
 * 响应码枚举类，用于标识系统返回的各种状态码及其对应的信息。
 *
 * @author 痞子
 * @date 2019年5月7日
 */
public enum ReturnCode {
    RC200(200, "操作成功"),
    RC500(500, "操作失败");
    // 自定义状态码
    private final int code;

    // 自定义描述
    private final String msg;


    /**
     * 构造方法，初始化响应码和响应信息。
     *
     * @param code 响应码
     * @param msg 响应信息
     */
    ReturnCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取响应码。
     *
     * @return 响应码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取响应码。
     *
     * @return 响应码
     */
    public String getMsg() {
        return msg;
    }
}
