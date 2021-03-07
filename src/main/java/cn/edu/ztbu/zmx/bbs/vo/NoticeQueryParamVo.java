package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program bbs.NoticeQueryParamVo
 * @author: zhaomengxin
 * @date: 2021/1/24 20:38
 * @Description:
 */
@Data
public class NoticeQueryParamVo extends BaseParamVo {

    private LocalDateTime now;

    private Long id;
}
