package com.isp.web.system;

import com.isp.common.web.BaseController;
import com.isp.security.user.entity.SysUser;
import com.isp.security.user.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统用户管理控制
 * Created by AllanLoo on 2015/9/5.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{
    @Resource
    protected SysUserService sysUserService;
    /**
     * 去管理页面
     * @return
     */
    @RequestMapping(value = "/mgr",method = RequestMethod.GET)
    public String forwardMgr(){
        return "user/user-mgr";
    }

    @RequestMapping(value = "/listByPage",method = RequestMethod.GET)
    @ResponseBody
    public List<SysUser> getUserListByPage(HttpServletRequest request){
        return null;
    }
}
