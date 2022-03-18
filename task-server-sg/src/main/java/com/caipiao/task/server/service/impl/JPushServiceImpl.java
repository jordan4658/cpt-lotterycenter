package com.caipiao.task.server.service.impl;

import com.caipiao.core.library.constant.Constants;
import com.caipiao.task.server.util.JPushClientUtil;
import com.caipiao.task.server.service.JPushService;
import com.mapper.JPushInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPushServiceImpl implements JPushService {
    private final String WIN_PUSH = "win_push";
    private final String OPEN_PUSH = "open_push";

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private JPushInfoMapper jPushInfoMapper;

    /**
     * 废弃，使用标签推送
     *
     * @param lotteryName
     * @param issue
     * @param openNumber
     */
    @Override
    public void openNmberPush(String lotteryName, String issue, String openNumber) {
        //开启了推送设置的用户集合
        List<Integer> userList = jPushInfoMapper.getPushSettingOnUser(OPEN_PUSH);

        String pushMsg = String.format("尊敬的用户，%s第%s期已开奖！开奖号码为：%s", lotteryName, issue, openNumber);

        for (Integer user_id : userList) {
            taskExecutor.execute(() -> {
                JPushClientUtil.sendPush("0", new String[]{String.valueOf(user_id)}, pushMsg, Constants.MSG_TYPE_OPENPUSH, "开奖通知");
            });
        }
    }
}
