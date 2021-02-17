package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program bbs.CategoryVo
 * @author: zhaomengxin
 * @date: 2021/1/10 20:16
 * @Description:
 */
@Data
public class CategoryVo {
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String categoryName;
    /**
     *
     */
    private Integer orderNum;
    /**
     *
     */
    private Integer postNum;
    /**
     *
     */
    private Integer moderatorNum;
    /**
     *
     */
    private LocalDateTime createTime;
}
