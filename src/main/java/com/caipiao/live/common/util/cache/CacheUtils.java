package com.caipiao.live.common.util.cache;


import com.caipiao.live.common.enums.SysParameterEnum;
import com.caipiao.live.common.mybatis.entity.SysParameter;
import com.caipiao.live.common.mybatis.mapperext.sys.SysParameterMapperExt;
import com.caipiao.live.common.util.redis.RedisBusinessUtil;

public class CacheUtils {

    /**
     * 统一由此获取SysParameter
     *
     * @param key
     * @param sysParameterMapperExt
     * @return
     */
    public static SysParameter getSysParameter(SysParameterEnum key, SysParameterMapperExt sysParameterMapperExt) {
        if (key == null) {
            return null;
        }
        SysParameter sysParameter = RedisBusinessUtil.getSysParameter(key);
        if (sysParameter == null) {
            sysParameter = sysParameterMapperExt.selectByCode(key.getCode());
            RedisBusinessUtil.addSysParameter(sysParameter);
        }
        return sysParameter;
    }

    /**
     * 统一由此获取SysParameter
     *
     * @param key
     * @param sysParameterMapperExt
     * @return
     */
    public static SysParameter getSysParameter(String key, SysParameterMapperExt sysParameterMapperExt) {
        if (key == null) {
            return null;
        }
        SysParameter sysParameter = RedisBusinessUtil.getSysParameter(key);
        if (sysParameter == null) {
            sysParameter = sysParameterMapperExt.selectByCode(key);
            RedisBusinessUtil.addSysParameter(sysParameter);
        }
        return sysParameter;
    }



}
