package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author You
 * @create 2019-08-11 21:28
 */
@Api(value = "课程营销接口", description = "课程营销接口，提供课程营销的管理")
public interface CourseMarketControllerApi {

    @ApiOperation(value = "获取课程营销信息")
    public CourseMarket getCourseMarketById(String courseId);

    @ApiOperation(value = "更新课程营销信息")
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);
}
