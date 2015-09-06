package com.isp.security.user.service;


import com.isp.security.shiro.SecurityService;
import com.isp.security.shiro.UserHolder;
import com.isp.security.user.dao.SysUserDao;
import com.isp.security.user.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * 系统用户管理服务
 * Created by AllanLoo on 2015/8/5.
 */
@Service
public class SysUserService extends SecurityService<SysUserDao,SysUser> {
    /**
     * 通过用户名获取用户信息
     * @param username 用户名
     * @return
     */
    public SysUser findUserByUserName(String username) {
        return dao.findUserByUserName(username);
    }

    /**
     * 更改账户状态
     * @param userId
     * @param accountStatus
     */
    public void updateAccountStatus(String userId,String accountStatus){
        SysUser user = new SysUser(userId);
        user.setAccountStatus(accountStatus);
        user.setUpdateById(UserHolder.getUser().getId());
    }
}
