package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTempleteRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author yzy
 * @classname CmsPageService
 * @description TODO
 * @create 2019-08-06 14:53
 */
@Service
public class CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CmsTempleteRepository cmsTempleteRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;


    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        //条件匹配器
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        //条件值
        CmsPage cmsPage = new CmsPage();
        //站点ID
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //模板ID
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        if (page <= 0) {
            page = 1;
        }
        page = page - 1;//为了适应mongodb的接口将页码减1
        if (size <= 0) {
            size = 20;
        }
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> pages = cmsPageRepository.findAll(example, PageRequest.of(page, size));
        //暂时采用测试数据，测试接口是否可以正常运行
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setTotal(pages.getTotalElements());
        queryResult.setList(pages.getContent());

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    public CmsPageResult add(CmsPage cmsPage) {
        CmsPage find = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (find != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTS);
        }
        cmsPage.setPageId(null);//添加页面主键由spring data 自动生成
        cmsPageRepository.save(cmsPage);
        //返回结果
        CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        return cmsPageResult;
    }

    //根据id查询页面
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        //返回空
        return null;
    }

    //更新页面信息
    public CmsPageResult update(String id, CmsPage cmsPage) {
        //根据id查询页面信息
        CmsPage one = this.getById(id);
        if (one != null) {
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新DataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            //执行更新
            CmsPage save = cmsPageRepository.save(one);
            if (save != null) {
                //返回成功
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }
        }
        //返回失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    //删除页面
    public ResponseResult del(String id) {
        CmsPage cmsPage = this.getById(id);
        if (cmsPage != null) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //页面静态化方法

    /**
     * 静态化程序获取页面的DataUrl
     * 静态化程序远程请求DataUrl获取数据模型
     * 静态化程序获取页面的模板信息
     * 执行页面静态化
     */
    public String getPageHtml(String pageId) {

        //获取数据模型
        Map model = getModelByPageId(pageId);
        if (model == null) {
            ExceptionCast.cast(com.xuecheng.framework.domain.cms.response.CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        //获取页面模板
        String templateContent = getTempleteByPageId(pageId);
        if (StringUtils.isEmpty(templateContent)) {
            //页面模板为空
            ExceptionCast.cast(com.xuecheng.framework.domain.cms.response.CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //执行静态化
        String html = generateHtml(templateContent, model);
        if (StringUtils.isEmpty(html)) {
            ExceptionCast.cast(com.xuecheng.framework.domain.cms.response.CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    //获取数据模型
    private Map getModelByPageId(String pageId) {
        //取出页面的信息
        CmsPage cmsPage = this.getById(pageId);
        //取出页面的dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            ExceptionCast.cast(com.xuecheng.framework.domain.cms.response.CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }

        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }

    //获取页面模板
    private String getTempleteByPageId(String pageId) {
        //取出页面的信息
        CmsPage cmsPage = this.getById(pageId);
        //取出页面的dataUrl
        String templeteId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templeteId)) {
            ExceptionCast.cast(com.xuecheng.framework.domain.cms.response.CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //查询模板信息
        Optional<CmsTemplate> template = cmsTempleteRepository.findById(templeteId);
        if (template.isPresent()) {
            CmsTemplate cmsTemplate = template.get();
            //模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //取出模板文件内容             
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象             
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            // 创建GridFsResource             
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            try {
                String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //执行静态化
    private String generateHtml(String templateContent, Map model) {
        try {
            //生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", templateContent);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //页面发布
    public ResponseResult postPage(String pageId) {
        //执行静态化     
        String pageHtml = this.getPageHtml(pageId);
        if (StringUtils.isEmpty(pageHtml)) {
            ExceptionCast.cast(com.xuecheng.framework.domain.cms.response.CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        // 保存静态化文件     
        CmsPage cmsPage = saveHtml(pageId, pageHtml);
        // 发送消息     
        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //保存静态页面内容
    public CmsPage saveHtml(String pageId, String content) {
        //先得到页面信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ObjectId objectId = null;
        try {
            //将htmlContent内容转成输入流
            InputStream inputStream = IOUtils.toInputStream(content, "utf-8");
            //将html文件内容保存到GridFS
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将html文件id更新到cmsPage中
        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }
    //发送页面发送消息
    private void sendPostPage(String pageId){
        //得到页面信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //创建消息对象
        Map<String,String> msg = new HashMap<>();
        msg.put("pageId",pageId);
        //转成json串
        String jsonString = JSON.toJSONString(msg);
        //发送给mq
        //站点id
        String siteId = cmsPage.getSiteId();
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,jsonString);
    }
}
