package com.xuecheng.api.learning;

import com.xuecheng.framework.domain.learning.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author You
 * @create 2019-08-17 21:48
 */
@Api(value = "课程学习管理", description = "课程学习")
public interface CourseLearningControllerApi {

    @ApiOperation("获取课程学习地址")
    public GetMediaResult getmedia(String courseId, String teachplanId);

}
