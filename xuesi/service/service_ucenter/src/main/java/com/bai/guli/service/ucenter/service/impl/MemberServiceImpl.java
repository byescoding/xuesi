package com.bai.guli.service.ucenter.service.impl;


import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.common.base.util.FormUtils;
import com.bai.guli.common.base.util.JwtInfo;
import com.bai.guli.common.base.util.JwtUtils;
import com.bai.guli.common.base.util.MD5;
import com.bai.guli.service.base.dto.MemberDto;
import com.bai.guli.service.base.exception.CollegeException;
import com.bai.guli.service.ucenter.entity.Member;
import com.bai.guli.service.ucenter.entity.vo.LoginVo;
import com.bai.guli.service.ucenter.entity.vo.RegisterVo;
import com.bai.guli.service.ucenter.mapper.MemberMapper;
import com.bai.guli.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author escoding
 * @since 2020-09-28
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 注册会员的人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterNum(String day) {
        Integer count = baseMapper.selectRegisterNumByDay(day);
        return  count;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //先取出注册信息
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //判断手机号码是否合法   数据库中是否已经存该数据
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new CollegeException(ResultCode.PARAM_ERROR);
        }

        //开始校验验证码
        String checkCode = (String)redisTemplate.opsForValue().get(mobile);
        if (!code.equals(checkCode)){
            throw new CollegeException(ResultCode.CODE_ERROR);
        }

        //开始在数据库进行查询  是否已经存在该号码
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(memberQueryWrapper);
        //如果为空的话就进行查询操作
        if (count > 0){
            throw new CollegeException(ResultCode.REGISTER_MOBLE_ERROR);
        }
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setDisabled(false);
        member.setAvatar("https://edu-college.oss-cn-shenzhen.aliyuncs.com/avatar/2020/07/27/20200727205056.jpg");
        baseMapper.insert(member);
    }


    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数的合法性
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile) ){
            throw new CollegeException(ResultCode.PARAM_ERROR);
        }

        //查询是否存在该用户
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        Member member = baseMapper.selectOne(memberQueryWrapper);

        if (member == null){
            throw new CollegeException(ResultCode.LOGIN_ERROR);
        }

        //校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new CollegeException(ResultCode.LOGIN_ERROR);
        }

        //查看用户是否已经被禁用]
        if (member.getDisabled()){
                throw new CollegeException(ResultCode.LOGIN_DISABLED_ERROR);
        }

        //返回token
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(member.getId());
        jwtInfo.setNickname(member.getNickname());
        jwtInfo.setAvatar(member.getAvatar());
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800); //设置token的过期时间

        return jwtToken;
    }


    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {

        Member member = baseMapper.selectById(memberId);
        MemberDto memberDto = new MemberDto();
        BeanUtils.copyProperties(member,memberDto);
        return memberDto;
    }


        @Override
        public Member getByOpenid(String openid) {
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid", openid);
            return baseMapper.selectOne(queryWrapper);
        }

}
