package com.isp.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 生产唯一性ID算法工具
 * Created by allan on 15-6-20.
 */
public class IdGen {
    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID，通过Random数字生成，中间无分割
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 使用SecureRandom随机生成Long
     * @return
     */
    public static long randomLong(){
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRnandom随机生成Bytes
     * @param length
     * @return
     */
    public static String randomBase62(int length){
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Encodes.encodeBase62(randomBytes);
    }
}
