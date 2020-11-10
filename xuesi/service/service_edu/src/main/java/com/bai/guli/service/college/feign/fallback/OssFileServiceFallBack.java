package com.bai.guli.service.college.feign.fallback;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {
    @Override
    public R test() {
        return R.error().message("出现未知错误");
    }

    @Override
    public R removeFile(String url) {
        log.info("熔断保护");
        return R.error().message("熔断保护");
    }
}
