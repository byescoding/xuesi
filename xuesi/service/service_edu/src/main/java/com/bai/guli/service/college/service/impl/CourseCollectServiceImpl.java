package com.bai.guli.service.college.service.impl;

import com.bai.guli.service.college.entity.CourseCollect;
import com.bai.guli.service.college.entity.vo.CourseCollectVo;
import com.bai.guli.service.college.mapper.CourseCollectMapper;
import com.bai.guli.service.college.service.CourseCollectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Service
public class CourseCollectServiceImpl extends ServiceImpl<CourseCollectMapper, CourseCollect> implements CourseCollectService {
    @Override
    public boolean isCollect(String courseId, String memberId) {
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id",courseId).eq("member_id",memberId);
        Integer count = baseMapper.selectCount(courseCollectQueryWrapper);
        return count.intValue()>0;
    }

    @Override
    public void saveCourseCollect(String courseId, String memberId) {
        CourseCollect courseCollect = new CourseCollect();
        courseCollect.setMemberId(memberId);
        courseCollect.setCourseId(courseId);

        baseMapper.insert(courseCollect);
    }

    @Override
    public List<CourseCollectVo> selectListByMemberId(String memberId) {
        return baseMapper.selectListByMemberId(memberId);
    }



    @Override
    public boolean removeCourseCollect(String courseId, String memberId) {
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id",courseId).eq("member_id",memberId);
        return this.remove(courseCollectQueryWrapper);
    }
}
