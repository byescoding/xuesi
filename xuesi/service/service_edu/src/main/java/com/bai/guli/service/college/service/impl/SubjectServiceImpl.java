package com.bai.guli.service.college.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.bai.guli.service.college.entity.Subject;
import com.bai.guli.service.college.entity.excel.ExcelSubjectData;
import com.bai.guli.service.college.entity.vo.SubjectVo;
import com.bai.guli.service.college.listener.ExcelSubjectDataListener;
import com.bai.guli.service.college.mapper.SubjectMapper;
import com.bai.guli.service.college.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * 批量写入文件
     * @param inputStream
     */
    @Override
    public void batchImport(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelSubjectData.class,new ExcelSubjectDataListener(baseMapper)
        ).excelType(ExcelTypeEnum.XLSX).doReadAll();
    }

    @Override
    public List<SubjectVo> nestedList() {
    return baseMapper.selectNestedList("0");
    }
}
