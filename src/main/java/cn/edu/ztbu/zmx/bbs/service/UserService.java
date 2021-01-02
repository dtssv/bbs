package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import cn.edu.ztbu.zmx.bbs.vo.UserRegisterVo;

/**
 * @author zhaomengxin
 * @version 1.0
 * @program bbs.UserService
 * @date 2020/10/17 15:28
 * @Description
 * @since 1.0
 */
public interface UserService {
    /**
     *
     * @param userRegisterVo
     * @return
     */
    ResultVo register(UserRegisterVo userRegisterVo);
}
