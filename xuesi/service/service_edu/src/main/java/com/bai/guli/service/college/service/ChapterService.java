package com.bai.guli.service.college.service;

import com.bai.guli.service.college.entity.Chapter;
import com.bai.guli.service.college.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
public interface ChapterService extends IService<Chapter> {

    boolean removeChapterById(String id);

    List<ChapterVo> nestedList(String courseId);
}
