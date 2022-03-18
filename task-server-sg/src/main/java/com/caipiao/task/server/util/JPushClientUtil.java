package com.caipiao.task.server.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送平台的工具类
 */
@Component
public class JPushClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(JPushClientUtil.class);
    private static boolean isInit = false;
    private static Map<String, List<JPushClient>> clientMap = new HashMap<String, List<JPushClient>>();

    @Autowired
    private JPushProperties jPushPropertiesAutowired;
    private static JPushProperties jPushProperties;

    @PostConstruct
    public void init() {
        jPushProperties = this.jPushPropertiesAutowired;
    }

    // 初始化推送对象
    public static void getJpushClient() {
        if (!isInit) {

            //批量创建极光客户端,按total参数叠加创建
            ClientConfig config = ClientConfig.getInstance();

            //fristOne 创建IOS客户端
            Boolean iosEnvironment = jPushProperties.getIos_apnsproduction();
            //设置生产环境或者测试环境
            config.setApnsProduction(iosEnvironment);
            int iosCount = Integer.parseInt(jPushProperties.getIos_total());
            String iosAppKey[] = jPushProperties.getIos_appkey().split("#");
            String iosSecret[] = jPushProperties.getIos_secret().split("#");
            List<JPushClient> iosList = new ArrayList<JPushClient>();
            for (int i = 0; i < iosCount; i++) {
                JPushClient jpushClient = new JPushClient(iosSecret[i], iosAppKey[i], null, config);
                iosList.add(jpushClient);
            }
            clientMap.put("iosList", iosList);

            //secondTwo 创建安卓客户端
            Integer androidCout = null;
            String androidAppKey[] = null;
            String andriodSercret[] = null;

            androidCout = Integer.parseInt(jPushProperties.getAndroid_total());
            androidAppKey = jPushProperties.getAndroid_appkey().split("#");
            andriodSercret = jPushProperties.getAndroid_secret().split("#");

            List<JPushClient> androidList = new ArrayList<JPushClient>();
            for (int i = 0; i < androidCout; i++) {
                JPushClient jpushClient = new JPushClient(andriodSercret[i], androidAppKey[i], null, config);
                androidList.add(jpushClient);
            }
            clientMap.put("androidList", androidList);
            isInit = true;
        }

    }


    /**
     * @param flag:1为推送全部，0：为一个或者部分     ，2：按照标签来推送
     * @param alias：flag=0时，用户的userId数组
     * @param alert：通知内容
     * @param msgType:                  消息类型
     * @param title:标题
     */
    public static void sendPush(String flag, String alias[], String alert, String msgType, String title) {
        getJpushClient();

        /*发送IOS平台*/
        PushPayload iosPayload = buildPushObject_ios_alias_alert_info(flag, alias, alert, msgType, title, false);
        List<JPushClient> iosList = clientMap.get("iosList");
        pushGo(alert, iosPayload, iosList);

        /*发送安卓平台*/
        PushPayload androidPayload = buildPushObject_android_alias_alert_info(flag, alias, alert, msgType, title, false);
        List<JPushClient> androidList = clientMap.get("androidList");
        pushGo(alert, androidPayload, androidList);


    }

    /**
     * 执行发送
     *
     * @param alert
     * @param iosPayload
     * @param iosList
     */
    private static void pushGo(String alert, PushPayload iosPayload, List<JPushClient> list) {
        for (int i = 0; i < list.size(); i++) {
            JPushClient tempJpushClient = list.get(i);
            try {
                PushResult result = tempJpushClient.sendPush(iosPayload);
                logger.info("Got result - " + result);
            } catch (APIConnectionException e) {
                // Connection error, should retry later
                logger.error("Connection error, should retry later", e);
                continue;
            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                logger.error("Should review the error, and fix the request", e);
                logger.info("HTTP Status: " + e.getStatus());
                logger.info("Error Code: " + e.getErrorCode());
                logger.info("Error Message: " + e.getErrorMessage());
                continue;
            } catch (Exception e) {
                logger.error("sendPush Error ,errorInfo: {" + e + "},alert  :{" + alert + "}");
                continue;
            }
        }
    }

    /**
     * 发送自定义消息
     * APP通知栏不显示
     *
     * @param msgType: 消息类型 (需和前端协商)
     */
    public static void sendCustomePush(String msgType) {
        getJpushClient();

        /*发送IOS平台*/
        PushPayload iosPayload = buildPushObject_ios_alias_alert_info("1", null, "custome message content", msgType, "custome message", true);
        List<JPushClient> iosList = clientMap.get("iosList");
        pushGo(iosPayload, iosList);

        /*发送安卓平台*/
        List<JPushClient> androidList = clientMap.get("androidList");
        PushPayload androidPayload = buildPushObject_android_alias_alert_info("1", null, "custome message content", msgType, "custome message", true);
        pushGo(androidPayload, androidList);

    }

    /**
     * 执行发送
     *
     * @param iosPayload
     * @param iosList
     */
    private static void pushGo(PushPayload iosPayload, List<JPushClient> list) {
        for (int i = 0; i < list.size(); i++) {
            JPushClient tempJpushClient = list.get(i);
            try {
                PushResult result = tempJpushClient.sendPush(iosPayload);
                logger.info("Got result - " + result);
            } catch (APIConnectionException e) {
                // Connection error, should retry later
                logger.error("Connection error, should retry later", e);
                continue;
            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                logger.error("Should review the error, and fix the request", e);
                logger.info("HTTP Status: " + e.getStatus());
                logger.info("Error Code: " + e.getErrorCode());
                logger.info("Error Message: " + e.getErrorMessage());
                continue;
            } catch (Exception e) {
                logger.error("sendPush Error ,errorInfo: {" + e + "}");
                continue;
            }
        }
    }

    /**
     * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
     *
     * @param flag:1为全部推送，0：别名推送
     * @param alias：当flag=0时，推送别名；当flag=2时，推送标签
     * @param alert:推送内容
     * @param msgType:                          1：展示窗口，0：不展示窗口
     * @param title:标题
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert_info(String flag, String[] alias, String alert, String msgType, String title, boolean isCustomMsg) {
        PushPayload.Builder builder = PushPayload.newBuilder().setPlatform(Platform.all());
        if ("1".equals(flag)) {
            builder.setAudience(Audience.all());
        } else if ("2".equals(flag)) {
            builder.setAudience(Audience.tag(alias));
        } else {
            builder.setAudience(Audience.alias(alias));
        }

        //是否是自定义消息
        if (isCustomMsg) {
            builder.setMessage(Message.newBuilder()
                    .setMsgContent(alert)
                    .setTitle(title)
                    .addExtra("msgType", msgType)
                    .build());
        } else {
            AndroidNotification androidNotification = AndroidNotification.newBuilder().setTitle(title).setAlert(alert).addExtra("msgType", msgType).build();
            IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody(title, null, alert).build();
            IosNotification iosNotification = IosNotification.newBuilder().setAlert(iosAlert).addExtra("msgType", msgType).build();

            builder.setNotification(Notification.newBuilder().addPlatformNotification(androidNotification)
                    .addPlatformNotification(iosNotification)
                    .build());
        }

        return builder.build();
    }

    /**
     * 构建推送对象：IOS平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
     *
     * @param flag:1为全部推送，0：别名推送
     * @param alias：当flag=0时，推送别名；当flag=2时，推送标签
     * @param alert:推送内容
     * @param msgType:                          1：展示窗口，0：不展示窗口
     * @param title:标题
     * @return
     */
    public static PushPayload buildPushObject_ios_alias_alert_info(String flag, String[] alias, String alert, String msgType, String title, boolean isCustomMsg) {
        PushPayload.Builder builder = PushPayload.newBuilder().setPlatform(Platform.all());
        if ("1".equals(flag)) {
            builder.setAudience(Audience.all());
        } else if ("2".equals(flag)) {
            builder.setAudience(Audience.tag(alias));
        } else {
            builder.setAudience(Audience.alias(alias));
        }

        //是否是自定义消息
        if (isCustomMsg) {
            builder.setMessage(Message.newBuilder()
                    .setMsgContent(alert)
                    .setTitle(title)
                    .addExtra("msgType", msgType)
                    .build());
        } else {
            IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody(title, null, alert).build();
            IosNotification iosNotification = IosNotification.newBuilder().setAlert(iosAlert).addExtra("msgType", msgType).build();

            builder.setNotification(Notification.newBuilder()
                    .addPlatformNotification(iosNotification)
                    .build());
        }

        return builder.build();
    }

    /**
     * 构建推送对象：android平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
     *
     * @param flag:1为全部推送，0：别名推送
     * @param alias：当flag=0时，推送别名；当flag=2时，推送标签
     * @param alert:推送内容
     * @param msgType:                          1：展示窗口，0：不展示窗口
     * @param title:标题
     * @return
     */
    public static PushPayload buildPushObject_android_alias_alert_info(String flag, String[] alias, String alert, String msgType, String title, boolean isCustomMsg) {
        PushPayload.Builder builder = PushPayload.newBuilder().setPlatform(Platform.all());
        if ("1".equals(flag)) {
            builder.setAudience(Audience.all());
        } else if ("2".equals(flag)) {
            builder.setAudience(Audience.tag(alias));
        } else {
            builder.setAudience(Audience.alias(alias));
        }

        //是否是自定义消息
        if (isCustomMsg) {
            builder.setMessage(Message.newBuilder()
                    .setMsgContent(alert)
                    .setTitle(title)
                    .addExtra("msgType", msgType)
                    .build());
        } else {
            AndroidNotification androidNotification = AndroidNotification.newBuilder().setTitle(title).setAlert(alert).addExtra("msgType", msgType).build();

            builder.setNotification(Notification.newBuilder().addPlatformNotification(androidNotification).build());
        }

        return builder.build();
    }

}
