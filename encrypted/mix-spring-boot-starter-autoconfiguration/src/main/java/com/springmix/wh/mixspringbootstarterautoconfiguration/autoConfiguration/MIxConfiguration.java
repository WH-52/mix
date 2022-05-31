package com.springmix.wh.mixspringbootstarterautoconfiguration.autoConfiguration;

import com.springmix.wh.mixspringbootstarterautoconfiguration.common.EncryptionUtils;
import com.springmix.wh.mixspringbootstarterautoconfiguration.properties.DESEDEAESProperties;
import com.springmix.wh.mixspringbootstarterautoconfiguration.properties.RSAProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * package com.springmix.wh.mixspringbootstarterautoconfiguration.properties
 *
 * @author wangfei
 * @version 1.0.0
 * @ClassName MIxConfiguration.java
 * @Description UTIL
 * @createTime 2022年05月30日 17:47:00
 */
@Configuration
@EnableConfigurationProperties({RSAProperties.class, DESEDEAESProperties.class})
public class MixConfiguration {


    @ConditionalOnMissingBean(EncryptionUtils.class)
    @Bean
    public EncryptionUtils encryptionUtils(RSAProperties rsaProperties,DESEDEAESProperties properties) {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        encryptionUtils.setPrivateKey(rsaProperties.getPrivateKey());
        encryptionUtils.setPublicKey(rsaProperties.getPublicKey());
        encryptionUtils.setDesedeKey(properties.getKey());
        return encryptionUtils;
    }




}
