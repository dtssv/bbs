package cn.edu.ztbu.zmx.bbs.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @program bbs.NoticeVo
 * @author: zhaomengxin
 * @date: 2021/1/24 20:37
 * @Description:
 */
@Data
public class NoticeVo {

    private Long id;

    private String noticeBody;

    private LocalDateTime modifyTime;

    private String linkUrl;

    private String startTime;
    private String endTime;
    private LocalDateTime createTime;
    private String creator;

}
