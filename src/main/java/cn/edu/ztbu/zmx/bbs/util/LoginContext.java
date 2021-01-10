package cn.edu.ztbu.zmx.bbs.util;

import cn.edu.ztbu.zmx.bbs.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program bbs.LoginContext
 * @author: zhaomengxin
 * @date: 2021/1/10 17:51
 * @Description:
 */
public class LoginContext {

    public static User getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object user = authentication.getPrincipal();
        if(user instanceof User){
            return (User) user;
        }
        return null;
    }
}
