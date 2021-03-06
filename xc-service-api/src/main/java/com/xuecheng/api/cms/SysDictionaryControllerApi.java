package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author yzy
 * @classname CmsPageControllerApi
 * @description TODO
 * @create 2019-08-06 10:35
 */
@Api(value = "数据字典接口", description = "提供数据字典接口的管理、查询功能")
public interface SysDictionaryControllerApi {
    @ApiOperation("数据字典接口查询")
    public SysDictionary getByType(String dType);
}
