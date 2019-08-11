package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author You
 * @create 2019-08-11 16:13
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
