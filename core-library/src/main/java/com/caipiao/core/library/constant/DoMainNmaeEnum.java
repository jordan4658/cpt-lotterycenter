package com.caipiao.core.library.constant;

/**
 * 域名类型枚举  对应domain_name表
 */
public enum DoMainNmaeEnum {
    DO_APP("APP", "APP端接口"),
    DO_web("web", "web端接口"),
    DO_FILE("file", "文件上传接口"),
    DO_OSS("oss", "oss文件访问");


    DoMainNmaeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getKey(String key) {
        DoMainNmaeEnum[] aboutUsEnums = values();
        for (DoMainNmaeEnum aboutUsEnum : aboutUsEnums) {
            if (aboutUsEnum.key.equals(key)) {
                return aboutUsEnum.name();
            }
        }
        return null;
    }
}
