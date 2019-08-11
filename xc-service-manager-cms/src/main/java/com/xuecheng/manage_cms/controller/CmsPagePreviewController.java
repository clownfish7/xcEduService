package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;

/**
 * @author You
 * @create 2019-08-10 21:22
 */
@Controller
@RequestMapping("/cms/preview")
public class CmsPagePreviewController extends BaseController {

    @Autowired
    private CmsPageService cmsPageService;

    @GetMapping("/{pageId}")
    public void preview(@PathVariable("pageId") String pageId) {
        String html = cmsPageService.getPageHtml(pageId);
        if (StringUtils.isNotEmpty(html)) {
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(html.getBytes("utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
