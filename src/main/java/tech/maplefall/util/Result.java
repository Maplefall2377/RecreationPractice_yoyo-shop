package tech.maplefall.util;

import lombok.Data;

/**
 * 此类是返回结果类。后台向前端返回此类型对象。
 */
@Data
public class Result {
    //状态码
    private int code;
    //提示信息
    private String msg;
    //返回数据
    private Object data;

    //私有构造方法。不对外使用，只能通过静态方法创建对象
    private Result(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     * @return Result对象
     */
    public static Result success(){
        return new Result(0, "success", null);
    }

    /**
     * 成功返回结果
     * @param data 返回数据
     * @return Result对象, 携带数据
     */
    public static Result success(Object data) {
        return new Result(0, "请求成功", data);
    }

    /**
     * 成功返回结果
     * @param msg 提示信息
     * @return Result对象, 携带提示信息
     */
    public static Result success(String msg){
        return new Result(0, msg, null);
    }
    /**
     * 成功返回结果
     * @param msg 提示信息
     * @param data 返回数据
     * @return Result对象, 携带数据和提示信息
     */
    public static Result success(String msg, Object data){
        return new Result(0, msg, data);
    }

    /**
     * 失败返回结果
     * @return Result对象,只含有code和msg
     */
    public static Result error(){
        return new Result(500, "请求失败", null);
    }

    /**
     * 失败返回结果
     * @param msg 提示信息
     * @return Result对象, 携带提示信息
     */
    public static Result error(String msg){
        return new Result(500, msg, null);
    }

    /**
     * 失败返回结果
     * @param code 状态码
     * @param msg 提示信息
     * @return Result对象, 携带数据和提示信息
     */
    public static Result error(int code, String msg){
        return new Result(code, msg, null);
    }
}
