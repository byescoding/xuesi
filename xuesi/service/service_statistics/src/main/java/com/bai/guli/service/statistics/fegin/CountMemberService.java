package com.bai.guli.service.statistics.fegin;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.statistics.fegin.fallback.CountMemberServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "service-ucenter" ,fallback = CountMemberServiceFallback.class)
public interface CountMemberService {
    @GetMapping("/admin/ucenter/member/count-register-num/{day}")
    R countRegisterNum(@PathVariable("day") String day);
}
