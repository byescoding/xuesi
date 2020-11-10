package com.bai.guli.service.college.service;

import com.bai.guli.service.college.entity.Subject;
import com.bai.guli.service.college.entity.vo.SubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
public interface SubjectService extends IService<Subject> {
    void batchImport(InputStream inputStream);

    List<SubjectVo> nestedList();
}
