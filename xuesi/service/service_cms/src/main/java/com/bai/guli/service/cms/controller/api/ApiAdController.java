package com.bai.guli.service.cms.controller.api;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.cms.entity.Ad;
import com.bai.guli.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/api/cms/ad")
@Slf4j
@Api("广告推荐管理")
public class ApiAdController {

    @Autowired
    private AdService adService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String adTypeId) {
        List<Ad> ads = adService.selectByAdTypeId(adTypeId);
        return R.ok().data("items", ads);
    }

    @PostMapping("save-test")
    public R saveAd(@RequestBody Ad ad){
        redisTemplate.opsForValue().set("index::ad", ad);
        return R.ok();
    }

    @GetMapping("get-test/{key}")
    public R getAd(@PathVariable String key){
        Ad ad = (Ad)redisTemplate.opsForValue().get(key);
        return R.ok().data("ad", ad);
    }

    @DeleteMapping("remove-test/{key}")
    public R removeAd(@PathVariable String key) {
        Boolean delete = redisTemplate.delete(key);
        Boolean hasKey = redisTemplate.hasKey(key);
        return R.ok();
    }
}
