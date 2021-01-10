package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

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

    private String category;

    private String nickName;

    private Integer commentNum;

    private Integer readNum;

    private Boolean cream;
}
