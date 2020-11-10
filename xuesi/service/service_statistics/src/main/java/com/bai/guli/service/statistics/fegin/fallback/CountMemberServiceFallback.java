package com.bai.guli.service.statistics.fegin.fallback;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.statistics.fegin.CountMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CountMemberServiceFallback implements CountMemberService {
    @Override
    public R countRegisterNum(String day) {
        //错误日志
        log.error("熔断器被执行");
        return R.ok().data("registerNum", 0);
    }
}
