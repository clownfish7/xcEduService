package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import org.springframework.stereotype.Component;

/**
 * @author yzy
 * @classname CmsPageClientFallBack
 * @description TODO
 * @create 2019-08-13 18:44
 */
@Component
public class CmsPageClientFallBack implements CmsPageClient {

    @Override
    public CmsPageResult findById(String id) {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("熔断");
        return new CmsPageResult(CommonCode.SERVER_ERROR, cmsPage);
    }

    @Override
    public CmsPageResult save(CmsPage cmsPage) {
        return null;
    }

    @Override
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        return null;
    }
}
