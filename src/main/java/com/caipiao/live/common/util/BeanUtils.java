package com.caipiao.live.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Desc:
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

    /** 系统共享的聚集 */
    private static final Map<String, Object> INSTANCE_MAP = new ConcurrentHashMap<>();
    /** 系统共享的ARRAYLIST */
    private static final List<Object> ARRAYLIST_INSTANCE = new ArrayList<>();
    /** 系统共享的LINKEDLIST */
    private static final List<Object> LINKEDLIST_INSTANCE = new LinkedList<>();

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 重载属性copy方法实现非null属性copy
     * 场景：适合于更新数据，新增数据
     *
     * @param source
     * @param target
     * @throws BeansException
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);

                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * 重载属性copy方法实现非空(null 或 '')属性copy
     * 场景：适合于查询使用
     *
     * @param source
     * @param target
     * @throws BeansException
     */
    public static void copyPropertiesExcludeNullAndBlank(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);

                        // 这里拦截掉空字符串的属性赋值
                        if (value != null && value instanceof java.lang.String && StringUtils.isEmpty((String) value)) {
                            continue;
                        }

                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * 设置对象的默认属性
     *
     * @param source
     * @throws BeansException
     */
    public static void defaultProperties(Object source) throws BeansException {
        //参数检查
        if (null == source) {
            logger.error("source must not be null!");
            return;
        }

        //设置属性默认属性
        Class<?> actualEditable = source.getClass();
        PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor sourcePd : sourcePds) {
            if (sourcePd.getWriteMethod() != null && sourcePd.getReadMethod() != null) {
                try {
                    Method readMethod = sourcePd.getReadMethod();
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(source);
                    if (value == null) {
                        Method writeMethod = sourcePd.getWriteMethod();
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        Object instance = getDefaultProperty(sourcePd.getPropertyType());
                        if (null != instance) {
                            writeMethod.invoke(source, instance);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取默认属性（目前仅支持List接口及子类型）
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object getDefaultProperty(Class<?> clazz) throws Exception {

        // List接口处理
        if (List.class.isAssignableFrom(clazz)) {
            if (clazz.equals(ArrayList.class) || clazz.equals(List.class)) {    //ArrayList实例
                return ARRAYLIST_INSTANCE;
            }

            //LinkedList实例
            if (clazz.equals(LinkedList.class)) {
                return LINKEDLIST_INSTANCE;
            }

            //其他类型的
            if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                String clazzType = clazz.getName();
                Object instance = INSTANCE_MAP.get(clazzType);
                if (null == instance) {
                    instance = clazz.newInstance();
                    INSTANCE_MAP.put(clazzType, instance);
                }
                return instance;
            } else {
                logger.error("获取LIST接口属性默认值失败，未找到合适的接口类型！");
            }
        }
        return null;
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException    如果分析类属*失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属*的 setter 方法失败
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map convertBean(Object bean) throws IntrospectionException,
            IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属*值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属*失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属*的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属*
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属*赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属*赋值失败的时候就不会影响其他属*赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}
