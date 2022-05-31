package com.springmix.wh.mixspringbootstarterautoconfiguration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * package com.springmix.wh.mixspringbootstarterautoconfiguration.properties
 *
 * @author wangfei
 * @version 1.0.0
 * @ClassName DESEDEAESProperties.java
 * @Description UTIL
 * @createTime 2022年05月30日 17:41:00
 */
@ConfigurationProperties(prefix = "mix.desede")
public class DESEDEAESProperties {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
