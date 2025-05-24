package com.pizi.tools.model.view;

import lombok.Data;

/**
 * 结果数据类，用于封装接口返回的数据。
 * 包含结果状态码、响应信息、响应数据和请求时间。
 *响应数据的泛型类型。
 *
 * @author 痞子
 * @date 2018年8月6日
 *
 */
@Data
public class ResultData<T> {

    // 结果状态码
    private int code;

    // 响应信息
    private String msg;

    // 响应数据
    private T data;

    // 接口请求时间
    private long timestamp ;

    /**
     * 结果状态码，用于表示接口调用的成功与否。
     */
    public ResultData (){
        this.timestamp = System.currentTimeMillis();
    }


    /**
     * 构造成功结果的方法。
     *
     * @param data 响应数据。
     * @return 成功的结果数据对象。
     */
    public static <T> ResultData<T> success(T data){
        ResultData resultData = new ResultData();
        resultData.setCode(ReturnCode.RC200.getCode());
        resultData.setMsg(ReturnCode.RC200.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * 构造失败结果的方法。
     *
     * @param code 结果状态码。
     * @param msg 响应信息。
     * @return 失败的结果数据对象。
     */
    public static <T> ResultData<T> fail(int code, String msg){
        ResultData resultData = new ResultData();
        resultData.setCode(code);
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * 构造成功结果但无数据的方法。
     *
     * @param message 响应信息。
     * @return 成功的结果数据对象，无响应数据。
     */
    public static <T> ResultData<T> success(String message){
        ResultData resultData = new ResultData();
        resultData.setCode(ReturnCode.RC200.getCode());
        resultData.setMsg(message);
        resultData.setData(null);
        return resultData;
    }
}
