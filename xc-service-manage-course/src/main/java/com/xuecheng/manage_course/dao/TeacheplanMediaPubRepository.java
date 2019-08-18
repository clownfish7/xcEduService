package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author You
 * @create 2019-08-17 19:09
 */
public interface TeacheplanMediaPubRepository extends JpaRepository<TeachplanMediaPub,String> {
    long deleteByCourseId(String courseId);
}
