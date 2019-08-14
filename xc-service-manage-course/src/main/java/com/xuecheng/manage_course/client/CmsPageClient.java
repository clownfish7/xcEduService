package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yzy
 * @classname CmsPageClient
 * @description TODO
 * @create 2019-08-13 18:41
 */
@FeignClient(value = "XC-SERVICE-MANAGE-CMS", fallback = CmsPageClientFallBack.class)
public interface CmsPageClient {
    @GetMapping("/cms/page/get/{id}")
    public CmsPageResult findById(@PathVariable("id") String id);

    //保存页面
    @PostMapping("/cms/page/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage);

    //一键发布页面
    @PostMapping("/cms/page/postPageQuick")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
