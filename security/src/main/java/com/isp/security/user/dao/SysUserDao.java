package com.isp.security.user.dao;

import com.isp.common.persistence.CrudDao;
import com.isp.common.persistence.annotation.MyBatisDao;
import com.isp.security.user.entity.SysUser;

/**
 * Created by allan on 15-6-22.
 */
@MyBatisDao
public interface SysUserDao extends CrudDao<SysUser> {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public SysUser findUserByUserName(String username);
}
