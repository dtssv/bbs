package cn.edu.ztbu.zmx.bbs.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @program bbs.Notice
 * @author: zhaomengxin
 * @date: 2020/11/14 20:20
 * @Description:
 */
@Data
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "notice_body")
    private String noticeBody;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

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
