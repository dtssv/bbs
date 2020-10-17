package cn.edu.ztbu.zmx.bbs.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhaomengxin
 * @version 1.0
 * @program bbs.User
 * @date 2020/10/17 15:29
 * @Description
 * @since 1.0
 */
@Data
public class User {

    /**
     * id
     */
    private Long id;

    /**
     * userName
     */
    private String userName;

    /**
     * password
     */
    private String password;

    /**
     * phone
     */
    private String phone;

    /**
     * email
     */
    private String email;

    /**
     * userNameCh
     */
    private String userNameCh;

    /**
     * registerTime
     */
    private LocalDateTime registerTime;

    /**
     * modifyTime
     */
    private LocalDateTime modifyTime;
}
