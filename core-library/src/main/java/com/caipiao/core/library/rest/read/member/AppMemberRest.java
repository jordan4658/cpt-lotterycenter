package com.caipiao.core.library.rest.read.member;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.appmember.AppMemberPageVO;
import com.mapper.domain.AppMember;
import com.mapper.domain.MemberOnlineCalc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AppMemberRest {

    /**
     * 后台会员管理页面
     * @param pageNum
     * @param pageSize
     * @param vipId
     * @param account
     * @return
     */
    @PostMapping("/appMember/pageAppMember.json")
    AppMemberPageVO pageAppMember(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("vipId") Integer vipId, @RequestParam("account") String account, @RequestParam("userType") Integer userType, @RequestParam("nickname") String nickname);

    @PostMapping("/appMember/pageAppMemberlist.json")
    PageResult<List<AppMember>> pageAppMemberlist(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("account") String account, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    @PostMapping("/appMember/getAppMemberbyId.json")
    AppMember getAppMemberbyId(@RequestParam("id") Integer id);
    
    @PostMapping("/appMember/getAppMember.json")
    AppMember getAppMember(@RequestParam("account") String account, @RequestParam("mid") String mid, @RequestParam("realname") String realname);

    /**
     * 获取正常的用户数量
     */
    @PostMapping("/appMember/countAvailableAppMember.json")
    Integer countAvailableAppMember();

    @PostMapping("/appMember/pageMemberOnline.json")
    PageResult<List<MemberOnlineCalc>> pageMemberOnline(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);

    @PostMapping("/appMember/selOnlineNum.json")
    MemberOnlineCalc selOnlineNum();
    
    @PostMapping("/appMember/getAppMemberListForNickname.json")
    List<Integer> getAppMemberListForNickname(@RequestParam("nickname") String nickname);
}
