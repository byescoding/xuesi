package com.bai.guli.service.cms.feign.Impl;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.cms.feign.OssFileCmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileCmsService {
    @Override
    public R removeFile(String url) {
        log.info("熔断保护");
        return R.error().message("调用超时");
    }
}
