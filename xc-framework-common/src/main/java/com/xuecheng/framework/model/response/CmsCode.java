package com.xuecheng.framework.model.response;

import lombok.ToString;

/**
 * @author yzy
 * @classname CmsCode
 * @description TODO
 * @create 2019-08-08 20:01
 */
@ToString
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTS(false, 24001, "页面已存在！");

    boolean success;
    int code;
    String message;

    CmsCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
