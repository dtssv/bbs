package cn.edu.ztbu.zmx.bbs.vo;

import lombok.Data;

/**
 * @program bbs.ResultVo
 * @author: zhaomengxin
 * @date: 2021/1/2 19:23
 * @Description:
 */
@Data
public class ResultVo<T> {
    /**
     *
     */
    private int code;
    /**
     *
     */
    private String msg;
    /**
     *
     */
    private T data;
    /**
     *
     */
    public static final int SUCCESS = 1;
    /**
     *
     */
    public static final int FAIL = 0;
    /**
     *
     */
    public static <T> ResultVo<T> success(T data){
        ResultVo<T> result = new ResultVo<>();
        result.setCode(SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultVo<T> fail(String msg){
        ResultVo<T> result = new ResultVo<>();
        result.setCode(FAIL);
        result.setMsg(msg);
        return result;
    }

    public boolean isSuccess(){
        return this.code == SUCCESS;
    }
}
