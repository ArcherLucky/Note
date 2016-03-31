package de.greenrobot.daoexample.util;

/**
 * Created by 84113 on 2016/3/31.
 */
public class DatabaseUtil {

    public static String getWhereStr(String[] args) {
        String result = "";
        for(String str : args) {
            result += str + " =?" + " and ";
        }
        String string = result.substring(0, result.length() - 4);
        return string;
    }

}
