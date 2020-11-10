package com.bai.guli.service.college.controller.admin;


import com.bai.guli.common.base.result.R;
import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.common.base.util.ExceptionUtils;
import com.bai.guli.service.base.exception.CollegeException;
import com.bai.guli.service.college.entity.vo.SubjectVo;
import com.bai.guli.service.college.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Api("课程分类管理")
@Slf4j
@RestController
@RequestMapping("/admin/college/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    //批量导入数据
@ApiOperation("批量导入数据")
@PostMapping("import")
public R batchExcel(@ApiParam(value = "文件",required = true) @RequestParam("file") MultipartFile file){
    try {
        InputStream inputStream = file.getInputStream();
        subjectService.batchImport(inputStream);
        return R.ok().message("导入成功");
    } catch (Exception e) {
        log.error(ExceptionUtils.getMessage(e));
        throw  new CollegeException(ResultCode.EXCEL_DATA_IMPORT_ERROR);
    }
}


@ApiOperation("嵌套课程分类列表")
@GetMapping("/nested-list")
public R getNestedList(){
    List<SubjectVo> subjectVos = subjectService.nestedList();
   return    R.ok().data("items",subjectVos);
}




}

