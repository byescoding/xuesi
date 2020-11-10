package com.bai.guli.service.college.service.impl;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.college.entity.*;
import com.bai.guli.service.college.entity.form.CourseInfoForm;
import com.bai.guli.service.college.entity.vo.*;
import com.bai.guli.service.college.feign.OssFileService;
import com.bai.guli.service.college.mapper.*;
import com.bai.guli.service.college.service.CourseService;
import com.bai.guli.service.college.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper descriptionMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CourseCollectMapper courseCollectMapper;

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private TeacherService teacherService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        //copy属性到  course   但是要求就是  命名必须一致 不然copy 不了属性
        BeanUtils.copyProperties(courseInfoForm,course);
        course.setStatus(Course.COURSE_DRAFT);
        //保存课程信息
        baseMapper.insert(course);
        //保存文本信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());  //此时course  的id已经生成  这时可以使用
        descriptionMapper.insert(courseDescription);
        return course.getId();
    }

    /**
     * 根据id获取表单信息
     * @param id
     * @return
     */
    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        Course course = baseMapper.selectById(id);
        if (course ==null){
            return  null;
        }
        CourseDescription courseDescription = descriptionMapper.selectById(id);

        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course,courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());
        return  courseInfoForm;
    }

    /**
     *跟新表单信息
     * @param courseInfoForm
     */
    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        //copy属性到  course   但是要求就是  命名必须一致 不然copy 不了属性
        BeanUtils.copyProperties(courseInfoForm,course);
        course.setStatus(Course.COURSE_DRAFT);
        //保存课程信息
        baseMapper.updateById(course);
        //保存文本信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());  //此时course  的id已经生成  这时可以使用
        descriptionMapper.updateById(courseDescription);
    }

    @Override
    public IPage<CourseVo> selectPage(long page, long limit, CourseQueryVo courseQueryVo) {
        //构造查询条件
        QueryWrapper<CourseVo> courseVoQueryWrapper = new QueryWrapper<>();
        courseVoQueryWrapper.orderByAsc("c.gmt_create");
        //取出对应的查询条件
        String subjectId = courseQueryVo.getSubjectId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String teacherId = courseQueryVo.getTeacherId();
        String title = courseQueryVo.getTitle();

        //注意多表查询时要注明字段是哪一个表的
        if (!StringUtils.isEmpty(title)){
            courseVoQueryWrapper.like("c.title",title);
        }
        if (!StringUtils.isEmpty(subjectId)){
            courseVoQueryWrapper.eq("c.subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            courseVoQueryWrapper.eq("c.subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)){
            courseVoQueryWrapper.eq("c.teacher_id",teacherId);
        }

        //开始执行查询

        Page<CourseVo> courseVoPage = new Page<>(page, limit);
        List<CourseVo> courseVos = baseMapper.selectPageByCourseQueryVo(courseVoPage ,courseVoQueryWrapper);
        courseVoPage.setRecords(courseVos);  //返回查询的记录
        return courseVoPage;
    }

    @Override
    public Boolean removeCoverById(String id) {
        Course course = baseMapper.selectById(id);
        //先判断是否为空
        if (course !=null){
            //先获取对应教师头像的url地址
            R r = ossFileService.removeFile(course.getCover());
            return r.getSuccess();
        }
        return false;
    }

    @Override
    public boolean removeCourseById(String id) {
        //按顺序删除关联表间的内容

        //收藏信息：course_collect
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        //评论信息：comment
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        //课时信息：video
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        //章节信息：chapter
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        //删除描述信息
       descriptionMapper.deleteById(id);
    //最后删除课程信息
        return this.removeById(id);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL); //修改发布状态
        return this.updateById(course);
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {
        QueryWrapper<Course>  queryWrapper = new QueryWrapper<>();

        //获取查询条件

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", webCourseQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())) {
            if(webCourseQueryVo.getType() == null || webCourseQueryVo.getType() == 1){
                queryWrapper.orderByAsc("price");
            }else{
                queryWrapper.orderByDesc("price");
            }
        }

        return baseMapper.selectList(queryWrapper);
    }

    /**
     *
     * @param courseId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebCourseVo selectWebCourseVoById(String courseId) {
        //添加访问
        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);
        WebCourseVo webCourseVo = baseMapper.selectWebCourseVoById(courseId);
        return webCourseVo;
    }

    /**
     * 课程排行榜
     * @return
     */
    @Override
    public List<Course> webSelectCourseList() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count").last("limit 8");
        return baseMapper.selectList(courseQueryWrapper);
    }

    @Override
    public void updateBuyCountById(String id) {

    }


    @Override
    public CourseDto getCourseDtoById(String courseId) {
        //根据课程id去组装 dto
       //在这里我们可以使用两种方式进行查询
//        Course course = baseMapper.selectById(courseId);
//
//        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("course_id",courseId);
//        Teacher teacher = teacherService.getOne(queryWrapper);

        //然后开始组装语句

//        方式二  使用连表查询
        return baseMapper.selectCourseDtoById(courseId);
    }
}
