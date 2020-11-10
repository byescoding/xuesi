package com.bai.guli.service.college.feign.fallback;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.feign.OssFileService;
import com.bai.guli.service.college.feign.VodFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VodFileServiceFallback implements VodFileService {


    @Override
    public R removeVideo(String url) {
        log.info("熔断保护");
        return R.error().message("熔断保护");
    }

    @Override
    public R removeVideoByIdList(List<String> videoIdList) {
        log.info("熔断保护");
        return R.error().message("熔断保护");
    }
}
