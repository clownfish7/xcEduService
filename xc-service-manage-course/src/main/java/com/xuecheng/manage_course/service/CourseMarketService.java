package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author yzy
 * @classname CourseMarketService
 * @description TODO
 * @create 2019-08-12 14:36
 */
@Service
public class CourseMarketService {

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    //查询课程营销by id
    public CourseMarket getCourseMarketById(String courseId) {
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    //更新课程营销
    @Transactional
    public CourseMarket updateCourseMarket(String id, CourseMarket courseMarket) {
        CourseMarket market = this.getCourseMarketById(id);
        if (market != null) {
            market.setCharge(courseMarket.getCharge());
            market.setStartTime(courseMarket.getStartTime());//课程有效期，开始时间
            market.setEndTime(courseMarket.getEndTime());//课程有效期，结束时间
            market.setPrice(courseMarket.getPrice());
            market.setQq(courseMarket.getQq());
            market.setValid(courseMarket.getValid());
            courseMarketRepository.save(market);
        } else {
            //添加课程营销信息
            market = new CourseMarket();
            BeanUtils.copyProperties(courseMarket, market);
            //设置课程id
            market.setId(id);
            courseMarketRepository.save(market);
        }
        return market;
    }
}
