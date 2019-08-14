package com.xuecheng.framework.domain.cms.response;

/**
 * @author You
 * @create 2019-08-14 23:06
 */

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//无参构造器注解
public class CmsPostPageResult extends ResponseResult {
    String pageUrl;

    public CmsPostPageResult(ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
