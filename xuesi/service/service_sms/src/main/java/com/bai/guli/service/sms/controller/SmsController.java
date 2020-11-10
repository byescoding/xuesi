package com.bai.guli.service.sms.controller;


import com.aliyuncs.exceptions.ClientException;
import com.bai.guli.common.base.result.R;
import com.bai.guli.common.base.util.FormUtils;
import com.bai.guli.common.base.util.RandomUtils;
import com.bai.guli.service.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@Api("发送短信")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("发送短信服务")
    @RequestMapping("/send/{mobile}")
    public R sentSms(@PathVariable("mobile") String mobile) throws ClientException {
        //先判断一下是否合法  是否为空  再去  发送 验证码
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile)){
            return R.error().message("请输入正确的手机号码");
        }
        //获取随机验证码
        String checkCode = RandomUtils.getFourBitRandom();
        //发送短信
//        smsService.send(mobile,checkCode);
        //存到redis  等发送之后就开始比较  五分钟后失效
        redisTemplate.opsForValue().set(mobile,checkCode,5, TimeUnit.MINUTES);
        return R.ok().message("短信发送成功");
    }
}
