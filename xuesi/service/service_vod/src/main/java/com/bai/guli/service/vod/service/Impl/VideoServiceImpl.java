package com.bai.guli.service.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.service.base.exception.CollegeException;
import com.bai.guli.service.vod.service.VideoService;
import com.bai.guli.service.vod.utils.VodProperties;
import com.bai.guli.service.vod.utils.aliyunVodSDKUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;


@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VodProperties vodProperties;


    @Override
    public String uploadVideo(InputStream inputStream, String originalFilename) {
        //截取文件的命名
        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        UploadStreamRequest request = new UploadStreamRequest(vodProperties.getKeyid(), vodProperties.getKeysecret(), title, originalFilename, inputStream);
        /* 模板组ID(可选) */
        request.setTemplateGroupId(vodProperties.getTemplateGroupId());
        /* 工作流ID(可选) */
        request.setWorkflowId(vodProperties.getWorkflowId());
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        String videoId = response.getVideoId();

        //没有正确的返回videoid则说明上传失败
        if (StringUtils.isEmpty(videoId)) {
            log.error("阿里云上传失败：" + response.getCode() + " - " + response.getMessage());
            throw new CollegeException(ResultCode.VIDEO_UPLOAD_ALIYUN_ERROR);
        }

        return videoId;
    }


    @Override
    public void removeVideo(String videoId) throws ClientException {
        DefaultAcsClient Client = aliyunVodSDKUtils.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());
        //删除视频的关键 请求
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoId);
        Client.getAcsResponse(request); //根据请求获取响应
    }

    @Override
    public void removeVideoByIdList(List<String> videoIdList) throws ClientException {
        DefaultAcsClient Client = aliyunVodSDKUtils.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());
        DeleteVideoRequest request = new DeleteVideoRequest();
        StringBuffer idList = new StringBuffer();
         int size =videoIdList.size();
        for (int i = 0; i <size; i++) {
                idList.append(videoIdList.get(i));

            if (i ==size-1 || i%20==19){
                request.setVideoIds(idList.toString());
                Client.getAcsResponse(request); //根据请求获取响应
                idList =new StringBuffer(); //清空字符串  因为一次最多只能20个  删除
                System.out.println(idList);
            }else if (i%20 <19){
                idList.append(",");
            }
        }

    }

    @Override
    public String getPlayAuth(String videoSourceId) throws ClientException {
        DefaultAcsClient client= aliyunVodSDKUtils.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());
        //创建请求对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoSourceId);

        //获取响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        return response.getPlayAuth();

    }
}
