package com.caipiao.core.library.rest.write.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.dto.appmember.AppMemberDTO;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AppMemberWriteRest {

    /**
     * 修改会员投注状态
     * @param id 会员id
     * @param status 投注状态
     * @return
     */
    @PostMapping("/appMember/updateBetStatus.json")
    boolean updateBetStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status);

    /**
     * 修改会员返水状态
     * @param id 会员id
     * @param status 返水状态
     * @return
     */
    @PostMapping("/appMember/updateBackwaterStatus.json")
    boolean updateBackwaterStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status);


    /**
     * 修改会员聊天状态
     * @param id 会员id
     * @param status 聊天状态
     * @return
     */
    @PostMapping("/appMember/updateChatStatus.json")
    boolean updateChatStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status);


    /**
     * 修改会员晒单状态
     * @param id 会员id
     * @param status 晒单状态
     * @return
     */
    @PostMapping("/appMember/updateShareOrderStatus.json")
    boolean updateShareOrderStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status);

    /**
     * 修改会员晒单状态
     * @param account 会员账号
     * @param status 晒单状态
     * @return
     */
    @PostMapping("/appMember/updateShareOrderStatusByAccount.json")
    boolean updateShareOrderStatusByAccount(@RequestParam("account") String account, @RequestParam("status") Integer status);

    /**
     * 修改会员晒单状态
     * @param id 会员id
     * @param status 晒单状态
     * @return
     */
    @PostMapping("/appMember/updateFreezeStatus.json")
    boolean updateFreezeStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status);

    @PostMapping("/appMember/updateAccountInfo.json")
	String updateAccountInfo(@RequestBody AppMemberDTO appMemberDTO);
}
