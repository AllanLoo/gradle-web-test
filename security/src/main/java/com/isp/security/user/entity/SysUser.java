package com.isp.security.user.entity;

import com.isp.common.web.bean.*;

import java.util.Date;

/**
 * Created by AllanLoo on 2015/7/31.
 */
public class SysUser extends com.isp.common.web.bean.User{
    /**
     *  ����½IP
      */
    private String loginIp;
    /**
     *  ����½����
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
