package com.bai.guli.service.vod.controller.api;
import com.bai.guli.common.base.result.R;
import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.common.base.util.ExceptionUtils;
import com.bai.guli.service.base.exception.CollegeException;
import com.bai.guli.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("视频文件凭证")
@RestController
@Slf4j
@RequestMapping("/api/vod/media")
public class ApiMediaController {
    @Autowired
    private VideoService videoService;

    @GetMapping("get-play-auth/{videoSourceId}")
    public R getPlayAuth(
            @ApiParam(value = "阿里云视频文件的id", required = true)
            @PathVariable("videoSourceId") String videoSourceId) {

        try {
            String playAuth = videoService.getPlayAuth(videoSourceId);
            return R.ok().message("获取播放凭证成功").data("playAuth", playAuth);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new CollegeException(ResultCode.FETCH_PLAYAUTH_ERROR);
        }
    }
}
