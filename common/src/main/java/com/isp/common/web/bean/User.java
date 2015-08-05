package com.isp.common.web.bean;

import com.isp.common.persistence.DataEntity;

/**
 * Created by allan on 15-7-31.
 */
public class User extends DataEntity<User>{
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
     * 用户状态（如：在职，离职）
     */
    private String userStatus;
    /**
     * 帐号状态（如：已登录，限制登录，未激活等）
     */
    private String accountStatus;

    public User(){}

    public User(String id){
        this.id = id;
    }

    public User(String id,String userName){
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
}
