package com.bai.guli.service.cms.controller;


import com.bai.guli.common.base.result.R;
import com.bai.guli.service.cms.entity.AdType;
import com.bai.guli.service.cms.service.AdTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 推荐位 前端控制器
 * </p>
 *
 * @author escoding
 * @since 2020-09-27
 */
//@CrossOrigin
@RestController
@Api("广告类型管理")
@RequestMapping("/admin/cms/ad-type")
public class AdTypeController {

    @Autowired
    private AdTypeService adTypeService;

    //插入了广告位
    @RequestMapping("/save")
    @ApiOperation("插入广告位置")
    public R save(@ApiParam(value = "广告位置信息",required = true) @RequestBody AdType adType){
        boolean save = adTypeService.save(adType);
        if (save){
            return R.ok().message("保存成功");
        }else{
            return R.error().message("保存失败");
        }
    }

    @RequestMapping("get/{id}")
    @ApiOperation("获取广告位置信息")
    public R getAdTypeById(@ApiParam(value = "广告位置Id",required = true) @PathVariable("id") String id){
        AdType adType = adTypeService.getById(id);
        if (adType!=null){
          return   R.ok().message("获取数据成功").data("adType",adType);
        }else {
           return R.error().message("该数据不存在");
        }
    }





    @ApiOperation("更新推荐类别")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "讲师推荐类别", required = true) @RequestBody AdType adType) {
        boolean result = adTypeService.updateById(adType);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }


    @ApiOperation("所有推荐类别列表")
    @GetMapping("/list")
    public R listAll() {
        List<AdType> list = adTypeService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation("推荐类别分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit) {

        Page<AdType> pageParam = new Page<>(page, limit);
        IPage<AdType> pageModel = adTypeService.page(pageParam);
        List<AdType> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除推荐类别")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "推荐类别ID", required = true) @PathVariable String id) {

        boolean result = adTypeService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }


}

