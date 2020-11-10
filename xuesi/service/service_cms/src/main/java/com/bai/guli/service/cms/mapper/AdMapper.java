package com.bai.guli.service.cms.mapper;

import com.bai.guli.service.cms.entity.Ad;
import com.bai.guli.service.cms.entity.Vo.AdVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 广告推荐 Mapper 接口
 * </p>
 *
 * @author escoding
 * @since 2020-09-27
 */
public interface AdMapper extends BaseMapper<Ad> {

    List<AdVo> selectPageByQueryWrapper(Page<AdVo> adVoPage,  @Param(Constants.WRAPPER)QueryWrapper<AdVo> adVoQueryWrapper);
}
