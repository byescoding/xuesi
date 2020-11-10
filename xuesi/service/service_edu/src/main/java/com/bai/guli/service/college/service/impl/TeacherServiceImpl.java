package com.bai.guli.service.college.service.impl;

import com.bai.guli.common.base.result.R;
import com.bai.guli.service.college.entity.Course;
import com.bai.guli.service.college.entity.Teacher;
import com.bai.guli.service.college.entity.vo.TeacherQueryVo;
import com.bai.guli.service.college.feign.OssFileService;
import com.bai.guli.service.college.mapper.CourseMapper;
import com.bai.guli.service.college.mapper.TeacherMapper;
import com.bai.guli.service.college.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-20
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private CourseMapper courseMapper;


    @Override
    public Page<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");  //根据什么字段进行排序
        //如果条件查询为空的话就普通分页查询
        if (teacherQueryVo ==null){
            return baseMapper.selectPage(pageParam,queryWrapper);
        }
        //否则及开始条件查询
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();

        if (!StringUtils.isEmpty(name)){
            queryWrapper.likeRight("name",name);
        }
        if (level !=null){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(joinDateBegin)){
            queryWrapper.ge("join_date",joinDateBegin);
        }
        if (!StringUtils.isEmpty(joinDateEnd)){
            queryWrapper.le("join_data",joinDateEnd);
        }

        return baseMapper.selectPage(pageParam,queryWrapper);
    }

    /**
     * 模糊查询name
     * @param key
     * @return
     */
    @Override
    public List<Map<String, Object>> selectNameListByKey(String key) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        queryWrapper.likeRight("name", key);

        return baseMapper.selectMaps(queryWrapper);
    }

    @Override
    public Boolean removeAvatarById(String id) {
        Teacher teacher = baseMapper.selectById(id);
        //先判断是否为空
        if (teacher !=null){
            //先获取对应教师头像的url地址
            R r = ossFileService.removeFile(teacher.getAvatar());
            return r.getSuccess();
        }
        return false;
    }

    @Override
    public Map<String, Object> getCoursesByTeacherId(String id) {
        HashMap<String, Object> map = new HashMap<>();
        Teacher teacher = baseMapper.selectById(id);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",id);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        map.put("courseList",courses);
        map.put("item",teacher);
        return map;
    }

    /**
     * 教师排行
     * @return
     */
    @Override
    public List<Teacher> webSelectTeacherList() {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort").last("limit 4");
        return baseMapper.selectList(queryWrapper);
    }
}
