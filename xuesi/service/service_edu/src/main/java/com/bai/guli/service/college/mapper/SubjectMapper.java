package com.bai.guli.service.college.mapper;

import com.bai.guli.service.college.entity.Subject;
import com.bai.guli.service.college.entity.vo.SubjectVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Repository
public interface SubjectMapper extends BaseMapper<Subject> {
    List<SubjectVo> selectNestedList(String parentId);
}
