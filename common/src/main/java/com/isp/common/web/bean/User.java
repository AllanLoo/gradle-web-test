package com.isp.common.web.bean;

import com.isp.common.persistence.DataEntity;

/**
 * Created by allan on 15-7-31.
 */
public class User extends DataEntity<User>{
    /**
     * �û���
     */
    private String userName;
    /**
     * ��ʵ����
     */
    private String realName;
    /**
     * �û�����
     */
    private String userPwd;
    /**
     * �û�����
     */
    private String userEmail;
    /**
     * �û��ֻ���
     */
    private String phone;
    /**
     * �û�״̬
     */
    private String userStatus;

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
}
