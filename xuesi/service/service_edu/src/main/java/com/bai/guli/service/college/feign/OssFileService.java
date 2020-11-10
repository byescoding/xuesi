package com.bai.guli.service.college.feign;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.feign.fallback.OssFileServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
@Service
@FeignClient(value = "service-oss",fallback = OssFileServiceFallBack.class)  //出现熔断错误时的替代方法   fallback
public interface OssFileService {
    @GetMapping("/admin/oss/file/test")
    R test();

    @DeleteMapping("/admin/oss/file/delete")
    R removeFile(String url);
}
