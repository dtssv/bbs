package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program bbs.UserVo
 * @author: zhaomengxin
 * @date: 2021/1/10 16:30
 * @Description:
 */
@Data
public class UserVo {

    private Long id;

    private String userName;

    private String nickName;

    private Integer postNum;

    private Integer commentNum;

    private LocalDateTime createTime;

    private Integer admin;

    private String headPhoto;

    private Integer sex;


    private String city;

    private String sign;
}
