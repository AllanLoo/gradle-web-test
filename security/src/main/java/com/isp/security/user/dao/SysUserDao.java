package com.isp.security.user.dao;

import com.isp.common.persistence.CrudDao;
import com.isp.common.persistence.annotation.MyBatisDao;
import com.isp.common.web.bean.User;
import com.isp.security.user.entity.SysUser;

/**
 * Created by allan on 15-6-22.
 */
@MyBatisDao
public interface SysUserDao extends CrudDao<User> {
}
