package com.isp.security.shiro;

import com.google.common.collect.Maps;
import com.isp.common.utils.Digests;
import com.isp.common.utils.EhCacheUtils;
import com.isp.common.utils.Encodes;
import com.isp.security.shiro.session.SessionDao;
import org.apache.shiro.session.Session;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 *系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * Created by AllanLoo on 2015/8/5.
 */
@Service
public class SystemService implements InitializingBean{
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Resource
    private SessionDao sessionDao;
    @Override
    public void afterPropertiesSet() throws Exception {

    }
    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param password 密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    /**
     * 是否需要验证码,登录是否超过3次需要输入验证码
     * @param username 用户名
     * @param bLoginFailed 是否登录失败：true-登录失败，计数器加1
     * @param bCleanCounting 登录失败计数器
     * @return true说明登录失败超过3次，需要输入验证码
     */
    public static boolean needValidateCode(String username,boolean bLoginFailed,boolean bCleanCounting) {
        Map<String, Integer> loginFailedMap = (Map<String, Integer>) EhCacheUtils.get("loginFailedMap");
        if (loginFailedMap==null){
            loginFailedMap = Maps.newHashMap();
            EhCacheUtils.put("loginFailedMap", loginFailedMap);
        }

        Integer loginFailedNum = loginFailedMap.get(username);
        if (loginFailedNum==null){
            loginFailedNum = 0;
        }
        if (bLoginFailed){
            loginFailedNum++;
            loginFailedMap.put(username, loginFailedNum);
        }
        if (bCleanCounting){
            loginFailedMap.remove(username);
        }
        return loginFailedNum >= 3;
    }
    /**
     * 获得活动会话
     * @return
     */
    public Collection<Session> getActiveSessions(){
        return sessionDao.getActiveSessions(false);
    }

    public SessionDao getSessionDao() {
        return sessionDao;
    }

    public void setSessionDao(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }
}
