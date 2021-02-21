package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program bbs.PostVo
 * @author: zhaomengxin
 * @date: 2021/1/10 17:41
 * @Description:
 */
@Data
public class PostVo {

    private Long id;

    private String title;

    private String postBody;

    private Long categoryId;

    private String categoryName;

    private String nickName;

    private Integer commentNum;

    private Integer readNum;

    private Boolean cream;

    private Boolean canEdit;

    private String headPhoto;

    private Integer sex;

    private Long userId;

    private LocalDateTime modifyTime;

    private LocalDateTime createTime;
}
