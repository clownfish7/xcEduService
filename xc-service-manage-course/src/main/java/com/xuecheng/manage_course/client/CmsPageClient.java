package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author yzy
 * @classname CmsPageClient
 * @description TODO
 * @create 2019-08-13 18:41
 */
@FeignClient(value = "XC-SERVICE-MANAGE-CMS",fallback = CmsPageClientFallBack.class)
public interface CmsPageClient {
    @GetMapping("/cms/page/get/{id}")
    public CmsPageResult findById(@PathVariable("id") String id);
}
