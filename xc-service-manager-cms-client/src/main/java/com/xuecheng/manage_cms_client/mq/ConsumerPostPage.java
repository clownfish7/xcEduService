package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author You
 * @create 2019-08-11 16:48
 */
@Slf4j
@Component
public class ConsumerPostPage {

    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private PageService pageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg) {
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        log.info("receive cms post page: {}", map.toString());
        //取出页面
        String pageId = (String) map.get("pageId");
        //查询页面信息
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()) {
            log.error("receive cms post page, cmsPage is null: {}",msg.toString());
            return;
        }
        //将页面保存到物理路径
        pageService.savePage2ServerPath(pageId);
    }

}
