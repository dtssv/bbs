package cn.edu.ztbu.zmx.bbs.web;

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

    @RequestMapping(value = "add")
    public String add(){
        return "post/add";
    }
}
