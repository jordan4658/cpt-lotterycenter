package com.caipiao.app.service;

import com.mapper.domain.TokenIp;

import java.util.List;


public interface TokenIpService {
	List<TokenIp> getTokenIpLIst();
	TokenIp getTokenIpByToken(String token);
}
