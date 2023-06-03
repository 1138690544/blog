package com.my.blog.handler;

import com.my.blog.enums.AppHttpCodeEnum;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.enums
 * @className: SystemException
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/18 22:23
 * @version: 1.0
 */
public class SystemException extends RuntimeException{
    private int code;
    private String msg;
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
