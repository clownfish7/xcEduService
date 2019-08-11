package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author You
 * @create 2019-08-11 21:28
 */
@Api(value = "课程管理接口", description = "课程管理接口，提供课程的增、删、改、查")
public interface CourseControllerApi {

    @ApiOperation(value = "课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation(value = "添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);
}
