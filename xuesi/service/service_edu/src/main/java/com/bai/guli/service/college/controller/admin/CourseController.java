package com.bai.guli.service.college.controller.admin;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.form.CourseInfoForm;
import com.bai.guli.service.college.entity.vo.CoursePublishVo;
import com.bai.guli.service.college.entity.vo.CourseQueryVo;
import com.bai.guli.service.college.entity.vo.CourseVo;
import com.bai.guli.service.college.entity.vo.TeacherQueryVo;
import com.bai.guli.service.college.service.CourseService;
import com.bai.guli.service.college.service.VideoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Api("课程管理")
@RestController
@RequestMapping("/admin/college/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private VideoService videoService;
    @PostMapping("/save-course-info")
    @ApiOperation("添加啊课程信息")
    public R  saveCourseInfo(@ApiParam(value = "课程信息",required = true) @RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.saveCourseInfo(courseInfoForm);

        if (!StringUtils.isEmpty(courseId)){
            return  R.ok().data("courseId",courseId).message("保存成功");
        }else{
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("/course-info/{id}")
    public R getById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id) {

        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm != null) {
            return R.ok().data("item", courseInfoForm);
        } else {
            return R.ok().message("数据不存在");
        }
    }

    @ApiOperation("更新课程")
    @PutMapping("/update-course-info")
    public R updateCourseInfoById(
            @ApiParam(value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok().message("修改成功");
    }

    //数据分页
    @GetMapping("/list/{page}/{limit}")
    @ApiOperation("课程信息分页")
    //必须填写的字段
    public R listPage(@ApiParam(value = "页码" ,required = true) @PathVariable("page") long page,
                      @ApiParam(value = "页面数据量" ,required = true) @PathVariable("limit") long limit,
                      @ApiParam("课程查询条件")CourseQueryVo courseQueryVo
                      ){
        IPage<CourseVo> courseVoIPage = courseService.selectPage(page, limit, courseQueryVo);
        long total = courseVoIPage.getTotal();//获取总数
        List<CourseVo> records = courseVoIPage.getRecords(); //获取记录行数
        return R.ok().data("total",total).data("rows",records);

    }


    //删除课程信息
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除课程信息")
    public R removeTeacherById(@ApiParam("要删除的课程的ID") @PathVariable("id") String id){

        //批量删除视频信息
        videoService.removeVideoByCourseId(id);


        Boolean flag = courseService.removeCoverById(id);
        //应该先要保证阿里云的图片已经被删除才行
        courseService.removeCourseById(id);
        if (flag){
            return R.ok().message("数据删除成功");
        }else {
            return R.error().message("数据删除失败");
        }
    }


    /**
     * 获取即将发布课程的信息
     * @param id
     * @return
     */
    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("/course-publish/{id}")
    public R getCoursePublishVoById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);
        if (coursePublishVo != null) {
            return R.ok().data("item", coursePublishVo);
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 改变课程的发布状态
     * @param id
     * @return
     */
    @ApiOperation("根据id发布课程")
    @PutMapping("/publish/{id}")
    public R publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id) {

        boolean result = courseService.publishCourseById(id);
        if (result) {
            return R.ok().message("发布成功");
        } else {
            return R.error().message("数据不存在");
        }
    }



}

