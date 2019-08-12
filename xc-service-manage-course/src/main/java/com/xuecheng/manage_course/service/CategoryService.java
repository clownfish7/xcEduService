package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yzy
 * @classname CategoryService
 * @description TODO
 * @create 2019-08-12 11:03
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    //查询分类
    public CategoryNode findList() {
        return categoryMapper.selectList();
    }
}
