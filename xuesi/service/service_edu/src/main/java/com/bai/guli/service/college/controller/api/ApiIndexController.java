package com.bai.guli.service.college.controller.api;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.entity.Course;
import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.vo.WebCourseQueryVo;
import com.bai.guli.service.college.service.CourseService;
import com.bai.guli.service.college.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api("课程")
@RestController
@RequestMapping("/api/college/index")
@Slf4j
public class ApiIndexController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;


    @ApiOperation("课程排行")
    @GetMapping("/sort-course")
    public R listCourse() {
        List<Course> courseList = courseService.webSelectCourseList();
        return R.ok().data("courseList", courseList);
    }

    @ApiOperation("教师排行")
    @GetMapping("/sort-teacher")
    public R listTeacher() {
        List<Teacher> teacherList = teacherService.webSelectTeacherList();
        return R.ok().data("teacherList",teacherList );
    }

}
