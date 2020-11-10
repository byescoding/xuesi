package com.bai.guli.service.trade.service.impl;

import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.service.base.dto.CourseDto;
import com.bai.guli.service.base.dto.MemberDto;
import com.bai.guli.service.base.exception.CollegeException;
import com.bai.guli.service.trade.entity.Order;
import com.bai.guli.service.trade.fegin.EduCourseService;
import com.bai.guli.service.trade.fegin.EduMemberService;
import com.bai.guli.service.trade.mapper.OrderMapper;
import com.bai.guli.service.trade.service.OrderService;
import com.bai.guli.service.trade.utils.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-10-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduMemberService eduMemberService;


    @Override
    public String saveOrder(String courseId, String memberId) {

        //查询当前用户是否已有当前课程的订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        Order orderExist = baseMapper.selectOne(queryWrapper);
        if(orderExist != null){
            return orderExist.getId(); //如果订单已存在，则直接返回订单id
        }

        CourseDto courseDto = eduCourseService.getCourseDtoById(courseId);
        if (courseDto == null){
            throw new CollegeException(ResultCode.PARAM_ERROR);
        }
        MemberDto memberDto = eduMemberService.getMemberDtoByMemberId(memberId);
        if (memberDto == null){
            throw new CollegeException(ResultCode.PARAM_ERROR);
        }
        //开始组装order
        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice().multiply(new BigDecimal(100)));//分
        order.setMemberId(memberId);
        order.setMobile(memberDto.getMobile());
        order.setNickname(memberDto.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//微信支付
        baseMapper.insert(order);
        return order.getId();
    }

    @Override
    public Order getByOrderId(String orderId, String memberId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("id",orderId).eq("member_id",memberId);

        Order order = baseMapper.selectOne(orderQueryWrapper);
        return order;
    }

    @Override
    public Boolean isBuyByCourseId(String courseId, String memberId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("course_id",courseId).eq("member_id",memberId).eq("status",1);

        Integer count = baseMapper.selectCount(orderQueryWrapper);
        //包装类型不能比较 要转换为 int  数值进行比较
        return count.intValue()>0;
    }


    @Override
    public List<Order> selectByMemberId(String memberId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("member_id",memberId).orderByDesc("gmt_create");

        List<Order> orders = baseMapper.selectList(orderQueryWrapper);
        return orders;
    }

    @Override
    public Boolean removeById(String orderId, String memberId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("id",orderId).eq("member_id",memberId);
        boolean remove = this.remove(orderQueryWrapper);
        return remove;
    }
}
