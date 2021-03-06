package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

/**
 * @program bbs.CommentQueryParamVo
 * @author: zhaomengxin
 * @date: 2021/1/10 20:59
 * @Description:
 */
@Data
public class CommentQueryParamVo extends BaseParamVo {

    private Long postId;

    private Long userId;

    private String nickName;
}
