package com.bai.guli.service.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;


public interface VideoService {

    //上传完视频会返回一个视频id
    String uploadVideo(InputStream inputStream, String originalFilename);

    void removeVideo(String videoId) throws ClientException;

    void removeVideoByIdList(List<String> videoIdList) throws ClientException;

    String getPlayAuth(String videoSourceId) throws ClientException;

}
