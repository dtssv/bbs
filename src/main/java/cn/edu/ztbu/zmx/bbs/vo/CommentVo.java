package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program bbs.CommentVp
 * @author: zhaomengxin
 * @date: 2021/1/17 15:20
 * @Description:
 */
@Data
public class CommentVo {

    private Long id;

    private Long userId;

    private String nickName;

    private Integer floorNum;

    private Long replyId;

    private String commentBody;

    private Long postId;

    private String creator;

    private LocalDateTime modifyTime;

    private String headUrl;

    private Integer sex;

    private Boolean canEdit;

    private Boolean author;

    private String postTitle;
}
