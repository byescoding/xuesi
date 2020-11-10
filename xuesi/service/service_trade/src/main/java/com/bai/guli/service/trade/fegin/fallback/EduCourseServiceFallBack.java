package com.bai.guli.service.trade.fegin.fallback;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.trade.fegin.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EduCourseServiceFallBack implements EduCourseService {
    @Override
    public CourseDto getCourseDtoById(String courseId) {
        log.info("熔断保护");
        return null;
    }

    @Override
    public R updateBuyCountById(String id) {
        log.error("熔断器被执行");
        return R.error();
    }
}
