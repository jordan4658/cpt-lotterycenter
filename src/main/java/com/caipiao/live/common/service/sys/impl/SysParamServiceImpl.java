package com.caipiao.live.common.service.sys.impl;

import com.caipiao.live.common.config.CacheConfigs;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.enums.SysParameterEnum;
import com.caipiao.live.common.exception.BusinessException;
import com.caipiao.live.common.mybatis.entity.SysParameter;
import com.caipiao.live.common.mybatis.mapper.SysParameterMapper;
import com.caipiao.live.common.mybatis.mapperext.sys.SysParameterMapperExt;
import com.caipiao.live.common.service.sys.SysParamService;
import com.caipiao.live.common.util.CollectionUtil;
import com.caipiao.live.common.util.StringUtils;
import com.caipiao.live.common.util.redis.RedisBusinessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysParamServiceImpl implements SysParamService {
    private static final Logger logger = LoggerFactory.getLogger(SysParamServiceImpl.class);
    @Resource
    private SysParameterMapper sysParameterMapper;
    @Resource
    private SysParameterMapperExt sysParameterMapperExt;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public SysParameter getByCode(SysParameterEnum sysParameterEnum) throws BusinessException {
        return getByCode(null == sysParameterEnum ? null : sysParameterEnum.getCode());
    }

    @Override
    public SysParameter getByCode(String sysparamcode) throws BusinessException {
        SysParameter sysParameter = RedisBusinessUtil.getSysParameter(sysparamcode);
        if (sysParameter == null) {
            sysParameter = sysParameterMapperExt.selectByCode(sysparamcode);
            RedisBusinessUtil.addSysParameter(sysParameter);
        }
        return sysParameter;
    }

    @Override
    public void initSysParams() {
//        List<String> codes = new ArrayList<>();
//        codes.add(SysParameterEnum.PLATFORM_NAME.getCode());
//        List<SysParameter> sysParameters = sysParameterMapperExt.queryByCodeNames(codes);
//        if (CollectionUtil.isEmpty(sysParameters)) {
//            throw new BusinessException(StatusCode.PARAM_ERROR.getCode(), "系统参数平台码配置异常");
//        }
//
//        Map<String, SysParameter> parameterMap = new HashMap<>();
//        for (SysParameter parameter : sysParameters) {
//            parameterMap.put(parameter.getParamCode().toUpperCase(), parameter);
//        }
//
//        SysParameter platform = parameterMap.get(SysParameterEnum.PLATFORM_NAME.name());
//        if (null == platform || StringUtils.isEmpty(platform.getParamValue())) {
//            throw new BusinessException(StatusCode.PARAM_ERROR.getCode(), "系统参数平台码配置异常");
//        }
//        CacheConfigs.platform = platform.getParamValue().toUpperCase();
//        logger.info("平台码:{} ", CacheConfigs.platform);

        CacheConfigs.platform = "SYLT";

    }


}
