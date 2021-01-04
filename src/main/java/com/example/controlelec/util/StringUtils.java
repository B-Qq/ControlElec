package com.example.controlelec.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : liuze
 * @create 2019/5/27 14:29
 */
public class StringUtils{

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);


    /**
     * 从 Map 中获取 指定 key 值 如果为 null  返回 ""
     * @param map
     * @param key
     * @return
     */
    public static String getString(Map map, String key){
       return map.get(key) == null ? "" : map.get(key).toString().trim();
    }

    /**
     * 在map中获取指定key的值 如果为 null  返回 null
     * @param map
     * @param key
     * @return
     */
    public static String getVal(Map<String,Object> map, String key){
        return map.get(key) == null ? null : map.get(key).toString();
    }

    /**
     * 将字符串数字判空转换 BigDecimal值
     * @param val
     * @return
     */
    public static BigDecimal getValNumbStr(String val){
        return isEmpty(val) ? null : new BigDecimal(val);
    }

    /**
     * 判断字符串是否为 null  或  空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equalsIgnoreCase("null") || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否 不为 null  或  空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    /**
     * 检查字符串数组是否为 null 或 ""  遇到 null 或 "" 停止检验
     * @param params
     * @return
     */
    public static boolean checkIsNotEmpty(String... params){

        for (int i = 0; i < params.length; i++) {
            if (isEmpty(params[i])){
                LOGGER.error("       参数索引 （ params index ) {} is null or '' !!!",i);
                return false;
            }
        }
        return true;
    }

}
