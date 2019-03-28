package com.app.core.common;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 *
 * <pre>
 * 配置文件工具类。
 * </pre>
 *
 * @author maipeitian<maipeitian@foresee.com.cn>
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:2012-3-20     修改内容:
 * </pre>
 */
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * 存放各个配置文件，按需加载
     */
    private static Hashtable<String, Properties> map = new Hashtable<String, Properties>();

    public PropertyUtil(){

    }

    public static Properties load(String filePath){

        if (map.get(filePath) == null) {
            Properties properties = null;
            ClassPathResource resource = new ClassPathResource(filePath);
            if(resource.exists()){
                try {
                    properties = PropertiesLoaderUtils.loadProperties(resource);
                    map.put(filePath, properties);
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
            return properties;
        }else{
            return map.get(filePath);
        }

    }

    /**
     * 获取指定配置文件中的指定配置项
     * @param propertyName
     * @return
     */
    public static String getProperty(Properties properties ,String propertyName) {
        String property = "";
        if(null!=properties){
            for (Entry<Object, Object> p : properties.entrySet()) {
                String propKey = (String) p.getKey();
                if (propKey.matches(propertyName.replace(".", "\\."))) {
                    return (String) p.getValue();
                }
            }
        }
        return property;
    }

}
