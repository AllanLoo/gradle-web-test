package com.isp.security.shiro;

/**
 * 用户和密码（包含验证码）令牌类
 * Created by allan on 15-6-20.
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken{
    private static final long serialVersionUID = 1L;

    private String captcha;
    private boolean mobileLogin;

    public UsernamePasswordToken() {
        super();
    }

    public UsernamePasswordToken(String username, char[] password, boolean bRememberMe,
                                 String host, String captcha, boolean bLoginBymobile) {
        super(username, password, bRememberMe, host);
        this.captcha = captcha;
        this.mobileLogin = bLoginBymobile;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(boolean mobileLogin) {
        this.mobileLogin = mobileLogin;
    }
}
