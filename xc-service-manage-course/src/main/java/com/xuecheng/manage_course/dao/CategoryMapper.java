package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CategoryMapper {
   //查询分类
   public CategoryNode selectList();
}
