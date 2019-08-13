package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yzy
 * @classname FileSystemRepository
 * @description TODO
 * @create 2019-08-13 10:54
 */
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
