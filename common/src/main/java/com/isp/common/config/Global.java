package com.isp.common.config;

import com.isp.common.utils.PropertiesLoader;
import com.isp.common.utils.StringUtils;
import com.isp.common.utils.SysConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全局配置类
 * Created by allan on 15-6-21.
 */
public class Global {
    /**
     * 当前对象实例
     */
    private static Global global = new Global();
    /**
     * 保存全局属性值
     */
    private static Map<String,String> map = new HashMap<String,String>();
    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader propertiesLoader = new PropertiesLoader(SysConstants.SYS_CONFIG_FILE_PATH);

    public static Global getInstance() {
        return global;
    }

    /**
     * 获取配置
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        String v = map.get(key);
        if(v == null) {
            v = propertiesLoader.getProperty(key);
            map.put(key,v != null ? v : StringUtils.EMPTY);
        }
        return v;
    }

}
