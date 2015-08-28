package com.isp.web.login;

import com.isp.common.web.BaseController;
import com.isp.security.shiro.SystemAuthorizingRealm.Principal;
import com.isp.security.shiro.SystemService;
import com.isp.security.shiro.UserHolder;
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
        System.out.println("失败了");
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
