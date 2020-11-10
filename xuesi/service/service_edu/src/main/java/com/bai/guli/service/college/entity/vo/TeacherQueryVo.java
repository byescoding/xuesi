package com.bai.guli.service.college.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;  //根据名字模糊查询
    private Integer level; //根等级进行查询
    private String joinDateBegin; // 开始时间
    private String joinDateEnd;   //结束时间
}
