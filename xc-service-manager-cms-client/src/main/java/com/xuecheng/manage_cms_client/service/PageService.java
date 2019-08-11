package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @author You
 * @create 2019-08-11 16:15
 */
@Slf4j
@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    //保存html到服务器物理路径
    public void savePage2ServerPath(String pageId) {
        CmsPage cmsPage = this.findCmsPageById(pageId);
        //得到html文件id，从cmsPage中获得
        String htmlFileId = cmsPage.getHtmlFileId();
        //从gridFs中查询html文件
        InputStream is = this.getFileById(htmlFileId);
        if (is == null) {
            log.error("getFileById InputStream is null, htmlFileId:{}",htmlFileId);
            return;
        }
        //得到站点的信息
        CmsSite cmsSite = this.findCmsSiteById(cmsPage.getSiteId());
        //得到站点的物理路径
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        //得到页面的物理路径
        String pagePath = sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        //将html文件保存到服务器的物理路径上
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(pagePath));
            IOUtils.copy(is, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //根据页面id查询页面的信息
    public CmsPage findCmsPageById(String pageId) {
        Optional<CmsPage> cmsPage = cmsPageRepository.findById(pageId);
        if (cmsPage.isPresent()) {
            return cmsPage.get();
        }
        return null;
    }

    //根据站点id查询站点的信息
    public CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> cmsSite = cmsSiteRepository.findById(siteId);
        if (cmsSite.isPresent()) {
            return cmsSite.get();
        }
        return null;
    }

    //根据文件id获取文件内容
    public InputStream getFileById(String fileId) {
        try {
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, downloadStream);
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
