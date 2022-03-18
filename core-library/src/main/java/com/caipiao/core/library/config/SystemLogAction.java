package com.caipiao.core.library.config;

import com.alibaba.fastjson.JSON;
import com.caipiao.core.library.annotation.CheckLogin;
import com.caipiao.core.library.annotation.SystemLog;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.model.aop.AppMemberModel;
import com.caipiao.core.library.rest.read.aop.AopReadServiceRest;
import com.caipiao.core.library.rest.write.aop.AopWriteServiceRest;
import com.caipiao.core.library.tool.BaseUtil;
import com.caipiao.core.library.tool.CommonUtils;
import com.caipiao.core.library.tool.SourceUtil;
import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.OperateSensitiveLog;
import com.mapper.domain.Operater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: admin
 * @Description:aop处理
 * @Version: 1.0.0
 * @Date; 2017-10-31 10:58
 */
@Aspect
@Component
public class SystemLogAction {

	private static final Logger log = LoggerFactory.getLogger(SystemLogAction.class);

    @Autowired
    private AopWriteServiceRest aopWriteServiceRest;

    @Autowired
    private AopReadServiceRest aopReadServiceRest;

    /**
     * 切控制器记录指定用户操作日志
     * 配置接入点:第一个”*“符号表示返回值的类型任意,包名后面的”..“表示当前包及子包,
     * 第二个”*“表示类名，*即所有类,.*(..)表示任何方法名，括号表示参数，两个点表示任何参数类型
     */
    @Pointcut("execution(* com.caipiao.*.controller..*.*(..))")
    private void controllerAspect(){}


    /**
     * @Author: admin
     * @Description:登录校验和日志记录
     * @Version: 1.0.0
     * @Date; 2017/11/1 9:20
     * @param: [pjp]
     * @return: java.lang.Object
     */
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Object object = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 方法通知前获取时间,用来计算模块执行时间的
        long start = System.currentTimeMillis();
        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 拦截的方法参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();

