package cn.edu.ztbu.zmx.bbs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
