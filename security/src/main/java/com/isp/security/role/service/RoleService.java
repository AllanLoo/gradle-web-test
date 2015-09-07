package com.isp.security.role.service;

import com.isp.common.service.CrudService;
import com.isp.security.role.dao.RoleDao;
import com.isp.security.role.entity.Role;
import com.isp.security.shiro.SecurityService;
import org.springframework.stereotype.Service;

/**
 * Created by AllanLoo on 2015/8/5.
 */
@Service
public class RoleService extends SecurityService<RoleDao,Role> {
}
