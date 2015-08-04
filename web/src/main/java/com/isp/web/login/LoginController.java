package com.isp.web.login;

import com.isp.common.web.BaseController;
import com.isp.security.shiro.SystemAuthorizingRealm;
import com.isp.security.shiro.UserHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * �û���¼
 * Created by AllanLoo on 2015/8/4.
 */
@Controller
public class LoginController extends BaseController{

    /**
     * ȥ��¼ҳ��
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String forwardLogin() {
        SystemAuthorizingRealm.Principal principal = UserHolder.getPrincipal();
        if(principal != null) {
            return "redirect:/";
        }
        System.out.println("----ת���¼");
        return "login";
    }

    /**
     * ȥϵͳ������
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String forwardMain() {

        return "main";
    }

}