        // 获得被拦截的方法
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        }

        if (null != method) {
        	String url = request.getRequestURL().toString();
    		if(url.indexOf("paycallback") != -1){
    			object = pjp.proceed();
            return object;
    		}

            //获取areaMark
            String mark = request.getParameter("areaMark");
            int areaMark = 0;
            if (!CommonUtils.isNull(mark)){
                areaMark = Integer.parseInt(mark);
            }
            // 判断是否包含自定义的注解
            if (method.isAnnotationPresent(SystemLog.class) || method.isAnnotationPresent(CheckLogin.class)) {
                boolean isLogin = true;
                //token错误类型，0：token校验错误 ,1：token过期
                int errorType = 0;
                if(method.isAnnotationPresent(CheckLogin.class)){
                    //登录校验判断
                    String token = request.getParameter("token");
                    String uid = request.getParameter("uid");
                    //请求头中获取token
                    if(CommonUtils.isNull(token)){
                        token = request.getHeader("appToken");
                    }
                    if(CommonUtils.isNull(uid)){
                        uid = request.getHeader("uid");
                    }
                    log.debug(String.format("url=%s, token=%s, uid=%s", request.getRequestURL(), token, uid));
                    if (CommonUtils.isNull(token) || CommonUtils.isNull(uid)) {
                        log.warn(String.format("warn:token or uid is null !!! url=%s, token=%s, uid=%s", request.getRequestURL(), token, uid));
                        isLogin = false;
                    } else {
                        Integer id = 0;
                        try {
                            id = Integer.parseInt(uid);
                        } catch (NumberFormatException e) {
                            log.error(String.format("warn:uid is not number !!! url=%s, token=%s, uid=%s", request.getRequestURL(), token, uid));
                            isLogin = false;
                        }
                        if(isLogin){
                            //在线人数统计
                            String source = SourceUtil.getClientSource(request);
                            if(source != null){
                                aopWriteServiceRest.saveMemberOnline(source, uid);
                            }

                            AppMemberModel member = queryAppMemberById(id);
                            if (member == null) {
                                log.warn(String.format("warn:get app_member is null !!! url=%s, token=%s, uid=%s", request.getRequestURL(), token, uid));
                                isLogin = false;
                            } else {
                                //判断token是否过期
                                Long tokenTime = member.getTokenTime();
                                long nowTime = TimeHelper.time();
                                tokenTime += Constants.TOKEN_EXPIRES;
                                if(tokenTime < nowTime){
                                    isLogin = false;
                                    errorType = 1;
                                }else{
                                    //判断token是否一致
                                    if (!member.getToken().equals(token)) {
                                        log.warn(String.format("warn:token not match !!! url=%s, req_token=%s, uid=%s, target_token=%s", request.getRequestURL(), token, uid, member.getToken()));
                                        isLogin = false;
                                    }
                                }
                            }
                        }
                    }
                }

                if(isLogin){
                    //判断是否要记录日志
                    if(method.isAnnotationPresent(SystemLog.class)){
                        object = saveSystemLog(pjp, object, request, start, method);
                    }else{
                        //登录校验成功
                        object = pjp.proceed();
                        //更改中英文提示,默认中文
                        object = judgeAreaMark(object, areaMark);
                    }

                }else {
                    //登录校验失败
                    ResultInfo<Void> resultInfo = new ResultInfo<>();
                    if(errorType == 0){
                        //areaMark 0:中文提示，1:英文提示
                        if(areaMark == 0){
                            resultInfo.setInfo(StatusCode.INVALID_TOKEN.getValue());
                            resultInfo.setStatus(StatusCode.INVALID_TOKEN.getCode());
                        }else {
                            resultInfo.setInfo(StatusCode.INVALID_TOKEN_EN.getValue());
                            resultInfo.setStatus(StatusCode.INVALID_TOKEN_EN.getCode());
                        }
                    }else{
                        if(areaMark == 0){
                            resultInfo.setInfo(StatusCode.OVERDUE_TOKEN.getValue());
                            resultInfo.setStatus(StatusCode.OVERDUE_TOKEN.getCode());
                        }else {
                            resultInfo.setInfo(StatusCode.OVERDUE_TOKEN_EN.getValue());
                            resultInfo.setStatus(StatusCode.OVERDUE_TOKEN_EN.getCode());
                        }
                    }
//                    object = resultInfo;
                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                    response.setHeader("Content-type", "application/json;charset=UTF-8");
                    response.getWriter().print(JSON.toJSONString(resultInfo));
                }
            }else {//没有包含注解，直接执行
                object = pjp.proceed();
                //更改中英文提示,默认中文
                object = judgeAreaMark(object, areaMark);
            }
        } else { //不需要拦截直接执行
            object = pjp.proceed();
        }
        return object;
    }

    /**
     * @Author: admin
     * @Description:保存系统日志
     * @Version: 1.0.0
     * @Date; 2017/12/5 12:57
     * @param pjp
     * @param object
     * @param request
     * @param start
     * @param method
     * @return
     */
    private Object saveSystemLog(ProceedingJoinPoint pjp, Object object, HttpServletRequest request, long start, Method method) {
        //常见日志实体对象
        OperateSensitiveLog log = new OperateSensitiveLog();
        log.setOperateIp(BaseUtil.getIpAddress(request));//ip
        log.setRequestType(request.getMethod());//请求类型GET_POST
        log.setUrl(request.getRequestURI());//URL
        //获取自定义注解的模块信息和方法信息
        SystemLog systemlog = method.getAnnotation(SystemLog.class);
        log.setModule(systemlog.module());
        log.setMethods(systemlog.methods());
        log.setCreateTime(TimeHelper.date());
        String uid = request.getParameter("uid");
        int id = 0;
        if(uid != null && !uid.isEmpty()){
            id = Integer.parseInt(uid);
        }
        try {
            //开始执行
            object = pjp.proceed();
            AppMemberModel member;
            member = getAppMemberModel(request, id);
            if(member != null){
                getMemberIdAndAccount(log, member);
                long end = System.currentTimeMillis();
                //将计算好的响应时间保存在实体中
                log.setResponseTime(end-start);
                //响应结果，0：执行成功，1：执行失败
                log.setResponseResult(0);
                //保存进数据库
                saveOperateLog(log);
            }
        } catch (Throwable e) {
            AppMemberModel member;
            member = getAppMemberModel(request, id);
            if(member != null){
                getMemberIdAndAccount(log, member);
                long end = System.currentTimeMillis();
                log.setResponseTime(end-start);
                log.setResponseResult(1);
                saveOperateLog(log);
            }
        }
        return object;
    }

    /**
     * 获取用户信息
     * @param request
     * @param id
     * @return
     */
    private AppMemberModel getAppMemberModel(HttpServletRequest request, int id) {
        AppMemberModel member;
        if(id > 0){
            //说明是app端
            member = queryAppMemberById(id);
        }else{
            //说明后台管理
            member = new AppMemberModel();
            Operater operater = (Operater)request.getSession().getAttribute(Constants.SESSION_KEY);
            if(operater == null){
                //说明未成功登录
                String accountNologin = request.getParameter("account");
                member.setId(0);
                member.setNickname(accountNologin);
            }else{
                member.setId(operater.getId());
                member.setNickname(operater.getAccount());
            }
        }
        return member;
    }

    /**
     * @Author: admin
     * @Description:根据标记更改提示语言
     * @Version: 1.0.0
     * @Date; 2017/12/5 12:57
     * @param object
     * @param areaMark 0:中文，1:英文
     * @return
     */
    private Object judgeAreaMark(Object object, int areaMark) {

        if(areaMark != 0){
            ResultInfo<Object> resultInfo = (ResultInfo<Object>) object;
            String status = resultInfo.getStatus();
            //错误返回，code统一为-1
            if("-1".equals(status)){
                resultInfo.setInfo(StatusCode.UNKNOW_ERROR_EN.getValue());
            }
            //成功返回，code统一为1
            if(status.startsWith("1")){
                int length = status.length();
                String code = status.replaceFirst("1","3");
                String info = StatusCode.getStatusCode(code)==null?"notEnCode":StatusCode.getStatusCode(code).getValue();
                if("notEnCode".equals(info)){
                    log.error("提示code:" + status + "，没有配对的英文提示信息！！！");
                }else{
                    if(length == 1){
                        resultInfo.setStatus(StatusCode.OK.getCode());
                    }else {
                        resultInfo.setStatus(code);
                    }
                    resultInfo.setInfo(info);
                }
            }
            object = resultInfo;
        }
        return object;
    }

    /**
     * @Author: admin
     * @Description:获取用户帐号,id
     * @Version: 1.0.0
     * @Date; 2017/11/20 14:11
     * @param: [log, me]
     * @return: void
     */
    private void getMemberIdAndAccount(OperateSensitiveLog log, AppMemberModel member) {

        if(member != null){
            log.setUid(member.getId());
            String nickname = member.getPhone();
            if(nickname == null){
                nickname = member.getNickname();
            }
            if(nickname == null){
                nickname = "";
            }
            log.setAccount(nickname);
        }
    }

    /**
     * @Author: admin
     * @Description:保存系统日志
     * @Version: 1.0.0
     * @Date; 2017/10/31/031 13:50
     * @param: [log] 日志对象
     * @return: void
     */
    private void saveOperateLog(OperateSensitiveLog log) {
        if(log != null){
            aopWriteServiceRest.saveOperateLogModel(log);
        }
    }

    /**
     * @Author: admin
     * @Description:根据id查询appMember
     * @Version: 1.0.0
     * @Date; 2017/11/17 17:55
     * @param: [id]
     * @return: com.zhuawawa.mapper.domain.AppMember
     */
    public AppMemberModel queryAppMemberById(Integer id) {
        return aopReadServiceRest.queryAppMemberById(id);
    }

}
