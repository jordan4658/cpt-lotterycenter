package com.caipiao.core.library.rest.write.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface MemberInstationMessageWriteRest {

    /**
     * 根据会员账号发布站内信
     * @param title
     * @param accounts
     * @param intro
     * @param message
     * @param account
     * @return
     */
    @PostMapping("/member/issueInstationMessage.json")
    boolean issueInstationMessage(@RequestParam("title") String title, @RequestParam("accounts") String accounts, @RequestParam("intro") String intro, @RequestParam("message") String message, @RequestParam("account") String account, @RequestParam("jg") Integer jg);

    /**
     * 删除会员站内信
     * @param id
     * @return
     */
    @PostMapping("/member/deleteMessage.json")
    boolean deleteMessage(@RequestParam("id") Integer id);
}
