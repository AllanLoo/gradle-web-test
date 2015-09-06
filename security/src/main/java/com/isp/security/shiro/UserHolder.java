package com.isp.security.shiro;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.isp.common.container.SpringContextHolder;
import com.isp.common.utils.EhCacheUtils;
import com.isp.security.func.entity.Func;
import com.isp.security.func.service.FuncServcie;
import com.isp.security.role.entity.Role;
import com.isp.security.role.service.RoleService;
import com.isp.security.user.entity.SysUser;
import com.isp.security.user.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户工具类
 * Created by allan on 15-6-22.
 */
public class UserHolder {
    private static SysUserService userService = SpringContextHolder.getBean(SysUserService.class);
    private static RoleService roleService = SpringContextHolder.getBean(RoleService.class);
    private static FuncServcie funcServcie = SpringContextHolder.getBean(FuncServcie.class);

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_USER_NAME_ = "ln_";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_FUNC_LIST = "funcList";

    /**
     * 根据ID获取用户
     * @param id
     * @return 取不到返回null
     */
    public static SysUser get(String id){
        SysUser user = (SysUser) EhCacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user ==  null){
            user = (SysUser)userService.get(id);
            if (user == null){
                return null;
            }
            //获取角色
            Map<String,Object> roleParams = Maps.newHashMap();
            roleParams.put("userId", id);
            user.setRoleList(roleService.findList(roleParams));
            EhCacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            EhCacheUtils.put(USER_CACHE, USER_CACHE_USER_NAME_ + user.getUserName(), user);
        }
        return user;
    }

    /**
     * 根据登录名获取用户
     * @param username
     * @return 取不到返回null
     */
    public static SysUser getByUsername(String username){
        SysUser user = (SysUser)EhCacheUtils.get(USER_CACHE, USER_CACHE_USER_NAME_ + username);
        if (user == null){
            user = userService.findUserByUserName(username);
            if (user == null){
                return null;
            }
            //获取角色
            Map<String,Object> roleParams = Maps.newHashMap();
            roleParams.put("userName", username);
            user.setRoleList(roleService.findList(roleParams));
            EhCacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            EhCacheUtils.put(USER_CACHE, USER_CACHE_USER_NAME_ + user.getUserName(), user);
        }
        return user;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_FUNC_LIST);

        UserHolder.clearCache(getUser());
    }

    /**
     * 清除指定用户缓存
     * @param user
     */
    public static void clearCache(SysUser user){
        EhCacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        EhCacheUtils.remove(USER_CACHE, USER_CACHE_USER_NAME_ + user.getUserName());
    }

    /**
     * 获取当前用户
     * @return 取不到返回 new User()
     */
    public static SysUser getUser(){
        SystemAuthorizingRealm.Principal principal = getPrincipal();
        if (principal!=null){
            SysUser user = get(principal.getUserId());
            if (user != null){
                return user;
            }
            return new SysUser();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new SysUser();
    }
    /**
     * 获取当前用户授权菜单
     * @return
     */
    public static List<Func> getMenuList(){
        @SuppressWarnings("unchecked")
        List<Func> menuList = (List<Func>)getCache(CACHE_FUNC_LIST);
        if (menuList == null){
            SysUser user = getUser();
            if (user.isAdmin()){
                menuList = funcServcie.findAllList();
            }else{

                menuList = funcServcie.findByUserId(user.getId());
            }
            putCache(CACHE_FUNC_LIST, menuList);
        }
        return menuList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static SystemAuthorizingRealm.Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){

        }
        return null;
    }

    // ============== User Cache ==============

    /**
     * 获取session里面的值
     * @param key
     * @return
     */
    public static Object getCache(String key) {
        return getCache(key, null);
    }

    /**
     *  获取seesion里的值，没有则给出默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj==null?defaultValue:obj;
    }

    /**
     *  将值放入到session
     * @param key
     * @param value
     */
    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 移除Session里的值
     * @param key
     */
    public static void removeCache(String key) {
        getSession().removeAttribute(key);
    }
}
