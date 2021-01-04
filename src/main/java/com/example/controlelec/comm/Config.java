package com.example.controlelec.comm;

import com.example.controlelec.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : liuze
 * @create 2019/5/20 11:33
 */
public class Config {

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    static ResourceBundle bundle_config = null;

    static ResourceBundle bundle_constant = null;

    /**
     * 获取 application.properties 文件的 key-value 值
     *
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        if (bundle_config == null) {
            bundle_config = ResourceBundle.getBundle("application");
        }
        String value = null;
        try {
            value = new String(bundle_config.getString(key).getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            new RuntimeException(e);
            LOGGER.error("不支持的编码类型  Exception ：{}", e.getMessage());
        }
        return value;
    }


    /**
     * 获取 constant.properties 文件的 key-value 值
     *
     * @param key
     * @return
     */
    public static String getConstant(String key) {
        if (bundle_constant == null) {
            String profiles_active = getConfig("spring.profiles.active");

            if (StringUtils.isNotEmpty(profiles_active)) {
                switch (profiles_active.toUpperCase(Locale.ENGLISH)) {
                    case "DEV":
                        bundle_constant = ResourceBundle.getBundle("application-dev");
                        break;
                    case "PROD":
                        bundle_constant = ResourceBundle.getBundle("application-prod");
                        break;
                    case "TEST":
                        bundle_constant = ResourceBundle.getBundle("application-test");
                        break;
                    default:
                        LOGGER.info(" [ Config getMqConfig ] switch default");
                }
            } else {
                throw new RuntimeException("%%%%%%%%%%%%     Config getMqConfig    profiles_active  is  null");
            }
        }
        String value = null;
        value = new String(bundle_constant.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        return value;
    }

}
