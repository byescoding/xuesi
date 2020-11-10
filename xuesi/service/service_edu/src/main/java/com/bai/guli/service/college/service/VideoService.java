package com.bai.guli.service.college.service;

import com.bai.guli.service.college.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
public interface VideoService extends IService<Video> {

    void removeMediaVideoById(String id);

    void removeMediaVideoByChapterId(String id);

    void removeVideoByCourseId(String id);
}
