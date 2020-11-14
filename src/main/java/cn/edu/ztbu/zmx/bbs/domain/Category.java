package cn.edu.ztbu.zmx.bbs.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @program bbs.Category
 * @author: zhaomengxin
 * @date: 2020/11/11 10:07
 * @Description:
 */
@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "orderNum")
    private Integer orderNum;

    @Column(name = "post_num")
    private Integer postNum;

    @Column(name = "last_post_time")
    private LocalDateTime lastPostTime;

    @Column(name = "moderator_num")
    private String moderatorNum;

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
