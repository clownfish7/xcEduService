package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author You
 * @create 2019-08-11 16:13
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
}
