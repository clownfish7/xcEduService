package com.xuecheng.framework.domain.learning;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import feign.Response;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author You
 * @create 2019-08-17 21:52
 */
@Data
@NoArgsConstructor
public class GetMediaResult extends ResponseResult {

    private String fileUrl;

    public GetMediaResult(ResultCode resultCode, String fileUrl) {
        super(resultCode);
        this.fileUrl = fileUrl;
    }
}
