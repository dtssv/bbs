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

    int YES = 1;

    int NO = 0;

    String ROLE_ADMIN = "ROLE_ADMIN";

    String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    String DATE_PATTERN = "yyyy-MM-dd";

    String TIME_PATTERN = "HH:mm:ss";

    Integer USER_STATUS_OK = 1;

    Integer USER_STATUS_DISABLE = -1;

    Integer USER_STATUS_CANNOT_LOGIN = 0;

    Integer ZERO = 0;

    Integer DEFAULT_PAGE_SIZE = 10;

    Integer ONE = 1;

    Integer SEX_DEFAULT = 0;

    Integer SEX_MAN = 1;

    Integer SEX_WOMAN = 2;

    String DEFAULT_HEAD_URL = "../../res/images/head/head_default.png";

    String MAN_HEAD_URL = "../../res/images/head/head_boy.png";

    String WOMAN_HEAD_URL = "../../res/images/head/head_girl.png";

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

        private String headUrl;

        SexEnum(Integer code,String desc,String headUrl){
            this.code = code;
            this.desc = desc;
            this.headUrl = headUrl;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public String getHeadUrl() {
            return headUrl;
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
