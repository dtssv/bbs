package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import cn.edu.ztbu.zmx.bbs.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
public class IndexController {
    /**
     * 首页
     */
    @RequestMapping(value = {"index","/"})
    public String index(){
        return "/index";
    }
    /**
     * 分类列表页
     */
    @RequestMapping(value = "post/index")
    public String postIndex(){
        return "/post/index";
    }
    /**
     * 文章详情页
     */
    @RequestMapping(value = "post/detail")
    public String postDetail(){
        return "/post/detail";
    }
    /**
     * 登录
     */
    @RequestMapping(value = "login")
    public String login(){
        User user = LoginContext.getLoginUser();
        if(Objects.nonNull(user)){
            return "/index";
        }
        return "/login";
    }
    /**
     * 注册
     */
    @RequestMapping(value = "reg")
    public String reg(){
        return "/reg";
    }
    /**
     * 忘记密码
     */
    @RequestMapping(value = "forget")
    public String forget(){
        return "forget";
    }

    /**
     * 用户主页
     */
    @RequestMapping(value = "user/home")
    public String home(){
        return "/user/home";
    }
    @ResponseBody
    @RequestMapping(value = "loginInfo")
    public ResultVo loginInfo(){
        User user = LoginContext.getLoginUser();
        if(user != null){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            return ResultVo.success(userVo);
        } else{
            return ResultVo.fail("未登录");
        }
    }
}
