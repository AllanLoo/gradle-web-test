package com.isp.security.role.dao;

import com.isp.common.persistence.CrudDao;
import com.isp.common.persistence.annotation.MyBatisDao;
import com.isp.security.role.entity.Role;

/**
 * Created by AllanLoo on 2015/8/5.
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {
}
