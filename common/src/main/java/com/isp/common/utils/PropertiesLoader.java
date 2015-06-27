package com.isp.common.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Properties文件载入工具类，可以载入多个文件，相同的属性会覆盖，但以System的Property优先
 * Created by allan on 15-6-21.
 */
public class PropertiesLoader {
    private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    private  Properties properties = null;

    public PropertiesLoader(String... resourcesPaths){
        properties = loadProperties(resourcesPaths);
    }

    public  Properties getProperties() {
        return properties;
    }

    /**
     * 取出Property，但以System的Property优先，取不到返回空字符串
     * @param key 配置项名称
     * @return 配置项值
     */
    private String getValue(String key){
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return "";
    }

    /**
     * 取出String类型的配置，但以System的配置优先，如果都为Null则抛出异常
     * @param key 配置项名称
     * @return 配置项值
     */
    public String getProperty(String key) {
        String v = getValue(key);
        if (v == null) {
            throw new NoSuchElementException();
        }
        return v;
    }

    /**
     * 取出String类型的配置，但以System的配置优先，如果都为Null则返回defaultValue
     * @param key 配置项名称
     * @param defaultValue 配置项默认值
     * @return 配置项值
     */
    public String getProperty(String key,String defaultValue){
        String v = getValue(key);
        return v != null ? v : defaultValue;
    }

    /**
     * 取出Integer类型的配置，但以System的配置优先，如果都为Null则抛出异常
     * @param key 配置项名称
     * @return 配置项值
     */
    public Integer getInteger(String key) {
        String v = getValue(key);
        if (v == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(v);
    }

    /**
     * 取出Integer类型的配置，但以System的配置优先，如果都为Null则返回defaultValue
     * @param key 配置项名称
     * @param defaultValue 配置项默认值
     * @return 配置项值
     */
    public Integer getInteger(String key,String defaultValue){
        String v = getValue(key);
        return v != null ? Integer.valueOf(v) : Integer.valueOf(defaultValue);
    }

    /**
     * 取出Boolean类型的配置，但以System的配置优先，如果都为Null则抛出异常
     * @param key 配置项名称
     * @return 配置项值
     */
    public Boolean getBoolean(String key) {
        String v = getValue(key);
        if (v == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(v);
    }

    /**
     * 取出Boolean类型的配置，但以System的配置优先，如果都为Null则返回defaultValue
     * @param key 配置项名称
     * @param defaultValue 配置项默认值
     * @return 配置项值
     */
    public Boolean getBoolean(String key,String defaultValue){
        String v = getValue(key);
        return v != null ? Boolean.valueOf(v) : Boolean.valueOf(defaultValue);
    }

    /**
     * 载入多个文件，文件路径使用Spring Resource 格式
     * @param resourcesPaths 资源路径
     * @return 配置对象
     */
    private Properties loadProperties(String... resourcesPaths){
        Properties props = new Properties();

        for (String location : resourcesPaths) {
            InputStream is = null;
            try{
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                props.load(is);
            } catch (IOException ex) {
                logger.info("不能加载配置从路径为{},{}",new Object[]{location,ex.getMessage()});
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        return props;
    }
}
