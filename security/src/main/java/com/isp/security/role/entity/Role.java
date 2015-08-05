package com.isp.security.role.entity;

import com.google.common.collect.Lists;
import com.isp.common.persistence.DataEntity;
import com.isp.security.func.entity.Func;

import java.util.List;

/**
 * 角色实体
 * Created by AllanLoo on 2015/8/5.
 */
public class Role extends DataEntity<Role> {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 英文名称
     */
    private String enname;
    /**
     * 该角色拥有的功能
     */
    private List<Func> funcList = Lists.newArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public List<Func> getFuncList() {
        return funcList;
    }

    public void setFuncList(List<Func> funcList) {
        this.funcList = funcList;
    }
}
