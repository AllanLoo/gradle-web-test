package com.isp.web.login;

import com.isp.common.utils.IdGen;
import com.isp.common.utils.StringUtils;
import com.isp.common.web.BaseController;
import com.isp.common.web.servlet.ValidateCodeServlet;
import com.isp.security.shiro.FormAuthenticationFilter;
import com.isp.security.shiro.SystemAuthorizingRealm.Principal;
import com.isp.security.shiro.SystemService;
import com.isp.security.shiro.UserHolder;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录
 * Created by AllanLoo on 2015/8/4.
 */
@Controller
public class LoginController extends BaseController{

    /**
     * 去登录页面
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String forwardLogin() {
        Principal principal = UserHolder.getPrincipal();
        if(principal != null) {
            return "redirect:/";
        }

        return "login";
    }

    /**
     * 登录失败，真正登录的POST请求由shiro的Filter完成
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取用户名
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        //获取是否记得我（即使系统没有这个功能，放着没关系）
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        //获取登录失败时抛出的异常名称
        String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        //登录失败的提示信息
        String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
            message = "用户或密码错误, 请重试";
        }

        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
       // model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);


        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception)){
            model.addAttribute("needValidateCode", SystemService.needValidateCode(username, true));
        }

        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

        return "login";
    }
    /**
     * 去系统主界面
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String forwardMain() {
        Principal principal = UserHolder.getPrincipal();

        // 登录成功后，验证码计算器清零
        SystemService.cleanFailedLoginCount(principal.getUsername());
        return "main";
    }

}
