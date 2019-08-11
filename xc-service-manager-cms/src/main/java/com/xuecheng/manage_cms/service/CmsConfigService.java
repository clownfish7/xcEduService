package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author You
 * @create 2019-08-10 17:29
 */
@Service
public class CmsConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    //根据id查询配置管理信息     
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> cmsConfig = cmsConfigRepository.findById(id);
        if (cmsConfig.isPresent()) {
            CmsConfig config = cmsConfig.get();
            return config;
        }
        return null;
    }
}
