package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import cn.edu.ztbu.zmx.bbs.vo.UserRegisterVo;
import cn.edu.ztbu.zmx.bbs.vo.UserVo;

import java.util.List;

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

    /**
     *
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     *
     * @param ids
     * @return
     */
    List<User> selectByIds(List<Long> ids);

    /**
     *
     * @param userVo
     * @return
     */
    User saveBasic(UserVo userVo);

    /**
     *
     * @param nowPass
     * @param newPass
     * @param id
     * @param userName
     * @return
     */
    ResultVo<User> changePassword(String nowPass,String newPass,Long id,String userName);

    /**
     *
     * @param headImage
     * @param id
     * @param userName
     * @return
     */
    User changeHeadImage(String headImage,Long id,String userName);

    /**
     *
     * @return
     */
    Long todayData();

    /**
     *
     * @param user
     * @return
     */
    User save(User user);
}
