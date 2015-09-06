package com.isp.security.user.entity;

import com.google.common.collect.Lists;
import com.isp.common.persistence.DataEntity;
import com.isp.common.web.bean.*;
import com.isp.security.role.entity.Role;

import java.util.Date;
import java.util.List;

/**
 * Created by AllanLoo on 2015/7/31.
 */
public class SysUser extends DataEntity<SysUser> {
    /**
     * 帐号正常
     */
    public static final String ACCOUNT_STATUS_NORMAL = "0";
    /**
     * 帐号已被登录
     */
    public static final String ACCOUNT_STATUS_LOINDED = "1";
    /**
     * 帐号被限制
     */
    public static final String ACCOUNT_STATUS_LIMITED = "2";

    /**
     * 用户名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 用户密码
     */
    private String userPwd;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户状态（如：1在职，0离职）
     */
    private String userStatus;
    /**
     * 帐号状态（如：1已登录，2限制登录，0默认等）
     */
    private String accountStatus;
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
        this.id = id;
    }
    public SysUser(String id,String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
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
