package com.caipiao.live.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;
import java.util.Locale;
import java.util.Map;

/**
 * @author 阿布
 */
@Component
// @Lazy(true)
public class SpringContextUtil implements ApplicationContextAware {

//	private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextUtil.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    public static boolean containsBean(String beanid) {
        return applicationContext.containsBean(beanid);
    }

    /**
     * 获取applicationContext对象
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的id来查找对象
     *
     * @return
     */

    @SuppressWarnings("unchecked")
    public static <T> T getBeanById(String id) {
        return (T) applicationContext.getBean(id);
    }

    /**
     * 根据bean的class来查找对象
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBeanByClass(Class c) {
        return (T) applicationContext.getBean(c);
    }

    /**
     * 根据bean的class来查找所有的对象(包括子类)
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map getBeansByClass(Class c) {
        return applicationContext.getBeansOfType(c);
    }

    public static String getMessage(String key) {
        return applicationContext.getMessage(key, null, Locale.getDefault());
    }
}
