package com.bai.guli.service.vod.controller.admin;

import com.bai.guli.common.base.result.R;
import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.common.base.util.ExceptionUtils;
import com.bai.guli.service.base.exception.CollegeException;
import com.bai.guli.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Api("视频文件管理")
@RestController
@Slf4j
@RequestMapping("/admin/vod/media")
public class MediaController {

    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    @ApiOperation("上传视频文件")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String videoId = videoService.uploadVideo(inputStream, originalFilename);
            return R.ok().message("视频上传成功").data("videoId", videoId);
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new CollegeException(ResultCode.VIDEO_UPLOAD_TOMCAT_ERROR);
        }
    }

    @DeleteMapping("remove/{vodId}")
    @ApiOperation("删除视频文件")
    public R removeVideo(
            @ApiParam(value = "阿里云视频id", required = true)
            @PathVariable("vodId") String vodId) {

        try {
            videoService.removeVideo(vodId);
            return R.ok().message("视频删除成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new CollegeException(ResultCode.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }


    @DeleteMapping("/remove")
    public R removeVideoByIdList(
            @ApiParam(value = "阿里云视频id列表", required = true)
            @RequestBody List<String> videoIdList) {

        try {
            videoService.removeVideoByIdList(videoIdList);
            return R.ok().message("视频删除成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new CollegeException(ResultCode.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }

}