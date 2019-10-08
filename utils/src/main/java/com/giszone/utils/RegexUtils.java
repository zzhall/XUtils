package com.giszone.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzh on 2017/9/9.
 */

public class RegexUtils {

    /**
     * 正则表达式：验证数字
     */
    public static final String REGEX_NUMBER = "[0-9]*";

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^\\w{6,20}$";


    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE_PHONE = "^1[3-9]\\d{9}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_TELEPHONE = "^[04]\\d{2,3}\\d{7,8}$";


    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?$";


    /**
     * 正则表达式：昵称6-20位
     */
    public static final String REGEX_NICKNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}$";

    /**
     * 正则表达式：昵称单个字符
     */
    public static final String REGEX_NICKNAME_CHAR = "^[\\w\\u4e00-\\u9fa5]$";

    public static boolean customMatch(String str, String reg) {
        Pattern pet = Pattern.compile(reg);
        Matcher match = pet.matcher(str);
        return match.find();
    }

    public static boolean isMobilePhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        return phone.matches(REGEX_MOBILE_PHONE);
    }

    public static boolean isTelephoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        return phone.matches(REGEX_TELEPHONE);
    }

    public static boolean isPhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        return phone.matches(REGEX_TELEPHONE) || phone.matches(REGEX_MOBILE_PHONE);
    }

    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches(REGEX_NUMBER);
    }

    public static void main(String[] args) {
        System.out.println(isTelephoneNumber("4008009855"));
        System.out.println(isTelephoneNumber("02788600508"));
        System.out.println(isTelephoneNumber("07284974041"));
        System.out.println(isTelephoneNumber("88600508"));
        System.out.println(isTelephoneNumber("4974041"));
        System.out.println(isPhoneNumber("4974041"));
        System.out.println(isPhoneNumber("02788600508"));
        System.out.println(isPhoneNumber("15972101843"));
    }

}