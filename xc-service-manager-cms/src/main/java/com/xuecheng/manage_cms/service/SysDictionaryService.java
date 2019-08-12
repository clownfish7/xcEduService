package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yzy
 * @classname SysDictionary
 * @description TODO
 * @create 2019-08-12 11:44
 */
@Service
public class SysDictionaryService {
    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary findBydType(String dType) {
        return sysDictionaryRepository.findByDType(dType);
    }
}
