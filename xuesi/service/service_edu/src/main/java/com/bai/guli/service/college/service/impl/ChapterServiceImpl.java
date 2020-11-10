package com.bai.guli.service.college.service.impl;

import com.bai.guli.service.college.entity.Chapter;
import com.bai.guli.service.college.entity.Video;
import com.bai.guli.service.college.entity.vo.ChapterVo;
import com.bai.guli.service.college.entity.vo.VideoVo;
import com.bai.guli.service.college.mapper.ChapterMapper;
import com.bai.guli.service.college.mapper.VideoMapper;
import com.bai.guli.service.college.service.ChapterService;
import com.bai.guli.service.college.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public boolean removeChapterById(String id) {
        //先删除video 关于该章节的视频信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",id);
          videoMapper.delete(videoQueryWrapper);

     //然后删除章节信息
        return this.removeById(id);
    }


    /**
     * 嵌套章节的处理
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> nestedList(String courseId) {

        //处理数据将数据放进  ChapterVo  的列表中
        ArrayList<ChapterVo> chapterVoList = new ArrayList<>();
        //获取章节信息
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        //通过 sort  id  字段进行排序“
        chapterQueryWrapper.eq("course_id",courseId).orderByAsc("sort","id");
        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);

        //获取视频信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId).orderByAsc("sort","id");
        List<Video> videos = videoMapper.selectList(videoQueryWrapper);




        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
              chapterVoList.add(chapterVo);

            ArrayList<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videos) {
                if ( chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }
}
