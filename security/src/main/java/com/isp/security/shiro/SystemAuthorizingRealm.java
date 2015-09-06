package com.isp.security.shiro;

import com.isp.common.config.Global;
import com.isp.common.utils.Encodes;
import com.isp.common.utils.StringUtils;
import com.isp.common.web.servlet.ValidateCodeServlet;
import com.isp.security.func.entity.Func;
import com.isp.security.role.entity.Role;
import com.isp.security.user.entity.SysUser;
import com.isp.security.user.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统安全实现类
 * Created by allan on 15-6-20.
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SystemService systemService;
    @Resource
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证回调函数
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        int activeSessionSize = systemService.getActiveSessions().size();
        if (logger.isDebugEnabled()){
            logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
        }
        //校验登录验证码
        if(SystemService.needValidateCode(token.getUsername(),false)) {
            Session session = UserHolder.getSession();
            String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
            if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
                throw new AuthenticationException("msg:验证码错误, 请重试.");
            }
        }

        SysUser user = sysUserService.findUserByUserName(token.getUsername());
        if(user != null) {
            if(SysUser.ACCOUNT_STATUS_LIMITED.equals(user.getAccountStatus())) {
                throw new AuthenticationException("msg:该帐号已被限制登录.");
            }
            byte[] salt = Encodes.decodeHex(user.getUserPwd().substring(0, 16));
            return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()),
                    user.getUserPwd().substring(16), ByteSource.Util.bytes(salt), getName());
        }
        return null;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);

        //如果系统设置不许一个帐号同时登录，那么将原来的帐号踢出去
        if (!"true".equals(Global.getConfig("user.multiAccountLogin"))){
            Collection<Session> sessions = systemService.getSessionDAO()
                    .getActiveSessions(true, principal, UserHolder.getSession());
            if (sessions.size() > 0){
                // 如果是登录进来的，则踢出已在线用户
                if (UserHolder.getSubject().isAuthenticated()){
                    for (Session session : sessions){
                        systemService.getSessionDAO().delete(session);
                    }
                }else{// 通过“记住我”进来的，并且当前用户已登录，则退出当前用户提示信息。
                    UserHolder.getSubject().logout();
                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
                }
            }
        }
        SysUser user = sysUserService.findUserByUserName(principal.getUserName());
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //TODO:
            List<Func> menus = null;
            for (Func func : menus){
                if (StringUtils.isNotBlank(func.getPermission())){
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(func.getPermission(),",")){
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加用户权限
            info.addStringPermission("system");
            // 添加用户角色信息
            for (Role role : user.getRoleList()){
                info.addRole(role.getEnname());
            }

            return info;
        } else {
            return null;
        }
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     * @param permission
     */
    private void authorizationValidate(Permission permission){
        // 模块授权预留接口
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
        matcher.setHashIterations(SystemService.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }
    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String userId; // 编号
        private String userName; // 登录名
        private String realName; // 姓名
        private boolean mobileLogin; // 是否手机登录


        public Principal(SysUser user, boolean bMobileLogin) {
            this.userId = user.getId();
            this.userName = user.getUserName();
            this.realName = user.getRealName();
            this.mobileLogin = bMobileLogin;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getRealName() {
            return realName;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }

        /**
         * 获取SESSIONID
         */
        public String getSessionId() {
            try{
                return (String) UserHolder.getSession().getId();
            }catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return userId;
        }

    }
}
