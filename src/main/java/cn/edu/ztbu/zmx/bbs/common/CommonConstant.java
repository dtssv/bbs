package cn.edu.ztbu.zmx.bbs.common;

import java.util.Objects;

/**
 * @author zhaomengxin
 * @version 1.0
 * @program bbs.CommonConstant
 * @date 2020/10/17 15:30
 * @Description
 * @since 1.0
 */
public interface CommonConstant {
    /**
     * 是
     */
    int YES = 1;
    /**
     *否
     */
    int NO = 0;
    /**
     * 管理员角色
     */
    String ROLE_ADMIN = "ROLE_ADMIN";
    /**
     * 时间格式化
     */
    String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式化
     */
    String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式化
     */
    String TIME_PATTERN = "HH:mm:ss";
    /**
     * 用户状态正常
     */
    Integer USER_STATUS_OK = 1;
    /**
     * 用户状态禁用
     */
    Integer USER_STATUS_DISABLE = -1;
    /**
     * 用户状态禁止登录
     */
    Integer USER_STATUS_CANNOT_LOGIN = 0;
    /**
     * 0
     */
    Integer ZERO = 0;
    /**
     * 默认页码
     */
    Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 1
     */
    Integer ONE = 1;
    /**
     * 默认性别
     */
    Integer SEX_DEFAULT = 0;
    /**
     * 男
     */
    Integer SEX_MAN = 1;
    /**
     * 女
     */
    Integer SEX_WOMAN = 2;
    /**
     * 默认头像
     */
    String DEFAULT_HEAD_URL = "../../res/images/head/head_default.png";
    /**
     * 男性头像
     */
    String MAN_HEAD_URL = "../../res/images/head/head_boy.png";
    /**
     * 女性头像
     */
    String WOMAN_HEAD_URL = "../../res/images/head/head_girl.png";
    /**
     * 编辑文章
     */
    Integer EDIT_TYPE = 1;
    /**
     * 时间格式化
     */
    enum YnEnum{
        Y(Boolean.TRUE),
        N(Boolean.FALSE);
        Boolean flag;

        YnEnum(Boolean flag){
            this.flag = flag;
        }

        public Boolean getFlag() {
            return flag;
        }
    }


    /**
     * @program bbs.SexEnum
     * @author: zhaomengxin
     * @date: 2021/1/17 13:30
     * @Description:
     */
    enum SexEnum {
        DEFAULT(CommonConstant.SEX_DEFAULT,"未知",CommonConstant.DEFAULT_HEAD_URL),
        MAN(CommonConstant.SEX_MAN,"男性",CommonConstant.MAN_HEAD_URL),
        WOMAN(CommonConstant.SEX_WOMAN,"女性",CommonConstant.WOMAN_HEAD_URL);

        private Integer code;

        private String desc;

        private String headPhoto;

        SexEnum(Integer code,String desc,String headPhoto){
            this.code = code;
            this.desc = desc;
            this.headPhoto = headPhoto;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public String getHeadPhoto() {
            return headPhoto;
        }

        public static SexEnum fromCode(Integer code){
            if(Objects.isNull(code)){
                return DEFAULT;
            }
            for (SexEnum sexEnum : SexEnum.values()) {
                if(sexEnum.code.equals(code)){
                    return sexEnum;
                }
            }
            return DEFAULT;
        }
    }
}
