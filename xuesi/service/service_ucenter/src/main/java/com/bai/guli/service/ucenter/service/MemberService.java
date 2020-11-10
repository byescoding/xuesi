package com.bai.guli.service.ucenter.service;

import com.bai.guli.service.base.dto.MemberDto;
import com.bai.guli.service.ucenter.entity.Member;
import com.bai.guli.service.ucenter.entity.vo.LoginVo;
import com.bai.guli.service.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author escoding
 * @since 2020-09-28
 */
public interface MemberService extends IService<Member> {

    //统计当天的注册人数
    Integer countRegisterNum(String day);


    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    MemberDto getMemberDtoByMemberId(String memberId);

    Member getByOpenid(String openid);
}


