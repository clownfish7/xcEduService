package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author You
 * @create 2019-08-11 22:50
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    //定义方法根据课程id和父结点id查询出结点列表，可以使用此方法实现查询根结点
    public List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);
}
