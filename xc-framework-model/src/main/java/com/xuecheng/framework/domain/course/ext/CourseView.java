package com.xuecheng.framework.domain.course.ext;

/**
 * @author You
 * @create 2019-08-14 20:36
 */

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class CourseView implements Serializable {
    private CourseBase courseBase;//基础信息
    private CourseMarket courseMarket;//课程营销
    private CoursePic coursePic;//课程图片
    private TeachplanNode teachplanNode;//教学计划
}

