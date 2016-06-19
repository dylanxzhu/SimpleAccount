package com.yuewawa.simpleaccount.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuewawa on 2016-06-15.
 */
public class StringUtil {

    /**
     * 验证字符串格式
     * @param str 要验证的字符串
     * @param reg 格式
     * @return
     * */
    public static boolean validateString(String str, String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        boolean flag = matcher.matches();
        return flag;
    }
}
