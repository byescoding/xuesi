package com.bai.guli.service.college.service.impl;

import com.bai.guli.service.college.entity.Course;
import com.bai.guli.service.college.entity.Video;
import com.bai.guli.service.college.feign.VodFileService;
import com.bai.guli.service.college.mapper.VideoMapper;
import com.bai.guli.service.college.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VodFileService vodFileService;

    @Override
    public void removeMediaVideoById(String id) {
        //先删除视频
        Video video = videoMapper.selectById(id);
        vodFileService.removeVideo(video.getVideoSourceId());
        //删除数据库中的记录
        baseMapper.deleteById(id);

    }

    @Override
    public void removeMediaVideoByChapterId(String id) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.select("video_source_id").eq("chapter_id", id);
        List<Map<String, Object>> vodList = videoMapper.selectMaps(videoQueryWrapper);
        ArrayList<String> ids = new ArrayList<>();
        for (Map<String, Object> map : vodList) {
            String video_sourse_id = (String) map.get("video_source_id");
            ids.add(video_sourse_id);
        }
        vodFileService.removeVideoByIdList(ids);
    }

    @Override
    public void removeVideoByCourseId(String id) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.select("video_source_id").eq("course_id", id);
        List<Map<String, Object>> vodList = videoMapper.selectMaps(videoQueryWrapper);
        ArrayList<String> ids = new ArrayList<>();
        for (Map<String, Object> map : vodList) {
            String video_sourse_id = (String) map.get("video_source_id");
            ids.add(video_sourse_id);
        }
        vodFileService.removeVideoByIdList(ids);
    }


}