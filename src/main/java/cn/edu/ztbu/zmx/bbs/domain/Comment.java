package cn.edu.ztbu.zmx.bbs.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @program bbs.Comment
 * @author: zhaomengxin
 * @date: 2020/11/11 22:32
 * @Description:
 */
@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "floor_num")
    private Integer floorNum;

    @Column(name = "comment_user_id")
    private Long commentUserId;

    @Column(name = "comment_body")
    private String commentBody;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "comment_title")
    private String commentTitle;

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
