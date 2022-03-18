package com.caipiao.core.library.rest.write.appLogin;

import com.caipiao.core.library.dto.appLogin.MemberDTO;
import com.caipiao.core.library.dto.appmember.ErrorPwdDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.appLogin.MemberVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AppLoginWriteRest extends BaseRest{

    /**
     * app登录
     * @param data
     * @return
     */
    @PostMapping("/appLoginFW/appLogin.json")
    ResultInfo<MemberVO> appLogin(@RequestBody MemberDTO data);

    /**
     * 获取验证码
     * @param phone 手机号
     * @param captchaType 验证码类型 1：注册用户，2：忘记密码
     * @return
     */
    @PostMapping("/appLoginFW/saveCaptcha.json")
    ResultInfo<String> saveCaptcha(@RequestParam("phone") String phone, @RequestParam("captchaType") int captchaType);

    /**
     * 重置密码
     * @param phone 手机号
     * @param password 新密码
     * @param captcha 验证码
     * @return
     */
    @PostMapping("/appLoginFW/resetPassword.json")
    ResultInfo<Void> resetPassword(@RequestParam("phone") String phone,
                                   @RequestParam("password") String password, @RequestParam("captcha") String captcha);

    /**
     * 修改密码
     * @param uid 用户id
     * @param password 新密码
     * @param oldPassword 旧密码
     * @return
     */
    @PostMapping("/appLoginFW/updatePassword.json")
    ResultInfo<Void> updatePassword(@RequestParam("uid") String uid, @RequestParam("password") String password, @RequestParam("oldPassword") String oldPassword);

    /**
     * app用户注册
     * @param memberDTO
     * @return
     */
    @PostMapping("/appLoginFW/appEnroll.json")
    ResultInfo<MemberVO> appEnroll(@RequestBody MemberDTO memberDTO);
    
   /**
    * 
    * @param memberDTO
    * @return
    */
   @PostMapping("/appLoginFW/appEnrollForAdmin.json")
   ResultInfo<MemberVO> appEnrollForAdmin(@RequestBody MemberDTO memberDTO);

    /**
     *  修改支付密码
     * @param uid 用户id
     * @param payPassword 支付密码
     * @param oldPayPassword 旧支付密码
     * @return
     */
    @PostMapping("/appLoginFW/updataPayPassword.json")
    ResultInfo<Void> updataPayPassword(@RequestParam("uid") String uid, @RequestParam("payPassword") String payPassword, @RequestParam("oldPayPassword") String oldPayPassword);

    /**
     * @Description:修改手机号
     */
    @PostMapping("/appLoginFW/updatePhone.json")
    ResultInfo<Void> updatePhone(@RequestParam("uid") String uid, @RequestParam("phone") String phone, @RequestParam("captcha") String captcha);

    @PostMapping("/appLoginFW/handlePwdErr.json")
    ResultInfo<MemberVO> handlePwdErr(@RequestBody ErrorPwdDTO errorPwdDTO);

    @PostMapping("/appLoginFW/clearPwdErrLog.json")
    void clearPwdErrLog(@RequestBody ErrorPwdDTO errorPwdDTO);

}
