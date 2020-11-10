package com.bai.guli.service.college.mapper;

import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.college.entity.Course;
import com.bai.guli.service.college.entity.vo.CoursePublishVo;
import com.bai.guli.service.college.entity.vo.CourseQueryVo;
import com.bai.guli.service.college.entity.vo.CourseVo;
import com.bai.guli.service.college.entity.vo.WebCourseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {
     //在执行多表查询时  自定义查询条件
    List<CourseVo> selectPageByCourseQueryVo(Page<CourseVo> pageParam,   @Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    CoursePublishVo selectCoursePublishVoById(String id);
    WebCourseVo selectWebCourseVoById(String courseId);;

    CourseDto selectCourseDtoById(String courseId);
}
