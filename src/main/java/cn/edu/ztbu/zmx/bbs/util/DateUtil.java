package cn.edu.ztbu.zmx.bbs.util;


import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @program bbs.DateUtil
 * @author: zhaomengxin
 * @date: 2021/3/7 17:51
 * @Description:
 */
public class DateUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static String format(Date date){
        if(Objects.isNull(date)){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(date);
    }

    public static String format(LocalDateTime date){
        if(Objects.isNull(date)){
            return "";
        }
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return sdf.format(date);
    }
    public static Date parse(String date){
        if(Strings.isNullOrEmpty(date)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
