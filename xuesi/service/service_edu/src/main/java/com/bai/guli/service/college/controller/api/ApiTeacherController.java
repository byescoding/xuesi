package com.bai.guli.service.college.controller.api;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.entity.Course;
import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.vo.TeacherQueryVo;
import com.bai.guli.service.college.service.CourseService;
import com.bai.guli.service.college.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("讲师")
@RestController
@RequestMapping("/api/college/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    //获取教师列表
    @ApiOperation("获取教师信息列表")
    @GetMapping("/list")
    public R listTeachers(){
        List<Teacher> list = teacherService.list();
        return R.ok().data("items",list);
    }

    @ApiOperation("根据教师ID获取教师信息及课程信息")
    @GetMapping("/get/{id}")
    public R getTeacgerById(@ApiParam(value = "教师ID",required = true)@PathVariable("id") String id){
        Map<String, Object> coursesByTeacherId = teacherService.getCoursesByTeacherId(id);

        if(coursesByTeacherId !=null){
        return R.ok().message("查找成功").data(coursesByTeacherId);
        }else {
            return R.error().message("数据不存在");
        }
    }



}
