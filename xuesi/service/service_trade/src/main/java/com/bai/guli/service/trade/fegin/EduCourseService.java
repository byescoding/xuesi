package com.bai.guli.service.trade.fegin;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.trade.fegin.fallback.EduCourseServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "service-edu",fallback = EduCourseServiceFallBack.class)
public interface EduCourseService {
    @GetMapping(value = "/api/college/course/inner/get-course-dto/{courseId}")
        //注意远程调用的话需要在路径参数解析  加上value
    CourseDto getCourseDtoById(@PathVariable(value = "courseId") String courseId);

    @GetMapping("/api/college/course/inner/update-buy-count/{id}")
    R updateBuyCountById(@PathVariable("id") String id);
}
