package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

/**
 * @program bbs.UserRegisterVo
 * @author: zhaomengxin
 * @date: 2021/1/2 19:29
 * @Description:
 */
@Data
public class UserRegisterVo {
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String nickName;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String rePassword;
}
