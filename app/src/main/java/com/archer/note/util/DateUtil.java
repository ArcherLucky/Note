package com.archer.note.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * Created by 84113 on 2016/4/3.
 */
public class DateUtil {

    /**
     * Date转成字符串
     * @param date 要转的Date
     * @return 返回转换后的字符串
     */
    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }
}
