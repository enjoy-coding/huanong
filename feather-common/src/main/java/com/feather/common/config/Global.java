package com.feather.common.config;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feather.common.utils.StringUtils;
import com.feather.common.utils.YamlUtil;

/**
 * 全局配置类
 * 
 * @author feather
 */
public class Global {
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    /**
     * 当前对象实例
     */
    private static Global global;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private Global() {
    }

    /**
     * 静态工厂方法
     */
    public static synchronized Global getInstance() {
        if (global == null) {
            global = new Global();
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            try {
                Map<?, ?> rmap = YamlUtil.loadYaml("application.yml");
                Object active = YamlUtil.getProperty(rmap, "spring.profiles.active");
                Map<?, ?> yamlMap = YamlUtil.loadYaml("application-" + active + ".yml");
                Object obj = YamlUtil.getProperty(yamlMap, key);
                value = obj != null ? obj.toString() : "";
                map.put(key, value);
            } catch (FileNotFoundException e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    public static Object getConfigObject(String key) {
        try {
            Map<?, ?> rmap = YamlUtil.loadYaml("application.yml");
            Object active = YamlUtil.getProperty(rmap, "spring.profiles.active");
            Map<?, ?> yamlMap = YamlUtil.loadYaml("application-" + active + ".yml");
            Object obj = YamlUtil.getProperty(yamlMap, key);
            return obj;
        } catch (FileNotFoundException e) {
            log.error("获取全局配置异常 {}", key);
        }
        return null;
    }

    /**
     * 获取项目版本
     */
    public static String getVersion() {
        return StringUtils.nvl(getConfig("feather.version"), "4.0.0");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled() {
        return Boolean.valueOf(getConfig("feather.addressEnabled"));
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile() {
        String profile = getConfig("feather.profile");
        if (profile != null && profile.length() > 1 && profile.charAt(1) == ':') {
            String os = System.getProperty("os.name");
            if (!os.toLowerCase().startsWith("win")) {// 如果不是Windows系统
                profile = profile.substring(2);
            }
        }
        return profile;
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }
}
