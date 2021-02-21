package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

/**
 * @program bbs.PostQueryParamVo
 * @author: zhaomengxin
 * @date: 2021/1/10 20:59
 * @Description:
 */
@Data
public class PostQueryParamVo extends BaseParamVo {

    private Long categoryId;

    private Long userId;

    private String categoryName;

    private String nickName;

    private String title;
}
