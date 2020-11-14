package cn.edu.ztbu.zmx.bbs.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @program bbs.OpeartorLog
 * @author: zhaomengxin
 * @date: 2020/11/14 20:51
 * @Description:
 */
@Data
@Entity
@Table(name = "operator_log")
public class OperatorLog {

    @Id
    private Long id;

    @Column(name = "operator_key")
    private String operatorKey;

    @Column(name = "operator_type")
    private Integer operatorType;

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
