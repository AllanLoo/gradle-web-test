package com.isp.security.user.service;

import com.isp.common.service.CrudService;
import com.isp.common.web.bean.User;
import com.isp.security.user.dao.SysUserDao;
import com.isp.security.user.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * 系统用户管理服务
 * Created by AllanLoo on 2015/8/5.
 */
@Service
public class SysUserService extends CrudService<SysUserDao,User> {
    /**
     * 通过用户名获取用户信息
     * @param username 用户名
     * @return
     */
    public SysUser findUserByUserName(String username) {
        return null;
    }


}
