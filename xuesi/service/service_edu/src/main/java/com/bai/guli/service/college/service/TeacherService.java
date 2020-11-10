package com.bai.guli.service.college.service;

import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.vo.TeacherQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
public interface TeacherService extends IService<Teacher> {
    Page<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo);

    List<Map<String, Object>> selectNameListByKey(String key);

     Boolean removeAvatarById(String id);

    Map<String,Object> getCoursesByTeacherId(String id);

    List<Teacher> webSelectTeacherList();
}


