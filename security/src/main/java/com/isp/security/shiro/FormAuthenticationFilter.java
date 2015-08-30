package com.isp.security.shiro;

import com.isp.common.utils.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 表单验证过滤类
 * Created by allan on 15-6-20.
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter{
    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_MESSAGE_PARAM = "message";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private String messageParam = DEFAULT_MESSAGE_PARAM;


    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if(password == null) {
            password = "";
        }
        //是否勾选记住我
        boolean bRememberMe = isRememberMe(request);
        //客户主机访问地址
        String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
        //验证码
        String captcha = getCaptcha(request);
        //是否是通过移动终端登录
        boolean bLoginBymobile = isMobileLogin(request);
        return new UsernamePasswordToken(username, password.toCharArray(), bRememberMe, host, captcha, bLoginBymobile);
    }

    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae.getClass().getName());
        if (ae.getMessage() != null && StringUtils.startsWith(ae.getMessage(), "msg:")){
            String message = StringUtils.replace(ae.getMessage(), "msg:", "");
            request.setAttribute(getMessageParam(), message);
        }
    }


    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }


    /**
     * 登录失败处理
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, org.apache.shiro.authc.AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className)
                || UnknownAccountException.class.getName().equals(className)){
            message = "用户或密码错误, 请重试";
        }
        else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        }
        else{
            message = "系统出现点问题，请稍后再试！";
            e.printStackTrace(); // 输出到控制台
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
    }

    /**
     * 获取验证码
     * @param request
     * @return
     */
    protected String getCaptcha(ServletRequest request) {
       return WebUtils.getCleanParam(request,getCaptchaParam());
    }

    /**
     * 是否是手机客户端访问
     * @param request
     * @return
     */
    protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    public String getMobileLoginParam() {
        return mobileLoginParam;
    }

    public void setMobileLoginParam(String mobileLoginParam) {
        this.mobileLoginParam = mobileLoginParam;
    }

    public String getMessageParam() {
        return messageParam;
    }

    public void setMessageParam(String messageParam) {
        this.messageParam = messageParam;
    }
}
