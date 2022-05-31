package com.springmix.wh.mixspringbootstarterautoconfiguration.autoConfiguration;

import com.springmix.wh.mixspringbootstarterautoconfiguration.common.EncryptionUtils;
import com.springmix.wh.mixspringbootstarterautoconfiguration.properties.AESProperties;
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
@EnableConfigurationProperties({RSAProperties.class,AESProperties.class})
public class MIxConfiguration {


    @ConditionalOnMissingBean(EncryptionUtils.class)
    @Bean
    public EncryptionUtils encryptionUtils(RSAProperties rsaProperties) {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        encryptionUtils.setPrivateKey(rsaProperties.getPrivateKey());
        encryptionUtils.setPublicKey(rsaProperties.getPublicKey());
        return encryptionUtils;
    }




}
