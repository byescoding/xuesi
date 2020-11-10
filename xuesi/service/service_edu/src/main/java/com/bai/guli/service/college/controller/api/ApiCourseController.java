package com.bai.guli.service.college.controller.api;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.college.entity.Course;
import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.vo.ChapterVo;
import com.bai.guli.service.college.entity.vo.TeacherQueryVo;
import com.bai.guli.service.college.entity.vo.WebCourseQueryVo;
import com.bai.guli.service.college.entity.vo.WebCourseVo;
import com.bai.guli.service.college.service.ChapterService;
import com.bai.guli.service.college.service.CourseService;
import com.bai.guli.service.college.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api("课程")
@RestController
@RequestMapping("/api/college/course")
@Slf4j
public class ApiCourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ChapterService chapterService;

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public R list(
            @ApiParam(value = "查询对象", required = false)
                    WebCourseQueryVo webCourseQueryVo) {
        List<Course> courseList = courseService.webSelectList(webCourseQueryVo);
        return R.ok().data("courseList", courseList);
    }

    //获取视频凭证

    @ApiOperation("根据ID查询课程")
    @GetMapping("get/{courseId}")
    public R getById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String courseId) {

        //查询课程信息和讲师信息
        WebCourseVo webCourseVo = courseService.selectWebCourseVoById(courseId);
        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        return R.ok().data("course", webCourseVo).data("chapterVoList", chapterVoList);
    }


    @ApiOperation("根据课程id更改销售量")
    @GetMapping("inner/update-buy-count/{id}")
    public R updateBuyCountById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String id){
        courseService.updateBuyCountById(id);
        return R.ok();
    }


    //根据id 获取课程信息
    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("inner/get-course-dto/{courseId}")
    public CourseDto getCourseDtoById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String courseId) {
        CourseDto courseDto = courseService.getCourseDtoById(courseId);
        return courseDto;
    }



}
