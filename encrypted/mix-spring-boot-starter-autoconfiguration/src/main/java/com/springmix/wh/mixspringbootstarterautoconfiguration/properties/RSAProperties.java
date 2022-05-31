package com.springmix.wh.mixspringbootstarterautoconfiguration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * package com.springmix.wh.mixspringbootstarterautoconfiguration.properties
 *
 * @author wangfei
 * @version 1.0.0
 * @ClassName RSAProperties.java
 * @Description UTIL
 * @createTime 2022年05月30日 17:28:00
 */

@ConfigurationProperties(prefix = "mix.rsa")
public class RSAProperties {

    private String privateKey;

    private List<String> publicKey;


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public List<String> getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(List<String> publicKey) {
        this.publicKey = publicKey;
    }
}
