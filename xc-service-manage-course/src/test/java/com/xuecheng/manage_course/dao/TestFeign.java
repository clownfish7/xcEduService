package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yzy
 * @classname TestFeign
 * @description TODO
 * @create 2019-08-13 19:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFeign {

    @Autowired
    private CmsPageClient cmsPageClient;

    @Test
    public void testFeign() {
        CmsPageResult res = cmsPageClient.findById("5a754adf6abb500ad05688d9");
        System.out.println(res);
    }
}
