package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author yzy
 * @classname CustomerException
 * @description TODO
 * @create 2019-08-08 19:51
 */
public class CustomerException extends RuntimeException {
    private ResultCode resultCode;

    public CustomerException(ResultCode resultCode) {
        super("错误代码: "+resultCode.code()+" 错误信息: "+resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
