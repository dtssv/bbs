package cn.edu.ztbu.zmx.bbs.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping(value = "index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "admin/index")
    public String adminIndex(){
        return "adminIndex";
    }
}
