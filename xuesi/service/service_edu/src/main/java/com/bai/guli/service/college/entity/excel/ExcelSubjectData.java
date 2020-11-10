package com.bai.guli.service.college.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * excel 表的分类
 */
@Data
public class ExcelSubjectData {
    @ExcelProperty(value = "一级分类")
    private String levelOneTitle;
    @ExcelProperty(value = "二级分类")
    private String levelTwoTitle;
}
