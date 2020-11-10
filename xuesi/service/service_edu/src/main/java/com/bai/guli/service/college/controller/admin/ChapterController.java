package com.bai.guli.service.college.controller.admin;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.entity.Chapter;
import com.bai.guli.service.college.entity.vo.ChapterVo;
import com.bai.guli.service.college.service.ChapterService;
import com.bai.guli.service.college.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@RestController
@Api("视频章节管理")
@RequestMapping("/admin/college/chapter")
@Slf4j
public class ChapterController {

  @Autowired
    private ChapterService chapterService;
  @Autowired
    private VideoService videoService;

  //添加章节
    @ApiOperation("添加章节信息")
    @PostMapping("/save")
    public R save(@ApiParam(value = "章节信息",required = true) @RequestBody Chapter chapter){
      boolean res = chapterService.save(chapter);
      if (res) {
        return R.ok().message("保存成功");
      } else {
        return R.error().message("保存失败");
      }
    }

    //修改章节
    @ApiOperation("修改章节信息")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "章节信息",required = true) @RequestBody Chapter chapter){
      boolean res = chapterService.updateById(chapter);
      if (res) {
        return R.ok().message("修改成功");
      } else {
        return R.error().message("修改失败");
      }
    }

  @ApiOperation("根据ID获取章节信息")
  @GetMapping("/get/{id}")
  public R getChapterById(@ApiParam(value = "章节信息",required = true) @PathVariable("id") String id){
    Chapter chapter= chapterService.getById(id);
    if (chapter !=null) {
      return R.ok().message("获取成功").data("item",chapter);
    } else {
      return R.error().message("数据不存在");
    }
  }


  //删除章节信息
  @ApiOperation("删除章节信息")
  @DeleteMapping("/delete/{id}")
  public R removeById(@ApiParam(value = "章节信息",required = true)  @PathVariable("id") String id){
      //批量vod的视频
      videoService.removeMediaVideoByChapterId(id);

    boolean res = chapterService.removeChapterById(id);
    if (res) {
      return R.ok().message("删除成功");
    } else {
      return R.error().message("删除失败");
    }
  }

  //或章节信息的嵌套列表
  @ApiOperation("嵌套章节数据列表")
  @GetMapping("nested-list/{courseId}")
  public R nestedListByCourseId(
          @ApiParam(value = "课程ID", required = true)
          @PathVariable String courseId){

    List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
    return R.ok().data("items", chapterVoList);
  }
}



