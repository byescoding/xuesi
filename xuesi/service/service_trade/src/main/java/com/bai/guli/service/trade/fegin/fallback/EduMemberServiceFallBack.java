package com.bai.guli.service.trade.fegin.fallback;

import com.bai.guli.service.base.dto.MemberDto;
import com.bai.guli.service.trade.fegin.EduMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EduMemberServiceFallBack implements EduMemberService {
    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        log.info("熔断保护");
        return null;
    }
}
