package cn.edu.ztbu.zmx.bbs.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @program bbs.Moderator
 * @author: zhaomengxin
 * @date: 2020/11/14 20:41
 * @Description:
 */
@Data
@Entity
@Table(name = "moderator")
public class Moderator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "creator")
    private String creator;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    @Column(name = "yn")
    private Boolean yn = Boolean.FALSE;
}
