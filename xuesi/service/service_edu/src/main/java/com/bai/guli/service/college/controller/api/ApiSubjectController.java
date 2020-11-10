package com.bai.guli.service.college.controller.api;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.entity.vo.SubjectVo;
import com.bai.guli.service.college.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("课程分类")
@Slf4j
@RestController
@RequestMapping("/api/college/subject")
public class ApiSubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("嵌套课程分类列表")
    @GetMapping("/nested-list")
    public R getNestedList(){
        List<SubjectVo> subjectVos = subjectService.nestedList();
        return    R.ok().data("items",subjectVos);
    }

}
