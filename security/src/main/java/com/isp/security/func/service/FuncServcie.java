package com.isp.security.func.service;

import com.isp.common.service.CrudService;
import com.isp.security.func.dao.FuncDao;
import com.isp.security.func.entity.Func;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by AllanLoo on 2015/8/5.
 */
@Service
public class FuncServcie extends CrudService<FuncDao,Func>{
    /**
     * 根据用户id获取功能菜单
     * @param userId 用户id
     * @return 用户的菜单列表
     */
    public List<Func> findByUserId(String userId) {
        return null;
    }
}
