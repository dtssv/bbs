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

    public static final int YES = 1;

    public static final int NO = 0;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public enum YnEnum{
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
