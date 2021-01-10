package cn.edu.ztbu.zmx.bbs.common;

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
}
