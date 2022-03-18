package com.caipiao.core.library.rest.read.appmember;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.appmember.MemberInfoVO;
import com.caipiao.core.library.vo.appmember.MemberInstationMessageVO;
import com.caipiao.core.library.vo.appmember.MessageVO;
import com.caipiao.core.library.vo.start.AppNoticeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

/**
 * 会员有关个人信息查询的接口
 */
@FeignClient(name = BUSINESS_READ)
public interface MemberInfoRest {

    /**
     * 用户的账号信息
     * @param memberId 会员
     * @return
     */
    @PostMapping("/memberInfo/myAccount.json")
    ResultInfo<MemberInfoVO> myAccount(@RequestParam("memberId") Integer memberId);

    /**
     * 用户的登录历史
     * @param memberId 会员id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/memberInfo/pageLoginLog.json")
    ResultInfo<Map<String,Object>> pageLoginLog(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取会员站内信
     * @param memberId 会员id
     * @return
     */
    @PostMapping("/memberInfo/listInstationMessage.json")
    List<MemberInstationMessageVO> listInstationMessage(@RequestParam("memberId") Integer memberId);

    /**
     * 获取滚动公告
     * @return
     */
    @PostMapping("/memberInfo/listRollNotice.json")
    List<AppNoticeVO> listRollNotice();

    /**
     * 获取公告列表
     * @return
     */
    @PostMapping("/memberInfo/listNotice.json")
    List<AppNoticeVO> listNotice();

    /**
     * 获取公告详情
     * @param id
     * @return
     */
    @PostMapping("/memberInfo/getOneNotice.json")
    AppNoticeVO getOneNotice(@RequestParam("id") Integer id);

    /**
     * 获取通知列表
     * @return
     */
    @PostMapping("/memberInfo/listInform.json")
    List<MessageVO> listInform();
}
