package com.bai.guli.service.cms.service.impl;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.cms.entity.Ad;
import com.bai.guli.service.cms.entity.Vo.AdVo;
import com.bai.guli.service.cms.feign.OssFileCmsService;
import com.bai.guli.service.cms.mapper.AdMapper;
import com.bai.guli.service.cms.service.AdService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-27
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {
    @Autowired
    private OssFileCmsService ossFileCmsService;
    @Override
    public IPage<AdVo> selectPage(Long page, Long limit) {
        QueryWrapper<AdVo> adVoQueryWrapper = new QueryWrapper<>();
        adVoQueryWrapper.orderByDesc("sort","id");
        Page<AdVo> adVoPage = new Page<>(page, limit);
        List<AdVo> adVos = baseMapper.selectPageByQueryWrapper(adVoPage, adVoQueryWrapper);
        Page<AdVo> records = adVoPage.setRecords(adVos);
        return records;
    }

    @Override
    public boolean removeAdImageById(String id) {
        //先获取图片地址
        Ad ad = baseMapper.selectById(id);
        String imageUrl = ad.getImageUrl();
        if (!StringUtils.isEmpty(imageUrl)){
            //执行删除图片操作
            R r = ossFileCmsService.removeFile(imageUrl);
            return r.getSuccess();
        }
          return false;
    }

    //注意这里还需要一个单引号括住
    @Cacheable(value = "index",key = "'selectByAdTypeId'")
    @Override
    public List<Ad> selectByAdTypeId(String adTypeId) {
        QueryWrapper<Ad> adQueryWrapper = new QueryWrapper<>();
        adQueryWrapper.orderByDesc("sort").eq("type_id",adTypeId);
        return baseMapper.selectList(adQueryWrapper);
    }
}
