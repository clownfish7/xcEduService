package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author yzy
 * @classname ExceptionCast
 * @description TODO
 * @create 2019-08-08 19:53
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode) {
        throw new CustomerException(resultCode);
    }
}
