package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author You
 * @create 2019-08-10 17:28
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
