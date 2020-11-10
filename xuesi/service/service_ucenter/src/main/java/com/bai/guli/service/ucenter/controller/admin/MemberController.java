package com.bai.guli.service.ucenter.controller.admin;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author escoding
 * @since 2020-09-28
 */

@RestController
@Slf4j
@Api("用户注册登录管理")
@RequestMapping("/admin/ucenter/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "根据日期统计注册人数")
    @GetMapping(value = "count-register-num/{day}")
    public R countRegisterNum(
            @ApiParam(name = "day", value = "统计日期")
            @PathVariable("day") String day) {
        Integer num = memberService.countRegisterNum(day);
        return R.ok().data("registerNum", num);
    }

}

