package com.xuecheng.framework.model.response;

/**
 * @author You
 * @create 2019-08-17 22:01
 */
public enum LearningCode implements ResultCode {
    LEARNING_GETMEDIA_ERROR(false,20000,"获取视频播放地址出错!");

    boolean success;
    int code;
    String message;

    LearningCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public int code() {
        return 0;
    }

    @Override
    public String message() {
        return null;
    }
}
