package com.caipiao.app.service.impl;

import com.caipiao.app.service.*;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.mapper.*;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lzy
 * @create 2018-08-27 14:18
 **/
@Service
public class TokenIpServiceImpl implements TokenIpService {

    private final static Logger logger = LoggerFactory.getLogger(TokenIpServiceImpl.class);
    @Autowired
    private TokenIpMapper tokenIpMapper;


    @Override
    public List<TokenIp> getTokenIpLIst() {
        TokenIpExample tokenIpExample = new TokenIpExample();
        TokenIpExample.Criteria criteria = tokenIpExample.createCriteria();
        criteria.andIsValidEqualTo(1);
        return tokenIpMapper.selectByExample(tokenIpExample);
    }

    @Override
    public TokenIp getTokenIpByToken(String token) {
        TokenIpExample tokenIpExample = new TokenIpExample();
        TokenIpExample.Criteria criteria = tokenIpExample.createCriteria();
        criteria.andIsValidEqualTo(1);
        criteria.andTokenEqualTo(token);
        return tokenIpMapper.selectOneByExample(tokenIpExample);
    }
}
