package com.isp.security.func.dao;

import com.isp.common.persistence.CrudDao;
import com.isp.common.persistence.annotation.MyBatisDao;
import com.isp.security.func.entity.Func;

/**
 * Created by AllanLoo on 2015/8/5.
 */
@MyBatisDao
public interface FuncDao extends CrudDao<Func> {
}
