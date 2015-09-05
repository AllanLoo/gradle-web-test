package com.isp.security.shiro;

import com.isp.common.persistence.CrudDao;
import com.isp.common.persistence.DataEntity;
import com.isp.common.service.CrudService;
import com.isp.common.utils.IdGen;

import java.util.Date;

/**
 * Created by AllanLoo on 2015/9/5.
 * 对常用的CrudService 添加一些数据权限控制的方法
 */
public class SecurityService <D extends CrudDao<T>, T extends DataEntity<T>>
       extends CrudService<D, T> {

    @Override
    public void preInsert(T entity) {
        // 如果需要自定义产生主键
        if (entity.getIsGeneratePrimaryKey()){
            entity.setId(IdGen.uuid());//暂时用uuid代替
        }

        entity.setUpdateDate(new Date());
        entity.setCreateDate(entity.getUpdateDate());
        entity.setUpdateById(UserHolder.getUser().getId());
        entity.setCreateById(UserHolder.getUser().getId());
    }

    @Override
    public void preUpdate(T entity) {
        entity.setUpdateDate(new Date());
        entity.setUpdateById(UserHolder.getUser().getId());
    }

    @Override
    public void preDelete(T entity) {
        entity.setUpdateDate(new Date());
        entity.setUpdateById(UserHolder.getUser().getId());
    }

    /**
     * 数据权限过滤
     * @return
     */
    public  String dataScopeFilter(){
        //TODO:
        return null;
    }
}
