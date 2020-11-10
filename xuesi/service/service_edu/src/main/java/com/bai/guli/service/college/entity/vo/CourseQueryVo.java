package com.bai.guli.service.college.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程查询条件
 */
@Data
public class CourseQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String teacherId;
    private String subjectParentId;
    private String subjectId;
}
