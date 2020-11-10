package com.bai.guli.service.cms.feign;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.cms.feign.Impl.OssFileServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;

@FeignClient(value = "service-oss" ,fallback = OssFileServiceFallBack.class)
public interface OssFileCmsService {
    @DeleteMapping("/admin/oss/file/delete")
    R removeFile(String url);
}
