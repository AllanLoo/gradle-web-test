package com.isp.security.user.entity;

import com.google.common.collect.Lists;
import com.isp.common.web.bean.*;
import com.isp.security.role.entity.Role;

import java.util.Date;
import java.util.List;

/**
 * Created by AllanLoo on 2015/7/31.
 */
public class SysUser extends com.isp.common.web.bean.User{
    /**
     *  最后登陆IP
     */
    private String loginIp;
    /**
     *  最后登陆日期
     */
    private Date loginDate;

    private List<Role> roleList = Lists.newArrayList();

    public SysUser(){}

    public SysUser(String id) {
        super(id);
    }
    public SysUser(String id,String userName) {
       super(id,userName);
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public boolean isAdmin(){
        return isAdmin(this.id);
    }

    public static boolean isAdmin(String id){
        return id != null && "1".equals(id);
    }
}
