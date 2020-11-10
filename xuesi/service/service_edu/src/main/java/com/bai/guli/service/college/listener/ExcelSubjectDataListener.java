package com.bai.guli.service.college.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.bai.guli.service.college.entity.Subject;
import com.bai.guli.service.college.entity.excel.ExcelSubjectData;
import com.bai.guli.service.college.mapper.SubjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor //构造初始化  basemapper
@NoArgsConstructor
public class ExcelSubjectDataListener   extends  AnalysisEventListener<ExcelSubjectData> {

    private SubjectMapper subjectMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext analysisContext) {
        log.info("解析到一条记录：{}", data);
        String levelOneTitle = data.getLevelOneTitle();
        String levelTwoTitle = data.getLevelTwoTitle();
        log.info("levelOneTitle:{}", levelOneTitle);
        log.info("levelTwoTitle:{}", levelTwoTitle);

        String parentId;

        //判断一级数据是否存在
        Subject subjectLevelOneTitle = this.getByTitle(levelOneTitle,"0");
        if (subjectLevelOneTitle == null){
            //组装数据，存入数据
            Subject subject = new Subject();
            subject.setParentId("0");
            subject.setTitle(levelOneTitle);
            subjectMapper.insert(subject);
            parentId = subject.getId();
        }else{
            parentId = subjectLevelOneTitle.getId();
        }
        //判断二级分类是否存在
        Subject subjectLevelTwoTitle = this.getByTitle(levelTwoTitle, parentId);
        if (subjectLevelTwoTitle == null){
            Subject subject = new Subject();
            subject.setTitle(levelTwoTitle);
            subject.setParentId(parentId);
            subjectMapper.insert(subject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }

    /**
     * 根据分类的名称和父id来查询数据
     * @param title 分类名名称
     * @param parentId 父id
     * @return
     */
    private Subject getByTitle(String title,String parentId){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id",parentId);
        return subjectMapper.selectOne(queryWrapper);
    }
}
