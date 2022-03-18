package com.caipiao.live.common.service.sys;

import com.caipiao.live.common.enums.SysParameterEnum;
import com.caipiao.live.common.exception.BusinessException;
import com.caipiao.live.common.mybatis.entity.SysParameter;

public interface SysParamService {

    /**
     * 获取系统配置
     *
     * @param sysParameterEnum 系统配置码枚举
     * @return
     * @throws BusinessException
     */
    SysParameter getByCode(SysParameterEnum sysParameterEnum) throws BusinessException;

    /**
     * 获取系统配置
     *
     * @param sysparamcode 系统配置码
     * @return
     * @throws BusinessException
     */
    SysParameter getByCode(String sysparamcode) throws BusinessException;

    void initSysParams();


}
