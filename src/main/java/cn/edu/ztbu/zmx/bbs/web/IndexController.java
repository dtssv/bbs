package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping(value = {"index","/"})
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "admin/index")
    public String adminIndex(){
        return "adminIndex";
    }

    @RequestMapping(value = "login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "reg")
    public String reg(){
        return "reg";
    }

    @RequestMapping(value = "forget")
    public String forget(){
        return "forget";
    }

    @ResponseBody
    @RequestMapping(value = "loginInfo")
    public ResultVo loginInfo(HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext != null){
            return ResultVo.success(((User)securityContext.getAuthentication().getPrincipal()).getUsername());
        }else{
            return ResultVo.fail("未登录");
        }
    }
}
