package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program bbs.MyController
 * @author: zhaomengxin
 * @date: 2021/1/10 12:19
 * @Description:
 */
@Controller
@RequestMapping(value = "my")
public class MyController {
    /**
     * 新增/编辑文章
     */
    @RequestMapping(value = "add")
    public String add(){
        return "/post/add";
    }
    /**
     * 主页
     */
    @RequestMapping(value = "home")
    public String home(){
        User user = LoginContext.getLoginUser();
        return "redirect:/user/home?userId=" + user.getId();
    }
    /**
     * 我的设置
     */
    @RequestMapping(value = "set")
    public String set(){
        return "/user/set";
    }
}
