package com.isp.security.user.entity;

import com.isp.common.web.bean.*;

import java.util.Date;

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
}
