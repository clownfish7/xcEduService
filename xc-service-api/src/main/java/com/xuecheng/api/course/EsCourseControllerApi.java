package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @author yzy
 * @classname EsCourseControllerApi
 * @description TODO
 * @create 2019-08-15 20:17
 */
@Api(value = "课程搜索", description = "课程搜索", tags = {"课程搜索"})
public interface EsCourseControllerApi {
    @ApiOperation("课程搜索")
    public QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam);

    @ApiOperation("根据id查询课程信息")
    public Map<String, CoursePub> getall(String id);

    @ApiOperation("根据课程计划查询媒资信息")
    public TeachplanMediaPub getmedia(String teachplanId);
}
