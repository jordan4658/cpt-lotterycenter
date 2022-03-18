package com.caipiao.core.library.rest.read.appLogin;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.BaseRest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AppLoginReadRest extends BaseRest {

    /**
     * 校验验证码
     *
     * @param phone       手机号
     * @param captchaType 验证码类型 1：注册用户，2：忘记密码
     * @param captcha     验证码
     * @return
     */
    @GetMapping("/appLoginFR/checkCaptcha.json")
    ResultInfo<Void> checkCaptcha(@RequestParam("phone") String phone, @RequestParam("captchaType") int captchaType, @RequestParam("captcha") String captcha);

    @GetMapping("/appLoginFR/thirdLoginIsFirst.json")
    ResultInfo<Map<String, Boolean>> thirdLoginIsFirst(@RequestParam("openid") String openid);

    @GetMapping("/appLoginFR/phoneIsRegisterd.json")
    ResultInfo<Void> thirdLoginNext(@RequestParam("phone") String phone, @RequestParam("captcha") String captcha);
}
