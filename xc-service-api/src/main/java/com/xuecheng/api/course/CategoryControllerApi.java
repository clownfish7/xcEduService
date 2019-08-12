package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author You
 * @create 2019-08-11 21:28
 */
@Api(value = "课程分类管理1", description = "课程分类管理2", tags = "课程分类管理3")
public interface CategoryControllerApi {

    @ApiOperation(value = "查询分类")
    public CategoryNode findList();

}
