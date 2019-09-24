package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.task.XcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author yzy
 * @classname XcTaskRepository
 * @description TODO
 * @create 2019-08-21 14:52
 */
public interface XcTaskRepository extends JpaRepository<XcTask,String> {
    //取出指定时间之前的任务
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    @Modifying
    @Query("update XcTask t set t.updateTime = :updateTime where t.id = :id")
    public int updateTaskTime(@Param(value = "id") String id, @Param("updateTime") Date updateTime);

    @Modifying
    @Query("update XcTask t set t.version = :version + 1 where t.id = :id and t.version = :version")
    public int updateVersion(@Param(value = "id") String id, @Param(value = "version") int version);
}
