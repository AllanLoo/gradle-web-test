package com.isp.common.utils;

/**
 * 系统常量配置
 * Created by allan on 15-6-21.
 */
public interface SysConstants {
    /**
     * 系统配置文件路径
      */
    String  SYS_CONFIG_FILE_PATH = "sysconfig.properties";

    /**
     * session 里登录用户id的键
     */
    String SESSION_KEY_LOGIN_USER_ID = "loginUserId";

    /**
     * session 里登录用户名的键
     */
    String SESSION_KEY_LOGIN_USER_NAME = "loginUserName";
}
