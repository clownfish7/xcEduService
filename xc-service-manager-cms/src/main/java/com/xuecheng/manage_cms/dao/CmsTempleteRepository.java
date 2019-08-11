package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author You
 * @create 2019-08-10 17:28
 */
public interface CmsTempleteRepository extends MongoRepository<CmsTemplate,String> {
}
