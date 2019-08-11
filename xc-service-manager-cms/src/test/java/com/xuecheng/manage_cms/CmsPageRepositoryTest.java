package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author yzy
 * @classname CmsPageRepositoryTest
 * @description TODO
 * @create 2019-08-06 11:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Test
    public void findAll() {
        List<CmsPage> cmsPageList = cmsDao.findAll();
        System.out.println(cmsPageList);
    }

    @Test
    public void findPage() {
        Pageable pageable = PageRequest.of(1, 3);
        Page<CmsPage> all = cmsDao.findAll(pageable);
        System.out.println(all);

    }

    @Test
    public void findAllByExample() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageAliase("首页");
        ExampleMatcher matching = ExampleMatcher.matching().withMatcher("pageAliase",
                ExampleMatcher.GenericPropertyMatchers.contains());
        //页面别名模糊查询，需要自定义字符串的匹配器实现模糊查询
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含
        //ExampleMatcher.GenericPropertyMatchers.startsWith()//开头匹配
        Example<CmsPage> example = Example.of(cmsPage, matching);

        List<CmsPage> pageList = cmsDao.findAll(example);
        System.out.println(pageList);
    }


    /**
     * 关于Optional：
     * Optional是jdk1.8引入的类型，Optional是一个容器对象，它包括了我们需要的对象，使用isPresent方法判断所包
     * 含对象是否为空，isPresent方法返回false则表示Optional包含对象为空，否则可以使用get()取出对象进行操作。
     * Optional的优点是：
     * 1、提醒你非空判断。
     * 2、将对象非空检测标准化。
     */
    @Test
    public void updatePage() {
        Optional<CmsPage> optional = cmsDao.findById("5a754adf6abb500ad05688d9");
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            cmsPage.setPageAliase("这是首页");
            cmsDao.save(cmsPage);
        }
    }

    @Test
    public void testRestTemplete() throws URISyntaxException {
        ResponseEntity<CmsConfig> forEntity = restTemplate.getForEntity(new URI("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f"), CmsConfig.class);
        System.out.println(forEntity);
    }

    /**
     * GridFs   存文件
     */
    @Test
    public void testGridFSTemplete() throws FileNotFoundException {
        File file = new File("f:\\test.html");
        InputStream is = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(is, "测试文件01");
        System.out.println(objectId.toString());
    }


    /**
     * GridFs   读文件
     */

    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void testGridFSTemplete1() throws IOException {
        String fileId = "5a7b9fa5d019f14224087d64";
        //根据id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        //打开下载流对象         
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        // 创建gridFsResource，用于获取流对象         
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        // 获取流中的数据         
        String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(s);
    }

    //删除文件 
    @Test
    public void testDelFile() throws IOException {
        //根据文件id删除fs.files和fs.chunks中的记录     
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5a7b9fa5d019f14224087d64")));
    }
}
