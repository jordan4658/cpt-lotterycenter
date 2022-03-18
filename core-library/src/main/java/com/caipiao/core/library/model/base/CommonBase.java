package com.caipiao.core.library.model.base;

import com.caipiao.core.library.constant.StatusCode;


public class CommonBase {

    /**
     * @Author: admin
     * @Description:判断数据库操作结果赋值提示信息
     * @Version: 1.0.0
     * @Date; 2017/12/4 13:51
     * @param count 数据库操作结果
     * @param baseInfo
     */
    protected void setResultInfo(int count,BaseInfo baseInfo){
        //判断响应行封装对应的操作结果
        if (count > 0) {
            setResultOK(baseInfo);
        } else {
            setResultError(baseInfo);
        }

    }

    /**
     * @Author: admin
     * @Description:封装默认成功提示
     * @Version: 1.0.0
     * @Date; 2017/12/4 13:51
     * @param baseInfo
     */
    protected void setResultOK(BaseInfo baseInfo){
        setResultCodeAndInfo(baseInfo,StatusCode.OK.getCode(),StatusCode.OK.getValue());
    }

    /**
     * @Author: admin
     * @Description:封装未知错误提示
     * @Version: 1.0.0
     * @Date; 2017/12/4 13:51
     * @param baseInfo
     */
    protected void setResultError(BaseInfo baseInfo){
        setResultCodeAndInfo(baseInfo,StatusCode.UNKNOW_ERROR.getCode(),StatusCode.UNKNOW_ERROR.getValue());
    }

    /**
     * @Author: admin
     * @Description:赋值参数错误的提示
     * @Version: 1.0.0
     * @Date; 2017/12/4 13:51
     * @param baseInfo
     */
    protected void setResultParamError(BaseInfo baseInfo){
        setResultCodeAndInfo(baseInfo, StatusCode.PARAM_ERROR.getCode(), StatusCode.PARAM_ERROR.getValue());
    }

    /**
     * @Author: admin
     * @Description:封装提示信息
     * @Version: 1.0.0
     * @Date; 2017/12/4 13:51
     * @param baseInfo
     * @param code 提示代号
     * @param info 提示信息
     */
    protected void setResultCodeAndInfo(BaseInfo baseInfo,String code, String info) {
        baseInfo.setStatus(code);
        baseInfo.setInfo(info);
    }

}
