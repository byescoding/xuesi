package com.bai.guli.service.trade.fegin;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.base.dto.MemberDto;
import com.bai.guli.service.trade.fegin.fallback.EduMemberServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "service-ucenter",fallback = EduMemberServiceFallBack.class)
public interface EduMemberService {
    @GetMapping(value = "/api/ucenter/member/inner/get-member-dto/{memberId}")
    MemberDto getMemberDtoByMemberId(@PathVariable(value = "memberId") String memberId);
}
