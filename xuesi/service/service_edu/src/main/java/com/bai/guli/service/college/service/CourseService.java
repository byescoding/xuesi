package com.bai.guli.service.college.service;

import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.college.entity.Course;
import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.form.CourseInfoForm;
import com.bai.guli.service.college.entity.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
public interface CourseService extends IService<Course> {
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);


    IPage<CourseVo> selectPage(long page, long limit, CourseQueryVo courseQueryVo);

    Boolean removeCoverById(String id);

    boolean removeCourseById(String id);

    CoursePublishVo getCoursePublishVoById(String id);

    boolean publishCourseById(String id);

    List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo);

    WebCourseVo selectWebCourseVoById(String courseId);

    void updateBuyCountById(String id);

    List<Course> webSelectCourseList();

    CourseDto getCourseDtoById(String courseId);
}
