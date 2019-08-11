package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author You
 * @create 2019-08-11 21:40
 */
@Mapper
public interface TeachplanMapper {
    public TeachplanNode selectList(String courseId);
}
