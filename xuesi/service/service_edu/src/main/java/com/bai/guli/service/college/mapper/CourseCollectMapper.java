package com.bai.guli.service.college.mapper;

import com.bai.guli.service.college.entity.CourseCollect;
import com.bai.guli.service.college.entity.vo.CourseCollectVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Repository
public interface CourseCollectMapper extends BaseMapper<CourseCollect> {

    List<CourseCollectVo> selectListByMemberId(String memberId);
}
