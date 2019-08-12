package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yzy
 * @classname SysDictionaryRepository
 * @description TODO
 * @create 2019-08-12 11:42
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    //根据字典分类查询字典信息
    public SysDictionary findByDType(String dType);
}
