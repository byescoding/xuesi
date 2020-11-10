package com.bai.guli.service.college.controller.admin;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.vo.TeacherQueryVo;
import com.bai.guli.service.college.feign.OssFileService;
import com.bai.guli.service.college.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Api("讲师管理")
@RestController
@RequestMapping("/admin/college/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OssFileService ossFileService;

//    @GetMapping("/test")
//    public R test(){
//        return ossFileService.test();
//    }


    //获取教师列表
    @ApiOperation("获取教师信息列表")
    @GetMapping("/list")
    public R listTeachers(){
        List<Teacher> list = teacherService.list();
        return R.ok().data("items",list);
    }

    //删除讲师
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除教师")
    public R removeTeacherById(@ApiParam("要删除的教师的ID") @PathVariable("id") String id){

        Boolean flag = teacherService.removeAvatarById(id);
        //应该先要保证阿里云的图片已经被删除才行
         if (flag){
             teacherService.removeById(id);
             return R.ok().message("数据删除成功");
         }else {
             return R.error().message("数据删除失败");
         }
    }

    //数据分页
    @GetMapping("/list/{page}/{limit}")
    @ApiOperation("教师信息分页")
    //必须填写的字段
    public R listPage(@ApiParam(value = "页码" ,required = true) @PathVariable("page") long page,
                      @ApiParam(value = "页面数据量" ,required = true) @PathVariable("limit") long limit,
                      @ApiParam("构造查询条件")TeacherQueryVo teacherQueryVo
                      ){
        Page<Teacher> pageParm = new Page<>(page,limit);
        Page<Teacher> teacherPage = teacherService.selectPage(pageParm, teacherQueryVo);
        long total = teacherPage.getTotal();//获取总数
        List<Teacher> records = teacherPage.getRecords(); //获取记录行数
        return R.ok().data("total",total).data("rows",records);
    }


    //添加教师数据
   @ApiOperation("添加新教师")
   @PostMapping("/save")
    public R saveTeacher(@ApiParam(value = "教师对象" ,required = true) @RequestBody Teacher teacher){
       boolean save = teacherService.save(teacher);
       if (save){
           return R.ok().message("保存成功");
       }else {
           return R.error().message("保存失败");
       }
   }

   //跟新教师新
    @ApiOperation("修改教师信息")
    @PostMapping("/update")
    public R updateTeacher(@ApiParam(value = "教师对象",required = true) @RequestBody Teacher teacher){
        boolean update = teacherService.updateById(teacher); //根据教师id进行修改
        if (update){
            return R.ok().message("修改成功");
        }else {
            return R.error().message("修改失败");
        }
    }

    @ApiOperation("根据教师ID获取教师信息")
    @GetMapping("/get/{id}")
    public R getTeacgerById(@ApiParam(value = "教师ID",required = true)@PathVariable("id") String id){
        Teacher teacher= teacherService.getById(id);
        if (teacher != null){
            return R.ok().message("查找成功").data("item",teacher);
        }else {
            return R.error().message("数据不存在");
        }
    }

    //批量删除教师信息
    @ApiOperation("批量删除教师信息")
    @PostMapping("/batch-remove")
    public R batchRemove(@ApiParam(value = "教师ID集合",required = true) @RequestBody List<Integer> ids){
        System.out.println(ids);
        boolean res = teacherService.removeByIds(ids);
        if (res){
            return R.ok().message("数据删除成功");
        }else {
            return R.error().message("数据不存在");
        }
    }


    //数据回显
    @ApiOperation("查询数据匹配")
    @GetMapping("list/name/{key}")
    public R likeName(@ApiParam(value = "模糊查询的关键字" ,required = true) @PathVariable("key") String key){

        List<Map<String, Object>> maps = teacherService.selectNameListByKey(key);
        if (maps !=null){
           return R.ok().data("nameList",maps);
        }else {
            return R.error().message("数据不存在");
        }
    }



}

